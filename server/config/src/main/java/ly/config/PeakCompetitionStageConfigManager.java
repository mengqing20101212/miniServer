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
 * File: PeakCompetitionStageConfigManager
 */
public class PeakCompetitionStageConfigManager implements InterfaceConfigManagerProxy {
  public static PeakCompetitionStageConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(PeakCompetitionStageConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new PeakCompetitionStageConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "peakCompetitionStage.xml"; }

  public static class PeakCompetitionStageConfigManagerImpl extends AbstractConfigManger {
    private List<PeakCompetitionStageConfig> configList = List.of();
    private Map<Integer, PeakCompetitionStageConfig> configMap = Map.of();

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
      ly.ConfigValueType type7 = ly.ConfigValueType.parse("string");
      String fileName = configDir + File.separator + getConfigFileName();
      PeakCompetitionStageConfigChecker checker = new PeakCompetitionStageConfigChecker();
      List<PeakCompetitionStageConfig> newList = new ArrayList<>();
      Map<Integer, PeakCompetitionStageConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          String parsedField1 = null;
          int parsedField2 = 0;
          int parsedField3 = 0;
          int parsedField4 = 0;
          int parsedField5 = 0;
          int parsedField6 = 0;
          String parsedField7 = null;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("stageName"), fileName + ":行" + lineNumber + ":字段 stageName");
            parsedField2 = ly.ConfigValues.read(type2, values.get("groupId"), fileName + ":行" + lineNumber + ":字段 groupId");
            parsedField3 = ly.ConfigValues.read(type3, values.get("nextId"), fileName + ":行" + lineNumber + ":字段 nextId");
            parsedField4 = ly.ConfigValues.read(type4, values.get("lastId"), fileName + ":行" + lineNumber + ":字段 lastId");
            parsedField5 = ly.ConfigValues.read(type5, values.get("sceneId"), fileName + ":行" + lineNumber + ":字段 sceneId");
            parsedField6 = ly.ConfigValues.read(type6, values.get("dropShow"), fileName + ":行" + lineNumber + ":字段 dropShow");
            parsedField7 = ly.ConfigValues.read(type7, values.get("fixedRole"), fileName + ":行" + lineNumber + ":字段 fixedRole");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          PeakCompetitionStageConfig config = new PeakCompetitionStageConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (PeakCompetitionStageConfig config : configList) config.afterLoad();
      new PeakCompetitionStageConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<PeakCompetitionStageConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, PeakCompetitionStageConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "peakCompetitionStage.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
