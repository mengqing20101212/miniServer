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
 * File: BattleFieldConfigManager
 */
public class BattleFieldConfigManager implements InterfaceConfigManagerProxy {
  public static BattleFieldConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(BattleFieldConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new BattleFieldConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "battleField.xml"; }

  public static class BattleFieldConfigManagerImpl extends AbstractConfigManger {
    private List<BattleFieldConfig> configList = List.of();
    private Map<Integer, BattleFieldConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("string");
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
      ly.ConfigValueType type14 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type15 = ly.ConfigValueType.parse("int");
      String fileName = configDir + File.separator + getConfigFileName();
      BattleFieldConfigChecker checker = new BattleFieldConfigChecker();
      List<BattleFieldConfig> newList = new ArrayList<>();
      Map<Integer, BattleFieldConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          String parsedField1 = null;
          String parsedField2 = null;
          String parsedField3 = null;
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
          int parsedField14 = 0;
          int parsedField15 = 0;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("posListRed"), fileName + ":行" + lineNumber + ":字段 posListRed");
            parsedField2 = ly.ConfigValues.read(type2, values.get("posListBlue"), fileName + ":行" + lineNumber + ":字段 posListBlue");
            parsedField3 = ly.ConfigValues.read(type3, values.get("frontAllRed"), fileName + ":行" + lineNumber + ":字段 frontAllRed");
            parsedField4 = ly.ConfigValues.read(type4, values.get("frontAllBlue"), fileName + ":行" + lineNumber + ":字段 frontAllBlue");
            parsedField5 = ly.ConfigValues.read(type5, values.get("BFCenterRed"), fileName + ":行" + lineNumber + ":字段 BFCenterRed");
            parsedField6 = ly.ConfigValues.read(type6, values.get("BFCenterBlue"), fileName + ":行" + lineNumber + ":字段 BFCenterBlue");
            parsedField7 = ly.ConfigValues.read(type7, values.get("BFCenter"), fileName + ":行" + lineNumber + ":字段 BFCenter");
            parsedField8 = ly.ConfigValues.read(type8, values.get("cameraListRed"), fileName + ":行" + lineNumber + ":字段 cameraListRed");
            parsedField9 = ly.ConfigValues.read(type9, values.get("cameraListBlue"), fileName + ":行" + lineNumber + ":字段 cameraListBlue");
            parsedField10 = ly.ConfigValues.read(type10, values.get("posListRedSummon"), fileName + ":行" + lineNumber + ":字段 posListRedSummon");
            parsedField11 = ly.ConfigValues.read(type11, values.get("posListBlueSummon"), fileName + ":行" + lineNumber + ":字段 posListBlueSummon");
            parsedField12 = ly.ConfigValues.read(type12, values.get("sceneConfig"), fileName + ":行" + lineNumber + ":字段 sceneConfig");
            parsedField13 = ly.ConfigValues.read(type13, values.get("sceneConfigEnemy"), fileName + ":行" + lineNumber + ":字段 sceneConfigEnemy");
            parsedField14 = ly.ConfigValues.read(type14, values.get("lineupType"), fileName + ":行" + lineNumber + ":字段 lineupType");
            parsedField15 = ly.ConfigValues.read(type15, values.get("lineupTypeEnemy"), fileName + ":行" + lineNumber + ":字段 lineupTypeEnemy");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          BattleFieldConfig config = new BattleFieldConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8, parsedField9, parsedField10, parsedField11, parsedField12, parsedField13, parsedField14, parsedField15);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (BattleFieldConfig config : configList) config.afterLoad();
      new BattleFieldConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<BattleFieldConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, BattleFieldConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "battleField.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
