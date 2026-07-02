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
 * File: SaitamaLevelConfigManager
 */
public class SaitamaLevelConfigManager implements InterfaceConfigManagerProxy {
  private static final AtomicBoolean switched = new AtomicBoolean(false);
  private static final SaitamaLevelConfigManager instance = new SaitamaLevelConfigManager();
  private static final SaitamaLevelConfigManagerImpl instanceImplA = new SaitamaLevelConfigManagerImpl();
  private static final SaitamaLevelConfigManagerImpl instanceImplB = new SaitamaLevelConfigManagerImpl();

  public static SaitamaLevelConfigManagerImpl getInstance() {
    return switched.get() ? instanceImplA : instanceImplB;
  }

  private static SaitamaLevelConfigManagerImpl getStandby() {
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
    SaitamaLevelConfigManagerImpl oldActive = getInstance();
    switched.set(!switched.get());
    return oldActive;
  }

  @Override
  public String getConfigFileName() {
    return getInstance().getConfigFileName();
  }

  public static class SaitamaLevelConfigManagerImpl extends AbstractConfigManger {
    private List<SaitamaLevelConfig> configList = List.of();
    private Map<Integer, SaitamaLevelConfig> configMap = Map.of();

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
      SaitamaLevelConfigChecker checker = new SaitamaLevelConfigChecker();
      checker.checkHeader(logger, configDir);
      List<SaitamaLevelConfig> newList = new ArrayList<>();
      Map<Integer, SaitamaLevelConfig> newMap = new HashMap<>();
      try (BufferedReader br = new BufferedReader(new FileReader(file))) {
        String rowText;
        br.readLine();
        br.readLine();
        while ((rowText = br.readLine()) != null) {
          if (rowText.isBlank()) { continue; }
          String[] arr = rowText.split("\\t", -1);
          if (arr.length < 18) {
            throw new ConfigLoadException("Config column size mismatch :" + fileName + ", line=" + rowText);
          }
          int id = 0;
          int nextLv = 0;
          int dailyGift = 0;
          int dailyGiftShow = 0;
          int giftTimes = 0;
          int summonLevel = 0;
          int exdropLevel = 0;
          int cookStar = 0;
          int cookmixPara = 0;
          int cookCritical = 0;
          int cookSlot = 0;
          int overflowMax = 0;
          int conversionRate = 0;
          int eggRewardTimes = 0;
          int trianningRewardLv = 0;
          String infoList = null;
          String upgradeInfo = null;
          int headIcon = 0;
          try {
            // 解析 编号
            if (!arr[0].trim().isEmpty()) {
              id = Integer.parseInt(arr[0].trim());
            }

            // 解析 到下一级经验
            if (!arr[1].trim().isEmpty()) {
              nextLv = Integer.parseInt(arr[1].trim());
            }

            // 解析 每日好感度礼包
            if (!arr[2].trim().isEmpty()) {
              dailyGift = Integer.parseInt(arr[2].trim());
            }

            // 解析 每日好感度礼包展示
            if (!arr[3].trim().isEmpty()) {
              dailyGiftShow = Integer.parseInt(arr[3].trim());
            }

            // 解析 每日好感度礼包领取次数
            if (!arr[4].trim().isEmpty()) {
              giftTimes = Integer.parseInt(arr[4].trim());
            }

            // 解析 埼玉卡片等级
            if (!arr[5].trim().isEmpty()) {
              summonLevel = Integer.parseInt(arr[5].trim());
            }

            // 解析 加成id
            if (!arr[6].trim().isEmpty()) {
              exdropLevel = Integer.parseInt(arr[6].trim());
            }

            // 解析 料理星级
            if (!arr[7].trim().isEmpty()) {
              cookStar = Integer.parseInt(arr[7].trim());
            }

            // 解析 料理合成加成系数
            if (!arr[8].trim().isEmpty()) {
              cookmixPara = Integer.parseInt(arr[8].trim());
            }

            // 解析 料理暴击概率（%）
            if (!arr[9].trim().isEmpty()) {
              cookCritical = Integer.parseInt(arr[9].trim());
            }

            // 解析 料理解锁栏位数
            if (!arr[10].trim().isEmpty()) {
              cookSlot = Integer.parseInt(arr[10].trim());
            }

            // 解析 体力储存池上限
            if (!arr[11].trim().isEmpty()) {
              overflowMax = Integer.parseInt(arr[11].trim());
            }

            // 解析 体力溢出转换率(%)
            if (!arr[12].trim().isEmpty()) {
              conversionRate = Integer.parseInt(arr[12].trim());
            }

            // 解析 每日彩蛋领取奖励最大次数
            if (!arr[13].trim().isEmpty()) {
              eggRewardTimes = Integer.parseInt(arr[13].trim());
            }

            // 解析 锻炼奖励等级
            if (!arr[14].trim().isEmpty()) {
              trianningRewardLv = Integer.parseInt(arr[14].trim());
            }

            // 解析 信息列表
            if (!arr[15].trim().isEmpty()) {
              infoList = arr[15].trim();
            }

            // 解析 升级信息预告列表
            if (!arr[16].trim().isEmpty()) {
              upgradeInfo = arr[16].trim();
            }

            // 解析 好感度入口头像
            if (!arr[17].trim().isEmpty()) {
              headIcon = Integer.parseInt(arr[17].trim());
            }

          } catch (Exception e) {
            logger.error(String.format("解析配置 %s 表, 字符串:%s 报错，请检查:%s", fileName, rowText, e.getMessage()));
            throw new ConfigLoadException("Error parsing config file :" + fileName);
          }
          SaitamaLevelConfig config = new SaitamaLevelConfig(id, nextLv, dailyGift, dailyGiftShow, giftTimes, summonLevel, exdropLevel, cookStar, cookmixPara, cookCritical, cookSlot, overflowMax, conversionRate, eggRewardTimes, trianningRewardLv, infoList, upgradeInfo, headIcon);
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

    public List<SaitamaLevelConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, SaitamaLevelConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "saitamaLevel.txt";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
