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
 * File: BonusEffectConfigManager
 */
public class BonusEffectConfigManager implements InterfaceConfigManagerProxy {
  public static BonusEffectConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(BonusEffectConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new BonusEffectConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "bonusEffect.xml"; }

  public static class BonusEffectConfigManagerImpl extends AbstractConfigManger {
    private List<BonusEffectConfig> configList = List.of();
    private Map<Integer, BonusEffectConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("bool");
      ly.ConfigValueType type5 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type7 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type8 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type9 = ly.ConfigValueType.parse("int");
      String fileName = configDir + File.separator + getConfigFileName();
      BonusEffectConfigChecker checker = new BonusEffectConfigChecker();
      List<BonusEffectConfig> newList = new ArrayList<>();
      Map<Integer, BonusEffectConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          String parsedField1 = null;
          int parsedField2 = 0;
          int parsedField3 = 0;
          boolean parsedField4 = false;
          String parsedField5 = null;
          String parsedField6 = null;
          String parsedField7 = null;
          int parsedField8 = 0;
          int parsedField9 = 0;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("description"), fileName + ":行" + lineNumber + ":字段 description");
            parsedField2 = ly.ConfigValues.read(type2, values.get("icon"), fileName + ":行" + lineNumber + ":字段 icon");
            parsedField3 = ly.ConfigValues.read(type3, values.get("bonusEffect"), fileName + ":行" + lineNumber + ":字段 bonusEffect");
            parsedField4 = ly.ConfigValues.read(type4, values.get("isBonusBuff"), fileName + ":行" + lineNumber + ":字段 isBonusBuff");
            parsedField5 = ly.ConfigValues.read(type5, values.get("bonusType"), fileName + ":行" + lineNumber + ":字段 bonusType");
            parsedField6 = ly.ConfigValues.read(type6, values.get("param_1"), fileName + ":行" + lineNumber + ":字段 param_1");
            parsedField7 = ly.ConfigValues.read(type7, values.get("isVanish"), fileName + ":行" + lineNumber + ":字段 isVanish");
            parsedField8 = ly.ConfigValues.read(type8, values.get("group"), fileName + ":行" + lineNumber + ":字段 group");
            parsedField9 = ly.ConfigValues.read(type9, values.get("level"), fileName + ":行" + lineNumber + ":字段 level");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          BonusEffectConfig config = new BonusEffectConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8, parsedField9);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (BonusEffectConfig config : configList) config.afterLoad();
      new BonusEffectConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<BonusEffectConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, BonusEffectConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "bonusEffect.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
