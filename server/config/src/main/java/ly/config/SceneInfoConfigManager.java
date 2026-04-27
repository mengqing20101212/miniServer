package ly.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import ly.utils.KV;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import ly.AbstractConfigManger;
import ly.ConfigLoadException;
import ly.InterfaceConfigManagerProxy;
import org.slf4j.Logger;

/*
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 * File: SceneInfoConfigManager
 */
public class SceneInfoConfigManager implements InterfaceConfigManagerProxy {
  AtomicBoolean switched = new AtomicBoolean(false);
  private static final SceneInfoConfigManager instance = new SceneInfoConfigManager();
  private static final SceneInfoConfigManagerImpl instanceImplA =
      new SceneInfoConfigManagerImpl();
  private static final SceneInfoConfigManagerImpl instanceImplB =
      new SceneInfoConfigManagerImpl();

  public boolean isSwitched() {
    return switched.get();
  }

  public static SceneInfoConfigManagerImpl getInstance() {
    if (instance.isSwitched()) {
      return instanceImplA;
    } else {
      return instanceImplB;
    }
  }

  @Override
  public void loadConfig(Logger logger, String configDir) throws ConfigLoadException {
    getInstance().reload(logger, configDir);
  }

  public static class SceneInfoConfigManagerImpl extends AbstractConfigManger {

