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
 * File: SkinConfigManager
 */
public class SkinConfigManager implements InterfaceConfigManagerProxy {
  public static SkinConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(SkinConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new SkinConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "skin.xml"; }

  public static class SkinConfigManagerImpl extends AbstractConfigManger {
    private List<SkinConfig> configList = List.of();
    private Map<Integer, SkinConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type5 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type7 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type8 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type9 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type10 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type11 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type12 = ly.ConfigValueType.parse("int");
      String fileName = configDir + File.separator + getConfigFileName();
      SkinConfigChecker checker = new SkinConfigChecker();
      List<SkinConfig> newList = new ArrayList<>();
      Map<Integer, SkinConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          int parsedField1 = 0;
          String parsedField2 = null;
          String parsedField3 = null;
          String parsedField4 = null;
          int parsedField5 = 0;
          int parsedField6 = 0;
          int parsedField7 = 0;
          int parsedField8 = 0;
          String parsedField9 = null;
          String parsedField10 = null;
          int parsedField11 = 0;
          int parsedField12 = 0;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("heroId"), fileName + ":行" + lineNumber + ":字段 heroId");
            parsedField2 = ly.ConfigValues.read(type2, values.get("name"), fileName + ":行" + lineNumber + ":字段 name");
            parsedField3 = ly.ConfigValues.read(type3, values.get("skinName"), fileName + ":行" + lineNumber + ":字段 skinName");
            parsedField4 = ly.ConfigValues.read(type4, values.get("skinGet"), fileName + ":行" + lineNumber + ":字段 skinGet");
            parsedField5 = ly.ConfigValues.read(type5, values.get("turnId"), fileName + ":行" + lineNumber + ":字段 turnId");
            parsedField6 = ly.ConfigValues.read(type6, values.get("modelResource"), fileName + ":行" + lineNumber + ":字段 modelResource");
            parsedField7 = ly.ConfigValues.read(type7, values.get("battleResource"), fileName + ":行" + lineNumber + ":字段 battleResource");
            parsedField8 = ly.ConfigValues.read(type8, values.get("showResource"), fileName + ":行" + lineNumber + ":字段 showResource");
            parsedField9 = ly.ConfigValues.read(type9, values.get("heroPerformance"), fileName + ":行" + lineNumber + ":字段 heroPerformance");
            parsedField10 = ly.ConfigValues.read(type10, values.get("endPerformance"), fileName + ":行" + lineNumber + ":字段 endPerformance");
            parsedField11 = ly.ConfigValues.read(type11, values.get("activityNpc"), fileName + ":行" + lineNumber + ":字段 activityNpc");
            parsedField12 = ly.ConfigValues.read(type12, values.get("cardBust"), fileName + ":行" + lineNumber + ":字段 cardBust");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          SkinConfig config = new SkinConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8, parsedField9, parsedField10, parsedField11, parsedField12);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (SkinConfig config : configList) config.afterLoad();
      new SkinConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<SkinConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, SkinConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "skin.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
