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
 * File: CircuitItemInfoConfigManager
 */
public class CircuitItemInfoConfigManager implements InterfaceConfigManagerProxy {
  public static CircuitItemInfoConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(CircuitItemInfoConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new CircuitItemInfoConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "circuitItemInfo.xml"; }

  public static class CircuitItemInfoConfigManagerImpl extends AbstractConfigManger {
    private List<CircuitItemInfoConfig> configList = List.of();
    private Map<Integer, CircuitItemInfoConfig> configMap = Map.of();

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
      ly.ConfigValueType type7 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type8 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type9 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type10 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type11 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type12 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type13 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type14 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type15 = ly.ConfigValueType.parse("int");
      String fileName = configDir + File.separator + getConfigFileName();
      CircuitItemInfoConfigChecker checker = new CircuitItemInfoConfigChecker();
      List<CircuitItemInfoConfig> newList = new ArrayList<>();
      Map<Integer, CircuitItemInfoConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          String parsedField1 = null;
          int parsedField2 = 0;
          int parsedField3 = 0;
          int parsedField4 = 0;
          int parsedField5 = 0;
          int parsedField6 = 0;
          String parsedField7 = null;
          int parsedField8 = 0;
          int parsedField9 = 0;
          int parsedField10 = 0;
          int parsedField11 = 0;
          int parsedField12 = 0;
          int parsedField13 = 0;
          int parsedField14 = 0;
          int parsedField15 = 0;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("description"), fileName + ":行" + lineNumber + ":字段 description");
            parsedField2 = ly.ConfigValues.read(type2, values.get("type"), fileName + ":行" + lineNumber + ":字段 type");
            parsedField3 = ly.ConfigValues.read(type3, values.get("school"), fileName + ":行" + lineNumber + ":字段 school");
            parsedField4 = ly.ConfigValues.read(type4, values.get("grade"), fileName + ":行" + lineNumber + ":字段 grade");
            parsedField5 = ly.ConfigValues.read(type5, values.get("sequence"), fileName + ":行" + lineNumber + ":字段 sequence");
            parsedField6 = ly.ConfigValues.read(type6, values.get("pos"), fileName + ":行" + lineNumber + ":字段 pos");
            parsedField7 = ly.ConfigValues.read(type7, values.get("suitIds"), fileName + ":行" + lineNumber + ":字段 suitIds");
            parsedField8 = ly.ConfigValues.read(type8, values.get("mainAttrPoolId"), fileName + ":行" + lineNumber + ":字段 mainAttrPoolId");
            parsedField9 = ly.ConfigValues.read(type9, values.get("subAttrPoolId"), fileName + ":行" + lineNumber + ":字段 subAttrPoolId");
            parsedField10 = ly.ConfigValues.read(type10, values.get("subAttrUpgradePoolId"), fileName + ":行" + lineNumber + ":字段 subAttrUpgradePoolId");
            parsedField11 = ly.ConfigValues.read(type11, values.get("upgradeType"), fileName + ":行" + lineNumber + ":字段 upgradeType");
            parsedField12 = ly.ConfigValues.read(type12, values.get("decomposeGold"), fileName + ":行" + lineNumber + ":字段 decomposeGold");
            parsedField13 = ly.ConfigValues.read(type13, values.get("decomposeDG"), fileName + ":行" + lineNumber + ":字段 decomposeDG");
            parsedField14 = ly.ConfigValues.read(type14, values.get("quality"), fileName + ":行" + lineNumber + ":字段 quality");
            parsedField15 = ly.ConfigValues.read(type15, values.get("lightAttrPoolId"), fileName + ":行" + lineNumber + ":字段 lightAttrPoolId");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          CircuitItemInfoConfig config = new CircuitItemInfoConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8, parsedField9, parsedField10, parsedField11, parsedField12, parsedField13, parsedField14, parsedField15);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (CircuitItemInfoConfig config : configList) config.afterLoad();
      new CircuitItemInfoConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<CircuitItemInfoConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, CircuitItemInfoConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "circuitItemInfo.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
