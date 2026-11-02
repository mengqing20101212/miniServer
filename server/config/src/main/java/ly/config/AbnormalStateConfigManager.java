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
 * File: AbnormalStateConfigManager
 */
public class AbnormalStateConfigManager implements InterfaceConfigManagerProxy {
  public static AbnormalStateConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(AbnormalStateConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new AbnormalStateConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "abnormalState.xml"; }

  public static class AbnormalStateConfigManagerImpl extends AbstractConfigManger {
    private List<AbnormalStateConfig> configList = List.of();
    private Map<Integer, AbnormalStateConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type5 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type7 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type8 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type9 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type10 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type11 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type12 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type13 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type14 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type15 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type16 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type17 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type18 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type19 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type20 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type21 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type22 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type23 = ly.ConfigValueType.parse("int");
      String fileName = configDir + File.separator + getConfigFileName();
      AbnormalStateConfigChecker checker = new AbnormalStateConfigChecker();
      List<AbnormalStateConfig> newList = new ArrayList<>();
      Map<Integer, AbnormalStateConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          String parsedField1 = null;
          int parsedField2 = 0;
          int parsedField3 = 0;
          int parsedField4 = 0;
          int parsedField5 = 0;
          int parsedField6 = 0;
          int parsedField7 = 0;
          int parsedField8 = 0;
          int parsedField9 = 0;
          int parsedField10 = 0;
          int parsedField11 = 0;
          String parsedField12 = null;
          String parsedField13 = null;
          int parsedField14 = 0;
          String parsedField15 = null;
          String parsedField16 = null;
          String parsedField17 = null;
          String parsedField18 = null;
          String parsedField19 = null;
          String parsedField20 = null;
          String parsedField21 = null;
          int parsedField22 = 0;
          int parsedField23 = 0;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("name"), fileName + ":行" + lineNumber + ":字段 name");
            parsedField2 = ly.ConfigValues.read(type2, values.get("type"), fileName + ":行" + lineNumber + ":字段 type");
            parsedField3 = ly.ConfigValues.read(type3, values.get("canReTrigger"), fileName + ":行" + lineNumber + ":字段 canReTrigger");
            parsedField4 = ly.ConfigValues.read(type4, values.get("playAni"), fileName + ":行" + lineNumber + ":字段 playAni");
            parsedField5 = ly.ConfigValues.read(type5, values.get("playShowAni"), fileName + ":行" + lineNumber + ":字段 playShowAni");
            parsedField6 = ly.ConfigValues.read(type6, values.get("skipTurn"), fileName + ":行" + lineNumber + ":字段 skipTurn");
            parsedField7 = ly.ConfigValues.read(type7, values.get("banEnergySkill"), fileName + ":行" + lineNumber + ":字段 banEnergySkill");
            parsedField8 = ly.ConfigValues.read(type8, values.get("banPassiveSkill"), fileName + ":行" + lineNumber + ":字段 banPassiveSkill");
            parsedField9 = ly.ConfigValues.read(type9, values.get("banSSkill"), fileName + ":行" + lineNumber + ":字段 banSSkill");
            parsedField10 = ly.ConfigValues.read(type10, values.get("canTriggerSkill"), fileName + ":行" + lineNumber + ":字段 canTriggerSkill");
            parsedField11 = ly.ConfigValues.read(type11, values.get("selectType"), fileName + ":行" + lineNumber + ":字段 selectType");
            parsedField12 = ly.ConfigValues.read(type12, values.get("dispelList"), fileName + ":行" + lineNumber + ":字段 dispelList");
            parsedField13 = ly.ConfigValues.read(type13, values.get("preventList"), fileName + ":行" + lineNumber + ":字段 preventList");
            parsedField14 = ly.ConfigValues.read(type14, values.get("isPlayAnim"), fileName + ":行" + lineNumber + ":字段 isPlayAnim");
            parsedField15 = ly.ConfigValues.read(type15, values.get("stateAnim"), fileName + ":行" + lineNumber + ":字段 stateAnim");
            parsedField16 = ly.ConfigValues.read(type16, values.get("stateDead"), fileName + ":行" + lineNumber + ":字段 stateDead");
            parsedField17 = ly.ConfigValues.read(type17, values.get("stateStart"), fileName + ":行" + lineNumber + ":字段 stateStart");
            parsedField18 = ly.ConfigValues.read(type18, values.get("stateFinish"), fileName + ":行" + lineNumber + ":字段 stateFinish");
            parsedField19 = ly.ConfigValues.read(type19, values.get("startEffect"), fileName + ":行" + lineNumber + ":字段 startEffect");
            parsedField20 = ly.ConfigValues.read(type20, values.get("runEffect"), fileName + ":行" + lineNumber + ":字段 runEffect");
            parsedField21 = ly.ConfigValues.read(type21, values.get("endEffect"), fileName + ":行" + lineNumber + ":字段 endEffect");
            parsedField22 = ly.ConfigValues.read(type22, values.get("statePriority"), fileName + ":行" + lineNumber + ":字段 statePriority");
            parsedField23 = ly.ConfigValues.read(type23, values.get("damageHitPro"), fileName + ":行" + lineNumber + ":字段 damageHitPro");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          AbnormalStateConfig config = new AbnormalStateConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8, parsedField9, parsedField10, parsedField11, parsedField12, parsedField13, parsedField14, parsedField15, parsedField16, parsedField17, parsedField18, parsedField19, parsedField20, parsedField21, parsedField22, parsedField23);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (AbnormalStateConfig config : configList) config.afterLoad();
      new AbnormalStateConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<AbnormalStateConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, AbnormalStateConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "abnormalState.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
