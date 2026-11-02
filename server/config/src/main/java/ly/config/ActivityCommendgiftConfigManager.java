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
 * File: ActivityCommendgiftConfigManager
 */
public class ActivityCommendgiftConfigManager implements InterfaceConfigManagerProxy {
  public static ActivityCommendgiftConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(ActivityCommendgiftConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new ActivityCommendgiftConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "activityCommendgift.xml"; }

  public static class ActivityCommendgiftConfigManagerImpl extends AbstractConfigManger {
    private List<ActivityCommendgiftConfig> configList = List.of();
    private Map<Integer, ActivityCommendgiftConfig> configMap = Map.of();

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
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("int");
      String fileName = configDir + File.separator + getConfigFileName();
      ActivityCommendgiftConfigChecker checker = new ActivityCommendgiftConfigChecker();
      List<ActivityCommendgiftConfig> newList = new ArrayList<>();
      Map<Integer, ActivityCommendgiftConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          int parsedField1 = 0;
          int parsedField2 = 0;
          int parsedField3 = 0;
          int parsedField4 = 0;
          int parsedField5 = 0;
          int parsedField6 = 0;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("CommendLevel"), fileName + ":行" + lineNumber + ":字段 CommendLevel");
            parsedField2 = ly.ConfigValues.read(type2, values.get("Commendtype"), fileName + ":行" + lineNumber + ":字段 Commendtype");
            parsedField3 = ly.ConfigValues.read(type3, values.get("RewardShow"), fileName + ":行" + lineNumber + ":字段 RewardShow");
            parsedField4 = ly.ConfigValues.read(type4, values.get("drop"), fileName + ":行" + lineNumber + ":字段 drop");
            parsedField5 = ly.ConfigValues.read(type5, values.get("rechargShow"), fileName + ":行" + lineNumber + ":字段 rechargShow");
            parsedField6 = ly.ConfigValues.read(type6, values.get("rechargdrop"), fileName + ":行" + lineNumber + ":字段 rechargdrop");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          ActivityCommendgiftConfig config = new ActivityCommendgiftConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (ActivityCommendgiftConfig config : configList) config.afterLoad();
      new ActivityCommendgiftConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<ActivityCommendgiftConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, ActivityCommendgiftConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "activityCommendgift.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
