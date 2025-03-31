package ly;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.logging.log4j.core.Logger;

public class ConfigService {

  private ConfigService() {}

  private static ConfigService configManger = null;
  List<InterfaceConfigManagerProxy> configManagerList = new ArrayList<>(256);

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

      Arrays.stream(dirFiles.listFiles())
          .filter(File::isFile)
          .forEach(
              file -> {
                try {
                  Class c = Class.forName("ly.config." + file.getName().replace(".class", ""));
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
    } else {
      System.out.println("Class file not found!");
    }
  }

  public void loadAllConfig(Logger logger, String configDir) {
    long startTime = System.currentTimeMillis();
    logger.info("开始加载配置表");
    configManagerList.parallelStream()
        .forEach(
            configManager -> {
              long loadConfigBeginTime = System.currentTimeMillis();
              configManager.loadConfig(logger, configDir);
              long loadConfigEndTime = System.currentTimeMillis();
              logger.info(
                  String.format(
                      "加载策划表 %s  耗时过长 %d (毫秒)",
                      configManager.getClass().getSimpleName(),
                      loadConfigEndTime - loadConfigBeginTime));
            });
    logger.info(String.format("加载配置表完成, 耗时:%d (毫秒)", System.currentTimeMillis() - startTime));
  }
}
