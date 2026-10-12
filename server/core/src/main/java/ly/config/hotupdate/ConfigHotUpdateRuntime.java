package ly.config.hotupdate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import ly.*;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.*;

/** 节点热更状态机。提交决定落盘后只向前恢复，不清理业务持有的旧快照。 */
public final class ConfigHotUpdateRuntime {
    public static final String BOOT_ID = UUID.randomUUID().toString();
    private static final ObjectMapper JSON = new ObjectMapper();
    private static final HttpClient HTTP =
            HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(5)).build();
    private static final ScheduledExecutorService RECOVERY =
            Executors.newSingleThreadScheduledExecutor(
                    Thread.ofVirtual().name("config-recovery-").factory());
    private static ConfigHotUpdateCommand prepared;
    private static ConfigHotUpdateCommand committed;
    private static long appliedSequence;
    private static String lastStatus = "";
    private static boolean recoveryStarted;

    private ConfigHotUpdateRuntime() {}

    private static Path root() {
        String configured = System.getProperty("miniserver.config.statePath");
        return configured == null
                ? Path.of(ServerContext.serverConfig.configPath)
                        .resolveSibling(".config-versions")
                        .resolve(ServerContext.ENV)
                        .resolve(ServerContext.getServerId())
                : Path.of(configured);
    }

    private static Path versionDirectory(ConfigHotUpdateCommand command) {
        return root().resolve(command.version);
    }

    public static synchronized void handle(String content) {
        if (content == null || content.isBlank()) return;
        try {
            apply(JSON.readValue(content, ConfigHotUpdateCommand.class), false);
        } catch (Exception error) {
            LoggerDef.SystemLogger.error("配置发布处理失败，保留原状态并等待恢复", error);
        }
    }

    private static void validate(ConfigHotUpdateCommand command) {
        if (command.version == null
                || !command.version.matches("[A-Za-z0-9_-]+")
                || command.sequence <= 0
                || command.publishId == null
                || command.digest == null) throw new ConfigLoadException("无效的发布版本、序号或摘要");
    }

    private static synchronized void apply(ConfigHotUpdateCommand command, boolean recovery)
            throws Exception {
        validate(command);
        if (!Set.of("PREPARE", "COMMIT", "CANCEL").contains(command.commandType))
            throw new ConfigLoadException("未知发布指令");
        if (command.sequence < appliedSequence
                || (committed != null && command.sequence < committed.sequence)) return;
        boolean commit = "COMMIT".equals(command.commandType);
        if (!command.targets.containsKey(ServerContext.getServerId())) return;
        if (command.sequence == appliedSequence) {
            if (commit) report(command, "ACTIVE", "");
            return;
        }
        if (committed != null && command.sequence == committed.sequence) {
            if (!Objects.equals(command.digest, committed.digest))
                throw new ConfigLoadException("同一发布序号内容冲突");
            if (ConfigService.getInstance().getActiveVersion().equals(command.version)) {
                appliedSequence = command.sequence;
                report(command, "ACTIVE", "");
                return;
            }
            if (lastStatus.equals("SWITCH_SCHEDULED")) {
                report(command, lastStatus, "");
                return;
            }
        }
        if (!commit && committed != null && committed.sequence > appliedSequence)
            throw new ConfigLoadException("上一提交尚未生效，不能准备下一版本");
        if ("CANCEL".equals(command.commandType)) {
            if (committed != null && committed.sequence == command.sequence)
                throw new ConfigLoadException("提交后不可取消");
            ConfigService.getInstance().cancelPrepared(command.version);
            prepared = null;
            report(command, "CANCELLED", "");
            return;
        }
        try {
            if (commit) {
                // 必须先保存决定；下载失败或进程崩溃仍能在重启后恢复。
                DurableConfigFile.write(root().resolve("committed.json"), command);
                committed = command;
            }
            if (prepared == null || prepared.sequence != command.sequence) prepare(command);
            if (!commit) {
                report(command, "READY", "");
                return;
            }
            long delay = command.switchAtMillis - System.currentTimeMillis();
            if (delay < 0 && !recovery) {
                ConfigService.getInstance().suspend("配置提交迟到，恢复完成前暂停业务");
            }
            ConfigService.getInstance()
                    .schedulePreparedSwitch(
                            command.version,
                            command.switchAtMillis,
                            () -> {
                                synchronized (ConfigHotUpdateRuntime.class) {
                                    appliedSequence = command.sequence;
                                    prepared = null;
                                    ConfigService.getInstance().resume();
                                    lastStatus = "ACTIVE";
                                    report(command, "ACTIVE", "");
                                }
                            });
            lastStatus = "SWITCH_SCHEDULED";
            report(command, lastStatus, "");
        } catch (Exception error) {
            if (commit) ConfigService.getInstance().suspend("配置提交恢复失败: " + command.version);
            lastStatus = "FAILED";
            report(command, "FAILED", error.getMessage());
            throw error;
        }
    }

    private static void prepare(ConfigHotUpdateCommand command) throws Exception {
        Path directory = versionDirectory(command);
        Map<String, String> files =
                Files.isDirectory(directory) ? ConfigBundle.read(directory) : Map.of();
        if (!ConfigBundle.digest(files).equals(command.digest))
            files = download(command.downloadUrl);
        if (!ConfigBundle.digest(files).equals(command.digest))
            throw new ConfigLoadException("完整配置摘要不匹配");
        if (!files.keySet().equals(ConfigService.getInstance().getExpectedConfigFileNames()))
            throw new ConfigLoadException("完整配置包表集合与当前程序不一致");
        Files.createDirectories(directory);
        for (var file : files.entrySet()) {
            ConfigBundle.validateName(file.getKey());
            Files.writeString(
                    directory.resolve(file.getKey()), file.getValue(), StandardCharsets.UTF_8);
        }
        ConfigService.getInstance()
                .loadAllConfigToStandby(
                        LoggerDef.SystemLogger, directory.toString(), command.version);
        prepared = command;
    }

    /** 在监听端口和注册节点之前调用；已提交配置加载失败则启动失败。 */
    public static void recoverBeforeServing(String authoritativeContent) throws Exception {
        Path journal = root().resolve("committed.json");
        ConfigHotUpdateCommand command =
                Files.exists(journal)
                        ? DurableConfigFile.read(journal, ConfigHotUpdateCommand.class)
                        : null;
        if (authoritativeContent != null && !authoritativeContent.isBlank()) {
            ConfigHotUpdateCommand remote =
                    JSON.readValue(authoritativeContent, ConfigHotUpdateCommand.class);
            if (remote.targets.containsKey(ServerContext.getServerId())
                    && (command == null || remote.sequence >= command.sequence)) command = remote;
        }
        if (command != null && command.targets.containsKey(ServerContext.getServerId())) {
            apply(command, true);
            // 不在持有状态锁时等待回调。启动直接完成整套替换，等待未来生效点。
            ConfigService.getInstance()
                    .awaitVersion(
                            command.version,
                            Math.max(
                                    30_000,
                                    command.switchAtMillis - System.currentTimeMillis() + 30_000));
        }
    }

    public static synchronized void startRecovery(
            java.util.function.Supplier<String> committedContent,
            java.util.function.Supplier<String> pendingContent) {
        if (recoveryStarted) return;
        recoveryStarted = true;
        RECOVERY.scheduleWithFixedDelay(
                () -> {
                    try {
                        String active = committedContent.get();
                        if (active != null && !active.isBlank()) handle(active);
                        ConfigHotUpdateCommand local;
                        synchronized (ConfigHotUpdateRuntime.class) {
                            local = committed;
                        }
                        if (local != null) {
                            synchronized (ConfigHotUpdateRuntime.class) {
                                apply(local, true);
                            }
                        }
                        String pending = pendingContent.get();
                        if (pending != null && !pending.isBlank()) handle(pending);
                    } catch (Exception error) {
                        LoggerDef.SystemLogger.error("配置发布恢复查询失败", error);
                    }
                },
                5,
                5,
                TimeUnit.SECONDS);
    }

    private static Map<String, String> download(String url) throws Exception {
        HttpResponse<String> response =
                HTTP.send(
                        HttpRequest.newBuilder(URI.create(url))
                                .timeout(Duration.ofSeconds(30))
                                .GET()
                                .build(),
                        HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
        if (response.statusCode() != 200)
            throw new ConfigLoadException("下载配置失败: " + response.statusCode());
        JsonNode data = JSON.readTree(response.body()).path("data");
        if (!data.isArray()) throw new ConfigLoadException("配置下载格式错误");
        Map<String, String> files = new TreeMap<>();
        for (JsonNode file : data) {
            String name = file.path("fileName").asText();
            ConfigBundle.validateName(name);
            if (files.put(name, file.path("content").asText()) != null)
                throw new ConfigLoadException("重复配置文件: " + name);
        }
        return files;
    }

    private static void report(ConfigHotUpdateCommand command, String status, String error) {
        if (command.reportUrl == null) return;
        try {
            String body =
                    "publishId="
                            + enc(command.publishId)
                            + "&version="
                            + enc(command.version)
                            + "&serverId="
                            + enc(ServerContext.getServerId())
                            + "&serverType="
                            + enc(ServerContext.serverType.getType())
                            + "&status="
                            + status
                            + "&errorMsg="
                            + enc(error == null ? "" : error)
                            + "&bootId="
                            + enc(BOOT_ID)
                            + "&digest="
                            + enc(command.digest)
                            + "&nodeTimeMillis="
                            + System.currentTimeMillis();
            HttpResponse<Void> response =
                    HTTP.send(
                            HttpRequest.newBuilder(URI.create(command.reportUrl))
                                    .timeout(Duration.ofSeconds(5))
                                    .header("Content-Type", "application/x-www-form-urlencoded")
                                    .POST(HttpRequest.BodyPublishers.ofString(body))
                                    .build(),
                            HttpResponse.BodyHandlers.discarding());
            if (response.statusCode() != 200)
                throw new IllegalStateException("HTTP " + response.statusCode());
        } catch (Exception failure) {
            LoggerDef.SystemLogger.warn("热更状态上报失败，恢复扫描会重报: {}", status, failure);
        }
    }

    private static String enc(String text) {
        return URLEncoder.encode(text, StandardCharsets.UTF_8);
    }
}
