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
 * File: ChapterTaskConfigManager
 */
public class ChapterTaskConfigManager implements InterfaceConfigManagerProxy {
  public static ChapterTaskConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(ChapterTaskConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new ChapterTaskConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "chapterTask.xml"; }

  public static class ChapterTaskConfigManagerImpl extends AbstractConfigManger {
    private List<ChapterTaskConfig> configList = List.of();
    private Map<Integer, ChapterTaskConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type5 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type7 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type8 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type9 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type10 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type11 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type12 = ly.ConfigValueType.parse("int");
      String fileName = configDir + File.separator + getConfigFileName();
      ChapterTaskConfigChecker checker = new ChapterTaskConfigChecker();
      List<ChapterTaskConfig> newList = new ArrayList<>();
      Map<Integer, ChapterTaskConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          int parsedField1 = 0;
          int parsedField2 = 0;
          String parsedField3 = null;
          int parsedField4 = 0;
          int parsedField5 = 0;
          String parsedField6 = null;
          int parsedField7 = 0;
          int parsedField8 = 0;
          String parsedField9 = null;
          int parsedField10 = 0;
          int parsedField11 = 0;
          int parsedField12 = 0;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("chapterMainId"), fileName + ":行" + lineNumber + ":字段 chapterMainId");
            parsedField2 = ly.ConfigValues.read(type2, values.get("missionId1"), fileName + ":行" + lineNumber + ":字段 missionId1");
            parsedField3 = ly.ConfigValues.read(type3, values.get("des1"), fileName + ":行" + lineNumber + ":字段 des1");
            parsedField4 = ly.ConfigValues.read(type4, values.get("redirectionId1"), fileName + ":行" + lineNumber + ":字段 redirectionId1");
            parsedField5 = ly.ConfigValues.read(type5, values.get("missionId2"), fileName + ":行" + lineNumber + ":字段 missionId2");
            parsedField6 = ly.ConfigValues.read(type6, values.get("des2"), fileName + ":行" + lineNumber + ":字段 des2");
            parsedField7 = ly.ConfigValues.read(type7, values.get("redirectionId2"), fileName + ":行" + lineNumber + ":字段 redirectionId2");
            parsedField8 = ly.ConfigValues.read(type8, values.get("missionId3"), fileName + ":行" + lineNumber + ":字段 missionId3");
            parsedField9 = ly.ConfigValues.read(type9, values.get("des3"), fileName + ":行" + lineNumber + ":字段 des3");
            parsedField10 = ly.ConfigValues.read(type10, values.get("redirectionId3"), fileName + ":行" + lineNumber + ":字段 redirectionId3");
            parsedField11 = ly.ConfigValues.read(type11, values.get("drop"), fileName + ":行" + lineNumber + ":字段 drop");
            parsedField12 = ly.ConfigValues.read(type12, values.get("dropShow"), fileName + ":行" + lineNumber + ":字段 dropShow");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          ChapterTaskConfig config = new ChapterTaskConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8, parsedField9, parsedField10, parsedField11, parsedField12);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (ChapterTaskConfig config : configList) config.afterLoad();
      new ChapterTaskConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<ChapterTaskConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, ChapterTaskConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "chapterTask.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
