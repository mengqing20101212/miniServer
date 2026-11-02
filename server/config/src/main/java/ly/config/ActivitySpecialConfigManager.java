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
 * File: ActivitySpecialConfigManager
 */
public class ActivitySpecialConfigManager implements InterfaceConfigManagerProxy {
  public static ActivitySpecialConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(ActivitySpecialConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new ActivitySpecialConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "activitySpecial.xml"; }

  public static class ActivitySpecialConfigManagerImpl extends AbstractConfigManger {
    private List<ActivitySpecialConfig> configList = List.of();
    private Map<Integer, ActivitySpecialConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type5 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type7 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type8 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type9 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type10 = ly.ConfigValueType.parse("int");
      String fileName = configDir + File.separator + getConfigFileName();
      ActivitySpecialConfigChecker checker = new ActivitySpecialConfigChecker();
      List<ActivitySpecialConfig> newList = new ArrayList<>();
      Map<Integer, ActivitySpecialConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          int parsedField1 = 0;
          String parsedField2 = null;
          int parsedField3 = 0;
          int parsedField4 = 0;
          String parsedField5 = null;
          String parsedField6 = null;
          int parsedField7 = 0;
          int parsedField8 = 0;
          int parsedField9 = 0;
          int parsedField10 = 0;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("scheDuling"), fileName + ":行" + lineNumber + ":字段 scheDuling");
            parsedField2 = ly.ConfigValues.read(type2, values.get("name"), fileName + ":行" + lineNumber + ":字段 name");
            parsedField3 = ly.ConfigValues.read(type3, values.get("heroShowStyleType"), fileName + ":行" + lineNumber + ":字段 heroShowStyleType");
            parsedField4 = ly.ConfigValues.read(type4, values.get("rolePic"), fileName + ":行" + lineNumber + ":字段 rolePic");
            parsedField5 = ly.ConfigValues.read(type5, values.get("rolePicPosOffset"), fileName + ":行" + lineNumber + ":字段 rolePicPosOffset");
            parsedField6 = ly.ConfigValues.read(type6, values.get("rolePicScaleOffset"), fileName + ":行" + lineNumber + ":字段 rolePicScaleOffset");
            parsedField7 = ly.ConfigValues.read(type7, values.get("titleResID"), fileName + ":行" + lineNumber + ":字段 titleResID");
            parsedField8 = ly.ConfigValues.read(type8, values.get("bgPic"), fileName + ":行" + lineNumber + ":字段 bgPic");
            parsedField9 = ly.ConfigValues.read(type9, values.get("holographic"), fileName + ":行" + lineNumber + ":字段 holographic");
            parsedField10 = ly.ConfigValues.read(type10, values.get("trueActivityId"), fileName + ":行" + lineNumber + ":字段 trueActivityId");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          ActivitySpecialConfig config = new ActivitySpecialConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8, parsedField9, parsedField10);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (ActivitySpecialConfig config : configList) config.afterLoad();
      new ActivitySpecialConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<ActivitySpecialConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, ActivitySpecialConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "activitySpecial.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
