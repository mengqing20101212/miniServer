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
 * File: ScenePointConfigManager
 */
public class ScenePointConfigManager implements InterfaceConfigManagerProxy {
  public static ScenePointConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(ScenePointConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new ScenePointConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "scenePoint.xml"; }

  public static class ScenePointConfigManagerImpl extends AbstractConfigManger {
    private List<ScenePointConfig> configList = List.of();
    private Map<Integer, ScenePointConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type5 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type7 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type8 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type9 = ly.ConfigValueType.parse("string");
      String fileName = configDir + File.separator + getConfigFileName();
      ScenePointConfigChecker checker = new ScenePointConfigChecker();
      List<ScenePointConfig> newList = new ArrayList<>();
      Map<Integer, ScenePointConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          int parsedField1 = 0;
          String parsedField2 = null;
          String parsedField3 = null;
          String parsedField4 = null;
          String parsedField5 = null;
          String parsedField6 = null;
          String parsedField7 = null;
          String parsedField8 = null;
          String parsedField9 = null;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("sceneId"), fileName + ":行" + lineNumber + ":字段 sceneId");
            parsedField2 = ly.ConfigValues.read(type2, values.get("scenePointList"), fileName + ":行" + lineNumber + ":字段 scenePointList");
            parsedField3 = ly.ConfigValues.read(type3, values.get("pointRoleList"), fileName + ":行" + lineNumber + ":字段 pointRoleList");
            parsedField4 = ly.ConfigValues.read(type4, values.get("functionCameraConfig"), fileName + ":行" + lineNumber + ":字段 functionCameraConfig");
            parsedField5 = ly.ConfigValues.read(type5, values.get("freeScenePointList"), fileName + ":行" + lineNumber + ":字段 freeScenePointList");
            parsedField6 = ly.ConfigValues.read(type6, values.get("freeCameraConfig"), fileName + ":行" + lineNumber + ":字段 freeCameraConfig");
            parsedField7 = ly.ConfigValues.read(type7, values.get("Param_1"), fileName + ":行" + lineNumber + ":字段 Param_1");
            parsedField8 = ly.ConfigValues.read(type8, values.get("groupScenePointList"), fileName + ":行" + lineNumber + ":字段 groupScenePointList");
            parsedField9 = ly.ConfigValues.read(type9, values.get("groupCameraConfig"), fileName + ":行" + lineNumber + ":字段 groupCameraConfig");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          ScenePointConfig config = new ScenePointConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8, parsedField9);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (ScenePointConfig config : configList) config.afterLoad();
      new ScenePointConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<ScenePointConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, ScenePointConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "scenePoint.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
