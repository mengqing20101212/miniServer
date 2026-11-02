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
 * File: ActivityrouletteAwardConfigManager
 */
public class ActivityrouletteAwardConfigManager implements InterfaceConfigManagerProxy {
  public static ActivityrouletteAwardConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(ActivityrouletteAwardConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new ActivityrouletteAwardConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "activityrouletteAward.xml"; }

  public static class ActivityrouletteAwardConfigManagerImpl extends AbstractConfigManger {
    private List<ActivityrouletteAwardConfig> configList = List.of();
    private Map<Integer, ActivityrouletteAwardConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type5 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type7 = ly.ConfigValueType.parse("int");
      String fileName = configDir + File.separator + getConfigFileName();
      ActivityrouletteAwardConfigChecker checker = new ActivityrouletteAwardConfigChecker();
      List<ActivityrouletteAwardConfig> newList = new ArrayList<>();
      Map<Integer, ActivityrouletteAwardConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          int parsedField1 = 0;
          int parsedField2 = 0;
          String parsedField3 = null;
          int parsedField4 = 0;
          int parsedField5 = 0;
          int parsedField6 = 0;
          int parsedField7 = 0;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("scheDuling"), fileName + ":行" + lineNumber + ":字段 scheDuling");
            parsedField2 = ly.ConfigValues.read(type2, values.get("prizeId"), fileName + ":行" + lineNumber + ":字段 prizeId");
            parsedField3 = ly.ConfigValues.read(type3, values.get("rewardShow"), fileName + ":行" + lineNumber + ":字段 rewardShow");
            parsedField4 = ly.ConfigValues.read(type4, values.get("drop"), fileName + ":行" + lineNumber + ":字段 drop");
            parsedField5 = ly.ConfigValues.read(type5, values.get("rewardWeight"), fileName + ":行" + lineNumber + ":字段 rewardWeight");
            parsedField6 = ly.ConfigValues.read(type6, values.get("rewardLevel"), fileName + ":行" + lineNumber + ":字段 rewardLevel");
            parsedField7 = ly.ConfigValues.read(type7, values.get("highest"), fileName + ":行" + lineNumber + ":字段 highest");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          ActivityrouletteAwardConfig config = new ActivityrouletteAwardConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (ActivityrouletteAwardConfig config : configList) config.afterLoad();
      new ActivityrouletteAwardConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<ActivityrouletteAwardConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, ActivityrouletteAwardConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "activityrouletteAward.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
