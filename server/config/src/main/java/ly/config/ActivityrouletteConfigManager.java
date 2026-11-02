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
 * File: ActivityrouletteConfigManager
 */
public class ActivityrouletteConfigManager implements InterfaceConfigManagerProxy {
  public static ActivityrouletteConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(ActivityrouletteConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new ActivityrouletteConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "activityroulette.xml"; }

  public static class ActivityrouletteConfigManagerImpl extends AbstractConfigManger {
    private List<ActivityrouletteConfig> configList = List.of();
    private Map<Integer, ActivityrouletteConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type5 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type7 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type8 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type9 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type10 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type11 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type12 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type13 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type14 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type15 = ly.ConfigValueType.parse("string");
      String fileName = configDir + File.separator + getConfigFileName();
      ActivityrouletteConfigChecker checker = new ActivityrouletteConfigChecker();
      List<ActivityrouletteConfig> newList = new ArrayList<>();
      Map<Integer, ActivityrouletteConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          int parsedField1 = 0;
          int parsedField2 = 0;
          int parsedField3 = 0;
          int parsedField4 = 0;
          int parsedField5 = 0;
          int parsedField6 = 0;
          int parsedField7 = 0;
          int parsedField8 = 0;
          int parsedField9 = 0;
          int parsedField10 = 0;
          int parsedField11 = 0;
          String parsedField12 = null;
          int parsedField13 = 0;
          int parsedField14 = 0;
          String parsedField15 = null;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("scheDuling"), fileName + ":行" + lineNumber + ":字段 scheDuling");
            parsedField2 = ly.ConfigValues.read(type2, values.get("prizeId"), fileName + ":行" + lineNumber + ":字段 prizeId");
            parsedField3 = ly.ConfigValues.read(type3, values.get("luckyPrize"), fileName + ":行" + lineNumber + ":字段 luckyPrize");
            parsedField4 = ly.ConfigValues.read(type4, values.get("rafflePoints"), fileName + ":行" + lineNumber + ":字段 rafflePoints");
            parsedField5 = ly.ConfigValues.read(type5, values.get("minimumPoints"), fileName + ":行" + lineNumber + ":字段 minimumPoints");
            parsedField6 = ly.ConfigValues.read(type6, values.get("consumableProps"), fileName + ":行" + lineNumber + ":字段 consumableProps");
            parsedField7 = ly.ConfigValues.read(type7, values.get("consumptionQuantity"), fileName + ":行" + lineNumber + ":字段 consumptionQuantity");
            parsedField8 = ly.ConfigValues.read(type8, values.get("luckValueCap"), fileName + ":行" + lineNumber + ":字段 luckValueCap");
            parsedField9 = ly.ConfigValues.read(type9, values.get("resetLuck"), fileName + ":行" + lineNumber + ":字段 resetLuck");
            parsedField10 = ly.ConfigValues.read(type10, values.get("getLucky"), fileName + ":行" + lineNumber + ":字段 getLucky");
            parsedField11 = ly.ConfigValues.read(type11, values.get("lotteryGuarantee"), fileName + ":行" + lineNumber + ":字段 lotteryGuarantee");
            parsedField12 = ly.ConfigValues.read(type12, values.get("rewardShow"), fileName + ":行" + lineNumber + ":字段 rewardShow");
            parsedField13 = ly.ConfigValues.read(type13, values.get("drop"), fileName + ":行" + lineNumber + ":字段 drop");
            parsedField14 = ly.ConfigValues.read(type14, values.get("numberAwards"), fileName + ":行" + lineNumber + ":字段 numberAwards");
            parsedField15 = ly.ConfigValues.read(type15, values.get("picture"), fileName + ":行" + lineNumber + ":字段 picture");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          ActivityrouletteConfig config = new ActivityrouletteConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8, parsedField9, parsedField10, parsedField11, parsedField12, parsedField13, parsedField14, parsedField15);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (ActivityrouletteConfig config : configList) config.afterLoad();
      new ActivityrouletteConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<ActivityrouletteConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, ActivityrouletteConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "activityroulette.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
