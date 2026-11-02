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
 * File: RankConfigManager
 */
public class RankConfigManager implements InterfaceConfigManagerProxy {
  public static RankConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(RankConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new RankConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "rank.xml"; }

  public static class RankConfigManagerImpl extends AbstractConfigManger {
    private List<RankConfig> configList = List.of();
    private Map<Integer, RankConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type5 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type7 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type8 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type9 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type10 = ly.ConfigValueType.parse("string");
      String fileName = configDir + File.separator + getConfigFileName();
      RankConfigChecker checker = new RankConfigChecker();
      List<RankConfig> newList = new ArrayList<>();
      Map<Integer, RankConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          int parsedField1 = 0;
          String parsedField2 = null;
          int parsedField3 = 0;
          String parsedField4 = null;
          String parsedField5 = null;
          int parsedField6 = 0;
          int parsedField7 = 0;
          int parsedField8 = 0;
          String parsedField9 = null;
          String parsedField10 = null;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("type"), fileName + ":行" + lineNumber + ":字段 type");
            parsedField2 = ly.ConfigValues.read(type2, values.get("name"), fileName + ":行" + lineNumber + ":字段 name");
            parsedField3 = ly.ConfigValues.read(type3, values.get("subtype"), fileName + ":行" + lineNumber + ":字段 subtype");
            parsedField4 = ly.ConfigValues.read(type4, values.get("subtype_name"), fileName + ":行" + lineNumber + ":字段 subtype_name");
            parsedField5 = ly.ConfigValues.read(type5, values.get("no_ranking"), fileName + ":行" + lineNumber + ":字段 no_ranking");
            parsedField6 = ly.ConfigValues.read(type6, values.get("rankNum"), fileName + ":行" + lineNumber + ":字段 rankNum");
            parsedField7 = ly.ConfigValues.read(type7, values.get("serverType"), fileName + ":行" + lineNumber + ":字段 serverType");
            parsedField8 = ly.ConfigValues.read(type8, values.get("serverSubtype"), fileName + ":行" + lineNumber + ":字段 serverSubtype");
            parsedField9 = ly.ConfigValues.read(type9, values.get("serverInfoName"), fileName + ":行" + lineNumber + ":字段 serverInfoName");
            parsedField10 = ly.ConfigValues.read(type10, values.get("defaultPage"), fileName + ":行" + lineNumber + ":字段 defaultPage");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          RankConfig config = new RankConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8, parsedField9, parsedField10);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (RankConfig config : configList) config.afterLoad();
      new RankConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<RankConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, RankConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "rank.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
