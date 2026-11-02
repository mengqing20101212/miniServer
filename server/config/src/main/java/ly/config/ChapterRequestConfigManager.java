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
 * File: ChapterRequestConfigManager
 */
public class ChapterRequestConfigManager implements InterfaceConfigManagerProxy {
  public static ChapterRequestConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(ChapterRequestConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new ChapterRequestConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "chapterRequest.xml"; }

  public static class ChapterRequestConfigManagerImpl extends AbstractConfigManger {
    private List<ChapterRequestConfig> configList = List.of();
    private Map<Integer, ChapterRequestConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type5 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type7 = ly.ConfigValueType.parse("string");
      String fileName = configDir + File.separator + getConfigFileName();
      ChapterRequestConfigChecker checker = new ChapterRequestConfigChecker();
      List<ChapterRequestConfig> newList = new ArrayList<>();
      Map<Integer, ChapterRequestConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          int parsedField1 = 0;
          String parsedField2 = null;
          String parsedField3 = null;
          int parsedField4 = 0;
          int parsedField5 = 0;
          String parsedField6 = null;
          String parsedField7 = null;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("patterntype"), fileName + ":行" + lineNumber + ":字段 patterntype");
            parsedField2 = ly.ConfigValues.read(type2, values.get("content"), fileName + ":行" + lineNumber + ":字段 content");
            parsedField3 = ly.ConfigValues.read(type3, values.get("beizhu"), fileName + ":行" + lineNumber + ":字段 beizhu");
            parsedField4 = ly.ConfigValues.read(type4, values.get("dropGroup"), fileName + ":行" + lineNumber + ":字段 dropGroup");
            parsedField5 = ly.ConfigValues.read(type5, values.get("firstDrop"), fileName + ":行" + lineNumber + ":字段 firstDrop");
            parsedField6 = ly.ConfigValues.read(type6, values.get("target"), fileName + ":行" + lineNumber + ":字段 target");
            parsedField7 = ly.ConfigValues.read(type7, values.get("result"), fileName + ":行" + lineNumber + ":字段 result");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          ChapterRequestConfig config = new ChapterRequestConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (ChapterRequestConfig config : configList) config.afterLoad();
      new ChapterRequestConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<ChapterRequestConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, ChapterRequestConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "chapterRequest.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
