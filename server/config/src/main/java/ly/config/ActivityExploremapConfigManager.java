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
 * File: ActivityExploremapConfigManager
 */
public class ActivityExploremapConfigManager implements InterfaceConfigManagerProxy {
  public static ActivityExploremapConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(ActivityExploremapConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new ActivityExploremapConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "activityExploremap.xml"; }

  public static class ActivityExploremapConfigManagerImpl extends AbstractConfigManger {
    private List<ActivityExploremapConfig> configList = List.of();
    private Map<Integer, ActivityExploremapConfig> configMap = Map.of();

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
      ly.ConfigValueType type8 = ly.ConfigValueType.parse("string");
      String fileName = configDir + File.separator + getConfigFileName();
      ActivityExploremapConfigChecker checker = new ActivityExploremapConfigChecker();
      List<ActivityExploremapConfig> newList = new ArrayList<>();
      Map<Integer, ActivityExploremapConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          int parsedField1 = 0;
          int parsedField2 = 0;
          int parsedField3 = 0;
          int parsedField4 = 0;
          String parsedField5 = null;
          String parsedField6 = null;
          String parsedField7 = null;
          String parsedField8 = null;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("scheDuling"), fileName + ":行" + lineNumber + ":字段 scheDuling");
            parsedField2 = ly.ConfigValues.read(type2, values.get("layers"), fileName + ":行" + lineNumber + ":字段 layers");
            parsedField3 = ly.ConfigValues.read(type3, values.get("Exploreid"), fileName + ":行" + lineNumber + ":字段 Exploreid");
            parsedField4 = ly.ConfigValues.read(type4, values.get("pictureid"), fileName + ":行" + lineNumber + ":字段 pictureid");
            parsedField5 = ly.ConfigValues.read(type5, values.get("specialPicture"), fileName + ":行" + lineNumber + ":字段 specialPicture");
            parsedField6 = ly.ConfigValues.read(type6, values.get("regionalText1"), fileName + ":行" + lineNumber + ":字段 regionalText1");
            parsedField7 = ly.ConfigValues.read(type7, values.get("regionalText2"), fileName + ":行" + lineNumber + ":字段 regionalText2");
            parsedField8 = ly.ConfigValues.read(type8, values.get("mapCoordinates"), fileName + ":行" + lineNumber + ":字段 mapCoordinates");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          ActivityExploremapConfig config = new ActivityExploremapConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (ActivityExploremapConfig config : configList) config.afterLoad();
      new ActivityExploremapConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<ActivityExploremapConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, ActivityExploremapConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "activityExploremap.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