    List<SceneInfoConfig> configList = new ArrayList<SceneInfoConfig>();
    Map<Integer, SceneInfoConfig> configMap = new HashMap<Integer, SceneInfoConfig>();


    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    protected void reload(Logger logger, String configDir) throws ConfigLoadException {
      String fileName = configDir + File.separator + getConfigFileName();
      File file = new File(fileName);
      clear();
      if (!file.exists()) {
        logger.error(fileName + " does not exist");
        throw new ConfigLoadException("Config file does not exist :" + fileName);
      }
      try (BufferedReader br = new BufferedReader(new FileReader(file))) {
        String line;
        br.readLine(); //先读取一行表头 
        while ((line = br.readLine()) != null) { // 按行读取
          String[] arr = line.split("\t");
          SceneInfoConfig config = new SceneInfoConfig();
          try {
            //解析 编号
            if (!arr[0].trim().isEmpty()) {
            config.id =  Integer.parseInt(arr[0].trim());
            }

            //解析 场景名称
            if (!arr[1].trim().isEmpty()) {
            config.sceneName = arr[1].trim();
            }

            //解析 场景资源
            if (!arr[2].trim().isEmpty()) {
            config.sceneResource = arr[2].trim();
            }

            //解析 进入等级（作废）
            if (!arr[3].trim().isEmpty()) {
            config.levelRequire =  Integer.parseInt(arr[3].trim());
            }

            //解析 前置关卡（作废）
            if (!arr[4].trim().isEmpty()) {
            config.sceneRequire = arr[4].trim();
            }

            //解析 失败体力返还（作废）
            if (!arr[5].trim().isEmpty()) {
            config.returnStamina =  Integer.parseInt(arr[5].trim());
            }

            //解析 进场动画
            if (!arr[6].trim().isEmpty()) {
            config.startPerformance = arr[6].trim();
            }

            //解析 场景音乐
            if (!arr[7].trim().isEmpty()) {
            config.sceneBGM =  Integer.parseInt(arr[7].trim());
            }

            //解析 关卡类型
            if (!arr[8].trim().isEmpty()) {
            config.sceneType =  Integer.parseInt(arr[8].trim());
            }

            //解析 是否只是前端运算
            if (!arr[9].trim().isEmpty()) {
            config.isClient =  Integer.parseInt(arr[9].trim());
            }

            //解析 是否验证战斗
            if (!arr[10].trim().isEmpty()) {
            config.checkBattle =  Integer.parseInt(arr[10].trim());
            }

            //解析 备战阶段类型
            if (!arr[11].trim().isEmpty()) {
            config.prepareType =  Integer.parseInt(arr[11].trim());
            }

            //解析 战前准备时间（ms）
            if (!arr[12].trim().isEmpty()) {
            config.readyWaitTime =  Integer.parseInt(arr[12].trim());
            }

            //解析 关联的activityid
            if (!arr[13].trim().isEmpty()) {
            config.activityId =  Integer.parseInt(arr[13].trim());
            }

            //解析 默认上阵阵容
            if (!arr[14].trim().isEmpty()) {
            config.lineupNo =  Integer.parseInt(arr[14].trim());
            }

            //解析 上阵类型
            if (!arr[15].trim().isEmpty()) {
            config.lineupType =  Integer.parseInt(arr[15].trim());
            }

            //解析 指定NPC列表
            if (!arr[16].trim().isEmpty()) {
            config.appointedNPC = arr[16].trim();
            }

            //解析 初始S能量
            if (!arr[17].trim().isEmpty()) {
            config.defaultSpEnergy =  Integer.parseInt(arr[17].trim());
            }

            //解析 S能量获取系数
            if (!arr[18].trim().isEmpty()) {
            config.spEnergyCoe =  Integer.parseInt(arr[18].trim());
            }

            //解析 初始能量
            if (!arr[19].trim().isEmpty()) {
            config.defaultEnergy =  Integer.parseInt(arr[19].trim());
            }

            //解析 能量点初始回复数值
            if (!arr[20].trim().isEmpty()) {
            config.defaultEnergyRecover =  Integer.parseInt(arr[20].trim());
            }

            //解析 能量点回复数值增量
            if (!arr[21].trim().isEmpty()) {
            config.defaultEnergyRaise =  Integer.parseInt(arr[21].trim());
            }

            //解析 能量点最大回复数值
            if (!arr[22].trim().isEmpty()) {
            config.defaultEnergyRecoverMax =  Integer.parseInt(arr[22].trim());
            }

            //解析 初始能量进度
            if (!arr[23].trim().isEmpty()) {
            config.defaultEnergyBar =  Integer.parseInt(arr[23].trim());
            }

            //解析 最多上阵数量
            if (!arr[24].trim().isEmpty()) {
            config.maxMember =  Integer.parseInt(arr[24].trim());
            }

            //解析 使用站位布点
            if (!arr[25].trim().isEmpty()) {
            config.sceneControlResource =  Integer.parseInt(arr[25].trim());
            }

            //解析 BOSS位置信息
            if (!arr[26].trim().isEmpty()) {
            config.bossPos = arr[26].trim();
            }

            //解析 NPC类型
            if (!arr[27].trim().isEmpty()) {
            config.npcType =  Integer.parseInt(arr[27].trim());
            }

            //解析 场景NPC列表
            if (!arr[28].trim().isEmpty()) {
            config.sceneNpc_1 = arr[28].trim();
            }

            //解析 场景NPC列表
            if (!arr[29].trim().isEmpty()) {
            config.sceneNpc_2 = arr[29].trim();
            }

            //解析 场景NPC列表
            if (!arr[30].trim().isEmpty()) {
            config.sceneNpc_3 = arr[30].trim();
            }

            //解析 场景NPC列表
            if (!arr[31].trim().isEmpty()) {
            config.sceneNpc_4 = arr[31].trim();
            }

            //解析 场景NPC列表
            if (!arr[32].trim().isEmpty()) {
            config.sceneNpc_5 = arr[32].trim();
            }

            //解析 角色站位信息
            if (!arr[33].trim().isEmpty()) {
            config.battlePosId = arr[33].trim();
            }

            //解析 击飞位置
            if (!arr[34].trim().isEmpty()) {
            config.knockPosition_a = arr[34].trim();
            }

            //解析 击飞位置
            if (!arr[35].trim().isEmpty()) {
            config.knockPosition_b = arr[35].trim();
            }

            //解析 场景交互物列表
            if (!arr[36].trim().isEmpty()) {
            config.sceneObjects = arr[36].trim();
            }

            //解析 Bonus组
            if (!arr[37].trim().isEmpty()) {
            config.bonusGroup =  Integer.parseInt(arr[37].trim());
            }

            //解析 固定Bonus组
            if (!arr[38].trim().isEmpty()) {
            config.fixedBonusGroup =  Integer.parseInt(arr[38].trim());
            }

            //解析 战场事件id
            if (!arr[39].trim().isEmpty()) {
            config.eventIds = arr[39].trim();
            }

            //解析 换波处理
            if (!arr[40].trim().isEmpty()) {
            config.changeInfo =  Integer.parseInt(arr[40].trim());
            }

            //解析 结束条件
            if (!arr[41].trim().isEmpty()) {
            config.endInfo = arr[41].trim();
            }

            //解析 关卡掉落
            if (!arr[42].trim().isEmpty()) {
            config.dropGroup =  Integer.parseInt(arr[42].trim());
            }

            //解析 关卡评分类型
            if (!arr[43].trim().isEmpty()) {
            config.starType =  Integer.parseInt(arr[43].trim());
            }

            //解析 关卡评分list
            if (!arr[44].trim().isEmpty()) {
            config.starList = arr[44].trim();
            }

            //解析 关卡掉落list
            if (!arr[45].trim().isEmpty()) {
            config.dropList = arr[45].trim();
            }

            //解析 关卡经验
            if (!arr[46].trim().isEmpty()) {
            config.exp =  Integer.parseInt(arr[46].trim());
            }

            //解析 关卡金币
            if (!arr[47].trim().isEmpty()) {
            config.gold =  Integer.parseInt(arr[47].trim());
            }

            //解析 首通掉落
            if (!arr[48].trim().isEmpty()) {
            config.firstDrop =  Integer.parseInt(arr[48].trim());
            }

            //解析 埼玉召唤类型
            if (!arr[49].trim().isEmpty()) {
            config.summonType =  Integer.parseInt(arr[49].trim());
            }

            //解析 场景技能
            if (!arr[50].trim().isEmpty()) {
            config.sceneSkills = arr[50].trim();
            }

            //解析 限时挑战触发概率
            if (!arr[51].trim().isEmpty()) {
            config.triggerPro1 =  Integer.parseInt(arr[51].trim());
            }

            //解析 限时挑战触发事件
            if (!arr[52].trim().isEmpty()) {
            config.triggerStage1 = arr[52].trim();
            }

            //解析 大体力玩法触发概率
            if (!arr[53].trim().isEmpty()) {
            config.triggerPro2 =  Integer.parseInt(arr[53].trim());
            }

            //解析 限时挑战触发事件
            if (!arr[54].trim().isEmpty()) {
            config.triggerStage2 = arr[54].trim();
            }

            //解析 光照方向
            if (!arr[55].trim().isEmpty()) {
            config.light =  Integer.parseInt(arr[55].trim());
            }

            //解析 场景偏移
            if (!arr[56].trim().isEmpty()) {
            config.scenePos = arr[56].trim();
            }

            //解析 关卡提示tips
            if (!arr[57].trim().isEmpty()) {
            config.scenePoint = null;
            }

            //解析 关卡机制提示类型
            if (!arr[58].trim().isEmpty()) {
            config.hintType = arr[58].trim();
            }

            //解析 关卡机制提示
            if (!arr[59].trim().isEmpty()) {
            config.sceneHint = arr[59].trim();
            }

            //解析 关卡提示图片
            if (!arr[60].trim().isEmpty()) {
            config.hintPic = arr[60].trim();
            }

            //解析 是否有关卡结算展示
            if (!arr[61].trim().isEmpty()) {
            config.endShow = null;
            }

            //解析 是否强制N倍速
            if (!arr[62].trim().isEmpty()) {
            config.isCloseSpeed =  Integer.parseInt(arr[62].trim());
            }

            //解析 是否强制手动战斗
            if (!arr[63].trim().isEmpty()) {
            config.isCloseAuto = null;
            }

            //解析 是否强制开启演出
            if (!arr[64].trim().isEmpty()) {
            config.isClosePerformance = null;
            }

            //解析 是否开启特殊UI边缘
            if (!arr[65].trim().isEmpty()) {
            config.isOpenSpecialUi =  Integer.parseInt(arr[65].trim());
            }

            //解析 开始战斗的特殊UI展示类型（波次,UI类型|波次,UI类型）
            if (!arr[66].trim().isEmpty()) {
            config.battleStartUiTpye = arr[66].trim();
            }

            //解析 是否显示我方血条
            if (!arr[67].trim().isEmpty()) {
            config.showSelfBlood =  Integer.parseInt(arr[67].trim());
            }

            //解析 失败是否再次挑战
            if (!arr[68].trim().isEmpty()) {
            config.tryAgain =  Integer.parseInt(arr[68].trim());
            }

            //解析 等待时间管理id
            if (!arr[69].trim().isEmpty()) {
            config.sceneTimeId =  Integer.parseInt(arr[69].trim());
            }

            //解析 是否显示战斗详情
            if (!arr[70].trim().isEmpty()) {
            config.showDetail = arr[70].trim();
            }

            //解析 战前换阵容时间限制（单位ms）
            if (!arr[71].trim().isEmpty()) {
            config.changeHeroTime =  Integer.parseInt(arr[71].trim());
            }

            //解析 时间是否共用
            if (!arr[72].trim().isEmpty()) {
            config.isShare =  Integer.parseInt(arr[72].trim());
            }

            //解析 攻略ID
            if (!arr[73].trim().isEmpty()) {
            config.strategy =  Integer.parseInt(arr[73].trim());
            }

            //解析 是否自动保存阵容
            if (!arr[74].trim().isEmpty()) {
            config.autoSaveLineup =  Integer.parseInt(arr[74].trim());
            }

            //解析 是否试玩关卡
            if (!arr[75].trim().isEmpty()) {
            config.isTryPlay =  Integer.parseInt(arr[75].trim());
            }

            //解析 是否试玩关卡
            if (!arr[76].trim().isEmpty()) {
            config.isChangeSkin =  Integer.parseInt(arr[76].trim());
            }

            //解析 托管默认集火目标
            if (!arr[77].trim().isEmpty()) {
            config.blockBattleConvergeId = arr[77].trim();
            }


          } catch (Exception e) {
            logger.error(
                String.format("解析配置 %s 表, 字符串:%s 报错，请检查:%s", fileName, line, e.getMessage()));
            e.printStackTrace();
            throw new ConfigLoadException("Error parsing config file :" + fileName);
          }
          config.afterLoad();
          configList.add(config);
          configMap.put(config.id, config);
        }
        afterLoad();
      } catch (IOException e) {
        e.printStackTrace();
        throw new ConfigLoadException("Config file could not be read :" + fileName);
      }
    }

