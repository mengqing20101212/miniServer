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
 * File: ActivitypassmissionConfigManager
 */
public class ActivitypassmissionConfigManager implements InterfaceConfigManagerProxy {
  public static ActivitypassmissionConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(ActivitypassmissionConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new ActivitypassmissionConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "activitypassmission.xml"; }

  public static class ActivitypassmissionConfigManagerImpl extends AbstractConfigManger {
    private List<ActivitypassmissionConfig> configList = List.of();
    private Map<Integer, ActivitypassmissionConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type5 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type7 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type8 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type9 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type10 = ly.ConfigValueType.parse("int");
      String fileName = configDir + File.separator + getConfigFileName();
      ActivitypassmissionConfigChecker checker = new ActivitypassmissionConfigChecker();
      List<ActivitypassmissionConfig> newList = new ArrayList<>();
      Map<Integer, ActivitypassmissionConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          int parsedField1 = 0;
          int parsedField2 = 0;
          int parsedField3 = 0;
          int parsedField4 = 0;
          int parsedField5 = 0;
          String parsedField6 = null;
          int parsedField7 = 0;
          int parsedField8 = 0;
          int parsedField9 = 0;
          int parsedField10 = 0;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("scheDuling"), fileName + ":行" + lineNumber + ":字段 scheDuling");
            parsedField2 = ly.ConfigValues.read(type2, values.get("questId"), fileName + ":行" + lineNumber + ":字段 questId");
            parsedField3 = ly.ConfigValues.read(type3, values.get("missinType"), fileName + ":行" + lineNumber + ":字段 missinType");
            parsedField4 = ly.ConfigValues.read(type4, values.get("minLevel"), fileName + ":行" + lineNumber + ":字段 minLevel");
            parsedField5 = ly.ConfigValues.read(type5, values.get("maxLevel"), fileName + ":行" + lineNumber + ":字段 maxLevel");
            parsedField6 = ly.ConfigValues.read(type6, values.get("name"), fileName + ":行" + lineNumber + ":字段 name");
            parsedField7 = ly.ConfigValues.read(type7, values.get("point"), fileName + ":行" + lineNumber + ":字段 point");
            parsedField8 = ly.ConfigValues.read(type8, values.get("redirectionId"), fileName + ":行" + lineNumber + ":字段 redirectionId");
            parsedField9 = ly.ConfigValues.read(type9, values.get("goundId"), fileName + ":行" + lineNumber + ":字段 goundId");
            parsedField10 = ly.ConfigValues.read(type10, values.get("weights"), fileName + ":行" + lineNumber + ":字段 weights");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          ActivitypassmissionConfig config = new ActivitypassmissionConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8, parsedField9, parsedField10);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (ActivitypassmissionConfig config : configList) config.afterLoad();
      new ActivitypassmissionConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<ActivitypassmissionConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, ActivitypassmissionConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "activitypassmission.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
