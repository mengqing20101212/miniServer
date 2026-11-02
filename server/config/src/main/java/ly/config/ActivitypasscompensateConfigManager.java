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
 * File: ActivitypasscompensateConfigManager
 */
public class ActivitypasscompensateConfigManager implements InterfaceConfigManagerProxy {
  public static ActivitypasscompensateConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(ActivitypasscompensateConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new ActivitypasscompensateConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "activitypasscompensate.xml"; }

  public static class ActivitypasscompensateConfigManagerImpl extends AbstractConfigManger {
    private List<ActivitypasscompensateConfig> configList = List.of();
    private Map<Integer, ActivitypasscompensateConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type5 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type7 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type8 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type9 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type10 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type11 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type12 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type13 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type14 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type15 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type16 = ly.ConfigValueType.parse("int");
      String fileName = configDir + File.separator + getConfigFileName();
      ActivitypasscompensateConfigChecker checker = new ActivitypasscompensateConfigChecker();
      List<ActivitypasscompensateConfig> newList = new ArrayList<>();
      Map<Integer, ActivitypasscompensateConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          int parsedField1 = 0;
          int parsedField2 = 0;
          int parsedField3 = 0;
          int parsedField4 = 0;
          String parsedField5 = null;
          String parsedField6 = null;
          String parsedField7 = null;
          int parsedField8 = 0;
          String parsedField9 = null;
          String parsedField10 = null;
          int parsedField11 = 0;
          int parsedField12 = 0;
          String parsedField13 = null;
          String parsedField14 = null;
          int parsedField15 = 0;
          int parsedField16 = 0;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("scheDuling"), fileName + ":行" + lineNumber + ":字段 scheDuling");
            parsedField2 = ly.ConfigValues.read(type2, values.get("score"), fileName + ":行" + lineNumber + ":字段 score");
            parsedField3 = ly.ConfigValues.read(type3, values.get("closeReward"), fileName + ":行" + lineNumber + ":字段 closeReward");
            parsedField4 = ly.ConfigValues.read(type4, values.get("closeRewardShow"), fileName + ":行" + lineNumber + ":字段 closeRewardShow");
            parsedField5 = ly.ConfigValues.read(type5, values.get("inherit1"), fileName + ":行" + lineNumber + ":字段 inherit1");
            parsedField6 = ly.ConfigValues.read(type6, values.get("inherit2"), fileName + ":行" + lineNumber + ":字段 inherit2");
            parsedField7 = ly.ConfigValues.read(type7, values.get("inherit3"), fileName + ":行" + lineNumber + ":字段 inherit3");
            parsedField8 = ly.ConfigValues.read(type8, values.get("mail"), fileName + ":行" + lineNumber + ":字段 mail");
            parsedField9 = ly.ConfigValues.read(type9, values.get("describe1"), fileName + ":行" + lineNumber + ":字段 describe1");
            parsedField10 = ly.ConfigValues.read(type10, values.get("describe2"), fileName + ":行" + lineNumber + ":字段 describe2");
            parsedField11 = ly.ConfigValues.read(type11, values.get("item"), fileName + ":行" + lineNumber + ":字段 item");
            parsedField12 = ly.ConfigValues.read(type12, values.get("picture"), fileName + ":行" + lineNumber + ":字段 picture");
            parsedField13 = ly.ConfigValues.read(type13, values.get("pictureOffset"), fileName + ":行" + lineNumber + ":字段 pictureOffset");
            parsedField14 = ly.ConfigValues.read(type14, values.get("pictureOffset2"), fileName + ":行" + lineNumber + ":字段 pictureOffset2");
            parsedField15 = ly.ConfigValues.read(type15, values.get("passaWard1"), fileName + ":行" + lineNumber + ":字段 passaWard1");
            parsedField16 = ly.ConfigValues.read(type16, values.get("passaWard2"), fileName + ":行" + lineNumber + ":字段 passaWard2");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          ActivitypasscompensateConfig config = new ActivitypasscompensateConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8, parsedField9, parsedField10, parsedField11, parsedField12, parsedField13, parsedField14, parsedField15, parsedField16);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (ActivitypasscompensateConfig config : configList) config.afterLoad();
      new ActivitypasscompensateConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<ActivitypasscompensateConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, ActivitypasscompensateConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "activitypasscompensate.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
