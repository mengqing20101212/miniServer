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
 * File: LimitedGitConditionConfigManager
 */
public class LimitedGitConditionConfigManager implements InterfaceConfigManagerProxy {
  public static LimitedGitConditionConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(LimitedGitConditionConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new LimitedGitConditionConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "limitedGitCondition.xml"; }

  public static class LimitedGitConditionConfigManagerImpl extends AbstractConfigManger {
    private List<LimitedGitConditionConfig> configList = List.of();
    private Map<Integer, LimitedGitConditionConfig> configMap = Map.of();

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
      ly.ConfigValueType type12 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type13 = ly.ConfigValueType.parse("int");
      String fileName = configDir + File.separator + getConfigFileName();
      LimitedGitConditionConfigChecker checker = new LimitedGitConditionConfigChecker();
      List<LimitedGitConditionConfig> newList = new ArrayList<>();
      Map<Integer, LimitedGitConditionConfig> newMap = new HashMap<>();
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
          int parsedField12 = 0;
          int parsedField13 = 0;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("des"), fileName + ":行" + lineNumber + ":字段 des");
            parsedField2 = ly.ConfigValues.read(type2, values.get("missionId"), fileName + ":行" + lineNumber + ":字段 missionId");
            parsedField3 = ly.ConfigValues.read(type3, values.get("missionType"), fileName + ":行" + lineNumber + ":字段 missionType");
            parsedField4 = ly.ConfigValues.read(type4, values.get("levelMin"), fileName + ":行" + lineNumber + ":字段 levelMin");
            parsedField5 = ly.ConfigValues.read(type5, values.get("levelMax"), fileName + ":行" + lineNumber + ":字段 levelMax");
            parsedField6 = ly.ConfigValues.read(type6, values.get("isRepeat"), fileName + ":行" + lineNumber + ":字段 isRepeat");
            parsedField7 = ly.ConfigValues.read(type7, values.get("RechargeShopId"), fileName + ":行" + lineNumber + ":字段 RechargeShopId");
            parsedField8 = ly.ConfigValues.read(type8, values.get("giveCloseTime"), fileName + ":行" + lineNumber + ":字段 giveCloseTime");
            parsedField9 = ly.ConfigValues.read(type9, values.get("cycleTime"), fileName + ":行" + lineNumber + ":字段 cycleTime");
            parsedField10 = ly.ConfigValues.read(type10, values.get("activateType"), fileName + ":行" + lineNumber + ":字段 activateType");
            parsedField11 = ly.ConfigValues.read(type11, values.get("activateMax"), fileName + ":行" + lineNumber + ":字段 activateMax");
            parsedField12 = ly.ConfigValues.read(type12, values.get("herocondition"), fileName + ":行" + lineNumber + ":字段 herocondition");
            parsedField13 = ly.ConfigValues.read(type13, values.get("rechargecondition"), fileName + ":行" + lineNumber + ":字段 rechargecondition");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          LimitedGitConditionConfig config = new LimitedGitConditionConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8, parsedField9, parsedField10, parsedField11, parsedField12, parsedField13);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (LimitedGitConditionConfig config : configList) config.afterLoad();
      new LimitedGitConditionConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<LimitedGitConditionConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, LimitedGitConditionConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "limitedGitCondition.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
