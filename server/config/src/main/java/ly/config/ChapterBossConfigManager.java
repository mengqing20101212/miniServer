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
 * File: ChapterBossConfigManager
 */
public class ChapterBossConfigManager implements InterfaceConfigManagerProxy {
  public static ChapterBossConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(ChapterBossConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new ChapterBossConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "chapterBoss.xml"; }

  public static class ChapterBossConfigManagerImpl extends AbstractConfigManger {
    private List<ChapterBossConfig> configList = List.of();
    private Map<Integer, ChapterBossConfig> configMap = Map.of();

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
      ly.ConfigValueType type9 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type10 = ly.ConfigValueType.parse("int");
      String fileName = configDir + File.separator + getConfigFileName();
      ChapterBossConfigChecker checker = new ChapterBossConfigChecker();
      List<ChapterBossConfig> newList = new ArrayList<>();
      Map<Integer, ChapterBossConfig> newMap = new HashMap<>();
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
          int parsedField9 = 0;
          int parsedField10 = 0;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("starNum"), fileName + ":行" + lineNumber + ":字段 starNum");
            parsedField2 = ly.ConfigValues.read(type2, values.get("type"), fileName + ":行" + lineNumber + ":字段 type");
            parsedField3 = ly.ConfigValues.read(type3, values.get("dropShow"), fileName + ":行" + lineNumber + ":字段 dropShow");
            parsedField4 = ly.ConfigValues.read(type4, values.get("drop"), fileName + ":行" + lineNumber + ":字段 drop");
            parsedField5 = ly.ConfigValues.read(type5, values.get("sceneId"), fileName + ":行" + lineNumber + ":字段 sceneId");
            parsedField6 = ly.ConfigValues.read(type6, values.get("npcId"), fileName + ":行" + lineNumber + ":字段 npcId");
            parsedField7 = ly.ConfigValues.read(type7, values.get("modelId"), fileName + ":行" + lineNumber + ":字段 modelId");
            parsedField8 = ly.ConfigValues.read(type8, values.get("resourceNpc"), fileName + ":行" + lineNumber + ":字段 resourceNpc");
            parsedField9 = ly.ConfigValues.read(type9, values.get("resourceIcon"), fileName + ":行" + lineNumber + ":字段 resourceIcon");
            parsedField10 = ly.ConfigValues.read(type10, values.get("iconNum"), fileName + ":行" + lineNumber + ":字段 iconNum");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          ChapterBossConfig config = new ChapterBossConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8, parsedField9, parsedField10);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (ChapterBossConfig config : configList) config.afterLoad();
      new ChapterBossConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<ChapterBossConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, ChapterBossConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "chapterBoss.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
