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
 * File: NpcCircuitModelConfigManager
 */
public class NpcCircuitModelConfigManager implements InterfaceConfigManagerProxy {
  public static NpcCircuitModelConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(NpcCircuitModelConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new NpcCircuitModelConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "npcCircuitModel.xml"; }

  public static class NpcCircuitModelConfigManagerImpl extends AbstractConfigManger {
    private List<NpcCircuitModelConfig> configList = List.of();
    private Map<Integer, NpcCircuitModelConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type5 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type7 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type8 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type9 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type10 = ly.ConfigValueType.parse("string");
      String fileName = configDir + File.separator + getConfigFileName();
      NpcCircuitModelConfigChecker checker = new NpcCircuitModelConfigChecker();
      List<NpcCircuitModelConfig> newList = new ArrayList<>();
      Map<Integer, NpcCircuitModelConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          int parsedField1 = 0;
          String parsedField2 = null;
          String parsedField3 = null;
          int parsedField4 = 0;
          int parsedField5 = 0;
          int parsedField6 = 0;
          int parsedField7 = 0;
          String parsedField8 = null;
          String parsedField9 = null;
          String parsedField10 = null;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("itemId"), fileName + ":行" + lineNumber + ":字段 itemId");
            parsedField2 = ly.ConfigValues.read(type2, values.get("des"), fileName + ":行" + lineNumber + ":字段 des");
            parsedField3 = ly.ConfigValues.read(type3, values.get("type"), fileName + ":行" + lineNumber + ":字段 type");
            parsedField4 = ly.ConfigValues.read(type4, values.get("suitId"), fileName + ":行" + lineNumber + ":字段 suitId");
            parsedField5 = ly.ConfigValues.read(type5, values.get("quality"), fileName + ":行" + lineNumber + ":字段 quality");
            parsedField6 = ly.ConfigValues.read(type6, values.get("pos"), fileName + ":行" + lineNumber + ":字段 pos");
            parsedField7 = ly.ConfigValues.read(type7, values.get("sequence"), fileName + ":行" + lineNumber + ":字段 sequence");
            parsedField8 = ly.ConfigValues.read(type8, values.get("mainAttr"), fileName + ":行" + lineNumber + ":字段 mainAttr");
            parsedField9 = ly.ConfigValues.read(type9, values.get("startSubAttr"), fileName + ":行" + lineNumber + ":字段 startSubAttr");
            parsedField10 = ly.ConfigValues.read(type10, values.get("lvSubAttr"), fileName + ":行" + lineNumber + ":字段 lvSubAttr");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          NpcCircuitModelConfig config = new NpcCircuitModelConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8, parsedField9, parsedField10);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (NpcCircuitModelConfig config : configList) config.afterLoad();
      new NpcCircuitModelConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<NpcCircuitModelConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, NpcCircuitModelConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "npcCircuitModel.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
