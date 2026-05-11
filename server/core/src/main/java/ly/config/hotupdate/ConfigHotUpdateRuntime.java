package ly.config.hotupdate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import ly.ConfigLoadException;
import ly.ConfigService;
import ly.LoggerDef;
import ly.ServerContext;

/** 业务服收到 GM 热更指令后的本地执行器。 */
public class ConfigHotUpdateRuntime {
  private static final ObjectMapper MAPPER = new ObjectMapper();
  private static final HttpClient HTTP_CLIENT = HttpClient.newHttpClient();
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
      Path configDir = Path.of(ServerContext.serverConfig.configPath);
      Path stagingDir = configDir.resolveSibling(".config-hot-update-staging").resolve(command.version);
      Files.createDirectories(stagingDir);
      JsonNode files = downloadFiles(command.downloadUrl).path("data");
      if (!files.isArray()) {
        throw new ConfigLoadException("配置热更下载结果格式错误");
      }
      for (JsonNode file : files) {
        Files.writeString(
            stagingDir.resolve(file.path("fileName").asText()),
            file.path("content").asText(),
            StandardCharsets.UTF_8);
      }
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

  private static void commit(ConfigHotUpdateCommand command) throws Exception {
    try {
      if (preparedCommand == null
          || preparedDir == null
          || !preparedCommand.version.equals(command.version)
          || !preparedCommand.publishId.equals(command.publishId)) {
        throw new ConfigLoadException("当前服务器没有该版本的 READY 配置:" + command.version);
      }
      Path configDir = Path.of(ServerContext.serverConfig.configPath);
      Files.createDirectories(configDir);
      try (var stream = Files.list(preparedDir)) {
        for (Path file : stream.toList()) {
          Files.copy(file, configDir.resolve(file.getFileName()), StandardCopyOption.REPLACE_EXISTING);
        }
      }
      ConfigService.getInstance()
          .schedulePreparedSwitch(LoggerDef.SystemLogger, command.version, command.switchAtMillis, 60_000L);
      report(command, "SWITCH_SCHEDULED", "");
    } catch (Exception e) {
      report(command, "FAILED", e.getMessage());
      throw e;
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
