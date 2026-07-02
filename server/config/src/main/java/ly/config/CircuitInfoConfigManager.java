package ly.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import ly.AbstractConfigManger;
import ly.ConfigLoadException;
import ly.InterfaceConfigManagerProxy;
import ly.utils.KV;
import org.slf4j.Logger;

/*
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 * File: CircuitInfoConfigManager
 */
public class CircuitInfoConfigManager implements InterfaceConfigManagerProxy {
  private static final AtomicBoolean switched = new AtomicBoolean(false);
  private static final CircuitInfoConfigManager instance = new CircuitInfoConfigManager();
  private static final CircuitInfoConfigManagerImpl instanceImplA = new CircuitInfoConfigManagerImpl();
  private static final CircuitInfoConfigManagerImpl instanceImplB = new CircuitInfoConfigManagerImpl();

  public static CircuitInfoConfigManagerImpl getInstance() {
    return switched.get() ? instanceImplA : instanceImplB;
  }

  private static CircuitInfoConfigManagerImpl getStandby() {
    return switched.get() ? instanceImplB : instanceImplA;
  }

  @Override
  public void loadConfig(Logger logger, String configDir) throws ConfigLoadException {
    getInstance().reload(logger, configDir);
  }

  @Override
  public void loadStandbyConfig(Logger logger, String configDir) throws ConfigLoadException {
    getStandby().reload(logger, configDir);
  }

  @Override
  public AbstractConfigManger switchConfig() {
    CircuitInfoConfigManagerImpl oldActive = getInstance();
    switched.set(!switched.get());
    return oldActive;
  }

  @Override
  public String getConfigFileName() {
    return getInstance().getConfigFileName();
  }

  public static class CircuitInfoConfigManagerImpl extends AbstractConfigManger {
    private List<CircuitInfoConfig> configList = List.of();
    private Map<Integer, CircuitInfoConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      String fileName = configDir + File.separator + getConfigFileName();
      File file = new File(fileName);
      if (!file.exists()) {
        logger.error(fileName + " does not exist");
        throw new ConfigLoadException("Config file does not exist :" + fileName);
      }
      CircuitInfoConfigChecker checker = new CircuitInfoConfigChecker();
      checker.checkHeader(logger, configDir);
      List<CircuitInfoConfig> newList = new ArrayList<>();
      Map<Integer, CircuitInfoConfig> newMap = new HashMap<>();
      try (BufferedReader br = new BufferedReader(new FileReader(file))) {
        String rowText;
        br.readLine();
        br.readLine();
        while ((rowText = br.readLine()) != null) {
          if (rowText.isBlank()) { continue; }
          String[] arr = rowText.split("\\t", -1);
          if (arr.length < 8) {
            throw new ConfigLoadException("Config column size mismatch :" + fileName + ", line=" + rowText);
          }
          int id = 0;
          int type = 0;
          String description = null;
          int mainForward = 0;
          String route0 = null;
          String route1 = null;
          String route2 = null;
          String route3 = null;
          try {
            // 解析 编号
            if (!arr[0].trim().isEmpty()) {
              id = Integer.parseInt(arr[0].trim());
            }

            // 解析 回路显示种类
            if (!arr[1].trim().isEmpty()) {
              type = Integer.parseInt(arr[1].trim());
            }

            // 解析 回路描述
            if (!arr[2].trim().isEmpty()) {
              description = arr[2].trim();
            }

            // 解析 回路主方向
            if (!arr[3].trim().isEmpty()) {
              mainForward = Integer.parseInt(arr[3].trim());
            }

            // 解析 路线0
            if (!arr[4].trim().isEmpty()) {
              route0 = arr[4].trim();
            }

            // 解析 路线1
            if (!arr[5].trim().isEmpty()) {
              route1 = arr[5].trim();
            }

            // 解析 路线2
            if (!arr[6].trim().isEmpty()) {
              route2 = arr[6].trim();
            }

            // 解析 路线3
            if (!arr[7].trim().isEmpty()) {
              route3 = arr[7].trim();
            }

          } catch (Exception e) {
            logger.error(String.format("解析配置 %s 表, 字符串:%s 报错，请检查:%s", fileName, rowText, e.getMessage()));
            throw new ConfigLoadException("Error parsing config file :" + fileName);
          }
          CircuitInfoConfig config = new CircuitInfoConfig(id, type, description, mainForward, route0, route1, route2, route3);
          config.afterLoad();
          newList.add(config);
          newMap.put(config.id, config);
        }
        checker.checkAfterParse(logger, newList);
        configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
        afterLoad();
      } catch (IOException e) {
        throw new ConfigLoadException("Config file could not be read :" + fileName);
      }
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    private List<Integer> parseIntList(String value) {
      if (value == null || value.trim().isEmpty()) { return new ArrayList<>(); }
      String[] parts = value.split(",");
      List<Integer> result = new ArrayList<>();
      for (String part : parts) {
        if (!part.trim().isEmpty()) { result.add(Integer.parseInt(part.trim())); }
      }
      return result;
    }

    private List<KV<Integer, Integer>> parseIntKVList(String value) {
      if (value == null || value.trim().isEmpty()) { return new ArrayList<>(); }
      List<KV<Integer, Integer>> result = new ArrayList<>();
      for (String pair : value.split(",")) {
        int idx = pair.indexOf(":");
        if (idx > 0) {
          result.add(new KV<>(Integer.parseInt(pair.substring(0, idx).trim()), Integer.parseInt(pair.substring(idx + 1).trim())));
        }
      }
      return result;
    }

    private List<KV<String, String>> parseStringKVList(String value) {
      if (value == null || value.trim().isEmpty()) { return new ArrayList<>(); }
      List<KV<String, String>> result = new ArrayList<>();
      for (String pair : value.split(",")) {
        int idx = pair.indexOf(":");
        if (idx > 0) { result.add(new KV<>(pair.substring(0, idx).trim(), pair.substring(idx + 1).trim())); }
      }
      return result;
    }

    public List<CircuitInfoConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, CircuitInfoConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "circuitInfo.txt";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
