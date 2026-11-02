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
 * File: HeroAdvanceConfigManager
 */
public class HeroAdvanceConfigManager implements InterfaceConfigManagerProxy {
  public static HeroAdvanceConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(HeroAdvanceConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new HeroAdvanceConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "heroAdvance.xml"; }

  public static class HeroAdvanceConfigManagerImpl extends AbstractConfigManger {
    private List<HeroAdvanceConfig> configList = List.of();
    private Map<Integer, HeroAdvanceConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type5 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type7 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type8 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type9 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type10 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type11 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type12 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type13 = ly.ConfigValueType.parse("string");
      String fileName = configDir + File.separator + getConfigFileName();
      HeroAdvanceConfigChecker checker = new HeroAdvanceConfigChecker();
      List<HeroAdvanceConfig> newList = new ArrayList<>();
      Map<Integer, HeroAdvanceConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          int parsedField1 = 0;
          int parsedField2 = 0;
          String parsedField3 = null;
          String parsedField4 = null;
          int parsedField5 = 0;
          int parsedField6 = 0;
          String parsedField7 = null;
          int parsedField8 = 0;
          String parsedField9 = null;
          int parsedField10 = 0;
          int parsedField11 = 0;
          int parsedField12 = 0;
          String parsedField13 = null;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("modelName"), fileName + ":行" + lineNumber + ":字段 modelName");
            parsedField2 = ly.ConfigValues.read(type2, values.get("sequence"), fileName + ":行" + lineNumber + ":字段 sequence");
            parsedField3 = ly.ConfigValues.read(type3, values.get("attrType"), fileName + ":行" + lineNumber + ":字段 attrType");
            parsedField4 = ly.ConfigValues.read(type4, values.get("attrNum"), fileName + ":行" + lineNumber + ":字段 attrNum");
            parsedField5 = ly.ConfigValues.read(type5, values.get("skillPoint"), fileName + ":行" + lineNumber + ":字段 skillPoint");
            parsedField6 = ly.ConfigValues.read(type6, values.get("skillPointNum"), fileName + ":行" + lineNumber + ":字段 skillPointNum");
            parsedField7 = ly.ConfigValues.read(type7, values.get("advanceItem"), fileName + ":行" + lineNumber + ":字段 advanceItem");
            parsedField8 = ly.ConfigValues.read(type8, values.get("replaceItems"), fileName + ":行" + lineNumber + ":字段 replaceItems");
            parsedField9 = ly.ConfigValues.read(type9, values.get("describe"), fileName + ":行" + lineNumber + ":字段 describe");
            parsedField10 = ly.ConfigValues.read(type10, values.get("getItem"), fileName + ":行" + lineNumber + ":字段 getItem");
            parsedField11 = ly.ConfigValues.read(type11, values.get("drop"), fileName + ":行" + lineNumber + ":字段 drop");
            parsedField12 = ly.ConfigValues.read(type12, values.get("dropShow"), fileName + ":行" + lineNumber + ":字段 dropShow");
            parsedField13 = ly.ConfigValues.read(type13, values.get("itemDes"), fileName + ":行" + lineNumber + ":字段 itemDes");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          HeroAdvanceConfig config = new HeroAdvanceConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8, parsedField9, parsedField10, parsedField11, parsedField12, parsedField13);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (HeroAdvanceConfig config : configList) config.afterLoad();
      new HeroAdvanceConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<HeroAdvanceConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, HeroAdvanceConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "heroAdvance.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