    @Override
    protected void clear() {

      configList.clear();
      configMap.clear();

      // @@@@@自定义clear方法开始区@@@@@


      // @@@@@自定义clear方法结束区@@@@@
    }

    private List<Integer> parseIntList(String value) {
      if (value == null || value.trim().isEmpty()) {
        return new ArrayList<>();
      }
      String[] parts = value.split(",");
      List<Integer> result = new ArrayList<>();
      for (String part : parts) {
        try {
          result.add(Integer.parseInt(part.trim()));
        } catch (NumberFormatException e) {
          // 如果不是数字，则跳过
        }
      }
      return result;
    }

    private List<KV<Integer, Integer>> parseIntKVList(String value) {
      if (value == null || value.trim().isEmpty()) {
        return new ArrayList<>();
      }
      List<KV<Integer, Integer>> result = new ArrayList<>();
      String[] pairs = value.split(",");
      for (String pair : pairs) {
        pair = pair.trim();
        if (!pair.isEmpty()) {
          int idx = pair.indexOf(":");
          if (idx > 0) {
            String keyStr = pair.substring(0, idx).trim();
            String valueStr = pair.substring(idx + 1).trim();
            try {
              Integer key = Integer.parseInt(keyStr);
              Integer val = Integer.parseInt(valueStr);
              result.add(new KV<>(key, val));
            } catch (NumberFormatException e) {
              // 如果不是数字，则跳过
            }
          }
        }
      }
      return result;
    }

    private List<KV<String, String>> parseStringKVList(String value) {
      if (value == null || value.trim().isEmpty()) {
        return new ArrayList<>();
      }
      List<KV<String, String>> result = new ArrayList<>();
      String[] pairs = value.split(",");
      for (String pair : pairs) {
        pair = pair.trim();
        if (!pair.isEmpty()) {
          int idx = pair.indexOf(":");
          if (idx > 0) {
            String keyStr = pair.substring(0, idx).trim();
            String valueStr = pair.substring(idx + 1).trim();
            result.add(new KV<>(keyStr, valueStr));
          }
        }
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
