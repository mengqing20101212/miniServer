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
 * File: ElectronicThreeHeroConfigManager
 */
public class ElectronicThreeHeroConfigManager implements InterfaceConfigManagerProxy {
  public static ElectronicThreeHeroConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(ElectronicThreeHeroConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new ElectronicThreeHeroConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "electronicThreeHero.xml"; }

  public static class ElectronicThreeHeroConfigManagerImpl extends AbstractConfigManger {
    private List<ElectronicThreeHeroConfig> configList = List.of();
    private Map<Integer, ElectronicThreeHeroConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type5 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type7 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type8 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type9 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type10 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type11 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type12 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type13 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type14 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type15 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type16 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type17 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type18 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type19 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type20 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type21 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type22 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type23 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type24 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type25 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type26 = ly.ConfigValueType.parse("int");
      String fileName = configDir + File.separator + getConfigFileName();
      ElectronicThreeHeroConfigChecker checker = new ElectronicThreeHeroConfigChecker();
      List<ElectronicThreeHeroConfig> newList = new ArrayList<>();
      Map<Integer, ElectronicThreeHeroConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          int parsedField1 = 0;
          String parsedField2 = null;
          int parsedField3 = 0;
          int parsedField4 = 0;
          String parsedField5 = null;
          int parsedField6 = 0;
          String parsedField7 = null;
          int parsedField8 = 0;
          String parsedField9 = null;
          int parsedField10 = 0;
          String parsedField11 = null;
          int parsedField12 = 0;
          String parsedField13 = null;
          int parsedField14 = 0;
          String parsedField15 = null;
          int parsedField16 = 0;
          String parsedField17 = null;
          int parsedField18 = 0;
          int parsedField19 = 0;
          int parsedField20 = 0;
          int parsedField21 = 0;
          int parsedField22 = 0;
          int parsedField23 = 0;
          int parsedField24 = 0;
          int parsedField25 = 0;
          int parsedField26 = 0;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("heroId"), fileName + ":行" + lineNumber + ":字段 heroId");
            parsedField2 = ly.ConfigValues.read(type2, values.get("heroDesc"), fileName + ":行" + lineNumber + ":字段 heroDesc");
            parsedField3 = ly.ConfigValues.read(type3, values.get("pro"), fileName + ":行" + lineNumber + ":字段 pro");
            parsedField4 = ly.ConfigValues.read(type4, values.get("group"), fileName + ":行" + lineNumber + ":字段 group");
            parsedField5 = ly.ConfigValues.read(type5, values.get("circuitAttr1"), fileName + ":行" + lineNumber + ":字段 circuitAttr1");
            parsedField6 = ly.ConfigValues.read(type6, values.get("circuit2"), fileName + ":行" + lineNumber + ":字段 circuit2");
            parsedField7 = ly.ConfigValues.read(type7, values.get("circuitAttr2"), fileName + ":行" + lineNumber + ":字段 circuitAttr2");
            parsedField8 = ly.ConfigValues.read(type8, values.get("circuit3"), fileName + ":行" + lineNumber + ":字段 circuit3");
            parsedField9 = ly.ConfigValues.read(type9, values.get("circuitAttr3"), fileName + ":行" + lineNumber + ":字段 circuitAttr3");
            parsedField10 = ly.ConfigValues.read(type10, values.get("circuit4"), fileName + ":行" + lineNumber + ":字段 circuit4");
            parsedField11 = ly.ConfigValues.read(type11, values.get("circuitAttr4"), fileName + ":行" + lineNumber + ":字段 circuitAttr4");
            parsedField12 = ly.ConfigValues.read(type12, values.get("circuit5"), fileName + ":行" + lineNumber + ":字段 circuit5");
            parsedField13 = ly.ConfigValues.read(type13, values.get("circuitAttr5"), fileName + ":行" + lineNumber + ":字段 circuitAttr5");
            parsedField14 = ly.ConfigValues.read(type14, values.get("circuit6"), fileName + ":行" + lineNumber + ":字段 circuit6");
            parsedField15 = ly.ConfigValues.read(type15, values.get("circuitAttr6"), fileName + ":行" + lineNumber + ":字段 circuitAttr6");
            parsedField16 = ly.ConfigValues.read(type16, values.get("circuit7"), fileName + ":行" + lineNumber + ":字段 circuit7");
            parsedField17 = ly.ConfigValues.read(type17, values.get("circuitAttr7"), fileName + ":行" + lineNumber + ":字段 circuitAttr7");
            parsedField18 = ly.ConfigValues.read(type18, values.get("maxHP"), fileName + ":行" + lineNumber + ":字段 maxHP");
            parsedField19 = ly.ConfigValues.read(type19, values.get("attack"), fileName + ":行" + lineNumber + ":字段 attack");
            parsedField20 = ly.ConfigValues.read(type20, values.get("defence"), fileName + ":行" + lineNumber + ":字段 defence");
            parsedField21 = ly.ConfigValues.read(type21, values.get("speed"), fileName + ":行" + lineNumber + ":字段 speed");
            parsedField22 = ly.ConfigValues.read(type22, values.get("crit"), fileName + ":行" + lineNumber + ":字段 crit");
            parsedField23 = ly.ConfigValues.read(type23, values.get("critRatio"), fileName + ":行" + lineNumber + ":字段 critRatio");
            parsedField24 = ly.ConfigValues.read(type24, values.get("effectHit"), fileName + ":行" + lineNumber + ":字段 effectHit");
            parsedField25 = ly.ConfigValues.read(type25, values.get("effectDodge"), fileName + ":行" + lineNumber + ":字段 effectDodge");
            parsedField26 = ly.ConfigValues.read(type26, values.get("spCoe"), fileName + ":行" + lineNumber + ":字段 spCoe");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          ElectronicThreeHeroConfig config = new ElectronicThreeHeroConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8, parsedField9, parsedField10, parsedField11, parsedField12, parsedField13, parsedField14, parsedField15, parsedField16, parsedField17, parsedField18, parsedField19, parsedField20, parsedField21, parsedField22, parsedField23, parsedField24, parsedField25, parsedField26);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (ElectronicThreeHeroConfig config : configList) config.afterLoad();
      new ElectronicThreeHeroConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<ElectronicThreeHeroConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, ElectronicThreeHeroConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "electronicThreeHero.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
