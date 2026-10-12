package ly.gmserver.service;

import com.fasterxml.jackson.databind.ObjectMapper;

import ly.ConfigService;
import ly.LoggerDef;
import ly.ServerContext;
import ly.config.hotupdate.*;
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
import java.util.TreeMap;
import java.util.stream.Collectors;

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

    public synchronized List<Map<String, Object>> upload(
            String version, MultipartFile[] files, String operator) throws Exception {
        if (version == null || version.isBlank()) {
            throw new IllegalArgumentException("版本号不能为空");
        }
        if (!version.matches("[A-Za-z0-9_-]+"))
            throw new IllegalArgumentException("版本号只能包含字母、数字、下划线和横线");
        if (Files.exists(publicationPath())) {
            ConfigPublication frozen = readPublication();
            if (frozen.command.version.equals(version))
                throw new IllegalStateException("已冻结版本不可修改");
        }
        GmConfigVersionEntry versionEntry = ensureEditableVersion(version, operator);
        List<Map<String, Object>> results = new ArrayList<>();
        for (MultipartFile file : files) {
            String fileName = Path.of(file.getOriginalFilename()).getFileName().toString();
            if (!fileName.endsWith(".txt")) {
                results.add(
                        Map.of("fileName", fileName, "success", false, "message", "只允许上传 txt 配表"));
                continue;
            }
            String content = new String(file.getBytes(), StandardCharsets.UTF_8);
            Path tempDir = Files.createTempDirectory("gm-config-check-" + version + "-");
            Files.writeString(tempDir.resolve(fileName), content, StandardCharsets.UTF_8);
            try {
                ConfigService.getInstance()
                        .validateSingleConfigFile(
                                LoggerDef.SystemLogger, tempDir.toString(), fileName);
                upsertFile(version, fileName, content);
                results.add(Map.of("fileName", fileName, "success", true, "message", "校验通过并已保存"));
            } catch (Exception e) {
                results.add(
                        Map.of("fileName", fileName, "success", false, "message", e.getMessage()));
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

    private Path publicationPath() {
        String root =
                System.getProperty(
                        "miniserver.config.publishPath",
                        Path.of(ServerContext.serverConfig.configPath)
                                .resolveSibling(".config-publications")
                                .toString());
        return Path.of(root).resolve(ServerContext.ENV).resolve("publication.json");
    }

    private ConfigPublication readPublication() throws Exception {
        return DurableConfigFile.read(publicationPath(), ConfigPublication.class);
    }

    public synchronized void prepare(
            String version,
            long switchAtMillis,
            String operator,
            java.util.Set<String> targetServerIds)
            throws Exception {
        if (targetServerIds == null || targetServerIds.isEmpty())
            throw new IllegalArgumentException("必须明确选择本次热更目标节点");
        if (!version.matches("[A-Za-z0-9_-]+")) throw new IllegalArgumentException("非法版本号");
        long sequence = 1;
        ConfigPublication previous = null;
        if (Files.exists(publicationPath())) {
            previous = readPublication();
            if (!java.util.Set.of("ACTIVE", "CANCELLED").contains(previous.phase))
                throw new IllegalStateException("上一发布尚未完成或取消");
            if (previous.command.version.equals(version))
                throw new IllegalStateException("发布版本不可复用");
            sequence = previous.command.sequence + 1;
        }
        GmConfigVersionEntry versionEntry = ensureEditableVersion(version, operator);
        checkVersionFiles(version);
        ConfigPublication publication = new ConfigPublication();
        Path committedPath = publicationPath().resolveSibling("committed.json");
        publication.files.putAll(
                Files.exists(committedPath)
                        ? DurableConfigFile.read(committedPath, ConfigPublication.class).files
                        : ConfigBundle.read(Path.of(ServerContext.serverConfig.configPath)));
        for (GmConfigFileEntry file : listFiles(version))
            publication.files.put(file.getFileName(), file.getContent());
        if (!publication
                .files
                .keySet()
                .equals(ConfigService.getInstance().getExpectedConfigFileNames()))
            throw new IllegalStateException("基线不是完整配置，不能发布");
        Path checkDir = Files.createTempDirectory("config-bundle-check-");
        try {
            for (var file : publication.files.entrySet()) {
                ConfigBundle.validateName(file.getKey());
                Files.writeString(checkDir.resolve(file.getKey()), file.getValue());
            }
            ConfigService.getInstance()
                    .loadSnapshot(LoggerDef.SystemLogger, checkDir.toString(), version);
        } finally {
            try (var files = Files.walk(checkDir)) {
                for (Path file : files.sorted(java.util.Comparator.reverseOrder()).toList())
                    Files.delete(file);
            }
        }
        ConfigHotUpdateCommand command = new ConfigHotUpdateCommand();
        command.commandType = "PREPARE";
        command.publishId = version;
        command.version = version;
        command.sequence = sequence;
        command.digest = ConfigBundle.digest(publication.files);
        command.downloadUrl = buildDownloadUrl(version);
        command.reportUrl = buildReportUrl();
        command.targets = new TreeMap<>();
        for (NacosServerNode node : NacosService.getInstance().getNodeMap().values()) {
            if (!targetServerIds.contains(node.getServerId())
                    || !node.canUse()
                    || ServerContext.getServerId().equals(node.getServerId())) continue;
            String bootId = node.getInstance().getMetadata().get("configBootId");
            if (bootId == null)
                throw new IllegalStateException("节点不支持本轮热更协议: " + node.getServerId());
            command.targets.put(node.getServerId(), bootId);
            publication.statuses.put(node.getServerId(), "PENDING");
        }
        if (!command.targets.keySet().equals(targetServerIds))
            throw new IllegalStateException("目标节点不存在、离线或不可参与发布");
        publication.command = command;
        // 完整包和目标集合先落盘，通知失败可直接重试，不能重新拼接节点本地基线。
        DurableConfigFile.write(publicationPath(), publication);
        createPendingStatus(version, command.targets.keySet());
        versionEntry.setStatus(STATUS_PREPARING);
        versionEntry.setSwitchAtMillis(switchAtMillis);
        versionEntry.setOperator(operator);
        GmConfigVersionEntryHelper.update(versionEntry, "status", "switch_at_millis", "operator");
        publish(publication);
    }

    public synchronized void commit(String version, String operator) throws Exception {
        ConfigPublication publication = readPublication();
        if (!publication.command.version.equals(version))
            throw new IllegalStateException("发布版本不匹配");
        if ("COMMITTED".equals(publication.phase) || "ACTIVE".equals(publication.phase)) {
            publish(publication);
            return;
        }
        for (var target : publication.command.targets.entrySet()) {
            NacosServerNode node = NacosService.getInstance().getNodeMap().get(target.getKey());
            if (node == null
                    || !node.canUse()
                    || !target.getValue()
                            .equals(node.getInstance().getMetadata().get("configBootId")))
                throw new IllegalStateException("目标节点离线或实例变化: " + target.getKey());
        }
        GmConfigVersionEntry entry = GmConfigVersionEntryHelper.getByVersion(version);
        long switchAt = Math.max(entry.getSwitchAtMillis(), System.currentTimeMillis() + 15_000);
        publication.commit(System.currentTimeMillis(), switchAt);
        DurableConfigFile.write(publicationPath(), publication);
        // 保留独立的正式决定，即使下一轮 PREPARE 也不覆盖；供新节点及重启恢复。
        DurableConfigFile.write(publicationPath().resolveSibling("committed.json"), publication);
        entry.setStatus(STATUS_COMMITTED);
        entry.setSwitchAtMillis(switchAt);
        GmConfigVersionEntryHelper.update(entry, "status", "switch_at_millis");
        publish(publication);
    }

    public synchronized void cancel(String version) throws Exception {
        ConfigPublication publication = readPublication();
        if (!publication.command.version.equals(version) || !"PREPARING".equals(publication.phase))
            throw new IllegalStateException("只能取消尚未提交的对应版本");
        publication.phase = "CANCELLED";
        publication.command.commandType = "CANCEL";
        DurableConfigFile.write(publicationPath(), publication);
        publish(publication);
    }

    private void publish(ConfigPublication publication) throws Exception {
        if ("COMMIT".equals(publication.command.commandType)) {
            if (!NacosService.getInstance()
                    .publishActiveConfig(MAPPER.writeValueAsString(publication.command)))
                throw new IllegalStateException("正式配置决定通知失败，持久化决定保留并重试");
        }
        if (!NacosService.getInstance()
                .publishConfigHotUpdate(MAPPER.writeValueAsString(publication.command)))
            throw new IllegalStateException("配置发布通知失败，持久化决定保留并重试");
    }

    @jakarta.annotation.PostConstruct
    public void startPublicationRecovery() {
        java.util.concurrent.Executors.newSingleThreadScheduledExecutor(
                        Thread.ofVirtual().name("config-publication-recovery-").factory())
                .scheduleWithFixedDelay(
                        () -> {
                            synchronized (this) {
                                try {
                                    if (!Files.exists(publicationPath())) return;
                                    ConfigPublication publication = readPublication();
                                    if ("COMMIT".equals(publication.command.commandType))
                                        DurableConfigFile.write(
                                                publicationPath().resolveSibling("committed.json"),
                                                publication);
                                    Path committed =
                                            publicationPath().resolveSibling("committed.json");
                                    if (Files.exists(committed)) {
                                        ConfigPublication active =
                                                DurableConfigFile.read(
                                                        committed, ConfigPublication.class);
                                        NacosService.getInstance()
                                                .publishActiveConfig(
                                                        MAPPER.writeValueAsString(active.command));
                                    }
                                    publish(publication);
                                } catch (Exception error) {
                                    LoggerDef.SystemLogger.error("恢复配置发布通知失败", error);
                                }
                            }
                        },
                        5,
                        5,
                        java.util.concurrent.TimeUnit.SECONDS);
    }

    public synchronized void reportVerified(
            String publishId,
            String version,
            String serverId,
            String serverType,
            String status,
            String errorMsg,
            String bootId,
            String digest,
            long nodeTimeMillis)
            throws Exception {
        ConfigPublication publication = readPublication();
        if (!publication.command.publishId.equals(publishId)
                || !publication.command.version.equals(version)) return;
        publication.acceptReport(
                serverId, bootId, digest, status, nodeTimeMillis, System.currentTimeMillis());
        DurableConfigFile.write(publicationPath(), publication);
        report(publishId, version, serverId, serverType, status, errorMsg);
        if ("ACTIVE".equals(publication.phase)) {
            GmConfigVersionEntry entry = GmConfigVersionEntryHelper.getByVersion(version);
            entry.setStatus(STATUS_PUBLISHED);
            GmConfigVersionEntryHelper.update(entry, "status");
        }
    }

    public void report(
            String publishId,
            String version,
            String serverId,
            String serverType,
            String status,
            String errorMsg) {
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

    public synchronized List<Map<String, String>> download(String version) throws Exception {
        ConfigPublication publication = readPublication();
        if (!publication.command.version.equals(version)) {
            Path committed = publicationPath().resolveSibling("committed.json");
            publication = DurableConfigFile.read(committed, ConfigPublication.class);
        }
        if (!publication.command.version.equals(version))
            throw new IllegalStateException("不可下载的配置版本");
        return publication.files.entrySet().stream()
                .map(file -> Map.of("fileName", file.getKey(), "content", file.getValue()))
                .toList();
    }

    private void checkVersionFiles(String version) {
        Set<String> expected = ConfigService.getInstance().getExpectedConfigFileNames();
        Set<String> uploaded =
                GmConfigFileEntryHelper.listByVersion(version).stream()
                        .map(GmConfigFileEntry::getFileName)
                        .collect(Collectors.toSet());
        if (uploaded.isEmpty()) {
            throw new IllegalStateException("版本没有上传任何配表文件，不能发布");
        }
        uploaded.removeAll(expected);
        if (!uploaded.isEmpty()) {
            throw new IllegalStateException("版本包含未知配表文件，不能发布: " + uploaded);
        }
    }

    private void createPendingStatus(String version, java.util.Set<String> targetServerIds) {
        for (NacosServerNode node : NacosService.getInstance().getNodeMap().values()) {
            if (!targetServerIds.contains(node.getServerId())) {
                continue;
            }
            report(
                    version,
                    version,
                    node.getServerId(),
                    node.getServerType().getType(),
                    "PENDING",
                    "");
        }
    }

    private GmConfigVersionEntry ensureEditableVersion(String version, String operator) {
        GmConfigVersionEntry entry = GmConfigVersionEntryHelper.getByVersion(version);
        if (entry != null
                && (STATUS_PUBLISHED.equals(entry.getStatus())
                        || STATUS_COMMITTED.equals(entry.getStatus()))) {
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
            GmConfigFileEntryHelper.update(
                    entry, "content", "content_md5", "file_size", "update_time");
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
        return HexFormat.of()
                .formatHex(
                        MessageDigest.getInstance("MD5")
                                .digest(content.getBytes(StandardCharsets.UTF_8)));
    }
}
