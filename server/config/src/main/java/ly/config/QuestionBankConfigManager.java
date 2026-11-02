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
 * File: QuestionBankConfigManager
 */
public class QuestionBankConfigManager implements InterfaceConfigManagerProxy {
  public static QuestionBankConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(QuestionBankConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new QuestionBankConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "questionBank.xml"; }

  public static class QuestionBankConfigManagerImpl extends AbstractConfigManger {
    private List<QuestionBankConfig> configList = List.of();
    private Map<Integer, QuestionBankConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type5 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type7 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type8 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type9 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type10 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type11 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type12 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type13 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type14 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type15 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type16 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type17 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type18 = ly.ConfigValueType.parse("string");
      String fileName = configDir + File.separator + getConfigFileName();
      QuestionBankConfigChecker checker = new QuestionBankConfigChecker();
      List<QuestionBankConfig> newList = new ArrayList<>();
      Map<Integer, QuestionBankConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          String parsedField1 = null;
          String parsedField2 = null;
          int parsedField3 = 0;
          int parsedField4 = 0;
          String parsedField5 = null;
          int parsedField6 = 0;
          String parsedField7 = null;
          String parsedField8 = null;
          String parsedField9 = null;
          String parsedField10 = null;
          String parsedField11 = null;
          String parsedField12 = null;
          String parsedField13 = null;
          String parsedField14 = null;
          String parsedField15 = null;
          String parsedField16 = null;
          String parsedField17 = null;
          String parsedField18 = null;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("questionContent"), fileName + ":行" + lineNumber + ":字段 questionContent");
            parsedField2 = ly.ConfigValues.read(type2, values.get("questionNote"), fileName + ":行" + lineNumber + ":字段 questionNote");
            parsedField3 = ly.ConfigValues.read(type3, values.get("type"), fileName + ":行" + lineNumber + ":字段 type");
            parsedField4 = ly.ConfigValues.read(type4, values.get("questionShow"), fileName + ":行" + lineNumber + ":字段 questionShow");
            parsedField5 = ly.ConfigValues.read(type5, values.get("questionPicture"), fileName + ":行" + lineNumber + ":字段 questionPicture");
            parsedField6 = ly.ConfigValues.read(type6, values.get("optionShow"), fileName + ":行" + lineNumber + ":字段 optionShow");
            parsedField7 = ly.ConfigValues.read(type7, values.get("simpleA"), fileName + ":行" + lineNumber + ":字段 simpleA");
            parsedField8 = ly.ConfigValues.read(type8, values.get("simpleB"), fileName + ":行" + lineNumber + ":字段 simpleB");
            parsedField9 = ly.ConfigValues.read(type9, values.get("simpleC"), fileName + ":行" + lineNumber + ":字段 simpleC");
            parsedField10 = ly.ConfigValues.read(type10, values.get("simpleD"), fileName + ":行" + lineNumber + ":字段 simpleD");
            parsedField11 = ly.ConfigValues.read(type11, values.get("simpleAnswer"), fileName + ":行" + lineNumber + ":字段 simpleAnswer");
            parsedField12 = ly.ConfigValues.read(type12, values.get("simpleNote"), fileName + ":行" + lineNumber + ":字段 simpleNote");
            parsedField13 = ly.ConfigValues.read(type13, values.get("hardA"), fileName + ":行" + lineNumber + ":字段 hardA");
            parsedField14 = ly.ConfigValues.read(type14, values.get("hardB"), fileName + ":行" + lineNumber + ":字段 hardB");
            parsedField15 = ly.ConfigValues.read(type15, values.get("hardC"), fileName + ":行" + lineNumber + ":字段 hardC");
            parsedField16 = ly.ConfigValues.read(type16, values.get("hardD"), fileName + ":行" + lineNumber + ":字段 hardD");
            parsedField17 = ly.ConfigValues.read(type17, values.get("hardAnswer"), fileName + ":行" + lineNumber + ":字段 hardAnswer");
            parsedField18 = ly.ConfigValues.read(type18, values.get("hardNote"), fileName + ":行" + lineNumber + ":字段 hardNote");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          QuestionBankConfig config = new QuestionBankConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8, parsedField9, parsedField10, parsedField11, parsedField12, parsedField13, parsedField14, parsedField15, parsedField16, parsedField17, parsedField18);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (QuestionBankConfig config : configList) config.afterLoad();
      new QuestionBankConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<QuestionBankConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, QuestionBankConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "questionBank.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
