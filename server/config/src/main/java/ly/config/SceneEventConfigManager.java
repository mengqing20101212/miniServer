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
 * File: SceneEventConfigManager
 */
public class SceneEventConfigManager implements InterfaceConfigManagerProxy {
  public static SceneEventConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(SceneEventConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new SceneEventConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "sceneEvent.xml"; }

  public static class SceneEventConfigManagerImpl extends AbstractConfigManger {
    private List<SceneEventConfig> configList = List.of();
    private Map<Integer, SceneEventConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type5 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type7 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type8 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type9 = ly.ConfigValueType.parse("int");
      String fileName = configDir + File.separator + getConfigFileName();
      SceneEventConfigChecker checker = new SceneEventConfigChecker();
      List<SceneEventConfig> newList = new ArrayList<>();
      Map<Integer, SceneEventConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          String parsedField1 = null;
          String parsedField2 = null;
          String parsedField3 = null;
          int parsedField4 = 0;
          String parsedField5 = null;
          String parsedField6 = null;
          String parsedField7 = null;
          String parsedField8 = null;
          int parsedField9 = 0;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("description"), fileName + ":行" + lineNumber + ":字段 description");
            parsedField2 = ly.ConfigValues.read(type2, values.get("triggerTypeList"), fileName + ":行" + lineNumber + ":字段 triggerTypeList");
            parsedField3 = ly.ConfigValues.read(type3, values.get("treeName"), fileName + ":行" + lineNumber + ":字段 treeName");
            parsedField4 = ly.ConfigValues.read(type4, values.get("uitype"), fileName + ":行" + lineNumber + ":字段 uitype");
            parsedField5 = ly.ConfigValues.read(type5, values.get("param_1"), fileName + ":行" + lineNumber + ":字段 param_1");
            parsedField6 = ly.ConfigValues.read(type6, values.get("param_2"), fileName + ":行" + lineNumber + ":字段 param_2");
            parsedField7 = ly.ConfigValues.read(type7, values.get("param_3"), fileName + ":行" + lineNumber + ":字段 param_3");
            parsedField8 = ly.ConfigValues.read(type8, values.get("param_4"), fileName + ":行" + lineNumber + ":字段 param_4");
            parsedField9 = ly.ConfigValues.read(type9, values.get("loopNum"), fileName + ":行" + lineNumber + ":字段 loopNum");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          SceneEventConfig config = new SceneEventConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8, parsedField9);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (SceneEventConfig config : configList) config.afterLoad();
      new SceneEventConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<SceneEventConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, SceneEventConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "sceneEvent.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
