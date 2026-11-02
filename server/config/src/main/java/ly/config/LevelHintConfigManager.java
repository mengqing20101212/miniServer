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
 * File: LevelHintConfigManager
 */
public class LevelHintConfigManager implements InterfaceConfigManagerProxy {
  public static LevelHintConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(LevelHintConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new LevelHintConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "levelHint.xml"; }

  public static class LevelHintConfigManagerImpl extends AbstractConfigManger {
    private List<LevelHintConfig> configList = List.of();
    private Map<Integer, LevelHintConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("string");
      String fileName = configDir + File.separator + getConfigFileName();
      LevelHintConfigChecker checker = new LevelHintConfigChecker();
      List<LevelHintConfig> newList = new ArrayList<>();
      Map<Integer, LevelHintConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          int parsedField1 = 0;
          String parsedField2 = null;
          String parsedField3 = null;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("icon"), fileName + ":行" + lineNumber + ":字段 icon");
            parsedField2 = ly.ConfigValues.read(type2, values.get("hint"), fileName + ":行" + lineNumber + ":字段 hint");
            parsedField3 = ly.ConfigValues.read(type3, values.get("des"), fileName + ":行" + lineNumber + ":字段 des");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          LevelHintConfig config = new LevelHintConfig(parsedField0, parsedField1, parsedField2, parsedField3);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (LevelHintConfig config : configList) config.afterLoad();
      new LevelHintConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<LevelHintConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, LevelHintConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "levelHint.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
