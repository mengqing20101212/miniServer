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
 * File: Battle_typeConfigManager
 */
public class Battle_typeConfigManager implements InterfaceConfigManagerProxy {
  public static Battle_typeConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(Battle_typeConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new Battle_typeConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "battle_type.xml"; }

  public static class Battle_typeConfigManagerImpl extends AbstractConfigManger {
    private List<Battle_typeConfig> configList = List.of();
    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type5 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type7 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type8 = ly.ConfigValueType.parse("int");
      String fileName = configDir + File.separator + getConfigFileName();
      Battle_typeConfigChecker checker = new Battle_typeConfigChecker();
      List<Battle_typeConfig> newList = new ArrayList<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          String parsedField0 = null;
          int parsedField1 = 0;
          int parsedField2 = 0;
          int parsedField3 = 0;
          int parsedField4 = 0;
          int parsedField5 = 0;
          int parsedField6 = 0;
          int parsedField7 = 0;
          int parsedField8 = 0;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("name"), fileName + ":行" + lineNumber + ":字段 name");
            parsedField1 = ly.ConfigValues.read(type1, values.get("ms"), fileName + ":行" + lineNumber + ":字段 ms");
            parsedField2 = ly.ConfigValues.read(type2, values.get("timeout"), fileName + ":行" + lineNumber + ":字段 timeout");
            parsedField3 = ly.ConfigValues.read(type3, values.get("battle_time"), fileName + ":行" + lineNumber + ":字段 battle_time");
            parsedField4 = ly.ConfigValues.read(type4, values.get("battle_type"), fileName + ":行" + lineNumber + ":字段 battle_type");
            parsedField5 = ly.ConfigValues.read(type5, values.get("sceneid"), fileName + ":行" + lineNumber + ":字段 sceneid");
            parsedField6 = ly.ConfigValues.read(type6, values.get("preparetimeout"), fileName + ":行" + lineNumber + ":字段 preparetimeout");
            parsedField7 = ly.ConfigValues.read(type7, values.get("checktimeoutinterval"), fileName + ":行" + lineNumber + ":字段 checktimeoutinterval");
            parsedField8 = ly.ConfigValues.read(type8, values.get("accounttimeout"), fileName + ":行" + lineNumber + ":字段 accounttimeout");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          Battle_typeConfig config = new Battle_typeConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8);
          newList.add(config);
      });
      configList = List.copyOf(newList);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (Battle_typeConfig config : configList) config.afterLoad();
      new Battle_typeConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<Battle_typeConfig> getConfigList() {
      return configList;
    }

    @Override
    public String getConfigFileName() {
      return "battle_type.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
