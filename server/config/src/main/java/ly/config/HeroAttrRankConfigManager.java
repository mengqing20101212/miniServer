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
 * File: HeroAttrRankConfigManager
 */
public class HeroAttrRankConfigManager implements InterfaceConfigManagerProxy {
  public static HeroAttrRankConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(HeroAttrRankConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new HeroAttrRankConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "heroAttrRank.xml"; }

  public static class HeroAttrRankConfigManagerImpl extends AbstractConfigManger {
    private List<HeroAttrRankConfig> configList = List.of();
    private Map<Integer, HeroAttrRankConfig> configMap = Map.of();

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
      ly.ConfigValueType type10 = ly.ConfigValueType.parse("int");
      String fileName = configDir + File.separator + getConfigFileName();
      HeroAttrRankConfigChecker checker = new HeroAttrRankConfigChecker();
      List<HeroAttrRankConfig> newList = new ArrayList<>();
      Map<Integer, HeroAttrRankConfig> newMap = new HashMap<>();
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
          int parsedField10 = 0;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("des"), fileName + ":行" + lineNumber + ":字段 des");
            parsedField2 = ly.ConfigValues.read(type2, values.get("maxHP"), fileName + ":行" + lineNumber + ":字段 maxHP");
            parsedField3 = ly.ConfigValues.read(type3, values.get("attack"), fileName + ":行" + lineNumber + ":字段 attack");
            parsedField4 = ly.ConfigValues.read(type4, values.get("defence"), fileName + ":行" + lineNumber + ":字段 defence");
            parsedField5 = ly.ConfigValues.read(type5, values.get("speed"), fileName + ":行" + lineNumber + ":字段 speed");
            parsedField6 = ly.ConfigValues.read(type6, values.get("crit"), fileName + ":行" + lineNumber + ":字段 crit");
            parsedField7 = ly.ConfigValues.read(type7, values.get("critRatio"), fileName + ":行" + lineNumber + ":字段 critRatio");
            parsedField8 = ly.ConfigValues.read(type8, values.get("effectHit"), fileName + ":行" + lineNumber + ":字段 effectHit");
            parsedField9 = ly.ConfigValues.read(type9, values.get("effectDodge"), fileName + ":行" + lineNumber + ":字段 effectDodge");
            parsedField10 = ly.ConfigValues.read(type10, values.get("spCoe"), fileName + ":行" + lineNumber + ":字段 spCoe");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          HeroAttrRankConfig config = new HeroAttrRankConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8, parsedField9, parsedField10);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (HeroAttrRankConfig config : configList) config.afterLoad();
      new HeroAttrRankConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<HeroAttrRankConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, HeroAttrRankConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "heroAttrRank.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
