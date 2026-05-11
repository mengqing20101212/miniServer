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
 * File: CreditConfigManager
 */
public class CreditConfigManager implements InterfaceConfigManagerProxy {
  private static final AtomicBoolean switched = new AtomicBoolean(false);
  private static final CreditConfigManager instance = new CreditConfigManager();
  private static final CreditConfigManagerImpl instanceImplA = new CreditConfigManagerImpl();
  private static final CreditConfigManagerImpl instanceImplB = new CreditConfigManagerImpl();

  public static CreditConfigManagerImpl getInstance() {
    return switched.get() ? instanceImplA : instanceImplB;
  }

  private static CreditConfigManagerImpl getStandby() {
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
    CreditConfigManagerImpl oldActive = getInstance();
    switched.set(!switched.get());
    return oldActive;
  }

  @Override
  public String getConfigFileName() {
    return getInstance().getConfigFileName();
  }

  public static class CreditConfigManagerImpl extends AbstractConfigManger {
    private List<CreditConfig> configList = List.of();
    private Map<Integer, CreditConfig> configMap = Map.of();

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
      CreditConfigChecker checker = new CreditConfigChecker();
      checker.checkHeader(logger, configDir);
      List<CreditConfig> newList = new ArrayList<>();
      Map<Integer, CreditConfig> newMap = new HashMap<>();
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
          int activityList = 0;
          String sceneId = null;
          int grouoId = 0;
          int ScoreMin = 0;
          int ScoreMax = 0;
          String lable = null;
          int lableMin = 0;
          int lableMax = 0;
          String dec = null;
          try {
            // 解析 编号
            if (!arr[0].trim().isEmpty()) {
              id = Integer.parseInt(arr[0].trim());
            }

            // 解析 功能ID
            if (!arr[1].trim().isEmpty()) {
              activityList = Integer.parseInt(arr[1].trim());
            }

            // 解析 场景ID
            if (!arr[2].trim().isEmpty()) {
              sceneId = arr[2].trim();
            }

            // 解析 分组ID
            if (!arr[3].trim().isEmpty()) {
              grouoId = Integer.parseInt(arr[3].trim());
            }

            // 解析 门槛下限
            if (!arr[4].trim().isEmpty()) {
              ScoreMin = Integer.parseInt(arr[4].trim());
            }

            // 解析 门槛上限
            if (!arr[5].trim().isEmpty()) {
              ScoreMax = Integer.parseInt(arr[5].trim());
            }

            // 解析 标签
            if (!arr[6].trim().isEmpty()) {
              lable = arr[6].trim();
            }

            // 解析 标签下限
            if (!arr[7].trim().isEmpty()) {
              lableMin = Integer.parseInt(arr[7].trim());
            }

            // 解析 标签上限
            if (!arr[8].trim().isEmpty()) {
              lableMax = Integer.parseInt(arr[8].trim());
            }

            // 解析 提示
            if (!arr[9].trim().isEmpty()) {
              dec = arr[9].trim();
            }

          } catch (Exception e) {
            logger.error(String.format("解析配置 %s 表, 字符串:%s 报错，请检查:%s", fileName, rowText, e.getMessage()));
            throw new ConfigLoadException("Error parsing config file :" + fileName);
          }
          CreditConfig config = new CreditConfig(id, activityList, sceneId, grouoId, ScoreMin, ScoreMax, lable, lableMin, lableMax, dec);
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

    public List<CreditConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, CreditConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "credit.txt";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
