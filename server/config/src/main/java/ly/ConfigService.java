package ly;

import org.slf4j.Logger;

import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

/** 配置服务入口，统一加载所有配置表管理器，并支持配置热更新。 */
public class ConfigService {

    private ConfigService() {}

    private static final String CONFIG_PACKAGE = "ly.config";
    private static final String CONFIG_PACKAGE_PATH = "ly/config";
    private static ConfigService configManger = null;

    List<InterfaceConfigManagerProxy> configManagerList = new ArrayList<>(256);

    private final ScheduledExecutorService hotUpdateExecutor =
            Executors.newSingleThreadScheduledExecutor(
                    r -> {
                        Thread t = new Thread(r, "config-hot-update");
                        t.setDaemon(true);
                        return t;
                    });

    private volatile ConfigSnapshot active = new ConfigSnapshot("startup", java.util.Map.of());
    private ConfigSnapshot prepared;
    private java.util.concurrent.ScheduledFuture<?> scheduledSwitch;
    private final java.util.concurrent.ConcurrentMap<Class<?>, AbstractConfigManger> emptyTables =
            new java.util.concurrent.ConcurrentHashMap<>();
    private static volatile ConfigService initialized;

    public static ConfigSnapshot captureSnapshot() {
        ConfigService service = initialized;
        if (service == null) return null;
        service.requireAvailable();
        return service.snapshot();
    }

    private static final class Holder {
        private static final ConfigService INSTANCE = create();
    }

    private static ConfigService create() {
        ConfigService service = new ConfigService();
        service.init();
        initialized = service;
        return service;
    }

    public static ConfigService getInstance() {
        return Holder.INSTANCE;
    }

    private volatile String unavailableReason;

    public void suspend(String reason) {
        unavailableReason = reason;
    }

    public void resume() {
        unavailableReason = null;
    }

    public void requireAvailable() {
        if (unavailableReason != null) throw new ConfigLoadException(unavailableReason);
    }

    public ConfigSnapshot snapshot() {
        return active;
    }

    public void awaitVersion(String version, long timeoutMillis) throws InterruptedException {
        long deadline = System.nanoTime() + TimeUnit.MILLISECONDS.toNanos(timeoutMillis);
        while (!active.version().equals(version)
                || (scheduledSwitch != null && !scheduledSwitch.isDone())) {
            if (System.nanoTime() >= deadline)
                throw new ConfigLoadException("等待配置生效超时: " + version);
            Thread.sleep(10);
        }
    }

    public <T extends AbstractConfigManger> T table(Class<T> type) {
        ConfigSnapshot scoped = ConfigScope.current();
        ConfigSnapshot snapshot = scoped == null ? active : scoped;
        if (snapshot.tables().containsKey(type)) return snapshot.table(type);
        // 仅允许尚未加载配置时使用空表，保留独立模块测试的构造行为。
        if (!snapshot.tables().isEmpty()) return snapshot.table(type);
        return type.cast(
                emptyTables.computeIfAbsent(
                        type,
                        key -> {
                            try {
                                return type.getDeclaredConstructor().newInstance();
                            } catch (Exception error) {
                                throw new ConfigLoadException("无法创建配置表: " + type.getName());
                            }
                        }));
    }

    private void init() {
        configManagerList.clear();
        try {
            for (String className : scanConfigManagerClassNames()) {
                Class<?> clazz = Class.forName(className);
                if (InterfaceConfigManagerProxy.class.isAssignableFrom(clazz)) {
                    configManagerList.add(
                            (InterfaceConfigManagerProxy)
                                    clazz.getDeclaredConstructor().newInstance());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("初始化配置表管理器失败", e);
        }

        if (configManagerList.isEmpty()) {
            throw new IllegalStateException(
                    "没有扫描到任何配置表管理器，请检查 ly/config 下的 ConfigManager 是否进入 classpath 或 jar");
        }
    }

    private Set<String> scanConfigManagerClassNames() throws IOException {
        Set<String> classNames = new TreeSet<>();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader == null) {
            classLoader = ConfigService.class.getClassLoader();
        }

        // 兼容两种运行方式：IDE/单独编译时是 file: 目录，打包发布后通常是 jar: 资源。
        Enumeration<URL> resources = classLoader.getResources(CONFIG_PACKAGE_PATH);
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            if ("file".equals(resource.getProtocol())) {
                scanClassDirectory(resource, classNames);
            } else {
                scanJarResource(resource, classNames);
            }
        }
        return classNames;
    }

    private void scanClassDirectory(URL resource, Set<String> classNames) {
        try {
            Path dir = Path.of(resource.toURI());
            if (!Files.isDirectory(dir)) {
                return;
            }
            try (var files = Files.list(dir)) {
                files.filter(Files::isRegularFile)
                        .map(path -> path.getFileName().toString())
                        .filter(this::isConfigManagerClassFile)
                        .map(this::toConfigClassName)
                        .forEach(classNames::add);
            }
        } catch (Exception e) {
            throw new RuntimeException("扫描配置表 class 目录失败:" + resource, e);
        }
    }

