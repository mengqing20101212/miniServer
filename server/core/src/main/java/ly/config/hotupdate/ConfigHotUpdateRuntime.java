package ly.config.hotupdate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import ly.ConfigLoadException;
import ly.ConfigService;
import ly.LoggerDef;
import ly.ServerContext;

/** 业务服收到 GM 配置热更指令后的本地执行器。 */
public class ConfigHotUpdateRuntime {
  private static final ObjectMapper MAPPER = new ObjectMapper();
  private static final HttpClient HTTP_CLIENT = HttpClient.newHttpClient();
  private static final String CHANGE_MANIFEST = "__changed_files.manifest";

  private static volatile boolean running;
  private static volatile ConfigHotUpdateCommand preparedCommand;
  private static volatile Path preparedDir;

  public static synchronized void handle(String content) {
    if (content == null || content.isBlank()) {
      return;
    }
    if (running) {
      LoggerDef.SystemLogger.error("已有配置热更任务正在执行，本次指令忽略: {}", content);
      return;
    }
    running = true;
    try {
      ConfigHotUpdateCommand command = MAPPER.readValue(content, ConfigHotUpdateCommand.class);
      String type = command.commandType == null ? "PREPARE" : command.commandType;
      if ("PREPARE".equalsIgnoreCase(type)) {
        prepare(command);
      } else if ("COMMIT".equalsIgnoreCase(type)) {
        commit(command);
      } else if ("CANCEL".equalsIgnoreCase(type)) {
        cancel(command);
      }
    } catch (Exception e) {
      LoggerDef.SystemLogger.error("配置热更执行失败, content={}", content, e);
    } finally {
      running = false;
    }
  }

  private static void prepare(ConfigHotUpdateCommand command) throws Exception {
    try {
      validate(command);
      report(command, "RECEIVED", "");
      Path configDir = Path.of(ServerContext.serverConfig.configPath.replace('\\', '/'));
      Path stagingDir =
          configDir
              .resolveSibling(".config-hot-update-staging")
              .resolve(command.version)
              .resolve(ServerContext.getServerId());
      resetDirectory(stagingDir);
      // 热更版本只需要包含本次变更的表，服务端用当前线上配置做基线，再用 GM 上传文件覆盖。
      copyCurrentConfigAsBaseline(configDir, stagingDir);
      JsonNode files = downloadFiles(command.downloadUrl).path("data");
      if (!files.isArray()) {
        throw new ConfigLoadException("配置热更下载结果格式错误");
      }
      List<String> changedFiles = new ArrayList<>();
      for (JsonNode file : files) {
        String fileName = file.path("fileName").asText();
        Files.writeString(stagingDir.resolve(fileName), file.path("content").asText(), StandardCharsets.UTF_8);
        changedFiles.add(fileName);
      }
      Files.write(stagingDir.resolve(CHANGE_MANIFEST), changedFiles, StandardCharsets.UTF_8);
      report(command, "DOWNLOADED", "");
      ConfigService.getInstance()
          .loadAllConfigToStandby(LoggerDef.SystemLogger, stagingDir.toString(), command.version);
      preparedCommand = command;
      preparedDir = stagingDir;
      report(command, "READY", "");
    } catch (Exception e) {
      report(command, "FAILED", e.getMessage());
      throw e;
    }
  }

  private static void resetDirectory(Path dir) throws Exception {
    if (Files.exists(dir)) {
      try (var stream = Files.walk(dir)) {
        for (Path path : stream.sorted(Comparator.reverseOrder()).toList()) {
          Files.delete(path);
        }
      }
    }
    Files.createDirectories(dir);
  }

  private static void copyCurrentConfigAsBaseline(Path configDir, Path stagingDir) throws Exception {
    if (!Files.isDirectory(configDir)) {
      throw new ConfigLoadException("当前配置目录不存在，不能执行配置热更:" + configDir);
    }
    try (var stream = Files.list(configDir)) {
      for (Path file : stream.toList()) {
        if (Files.isRegularFile(file) && file.getFileName().toString().endsWith(".txt")) {
          Files.copy(file, stagingDir.resolve(file.getFileName()), StandardCopyOption.REPLACE_EXISTING);
        }
      }
    }
  }

