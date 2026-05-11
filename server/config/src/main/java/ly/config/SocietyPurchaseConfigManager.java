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
 * File: SocietyPurchaseConfigManager
 */
public class SocietyPurchaseConfigManager implements InterfaceConfigManagerProxy {
  private static final AtomicBoolean switched = new AtomicBoolean(false);
  private static final SocietyPurchaseConfigManager instance = new SocietyPurchaseConfigManager();
  private static final SocietyPurchaseConfigManagerImpl instanceImplA = new SocietyPurchaseConfigManagerImpl();
  private static final SocietyPurchaseConfigManagerImpl instanceImplB = new SocietyPurchaseConfigManagerImpl();

  public static SocietyPurchaseConfigManagerImpl getInstance() {
    return switched.get() ? instanceImplA : instanceImplB;
  }

  private static SocietyPurchaseConfigManagerImpl getStandby() {
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
    SocietyPurchaseConfigManagerImpl oldActive = getInstance();
    switched.set(!switched.get());
    return oldActive;
  }

  @Override
  public String getConfigFileName() {
    return getInstance().getConfigFileName();
  }

  public static class SocietyPurchaseConfigManagerImpl extends AbstractConfigManger {
    private List<SocietyPurchaseConfig> configList = List.of();
    private Map<Integer, SocietyPurchaseConfig> configMap = Map.of();

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
      SocietyPurchaseConfigChecker checker = new SocietyPurchaseConfigChecker();
      checker.checkHeader(logger, configDir);
      List<SocietyPurchaseConfig> newList = new ArrayList<>();
      Map<Integer, SocietyPurchaseConfig> newMap = new HashMap<>();
      try (BufferedReader br = new BufferedReader(new FileReader(file))) {
        String rowText;
        br.readLine();
        br.readLine();
        while ((rowText = br.readLine()) != null) {
          if (rowText.isBlank()) { continue; }
          String[] arr = rowText.split("\\t", -1);
          if (arr.length < 12) {
            throw new ConfigLoadException("Config column size mismatch :" + fileName + ", line=" + rowText);
          }
          int id = 0;
          int phase = 0;
          int sequence = 0;
          int minLevel = 0;
          int maxLevel = 0;
          int isRare = 0;
          int demandProps = 0;
          String beizhu1 = null;
          int demandNum = 0;
          String eachReward = null;
          String additionalReward = null;
          int weights = 0;
          try {
            // 解析 编号
            if (!arr[0].trim().isEmpty()) {
              id = Integer.parseInt(arr[0].trim());
            }

            // 解析 等级段
            if (!arr[1].trim().isEmpty()) {
              phase = Integer.parseInt(arr[1].trim());
            }

            // 解析 次序
            if (!arr[2].trim().isEmpty()) {
              sequence = Integer.parseInt(arr[2].trim());
            }

            // 解析 最小等级
            if (!arr[3].trim().isEmpty()) {
              minLevel = Integer.parseInt(arr[3].trim());
            }

            // 解析 最大等级
            if (!arr[4].trim().isEmpty()) {
              maxLevel = Integer.parseInt(arr[4].trim());
            }

            // 解析 稀有道具标记
            if (!arr[5].trim().isEmpty()) {
              isRare = Integer.parseInt(arr[5].trim());
            }

            // 解析 需求道具
            if (!arr[6].trim().isEmpty()) {
              demandProps = Integer.parseInt(arr[6].trim());
            }

            // 解析 道具名称
            if (!arr[7].trim().isEmpty()) {
              beizhu1 = arr[7].trim();
            }

            // 解析 需求数量
            if (!arr[8].trim().isEmpty()) {
              demandNum = Integer.parseInt(arr[8].trim());
            }

            // 解析 每个道具奖励
            if (!arr[9].trim().isEmpty()) {
              eachReward = arr[9].trim();
            }

            // 解析 协助额外奖励
            if (!arr[10].trim().isEmpty()) {
              additionalReward = arr[10].trim();
            }

            // 解析 随机权重
            if (!arr[11].trim().isEmpty()) {
              weights = Integer.parseInt(arr[11].trim());
            }

          } catch (Exception e) {
            logger.error(String.format("解析配置 %s 表, 字符串:%s 报错，请检查:%s", fileName, rowText, e.getMessage()));
            throw new ConfigLoadException("Error parsing config file :" + fileName);
          }
          SocietyPurchaseConfig config = new SocietyPurchaseConfig(id, phase, sequence, minLevel, maxLevel, isRare, demandProps, beizhu1, demandNum, eachReward, additionalReward, weights);
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

    public List<SocietyPurchaseConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, SocietyPurchaseConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "societyPurchase.txt";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
