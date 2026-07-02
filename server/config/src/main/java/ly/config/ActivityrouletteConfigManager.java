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
 * File: ActivityrouletteConfigManager
 */
public class ActivityrouletteConfigManager implements InterfaceConfigManagerProxy {
  private static final AtomicBoolean switched = new AtomicBoolean(false);
  private static final ActivityrouletteConfigManager instance = new ActivityrouletteConfigManager();
  private static final ActivityrouletteConfigManagerImpl instanceImplA = new ActivityrouletteConfigManagerImpl();
  private static final ActivityrouletteConfigManagerImpl instanceImplB = new ActivityrouletteConfigManagerImpl();

  public static ActivityrouletteConfigManagerImpl getInstance() {
    return switched.get() ? instanceImplA : instanceImplB;
  }

  private static ActivityrouletteConfigManagerImpl getStandby() {
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
    ActivityrouletteConfigManagerImpl oldActive = getInstance();
    switched.set(!switched.get());
    return oldActive;
  }

  @Override
  public String getConfigFileName() {
    return getInstance().getConfigFileName();
  }

  public static class ActivityrouletteConfigManagerImpl extends AbstractConfigManger {
    private List<ActivityrouletteConfig> configList = List.of();
    private Map<Integer, ActivityrouletteConfig> configMap = Map.of();

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
      ActivityrouletteConfigChecker checker = new ActivityrouletteConfigChecker();
      checker.checkHeader(logger, configDir);
      List<ActivityrouletteConfig> newList = new ArrayList<>();
      Map<Integer, ActivityrouletteConfig> newMap = new HashMap<>();
      try (BufferedReader br = new BufferedReader(new FileReader(file))) {
        String rowText;
        br.readLine();
        br.readLine();
        while ((rowText = br.readLine()) != null) {
          if (rowText.isBlank()) { continue; }
          String[] arr = rowText.split("\\t", -1);
          if (arr.length < 16) {
            throw new ConfigLoadException("Config column size mismatch :" + fileName + ", line=" + rowText);
          }
          int id = 0;
          int scheDuling = 0;
          int prizeId = 0;
          int luckyPrize = 0;
          int rafflePoints = 0;
          int minimumPoints = 0;
          int consumableProps = 0;
          int consumptionQuantity = 0;
          int luckValueCap = 0;
          int resetLuck = 0;
          int getLucky = 0;
          int lotteryGuarantee = 0;
          String rewardShow = null;
          int drop = 0;
          int numberAwards = 0;
          String picture = null;
          try {
            // 解析 序号
            if (!arr[0].trim().isEmpty()) {
              id = Integer.parseInt(arr[0].trim());
            }

            // 解析 活动期数
            if (!arr[1].trim().isEmpty()) {
              scheDuling = Integer.parseInt(arr[1].trim());
            }

            // 解析 奖池id
            if (!arr[2].trim().isEmpty()) {
              prizeId = Integer.parseInt(arr[2].trim());
            }

            // 解析 幸运值大奖档位
            if (!arr[3].trim().isEmpty()) {
              luckyPrize = Integer.parseInt(arr[3].trim());
            }

            // 解析 每次抽奖获得积分
            if (!arr[4].trim().isEmpty()) {
              rafflePoints = Integer.parseInt(arr[4].trim());
            }

            // 解析 触发珍品所需最低积分
            if (!arr[5].trim().isEmpty()) {
              minimumPoints = Integer.parseInt(arr[5].trim());
            }

            // 解析 单次抽奖消耗道具ID
            if (!arr[6].trim().isEmpty()) {
              consumableProps = Integer.parseInt(arr[6].trim());
            }

            // 解析 单次抽奖消耗道具数量
            if (!arr[7].trim().isEmpty()) {
              consumptionQuantity = Integer.parseInt(arr[7].trim());
            }

            // 解析 幸运值上限
            if (!arr[8].trim().isEmpty()) {
              luckValueCap = Integer.parseInt(arr[8].trim());
            }

            // 解析 抽中幸运值大奖后幸运值是否重置
            if (!arr[9].trim().isEmpty()) {
              resetLuck = Integer.parseInt(arr[9].trim());
            }

            // 解析 每次抽奖获得幸运值
            if (!arr[10].trim().isEmpty()) {
              getLucky = Integer.parseInt(arr[10].trim());
            }

            // 解析 10连保底
            if (!arr[11].trim().isEmpty()) {
              lotteryGuarantee = Integer.parseInt(arr[11].trim());
            }

            // 解析 奖励展示
            if (!arr[12].trim().isEmpty()) {
              rewardShow = arr[12].trim();
            }

            // 解析 实际掉落
            if (!arr[13].trim().isEmpty()) {
              drop = Integer.parseInt(arr[13].trim());
            }

            // 解析 幸运值珍品奖励次数
            if (!arr[14].trim().isEmpty()) {
              numberAwards = Integer.parseInt(arr[14].trim());
            }

            // 解析 立绘
            if (!arr[15].trim().isEmpty()) {
              picture = arr[15].trim();
            }

          } catch (Exception e) {
            logger.error(String.format("解析配置 %s 表, 字符串:%s 报错，请检查:%s", fileName, rowText, e.getMessage()));
            throw new ConfigLoadException("Error parsing config file :" + fileName);
          }
          ActivityrouletteConfig config = new ActivityrouletteConfig(id, scheDuling, prizeId, luckyPrize, rafflePoints, minimumPoints, consumableProps, consumptionQuantity, luckValueCap, resetLuck, getLucky, lotteryGuarantee, rewardShow, drop, numberAwards, picture);
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

    public List<ActivityrouletteConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, ActivityrouletteConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "activityroulette.txt";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
