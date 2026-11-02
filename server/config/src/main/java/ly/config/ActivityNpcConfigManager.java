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
 * File: ActivityNpcConfigManager
 */
public class ActivityNpcConfigManager implements InterfaceConfigManagerProxy {
  public static ActivityNpcConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(ActivityNpcConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new ActivityNpcConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "activityNpc.xml"; }

  public static class ActivityNpcConfigManagerImpl extends AbstractConfigManger {
    private List<ActivityNpcConfig> configList = List.of();
    private Map<Integer, ActivityNpcConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("string");
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
      ly.ConfigValueType type13 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type14 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type15 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type16 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type17 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type18 = ly.ConfigValueType.parse("string");
      String fileName = configDir + File.separator + getConfigFileName();
      ActivityNpcConfigChecker checker = new ActivityNpcConfigChecker();
      List<ActivityNpcConfig> newList = new ArrayList<>();
      Map<Integer, ActivityNpcConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          String parsedField1 = null;
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
          String parsedField13 = null;
          String parsedField14 = null;
          String parsedField15 = null;
          String parsedField16 = null;
          String parsedField17 = null;
          String parsedField18 = null;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("config_name"), fileName + ":行" + lineNumber + ":字段 config_name");
            parsedField2 = ly.ConfigValues.read(type2, values.get("characterModelId"), fileName + ":行" + lineNumber + ":字段 characterModelId");
            parsedField3 = ly.ConfigValues.read(type3, values.get("defaultAni"), fileName + ":行" + lineNumber + ":字段 defaultAni");
            parsedField4 = ly.ConfigValues.read(type4, values.get("bornShowAni"), fileName + ":行" + lineNumber + ":字段 bornShowAni");
            parsedField5 = ly.ConfigValues.read(type5, values.get("clickType"), fileName + ":行" + lineNumber + ":字段 clickType");
            parsedField6 = ly.ConfigValues.read(type6, values.get("clickAniList"), fileName + ":行" + lineNumber + ":字段 clickAniList");
            parsedField7 = ly.ConfigValues.read(type7, values.get("clickCameraList"), fileName + ":行" + lineNumber + ":字段 clickCameraList");
            parsedField8 = ly.ConfigValues.read(type8, values.get("moveDistance"), fileName + ":行" + lineNumber + ":字段 moveDistance");
            parsedField9 = ly.ConfigValues.read(type9, values.get("cameraDistance"), fileName + ":行" + lineNumber + ":字段 cameraDistance");
            parsedField10 = ly.ConfigValues.read(type10, values.get("clickText"), fileName + ":行" + lineNumber + ":字段 clickText");
            parsedField11 = ly.ConfigValues.read(type11, values.get("groupText"), fileName + ":行" + lineNumber + ":字段 groupText");
            parsedField12 = ly.ConfigValues.read(type12, values.get("ActivityType"), fileName + ":行" + lineNumber + ":字段 ActivityType");
            parsedField13 = ly.ConfigValues.read(type13, values.get("param_1"), fileName + ":行" + lineNumber + ":字段 param_1");
            parsedField14 = ly.ConfigValues.read(type14, values.get("param_2"), fileName + ":行" + lineNumber + ":字段 param_2");
            parsedField15 = ly.ConfigValues.read(type15, values.get("npcGrounpId"), fileName + ":行" + lineNumber + ":字段 npcGrounpId");
            parsedField16 = ly.ConfigValues.read(type16, values.get("showPriority"), fileName + ":行" + lineNumber + ":字段 showPriority");
            parsedField17 = ly.ConfigValues.read(type17, values.get("decorationId"), fileName + ":行" + lineNumber + ":字段 decorationId");
            parsedField18 = ly.ConfigValues.read(type18, values.get("decorationPoint"), fileName + ":行" + lineNumber + ":字段 decorationPoint");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          ActivityNpcConfig config = new ActivityNpcConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8, parsedField9, parsedField10, parsedField11, parsedField12, parsedField13, parsedField14, parsedField15, parsedField16, parsedField17, parsedField18);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (ActivityNpcConfig config : configList) config.afterLoad();
      new ActivityNpcConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<ActivityNpcConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, ActivityNpcConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "activityNpc.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
