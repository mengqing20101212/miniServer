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
 * File: SceneStrategyConfigManager
 */
public class SceneStrategyConfigManager implements InterfaceConfigManagerProxy {
  public static SceneStrategyConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(SceneStrategyConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new SceneStrategyConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "sceneStrategy.xml"; }

  public static class SceneStrategyConfigManagerImpl extends AbstractConfigManger {
    private List<SceneStrategyConfig> configList = List.of();
    private Map<Integer, SceneStrategyConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type5 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type7 = ly.ConfigValueType.parse("int");
      String fileName = configDir + File.separator + getConfigFileName();
      SceneStrategyConfigChecker checker = new SceneStrategyConfigChecker();
      List<SceneStrategyConfig> newList = new ArrayList<>();
      Map<Integer, SceneStrategyConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          String parsedField1 = null;
          int parsedField2 = 0;
          String parsedField3 = null;
          String parsedField4 = null;
          int parsedField5 = 0;
          int parsedField6 = 0;
          int parsedField7 = 0;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("beizhu"), fileName + ":行" + lineNumber + ":字段 beizhu");
            parsedField2 = ly.ConfigValues.read(type2, values.get("npcId"), fileName + ":行" + lineNumber + ":字段 npcId");
            parsedField3 = ly.ConfigValues.read(type3, values.get("strategyDec"), fileName + ":行" + lineNumber + ":字段 strategyDec");
            parsedField4 = ly.ConfigValues.read(type4, values.get("heroId"), fileName + ":行" + lineNumber + ":字段 heroId");
            parsedField5 = ly.ConfigValues.read(type5, values.get("nextId"), fileName + ":行" + lineNumber + ":字段 nextId");
            parsedField6 = ly.ConfigValues.read(type6, values.get("lastId"), fileName + ":行" + lineNumber + ":字段 lastId");
            parsedField7 = ly.ConfigValues.read(type7, values.get("sceneGroup"), fileName + ":行" + lineNumber + ":字段 sceneGroup");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          SceneStrategyConfig config = new SceneStrategyConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (SceneStrategyConfig config : configList) config.afterLoad();
      new SceneStrategyConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<SceneStrategyConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, SceneStrategyConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "sceneStrategy.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
