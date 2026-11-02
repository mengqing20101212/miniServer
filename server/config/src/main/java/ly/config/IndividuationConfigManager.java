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
 * File: IndividuationConfigManager
 */
public class IndividuationConfigManager implements InterfaceConfigManagerProxy {
  public static IndividuationConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(IndividuationConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new IndividuationConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "individuation.xml"; }

  public static class IndividuationConfigManagerImpl extends AbstractConfigManger {
    private List<IndividuationConfig> configList = List.of();
    private Map<Integer, IndividuationConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type5 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type7 = ly.ConfigValueType.parse("string");
      String fileName = configDir + File.separator + getConfigFileName();
      IndividuationConfigChecker checker = new IndividuationConfigChecker();
      List<IndividuationConfig> newList = new ArrayList<>();
      Map<Integer, IndividuationConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          int parsedField1 = 0;
          int parsedField2 = 0;
          String parsedField3 = null;
          String parsedField4 = null;
          String parsedField5 = null;
          String parsedField6 = null;
          String parsedField7 = null;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("type"), fileName + ":行" + lineNumber + ":字段 type");
            parsedField2 = ly.ConfigValues.read(type2, values.get("item"), fileName + ":行" + lineNumber + ":字段 item");
            parsedField3 = ly.ConfigValues.read(type3, values.get("param_1"), fileName + ":行" + lineNumber + ":字段 param_1");
            parsedField4 = ly.ConfigValues.read(type4, values.get("sceneInfoId"), fileName + ":行" + lineNumber + ":字段 sceneInfoId");
            parsedField5 = ly.ConfigValues.read(type5, values.get("picRes"), fileName + ":行" + lineNumber + ":字段 picRes");
            parsedField6 = ly.ConfigValues.read(type6, values.get("name"), fileName + ":行" + lineNumber + ":字段 name");
            parsedField7 = ly.ConfigValues.read(type7, values.get("dec"), fileName + ":行" + lineNumber + ":字段 dec");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          IndividuationConfig config = new IndividuationConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (IndividuationConfig config : configList) config.afterLoad();
      new IndividuationConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<IndividuationConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, IndividuationConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "individuation.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
