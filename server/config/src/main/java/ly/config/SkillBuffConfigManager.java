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
 * File: SkillBuffConfigManager
 */
public class SkillBuffConfigManager implements InterfaceConfigManagerProxy {
  AtomicBoolean switched = new AtomicBoolean(false);
  private static final SkillBuffConfigManager instance = new SkillBuffConfigManager();
  private static final SkillBuffConfigManagerImpl instanceImplA =
      new SkillBuffConfigManagerImpl();
  private static final SkillBuffConfigManagerImpl instanceImplB =
      new SkillBuffConfigManagerImpl();

  public boolean isSwitched() {
    return switched.get();
  }

  public static SkillBuffConfigManagerImpl getInstance() {
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

  public static class SkillBuffConfigManagerImpl extends AbstractConfigManger {

    List<SkillBuffConfig> configList = new ArrayList<SkillBuffConfig>();
    Map<Integer, SkillBuffConfig> configMap = new HashMap<Integer, SkillBuffConfig>();


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
          SkillBuffConfig config = new SkillBuffConfig();
          try {
            //解析 编号
            if (!arr[0].trim().isEmpty()) {
            config.id =  Integer.parseInt(arr[0].trim());
            }

            //解析 效果类型
            if (!arr[1].trim().isEmpty()) {
            config.effectType =  Integer.parseInt(arr[1].trim());
            }

            //解析 作用目标类型
            if (!arr[2].trim().isEmpty()) {
            config.targetType = arr[2].trim();
            }

            //解析 效果名称
            if (!arr[3].trim().isEmpty()) {
            config.name = arr[3].trim();
            }

            //解析 效果描述
            if (!arr[4].trim().isEmpty()) {
            config.description = arr[4].trim();
            }

            //解析 效果图标
            if (!arr[5].trim().isEmpty()) {
            config.icon =  Integer.parseInt(arr[5].trim());
            }

            //解析 源核触发显示id
            if (!arr[6].trim().isEmpty()) {
            config.suitId = arr[6].trim();
            }

            //解析 是否显示
            if (!arr[7].trim().isEmpty()) {
            config.display =  Integer.parseInt(arr[7].trim());
            }

            //解析 显示类型
            if (!arr[8].trim().isEmpty()) {
            config.displayType =  Integer.parseInt(arr[8].trim());
            }

            //解析 对死亡目标处理
            if (!arr[9].trim().isEmpty()) {
            config.targetTypeEx =  Integer.parseInt(arr[9].trim());
            }

            //解析 目标属性筛选
            if (!arr[10].trim().isEmpty()) {
            config.targetTypeEx2 = arr[10].trim();
            }

            //解析 归属技能组
            if (!arr[11].trim().isEmpty()) {
            config.originSkillGroupId =  Integer.parseInt(arr[11].trim());
            }

            //解析 封被动类型
            if (!arr[12].trim().isEmpty()) {
            config.banPassiveType =  Integer.parseInt(arr[12].trim());
            }

            //解析 是否为被动
            if (!arr[13].trim().isEmpty()) {
            config.isPassive =  Integer.parseInt(arr[13].trim());
            }

            //解析 是否为源核
            if (!arr[14].trim().isEmpty()) {
            config.isCircuit =  Integer.parseInt(arr[14].trim());
            }

            //解析 是否触发源核
            if (!arr[15].trim().isEmpty()) {
            config.isTriggerCircuit =  Integer.parseInt(arr[15].trim());
            }

            //解析 是否触发被动技能
            if (!arr[16].trim().isEmpty()) {
            config.isTriggerPassiveSkill =  Integer.parseInt(arr[16].trim());
            }

            //解析 释放者击飞buff能否触发
            if (!arr[17].trim().isEmpty()) {
            config.casterStrikeFlyTriggerFlag =  Integer.parseInt(arr[17].trim());
            }

            //解析 依附者击飞buff能否触发
            if (!arr[18].trim().isEmpty()) {
            config.targetStrikeFlyTriggerFlag =  Integer.parseInt(arr[18].trim());
            }

            //解析 依附者击飞buff能否添加
            if (!arr[19].trim().isEmpty()) {
            config.targetStrikeFlyAddFlag =  Integer.parseInt(arr[19].trim());
            }

            //解析 互斥类型
            if (!arr[20].trim().isEmpty()) {
            config.mutexType =  Integer.parseInt(arr[20].trim());
            }

            //解析 互斥优先级
            if (!arr[21].trim().isEmpty()) {
            config.mutexPriority =  Integer.parseInt(arr[21].trim());
            }

            //解析 伤害类型
            if (!arr[22].trim().isEmpty()) {
            config.rangeType =  Integer.parseInt(arr[22].trim());
            }

            //解析 buff影响
            if (!arr[23].trim().isEmpty()) {
            config.buffInfluence = arr[23].trim();
            }

            //解析 初始层数
            if (!arr[24].trim().isEmpty()) {
            config.initStack =  Integer.parseInt(arr[24].trim());
            }

            //解析 基础层数
            if (!arr[25].trim().isEmpty()) {
            config.baseStack =  Integer.parseInt(arr[25].trim());
            }

            //解析 最大叠加层数
            if (!arr[26].trim().isEmpty()) {
            config.maxStack =  Integer.parseInt(arr[26].trim());
            }

            //解析 是否多个共存
            if (!arr[27].trim().isEmpty()) {
            config.coexist =  Integer.parseInt(arr[27].trim());
            }

            //解析 是否叠层刷新
            if (!arr[28].trim().isEmpty()) {
            config.refreshStack =  Integer.parseInt(arr[28].trim());
            }

            //解析 效果添加类型
            if (!arr[29].trim().isEmpty()) {
            config.addProType =  Integer.parseInt(arr[29].trim());
            }

            //解析 效果添加几率
            if (!arr[30].trim().isEmpty()) {
            config.addPro =  Integer.parseInt(arr[30].trim());
            }

            //解析 效果持续类型
            if (!arr[31].trim().isEmpty()) {
            config.continuousType =  Integer.parseInt(arr[31].trim());
            }

            //解析 效果持续量
            if (!arr[32].trim().isEmpty()) {
            config.continuousValue = arr[32].trim();
            }

            //解析 效果生效时机
            if (!arr[33].trim().isEmpty()) {
            config.triggerType =  Integer.parseInt(arr[33].trim());
            }

            //解析 生效间隔
            if (!arr[34].trim().isEmpty()) {
            config.triggerInterval =  Integer.parseInt(arr[34].trim());
            }

            //解析 效果生效几率
            if (!arr[35].trim().isEmpty()) {
            config.triggerPro =  Integer.parseInt(arr[35].trim());
            }

            //解析 单回合触发上限
            if (!arr[36].trim().isEmpty()) {
            config.triggerLimitPerTurn =  Integer.parseInt(arr[36].trim());
            }

            //解析 效果生效消耗层数
            if (!arr[37].trim().isEmpty()) {
            config.triggerConsumeStack =  Integer.parseInt(arr[37].trim());
            }

            //解析 生效标记过滤
            if (!arr[38].trim().isEmpty()) {
            config.entityTagFilters = arr[38].trim();
            }

            //解析 效果中断时机
            if (!arr[39].trim().isEmpty()) {
            config.breakType = arr[39].trim();
            }

            //解析 中断后续效果几率
            if (!arr[40].trim().isEmpty()) {
            config.breakEffectPro =  Integer.parseInt(arr[40].trim());
            }

            //解析 中断后续效果
            if (!arr[41].trim().isEmpty()) {
            config.breakEffects = arr[41].trim();
            }

            //解析 结束后续效果
            if (!arr[42].trim().isEmpty()) {
            config.endEffects = arr[42].trim();
            }

            //解析 结束后续效果
            if (!arr[43].trim().isEmpty()) {
            config.consumeEffects = arr[43].trim();
            }

            //解析 s能量获取系数
            if (!arr[44].trim().isEmpty()) {
            config.spCoa1 =  Integer.parseInt(arr[44].trim());
            }

            //解析 s能量获取系数
            if (!arr[45].trim().isEmpty()) {
            config.spCoa2 =  Integer.parseInt(arr[45].trim());
            }

            //解析 效果开始表现
            if (!arr[46].trim().isEmpty()) {
            config.startPerformance = arr[46].trim();
            }

            //解析 效果持续表现
            if (!arr[47].trim().isEmpty()) {
            config.runPerformance = arr[47].trim();
            }

            //解析 效果中断表现
            if (!arr[48].trim().isEmpty()) {
            config.breakPerformance = arr[48].trim();
            }

            //解析 效果未命中表现
            if (!arr[49].trim().isEmpty()) {
            config.missPerformance = arr[49].trim();
            }

            //解析 效果结束表现
            if (!arr[50].trim().isEmpty()) {
            config.endPerformance = arr[50].trim();
            }

            //解析 触发时长
            if (!arr[51].trim().isEmpty()) {
            config.triggerLength =  Float.parseFloat(arr[51].trim());
            }

            //解析 效果参数
            if (!arr[52].trim().isEmpty()) {
            config.param_1 = arr[52].trim();
            }

            //解析 效果参数
            if (!arr[53].trim().isEmpty()) {
            config.param_2 = arr[53].trim();
            }

            //解析 效果参数
            if (!arr[54].trim().isEmpty()) {
            config.param_3 = arr[54].trim();
            }

            //解析 效果参数
            if (!arr[55].trim().isEmpty()) {
            config.param_4 = arr[55].trim();
            }

            //解析 效果参数
            if (!arr[56].trim().isEmpty()) {
            config.param_5 = arr[56].trim();
            }

            //解析 效果参数
            if (!arr[57].trim().isEmpty()) {
            config.param_6 = arr[57].trim();
            }

            //解析 效果参数
            if (!arr[58].trim().isEmpty()) {
            config.param_7 = arr[58].trim();
            }

            //解析 效果参数
            if (!arr[59].trim().isEmpty()) {
            config.param_8 = arr[59].trim();
            }

            //解析 效果参数
            if (!arr[60].trim().isEmpty()) {
            config.param_9 = arr[60].trim();
            }

            //解析 效果参数
            if (!arr[61].trim().isEmpty()) {
            config.param_10 = arr[61].trim();
            }

            //解析 英雄id
            if (!arr[62].trim().isEmpty()) {
            config.heroId =  Integer.parseInt(arr[62].trim());
            }

            //解析 技能序列
            if (!arr[63].trim().isEmpty()) {
            config.skillSequence =  Integer.parseInt(arr[63].trim());
            }

            //解析 效果序列
            if (!arr[64].trim().isEmpty()) {
            config.effectSequence =  Integer.parseInt(arr[64].trim());
            }

            //解析 是否下次战斗保留
            if (!arr[65].trim().isEmpty()) {
            config.isReAdd =  Integer.parseInt(arr[65].trim());
            }

            //解析 特效恢复信息
            if (!arr[66].trim().isEmpty()) {
            config.effectRenew = arr[66].trim();
            }

            //解析 模型展示
            if (!arr[67].trim().isEmpty()) {
            config.modelPerformance = arr[67].trim();
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

    public List<SkillBuffConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, SkillBuffConfig> getConfigMap() {
      return configMap;
    }
    @Override
    public String getConfigFileName() {
      return "skillBuff.txt";
    }

    // @@@@@自定义方法开始区@@@@@
    @Override
    protected void afterLoad() {

    }

    // @@@@@自定义方法结束区@@@@@
  }
}
