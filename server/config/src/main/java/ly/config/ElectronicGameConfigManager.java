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
 * File: ElectronicGameConfigManager
 */
public class ElectronicGameConfigManager implements InterfaceConfigManagerProxy {
  private static final AtomicBoolean switched = new AtomicBoolean(false);
  private static final ElectronicGameConfigManager instance = new ElectronicGameConfigManager();
  private static final ElectronicGameConfigManagerImpl instanceImplA = new ElectronicGameConfigManagerImpl();
  private static final ElectronicGameConfigManagerImpl instanceImplB = new ElectronicGameConfigManagerImpl();

  public static ElectronicGameConfigManagerImpl getInstance() {
    return switched.get() ? instanceImplA : instanceImplB;
  }

  private static ElectronicGameConfigManagerImpl getStandby() {
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
    ElectronicGameConfigManagerImpl oldActive = getInstance();
    switched.set(!switched.get());
    return oldActive;
  }

  @Override
  public String getConfigFileName() {
    return getInstance().getConfigFileName();
  }

  public static class ElectronicGameConfigManagerImpl extends AbstractConfigManger {
    private List<ElectronicGameConfig> configList = List.of();
    private Map<Integer, ElectronicGameConfig> configMap = Map.of();

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
      ElectronicGameConfigChecker checker = new ElectronicGameConfigChecker();
      checker.checkHeader(logger, configDir);
      List<ElectronicGameConfig> newList = new ArrayList<>();
      Map<Integer, ElectronicGameConfig> newMap = new HashMap<>();
      try (BufferedReader br = new BufferedReader(new FileReader(file))) {
        String rowText;
        br.readLine();
        br.readLine();
        while ((rowText = br.readLine()) != null) {
          if (rowText.isBlank()) { continue; }
          String[] arr = rowText.split("\\t", -1);
          if (arr.length < 39) {
            throw new ConfigLoadException("Config column size mismatch :" + fileName + ", line=" + rowText);
          }
          int id = 0;
          String duan = null;
          String duanBigName = null;
          int duanBig = 0;
          int duanSmall = 0;
          int duanIconBig = 0;
          int duanIconSmall = 0;
          int starNum = 0;
          int starCollect = 0;
          float eloKValue = 0F;
          int scoreMax = 0;
          int scoreProtection = 0;
          int isScoreProtection = 0;
          int winningStreak = 0;
          int dropProtection = 0;
          int duanProtection = 0;
          int bestMatchScore = 0;
          int bestMatchTime = 0;
          int bestMatchStar = 0;
          int fuzzyMatchScore = 0;
          int fuzzyMatchTime = 0;
          int fuzzyMatchStar = 0;
          int leastMatchScore = 0;
          int leastMatchTime = 0;
          int leastMatchStar = 0;
          int isTimeOutRebotMatch = 0;
          int isLoseRobotMatch = 0;
          int isPick = 0;
          int winReward = 0;
          int loseReward = 0;
          int honorLimit = 0;
          String awardWeek = null;
          String awardWeekPre = null;
          String bossTimeSetting = null;
          int robotPool = 0;
          int fairSceneMatch1 = 0;
          int SceneMatch3v3 = 0;
          int drop = 0;
          int dropShow = 0;
          try {
            // 解析 编号
            if (!arr[0].trim().isEmpty()) {
              id = Integer.parseInt(arr[0].trim());
            }

            // 解析 段位
            if (!arr[1].trim().isEmpty()) {
              duan = arr[1].trim();
            }

            // 解析 大段名
            if (!arr[2].trim().isEmpty()) {
              duanBigName = arr[2].trim();
            }

            // 解析 大段位
            if (!arr[3].trim().isEmpty()) {
              duanBig = Integer.parseInt(arr[3].trim());
            }

            // 解析 小段位
            if (!arr[4].trim().isEmpty()) {
              duanSmall = Integer.parseInt(arr[4].trim());
            }

            // 解析 段位图标
            if (!arr[5].trim().isEmpty()) {
              duanIconBig = Integer.parseInt(arr[5].trim());
            }

            // 解析 段位图标
            if (!arr[6].trim().isEmpty()) {
              duanIconSmall = Integer.parseInt(arr[6].trim());
            }

            // 解析 星级数量
            if (!arr[7].trim().isEmpty()) {
              starNum = Integer.parseInt(arr[7].trim());
            }

            // 解析 累积星数
            if (!arr[8].trim().isEmpty()) {
              starCollect = Integer.parseInt(arr[8].trim());
            }

            // 解析 ELO分K值
            if (!arr[9].trim().isEmpty()) {
              eloKValue = Float.parseFloat(arr[9].trim());
            }

            // 解析 勇者积分上限
            if (!arr[10].trim().isEmpty()) {
              scoreMax = Integer.parseInt(arr[10].trim());
            }

            // 解析 保星积分
            if (!arr[11].trim().isEmpty()) {
              scoreProtection = Integer.parseInt(arr[11].trim());
            }

            // 解析 是否开启积分保星
            if (!arr[12].trim().isEmpty()) {
              isScoreProtection = Integer.parseInt(arr[12].trim());
            }

            // 解析 连胜加星
            if (!arr[13].trim().isEmpty()) {
              winningStreak = Integer.parseInt(arr[13].trim());
            }

            // 解析 掉段保护
            if (!arr[14].trim().isEmpty()) {
              dropProtection = Integer.parseInt(arr[14].trim());
            }

            // 解析 保大段
            if (!arr[15].trim().isEmpty()) {
              duanProtection = Integer.parseInt(arr[15].trim());
            }

            // 解析 精确匹配分数
            if (!arr[16].trim().isEmpty()) {
              bestMatchScore = Integer.parseInt(arr[16].trim());
            }

            // 解析 精确匹配时间
            if (!arr[17].trim().isEmpty()) {
              bestMatchTime = Integer.parseInt(arr[17].trim());
            }

            // 解析 精确匹配星数
            if (!arr[18].trim().isEmpty()) {
              bestMatchStar = Integer.parseInt(arr[18].trim());
            }

            // 解析 模糊匹配分数
            if (!arr[19].trim().isEmpty()) {
              fuzzyMatchScore = Integer.parseInt(arr[19].trim());
            }

            // 解析 模糊匹配时间
            if (!arr[20].trim().isEmpty()) {
              fuzzyMatchTime = Integer.parseInt(arr[20].trim());
            }

            // 解析 模糊匹配星数
            if (!arr[21].trim().isEmpty()) {
              fuzzyMatchStar = Integer.parseInt(arr[21].trim());
            }

            // 解析 保底匹配分数
            if (!arr[22].trim().isEmpty()) {
              leastMatchScore = Integer.parseInt(arr[22].trim());
            }

            // 解析 保底匹配时间
            if (!arr[23].trim().isEmpty()) {
              leastMatchTime = Integer.parseInt(arr[23].trim());
            }

            // 解析 保底匹配星数
            if (!arr[24].trim().isEmpty()) {
              leastMatchStar = Integer.parseInt(arr[24].trim());
            }

            // 解析 是否超时保底匹配机器人
            if (!arr[25].trim().isEmpty()) {
              isTimeOutRebotMatch = Integer.parseInt(arr[25].trim());
            }

            // 解析 是否战败保底匹配机器人
            if (!arr[26].trim().isEmpty()) {
              isLoseRobotMatch = Integer.parseInt(arr[26].trim());
            }

            // 解析 是否轮选
            if (!arr[27].trim().isEmpty()) {
              isPick = Integer.parseInt(arr[27].trim());
            }

            // 解析 战斗胜利荣誉
            if (!arr[28].trim().isEmpty()) {
              winReward = Integer.parseInt(arr[28].trim());
            }

            // 解析 战斗失败荣誉
            if (!arr[29].trim().isEmpty()) {
              loseReward = Integer.parseInt(arr[29].trim());
            }

            // 解析 每周荣誉上限
            if (!arr[30].trim().isEmpty()) {
              honorLimit = Integer.parseInt(arr[30].trim());
            }

            // 解析 每周结算奖励
            if (!arr[31].trim().isEmpty()) {
              awardWeek = arr[31].trim();
            }

            // 解析 每周结算奖励预览
            if (!arr[32].trim().isEmpty()) {
              awardWeekPre = arr[32].trim();
            }

            // 解析 匹配服务器
            if (!arr[33].trim().isEmpty()) {
              bossTimeSetting = arr[33].trim();
            }

            // 解析 对应机器人范围
            if (!arr[34].trim().isEmpty()) {
              robotPool = Integer.parseInt(arr[34].trim());
            }

            // 解析 对应场次
            if (!arr[35].trim().isEmpty()) {
              fairSceneMatch1 = Integer.parseInt(arr[35].trim());
            }

            // 解析 对应场次
            if (!arr[36].trim().isEmpty()) {
              SceneMatch3v3 = Integer.parseInt(arr[36].trim());
            }

            // 解析 段位奖励
            if (!arr[37].trim().isEmpty()) {
              drop = Integer.parseInt(arr[37].trim());
            }

            // 解析 段位奖励前端
            if (!arr[38].trim().isEmpty()) {
              dropShow = Integer.parseInt(arr[38].trim());
            }

          } catch (Exception e) {
            logger.error(String.format("解析配置 %s 表, 字符串:%s 报错，请检查:%s", fileName, rowText, e.getMessage()));
            throw new ConfigLoadException("Error parsing config file :" + fileName);
          }
          ElectronicGameConfig config = new ElectronicGameConfig(id, duan, duanBigName, duanBig, duanSmall, duanIconBig, duanIconSmall, starNum, starCollect, eloKValue, scoreMax, scoreProtection, isScoreProtection, winningStreak, dropProtection, duanProtection, bestMatchScore, bestMatchTime, bestMatchStar, fuzzyMatchScore, fuzzyMatchTime, fuzzyMatchStar, leastMatchScore, leastMatchTime, leastMatchStar, isTimeOutRebotMatch, isLoseRobotMatch, isPick, winReward, loseReward, honorLimit, awardWeek, awardWeekPre, bossTimeSetting, robotPool, fairSceneMatch1, SceneMatch3v3, drop, dropShow);
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

    public List<ElectronicGameConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, ElectronicGameConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "electronicGame.txt";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
