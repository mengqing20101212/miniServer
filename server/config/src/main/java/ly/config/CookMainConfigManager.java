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
 * File: CookMainConfigManager
 */
public class CookMainConfigManager implements InterfaceConfigManagerProxy {
  public static CookMainConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(CookMainConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new CookMainConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "cookMain.xml"; }

  public static class CookMainConfigManagerImpl extends AbstractConfigManger {
    private List<CookMainConfig> configList = List.of();
    private Map<Integer, CookMainConfig> configMap = Map.of();

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
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type7 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type8 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type9 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type10 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type11 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type12 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type13 = ly.ConfigValueType.parse("string");
      String fileName = configDir + File.separator + getConfigFileName();
      CookMainConfigChecker checker = new CookMainConfigChecker();
      List<CookMainConfig> newList = new ArrayList<>();
      Map<Integer, CookMainConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          int parsedField1 = 0;
          String parsedField2 = null;
          int parsedField3 = 0;
          int parsedField4 = 0;
          String parsedField5 = null;
          String parsedField6 = null;
          int parsedField7 = 0;
          int parsedField8 = 0;
          String parsedField9 = null;
          int parsedField10 = 0;
          int parsedField11 = 0;
          int parsedField12 = 0;
          String parsedField13 = null;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("type"), fileName + ":行" + lineNumber + ":字段 type");
            parsedField2 = ly.ConfigValues.read(type2, values.get("name"), fileName + ":行" + lineNumber + ":字段 name");
            parsedField3 = ly.ConfigValues.read(type3, values.get("icon"), fileName + ":行" + lineNumber + ":字段 icon");
            parsedField4 = ly.ConfigValues.read(type4, values.get("star"), fileName + ":行" + lineNumber + ":字段 star");
            parsedField5 = ly.ConfigValues.read(type5, values.get("recipe"), fileName + ":行" + lineNumber + ":字段 recipe");
            parsedField6 = ly.ConfigValues.read(type6, values.get("cookSkill"), fileName + ":行" + lineNumber + ":字段 cookSkill");
            parsedField7 = ly.ConfigValues.read(type7, values.get("skillExp"), fileName + ":行" + lineNumber + ":字段 skillExp");
            parsedField8 = ly.ConfigValues.read(type8, values.get("skillIncrease"), fileName + ":行" + lineNumber + ":字段 skillIncrease");
            parsedField9 = ly.ConfigValues.read(type9, values.get("baseReward"), fileName + ":行" + lineNumber + ":字段 baseReward");
            parsedField10 = ly.ConfigValues.read(type10, values.get("activityId"), fileName + ":行" + lineNumber + ":字段 activityId");
            parsedField11 = ly.ConfigValues.read(type11, values.get("saitamaExp"), fileName + ":行" + lineNumber + ":字段 saitamaExp");
            parsedField12 = ly.ConfigValues.read(type12, values.get("dropId"), fileName + ":行" + lineNumber + ":字段 dropId");
            parsedField13 = ly.ConfigValues.read(type13, values.get("word"), fileName + ":行" + lineNumber + ":字段 word");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          CookMainConfig config = new CookMainConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8, parsedField9, parsedField10, parsedField11, parsedField12, parsedField13);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (CookMainConfig config : configList) config.afterLoad();
      new CookMainConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<CookMainConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, CookMainConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "cookMain.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
