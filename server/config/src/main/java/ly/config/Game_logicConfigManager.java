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
 * File: Game_logicConfigManager
 */
public class Game_logicConfigManager implements InterfaceConfigManagerProxy {
  public static Game_logicConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(Game_logicConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new Game_logicConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "game_logic.xml"; }

  public static class Game_logicConfigManagerImpl extends AbstractConfigManger {
    private List<Game_logicConfig> configList = List.of();
    private Map<Integer, Game_logicConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("string");
      String fileName = configDir + File.separator + getConfigFileName();
      Game_logicConfigChecker checker = new Game_logicConfigChecker();
      List<Game_logicConfig> newList = new ArrayList<>();
      Map<Integer, Game_logicConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          String parsedField1 = null;
          String parsedField2 = null;
          String parsedField3 = null;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("name"), fileName + ":行" + lineNumber + ":字段 name");
            parsedField2 = ly.ConfigValues.read(type2, values.get("des"), fileName + ":行" + lineNumber + ":字段 des");
            parsedField3 = ly.ConfigValues.read(type3, values.get("value"), fileName + ":行" + lineNumber + ":字段 value");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          Game_logicConfig config = new Game_logicConfig(parsedField0, parsedField1, parsedField2, parsedField3);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (Game_logicConfig config : configList) config.afterLoad();
      new Game_logicConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<Game_logicConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, Game_logicConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "game_logic.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
