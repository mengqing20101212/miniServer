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
 * File: SkillConditionConfigManager
 */
public class SkillConditionConfigManager implements InterfaceConfigManagerProxy {
  public static SkillConditionConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(SkillConditionConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new SkillConditionConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "skillCondition.xml"; }

  public static class SkillConditionConfigManagerImpl extends AbstractConfigManger {
    private List<SkillConditionConfig> configList = List.of();
    private Map<Integer, SkillConditionConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type5 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type7 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type8 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type9 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type10 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type11 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type12 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type13 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type14 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type15 = ly.ConfigValueType.parse("int");
      String fileName = configDir + File.separator + getConfigFileName();
      SkillConditionConfigChecker checker = new SkillConditionConfigChecker();
      List<SkillConditionConfig> newList = new ArrayList<>();
      Map<Integer, SkillConditionConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          int parsedField1 = 0;
          String parsedField2 = null;
          String parsedField3 = null;
          String parsedField4 = null;
          String parsedField5 = null;
          String parsedField6 = null;
          String parsedField7 = null;
          String parsedField8 = null;
          String parsedField9 = null;
          String parsedField10 = null;
          String parsedField11 = null;
          String parsedField12 = null;
          int parsedField13 = 0;
          int parsedField14 = 0;
          int parsedField15 = 0;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("type"), fileName + ":行" + lineNumber + ":字段 type");
            parsedField2 = ly.ConfigValues.read(type2, values.get("des"), fileName + ":行" + lineNumber + ":字段 des");
            parsedField3 = ly.ConfigValues.read(type3, values.get("param_1"), fileName + ":行" + lineNumber + ":字段 param_1");
            parsedField4 = ly.ConfigValues.read(type4, values.get("param_2"), fileName + ":行" + lineNumber + ":字段 param_2");
            parsedField5 = ly.ConfigValues.read(type5, values.get("param_3"), fileName + ":行" + lineNumber + ":字段 param_3");
            parsedField6 = ly.ConfigValues.read(type6, values.get("param_4"), fileName + ":行" + lineNumber + ":字段 param_4");
            parsedField7 = ly.ConfigValues.read(type7, values.get("param_5"), fileName + ":行" + lineNumber + ":字段 param_5");
            parsedField8 = ly.ConfigValues.read(type8, values.get("param_6"), fileName + ":行" + lineNumber + ":字段 param_6");
            parsedField9 = ly.ConfigValues.read(type9, values.get("param_7"), fileName + ":行" + lineNumber + ":字段 param_7");
            parsedField10 = ly.ConfigValues.read(type10, values.get("param_8"), fileName + ":行" + lineNumber + ":字段 param_8");
            parsedField11 = ly.ConfigValues.read(type11, values.get("param_9"), fileName + ":行" + lineNumber + ":字段 param_9");
            parsedField12 = ly.ConfigValues.read(type12, values.get("param_10"), fileName + ":行" + lineNumber + ":字段 param_10");
            parsedField13 = ly.ConfigValues.read(type13, values.get("heroId"), fileName + ":行" + lineNumber + ":字段 heroId");
            parsedField14 = ly.ConfigValues.read(type14, values.get("skillSequence"), fileName + ":行" + lineNumber + ":字段 skillSequence");
            parsedField15 = ly.ConfigValues.read(type15, values.get("effectSequence"), fileName + ":行" + lineNumber + ":字段 effectSequence");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          SkillConditionConfig config = new SkillConditionConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8, parsedField9, parsedField10, parsedField11, parsedField12, parsedField13, parsedField14, parsedField15);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (SkillConditionConfig config : configList) config.afterLoad();
      new SkillConditionConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<SkillConditionConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, SkillConditionConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "skillCondition.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
