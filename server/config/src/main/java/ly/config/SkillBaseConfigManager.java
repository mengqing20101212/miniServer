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
 * File: SkillBaseConfigManager
 */
public class SkillBaseConfigManager implements InterfaceConfigManagerProxy {
  private static final AtomicBoolean switched = new AtomicBoolean(false);
  private static final SkillBaseConfigManager instance = new SkillBaseConfigManager();
  private static final SkillBaseConfigManagerImpl instanceImplA = new SkillBaseConfigManagerImpl();
  private static final SkillBaseConfigManagerImpl instanceImplB = new SkillBaseConfigManagerImpl();

  public static SkillBaseConfigManagerImpl getInstance() {
    return switched.get() ? instanceImplA : instanceImplB;
  }

  private static SkillBaseConfigManagerImpl getStandby() {
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
    SkillBaseConfigManagerImpl oldActive = getInstance();
    switched.set(!switched.get());
    return oldActive;
  }

  @Override
  public String getConfigFileName() {
    return getInstance().getConfigFileName();
  }

  public static class SkillBaseConfigManagerImpl extends AbstractConfigManger {
    private List<SkillBaseConfig> configList = List.of();
    private Map<Integer, SkillBaseConfig> configMap = Map.of();

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
      SkillBaseConfigChecker checker = new SkillBaseConfigChecker();
      checker.checkHeader(logger, configDir);
      List<SkillBaseConfig> newList = new ArrayList<>();
      Map<Integer, SkillBaseConfig> newMap = new HashMap<>();
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
          String name = null;
          String description = null;
          String upgradeDes = null;
          String detail = null;
          int bonusTrigger = 0;
          String popLocId = null;
          int isPopShow = 0;
          int popType = 0;
          int icon = 0;
          int showSGId = 0;
          int skillBase = 0;
          int skillGroupId = 0;
          int mutexType = 0;
          int priority = 0;
          int group = 0;
          int isTriggerInState = 0;
          int transType = 0;
          String transCondition = null;
          String transSkills = null;
          int skillTargetType = 0;
          int canZombie = 0;
          int isDelayDeath = 0;
          int isCasterDieInSkill = 0;
          int isBasic = 0;
          int isAttack = 0;
          int isPassive = 0;
          int isEnergy = 0;
          int isTrigger = 0;
          int isSuper = 0;
          int isSummon = 0;
          int consumeEnergy = 0;
          int isUseAtStart = 0;
          int cd = 0;
          String behaviorTreeName = null;
          int skillLv = 0;
          String upgradeItems = null;
          int replaceItems = 0;
          String flags = null;
          try {
            // 解析 技能ID
            if (!arr[0].trim().isEmpty()) {
              id = Integer.parseInt(arr[0].trim());
            }

            // 解析 技能名称
            if (!arr[1].trim().isEmpty()) {
              name = arr[1].trim();
            }

            // 解析 技能描述
            if (!arr[2].trim().isEmpty()) {
              description = arr[2].trim();
            }

            // 解析 升级描述
            if (!arr[3].trim().isEmpty()) {
              upgradeDes = arr[3].trim();
            }

            // 解析 详情
            if (!arr[4].trim().isEmpty()) {
              detail = arr[4].trim();
            }

            // 解析 是否bonus触发图标
            if (!arr[5].trim().isEmpty()) {
              bonusTrigger = Integer.parseInt(arr[5].trim());
            }

            // 解析 技能泡泡字本地化id
            if (!arr[6].trim().isEmpty()) {
              popLocId = arr[6].trim();
            }

            // 解析 是否展示泡泡字
            if (!arr[7].trim().isEmpty()) {
              isPopShow = Integer.parseInt(arr[7].trim());
            }

            // 解析 泡泡字显示类型
            if (!arr[8].trim().isEmpty()) {
              popType = Integer.parseInt(arr[8].trim());
            }

            // 解析 技能图标
            if (!arr[9].trim().isEmpty()) {
              icon = Integer.parseInt(arr[9].trim());
            }

            // 解析 反查技能组id
            if (!arr[10].trim().isEmpty()) {
              showSGId = Integer.parseInt(arr[10].trim());
            }

            // 解析 技能模板id(服务器)
            if (!arr[11].trim().isEmpty()) {
              skillBase = Integer.parseInt(arr[11].trim());
            }

            // 解析 技能模板id
            if (!arr[12].trim().isEmpty()) {
              skillGroupId = Integer.parseInt(arr[12].trim());
            }

            // 解析 互斥类型
            if (!arr[13].trim().isEmpty()) {
              mutexType = Integer.parseInt(arr[13].trim());
            }

            // 解析 互斥优先级
            if (!arr[14].trim().isEmpty()) {
              priority = Integer.parseInt(arr[14].trim());
            }

            // 解析 组
            if (!arr[15].trim().isEmpty()) {
              group = Integer.parseInt(arr[15].trim());
            }

            // 解析 状态中是否触发
            if (!arr[16].trim().isEmpty()) {
              isTriggerInState = Integer.parseInt(arr[16].trim());
            }

            // 解析 转化条件
            if (!arr[17].trim().isEmpty()) {
              transType = Integer.parseInt(arr[17].trim());
            }

            // 解析 转化条件
            if (!arr[18].trim().isEmpty()) {
              transCondition = arr[18].trim();
            }

            // 解析 转化技能
            if (!arr[19].trim().isEmpty()) {
              transSkills = arr[19].trim();
            }

            // 解析 攻击类型
            if (!arr[20].trim().isEmpty()) {
              skillTargetType = Integer.parseInt(arr[20].trim());
            }

            // 解析 能否施法者死亡后释放
            if (!arr[21].trim().isEmpty()) {
              canZombie = Integer.parseInt(arr[21].trim());
            }

            // 解析 是否延迟死亡
            if (!arr[22].trim().isEmpty()) {
              isDelayDeath = Integer.parseInt(arr[22].trim());
            }

            // 解析 是否技能中死亡
            if (!arr[23].trim().isEmpty()) {
              isCasterDieInSkill = Integer.parseInt(arr[23].trim());
            }

            // 解析 是否为普攻
            if (!arr[24].trim().isEmpty()) {
              isBasic = Integer.parseInt(arr[24].trim());
            }

            // 解析 是否为攻击
            if (!arr[25].trim().isEmpty()) {
              isAttack = Integer.parseInt(arr[25].trim());
            }

            // 解析 是否为被动技能
            if (!arr[26].trim().isEmpty()) {
              isPassive = Integer.parseInt(arr[26].trim());
            }

            // 解析 是否为主动技能
            if (!arr[27].trim().isEmpty()) {
              isEnergy = Integer.parseInt(arr[27].trim());
            }

            // 解析 是否为触发技能
            if (!arr[28].trim().isEmpty()) {
              isTrigger = Integer.parseInt(arr[28].trim());
            }

            // 解析 是否为S技能
            if (!arr[29].trim().isEmpty()) {
              isSuper = Integer.parseInt(arr[29].trim());
            }

            // 解析 是否为召唤技能
            if (!arr[30].trim().isEmpty()) {
              isSummon = Integer.parseInt(arr[30].trim());
            }

            // 解析 能量消耗
            if (!arr[31].trim().isEmpty()) {
              consumeEnergy = Integer.parseInt(arr[31].trim());
            }

            // 解析 是否战斗开始生效
            if (!arr[32].trim().isEmpty()) {
              isUseAtStart = Integer.parseInt(arr[32].trim());
            }

            // 解析 冷却时间
            if (!arr[33].trim().isEmpty()) {
              cd = Integer.parseInt(arr[33].trim());
            }

            // 解析 行为树名称
            if (!arr[34].trim().isEmpty()) {
              behaviorTreeName = arr[34].trim();
            }

            // 解析 技能等级
            if (!arr[35].trim().isEmpty()) {
              skillLv = Integer.parseInt(arr[35].trim());
            }

            // 解析 升级物品
            if (!arr[36].trim().isEmpty()) {
              upgradeItems = arr[36].trim();
            }

            // 解析 升级替换物
            if (!arr[37].trim().isEmpty()) {
              replaceItems = Integer.parseInt(arr[37].trim());
            }

            // 解析 标签
            if (!arr[38].trim().isEmpty()) {
              flags = arr[38].trim();
            }

          } catch (Exception e) {
            logger.error(String.format("解析配置 %s 表, 字符串:%s 报错，请检查:%s", fileName, rowText, e.getMessage()));
            throw new ConfigLoadException("Error parsing config file :" + fileName);
          }
          SkillBaseConfig config = new SkillBaseConfig(id, name, description, upgradeDes, detail, bonusTrigger, popLocId, isPopShow, popType, icon, showSGId, skillBase, skillGroupId, mutexType, priority, group, isTriggerInState, transType, transCondition, transSkills, skillTargetType, canZombie, isDelayDeath, isCasterDieInSkill, isBasic, isAttack, isPassive, isEnergy, isTrigger, isSuper, isSummon, consumeEnergy, isUseAtStart, cd, behaviorTreeName, skillLv, upgradeItems, replaceItems, flags);
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

    public List<SkillBaseConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, SkillBaseConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "skillBase.txt";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
