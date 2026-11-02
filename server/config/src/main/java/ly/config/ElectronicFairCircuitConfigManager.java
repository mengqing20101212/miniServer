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
 * File: ElectronicFairCircuitConfigManager
 */
public class ElectronicFairCircuitConfigManager implements InterfaceConfigManagerProxy {
  public static ElectronicFairCircuitConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(ElectronicFairCircuitConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new ElectronicFairCircuitConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "electronicFairCircuit.xml"; }

  public static class ElectronicFairCircuitConfigManagerImpl extends AbstractConfigManger {
    private List<ElectronicFairCircuitConfig> configList = List.of();
    private Map<Integer, ElectronicFairCircuitConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("string");
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
      String fileName = configDir + File.separator + getConfigFileName();
      ElectronicFairCircuitConfigChecker checker = new ElectronicFairCircuitConfigChecker();
      List<ElectronicFairCircuitConfig> newList = new ArrayList<>();
      Map<Integer, ElectronicFairCircuitConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          String parsedField1 = null;
          int parsedField2 = 0;
          String parsedField3 = null;
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
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("circuitName"), fileName + ":行" + lineNumber + ":字段 circuitName");
            parsedField2 = ly.ConfigValues.read(type2, values.get("circuit1"), fileName + ":行" + lineNumber + ":字段 circuit1");
            parsedField3 = ly.ConfigValues.read(type3, values.get("circuitAttr1"), fileName + ":行" + lineNumber + ":字段 circuitAttr1");
            parsedField4 = ly.ConfigValues.read(type4, values.get("circuit2"), fileName + ":行" + lineNumber + ":字段 circuit2");
            parsedField5 = ly.ConfigValues.read(type5, values.get("circuitAttr2"), fileName + ":行" + lineNumber + ":字段 circuitAttr2");
            parsedField6 = ly.ConfigValues.read(type6, values.get("circuit3"), fileName + ":行" + lineNumber + ":字段 circuit3");
            parsedField7 = ly.ConfigValues.read(type7, values.get("circuitAttr3"), fileName + ":行" + lineNumber + ":字段 circuitAttr3");
            parsedField8 = ly.ConfigValues.read(type8, values.get("circuit4"), fileName + ":行" + lineNumber + ":字段 circuit4");
            parsedField9 = ly.ConfigValues.read(type9, values.get("circuitAttr4"), fileName + ":行" + lineNumber + ":字段 circuitAttr4");
            parsedField10 = ly.ConfigValues.read(type10, values.get("circuit5"), fileName + ":行" + lineNumber + ":字段 circuit5");
            parsedField11 = ly.ConfigValues.read(type11, values.get("circuitAttr5"), fileName + ":行" + lineNumber + ":字段 circuitAttr5");
            parsedField12 = ly.ConfigValues.read(type12, values.get("circuit6"), fileName + ":行" + lineNumber + ":字段 circuit6");
            parsedField13 = ly.ConfigValues.read(type13, values.get("circuitAttr6"), fileName + ":行" + lineNumber + ":字段 circuitAttr6");
            parsedField14 = ly.ConfigValues.read(type14, values.get("circuit7"), fileName + ":行" + lineNumber + ":字段 circuit7");
            parsedField15 = ly.ConfigValues.read(type15, values.get("circuitAttr7"), fileName + ":行" + lineNumber + ":字段 circuitAttr7");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          ElectronicFairCircuitConfig config = new ElectronicFairCircuitConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8, parsedField9, parsedField10, parsedField11, parsedField12, parsedField13, parsedField14, parsedField15);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (ElectronicFairCircuitConfig config : configList) config.afterLoad();
      new ElectronicFairCircuitConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<ElectronicFairCircuitConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, ElectronicFairCircuitConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "electronicFairCircuit.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
