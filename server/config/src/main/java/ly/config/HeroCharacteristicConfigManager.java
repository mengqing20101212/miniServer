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
 * File: HeroCharacteristicConfigManager
 */
public class HeroCharacteristicConfigManager implements InterfaceConfigManagerProxy {
  public static HeroCharacteristicConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(HeroCharacteristicConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new HeroCharacteristicConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "heroCharacteristic.xml"; }

  public static class HeroCharacteristicConfigManagerImpl extends AbstractConfigManger {
    private List<HeroCharacteristicConfig> configList = List.of();
    private Map<Integer, HeroCharacteristicConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("list<int>");
      ly.ConfigValueType type5 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type7 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type8 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type9 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type10 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type11 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type12 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type13 = ly.ConfigValueType.parse("string");
      String fileName = configDir + File.separator + getConfigFileName();
      HeroCharacteristicConfigChecker checker = new HeroCharacteristicConfigChecker();
      List<HeroCharacteristicConfig> newList = new ArrayList<>();
      Map<Integer, HeroCharacteristicConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          String parsedField1 = null;
          int parsedField2 = 0;
          int parsedField3 = 0;
          java.util.List<Integer> parsedField4 = null;
          int parsedField5 = 0;
          String parsedField6 = null;
          String parsedField7 = null;
          String parsedField8 = null;
          String parsedField9 = null;
          int parsedField10 = 0;
          int parsedField11 = 0;
          String parsedField12 = null;
          String parsedField13 = null;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("beizhu"), fileName + ":行" + lineNumber + ":字段 beizhu");
            parsedField2 = ly.ConfigValues.read(type2, values.get("heroId"), fileName + ":行" + lineNumber + ":字段 heroId");
            parsedField3 = ly.ConfigValues.read(type3, values.get("skillGroup"), fileName + ":行" + lineNumber + ":字段 skillGroup");
            parsedField4 = ly.ConfigValues.read(type4, values.get("heroFetters"), fileName + ":行" + lineNumber + ":字段 heroFetters");
            parsedField5 = ly.ConfigValues.read(type5, values.get("advanced"), fileName + ":行" + lineNumber + ":字段 advanced");
            parsedField6 = ly.ConfigValues.read(type6, values.get("quality"), fileName + ":行" + lineNumber + ":字段 quality");
            parsedField7 = ly.ConfigValues.read(type7, values.get("qualityNum"), fileName + ":行" + lineNumber + ":字段 qualityNum");
            parsedField8 = ly.ConfigValues.read(type8, values.get("heroType"), fileName + ":行" + lineNumber + ":字段 heroType");
            parsedField9 = ly.ConfigValues.read(type9, values.get("heroTypeNum"), fileName + ":行" + lineNumber + ":字段 heroTypeNum");
            parsedField10 = ly.ConfigValues.read(type10, values.get("characterType"), fileName + ":行" + lineNumber + ":字段 characterType");
            parsedField11 = ly.ConfigValues.read(type11, values.get("characterNum"), fileName + ":行" + lineNumber + ":字段 characterNum");
            parsedField12 = ly.ConfigValues.read(type12, values.get("activationDes"), fileName + ":行" + lineNumber + ":字段 activationDes");
            parsedField13 = ly.ConfigValues.read(type13, values.get("des"), fileName + ":行" + lineNumber + ":字段 des");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          HeroCharacteristicConfig config = new HeroCharacteristicConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8, parsedField9, parsedField10, parsedField11, parsedField12, parsedField13);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (HeroCharacteristicConfig config : configList) config.afterLoad();
      new HeroCharacteristicConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<HeroCharacteristicConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, HeroCharacteristicConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "heroCharacteristic.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
