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
 * File: SceneTimeConfigManager
 */
public class SceneTimeConfigManager implements InterfaceConfigManagerProxy {
  public static SceneTimeConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(SceneTimeConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new SceneTimeConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "sceneTime.xml"; }

  public static class SceneTimeConfigManagerImpl extends AbstractConfigManger {
    private List<SceneTimeConfig> configList = List.of();
    private Map<Integer, SceneTimeConfig> configMap = Map.of();

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
      ly.ConfigValueType type7 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type8 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type9 = ly.ConfigValueType.parse("int");
      String fileName = configDir + File.separator + getConfigFileName();
      SceneTimeConfigChecker checker = new SceneTimeConfigChecker();
      List<SceneTimeConfig> newList = new ArrayList<>();
      Map<Integer, SceneTimeConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          String parsedField1 = null;
          int parsedField2 = 0;
          int parsedField3 = 0;
          int parsedField4 = 0;
          int parsedField5 = 0;
          int parsedField6 = 0;
          int parsedField7 = 0;
          int parsedField8 = 0;
          int parsedField9 = 0;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("des"), fileName + ":行" + lineNumber + ":字段 des");
            parsedField2 = ly.ConfigValues.read(type2, values.get("reConnectTime"), fileName + ":行" + lineNumber + ":字段 reConnectTime");
            parsedField3 = ly.ConfigValues.read(type3, values.get("offLineTime"), fileName + ":行" + lineNumber + ":字段 offLineTime");
            parsedField4 = ly.ConfigValues.read(type4, values.get("turnTime"), fileName + ":行" + lineNumber + ":字段 turnTime");
            parsedField5 = ly.ConfigValues.read(type5, values.get("playerLoadingTime"), fileName + ":行" + lineNumber + ":字段 playerLoadingTime");
            parsedField6 = ly.ConfigValues.read(type6, values.get("reConnectTimeMult"), fileName + ":行" + lineNumber + ":字段 reConnectTimeMult");
            parsedField7 = ly.ConfigValues.read(type7, values.get("offLineTimeMult"), fileName + ":行" + lineNumber + ":字段 offLineTimeMult");
            parsedField8 = ly.ConfigValues.read(type8, values.get("turnTimeMult"), fileName + ":行" + lineNumber + ":字段 turnTimeMult");
            parsedField9 = ly.ConfigValues.read(type9, values.get("playerLoadingTimeMult"), fileName + ":行" + lineNumber + ":字段 playerLoadingTimeMult");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          SceneTimeConfig config = new SceneTimeConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8, parsedField9);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (SceneTimeConfig config : configList) config.afterLoad();
      new SceneTimeConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<SceneTimeConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, SceneTimeConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "sceneTime.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
