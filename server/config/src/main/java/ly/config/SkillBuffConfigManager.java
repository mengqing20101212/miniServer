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
 * File: SkillBuffConfigManager
 */
public class SkillBuffConfigManager implements InterfaceConfigManagerProxy {
  private static final AtomicBoolean switched = new AtomicBoolean(false);
  private static final SkillBuffConfigManager instance = new SkillBuffConfigManager();
  private static final SkillBuffConfigManagerImpl instanceImplA = new SkillBuffConfigManagerImpl();
  private static final SkillBuffConfigManagerImpl instanceImplB = new SkillBuffConfigManagerImpl();

  public static SkillBuffConfigManagerImpl getInstance() {
    return switched.get() ? instanceImplA : instanceImplB;
  }

  private static SkillBuffConfigManagerImpl getStandby() {
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
    SkillBuffConfigManagerImpl oldActive = getInstance();
    switched.set(!switched.get());
    return oldActive;
  }

  @Override
  public String getConfigFileName() {
    return getInstance().getConfigFileName();
  }

  public static class SkillBuffConfigManagerImpl extends AbstractConfigManger {
    private List<SkillBuffConfig> configList = List.of();
    private Map<Integer, SkillBuffConfig> configMap = Map.of();

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
      SkillBuffConfigChecker checker = new SkillBuffConfigChecker();
      checker.checkHeader(logger, configDir);
      List<SkillBuffConfig> newList = new ArrayList<>();
      Map<Integer, SkillBuffConfig> newMap = new HashMap<>();
      try (BufferedReader br = new BufferedReader(new FileReader(file))) {
        String rowText;
        br.readLine();
        br.readLine();
        while ((rowText = br.readLine()) != null) {
          if (rowText.isBlank()) { continue; }
          String[] arr = rowText.split("\\t", -1);
          if (arr.length < 68) {
            throw new ConfigLoadException("Config column size mismatch :" + fileName + ", line=" + rowText);
          }
          int id = 0;
          int effectType = 0;
          String targetType = null;
          String name = null;
          String description = null;
          int icon = 0;
          String suitId = null;
          int display = 0;
          int displayType = 0;
          int targetTypeEx = 0;
          String targetTypeEx2 = null;
          int originSkillGroupId = 0;
          int banPassiveType = 0;
          int isPassive = 0;
          int isCircuit = 0;
          int isTriggerCircuit = 0;
          int isTriggerPassiveSkill = 0;
          int casterStrikeFlyTriggerFlag = 0;
          int targetStrikeFlyTriggerFlag = 0;
          int targetStrikeFlyAddFlag = 0;
          int mutexType = 0;
          int mutexPriority = 0;
          int rangeType = 0;
          String buffInfluence = null;
          int initStack = 0;
          int baseStack = 0;
          int maxStack = 0;
          int coexist = 0;
          int refreshStack = 0;
          int addProType = 0;
          int addPro = 0;
          int continuousType = 0;
          String continuousValue = null;
          int triggerType = 0;
          int triggerInterval = 0;
          int triggerPro = 0;
          int triggerLimitPerTurn = 0;
          int triggerConsumeStack = 0;
          String entityTagFilters = null;
          String breakType = null;
          int breakEffectPro = 0;
          String breakEffects = null;
          String endEffects = null;
          String consumeEffects = null;
          int spCoa1 = 0;
          int spCoa2 = 0;
          String startPerformance = null;
          String runPerformance = null;
          String breakPerformance = null;
          String missPerformance = null;
          String endPerformance = null;
          float triggerLength = 0F;
          String param_1 = null;
          String param_2 = null;
          String param_3 = null;
          String param_4 = null;
          String param_5 = null;
          String param_6 = null;
          String param_7 = null;
          String param_8 = null;
          String param_9 = null;
          String param_10 = null;
          int heroId = 0;
          int skillSequence = 0;
          int effectSequence = 0;
          int isReAdd = 0;
          String effectRenew = null;
          String modelPerformance = null;
          try {
            // 解析 编号
            if (!arr[0].trim().isEmpty()) {
              id = Integer.parseInt(arr[0].trim());
            }

            // 解析 效果类型
            if (!arr[1].trim().isEmpty()) {
              effectType = Integer.parseInt(arr[1].trim());
            }

            // 解析 作用目标类型
            if (!arr[2].trim().isEmpty()) {
              targetType = arr[2].trim();
            }

            // 解析 效果名称
            if (!arr[3].trim().isEmpty()) {
              name = arr[3].trim();
            }

            // 解析 效果描述
            if (!arr[4].trim().isEmpty()) {
              description = arr[4].trim();
            }

            // 解析 效果图标
            if (!arr[5].trim().isEmpty()) {
              icon = Integer.parseInt(arr[5].trim());
            }

            // 解析 源核触发显示id
            if (!arr[6].trim().isEmpty()) {
              suitId = arr[6].trim();
            }

            // 解析 是否显示
            if (!arr[7].trim().isEmpty()) {
              display = Integer.parseInt(arr[7].trim());
            }

            // 解析 显示类型
            if (!arr[8].trim().isEmpty()) {
              displayType = Integer.parseInt(arr[8].trim());
            }

            // 解析 对死亡目标处理
            if (!arr[9].trim().isEmpty()) {
              targetTypeEx = Integer.parseInt(arr[9].trim());
            }

            // 解析 目标属性筛选
            if (!arr[10].trim().isEmpty()) {
              targetTypeEx2 = arr[10].trim();
            }

            // 解析 归属技能组
            if (!arr[11].trim().isEmpty()) {
              originSkillGroupId = Integer.parseInt(arr[11].trim());
            }

            // 解析 封被动类型
            if (!arr[12].trim().isEmpty()) {
              banPassiveType = Integer.parseInt(arr[12].trim());
            }

            // 解析 是否为被动
            if (!arr[13].trim().isEmpty()) {
              isPassive = Integer.parseInt(arr[13].trim());
            }

            // 解析 是否为源核
            if (!arr[14].trim().isEmpty()) {
              isCircuit = Integer.parseInt(arr[14].trim());
            }

            // 解析 是否触发源核
            if (!arr[15].trim().isEmpty()) {
              isTriggerCircuit = Integer.parseInt(arr[15].trim());
            }

            // 解析 是否触发被动技能
            if (!arr[16].trim().isEmpty()) {
              isTriggerPassiveSkill = Integer.parseInt(arr[16].trim());
            }

            // 解析 释放者击飞buff能否触发
            if (!arr[17].trim().isEmpty()) {
              casterStrikeFlyTriggerFlag = Integer.parseInt(arr[17].trim());
            }

            // 解析 依附者击飞buff能否触发
            if (!arr[18].trim().isEmpty()) {
              targetStrikeFlyTriggerFlag = Integer.parseInt(arr[18].trim());
            }

            // 解析 依附者击飞buff能否添加
            if (!arr[19].trim().isEmpty()) {
              targetStrikeFlyAddFlag = Integer.parseInt(arr[19].trim());
            }

            // 解析 互斥类型
            if (!arr[20].trim().isEmpty()) {
              mutexType = Integer.parseInt(arr[20].trim());
            }

            // 解析 互斥优先级
            if (!arr[21].trim().isEmpty()) {
              mutexPriority = Integer.parseInt(arr[21].trim());
            }

            // 解析 伤害类型
            if (!arr[22].trim().isEmpty()) {
              rangeType = Integer.parseInt(arr[22].trim());
            }

            // 解析 buff影响
            if (!arr[23].trim().isEmpty()) {
              buffInfluence = arr[23].trim();
            }

            // 解析 初始层数
            if (!arr[24].trim().isEmpty()) {
              initStack = Integer.parseInt(arr[24].trim());
            }

            // 解析 基础层数
            if (!arr[25].trim().isEmpty()) {
              baseStack = Integer.parseInt(arr[25].trim());
            }

            // 解析 最大叠加层数
            if (!arr[26].trim().isEmpty()) {
              maxStack = Integer.parseInt(arr[26].trim());
            }

            // 解析 是否多个共存
            if (!arr[27].trim().isEmpty()) {
              coexist = Integer.parseInt(arr[27].trim());
            }

            // 解析 是否叠层刷新
            if (!arr[28].trim().isEmpty()) {
              refreshStack = Integer.parseInt(arr[28].trim());
            }

            // 解析 效果添加类型
            if (!arr[29].trim().isEmpty()) {
              addProType = Integer.parseInt(arr[29].trim());
            }

            // 解析 效果添加几率
            if (!arr[30].trim().isEmpty()) {
              addPro = Integer.parseInt(arr[30].trim());
            }

            // 解析 效果持续类型
            if (!arr[31].trim().isEmpty()) {
              continuousType = Integer.parseInt(arr[31].trim());
            }

            // 解析 效果持续量
            if (!arr[32].trim().isEmpty()) {
              continuousValue = arr[32].trim();
            }

            // 解析 效果生效时机
            if (!arr[33].trim().isEmpty()) {
              triggerType = Integer.parseInt(arr[33].trim());
            }

            // 解析 生效间隔
            if (!arr[34].trim().isEmpty()) {
              triggerInterval = Integer.parseInt(arr[34].trim());
            }

            // 解析 效果生效几率
            if (!arr[35].trim().isEmpty()) {
              triggerPro = Integer.parseInt(arr[35].trim());
            }

            // 解析 单回合触发上限
            if (!arr[36].trim().isEmpty()) {
              triggerLimitPerTurn = Integer.parseInt(arr[36].trim());
            }

            // 解析 效果生效消耗层数
            if (!arr[37].trim().isEmpty()) {
              triggerConsumeStack = Integer.parseInt(arr[37].trim());
            }

            // 解析 生效标记过滤
            if (!arr[38].trim().isEmpty()) {
              entityTagFilters = arr[38].trim();
            }

            // 解析 效果中断时机
            if (!arr[39].trim().isEmpty()) {
              breakType = arr[39].trim();
            }

            // 解析 中断后续效果几率
            if (!arr[40].trim().isEmpty()) {
              breakEffectPro = Integer.parseInt(arr[40].trim());
            }

            // 解析 中断后续效果
            if (!arr[41].trim().isEmpty()) {
              breakEffects = arr[41].trim();
            }

            // 解析 结束后续效果
            if (!arr[42].trim().isEmpty()) {
              endEffects = arr[42].trim();
            }

            // 解析 结束后续效果
            if (!arr[43].trim().isEmpty()) {
              consumeEffects = arr[43].trim();
            }

            // 解析 s能量获取系数
            if (!arr[44].trim().isEmpty()) {
              spCoa1 = Integer.parseInt(arr[44].trim());
            }

            // 解析 s能量获取系数
            if (!arr[45].trim().isEmpty()) {
              spCoa2 = Integer.parseInt(arr[45].trim());
            }

            // 解析 效果开始表现
            if (!arr[46].trim().isEmpty()) {
              startPerformance = arr[46].trim();
            }

            // 解析 效果持续表现
            if (!arr[47].trim().isEmpty()) {
              runPerformance = arr[47].trim();
            }

            // 解析 效果中断表现
            if (!arr[48].trim().isEmpty()) {
              breakPerformance = arr[48].trim();
            }

            // 解析 效果未命中表现
            if (!arr[49].trim().isEmpty()) {
              missPerformance = arr[49].trim();
            }

            // 解析 效果结束表现
            if (!arr[50].trim().isEmpty()) {
              endPerformance = arr[50].trim();
            }

            // 解析 触发时长
            if (!arr[51].trim().isEmpty()) {
              triggerLength = Float.parseFloat(arr[51].trim());
            }

            // 解析 效果参数
            if (!arr[52].trim().isEmpty()) {
              param_1 = arr[52].trim();
            }

            // 解析 效果参数
            if (!arr[53].trim().isEmpty()) {
              param_2 = arr[53].trim();
            }

            // 解析 效果参数
            if (!arr[54].trim().isEmpty()) {
              param_3 = arr[54].trim();
            }

            // 解析 效果参数
            if (!arr[55].trim().isEmpty()) {
              param_4 = arr[55].trim();
            }

            // 解析 效果参数
            if (!arr[56].trim().isEmpty()) {
              param_5 = arr[56].trim();
            }

            // 解析 效果参数
            if (!arr[57].trim().isEmpty()) {
              param_6 = arr[57].trim();
            }

            // 解析 效果参数
            if (!arr[58].trim().isEmpty()) {
              param_7 = arr[58].trim();
            }

            // 解析 效果参数
            if (!arr[59].trim().isEmpty()) {
              param_8 = arr[59].trim();
            }

            // 解析 效果参数
            if (!arr[60].trim().isEmpty()) {
              param_9 = arr[60].trim();
            }

            // 解析 效果参数
            if (!arr[61].trim().isEmpty()) {
              param_10 = arr[61].trim();
            }

            // 解析 英雄id
            if (!arr[62].trim().isEmpty()) {
              heroId = Integer.parseInt(arr[62].trim());
            }

            // 解析 技能序列
            if (!arr[63].trim().isEmpty()) {
              skillSequence = Integer.parseInt(arr[63].trim());
            }

            // 解析 效果序列
            if (!arr[64].trim().isEmpty()) {
              effectSequence = Integer.parseInt(arr[64].trim());
            }

            // 解析 是否下次战斗保留
            if (!arr[65].trim().isEmpty()) {
              isReAdd = Integer.parseInt(arr[65].trim());
            }

            // 解析 特效恢复信息
            if (!arr[66].trim().isEmpty()) {
              effectRenew = arr[66].trim();
            }

            // 解析 模型展示
            if (!arr[67].trim().isEmpty()) {
              modelPerformance = arr[67].trim();
            }

          } catch (Exception e) {
            logger.error(String.format("解析配置 %s 表, 字符串:%s 报错，请检查:%s", fileName, rowText, e.getMessage()));
            throw new ConfigLoadException("Error parsing config file :" + fileName);
          }
          SkillBuffConfig config = new SkillBuffConfig(id, effectType, targetType, name, description, icon, suitId, display, displayType, targetTypeEx, targetTypeEx2, originSkillGroupId, banPassiveType, isPassive, isCircuit, isTriggerCircuit, isTriggerPassiveSkill, casterStrikeFlyTriggerFlag, targetStrikeFlyTriggerFlag, targetStrikeFlyAddFlag, mutexType, mutexPriority, rangeType, buffInfluence, initStack, baseStack, maxStack, coexist, refreshStack, addProType, addPro, continuousType, continuousValue, triggerType, triggerInterval, triggerPro, triggerLimitPerTurn, triggerConsumeStack, entityTagFilters, breakType, breakEffectPro, breakEffects, endEffects, consumeEffects, spCoa1, spCoa2, startPerformance, runPerformance, breakPerformance, missPerformance, endPerformance, triggerLength, param_1, param_2, param_3, param_4, param_5, param_6, param_7, param_8, param_9, param_10, heroId, skillSequence, effectSequence, isReAdd, effectRenew, modelPerformance);
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
