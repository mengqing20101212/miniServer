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
 * File: ActivityTaskConfigManager
 */
public class ActivityTaskConfigManager implements InterfaceConfigManagerProxy {
  public static ActivityTaskConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(ActivityTaskConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new ActivityTaskConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "activityTask.xml"; }

  public static class ActivityTaskConfigManagerImpl extends AbstractConfigManger {
    private List<ActivityTaskConfig> configList = List.of();
    private Map<Integer, ActivityTaskConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type5 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type7 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type8 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type9 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type10 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type11 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type12 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type13 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type14 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type15 = ly.ConfigValueType.parse("int");
      String fileName = configDir + File.separator + getConfigFileName();
      ActivityTaskConfigChecker checker = new ActivityTaskConfigChecker();
      List<ActivityTaskConfig> newList = new ArrayList<>();
      Map<Integer, ActivityTaskConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          String parsedField1 = null;
          String parsedField2 = null;
          int parsedField3 = 0;
          int parsedField4 = 0;
          int parsedField5 = 0;
          int parsedField6 = 0;
          String parsedField7 = null;
          int parsedField8 = 0;
          int parsedField9 = 0;
          int parsedField10 = 0;
          int parsedField11 = 0;
          int parsedField12 = 0;
          String parsedField13 = null;
          String parsedField14 = null;
          int parsedField15 = 0;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("title"), fileName + ":行" + lineNumber + ":字段 title");
            parsedField2 = ly.ConfigValues.read(type2, values.get("name"), fileName + ":行" + lineNumber + ":字段 name");
            parsedField3 = ly.ConfigValues.read(type3, values.get("questType"), fileName + ":行" + lineNumber + ":字段 questType");
            parsedField4 = ly.ConfigValues.read(type4, values.get("scheDuling"), fileName + ":行" + lineNumber + ":字段 scheDuling");
            parsedField5 = ly.ConfigValues.read(type5, values.get("page"), fileName + ":行" + lineNumber + ":字段 page");
            parsedField6 = ly.ConfigValues.read(type6, values.get("condition"), fileName + ":行" + lineNumber + ":字段 condition");
            parsedField7 = ly.ConfigValues.read(type7, values.get("rewardShow"), fileName + ":行" + lineNumber + ":字段 rewardShow");
            parsedField8 = ly.ConfigValues.read(type8, values.get("drop"), fileName + ":行" + lineNumber + ":字段 drop");
            parsedField9 = ly.ConfigValues.read(type9, values.get("redirectionId"), fileName + ":行" + lineNumber + ":字段 redirectionId");
            parsedField10 = ly.ConfigValues.read(type10, values.get("priority"), fileName + ":行" + lineNumber + ":字段 priority");
            parsedField11 = ly.ConfigValues.read(type11, values.get("pointType"), fileName + ":行" + lineNumber + ":字段 pointType");
            parsedField12 = ly.ConfigValues.read(type12, values.get("point"), fileName + ":行" + lineNumber + ":字段 point");
            parsedField13 = ly.ConfigValues.read(type13, values.get("startTime"), fileName + ":行" + lineNumber + ":字段 startTime");
            parsedField14 = ly.ConfigValues.read(type14, values.get("endTime"), fileName + ":行" + lineNumber + ":字段 endTime");
            parsedField15 = ly.ConfigValues.read(type15, values.get("titlePicId"), fileName + ":行" + lineNumber + ":字段 titlePicId");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          ActivityTaskConfig config = new ActivityTaskConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8, parsedField9, parsedField10, parsedField11, parsedField12, parsedField13, parsedField14, parsedField15);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (ActivityTaskConfig config : configList) config.afterLoad();
      new ActivityTaskConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<ActivityTaskConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, ActivityTaskConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "activityTask.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
