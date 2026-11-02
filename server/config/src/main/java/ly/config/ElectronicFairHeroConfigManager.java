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
 * File: ElectronicFairHeroConfigManager
 */
public class ElectronicFairHeroConfigManager implements InterfaceConfigManagerProxy {
  public static ElectronicFairHeroConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(ElectronicFairHeroConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new ElectronicFairHeroConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "electronicFairHero.xml"; }

  public static class ElectronicFairHeroConfigManagerImpl extends AbstractConfigManger {
    private List<ElectronicFairHeroConfig> configList = List.of();
    private Map<Integer, ElectronicFairHeroConfig> configMap = Map.of();

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
      ly.ConfigValueType type7 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type8 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type9 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type10 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type11 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type12 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type13 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type14 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type15 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type16 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type17 = ly.ConfigValueType.parse("int");
      String fileName = configDir + File.separator + getConfigFileName();
      ElectronicFairHeroConfigChecker checker = new ElectronicFairHeroConfigChecker();
      List<ElectronicFairHeroConfig> newList = new ArrayList<>();
      Map<Integer, ElectronicFairHeroConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          int parsedField1 = 0;
          int parsedField2 = 0;
          int parsedField3 = 0;
          int parsedField4 = 0;
          int parsedField5 = 0;
          int parsedField6 = 0;
          int parsedField7 = 0;
          int parsedField8 = 0;
          int parsedField9 = 0;
          int parsedField10 = 0;
          int parsedField11 = 0;
          int parsedField12 = 0;
          int parsedField13 = 0;
          int parsedField14 = 0;
          int parsedField15 = 0;
          int parsedField16 = 0;
          int parsedField17 = 0;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("heroId"), fileName + ":行" + lineNumber + ":字段 heroId");
            parsedField2 = ly.ConfigValues.read(type2, values.get("Level"), fileName + ":行" + lineNumber + ":字段 Level");
            parsedField3 = ly.ConfigValues.read(type3, values.get("star"), fileName + ":行" + lineNumber + ":字段 star");
            parsedField4 = ly.ConfigValues.read(type4, values.get("awaken"), fileName + ":行" + lineNumber + ":字段 awaken");
            parsedField5 = ly.ConfigValues.read(type5, values.get("skill1"), fileName + ":行" + lineNumber + ":字段 skill1");
            parsedField6 = ly.ConfigValues.read(type6, values.get("skill2"), fileName + ":行" + lineNumber + ":字段 skill2");
            parsedField7 = ly.ConfigValues.read(type7, values.get("skill3"), fileName + ":行" + lineNumber + ":字段 skill3");
            parsedField8 = ly.ConfigValues.read(type8, values.get("skillS"), fileName + ":行" + lineNumber + ":字段 skillS");
            parsedField9 = ly.ConfigValues.read(type9, values.get("maxHP"), fileName + ":行" + lineNumber + ":字段 maxHP");
            parsedField10 = ly.ConfigValues.read(type10, values.get("attack"), fileName + ":行" + lineNumber + ":字段 attack");
            parsedField11 = ly.ConfigValues.read(type11, values.get("defence"), fileName + ":行" + lineNumber + ":字段 defence");
            parsedField12 = ly.ConfigValues.read(type12, values.get("speed"), fileName + ":行" + lineNumber + ":字段 speed");
            parsedField13 = ly.ConfigValues.read(type13, values.get("crit"), fileName + ":行" + lineNumber + ":字段 crit");
            parsedField14 = ly.ConfigValues.read(type14, values.get("critRatio"), fileName + ":行" + lineNumber + ":字段 critRatio");
            parsedField15 = ly.ConfigValues.read(type15, values.get("effectHit"), fileName + ":行" + lineNumber + ":字段 effectHit");
            parsedField16 = ly.ConfigValues.read(type16, values.get("effectDodge"), fileName + ":行" + lineNumber + ":字段 effectDodge");
            parsedField17 = ly.ConfigValues.read(type17, values.get("spCoe"), fileName + ":行" + lineNumber + ":字段 spCoe");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          ElectronicFairHeroConfig config = new ElectronicFairHeroConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8, parsedField9, parsedField10, parsedField11, parsedField12, parsedField13, parsedField14, parsedField15, parsedField16, parsedField17);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (ElectronicFairHeroConfig config : configList) config.afterLoad();
      new ElectronicFairHeroConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<ElectronicFairHeroConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, ElectronicFairHeroConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "electronicFairHero.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