  private static void commit(ConfigHotUpdateCommand command) throws Exception {
    try {
      if (preparedCommand == null
          || preparedDir == null
          || !preparedCommand.version.equals(command.version)
          || !preparedCommand.publishId.equals(command.publishId)) {
        throw new ConfigLoadException("当前服务器没有该版本的 READY 配置:" + command.version);
      }
      Path configDir = Path.of(ServerContext.serverConfig.configPath.replace('\\', '/'));
      Files.createDirectories(configDir);
      copyChangedFilesToConfigDir(configDir, preparedDir);
      ConfigService.getInstance()
          .schedulePreparedSwitch(LoggerDef.SystemLogger, command.version, command.switchAtMillis, 60_000L);
      report(command, "SWITCH_SCHEDULED", "");
    } catch (Exception e) {
      report(command, "FAILED", e.getMessage());
      throw e;
    }
  }

  private static void copyChangedFilesToConfigDir(Path configDir, Path preparedDir) throws Exception {
    Path manifest = preparedDir.resolve(CHANGE_MANIFEST);
    if (!Files.exists(manifest)) {
      throw new ConfigLoadException("配置热更缺少变更文件清单:" + manifest);
    }
    List<String> changedFiles = Files.readAllLines(manifest, StandardCharsets.UTF_8);
    Path lockFile = configDir.resolve(".config-hot-update.lock");
    // 本地开发时多个服务可能共用同一个 configDir，文件锁用于串行化覆盖动作，避免 Windows 文件占用冲突。
    try (FileChannel channel =
            FileChannel.open(lockFile, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
        FileLock ignored = channel.lock()) {
      for (String fileName : changedFiles) {
        if (fileName == null || fileName.isBlank()) {
          continue;
        }
        Path src = preparedDir.resolve(fileName);
        if (!Files.isRegularFile(src)) {
          throw new ConfigLoadException("配置热更变更文件不存在:" + src);
        }
        Files.copy(src, configDir.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
      }
    }
  }

  private static void cancel(ConfigHotUpdateCommand command) {
    preparedCommand = null;
    preparedDir = null;
    report(command, "CANCELLED", "");
  }

  private static void validate(ConfigHotUpdateCommand command) throws ConfigLoadException {
    if (command.publishId == null || command.publishId.isBlank()) {
      throw new ConfigLoadException("配置热更发布ID为空");
    }
    if (command.version == null || command.version.isBlank()) {
      throw new ConfigLoadException("配置热更版本号为空");
    }
    if (command.downloadUrl == null || command.downloadUrl.isBlank()) {
      throw new ConfigLoadException("配置热更下载地址为空");
    }
  }

  private static JsonNode downloadFiles(String url) throws Exception {
    HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
    HttpResponse<String> response =
        HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
    if (response.statusCode() != 200) {
      throw new ConfigLoadException("配置热更下载失败, status=" + response.statusCode());
    }
    return MAPPER.readTree(response.body());
  }

  private static void report(ConfigHotUpdateCommand command, String status, String errorMsg) {
    if (command == null || command.reportUrl == null || command.reportUrl.isBlank()) {
      return;
    }
    try {
      String body =
          "publishId=" + enc(command.publishId)
              + "&version=" + enc(command.version)
              + "&serverId=" + enc(ServerContext.getServerId())
              + "&serverType=" + enc(ServerContext.serverType.getType())
              + "&status=" + enc(status)
              + "&errorMsg=" + enc(errorMsg == null ? "" : errorMsg);
      HttpRequest request =
          HttpRequest.newBuilder()
              .uri(URI.create(command.reportUrl))
              .header("Content-Type", "application/x-www-form-urlencoded")
              .POST(HttpRequest.BodyPublishers.ofString(body, StandardCharsets.UTF_8))
              .build();
      HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.discarding());
    } catch (Exception e) {
      LoggerDef.SystemLogger.error("上报配置热更状态失败, status={}", status, e);
    }
  }

  private static String enc(String value) {
    return URLEncoder.encode(value == null ? "" : value, StandardCharsets.UTF_8);
  }
}
