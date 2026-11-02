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
 * File: WantedStageConfigManager
 */
public class WantedStageConfigManager implements InterfaceConfigManagerProxy {
  public static WantedStageConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(WantedStageConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new WantedStageConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "wantedStage.xml"; }

  public static class WantedStageConfigManagerImpl extends AbstractConfigManger {
    private List<WantedStageConfig> configList = List.of();
    private Map<Integer, WantedStageConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type5 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type7 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type8 = ly.ConfigValueType.parse("int");
      String fileName = configDir + File.separator + getConfigFileName();
      WantedStageConfigChecker checker = new WantedStageConfigChecker();
      List<WantedStageConfig> newList = new ArrayList<>();
      Map<Integer, WantedStageConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          String parsedField1 = null;
          int parsedField2 = 0;
          String parsedField3 = null;
          String parsedField4 = null;
          int parsedField5 = 0;
          int parsedField6 = 0;
          String parsedField7 = null;
          int parsedField8 = 0;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("missionTitle"), fileName + ":行" + lineNumber + ":字段 missionTitle");
            parsedField2 = ly.ConfigValues.read(type2, values.get("titleRes"), fileName + ":行" + lineNumber + ":字段 titleRes");
            parsedField3 = ly.ConfigValues.read(type3, values.get("titleDesc"), fileName + ":行" + lineNumber + ":字段 titleDesc");
            parsedField4 = ly.ConfigValues.read(type4, values.get("missionDesc"), fileName + ":行" + lineNumber + ":字段 missionDesc");
            parsedField5 = ly.ConfigValues.read(type5, values.get("missionPic"), fileName + ":行" + lineNumber + ":字段 missionPic");
            parsedField6 = ly.ConfigValues.read(type6, values.get("star"), fileName + ":行" + lineNumber + ":字段 star");
            parsedField7 = ly.ConfigValues.read(type7, values.get("rewardShow"), fileName + ":行" + lineNumber + ":字段 rewardShow");
            parsedField8 = ly.ConfigValues.read(type8, values.get("sceneId"), fileName + ":行" + lineNumber + ":字段 sceneId");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          WantedStageConfig config = new WantedStageConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (WantedStageConfig config : configList) config.afterLoad();
      new WantedStageConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<WantedStageConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, WantedStageConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "wantedStage.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
