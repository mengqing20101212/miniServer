package ly;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import org.slf4j.Logger;

/**
 * 配置服务入口，统一加载所有配置管理器并支持配置热更新。
 */
public class ConfigService {

  private ConfigService() {}

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

    // 获取当前类的路径
    String className = ConfigService.class.getName();
    String classFilePath = className.replace('.', '/') + ".class"; // 获取当前类的文件路径
    // 获取当前类的 ClassLoader
    URL classUrl = ConfigService.class.getClassLoader().getResource(classFilePath);

    if (classUrl != null) {
      // 当前类文件所在的路径
      String currentClassPath =
          classUrl.getPath().substring(0, classUrl.getPath().lastIndexOf("/"));

      // 扫描该目录中的所有文件
      File dirFiles = new File(currentClassPath + File.separator + "config");
      File[] files = dirFiles.listFiles();
      if (files != null && files.length > 0) {
        Arrays.stream(files)
            .filter(File::isFile)
            .forEach(
                file -> {
                  try {
                    Class<?> c = Class.forName("ly.config." + file.getName().replace(".class", ""));
                    // 判断加载的类是否是 InterfaceConfigManager 的子类
                    if (!c.getSimpleName().equals(InterfaceConfigManagerProxy.class.getSimpleName())
                        && InterfaceConfigManagerProxy.class.isAssignableFrom(c)) {
                      // 如果是 InterfaceConfigManager 类型的类
                      configManagerList.add((InterfaceConfigManagerProxy) c.newInstance());
                    }

                  } catch (Exception e) {
                    e.printStackTrace();
                    System.out.printf("[ERROR] Class not found: %s\n", file.getName());
                    throw new RuntimeException(e);
                  }
                });
      }
    } else {
      System.out.println("Class file not found!");
    }
  }

  public void loadAllConfig(Logger logger, String configDir) {
    long startTime = System.currentTimeMillis();
    logger.info("开始加载配置表");
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
              String.format(
                  "加载策划表 %s  耗时 %d (毫秒)",
                  configManager.getClass().getSimpleName(),
                  loadConfigEndTime - loadConfigBeginTime));
        });
    logger.info(String.format("加载配置表完成, 耗时:%d (毫秒)", System.currentTimeMillis() - startTime));
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
    logger.info("开始加载备用配置表, version={}, configDir={}", version, configDir);
    for (InterfaceConfigManagerProxy configManager : configManagerList) {
      long loadConfigBeginTime = System.currentTimeMillis();
      configManager.loadStandbyConfig(logger, configDir);
      long loadConfigEndTime = System.currentTimeMillis();
      logger.info(
          "加载备用策划表 {} 耗时 {} (毫秒)",
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

  public void schedulePreparedSwitch(Logger logger, String version, long switchAtMillis, long releaseDelayMillis)
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
