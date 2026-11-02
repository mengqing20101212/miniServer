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
 * File: CharacterskillsConfigManager
 */
public class CharacterskillsConfigManager implements InterfaceConfigManagerProxy {
  public static CharacterskillsConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(CharacterskillsConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new CharacterskillsConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "characterskills.xml"; }

  public static class CharacterskillsConfigManagerImpl extends AbstractConfigManger {
    private List<CharacterskillsConfig> configList = List.of();
    private Map<Integer, CharacterskillsConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type5 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type7 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type8 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type9 = ly.ConfigValueType.parse("string");
      String fileName = configDir + File.separator + getConfigFileName();
      CharacterskillsConfigChecker checker = new CharacterskillsConfigChecker();
      List<CharacterskillsConfig> newList = new ArrayList<>();
      Map<Integer, CharacterskillsConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          int parsedField1 = 0;
          String parsedField2 = null;
          int parsedField3 = 0;
          int parsedField4 = 0;
          int parsedField5 = 0;
          int parsedField6 = 0;
          int parsedField7 = 0;
          int parsedField8 = 0;
          String parsedField9 = null;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("heroid"), fileName + ":行" + lineNumber + ":字段 heroid");
            parsedField2 = ly.ConfigValues.read(type2, values.get("config_name"), fileName + ":行" + lineNumber + ":字段 config_name");
            parsedField3 = ly.ConfigValues.read(type3, values.get("headResource_S"), fileName + ":行" + lineNumber + ":字段 headResource_S");
            parsedField4 = ly.ConfigValues.read(type4, values.get("bgResource"), fileName + ":行" + lineNumber + ":字段 bgResource");
            parsedField5 = ly.ConfigValues.read(type5, values.get("bgResource_0"), fileName + ":行" + lineNumber + ":字段 bgResource_0");
            parsedField6 = ly.ConfigValues.read(type6, values.get("skillResource"), fileName + ":行" + lineNumber + ":字段 skillResource");
            parsedField7 = ly.ConfigValues.read(type7, values.get("effectResource_1"), fileName + ":行" + lineNumber + ":字段 effectResource_1");
            parsedField8 = ly.ConfigValues.read(type8, values.get("effectResource_2"), fileName + ":行" + lineNumber + ":字段 effectResource_2");
            parsedField9 = ly.ConfigValues.read(type9, values.get("color"), fileName + ":行" + lineNumber + ":字段 color");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          CharacterskillsConfig config = new CharacterskillsConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8, parsedField9);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (CharacterskillsConfig config : configList) config.afterLoad();
      new CharacterskillsConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<CharacterskillsConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, CharacterskillsConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "characterskills.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
