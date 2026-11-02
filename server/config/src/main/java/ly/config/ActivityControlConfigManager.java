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
 * File: ActivityControlConfigManager
 */
public class ActivityControlConfigManager implements InterfaceConfigManagerProxy {
  public static ActivityControlConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(ActivityControlConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new ActivityControlConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "activityControl.xml"; }

  public static class ActivityControlConfigManagerImpl extends AbstractConfigManger {
    private List<ActivityControlConfig> configList = List.of();
    private Map<Integer, ActivityControlConfig> configMap = Map.of();

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
      ly.ConfigValueType type10 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type11 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type12 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type13 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type14 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type15 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type16 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type17 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type18 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type19 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type20 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type21 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type22 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type23 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type24 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type25 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type26 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type27 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type28 = ly.ConfigValueType.parse("int");
      String fileName = configDir + File.separator + getConfigFileName();
      ActivityControlConfigChecker checker = new ActivityControlConfigChecker();
      List<ActivityControlConfig> newList = new ArrayList<>();
      Map<Integer, ActivityControlConfig> newMap = new HashMap<>();
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
          String parsedField10 = null;
          int parsedField11 = 0;
          int parsedField12 = 0;
          String parsedField13 = null;
          String parsedField14 = null;
          String parsedField15 = null;
          String parsedField16 = null;
          int parsedField17 = 0;
          int parsedField18 = 0;
          int parsedField19 = 0;
          int parsedField20 = 0;
          int parsedField21 = 0;
          String parsedField22 = null;
          String parsedField23 = null;
          String parsedField24 = null;
          String parsedField25 = null;
          String parsedField26 = null;
          int parsedField27 = 0;
          int parsedField28 = 0;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("name"), fileName + ":行" + lineNumber + ":字段 name");
            parsedField2 = ly.ConfigValues.read(type2, values.get("unlockType"), fileName + ":行" + lineNumber + ":字段 unlockType");
            parsedField3 = ly.ConfigValues.read(type3, values.get("unlockPara"), fileName + ":行" + lineNumber + ":字段 unlockPara");
            parsedField4 = ly.ConfigValues.read(type4, values.get("unlockPara2"), fileName + ":行" + lineNumber + ":字段 unlockPara2");
            parsedField5 = ly.ConfigValues.read(type5, values.get("lineupId"), fileName + ":行" + lineNumber + ":字段 lineupId");
            parsedField6 = ly.ConfigValues.read(type6, values.get("lineupLimit"), fileName + ":行" + lineNumber + ":字段 lineupLimit");
            parsedField7 = ly.ConfigValues.read(type7, values.get("isOnlineBattle"), fileName + ":行" + lineNumber + ":字段 isOnlineBattle");
            parsedField8 = ly.ConfigValues.read(type8, values.get("BgmId"), fileName + ":行" + lineNumber + ":字段 BgmId");
            parsedField9 = ly.ConfigValues.read(type9, values.get("openType"), fileName + ":行" + lineNumber + ":字段 openType");
            parsedField10 = ly.ConfigValues.read(type10, values.get("openPara"), fileName + ":行" + lineNumber + ":字段 openPara");
            parsedField11 = ly.ConfigValues.read(type11, values.get("guideId"), fileName + ":行" + lineNumber + ":字段 guideId");
            parsedField12 = ly.ConfigValues.read(type12, values.get("icon"), fileName + ":行" + lineNumber + ":字段 icon");
            parsedField13 = ly.ConfigValues.read(type13, values.get("des"), fileName + ":行" + lineNumber + ":字段 des");
            parsedField14 = ly.ConfigValues.read(type14, values.get("timeDes"), fileName + ":行" + lineNumber + ":字段 timeDes");
            parsedField15 = ly.ConfigValues.read(type15, values.get("rewardId"), fileName + ":行" + lineNumber + ":字段 rewardId");
            parsedField16 = ly.ConfigValues.read(type16, values.get("openLimitDes"), fileName + ":行" + lineNumber + ":字段 openLimitDes");
            parsedField17 = ly.ConfigValues.read(type17, values.get("turnId"), fileName + ":行" + lineNumber + ":字段 turnId");
            parsedField18 = ly.ConfigValues.read(type18, values.get("lineupTeamId"), fileName + ":行" + lineNumber + ":字段 lineupTeamId");
            parsedField19 = ly.ConfigValues.read(type19, values.get("saveBattleLog"), fileName + ":行" + lineNumber + ":字段 saveBattleLog");
            parsedField20 = ly.ConfigValues.read(type20, values.get("dayLimit"), fileName + ":行" + lineNumber + ":字段 dayLimit");
            parsedField21 = ly.ConfigValues.read(type21, values.get("weekLimit"), fileName + ":行" + lineNumber + ":字段 weekLimit");
            parsedField22 = ly.ConfigValues.read(type22, values.get("activityIcon"), fileName + ":行" + lineNumber + ":字段 activityIcon");
            parsedField23 = ly.ConfigValues.read(type23, values.get("activityName"), fileName + ":行" + lineNumber + ":字段 activityName");
            parsedField24 = ly.ConfigValues.read(type24, values.get("bgColour"), fileName + ":行" + lineNumber + ":字段 bgColour");
            parsedField25 = ly.ConfigValues.read(type25, values.get("activityreward"), fileName + ":行" + lineNumber + ":字段 activityreward");
            parsedField26 = ly.ConfigValues.read(type26, values.get("teamLv"), fileName + ":行" + lineNumber + ":字段 teamLv");
            parsedField27 = ly.ConfigValues.read(type27, values.get("help"), fileName + ":行" + lineNumber + ":字段 help");
            parsedField28 = ly.ConfigValues.read(type28, values.get("noPrelock"), fileName + ":行" + lineNumber + ":字段 noPrelock");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          ActivityControlConfig config = new ActivityControlConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8, parsedField9, parsedField10, parsedField11, parsedField12, parsedField13, parsedField14, parsedField15, parsedField16, parsedField17, parsedField18, parsedField19, parsedField20, parsedField21, parsedField22, parsedField23, parsedField24, parsedField25, parsedField26, parsedField27, parsedField28);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (ActivityControlConfig config : configList) config.afterLoad();
      new ActivityControlConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<ActivityControlConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, ActivityControlConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "activityControl.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
