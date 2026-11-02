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
 * File: BattleQuestionConfigManager
 */
public class BattleQuestionConfigManager implements InterfaceConfigManagerProxy {
  public static BattleQuestionConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(BattleQuestionConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new BattleQuestionConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "battleQuestion.xml"; }

  public static class BattleQuestionConfigManagerImpl extends AbstractConfigManger {
    private List<BattleQuestionConfig> configList = List.of();
    private Map<Integer, BattleQuestionConfig> configMap = Map.of();

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
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type7 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type8 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type9 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type10 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type11 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type12 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type13 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type14 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type15 = ly.ConfigValueType.parse("int");
      String fileName = configDir + File.separator + getConfigFileName();
      BattleQuestionConfigChecker checker = new BattleQuestionConfigChecker();
      List<BattleQuestionConfig> newList = new ArrayList<>();
      Map<Integer, BattleQuestionConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          String parsedField1 = null;
          int parsedField2 = 0;
          int parsedField3 = 0;
          String parsedField4 = null;
          String parsedField5 = null;
          int parsedField6 = 0;
          String parsedField7 = null;
          String parsedField8 = null;
          int parsedField9 = 0;
          String parsedField10 = null;
          String parsedField11 = null;
          int parsedField12 = 0;
          String parsedField13 = null;
          String parsedField14 = null;
          int parsedField15 = 0;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("questionContent"), fileName + ":行" + lineNumber + ":字段 questionContent");
            parsedField2 = ly.ConfigValues.read(type2, values.get("questionPic"), fileName + ":行" + lineNumber + ":字段 questionPic");
            parsedField3 = ly.ConfigValues.read(type3, values.get("answer"), fileName + ":行" + lineNumber + ":字段 answer");
            parsedField4 = ly.ConfigValues.read(type4, values.get("answer1"), fileName + ":行" + lineNumber + ":字段 answer1");
            parsedField5 = ly.ConfigValues.read(type5, values.get("answerResult1"), fileName + ":行" + lineNumber + ":字段 answerResult1");
            parsedField6 = ly.ConfigValues.read(type6, values.get("answerEffect1"), fileName + ":行" + lineNumber + ":字段 answerEffect1");
            parsedField7 = ly.ConfigValues.read(type7, values.get("answer2"), fileName + ":行" + lineNumber + ":字段 answer2");
            parsedField8 = ly.ConfigValues.read(type8, values.get("answerResult2"), fileName + ":行" + lineNumber + ":字段 answerResult2");
            parsedField9 = ly.ConfigValues.read(type9, values.get("answerEffect2"), fileName + ":行" + lineNumber + ":字段 answerEffect2");
            parsedField10 = ly.ConfigValues.read(type10, values.get("answer3"), fileName + ":行" + lineNumber + ":字段 answer3");
            parsedField11 = ly.ConfigValues.read(type11, values.get("answerResult3"), fileName + ":行" + lineNumber + ":字段 answerResult3");
            parsedField12 = ly.ConfigValues.read(type12, values.get("answerEffect3"), fileName + ":行" + lineNumber + ":字段 answerEffect3");
            parsedField13 = ly.ConfigValues.read(type13, values.get("answer4"), fileName + ":行" + lineNumber + ":字段 answer4");
            parsedField14 = ly.ConfigValues.read(type14, values.get("answerResult4"), fileName + ":行" + lineNumber + ":字段 answerResult4");
            parsedField15 = ly.ConfigValues.read(type15, values.get("answerEffect4"), fileName + ":行" + lineNumber + ":字段 answerEffect4");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          BattleQuestionConfig config = new BattleQuestionConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8, parsedField9, parsedField10, parsedField11, parsedField12, parsedField13, parsedField14, parsedField15);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (BattleQuestionConfig config : configList) config.afterLoad();
      new BattleQuestionConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<BattleQuestionConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, BattleQuestionConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "battleQuestion.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
