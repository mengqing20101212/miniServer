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
 * File: SceneGradeConfigManager
 */
public class SceneGradeConfigManager implements InterfaceConfigManagerProxy {
  public static SceneGradeConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(SceneGradeConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new SceneGradeConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "sceneGrade.xml"; }

  public static class SceneGradeConfigManagerImpl extends AbstractConfigManger {
    private List<SceneGradeConfig> configList = List.of();
    private Map<Integer, SceneGradeConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type5 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("int");
      String fileName = configDir + File.separator + getConfigFileName();
      SceneGradeConfigChecker checker = new SceneGradeConfigChecker();
      List<SceneGradeConfig> newList = new ArrayList<>();
      Map<Integer, SceneGradeConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          String parsedField1 = null;
          int parsedField2 = 0;
          int parsedField3 = 0;
          int parsedField4 = 0;
          int parsedField5 = 0;
          int parsedField6 = 0;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("beizhu"), fileName + ":行" + lineNumber + ":字段 beizhu");
            parsedField2 = ly.ConfigValues.read(type2, values.get("grade1"), fileName + ":行" + lineNumber + ":字段 grade1");
            parsedField3 = ly.ConfigValues.read(type3, values.get("grade2"), fileName + ":行" + lineNumber + ":字段 grade2");
            parsedField4 = ly.ConfigValues.read(type4, values.get("grade3"), fileName + ":行" + lineNumber + ":字段 grade3");
            parsedField5 = ly.ConfigValues.read(type5, values.get("grade4"), fileName + ":行" + lineNumber + ":字段 grade4");
            parsedField6 = ly.ConfigValues.read(type6, values.get("grade5"), fileName + ":行" + lineNumber + ":字段 grade5");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          SceneGradeConfig config = new SceneGradeConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (SceneGradeConfig config : configList) config.afterLoad();
      new SceneGradeConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<SceneGradeConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, SceneGradeConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "sceneGrade.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