    private void scanJarResource(URL resource, Set<String> classNames) {
        Set<String> before = new LinkedHashSet<>(classNames);
        try {
            URLConnection connection = resource.openConnection();
            if (connection instanceof JarURLConnection jarConnection) {
                scanJarFile(jarConnection.getJarFile(), classNames, false);
                return;
            }
        } catch (Exception ignored) {
            // 部分 nested jar URL 不能直接转成 JarURLConnection，下面继续按 URL 文本兜底解析。
        }

        try {
            String external = resource.toExternalForm();
            int bangIndex = external.indexOf("!/");
            if (external.startsWith("jar:") && bangIndex > 0) {
                String jarPath = external.substring("jar:".length(), bangIndex);
                if (jarPath.startsWith("file:")) {
                    scanJarFile(
                            new JarFile(Path.of(URI.create(jarPath)).toFile()), classNames, true);
                }
            }
        } catch (Exception e) {
            if (classNames.equals(before)) {
                throw new RuntimeException("扫描配置表 jar 资源失败:" + resource, e);
            }
        }
    }

    private void scanJarFile(JarFile jarFile, Set<String> classNames, boolean closeAfterScan) {
        try {
            Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                String name = entries.nextElement().getName();
                if (name.startsWith(CONFIG_PACKAGE_PATH + "/")) {
                    String fileName = name.substring(CONFIG_PACKAGE_PATH.length() + 1);
                    if (isConfigManagerClassFile(fileName)) {
                        classNames.add(toConfigClassName(fileName));
                    }
                }
            }
        } finally {
            if (closeAfterScan) {
                try {
                    jarFile.close();
                } catch (IOException ignored) {
                    // 扫描已经完成，关闭临时 JarFile 失败不影响配置管理器注册结果。
                }
            }
        }
    }

    private boolean isConfigManagerClassFile(String fileName) {
        return fileName.endsWith("ConfigManager.class")
                && !fileName.contains("/")
                && !fileName.contains("\\")
                && !fileName.contains("$");
    }

    private String toConfigClassName(String classFileName) {
        return CONFIG_PACKAGE
                + "."
                + classFileName.substring(0, classFileName.length() - ".class".length());
    }

    public synchronized void loadAllConfig(Logger logger, String configDir) {
        active = loadSnapshot(logger, configDir, "startup");
        emptyTables.clear();
    }

    public Set<String> getExpectedConfigFileNames() {
        return configManagerList.stream()
                .map(InterfaceConfigManagerProxy::getConfigFileName)
                .collect(Collectors.toSet());
    }

    public void validateSingleConfigFile(Logger logger, String configDir, String fileName) {
        configManagerList.stream()
                .filter(factory -> factory.getConfigFileName().equals(fileName))
                .findFirst()
                .orElseThrow(() -> new ConfigLoadException("未知配置表: " + fileName))
                .createTable()
                .reload(logger, configDir);
    }

    public ConfigSnapshot loadSnapshot(Logger logger, String configDir, String version) {
        java.util.Map<Class<?>, AbstractConfigManger> tables = new java.util.LinkedHashMap<>();
        for (InterfaceConfigManagerProxy factory : configManagerList) {
            AbstractConfigManger table = factory.createTable();
            tables.put(table.getClass(), table);
        }
        ConfigSnapshot snapshot = new ConfigSnapshot(version, tables);
        // afterLoad 的跨表读取也固定在待加载快照，不能引用当前线上表。
        // 先解析全部表，再执行跨表 afterLoad，避免依赖并行加载顺序。
        try (ConfigScope ignored = ConfigScope.use(snapshot)) {
            for (AbstractConfigManger table : tables.values()) table.reload(logger, configDir);
            for (AbstractConfigManger table : tables.values()) table.afterLoad();
        }
        return snapshot;
    }

    public synchronized void loadAllConfigToStandby(
            Logger logger, String configDir, String version) {
        if (scheduledSwitch != null && !scheduledSwitch.isDone())
            throw new ConfigLoadException("已有已提交切换等待执行");
        prepared = loadSnapshot(logger, configDir, version);
    }

    public synchronized void cancelPrepared(String version) {
        if (scheduledSwitch != null && !scheduledSwitch.isDone())
            throw new ConfigLoadException("提交后不可取消");
        if (prepared != null && prepared.version().equals(version)) prepared = null;
    }

    public void scheduleSwitch(
            Logger logger,
            String configDir,
            String version,
            long switchAtMillis,
            long ignoredReleaseDelay) {
        loadAllConfigToStandby(logger, configDir, version);
        schedulePreparedSwitch(logger, version, switchAtMillis, ignoredReleaseDelay);
    }

    public synchronized void schedulePreparedSwitch(
            Logger logger, String version, long switchAtMillis, long ignoredReleaseDelay) {
        schedulePreparedSwitch(version, switchAtMillis, () -> logger.info("配置已生效: {}", version));
    }

    public synchronized void schedulePreparedSwitch(
            String version, long switchAtMillis, Runnable activated) {
        if (active.version().equals(version)) {
            activated.run();
            return;
        }
        if (prepared == null || !prepared.version().equals(version))
            throw new ConfigLoadException("没有该版本的 READY 快照: " + version);
        if (scheduledSwitch != null && !scheduledSwitch.isDone()) return;
        ConfigSnapshot next = prepared;
        scheduledSwitch =
                hotUpdateExecutor.schedule(
                        () -> {
                            synchronized (this) {
                                active = next;
                                prepared = null;
                                emptyTables.clear();
                            }
                            activated.run();
                        },
                        Math.max(0, switchAtMillis - System.currentTimeMillis()),
                        TimeUnit.MILLISECONDS);
    }

    public String getActiveVersion() {
        return active.version();
    }
}
