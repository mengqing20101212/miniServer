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
 * File: ActivityRouteConfigManager
 */
public class ActivityRouteConfigManager implements InterfaceConfigManagerProxy {
  private static final AtomicBoolean switched = new AtomicBoolean(false);
  private static final ActivityRouteConfigManager instance = new ActivityRouteConfigManager();
  private static final ActivityRouteConfigManagerImpl instanceImplA = new ActivityRouteConfigManagerImpl();
  private static final ActivityRouteConfigManagerImpl instanceImplB = new ActivityRouteConfigManagerImpl();

  public static ActivityRouteConfigManagerImpl getInstance() {
    return switched.get() ? instanceImplA : instanceImplB;
  }

  private static ActivityRouteConfigManagerImpl getStandby() {
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
    ActivityRouteConfigManagerImpl oldActive = getInstance();
    switched.set(!switched.get());
    return oldActive;
  }

  @Override
  public String getConfigFileName() {
    return getInstance().getConfigFileName();
  }

  public static class ActivityRouteConfigManagerImpl extends AbstractConfigManger {
    private List<ActivityRouteConfig> configList = List.of();
    private Map<Integer, ActivityRouteConfig> configMap = Map.of();

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
      ActivityRouteConfigChecker checker = new ActivityRouteConfigChecker();
      checker.checkHeader(logger, configDir);
      List<ActivityRouteConfig> newList = new ArrayList<>();
      Map<Integer, ActivityRouteConfig> newMap = new HashMap<>();
      try (BufferedReader br = new BufferedReader(new FileReader(file))) {
        String rowText;
        br.readLine();
        br.readLine();
        while ((rowText = br.readLine()) != null) {
          if (rowText.isBlank()) { continue; }
          String[] arr = rowText.split("\\t", -1);
          if (arr.length < 15) {
            throw new ConfigLoadException("Config column size mismatch :" + fileName + ", line=" + rowText);
          }
          int id = 0;
          int drop = 0;
          String rewardShow = null;
          int score = 0;
          int array = 0;
          int row = 0;
          String front = null;
          int payGift = 0;
          String payGiftShow = null;
          int rechargeId = 0;
          int isCrossNode = 0;
          int drop2 = 0;
          String rewardShow2 = null;
          String line = null;
          String circle = null;
          try {
            // 解析 编号
            if (!arr[0].trim().isEmpty()) {
              id = Integer.parseInt(arr[0].trim());
            }

            // 解析 积分奖励
            if (!arr[1].trim().isEmpty()) {
              drop = Integer.parseInt(arr[1].trim());
            }

            // 解析 积分奖励展示
            if (!arr[2].trim().isEmpty()) {
              rewardShow = arr[2].trim();
            }

            // 解析 消耗积分
            if (!arr[3].trim().isEmpty()) {
              score = Integer.parseInt(arr[3].trim());
            }

            // 解析 所属位置(列)
            if (!arr[4].trim().isEmpty()) {
              array = Integer.parseInt(arr[4].trim());
            }

            // 解析 所属位置(排)
            if (!arr[5].trim().isEmpty()) {
              row = Integer.parseInt(arr[5].trim());
            }

            // 解析 前置档位
            if (!arr[6].trim().isEmpty()) {
              front = arr[6].trim();
            }

            // 解析 付费奖励
            if (!arr[7].trim().isEmpty()) {
              payGift = Integer.parseInt(arr[7].trim());
            }

            // 解析 付费奖励展示
            if (!arr[8].trim().isEmpty()) {
              payGiftShow = arr[8].trim();
            }

            // 解析 充值ID
            if (!arr[9].trim().isEmpty()) {
              rechargeId = Integer.parseInt(arr[9].trim());
            }

            // 解析 是否是交叉点
            if (!arr[10].trim().isEmpty()) {
              isCrossNode = Integer.parseInt(arr[10].trim());
            }

            // 解析 付费积分奖励
            if (!arr[11].trim().isEmpty()) {
              drop2 = Integer.parseInt(arr[11].trim());
            }

            // 解析 积分奖励展示
            if (!arr[12].trim().isEmpty()) {
              rewardShow2 = arr[12].trim();
            }

            // 解析 线坐标
            if (!arr[13].trim().isEmpty()) {
              line = arr[13].trim();
            }

            // 解析 圈坐标
            if (!arr[14].trim().isEmpty()) {
              circle = arr[14].trim();
            }

          } catch (Exception e) {
            logger.error(String.format("解析配置 %s 表, 字符串:%s 报错，请检查:%s", fileName, rowText, e.getMessage()));
            throw new ConfigLoadException("Error parsing config file :" + fileName);
          }
          ActivityRouteConfig config = new ActivityRouteConfig(id, drop, rewardShow, score, array, row, front, payGift, payGiftShow, rechargeId, isCrossNode, drop2, rewardShow2, line, circle);
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

    public List<ActivityRouteConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, ActivityRouteConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "activityRoute.txt";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
