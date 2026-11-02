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
 * File: CircuitRecommendConfigManager
 */
public class CircuitRecommendConfigManager implements InterfaceConfigManagerProxy {
  public static CircuitRecommendConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(CircuitRecommendConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new CircuitRecommendConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "circuitRecommend.xml"; }

  public static class CircuitRecommendConfigManagerImpl extends AbstractConfigManger {
    private List<CircuitRecommendConfig> configList = List.of();
    private Map<Integer, CircuitRecommendConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type5 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type7 = ly.ConfigValueType.parse("string");
      String fileName = configDir + File.separator + getConfigFileName();
      CircuitRecommendConfigChecker checker = new CircuitRecommendConfigChecker();
      List<CircuitRecommendConfig> newList = new ArrayList<>();
      Map<Integer, CircuitRecommendConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          String parsedField1 = null;
          String parsedField2 = null;
          String parsedField3 = null;
          String parsedField4 = null;
          String parsedField5 = null;
          String parsedField6 = null;
          String parsedField7 = null;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("name"), fileName + ":行" + lineNumber + ":字段 name");
            parsedField2 = ly.ConfigValues.read(type2, values.get("recommend1"), fileName + ":行" + lineNumber + ":字段 recommend1");
            parsedField3 = ly.ConfigValues.read(type3, values.get("recommend2"), fileName + ":行" + lineNumber + ":字段 recommend2");
            parsedField4 = ly.ConfigValues.read(type4, values.get("recommendAttr1"), fileName + ":行" + lineNumber + ":字段 recommendAttr1");
            parsedField5 = ly.ConfigValues.read(type5, values.get("recommendAttr2"), fileName + ":行" + lineNumber + ":字段 recommendAttr2");
            parsedField6 = ly.ConfigValues.read(type6, values.get("recommendAttr3"), fileName + ":行" + lineNumber + ":字段 recommendAttr3");
            parsedField7 = ly.ConfigValues.read(type7, values.get("recommendWord"), fileName + ":行" + lineNumber + ":字段 recommendWord");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          CircuitRecommendConfig config = new CircuitRecommendConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (CircuitRecommendConfig config : configList) config.afterLoad();
      new CircuitRecommendConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<CircuitRecommendConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, CircuitRecommendConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "circuitRecommend.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
