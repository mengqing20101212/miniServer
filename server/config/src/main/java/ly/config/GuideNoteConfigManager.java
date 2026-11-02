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
 * File: GuideNoteConfigManager
 */
public class GuideNoteConfigManager implements InterfaceConfigManagerProxy {
  public static GuideNoteConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(GuideNoteConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new GuideNoteConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "guideNote.xml"; }

  public static class GuideNoteConfigManagerImpl extends AbstractConfigManger {
    private List<GuideNoteConfig> configList = List.of();
    private Map<Integer, GuideNoteConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("list<int>");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type5 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type7 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type8 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type9 = ly.ConfigValueType.parse("list<int>");
      ly.ConfigValueType type10 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type11 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type12 = ly.ConfigValueType.parse("string");
      String fileName = configDir + File.separator + getConfigFileName();
      GuideNoteConfigChecker checker = new GuideNoteConfigChecker();
      List<GuideNoteConfig> newList = new ArrayList<>();
      Map<Integer, GuideNoteConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          java.util.List<Integer> parsedField1 = null;
          String parsedField2 = null;
          String parsedField3 = null;
          int parsedField4 = 0;
          int parsedField5 = 0;
          String parsedField6 = null;
          int parsedField7 = 0;
          String parsedField8 = null;
          java.util.List<Integer> parsedField9 = null;
          String parsedField10 = null;
          int parsedField11 = 0;
          String parsedField12 = null;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("guideCond"), fileName + ":行" + lineNumber + ":字段 guideCond");
            parsedField2 = ly.ConfigValues.read(type2, values.get("guidePara1"), fileName + ":行" + lineNumber + ":字段 guidePara1");
            parsedField3 = ly.ConfigValues.read(type3, values.get("linkPart"), fileName + ":行" + lineNumber + ":字段 linkPart");
            parsedField4 = ly.ConfigValues.read(type4, values.get("linkParam"), fileName + ":行" + lineNumber + ":字段 linkParam");
            parsedField5 = ly.ConfigValues.read(type5, values.get("uiType"), fileName + ":行" + lineNumber + ":字段 uiType");
            parsedField6 = ly.ConfigValues.read(type6, values.get("linkShift"), fileName + ":行" + lineNumber + ":字段 linkShift");
            parsedField7 = ly.ConfigValues.read(type7, values.get("noticeType"), fileName + ":行" + lineNumber + ":字段 noticeType");
            parsedField8 = ly.ConfigValues.read(type8, values.get("word"), fileName + ":行" + lineNumber + ":字段 word");
            parsedField9 = ly.ConfigValues.read(type9, values.get("endType"), fileName + ":行" + lineNumber + ":字段 endType");
            parsedField10 = ly.ConfigValues.read(type10, values.get("endPara"), fileName + ":行" + lineNumber + ":字段 endPara");
            parsedField11 = ly.ConfigValues.read(type11, values.get("turnId"), fileName + ":行" + lineNumber + ":字段 turnId");
            parsedField12 = ly.ConfigValues.read(type12, values.get("beizhu"), fileName + ":行" + lineNumber + ":字段 beizhu");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          GuideNoteConfig config = new GuideNoteConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8, parsedField9, parsedField10, parsedField11, parsedField12);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (GuideNoteConfig config : configList) config.afterLoad();
      new GuideNoteConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<GuideNoteConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, GuideNoteConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "guideNote.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
