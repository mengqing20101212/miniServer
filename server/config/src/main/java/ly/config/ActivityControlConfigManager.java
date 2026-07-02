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
 * File: ActivityControlConfigManager
 */
public class ActivityControlConfigManager implements InterfaceConfigManagerProxy {
  private static final AtomicBoolean switched = new AtomicBoolean(false);
  private static final ActivityControlConfigManager instance = new ActivityControlConfigManager();
  private static final ActivityControlConfigManagerImpl instanceImplA = new ActivityControlConfigManagerImpl();
  private static final ActivityControlConfigManagerImpl instanceImplB = new ActivityControlConfigManagerImpl();

  public static ActivityControlConfigManagerImpl getInstance() {
    return switched.get() ? instanceImplA : instanceImplB;
  }

  private static ActivityControlConfigManagerImpl getStandby() {
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
    ActivityControlConfigManagerImpl oldActive = getInstance();
    switched.set(!switched.get());
    return oldActive;
  }

  @Override
  public String getConfigFileName() {
    return getInstance().getConfigFileName();
  }

  public static class ActivityControlConfigManagerImpl extends AbstractConfigManger {
    private List<ActivityControlConfig> configList = List.of();
    private Map<Integer, ActivityControlConfig> configMap = Map.of();

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
      ActivityControlConfigChecker checker = new ActivityControlConfigChecker();
      checker.checkHeader(logger, configDir);
      List<ActivityControlConfig> newList = new ArrayList<>();
      Map<Integer, ActivityControlConfig> newMap = new HashMap<>();
      try (BufferedReader br = new BufferedReader(new FileReader(file))) {
        String rowText;
        br.readLine();
        br.readLine();
        while ((rowText = br.readLine()) != null) {
          if (rowText.isBlank()) { continue; }
          String[] arr = rowText.split("\\t", -1);
          if (arr.length < 29) {
            throw new ConfigLoadException("Config column size mismatch :" + fileName + ", line=" + rowText);
          }
          int id = 0;
          String name = null;
          int unlockType = 0;
          int unlockPara = 0;
          int unlockPara2 = 0;
          int lineupId = 0;
          int lineupLimit = 0;
          int isOnlineBattle = 0;
          int BgmId = 0;
          int openType = 0;
          String openPara = null;
          int guideId = 0;
          int icon = 0;
          String des = null;
          String timeDes = null;
          String rewardId = null;
          String openLimitDes = null;
          int turnId = 0;
          int lineupTeamId = 0;
          int saveBattleLog = 0;
          int dayLimit = 0;
          int weekLimit = 0;
          String activityIcon = null;
          String activityName = null;
          String bgColour = null;
          String activityreward = null;
          String teamLv = null;
          int help = 0;
          int noPrelock = 0;
          try {
            // 解析 编号
            if (!arr[0].trim().isEmpty()) {
              id = Integer.parseInt(arr[0].trim());
            }

            // 解析 活动名称
            if (!arr[1].trim().isEmpty()) {
              name = arr[1].trim();
            }

            // 解析 解锁类型
            if (!arr[2].trim().isEmpty()) {
              unlockType = Integer.parseInt(arr[2].trim());
            }

            // 解析 解锁参数
            if (!arr[3].trim().isEmpty()) {
              unlockPara = Integer.parseInt(arr[3].trim());
            }

            // 解析 解锁参数2
            if (!arr[4].trim().isEmpty()) {
              unlockPara2 = Integer.parseInt(arr[4].trim());
            }

            // 解析 阵容id
            if (!arr[5].trim().isEmpty()) {
              lineupId = Integer.parseInt(arr[5].trim());
            }

            // 解析 阵容限制
            if (!arr[6].trim().isEmpty()) {
              lineupLimit = Integer.parseInt(arr[6].trim());
            }

            // 解析 是否是服务器战斗
            if (!arr[7].trim().isEmpty()) {
              isOnlineBattle = Integer.parseInt(arr[7].trim());
            }

            // 解析 系统相关的BGM
            if (!arr[8].trim().isEmpty()) {
              BgmId = Integer.parseInt(arr[8].trim());
            }

            // 解析 开启类型
            if (!arr[9].trim().isEmpty()) {
              openType = Integer.parseInt(arr[9].trim());
            }

            // 解析 开启参数
            if (!arr[10].trim().isEmpty()) {
              openPara = arr[10].trim();
            }

            // 解析 新手引导id
            if (!arr[11].trim().isEmpty()) {
              guideId = Integer.parseInt(arr[11].trim());
            }

            // 解析 图标
            if (!arr[12].trim().isEmpty()) {
              icon = Integer.parseInt(arr[12].trim());
            }

            // 解析 描述
            if (!arr[13].trim().isEmpty()) {
              des = arr[13].trim();
            }

            // 解析 时间描述
            if (!arr[14].trim().isEmpty()) {
              timeDes = arr[14].trim();
            }

            // 解析 奖励展示
            if (!arr[15].trim().isEmpty()) {
              rewardId = arr[15].trim();
            }

            // 解析 未解锁提示
            if (!arr[16].trim().isEmpty()) {
              openLimitDes = arr[16].trim();
            }

            // 解析 跳转id
            if (!arr[17].trim().isEmpty()) {
              turnId = Integer.parseInt(arr[17].trim());
            }

            // 解析 组队阵容id
            if (!arr[18].trim().isEmpty()) {
              lineupTeamId = Integer.parseInt(arr[18].trim());
            }

            // 解析 是否在个人空间展示战绩
            if (!arr[19].trim().isEmpty()) {
              saveBattleLog = Integer.parseInt(arr[19].trim());
            }

            // 解析 每日限制
            if (!arr[20].trim().isEmpty()) {
              dayLimit = Integer.parseInt(arr[20].trim());
            }

            // 解析 每周限制
            if (!arr[21].trim().isEmpty()) {
              weekLimit = Integer.parseInt(arr[21].trim());
            }

            // 解析 图标
            if (!arr[22].trim().isEmpty()) {
              activityIcon = arr[22].trim();
            }

            // 解析 名字
            if (!arr[23].trim().isEmpty()) {
              activityName = arr[23].trim();
            }

            // 解析 立绘+颜色
            if (!arr[24].trim().isEmpty()) {
              bgColour = arr[24].trim();
            }

            // 解析 奖励类型
            if (!arr[25].trim().isEmpty()) {
              activityreward = arr[25].trim();
            }

            // 解析 组队推荐等级
            if (!arr[26].trim().isEmpty()) {
              teamLv = arr[26].trim();
            }

            // 解析 帮助跳转
            if (!arr[27].trim().isEmpty()) {
              help = Integer.parseInt(arr[27].trim());
            }

            // 解析 是否屏幕预解锁
            if (!arr[28].trim().isEmpty()) {
              noPrelock = Integer.parseInt(arr[28].trim());
            }

          } catch (Exception e) {
            logger.error(String.format("解析配置 %s 表, 字符串:%s 报错，请检查:%s", fileName, rowText, e.getMessage()));
            throw new ConfigLoadException("Error parsing config file :" + fileName);
          }
          ActivityControlConfig config = new ActivityControlConfig(id, name, unlockType, unlockPara, unlockPara2, lineupId, lineupLimit, isOnlineBattle, BgmId, openType, openPara, guideId, icon, des, timeDes, rewardId, openLimitDes, turnId, lineupTeamId, saveBattleLog, dayLimit, weekLimit, activityIcon, activityName, bgColour, activityreward, teamLv, help, noPrelock);
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

    public List<ActivityControlConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, ActivityControlConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "activityControl.txt";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
