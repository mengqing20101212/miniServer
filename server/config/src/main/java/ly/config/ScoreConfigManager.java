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
 * File: ScoreConfigManager
 */
public class ScoreConfigManager implements InterfaceConfigManagerProxy {
  public static ScoreConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(ScoreConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new ScoreConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "score.xml"; }

  public static class ScoreConfigManagerImpl extends AbstractConfigManger {
    private List<ScoreConfig> configList = List.of();
    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type5 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type7 = ly.ConfigValueType.parse("float");
      ly.ConfigValueType type8 = ly.ConfigValueType.parse("float");
      ly.ConfigValueType type9 = ly.ConfigValueType.parse("float");
      ly.ConfigValueType type10 = ly.ConfigValueType.parse("float");
      ly.ConfigValueType type11 = ly.ConfigValueType.parse("float");
      ly.ConfigValueType type12 = ly.ConfigValueType.parse("float");
      String fileName = configDir + File.separator + getConfigFileName();
      ScoreConfigChecker checker = new ScoreConfigChecker();
      List<ScoreConfig> newList = new ArrayList<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          int parsedField1 = 0;
          int parsedField2 = 0;
          int parsedField3 = 0;
          int parsedField4 = 0;
          int parsedField5 = 0;
          int parsedField6 = 0;
          float parsedField7 = 0F;
          float parsedField8 = 0F;
          float parsedField9 = 0F;
          float parsedField10 = 0F;
          float parsedField11 = 0F;
          float parsedField12 = 0F;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("level"), fileName + ":行" + lineNumber + ":字段 level");
            parsedField1 = ly.ConfigValues.read(type1, values.get("heroLevelTarget"), fileName + ":行" + lineNumber + ":字段 heroLevelTarget");
            parsedField2 = ly.ConfigValues.read(type2, values.get("breakTarget"), fileName + ":行" + lineNumber + ":字段 breakTarget");
            parsedField3 = ly.ConfigValues.read(type3, values.get("advanceTarget"), fileName + ":行" + lineNumber + ":字段 advanceTarget");
            parsedField4 = ly.ConfigValues.read(type4, values.get("awakenTarget"), fileName + ":行" + lineNumber + ":字段 awakenTarget");
            parsedField5 = ly.ConfigValues.read(type5, values.get("suitTarget"), fileName + ":行" + lineNumber + ":字段 suitTarget");
            parsedField6 = ly.ConfigValues.read(type6, values.get("supportTarget"), fileName + ":行" + lineNumber + ":字段 supportTarget");
            parsedField7 = ly.ConfigValues.read(type7, values.get("heroLevelCoefficient"), fileName + ":行" + lineNumber + ":字段 heroLevelCoefficient");
            parsedField8 = ly.ConfigValues.read(type8, values.get("breakCoefficient"), fileName + ":行" + lineNumber + ":字段 breakCoefficient");
            parsedField9 = ly.ConfigValues.read(type9, values.get("advanceCoefficient"), fileName + ":行" + lineNumber + ":字段 advanceCoefficient");
            parsedField10 = ly.ConfigValues.read(type10, values.get("awakenCoefficient"), fileName + ":行" + lineNumber + ":字段 awakenCoefficient");
            parsedField11 = ly.ConfigValues.read(type11, values.get("suitCoefficient"), fileName + ":行" + lineNumber + ":字段 suitCoefficient");
            parsedField12 = ly.ConfigValues.read(type12, values.get("supportCoefficient"), fileName + ":行" + lineNumber + ":字段 supportCoefficient");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          ScoreConfig config = new ScoreConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8, parsedField9, parsedField10, parsedField11, parsedField12);
          newList.add(config);
      });
      configList = List.copyOf(newList);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (ScoreConfig config : configList) config.afterLoad();
      new ScoreConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<ScoreConfig> getConfigList() {
      return configList;
    }

    @Override
    public String getConfigFileName() {
      return "score.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
