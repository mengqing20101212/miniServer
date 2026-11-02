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
 * File: CircuitGradeConfigManager
 */
public class CircuitGradeConfigManager implements InterfaceConfigManagerProxy {
  public static CircuitGradeConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(CircuitGradeConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new CircuitGradeConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "circuitGrade.xml"; }

  public static class CircuitGradeConfigManagerImpl extends AbstractConfigManger {
    private List<CircuitGradeConfig> configList = List.of();
    private Map<Integer, CircuitGradeConfig> configMap = Map.of();

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
      ly.ConfigValueType type11 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type12 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type13 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type14 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type15 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type16 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type17 = ly.ConfigValueType.parse("int");
      String fileName = configDir + File.separator + getConfigFileName();
      CircuitGradeConfigChecker checker = new CircuitGradeConfigChecker();
      List<CircuitGradeConfig> newList = new ArrayList<>();
      Map<Integer, CircuitGradeConfig> newMap = new HashMap<>();
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
          int parsedField11 = 0;
          int parsedField12 = 0;
          int parsedField13 = 0;
          int parsedField14 = 0;
          int parsedField15 = 0;
          int parsedField16 = 0;
          int parsedField17 = 0;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("description"), fileName + ":行" + lineNumber + ":字段 description");
            parsedField2 = ly.ConfigValues.read(type2, values.get("quality"), fileName + ":行" + lineNumber + ":字段 quality");
            parsedField3 = ly.ConfigValues.read(type3, values.get("lv1"), fileName + ":行" + lineNumber + ":字段 lv1");
            parsedField4 = ly.ConfigValues.read(type4, values.get("lv2"), fileName + ":行" + lineNumber + ":字段 lv2");
            parsedField5 = ly.ConfigValues.read(type5, values.get("lv3"), fileName + ":行" + lineNumber + ":字段 lv3");
            parsedField6 = ly.ConfigValues.read(type6, values.get("lv4"), fileName + ":行" + lineNumber + ":字段 lv4");
            parsedField7 = ly.ConfigValues.read(type7, values.get("lv5"), fileName + ":行" + lineNumber + ":字段 lv5");
            parsedField8 = ly.ConfigValues.read(type8, values.get("lv6"), fileName + ":行" + lineNumber + ":字段 lv6");
            parsedField9 = ly.ConfigValues.read(type9, values.get("lv7"), fileName + ":行" + lineNumber + ":字段 lv7");
            parsedField10 = ly.ConfigValues.read(type10, values.get("lv8"), fileName + ":行" + lineNumber + ":字段 lv8");
            parsedField11 = ly.ConfigValues.read(type11, values.get("lv9"), fileName + ":行" + lineNumber + ":字段 lv9");
            parsedField12 = ly.ConfigValues.read(type12, values.get("lv10"), fileName + ":行" + lineNumber + ":字段 lv10");
            parsedField13 = ly.ConfigValues.read(type13, values.get("lv11"), fileName + ":行" + lineNumber + ":字段 lv11");
            parsedField14 = ly.ConfigValues.read(type14, values.get("lv12"), fileName + ":行" + lineNumber + ":字段 lv12");
            parsedField15 = ly.ConfigValues.read(type15, values.get("lv13"), fileName + ":行" + lineNumber + ":字段 lv13");
            parsedField16 = ly.ConfigValues.read(type16, values.get("lv14"), fileName + ":行" + lineNumber + ":字段 lv14");
            parsedField17 = ly.ConfigValues.read(type17, values.get("lv15"), fileName + ":行" + lineNumber + ":字段 lv15");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          CircuitGradeConfig config = new CircuitGradeConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8, parsedField9, parsedField10, parsedField11, parsedField12, parsedField13, parsedField14, parsedField15, parsedField16, parsedField17);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (CircuitGradeConfig config : configList) config.afterLoad();
      new CircuitGradeConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<CircuitGradeConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, CircuitGradeConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "circuitGrade.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
