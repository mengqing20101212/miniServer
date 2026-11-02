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
 * File: ChapterRewardConfigManager
 */
public class ChapterRewardConfigManager implements InterfaceConfigManagerProxy {
  public static ChapterRewardConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(ChapterRewardConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new ChapterRewardConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "chapterReward.xml"; }

  public static class ChapterRewardConfigManagerImpl extends AbstractConfigManger {
    private List<ChapterRewardConfig> configList = List.of();
    private Map<Integer, ChapterRewardConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type5 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type7 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type8 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type9 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type10 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type11 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type12 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type13 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type14 = ly.ConfigValueType.parse("int");
      String fileName = configDir + File.separator + getConfigFileName();
      ChapterRewardConfigChecker checker = new ChapterRewardConfigChecker();
      List<ChapterRewardConfig> newList = new ArrayList<>();
      Map<Integer, ChapterRewardConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          int parsedField1 = 0;
          int parsedField2 = 0;
          String parsedField3 = null;
          int parsedField4 = 0;
          String parsedField5 = null;
          String parsedField6 = null;
          String parsedField7 = null;
          String parsedField8 = null;
          String parsedField9 = null;
          String parsedField10 = null;
          String parsedField11 = null;
          int parsedField12 = 0;
          int parsedField13 = 0;
          int parsedField14 = 0;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("StageId"), fileName + ":行" + lineNumber + ":字段 StageId");
            parsedField2 = ly.ConfigValues.read(type2, values.get("type"), fileName + ":行" + lineNumber + ":字段 type");
            parsedField3 = ly.ConfigValues.read(type3, values.get("rewardShow"), fileName + ":行" + lineNumber + ":字段 rewardShow");
            parsedField4 = ly.ConfigValues.read(type4, values.get("drop"), fileName + ":行" + lineNumber + ":字段 drop");
            parsedField5 = ly.ConfigValues.read(type5, values.get("word1"), fileName + ":行" + lineNumber + ":字段 word1");
            parsedField6 = ly.ConfigValues.read(type6, values.get("word2"), fileName + ":行" + lineNumber + ":字段 word2");
            parsedField7 = ly.ConfigValues.read(type7, values.get("outGradientColor"), fileName + ":行" + lineNumber + ":字段 outGradientColor");
            parsedField8 = ly.ConfigValues.read(type8, values.get("word3"), fileName + ":行" + lineNumber + ":字段 word3");
            parsedField9 = ly.ConfigValues.read(type9, values.get("word4"), fileName + ":行" + lineNumber + ":字段 word4");
            parsedField10 = ly.ConfigValues.read(type10, values.get("inGradientColor"), fileName + ":行" + lineNumber + ":字段 inGradientColor");
            parsedField11 = ly.ConfigValues.read(type11, values.get("word5"), fileName + ":行" + lineNumber + ":字段 word5");
            parsedField12 = ly.ConfigValues.read(type12, values.get("icon"), fileName + ":行" + lineNumber + ":字段 icon");
            parsedField13 = ly.ConfigValues.read(type13, values.get("popUp"), fileName + ":行" + lineNumber + ":字段 popUp");
            parsedField14 = ly.ConfigValues.read(type14, values.get("popUpUIIcon"), fileName + ":行" + lineNumber + ":字段 popUpUIIcon");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          ChapterRewardConfig config = new ChapterRewardConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8, parsedField9, parsedField10, parsedField11, parsedField12, parsedField13, parsedField14);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (ChapterRewardConfig config : configList) config.afterLoad();
      new ChapterRewardConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<ChapterRewardConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, ChapterRewardConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "chapterReward.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
