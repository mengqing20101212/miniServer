package ly.gmserver.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HexFormat;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import ly.ConfigService;
import ly.LoggerDef;
import ly.ServerContext;
import ly.db.entry.GmConfigFileEntry;
import ly.db.entry.GmConfigFileEntryHelper;
import ly.db.entry.GmConfigServerStatusEntry;
import ly.db.entry.GmConfigServerStatusEntryHelper;
import ly.db.entry.GmConfigVersionEntry;
import ly.db.entry.GmConfigVersionEntryHelper;
import ly.nacos.NacosServerNode;
import ly.nacos.NacosService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/** GM 配表热更服务：上传即校验，发布走 PREPARE/COMMIT 两阶段。 */
@Service
public class ConfigHotUpdateService {
  private static final String STATUS_UPLOADING = "UPLOADING";
  private static final String STATUS_PREPARING = "PREPARING";
  private static final String STATUS_COMMITTED = "COMMITTED";
  private static final String STATUS_PUBLISHED = "PUBLISHED";
  private static final ObjectMapper MAPPER = new ObjectMapper();

  @Value("${server.port:9090}")
  private int gmHttpPort;

  public List<Map<String, Object>> upload(String version, MultipartFile[] files, String operator)
      throws Exception {
    if (version == null || version.isBlank()) {
      throw new IllegalArgumentException("版本号不能为空");
    }
    GmConfigVersionEntry versionEntry = ensureEditableVersion(version, operator);
    List<Map<String, Object>> results = new ArrayList<>();
    for (MultipartFile file : files) {
      String fileName = Path.of(file.getOriginalFilename()).getFileName().toString();
      if (!fileName.endsWith(".txt")) {
        results.add(Map.of("fileName", fileName, "success", false, "message", "只允许上传 txt 配表"));
        continue;
      }
      String content = new String(file.getBytes(), StandardCharsets.UTF_8);
      Path tempDir = Files.createTempDirectory("gm-config-check-" + version + "-");
      Files.writeString(tempDir.resolve(fileName), content, StandardCharsets.UTF_8);
      try {
        ConfigService.getInstance().validateSingleConfigFile(LoggerDef.SystemLogger, tempDir.toString(), fileName);
        upsertFile(version, fileName, content);
        results.add(Map.of("fileName", fileName, "success", true, "message", "校验通过并已保存"));
      } catch (Exception e) {
        results.add(Map.of("fileName", fileName, "success", false, "message", e.getMessage()));
      }
    }
    versionEntry.setUpdateTime(LocalDateTime.now());
    GmConfigVersionEntryHelper.update(versionEntry, "update_time");
    return results;
  }

  public List<GmConfigVersionEntry> listVersions() {
    return GmConfigVersionEntryHelper.listAll();
  }

  public List<GmConfigFileEntry> listFiles(String version) {
    return GmConfigFileEntryHelper.listByVersion(version);
  }

  public List<GmConfigServerStatusEntry> listServerStatus(String version) {
    return GmConfigServerStatusEntryHelper.listByPublishId(version);
  }

  public void prepare(String version, long switchAtMillis, String operator) throws Exception {
    GmConfigVersionEntry versionEntry = ensureEditableVersion(version, operator);
    checkVersionFiles(version);
    createPendingStatus(version);
    String command =
        MAPPER.writeValueAsString(
            Map.of(
                "commandType", "PREPARE",
                "publishId", version,
                "version", version,
                "downloadUrl", buildDownloadUrl(version),
                "reportUrl", buildReportUrl(),
                "switchAtMillis", switchAtMillis));
    if (!NacosService.getInstance().publishConfigHotUpdate(command)) {
      throw new IllegalStateException("Nacos 热更准备指令发布失败");
    }
    versionEntry.setStatus(STATUS_PREPARING);
    versionEntry.setSwitchAtMillis(switchAtMillis);
    versionEntry.setOperator(operator);
    versionEntry.setUpdateTime(LocalDateTime.now());
    GmConfigVersionEntryHelper.update(versionEntry, "status", "switch_at_millis", "operator", "update_time");
  }

  public void commit(String version, String operator) throws Exception {
    GmConfigVersionEntry versionEntry = GmConfigVersionEntryHelper.getByVersion(version);
    if (versionEntry == null) {
      throw new IllegalStateException("版本不存在:" + version);
    }
    List<GmConfigServerStatusEntry> statusList = listServerStatus(version);
    if (statusList.isEmpty() || statusList.stream().anyMatch(s -> !"READY".equals(s.getStatus()))) {
      throw new IllegalStateException("仍有服务器未 READY，不能提交切换");
    }
    String command =
        MAPPER.writeValueAsString(
            Map.of(
                "commandType", "COMMIT",
                "publishId", version,
                "version", version,
                "reportUrl", buildReportUrl(),
                "switchAtMillis", versionEntry.getSwitchAtMillis()));
    if (!NacosService.getInstance().publishConfigHotUpdate(command)) {
      throw new IllegalStateException("Nacos 热更提交指令发布失败");
    }
    versionEntry.setStatus(STATUS_COMMITTED);
    versionEntry.setOperator(operator);
    versionEntry.setUpdateTime(LocalDateTime.now());
    GmConfigVersionEntryHelper.update(versionEntry, "status", "operator", "update_time");
  }

