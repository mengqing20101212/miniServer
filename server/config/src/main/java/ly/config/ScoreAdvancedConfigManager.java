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
 * File: ScoreAdvancedConfigManager
 */
public class ScoreAdvancedConfigManager implements InterfaceConfigManagerProxy {
  public static ScoreAdvancedConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(ScoreAdvancedConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new ScoreAdvancedConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "scoreAdvanced.xml"; }

  public static class ScoreAdvancedConfigManagerImpl extends AbstractConfigManger {
    private List<ScoreAdvancedConfig> configList = List.of();
    private Map<Integer, ScoreAdvancedConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("int");
      String fileName = configDir + File.separator + getConfigFileName();
      ScoreAdvancedConfigChecker checker = new ScoreAdvancedConfigChecker();
      List<ScoreAdvancedConfig> newList = new ArrayList<>();
      Map<Integer, ScoreAdvancedConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          int parsedField1 = 0;
          int parsedField2 = 0;
          int parsedField3 = 0;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("quality"), fileName + ":行" + lineNumber + ":字段 quality");
            parsedField2 = ly.ConfigValues.read(type2, values.get("advanced"), fileName + ":行" + lineNumber + ":字段 advanced");
            parsedField3 = ly.ConfigValues.read(type3, values.get("score"), fileName + ":行" + lineNumber + ":字段 score");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          ScoreAdvancedConfig config = new ScoreAdvancedConfig(parsedField0, parsedField1, parsedField2, parsedField3);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (ScoreAdvancedConfig config : configList) config.afterLoad();
      new ScoreAdvancedConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<ScoreAdvancedConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, ScoreAdvancedConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "scoreAdvanced.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
