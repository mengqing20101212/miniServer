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
 * File: MainStoryShowConfigManager
 */
public class MainStoryShowConfigManager implements InterfaceConfigManagerProxy {
  public static MainStoryShowConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(MainStoryShowConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new MainStoryShowConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "mainStoryShow.xml"; }

  public static class MainStoryShowConfigManagerImpl extends AbstractConfigManger {
    private List<MainStoryShowConfig> configList = List.of();
    private Map<Integer, MainStoryShowConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("int");
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
      String fileName = configDir + File.separator + getConfigFileName();
      MainStoryShowConfigChecker checker = new MainStoryShowConfigChecker();
      List<MainStoryShowConfig> newList = new ArrayList<>();
      Map<Integer, MainStoryShowConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          String parsedField1 = null;
          int parsedField2 = 0;
          int parsedField3 = 0;
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
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("name"), fileName + ":行" + lineNumber + ":字段 name");
            parsedField2 = ly.ConfigValues.read(type2, values.get("keyPos"), fileName + ":行" + lineNumber + ":字段 keyPos");
            parsedField3 = ly.ConfigValues.read(type3, values.get("activityNpcId"), fileName + ":行" + lineNumber + ":字段 activityNpcId");
            parsedField4 = ly.ConfigValues.read(type4, values.get("keyAction"), fileName + ":行" + lineNumber + ":字段 keyAction");
            parsedField5 = ly.ConfigValues.read(type5, values.get("touchAction"), fileName + ":行" + lineNumber + ":字段 touchAction");
            parsedField6 = ly.ConfigValues.read(type6, values.get("returnAction"), fileName + ":行" + lineNumber + ":字段 returnAction");
            parsedField7 = ly.ConfigValues.read(type7, values.get("speakerName"), fileName + ":行" + lineNumber + ":字段 speakerName");
            parsedField8 = ly.ConfigValues.read(type8, values.get("question"), fileName + ":行" + lineNumber + ":字段 question");
            parsedField9 = ly.ConfigValues.read(type9, values.get("bubbleShift"), fileName + ":行" + lineNumber + ":字段 bubbleShift");
            parsedField10 = ly.ConfigValues.read(type10, values.get("questionShift"), fileName + ":行" + lineNumber + ":字段 questionShift");
            parsedField11 = ly.ConfigValues.read(type11, values.get("answerList"), fileName + ":行" + lineNumber + ":字段 answerList");
            parsedField12 = ly.ConfigValues.read(type12, values.get("reactionList"), fileName + ":行" + lineNumber + ":字段 reactionList");
            parsedField13 = ly.ConfigValues.read(type13, values.get("changeList"), fileName + ":行" + lineNumber + ":字段 changeList");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          MainStoryShowConfig config = new MainStoryShowConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8, parsedField9, parsedField10, parsedField11, parsedField12, parsedField13);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (MainStoryShowConfig config : configList) config.afterLoad();
      new MainStoryShowConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<MainStoryShowConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, MainStoryShowConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "mainStoryShow.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
