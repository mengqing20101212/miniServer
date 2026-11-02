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
 * File: SocietyPurchaseConfigManager
 */
public class SocietyPurchaseConfigManager implements InterfaceConfigManagerProxy {
  public static SocietyPurchaseConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(SocietyPurchaseConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new SocietyPurchaseConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "societyPurchase.xml"; }

  public static class SocietyPurchaseConfigManagerImpl extends AbstractConfigManger {
    private List<SocietyPurchaseConfig> configList = List.of();
    private Map<Integer, SocietyPurchaseConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type5 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type7 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type8 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type9 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type10 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type11 = ly.ConfigValueType.parse("int");
      String fileName = configDir + File.separator + getConfigFileName();
      SocietyPurchaseConfigChecker checker = new SocietyPurchaseConfigChecker();
      List<SocietyPurchaseConfig> newList = new ArrayList<>();
      Map<Integer, SocietyPurchaseConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          int parsedField1 = 0;
          int parsedField2 = 0;
          int parsedField3 = 0;
          int parsedField4 = 0;
          int parsedField5 = 0;
          int parsedField6 = 0;
          String parsedField7 = null;
          int parsedField8 = 0;
          String parsedField9 = null;
          String parsedField10 = null;
          int parsedField11 = 0;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("phase"), fileName + ":行" + lineNumber + ":字段 phase");
            parsedField2 = ly.ConfigValues.read(type2, values.get("sequence"), fileName + ":行" + lineNumber + ":字段 sequence");
            parsedField3 = ly.ConfigValues.read(type3, values.get("minLevel"), fileName + ":行" + lineNumber + ":字段 minLevel");
            parsedField4 = ly.ConfigValues.read(type4, values.get("maxLevel"), fileName + ":行" + lineNumber + ":字段 maxLevel");
            parsedField5 = ly.ConfigValues.read(type5, values.get("isRare"), fileName + ":行" + lineNumber + ":字段 isRare");
            parsedField6 = ly.ConfigValues.read(type6, values.get("demandProps"), fileName + ":行" + lineNumber + ":字段 demandProps");
            parsedField7 = ly.ConfigValues.read(type7, values.get("beizhu1"), fileName + ":行" + lineNumber + ":字段 beizhu1");
            parsedField8 = ly.ConfigValues.read(type8, values.get("demandNum"), fileName + ":行" + lineNumber + ":字段 demandNum");
            parsedField9 = ly.ConfigValues.read(type9, values.get("eachReward"), fileName + ":行" + lineNumber + ":字段 eachReward");
            parsedField10 = ly.ConfigValues.read(type10, values.get("additionalReward"), fileName + ":行" + lineNumber + ":字段 additionalReward");
            parsedField11 = ly.ConfigValues.read(type11, values.get("weights"), fileName + ":行" + lineNumber + ":字段 weights");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          SocietyPurchaseConfig config = new SocietyPurchaseConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8, parsedField9, parsedField10, parsedField11);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (SocietyPurchaseConfig config : configList) config.afterLoad();
      new SocietyPurchaseConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<SocietyPurchaseConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, SocietyPurchaseConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "societyPurchase.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
