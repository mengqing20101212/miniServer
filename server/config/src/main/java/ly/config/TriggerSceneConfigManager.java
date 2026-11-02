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
 * File: TriggerSceneConfigManager
 */
public class TriggerSceneConfigManager implements InterfaceConfigManagerProxy {
  public static TriggerSceneConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(TriggerSceneConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new TriggerSceneConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "triggerScene.xml"; }

  public static class TriggerSceneConfigManagerImpl extends AbstractConfigManger {
    private List<TriggerSceneConfig> configList = List.of();
    private Map<Integer, TriggerSceneConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type5 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type7 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type8 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type9 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type10 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type11 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type12 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type13 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type14 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type15 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type16 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type17 = ly.ConfigValueType.parse("string");
      String fileName = configDir + File.separator + getConfigFileName();
      TriggerSceneConfigChecker checker = new TriggerSceneConfigChecker();
      List<TriggerSceneConfig> newList = new ArrayList<>();
      Map<Integer, TriggerSceneConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          int parsedField1 = 0;
          int parsedField2 = 0;
          int parsedField3 = 0;
          String parsedField4 = null;
          String parsedField5 = null;
          String parsedField6 = null;
          int parsedField7 = 0;
          int parsedField8 = 0;
          int parsedField9 = 0;
          int parsedField10 = 0;
          int parsedField11 = 0;
          int parsedField12 = 0;
          String parsedField13 = null;
          String parsedField14 = null;
          int parsedField15 = 0;
          String parsedField16 = null;
          String parsedField17 = null;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("sceneId"), fileName + ":行" + lineNumber + ":字段 sceneId");
            parsedField2 = ly.ConfigValues.read(type2, values.get("group"), fileName + ":行" + lineNumber + ":字段 group");
            parsedField3 = ly.ConfigValues.read(type3, values.get("type"), fileName + ":行" + lineNumber + ":字段 type");
            parsedField4 = ly.ConfigValues.read(type4, values.get("stageName"), fileName + ":行" + lineNumber + ":字段 stageName");
            parsedField5 = ly.ConfigValues.read(type5, values.get("stageName2"), fileName + ":行" + lineNumber + ":字段 stageName2");
            parsedField6 = ly.ConfigValues.read(type6, values.get("stageName3"), fileName + ":行" + lineNumber + ":字段 stageName3");
            parsedField7 = ly.ConfigValues.read(type7, values.get("preCost"), fileName + ":行" + lineNumber + ":字段 preCost");
            parsedField8 = ly.ConfigValues.read(type8, values.get("cost"), fileName + ":行" + lineNumber + ":字段 cost");
            parsedField9 = ly.ConfigValues.read(type9, values.get("weight"), fileName + ":行" + lineNumber + ":字段 weight");
            parsedField10 = ly.ConfigValues.read(type10, values.get("time"), fileName + ":行" + lineNumber + ":字段 time");
            parsedField11 = ly.ConfigValues.read(type11, values.get("bossId"), fileName + ":行" + lineNumber + ":字段 bossId");
            parsedField12 = ly.ConfigValues.read(type12, values.get("sceneAvatar"), fileName + ":行" + lineNumber + ":字段 sceneAvatar");
            parsedField13 = ly.ConfigValues.read(type13, values.get("dropPro"), fileName + ":行" + lineNumber + ":字段 dropPro");
            parsedField14 = ly.ConfigValues.read(type14, values.get("bossDes"), fileName + ":行" + lineNumber + ":字段 bossDes");
            parsedField15 = ly.ConfigValues.read(type15, values.get("scenePic"), fileName + ":行" + lineNumber + ":字段 scenePic");
            parsedField16 = ly.ConfigValues.read(type16, values.get("posOffset"), fileName + ":行" + lineNumber + ":字段 posOffset");
            parsedField17 = ly.ConfigValues.read(type17, values.get("scale"), fileName + ":行" + lineNumber + ":字段 scale");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          TriggerSceneConfig config = new TriggerSceneConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8, parsedField9, parsedField10, parsedField11, parsedField12, parsedField13, parsedField14, parsedField15, parsedField16, parsedField17);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (TriggerSceneConfig config : configList) config.afterLoad();
      new TriggerSceneConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<TriggerSceneConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, TriggerSceneConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "triggerScene.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
