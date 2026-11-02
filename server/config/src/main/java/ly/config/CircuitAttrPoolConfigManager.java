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
 * File: CircuitAttrPoolConfigManager
 */
public class CircuitAttrPoolConfigManager implements InterfaceConfigManagerProxy {
  public static CircuitAttrPoolConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(CircuitAttrPoolConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new CircuitAttrPoolConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "circuitAttrPool.xml"; }

  public static class CircuitAttrPoolConfigManagerImpl extends AbstractConfigManger {
    private List<CircuitAttrPoolConfig> configList = List.of();
    private Map<Integer, CircuitAttrPoolConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("string");
      String fileName = configDir + File.separator + getConfigFileName();
      CircuitAttrPoolConfigChecker checker = new CircuitAttrPoolConfigChecker();
      List<CircuitAttrPoolConfig> newList = new ArrayList<>();
      Map<Integer, CircuitAttrPoolConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          String parsedField1 = null;
          String parsedField2 = null;
          String parsedField3 = null;
          String parsedField4 = null;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("des"), fileName + ":行" + lineNumber + ":字段 des");
            parsedField2 = ly.ConfigValues.read(type2, values.get("attrList"), fileName + ":行" + lineNumber + ":字段 attrList");
            parsedField3 = ly.ConfigValues.read(type3, values.get("attrPro"), fileName + ":行" + lineNumber + ":字段 attrPro");
            parsedField4 = ly.ConfigValues.read(type4, values.get("attrRelativePro"), fileName + ":行" + lineNumber + ":字段 attrRelativePro");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          CircuitAttrPoolConfig config = new CircuitAttrPoolConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (CircuitAttrPoolConfig config : configList) config.afterLoad();
      new CircuitAttrPoolConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<CircuitAttrPoolConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, CircuitAttrPoolConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "circuitAttrPool.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
