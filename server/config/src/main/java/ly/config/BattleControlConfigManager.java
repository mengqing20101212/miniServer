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
 * File: BattleControlConfigManager
 */
public class BattleControlConfigManager implements InterfaceConfigManagerProxy {
  public static BattleControlConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(BattleControlConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new BattleControlConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "battleControl.xml"; }

  public static class BattleControlConfigManagerImpl extends AbstractConfigManger {
    private List<BattleControlConfig> configList = List.of();
    private Map<Integer, BattleControlConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type5 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type7 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type8 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type9 = ly.ConfigValueType.parse("int");
      String fileName = configDir + File.separator + getConfigFileName();
      BattleControlConfigChecker checker = new BattleControlConfigChecker();
      List<BattleControlConfig> newList = new ArrayList<>();
      Map<Integer, BattleControlConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          String parsedField1 = null;
          int parsedField2 = 0;
          int parsedField3 = 0;
          int parsedField4 = 0;
          int parsedField5 = 0;
          int parsedField6 = 0;
          int parsedField7 = 0;
          int parsedField8 = 0;
          int parsedField9 = 0;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("name"), fileName + ":行" + lineNumber + ":字段 name");
            parsedField2 = ly.ConfigValues.read(type2, values.get("lineupId"), fileName + ":行" + lineNumber + ":字段 lineupId");
            parsedField3 = ly.ConfigValues.read(type3, values.get("isOnlineBattle"), fileName + ":行" + lineNumber + ":字段 isOnlineBattle");
            parsedField4 = ly.ConfigValues.read(type4, values.get("loadingTime"), fileName + ":行" + lineNumber + ":字段 loadingTime");
            parsedField5 = ly.ConfigValues.read(type5, values.get("readyTime"), fileName + ":行" + lineNumber + ":字段 readyTime");
            parsedField6 = ly.ConfigValues.read(type6, values.get("changeHeroTime"), fileName + ":行" + lineNumber + ":字段 changeHeroTime");
            parsedField7 = ly.ConfigValues.read(type7, values.get("isShare"), fileName + ":行" + lineNumber + ":字段 isShare");
            parsedField8 = ly.ConfigValues.read(type8, values.get("chaoiceWaitTime"), fileName + ":行" + lineNumber + ":字段 chaoiceWaitTime");
            parsedField9 = ly.ConfigValues.read(type9, values.get("isSaveDate"), fileName + ":行" + lineNumber + ":字段 isSaveDate");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          BattleControlConfig config = new BattleControlConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8, parsedField9);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (BattleControlConfig config : configList) config.afterLoad();
      new BattleControlConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<BattleControlConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, BattleControlConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "battleControl.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
