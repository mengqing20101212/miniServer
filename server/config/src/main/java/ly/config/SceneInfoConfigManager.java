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
 * File: SceneInfoConfigManager
 */
public class SceneInfoConfigManager implements InterfaceConfigManagerProxy {
  private static final AtomicBoolean switched = new AtomicBoolean(false);
  private static final SceneInfoConfigManager instance = new SceneInfoConfigManager();
  private static final SceneInfoConfigManagerImpl instanceImplA = new SceneInfoConfigManagerImpl();
  private static final SceneInfoConfigManagerImpl instanceImplB = new SceneInfoConfigManagerImpl();

  public static SceneInfoConfigManagerImpl getInstance() {
    return switched.get() ? instanceImplA : instanceImplB;
  }

  private static SceneInfoConfigManagerImpl getStandby() {
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
    SceneInfoConfigManagerImpl oldActive = getInstance();
    switched.set(!switched.get());
    return oldActive;
  }

  @Override
  public String getConfigFileName() {
    return getInstance().getConfigFileName();
  }

  public static class SceneInfoConfigManagerImpl extends AbstractConfigManger {
    private List<SceneInfoConfig> configList = List.of();
    private Map<Integer, SceneInfoConfig> configMap = Map.of();

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
      SceneInfoConfigChecker checker = new SceneInfoConfigChecker();
      checker.checkHeader(logger, configDir);
      List<SceneInfoConfig> newList = new ArrayList<>();
      Map<Integer, SceneInfoConfig> newMap = new HashMap<>();
      try (BufferedReader br = new BufferedReader(new FileReader(file))) {
        String rowText;
        br.readLine();
        br.readLine();
        while ((rowText = br.readLine()) != null) {
          if (rowText.isBlank()) { continue; }
          String[] arr = rowText.split("\\t", -1);
          if (arr.length < 78) {
            throw new ConfigLoadException("Config column size mismatch :" + fileName + ", line=" + rowText);
          }
          int id = 0;
          String sceneName = null;
          String sceneResource = null;
          int levelRequire = 0;
          String sceneRequire = null;
          int returnStamina = 0;
          String startPerformance = null;
          int sceneBGM = 0;
          int sceneType = 0;
          int isClient = 0;
          int checkBattle = 0;
          int prepareType = 0;
          int readyWaitTime = 0;
          int activityId = 0;
          int lineupNo = 0;
          int lineupType = 0;
          String appointedNPC = null;
          int defaultSpEnergy = 0;
          int spEnergyCoe = 0;
          int defaultEnergy = 0;
          int defaultEnergyRecover = 0;
          int defaultEnergyRaise = 0;
          int defaultEnergyRecoverMax = 0;
          int defaultEnergyBar = 0;
          int maxMember = 0;
          int sceneControlResource = 0;
          String bossPos = null;
          int npcType = 0;
          String sceneNpc_1 = null;
          String sceneNpc_2 = null;
          String sceneNpc_3 = null;
          String sceneNpc_4 = null;
          String sceneNpc_5 = null;
          String battlePosId = null;
          String knockPosition_a = null;
          String knockPosition_b = null;
          String sceneObjects = null;
          int bonusGroup = 0;
          int fixedBonusGroup = 0;
          String eventIds = null;
          int changeInfo = 0;
          String endInfo = null;
          int dropGroup = 0;
          int starType = 0;
          String starList = null;
          String dropList = null;
          int exp = 0;
          int gold = 0;
          int firstDrop = 0;
          int summonType = 0;
          String sceneSkills = null;
          int triggerPro1 = 0;
          String triggerStage1 = null;
          int triggerPro2 = 0;
          String triggerStage2 = null;
          int light = 0;
          String scenePos = null;
          String scenePoint = null;
          String hintType = null;
          String sceneHint = null;
          String hintPic = null;
          String endShow = null;
          int isCloseSpeed = 0;
          String isCloseAuto = null;
          String isClosePerformance = null;
          int isOpenSpecialUi = 0;
          String battleStartUiTpye = null;
          int showSelfBlood = 0;
          int tryAgain = 0;
          int sceneTimeId = 0;
          String showDetail = null;
          int changeHeroTime = 0;
          int isShare = 0;
          int strategy = 0;
          int autoSaveLineup = 0;
          int isTryPlay = 0;
          int isChangeSkin = 0;
          String blockBattleConvergeId = null;
          try {
            // 解析 编号
            if (!arr[0].trim().isEmpty()) {
              id = Integer.parseInt(arr[0].trim());
            }

            // 解析 场景名称
            if (!arr[1].trim().isEmpty()) {
              sceneName = arr[1].trim();
            }

            // 解析 场景资源
            if (!arr[2].trim().isEmpty()) {
              sceneResource = arr[2].trim();
            }

            // 解析 进入等级（作废）
            if (!arr[3].trim().isEmpty()) {
              levelRequire = Integer.parseInt(arr[3].trim());
            }

            // 解析 前置关卡（作废）
            if (!arr[4].trim().isEmpty()) {
              sceneRequire = arr[4].trim();
            }

            // 解析 失败体力返还（作废）
            if (!arr[5].trim().isEmpty()) {
              returnStamina = Integer.parseInt(arr[5].trim());
            }

            // 解析 进场动画
            if (!arr[6].trim().isEmpty()) {
              startPerformance = arr[6].trim();
            }

            // 解析 场景音乐
            if (!arr[7].trim().isEmpty()) {
              sceneBGM = Integer.parseInt(arr[7].trim());
            }

            // 解析 关卡类型
            if (!arr[8].trim().isEmpty()) {
              sceneType = Integer.parseInt(arr[8].trim());
            }

            // 解析 是否只是前端运算
            if (!arr[9].trim().isEmpty()) {
              isClient = Integer.parseInt(arr[9].trim());
            }

            // 解析 是否验证战斗
            if (!arr[10].trim().isEmpty()) {
              checkBattle = Integer.parseInt(arr[10].trim());
            }

            // 解析 备战阶段类型
            if (!arr[11].trim().isEmpty()) {
              prepareType = Integer.parseInt(arr[11].trim());
            }

            // 解析 战前准备时间（ms）
            if (!arr[12].trim().isEmpty()) {
              readyWaitTime = Integer.parseInt(arr[12].trim());
            }

            // 解析 关联的activityid
            if (!arr[13].trim().isEmpty()) {
              activityId = Integer.parseInt(arr[13].trim());
            }

            // 解析 默认上阵阵容
            if (!arr[14].trim().isEmpty()) {
              lineupNo = Integer.parseInt(arr[14].trim());
            }

            // 解析 上阵类型
            if (!arr[15].trim().isEmpty()) {
              lineupType = Integer.parseInt(arr[15].trim());
            }

            // 解析 指定NPC列表
            if (!arr[16].trim().isEmpty()) {
              appointedNPC = arr[16].trim();
            }

            // 解析 初始S能量
            if (!arr[17].trim().isEmpty()) {
              defaultSpEnergy = Integer.parseInt(arr[17].trim());
            }

            // 解析 S能量获取系数
            if (!arr[18].trim().isEmpty()) {
              spEnergyCoe = Integer.parseInt(arr[18].trim());
            }

            // 解析 初始能量
            if (!arr[19].trim().isEmpty()) {
              defaultEnergy = Integer.parseInt(arr[19].trim());
            }

            // 解析 能量点初始回复数值
            if (!arr[20].trim().isEmpty()) {
              defaultEnergyRecover = Integer.parseInt(arr[20].trim());
            }

            // 解析 能量点回复数值增量
            if (!arr[21].trim().isEmpty()) {
              defaultEnergyRaise = Integer.parseInt(arr[21].trim());
            }

            // 解析 能量点最大回复数值
            if (!arr[22].trim().isEmpty()) {
              defaultEnergyRecoverMax = Integer.parseInt(arr[22].trim());
            }

            // 解析 初始能量进度
            if (!arr[23].trim().isEmpty()) {
              defaultEnergyBar = Integer.parseInt(arr[23].trim());
            }

            // 解析 最多上阵数量
            if (!arr[24].trim().isEmpty()) {
              maxMember = Integer.parseInt(arr[24].trim());
            }

            // 解析 使用站位布点
            if (!arr[25].trim().isEmpty()) {
              sceneControlResource = Integer.parseInt(arr[25].trim());
            }

            // 解析 BOSS位置信息
            if (!arr[26].trim().isEmpty()) {
              bossPos = arr[26].trim();
            }

            // 解析 NPC类型
            if (!arr[27].trim().isEmpty()) {
              npcType = Integer.parseInt(arr[27].trim());
            }

            // 解析 场景NPC列表
            if (!arr[28].trim().isEmpty()) {
              sceneNpc_1 = arr[28].trim();
            }

            // 解析 场景NPC列表
            if (!arr[29].trim().isEmpty()) {
              sceneNpc_2 = arr[29].trim();
            }

            // 解析 场景NPC列表
            if (!arr[30].trim().isEmpty()) {
              sceneNpc_3 = arr[30].trim();
            }

            // 解析 场景NPC列表
            if (!arr[31].trim().isEmpty()) {
              sceneNpc_4 = arr[31].trim();
            }

            // 解析 场景NPC列表
            if (!arr[32].trim().isEmpty()) {
              sceneNpc_5 = arr[32].trim();
            }

            // 解析 角色站位信息
            if (!arr[33].trim().isEmpty()) {
              battlePosId = arr[33].trim();
            }

            // 解析 击飞位置
            if (!arr[34].trim().isEmpty()) {
              knockPosition_a = arr[34].trim();
            }

            // 解析 击飞位置
            if (!arr[35].trim().isEmpty()) {
              knockPosition_b = arr[35].trim();
            }

            // 解析 场景交互物列表
            if (!arr[36].trim().isEmpty()) {
              sceneObjects = arr[36].trim();
            }

            // 解析 Bonus组
            if (!arr[37].trim().isEmpty()) {
              bonusGroup = Integer.parseInt(arr[37].trim());
            }

            // 解析 固定Bonus组
            if (!arr[38].trim().isEmpty()) {
              fixedBonusGroup = Integer.parseInt(arr[38].trim());
            }

            // 解析 战场事件id
            if (!arr[39].trim().isEmpty()) {
              eventIds = arr[39].trim();
            }

            // 解析 换波处理
            if (!arr[40].trim().isEmpty()) {
              changeInfo = Integer.parseInt(arr[40].trim());
            }

            // 解析 结束条件
            if (!arr[41].trim().isEmpty()) {
              endInfo = arr[41].trim();
            }

            // 解析 关卡掉落
            if (!arr[42].trim().isEmpty()) {
              dropGroup = Integer.parseInt(arr[42].trim());
            }

            // 解析 关卡评分类型
            if (!arr[43].trim().isEmpty()) {
              starType = Integer.parseInt(arr[43].trim());
            }

            // 解析 关卡评分list
            if (!arr[44].trim().isEmpty()) {
              starList = arr[44].trim();
            }

            // 解析 关卡掉落list
            if (!arr[45].trim().isEmpty()) {
              dropList = arr[45].trim();
            }

            // 解析 关卡经验
            if (!arr[46].trim().isEmpty()) {
              exp = Integer.parseInt(arr[46].trim());
            }

            // 解析 关卡金币
            if (!arr[47].trim().isEmpty()) {
              gold = Integer.parseInt(arr[47].trim());
            }

            // 解析 首通掉落
            if (!arr[48].trim().isEmpty()) {
              firstDrop = Integer.parseInt(arr[48].trim());
            }

            // 解析 埼玉召唤类型
            if (!arr[49].trim().isEmpty()) {
              summonType = Integer.parseInt(arr[49].trim());
            }

            // 解析 场景技能
            if (!arr[50].trim().isEmpty()) {
              sceneSkills = arr[50].trim();
            }

            // 解析 限时挑战触发概率
            if (!arr[51].trim().isEmpty()) {
              triggerPro1 = Integer.parseInt(arr[51].trim());
            }

            // 解析 限时挑战触发事件
            if (!arr[52].trim().isEmpty()) {
              triggerStage1 = arr[52].trim();
            }

            // 解析 大体力玩法触发概率
            if (!arr[53].trim().isEmpty()) {
              triggerPro2 = Integer.parseInt(arr[53].trim());
            }

            // 解析 限时挑战触发事件
            if (!arr[54].trim().isEmpty()) {
              triggerStage2 = arr[54].trim();
            }

            // 解析 光照方向
            if (!arr[55].trim().isEmpty()) {
              light = Integer.parseInt(arr[55].trim());
            }

            // 解析 场景偏移
            if (!arr[56].trim().isEmpty()) {
              scenePos = arr[56].trim();
            }

            // 解析 关卡提示tips
            if (!arr[57].trim().isEmpty()) {
              scenePoint = arr[57].trim();
            }

            // 解析 关卡机制提示类型
            if (!arr[58].trim().isEmpty()) {
              hintType = arr[58].trim();
            }

            // 解析 关卡机制提示
            if (!arr[59].trim().isEmpty()) {
              sceneHint = arr[59].trim();
            }

            // 解析 关卡提示图片
            if (!arr[60].trim().isEmpty()) {
              hintPic = arr[60].trim();
            }

            // 解析 是否有关卡结算展示
            if (!arr[61].trim().isEmpty()) {
              endShow = arr[61].trim();
            }

            // 解析 是否强制N倍速
            if (!arr[62].trim().isEmpty()) {
              isCloseSpeed = Integer.parseInt(arr[62].trim());
            }

            // 解析 是否强制手动战斗
            if (!arr[63].trim().isEmpty()) {
              isCloseAuto = arr[63].trim();
            }

            // 解析 是否强制开启演出
            if (!arr[64].trim().isEmpty()) {
              isClosePerformance = arr[64].trim();
            }

            // 解析 是否开启特殊UI边缘
            if (!arr[65].trim().isEmpty()) {
              isOpenSpecialUi = Integer.parseInt(arr[65].trim());
            }

            // 解析 开始战斗的特殊UI展示类型（波次,UI类型|波次,UI类型）
            if (!arr[66].trim().isEmpty()) {
              battleStartUiTpye = arr[66].trim();
            }

            // 解析 是否显示我方血条
            if (!arr[67].trim().isEmpty()) {
              showSelfBlood = Integer.parseInt(arr[67].trim());
            }

            // 解析 失败是否再次挑战
            if (!arr[68].trim().isEmpty()) {
              tryAgain = Integer.parseInt(arr[68].trim());
            }

            // 解析 等待时间管理id
            if (!arr[69].trim().isEmpty()) {
              sceneTimeId = Integer.parseInt(arr[69].trim());
            }

            // 解析 是否显示战斗详情
            if (!arr[70].trim().isEmpty()) {
              showDetail = arr[70].trim();
            }

            // 解析 战前换阵容时间限制（单位ms）
            if (!arr[71].trim().isEmpty()) {
              changeHeroTime = Integer.parseInt(arr[71].trim());
            }

            // 解析 时间是否共用
            if (!arr[72].trim().isEmpty()) {
              isShare = Integer.parseInt(arr[72].trim());
            }

            // 解析 攻略ID
            if (!arr[73].trim().isEmpty()) {
              strategy = Integer.parseInt(arr[73].trim());
            }

            // 解析 是否自动保存阵容
            if (!arr[74].trim().isEmpty()) {
              autoSaveLineup = Integer.parseInt(arr[74].trim());
            }

            // 解析 是否试玩关卡
            if (!arr[75].trim().isEmpty()) {
              isTryPlay = Integer.parseInt(arr[75].trim());
            }

            // 解析 是否试玩关卡
            if (!arr[76].trim().isEmpty()) {
              isChangeSkin = Integer.parseInt(arr[76].trim());
            }

            // 解析 托管默认集火目标
            if (!arr[77].trim().isEmpty()) {
              blockBattleConvergeId = arr[77].trim();
            }

          } catch (Exception e) {
            logger.error(String.format("解析配置 %s 表, 字符串:%s 报错，请检查:%s", fileName, rowText, e.getMessage()));
            throw new ConfigLoadException("Error parsing config file :" + fileName);
          }
          SceneInfoConfig config = new SceneInfoConfig(id, sceneName, sceneResource, levelRequire, sceneRequire, returnStamina, startPerformance, sceneBGM, sceneType, isClient, checkBattle, prepareType, readyWaitTime, activityId, lineupNo, lineupType, appointedNPC, defaultSpEnergy, spEnergyCoe, defaultEnergy, defaultEnergyRecover, defaultEnergyRaise, defaultEnergyRecoverMax, defaultEnergyBar, maxMember, sceneControlResource, bossPos, npcType, sceneNpc_1, sceneNpc_2, sceneNpc_3, sceneNpc_4, sceneNpc_5, battlePosId, knockPosition_a, knockPosition_b, sceneObjects, bonusGroup, fixedBonusGroup, eventIds, changeInfo, endInfo, dropGroup, starType, starList, dropList, exp, gold, firstDrop, summonType, sceneSkills, triggerPro1, triggerStage1, triggerPro2, triggerStage2, light, scenePos, scenePoint, hintType, sceneHint, hintPic, endShow, isCloseSpeed, isCloseAuto, isClosePerformance, isOpenSpecialUi, battleStartUiTpye, showSelfBlood, tryAgain, sceneTimeId, showDetail, changeHeroTime, isShare, strategy, autoSaveLineup, isTryPlay, isChangeSkin, blockBattleConvergeId);
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

    public List<SceneInfoConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, SceneInfoConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "sceneInfo.txt";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
