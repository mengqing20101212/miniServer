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
 * File: StrategyRecommendLineupConfigManager
 */
public class StrategyRecommendLineupConfigManager implements InterfaceConfigManagerProxy {
  public static StrategyRecommendLineupConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(StrategyRecommendLineupConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new StrategyRecommendLineupConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "strategyRecommendLineup.xml"; }

  public static class StrategyRecommendLineupConfigManagerImpl extends AbstractConfigManger {
    private List<StrategyRecommendLineupConfig> configList = List.of();
    private Map<Integer, StrategyRecommendLineupConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type5 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("string");
      String fileName = configDir + File.separator + getConfigFileName();
      StrategyRecommendLineupConfigChecker checker = new StrategyRecommendLineupConfigChecker();
      List<StrategyRecommendLineupConfig> newList = new ArrayList<>();
      Map<Integer, StrategyRecommendLineupConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          String parsedField1 = null;
          int parsedField2 = 0;
          String parsedField3 = null;
          String parsedField4 = null;
          String parsedField5 = null;
          String parsedField6 = null;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("heroIds"), fileName + ":行" + lineNumber + ":字段 heroIds");
            parsedField2 = ly.ConfigValues.read(type2, values.get("sceneId"), fileName + ":行" + lineNumber + ":字段 sceneId");
            parsedField3 = ly.ConfigValues.read(type3, values.get("title"), fileName + ":行" + lineNumber + ":字段 title");
            parsedField4 = ly.ConfigValues.read(type4, values.get("actorDetail"), fileName + ":行" + lineNumber + ":字段 actorDetail");
            parsedField5 = ly.ConfigValues.read(type5, values.get("playDetail"), fileName + ":行" + lineNumber + ":字段 playDetail");
            parsedField6 = ly.ConfigValues.read(type6, values.get("effectDetail"), fileName + ":行" + lineNumber + ":字段 effectDetail");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          StrategyRecommendLineupConfig config = new StrategyRecommendLineupConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (StrategyRecommendLineupConfig config : configList) config.afterLoad();
      new StrategyRecommendLineupConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<StrategyRecommendLineupConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, StrategyRecommendLineupConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "strategyRecommendLineup.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
