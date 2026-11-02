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
 * File: SuitInfoConfigManager
 */
public class SuitInfoConfigManager implements InterfaceConfigManagerProxy {
  public static SuitInfoConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(SuitInfoConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new SuitInfoConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "suitInfo.xml"; }

  public static class SuitInfoConfigManagerImpl extends AbstractConfigManger {
    private List<SuitInfoConfig> configList = List.of();
    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type5 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("string");
      String fileName = configDir + File.separator + getConfigFileName();
      SuitInfoConfigChecker checker = new SuitInfoConfigChecker();
      List<SuitInfoConfig> newList = new ArrayList<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          String parsedField1 = null;
          int parsedField2 = 0;
          int parsedField3 = 0;
          String parsedField4 = null;
          String parsedField5 = null;
          String parsedField6 = null;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("suitId"), fileName + ":行" + lineNumber + ":字段 suitId");
            parsedField1 = ly.ConfigValues.read(type1, values.get("suitName"), fileName + ":行" + lineNumber + ":字段 suitName");
            parsedField2 = ly.ConfigValues.read(type2, values.get("suitIcon"), fileName + ":行" + lineNumber + ":字段 suitIcon");
            parsedField3 = ly.ConfigValues.read(type3, values.get("suitIcon2"), fileName + ":行" + lineNumber + ":字段 suitIcon2");
            parsedField4 = ly.ConfigValues.read(type4, values.get("suitActive"), fileName + ":行" + lineNumber + ":字段 suitActive");
            parsedField5 = ly.ConfigValues.read(type5, values.get("twoSuitDec"), fileName + ":行" + lineNumber + ":字段 twoSuitDec");
            parsedField6 = ly.ConfigValues.read(type6, values.get("threeSuitDec"), fileName + ":行" + lineNumber + ":字段 threeSuitDec");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          SuitInfoConfig config = new SuitInfoConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6);
          newList.add(config);
      });
      configList = List.copyOf(newList);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (SuitInfoConfig config : configList) config.afterLoad();
      new SuitInfoConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<SuitInfoConfig> getConfigList() {
      return configList;
    }

    @Override
    public String getConfigFileName() {
      return "suitInfo.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
