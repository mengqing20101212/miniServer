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
 * File: ActivitywishingConfigManager
 */
public class ActivitywishingConfigManager implements InterfaceConfigManagerProxy {
  private static final AtomicBoolean switched = new AtomicBoolean(false);
  private static final ActivitywishingConfigManager instance = new ActivitywishingConfigManager();
  private static final ActivitywishingConfigManagerImpl instanceImplA = new ActivitywishingConfigManagerImpl();
  private static final ActivitywishingConfigManagerImpl instanceImplB = new ActivitywishingConfigManagerImpl();

  public static ActivitywishingConfigManagerImpl getInstance() {
    return switched.get() ? instanceImplA : instanceImplB;
  }

  private static ActivitywishingConfigManagerImpl getStandby() {
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
    ActivitywishingConfigManagerImpl oldActive = getInstance();
    switched.set(!switched.get());
    return oldActive;
  }

  @Override
  public String getConfigFileName() {
    return getInstance().getConfigFileName();
  }

  public static class ActivitywishingConfigManagerImpl extends AbstractConfigManger {
    private List<ActivitywishingConfig> configList = List.of();
    private Map<Integer, ActivitywishingConfig> configMap = Map.of();

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
      ActivitywishingConfigChecker checker = new ActivitywishingConfigChecker();
      checker.checkHeader(logger, configDir);
      List<ActivitywishingConfig> newList = new ArrayList<>();
      Map<Integer, ActivitywishingConfig> newMap = new HashMap<>();
      try (BufferedReader br = new BufferedReader(new FileReader(file))) {
        String rowText;
        br.readLine();
        br.readLine();
        while ((rowText = br.readLine()) != null) {
          if (rowText.isBlank()) { continue; }
          String[] arr = rowText.split("\\t", -1);
          if (arr.length < 14) {
            throw new ConfigLoadException("Config column size mismatch :" + fileName + ", line=" + rowText);
          }
          int id = 0;
          int wishingNum = 0;
          String awardList = null;
          String awardRelativePro = null;
          String desc = null;
          int dayLimit = 0;
          int Guaranteed = 0;
          String Guaranteerange = null;
          String GuaranteerangeWeights = null;
          int intervaltime = 0;
          int Receiveaward = 0;
          String rewardShow = null;
          String pictures1 = null;
          String pictures2 = null;
          try {
            // 解析 编号
            if (!arr[0].trim().isEmpty()) {
              id = Integer.parseInt(arr[0].trim());
            }

            // 解析 许愿次数次数
            if (!arr[1].trim().isEmpty()) {
              wishingNum = Integer.parseInt(arr[1].trim());
            }

            // 解析 掉落列表
            if (!arr[2].trim().isEmpty()) {
              awardList = arr[2].trim();
            }

            // 解析 掉落权重
            if (!arr[3].trim().isEmpty()) {
              awardRelativePro = arr[3].trim();
            }

            // 解析 注释
            if (!arr[4].trim().isEmpty()) {
              desc = arr[4].trim();
            }

            // 解析 每日上限
            if (!arr[5].trim().isEmpty()) {
              dayLimit = Integer.parseInt(arr[5].trim());
            }

            // 解析 10次保底道具数量
            if (!arr[6].trim().isEmpty()) {
              Guaranteed = Integer.parseInt(arr[6].trim());
            }

            // 解析 保底道具范围
            if (!arr[7].trim().isEmpty()) {
              Guaranteerange = arr[7].trim();
            }

            // 解析 保底道具权重
            if (!arr[8].trim().isEmpty()) {
              GuaranteerangeWeights = arr[8].trim();
            }

            // 解析 活动开启间隔时间
            if (!arr[9].trim().isEmpty()) {
              intervaltime = Integer.parseInt(arr[9].trim());
            }

            // 解析 领奖次数
            if (!arr[10].trim().isEmpty()) {
              Receiveaward = Integer.parseInt(arr[10].trim());
            }

            // 解析 奖励展示
            if (!arr[11].trim().isEmpty()) {
              rewardShow = arr[11].trim();
            }

            // 解析 活动图片1
            if (!arr[12].trim().isEmpty()) {
              pictures1 = arr[12].trim();
            }

            // 解析 活动图片2
            if (!arr[13].trim().isEmpty()) {
              pictures2 = arr[13].trim();
            }

          } catch (Exception e) {
            logger.error(String.format("解析配置 %s 表, 字符串:%s 报错，请检查:%s", fileName, rowText, e.getMessage()));
            throw new ConfigLoadException("Error parsing config file :" + fileName);
          }
          ActivitywishingConfig config = new ActivitywishingConfig(id, wishingNum, awardList, awardRelativePro, desc, dayLimit, Guaranteed, Guaranteerange, GuaranteerangeWeights, intervaltime, Receiveaward, rewardShow, pictures1, pictures2);
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

    public List<ActivitywishingConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, ActivitywishingConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "activitywishing.txt";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
