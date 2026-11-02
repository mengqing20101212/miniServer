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
 * File: ActivityshareConfigManager
 */
public class ActivityshareConfigManager implements InterfaceConfigManagerProxy {
  public static ActivityshareConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(ActivityshareConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new ActivityshareConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "activityshare.xml"; }

  public static class ActivityshareConfigManagerImpl extends AbstractConfigManger {
    private List<ActivityshareConfig> configList = List.of();
    private Map<Integer, ActivityshareConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type5 = ly.ConfigValueType.parse("string");
      String fileName = configDir + File.separator + getConfigFileName();
      ActivityshareConfigChecker checker = new ActivityshareConfigChecker();
      List<ActivityshareConfig> newList = new ArrayList<>();
      Map<Integer, ActivityshareConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          String parsedField1 = null;
          int parsedField2 = 0;
          int parsedField3 = 0;
          int parsedField4 = 0;
          String parsedField5 = null;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("beizhu"), fileName + ":行" + lineNumber + ":字段 beizhu");
            parsedField2 = ly.ConfigValues.read(type2, values.get("share_type"), fileName + ":行" + lineNumber + ":字段 share_type");
            parsedField3 = ly.ConfigValues.read(type3, values.get("drop"), fileName + ":行" + lineNumber + ":字段 drop");
            parsedField4 = ly.ConfigValues.read(type4, values.get("dropShow"), fileName + ":行" + lineNumber + ":字段 dropShow");
            parsedField5 = ly.ConfigValues.read(type5, values.get("name"), fileName + ":行" + lineNumber + ":字段 name");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          ActivityshareConfig config = new ActivityshareConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (ActivityshareConfig config : configList) config.afterLoad();
      new ActivityshareConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<ActivityshareConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, ActivityshareConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "activityshare.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
