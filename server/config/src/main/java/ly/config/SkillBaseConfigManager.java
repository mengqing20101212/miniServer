package ly.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
  public static SkillBaseConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(SkillBaseConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new SkillBaseConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "skillBase.xml"; }

  public static class SkillBaseConfigManagerImpl extends AbstractConfigManger {
    private List<SkillBaseConfig> configList = List.of();
    private Map<Integer, SkillBaseConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type5 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type7 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type8 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type9 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type10 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type11 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type12 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type13 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type14 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type15 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type16 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type17 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type18 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type19 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type20 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type21 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type22 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type23 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type24 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type25 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type26 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type27 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type28 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type29 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type30 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type31 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type32 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type33 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type34 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type35 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type36 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type37 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type38 = ly.ConfigValueType.parse("string");
      String fileName = configDir + File.separator + getConfigFileName();
      SkillBaseConfigChecker checker = new SkillBaseConfigChecker();
      List<SkillBaseConfig> newList = new ArrayList<>();
      Map<Integer, SkillBaseConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          String parsedField1 = null;
          String parsedField2 = null;
          String parsedField3 = null;
          String parsedField4 = null;
          int parsedField5 = 0;
          String parsedField6 = null;
          int parsedField7 = 0;
          int parsedField8 = 0;
          int parsedField9 = 0;
          int parsedField10 = 0;
          int parsedField11 = 0;
          int parsedField12 = 0;
          int parsedField13 = 0;
          int parsedField14 = 0;
          int parsedField15 = 0;
          int parsedField16 = 0;
          int parsedField17 = 0;
          String parsedField18 = null;
          String parsedField19 = null;
          int parsedField20 = 0;
          int parsedField21 = 0;
          int parsedField22 = 0;
          int parsedField23 = 0;
          int parsedField24 = 0;
          int parsedField25 = 0;
          int parsedField26 = 0;
          int parsedField27 = 0;
          int parsedField28 = 0;
          int parsedField29 = 0;
          int parsedField30 = 0;
          int parsedField31 = 0;
          int parsedField32 = 0;
          int parsedField33 = 0;
          String parsedField34 = null;
          int parsedField35 = 0;
          String parsedField36 = null;
          int parsedField37 = 0;
          String parsedField38 = null;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("name"), fileName + ":行" + lineNumber + ":字段 name");
            parsedField2 = ly.ConfigValues.read(type2, values.get("description"), fileName + ":行" + lineNumber + ":字段 description");
            parsedField3 = ly.ConfigValues.read(type3, values.get("upgradeDes"), fileName + ":行" + lineNumber + ":字段 upgradeDes");
            parsedField4 = ly.ConfigValues.read(type4, values.get("detail"), fileName + ":行" + lineNumber + ":字段 detail");
            parsedField5 = ly.ConfigValues.read(type5, values.get("bonusTrigger"), fileName + ":行" + lineNumber + ":字段 bonusTrigger");
            parsedField6 = ly.ConfigValues.read(type6, values.get("popLocId"), fileName + ":行" + lineNumber + ":字段 popLocId");
            parsedField7 = ly.ConfigValues.read(type7, values.get("isPopShow"), fileName + ":行" + lineNumber + ":字段 isPopShow");
            parsedField8 = ly.ConfigValues.read(type8, values.get("popType"), fileName + ":行" + lineNumber + ":字段 popType");
            parsedField9 = ly.ConfigValues.read(type9, values.get("icon"), fileName + ":行" + lineNumber + ":字段 icon");
            parsedField10 = ly.ConfigValues.read(type10, values.get("showSGId"), fileName + ":行" + lineNumber + ":字段 showSGId");
            parsedField11 = ly.ConfigValues.read(type11, values.get("skillBase"), fileName + ":行" + lineNumber + ":字段 skillBase");
            parsedField12 = ly.ConfigValues.read(type12, values.get("skillGroupId"), fileName + ":行" + lineNumber + ":字段 skillGroupId");
            parsedField13 = ly.ConfigValues.read(type13, values.get("mutexType"), fileName + ":行" + lineNumber + ":字段 mutexType");
            parsedField14 = ly.ConfigValues.read(type14, values.get("priority"), fileName + ":行" + lineNumber + ":字段 priority");
            parsedField15 = ly.ConfigValues.read(type15, values.get("group"), fileName + ":行" + lineNumber + ":字段 group");
            parsedField16 = ly.ConfigValues.read(type16, values.get("isTriggerInState"), fileName + ":行" + lineNumber + ":字段 isTriggerInState");
            parsedField17 = ly.ConfigValues.read(type17, values.get("transType"), fileName + ":行" + lineNumber + ":字段 transType");
            parsedField18 = ly.ConfigValues.read(type18, values.get("transCondition"), fileName + ":行" + lineNumber + ":字段 transCondition");
            parsedField19 = ly.ConfigValues.read(type19, values.get("transSkills"), fileName + ":行" + lineNumber + ":字段 transSkills");
            parsedField20 = ly.ConfigValues.read(type20, values.get("skillTargetType"), fileName + ":行" + lineNumber + ":字段 skillTargetType");
            parsedField21 = ly.ConfigValues.read(type21, values.get("canZombie"), fileName + ":行" + lineNumber + ":字段 canZombie");
            parsedField22 = ly.ConfigValues.read(type22, values.get("isDelayDeath"), fileName + ":行" + lineNumber + ":字段 isDelayDeath");
            parsedField23 = ly.ConfigValues.read(type23, values.get("isCasterDieInSkill"), fileName + ":行" + lineNumber + ":字段 isCasterDieInSkill");
            parsedField24 = ly.ConfigValues.read(type24, values.get("isBasic"), fileName + ":行" + lineNumber + ":字段 isBasic");
            parsedField25 = ly.ConfigValues.read(type25, values.get("isAttack"), fileName + ":行" + lineNumber + ":字段 isAttack");
            parsedField26 = ly.ConfigValues.read(type26, values.get("isPassive"), fileName + ":行" + lineNumber + ":字段 isPassive");
            parsedField27 = ly.ConfigValues.read(type27, values.get("isEnergy"), fileName + ":行" + lineNumber + ":字段 isEnergy");
            parsedField28 = ly.ConfigValues.read(type28, values.get("isTrigger"), fileName + ":行" + lineNumber + ":字段 isTrigger");
            parsedField29 = ly.ConfigValues.read(type29, values.get("isSuper"), fileName + ":行" + lineNumber + ":字段 isSuper");
            parsedField30 = ly.ConfigValues.read(type30, values.get("isSummon"), fileName + ":行" + lineNumber + ":字段 isSummon");
            parsedField31 = ly.ConfigValues.read(type31, values.get("consumeEnergy"), fileName + ":行" + lineNumber + ":字段 consumeEnergy");
            parsedField32 = ly.ConfigValues.read(type32, values.get("isUseAtStart"), fileName + ":行" + lineNumber + ":字段 isUseAtStart");
            parsedField33 = ly.ConfigValues.read(type33, values.get("cd"), fileName + ":行" + lineNumber + ":字段 cd");
            parsedField34 = ly.ConfigValues.read(type34, values.get("behaviorTreeName"), fileName + ":行" + lineNumber + ":字段 behaviorTreeName");
            parsedField35 = ly.ConfigValues.read(type35, values.get("skillLv"), fileName + ":行" + lineNumber + ":字段 skillLv");
            parsedField36 = ly.ConfigValues.read(type36, values.get("upgradeItems"), fileName + ":行" + lineNumber + ":字段 upgradeItems");
            parsedField37 = ly.ConfigValues.read(type37, values.get("replaceItems"), fileName + ":行" + lineNumber + ":字段 replaceItems");
            parsedField38 = ly.ConfigValues.read(type38, values.get("flags"), fileName + ":行" + lineNumber + ":字段 flags");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          SkillBaseConfig config = new SkillBaseConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8, parsedField9, parsedField10, parsedField11, parsedField12, parsedField13, parsedField14, parsedField15, parsedField16, parsedField17, parsedField18, parsedField19, parsedField20, parsedField21, parsedField22, parsedField23, parsedField24, parsedField25, parsedField26, parsedField27, parsedField28, parsedField29, parsedField30, parsedField31, parsedField32, parsedField33, parsedField34, parsedField35, parsedField36, parsedField37, parsedField38);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (SkillBaseConfig config : configList) config.afterLoad();
      new SkillBaseConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<SkillBaseConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, SkillBaseConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "skillBase.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
