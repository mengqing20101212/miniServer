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
 * File: MainStoryConfigManager
 */
public class MainStoryConfigManager implements InterfaceConfigManagerProxy {
  public static MainStoryConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(MainStoryConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new MainStoryConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "mainStory.xml"; }

  public static class MainStoryConfigManagerImpl extends AbstractConfigManger {
    private List<MainStoryConfig> configList = List.of();
    private Map<Integer, MainStoryConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type5 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type7 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type8 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type9 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type10 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type11 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type12 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type13 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type14 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type15 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type16 = ly.ConfigValueType.parse("int");
      String fileName = configDir + File.separator + getConfigFileName();
      MainStoryConfigChecker checker = new MainStoryConfigChecker();
      List<MainStoryConfig> newList = new ArrayList<>();
      Map<Integer, MainStoryConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          String parsedField1 = null;
          String parsedField2 = null;
          String parsedField3 = null;
          String parsedField4 = null;
          int parsedField5 = 0;
          int parsedField6 = 0;
          int parsedField7 = 0;
          int parsedField8 = 0;
          int parsedField9 = 0;
          int parsedField10 = 0;
          int parsedField11 = 0;
          String parsedField12 = null;
          String parsedField13 = null;
          int parsedField14 = 0;
          int parsedField15 = 0;
          int parsedField16 = 0;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("name"), fileName + ":行" + lineNumber + ":字段 name");
            parsedField2 = ly.ConfigValues.read(type2, values.get("titile"), fileName + ":行" + lineNumber + ":字段 titile");
            parsedField3 = ly.ConfigValues.read(type3, values.get("target"), fileName + ":行" + lineNumber + ":字段 target");
            parsedField4 = ly.ConfigValues.read(type4, values.get("eventIds"), fileName + ":行" + lineNumber + ":字段 eventIds");
            parsedField5 = ly.ConfigValues.read(type5, values.get("chapterRes"), fileName + ":行" + lineNumber + ":字段 chapterRes");
            parsedField6 = ly.ConfigValues.read(type6, values.get("chapterNum"), fileName + ":行" + lineNumber + ":字段 chapterNum");
            parsedField7 = ly.ConfigValues.read(type7, values.get("chapterId"), fileName + ":行" + lineNumber + ":字段 chapterId");
            parsedField8 = ly.ConfigValues.read(type8, values.get("requireLv"), fileName + ":行" + lineNumber + ":字段 requireLv");
            parsedField9 = ly.ConfigValues.read(type9, values.get("preIds"), fileName + ":行" + lineNumber + ":字段 preIds");
            parsedField10 = ly.ConfigValues.read(type10, values.get("followID"), fileName + ":行" + lineNumber + ":字段 followID");
            parsedField11 = ly.ConfigValues.read(type11, values.get("dropGroupId"), fileName + ":行" + lineNumber + ":字段 dropGroupId");
            parsedField12 = ly.ConfigValues.read(type12, values.get("dropshow"), fileName + ":行" + lineNumber + ":字段 dropshow");
            parsedField13 = ly.ConfigValues.read(type13, values.get("lockWord"), fileName + ":行" + lineNumber + ":字段 lockWord");
            parsedField14 = ly.ConfigValues.read(type14, values.get("pic"), fileName + ":行" + lineNumber + ":字段 pic");
            parsedField15 = ly.ConfigValues.read(type15, values.get("showId"), fileName + ":行" + lineNumber + ":字段 showId");
            parsedField16 = ly.ConfigValues.read(type16, values.get("pic2"), fileName + ":行" + lineNumber + ":字段 pic2");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          MainStoryConfig config = new MainStoryConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8, parsedField9, parsedField10, parsedField11, parsedField12, parsedField13, parsedField14, parsedField15, parsedField16);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (MainStoryConfig config : configList) config.afterLoad();
      new MainStoryConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<MainStoryConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, MainStoryConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "mainStory.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
