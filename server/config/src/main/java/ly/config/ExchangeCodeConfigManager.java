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
 * File: ExchangeCodeConfigManager
 */
public class ExchangeCodeConfigManager implements InterfaceConfigManagerProxy {
  public static ExchangeCodeConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(ExchangeCodeConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new ExchangeCodeConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "exchangeCode.xml"; }

  public static class ExchangeCodeConfigManagerImpl extends AbstractConfigManger {
    private List<ExchangeCodeConfig> configList = List.of();
    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type5 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type7 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type8 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type9 = ly.ConfigValueType.parse("string");
      String fileName = configDir + File.separator + getConfigFileName();
      ExchangeCodeConfigChecker checker = new ExchangeCodeConfigChecker();
      List<ExchangeCodeConfig> newList = new ArrayList<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          String parsedField1 = null;
          String parsedField2 = null;
          String parsedField3 = null;
          int parsedField4 = 0;
          String parsedField5 = null;
          String parsedField6 = null;
          int parsedField7 = 0;
          int parsedField8 = 0;
          String parsedField9 = null;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("groupId"), fileName + ":行" + lineNumber + ":字段 groupId");
            parsedField1 = ly.ConfigValues.read(type1, values.get("name"), fileName + ":行" + lineNumber + ":字段 name");
            parsedField2 = ly.ConfigValues.read(type2, values.get("beginTime"), fileName + ":行" + lineNumber + ":字段 beginTime");
            parsedField3 = ly.ConfigValues.read(type3, values.get("endTime"), fileName + ":行" + lineNumber + ":字段 endTime");
            parsedField4 = ly.ConfigValues.read(type4, values.get("codeNum"), fileName + ":行" + lineNumber + ":字段 codeNum");
            parsedField5 = ly.ConfigValues.read(type5, values.get("channel"), fileName + ":行" + lineNumber + ":字段 channel");
            parsedField6 = ly.ConfigValues.read(type6, values.get("rewards"), fileName + ":行" + lineNumber + ":字段 rewards");
            parsedField7 = ly.ConfigValues.read(type7, values.get("limit1"), fileName + ":行" + lineNumber + ":字段 limit1");
            parsedField8 = ly.ConfigValues.read(type8, values.get("limit2"), fileName + ":行" + lineNumber + ":字段 limit2");
            parsedField9 = ly.ConfigValues.read(type9, values.get("limit3"), fileName + ":行" + lineNumber + ":字段 limit3");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          ExchangeCodeConfig config = new ExchangeCodeConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8, parsedField9);
          newList.add(config);
      });
      configList = List.copyOf(newList);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (ExchangeCodeConfig config : configList) config.afterLoad();
      new ExchangeCodeConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<ExchangeCodeConfig> getConfigList() {
      return configList;
    }

    @Override
    public String getConfigFileName() {
      return "exchangeCode.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
