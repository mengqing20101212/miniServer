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
 * File: PlayerLvConfigManager
 */
public class PlayerLvConfigManager implements InterfaceConfigManagerProxy {
  public static PlayerLvConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(PlayerLvConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new PlayerLvConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "playerLv.xml"; }

  public static class PlayerLvConfigManagerImpl extends AbstractConfigManger {
    private List<PlayerLvConfig> configList = List.of();
    private Map<Integer, PlayerLvConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type5 = ly.ConfigValueType.parse("int");
      String fileName = configDir + File.separator + getConfigFileName();
      PlayerLvConfigChecker checker = new PlayerLvConfigChecker();
      List<PlayerLvConfig> newList = new ArrayList<>();
      Map<Integer, PlayerLvConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          int parsedField1 = 0;
          String parsedField2 = null;
          int parsedField3 = 0;
          int parsedField4 = 0;
          int parsedField5 = 0;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("dropId"), fileName + ":行" + lineNumber + ":字段 dropId");
            parsedField2 = ly.ConfigValues.read(type2, values.get("hintId"), fileName + ":行" + lineNumber + ":字段 hintId");
            parsedField3 = ly.ConfigValues.read(type3, values.get("addStamina"), fileName + ":行" + lineNumber + ":字段 addStamina");
            parsedField4 = ly.ConfigValues.read(type4, values.get("trialTimes"), fileName + ":行" + lineNumber + ":字段 trialTimes");
            parsedField5 = ly.ConfigValues.read(type5, values.get("dropShow"), fileName + ":行" + lineNumber + ":字段 dropShow");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          PlayerLvConfig config = new PlayerLvConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (PlayerLvConfig config : configList) config.afterLoad();
      new PlayerLvConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<PlayerLvConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, PlayerLvConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "playerLv.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
