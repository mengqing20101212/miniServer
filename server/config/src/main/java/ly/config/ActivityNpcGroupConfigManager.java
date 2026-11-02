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
 * File: ActivityNpcGroupConfigManager
 */
public class ActivityNpcGroupConfigManager implements InterfaceConfigManagerProxy {
  public static ActivityNpcGroupConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(ActivityNpcGroupConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new ActivityNpcGroupConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "activityNpcGroup.xml"; }

  public static class ActivityNpcGroupConfigManagerImpl extends AbstractConfigManger {
    private List<ActivityNpcGroupConfig> configList = List.of();
    private Map<Integer, ActivityNpcGroupConfig> configMap = Map.of();

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
      String fileName = configDir + File.separator + getConfigFileName();
      ActivityNpcGroupConfigChecker checker = new ActivityNpcGroupConfigChecker();
      List<ActivityNpcGroupConfig> newList = new ArrayList<>();
      Map<Integer, ActivityNpcGroupConfig> newMap = new HashMap<>();
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
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("defaultAni"), fileName + ":行" + lineNumber + ":字段 defaultAni");
            parsedField2 = ly.ConfigValues.read(type2, values.get("p1"), fileName + ":行" + lineNumber + ":字段 p1");
            parsedField3 = ly.ConfigValues.read(type3, values.get("groupName"), fileName + ":行" + lineNumber + ":字段 groupName");
            parsedField4 = ly.ConfigValues.read(type4, values.get("clickAniList"), fileName + ":行" + lineNumber + ":字段 clickAniList");
            parsedField5 = ly.ConfigValues.read(type5, values.get("moveDistance"), fileName + ":行" + lineNumber + ":字段 moveDistance");
            parsedField6 = ly.ConfigValues.read(type6, values.get("cameraHight"), fileName + ":行" + lineNumber + ":字段 cameraHight");
            parsedField7 = ly.ConfigValues.read(type7, values.get("cameraDistance"), fileName + ":行" + lineNumber + ":字段 cameraDistance");
            parsedField8 = ly.ConfigValues.read(type8, values.get("npcDistance"), fileName + ":行" + lineNumber + ":字段 npcDistance");
            parsedField9 = ly.ConfigValues.read(type9, values.get("textPrefabType"), fileName + ":行" + lineNumber + ":字段 textPrefabType");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          ActivityNpcGroupConfig config = new ActivityNpcGroupConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8, parsedField9);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (ActivityNpcGroupConfig config : configList) config.afterLoad();
      new ActivityNpcGroupConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<ActivityNpcGroupConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, ActivityNpcGroupConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "activityNpcGroup.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