  public void report(
      String publishId, String version, String serverId, String serverType, String status, String errorMsg) {
    LocalDateTime now = LocalDateTime.now();
    GmConfigServerStatusEntry entry = GmConfigServerStatusEntryHelper.get(publishId, serverId);
    boolean create = entry == null;
    if (create) {
      entry = new GmConfigServerStatusEntry();
      entry.setPublishId(publishId);
      entry.setVersion(version);
      entry.setServerId(serverId);
      entry.setServerType(serverType);
      entry.setCreateTime(now);
    }
    entry.setStatus(status);
    entry.setErrorMsg(errorMsg == null ? "" : errorMsg);
    entry.setUpdateTime(now);
    if (create) {
      GmConfigServerStatusEntryHelper.save(entry);
    } else {
      GmConfigServerStatusEntryHelper.update(entry, "status", "error_msg", "update_time");
    }
  }

  public List<Map<String, String>> download(String version) {
    return GmConfigFileEntryHelper.listByVersion(version).stream()
        .map(e -> Map.of("fileName", e.getFileName(), "content", e.getContent()))
        .toList();
  }

  private void checkVersionFiles(String version) {
    Set<String> expected = ConfigService.getInstance().getExpectedConfigFileNames();
    Set<String> uploaded =
        GmConfigFileEntryHelper.listByVersion(version).stream()
            .map(GmConfigFileEntry::getFileName)
            .collect(Collectors.toSet());
    expected.removeAll(uploaded);
    if (!expected.isEmpty()) {
      throw new IllegalStateException("版本缺少配表文件，不能发布: " + expected);
    }
  }

  private void createPendingStatus(String version) {
    for (NacosServerNode node : NacosService.getInstance().getNodeMap().values()) {
      if (!node.canUse() || ServerContext.getServerId().equals(node.getServerId())) {
        continue;
      }
      report(version, version, node.getServerId(), node.getServerType().getType(), "PENDING", "");
    }
  }

  private GmConfigVersionEntry ensureEditableVersion(String version, String operator) {
    GmConfigVersionEntry entry = GmConfigVersionEntryHelper.getByVersion(version);
    if (entry != null && (STATUS_PUBLISHED.equals(entry.getStatus()) || STATUS_COMMITTED.equals(entry.getStatus()))) {
      throw new IllegalStateException("已提交版本不能覆盖: " + version);
    }
    if (entry != null) {
      return entry;
    }
    LocalDateTime now = LocalDateTime.now();
    entry = new GmConfigVersionEntry();
    entry.setVersion(version);
    entry.setStatus(STATUS_UPLOADING);
    entry.setOperator(operator);
    entry.setRemark("");
    entry.setCreateTime(now);
    entry.setUpdateTime(now);
    GmConfigVersionEntryHelper.save(entry);
    return entry;
  }

  private void upsertFile(String version, String fileName, String content) throws Exception {
    GmConfigFileEntry entry = GmConfigFileEntryHelper.get(version, fileName);
    LocalDateTime now = LocalDateTime.now();
    boolean create = entry == null;
    if (create) {
      entry = new GmConfigFileEntry();
      entry.setVersion(version);
      entry.setFileName(fileName);
      entry.setCreateTime(now);
    }
    entry.setContent(content);
    entry.setContentMd5(md5(content));
    entry.setFileSize(content.getBytes(StandardCharsets.UTF_8).length);
    entry.setUpdateTime(now);
    if (create) {
      GmConfigFileEntryHelper.save(entry);
    } else {
      GmConfigFileEntryHelper.update(entry, "content", "content_md5", "file_size", "update_time");
    }
  }

  private String buildDownloadUrl(String version) {
    return buildBaseUrl() + "/api/config-hot-update/download/" + version;
  }

  private String buildReportUrl() {
    return buildBaseUrl() + "/api/config-hot-update/report";
  }

  private String buildBaseUrl() {
    String ip =
        ServerContext.serverConfig != null && ServerContext.serverConfig.serverIp != null
            ? ServerContext.serverConfig.serverIp
            : "127.0.0.1";
    return "http://" + ip + ":" + gmHttpPort;
  }

  private String md5(String content) throws Exception {
    return HexFormat.of().formatHex(MessageDigest.getInstance("MD5").digest(content.getBytes(StandardCharsets.UTF_8)));
  }
}
