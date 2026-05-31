package ly;

import java.io.File;
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
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;
import org.slf4j.Logger;

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

  private final AtomicBoolean hotUpdateRunning = new AtomicBoolean(false);
  private volatile String activeVersion = "startup";
  private volatile String standbyVersion;
  private volatile boolean standbyLoaded;
  private volatile long switchAtMillis;

  public static ConfigService getInstance() {
    if (configManger == null) {
      configManger = new ConfigService();
      configManger.init();
    }
    return configManger;
  }

  private void init() {
    configManagerList.clear();
    try {
      for (String className : scanConfigManagerClassNames()) {
        Class<?> clazz = Class.forName(className);
        if (InterfaceConfigManagerProxy.class.isAssignableFrom(clazz)) {
          configManagerList.add(
              (InterfaceConfigManagerProxy) clazz.getDeclaredConstructor().newInstance());
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
        files
            .filter(Files::isRegularFile)
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
          scanJarFile(new JarFile(Path.of(URI.create(jarPath)).toFile()), classNames, true);
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
    return CONFIG_PACKAGE + "." + classFileName.substring(0, classFileName.length() - ".class".length());
  }

  public void loadAllConfig(Logger logger, String configDir) {
    long startTime = System.currentTimeMillis();
    logger.info("开始加载配置表, managerCount={}", configManagerList.size());
    configManagerList.forEach(
        configManager -> {
          long loadConfigBeginTime = System.currentTimeMillis();
          try {
            configManager.loadConfig(logger, configDir);
          } catch (Exception e) {
            logger.error("加载配置表失败，manager={}", configManager.getClass().getSimpleName(), e);
            throw new RuntimeException("加载配置表失败:" + configManager.getClass().getSimpleName(), e);
          }
          long loadConfigEndTime = System.currentTimeMillis();
          logger.info(
              "加载策划表 {} 耗时 {}ms",
              configManager.getClass().getSimpleName(),
              loadConfigEndTime - loadConfigBeginTime);
        });
    logger.info("加载配置表完成, cost={}ms", System.currentTimeMillis() - startTime);
  }

  public Set<String> getExpectedConfigFileNames() {
    return configManagerList.stream()
        .map(InterfaceConfigManagerProxy::getConfigFileName)
        .collect(Collectors.toSet());
  }

  public void validateSingleConfigFile(Logger logger, String configDir, String fileName)
      throws ConfigLoadException {
    InterfaceConfigManagerProxy manager = findManager(fileName);
    manager.loadStandbyConfig(logger, configDir);
  }

  public void loadAllConfigToStandby(Logger logger, String configDir, String version)
      throws ConfigLoadException {
    long startTime = System.currentTimeMillis();
    logger.info(
        "开始加载备用配置表, version={}, configDir={}, managerCount={}",
        version,
        configDir,
        configManagerList.size());
    for (InterfaceConfigManagerProxy configManager : configManagerList) {
      long loadConfigBeginTime = System.currentTimeMillis();
      configManager.loadStandbyConfig(logger, configDir);
      long loadConfigEndTime = System.currentTimeMillis();
      logger.info(
          "加载备用策划表 {} 耗时 {}ms",
          configManager.getClass().getSimpleName(),
          loadConfigEndTime - loadConfigBeginTime);
    }
    standbyVersion = version;
    standbyLoaded = true;
    logger.info("加载备用配置表完成, version={}, cost={}ms", version, System.currentTimeMillis() - startTime);
  }

  public void scheduleSwitch(
      Logger logger, String configDir, String version, long switchAtMillis, long releaseDelayMillis)
      throws ConfigLoadException {
    if (!hotUpdateRunning.compareAndSet(false, true)) {
      throw new ConfigLoadException("当前服务器已有配置热更任务正在执行");
    }
    try {
      standbyLoaded = false;
      standbyVersion = null;
      this.switchAtMillis = switchAtMillis;
      loadAllConfigToStandby(logger, configDir, version);
      long delay = Math.max(0L, switchAtMillis - System.currentTimeMillis());
      hotUpdateExecutor.schedule(
          () -> doSwitch(logger, version, releaseDelayMillis), delay, TimeUnit.MILLISECONDS);
      logger.info("配置热更已准备完成, version={}, switchAtMillis={}", version, switchAtMillis);
    } catch (ConfigLoadException e) {
      hotUpdateRunning.set(false);
      throw e;
    } catch (Exception e) {
      hotUpdateRunning.set(false);
      throw new ConfigLoadException("配置热更准备失败:" + e.getMessage());
    }
  }

  public void schedulePreparedSwitch(
      Logger logger, String version, long switchAtMillis, long releaseDelayMillis)
      throws ConfigLoadException {
    if (!standbyLoaded || !version.equals(standbyVersion)) {
      throw new ConfigLoadException("备用配置未加载成功，不能切换:" + version);
    }
    long delay = Math.max(0L, switchAtMillis - System.currentTimeMillis());
    hotUpdateExecutor.schedule(
        () -> doSwitch(logger, version, releaseDelayMillis), delay, TimeUnit.MILLISECONDS);
    logger.info("配置热更切换任务已提交, version={}, switchAtMillis={}", version, switchAtMillis);
  }

  private void doSwitch(Logger logger, String version, long releaseDelayMillis) {
    List<AbstractConfigManger> oldConfigManagers = new ArrayList<>();
    try {
      if (!standbyLoaded || !version.equals(standbyVersion)) {
        logger.error("备用配置未加载成功，不能切换, version={}, standbyVersion={}", version, standbyVersion);
        return;
      }
      for (InterfaceConfigManagerProxy configManager : configManagerList) {
        oldConfigManagers.add(configManager.switchConfig());
      }
      activeVersion = version;
      standbyVersion = null;
      standbyLoaded = false;
      logger.info("配置热更切换成功, activeVersion={}", activeVersion);
      hotUpdateExecutor.schedule(
          () -> {
            for (AbstractConfigManger old : oldConfigManagers) {
              if (old != null) {
                old.release();
              }
            }
            logger.info("配置热更旧版本释放完成, version={}", version);
          },
          Math.max(0L, releaseDelayMillis),
          TimeUnit.MILLISECONDS);
    } finally {
      hotUpdateRunning.set(false);
    }
  }

  public String getActiveVersion() {
    return activeVersion;
  }

  private InterfaceConfigManagerProxy findManager(String fileName) throws ConfigLoadException {
    for (InterfaceConfigManagerProxy manager : configManagerList) {
      if (manager.getConfigFileName().equals(fileName)) {
        return manager;
      }
    }
    throw new ConfigLoadException("未知配置表文件:" + fileName);
  }
}
