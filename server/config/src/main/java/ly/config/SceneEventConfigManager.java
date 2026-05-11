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
 * File: SceneEventConfigManager
 */
public class SceneEventConfigManager implements InterfaceConfigManagerProxy {
  private static final AtomicBoolean switched = new AtomicBoolean(false);
  private static final SceneEventConfigManager instance = new SceneEventConfigManager();
  private static final SceneEventConfigManagerImpl instanceImplA = new SceneEventConfigManagerImpl();
  private static final SceneEventConfigManagerImpl instanceImplB = new SceneEventConfigManagerImpl();

  public static SceneEventConfigManagerImpl getInstance() {
    return switched.get() ? instanceImplA : instanceImplB;
  }

  private static SceneEventConfigManagerImpl getStandby() {
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
    SceneEventConfigManagerImpl oldActive = getInstance();
    switched.set(!switched.get());
    return oldActive;
  }

  @Override
  public String getConfigFileName() {
    return getInstance().getConfigFileName();
  }

  public static class SceneEventConfigManagerImpl extends AbstractConfigManger {
    private List<SceneEventConfig> configList = List.of();
    private Map<Integer, SceneEventConfig> configMap = Map.of();

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
      SceneEventConfigChecker checker = new SceneEventConfigChecker();
      checker.checkHeader(logger, configDir);
      List<SceneEventConfig> newList = new ArrayList<>();
      Map<Integer, SceneEventConfig> newMap = new HashMap<>();
      try (BufferedReader br = new BufferedReader(new FileReader(file))) {
        String rowText;
        br.readLine();
        br.readLine();
        while ((rowText = br.readLine()) != null) {
          if (rowText.isBlank()) { continue; }
          String[] arr = rowText.split("\\t", -1);
          if (arr.length < 10) {
            throw new ConfigLoadException("Config column size mismatch :" + fileName + ", line=" + rowText);
          }
          int id = 0;
          String description = null;
          String triggerTypeList = null;
          String treeName = null;
          int uitype = 0;
          String param_1 = null;
          String param_2 = null;
          String param_3 = null;
          String param_4 = null;
          int loopNum = 0;
          try {
            // 解析 事件编号
            if (!arr[0].trim().isEmpty()) {
              id = Integer.parseInt(arr[0].trim());
            }

            // 解析 事件描述
            if (!arr[1].trim().isEmpty()) {
              description = arr[1].trim();
            }

            // 解析 触发类型列表
            if (!arr[2].trim().isEmpty()) {
              triggerTypeList = arr[2].trim();
            }

            // 解析 使用行为树名称
            if (!arr[3].trim().isEmpty()) {
              treeName = arr[3].trim();
            }

            // 解析 事件UI类型
            if (!arr[4].trim().isEmpty()) {
              uitype = Integer.parseInt(arr[4].trim());
            }

            // 解析 参数1
            if (!arr[5].trim().isEmpty()) {
              param_1 = arr[5].trim();
            }

            // 解析 参数2
            if (!arr[6].trim().isEmpty()) {
              param_2 = arr[6].trim();
            }

            // 解析 参数3
            if (!arr[7].trim().isEmpty()) {
              param_3 = arr[7].trim();
            }

            // 解析 参数4
            if (!arr[8].trim().isEmpty()) {
              param_4 = arr[8].trim();
            }

            // 解析 循环次数
            if (!arr[9].trim().isEmpty()) {
              loopNum = Integer.parseInt(arr[9].trim());
            }

          } catch (Exception e) {
            logger.error(String.format("解析配置 %s 表, 字符串:%s 报错，请检查:%s", fileName, rowText, e.getMessage()));
            throw new ConfigLoadException("Error parsing config file :" + fileName);
          }
          SceneEventConfig config = new SceneEventConfig(id, description, triggerTypeList, treeName, uitype, param_1, param_2, param_3, param_4, loopNum);
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

    public List<SceneEventConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, SceneEventConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "sceneEvent.txt";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
