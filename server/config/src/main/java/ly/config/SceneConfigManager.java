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
 * File: SceneConfigManager
 */
public class SceneConfigManager implements InterfaceConfigManagerProxy {
  public static SceneConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(SceneConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new SceneConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "scene.xml"; }

  public static class SceneConfigManagerImpl extends AbstractConfigManger {
    private List<SceneConfig> configList = List.of();
    private Map<Integer, SceneConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type5 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type7 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type8 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type9 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type10 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type11 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type12 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type13 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type14 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type15 = ly.ConfigValueType.parse("int");
      String fileName = configDir + File.separator + getConfigFileName();
      SceneConfigChecker checker = new SceneConfigChecker();
      List<SceneConfig> newList = new ArrayList<>();
      Map<Integer, SceneConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          String parsedField1 = null;
          String parsedField2 = null;
          String parsedField3 = null;
          int parsedField4 = 0;
          int parsedField5 = 0;
          String parsedField6 = null;
          String parsedField7 = null;
          String parsedField8 = null;
          String parsedField9 = null;
          String parsedField10 = null;
          String parsedField11 = null;
          int parsedField12 = 0;
          String parsedField13 = null;
          String parsedField14 = null;
          int parsedField15 = 0;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("sceneConfig"), fileName + ":行" + lineNumber + ":字段 sceneConfig");
            parsedField2 = ly.ConfigValues.read(type2, values.get("sceneConfigEnemy"), fileName + ":行" + lineNumber + ":字段 sceneConfigEnemy");
            parsedField3 = ly.ConfigValues.read(type3, values.get("resname"), fileName + ":行" + lineNumber + ":字段 resname");
            parsedField4 = ly.ConfigValues.read(type4, values.get("profile"), fileName + ":行" + lineNumber + ":字段 profile");
            parsedField5 = ly.ConfigValues.read(type5, values.get("tpye"), fileName + ":行" + lineNumber + ":字段 tpye");
            parsedField6 = ly.ConfigValues.read(type6, values.get("fogConfig"), fileName + ":行" + lineNumber + ":字段 fogConfig");
            parsedField7 = ly.ConfigValues.read(type7, values.get("lightConfig"), fileName + ":行" + lineNumber + ":字段 lightConfig");
            parsedField8 = ly.ConfigValues.read(type8, values.get("functionPoint"), fileName + ":行" + lineNumber + ":字段 functionPoint");
            parsedField9 = ly.ConfigValues.read(type9, values.get("functionCameraConfig"), fileName + ":行" + lineNumber + ":字段 functionCameraConfig");
            parsedField10 = ly.ConfigValues.read(type10, values.get("roleLight"), fileName + ":行" + lineNumber + ":字段 roleLight");
            parsedField11 = ly.ConfigValues.read(type11, values.get("WinPos"), fileName + ":行" + lineNumber + ":字段 WinPos");
            parsedField12 = ly.ConfigValues.read(type12, values.get("eftResid"), fileName + ":行" + lineNumber + ":字段 eftResid");
            parsedField13 = ly.ConfigValues.read(type13, values.get("eftResOffset"), fileName + ":行" + lineNumber + ":字段 eftResOffset");
            parsedField14 = ly.ConfigValues.read(type14, values.get("SFX"), fileName + ":行" + lineNumber + ":字段 SFX");
            parsedField15 = ly.ConfigValues.read(type15, values.get("sceneConfigRotation"), fileName + ":行" + lineNumber + ":字段 sceneConfigRotation");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          SceneConfig config = new SceneConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8, parsedField9, parsedField10, parsedField11, parsedField12, parsedField13, parsedField14, parsedField15);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (SceneConfig config : configList) config.afterLoad();
      new SceneConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<SceneConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, SceneConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "scene.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
