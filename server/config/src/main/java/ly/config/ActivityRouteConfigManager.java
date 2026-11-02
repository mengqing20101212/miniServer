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
 * File: ActivityRouteConfigManager
 */
public class ActivityRouteConfigManager implements InterfaceConfigManagerProxy {
  public static ActivityRouteConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(ActivityRouteConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new ActivityRouteConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "activityRoute.xml"; }

  public static class ActivityRouteConfigManagerImpl extends AbstractConfigManger {
    private List<ActivityRouteConfig> configList = List.of();
    private Map<Integer, ActivityRouteConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type5 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type7 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type8 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type9 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type10 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type11 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type12 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type13 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type14 = ly.ConfigValueType.parse("string");
      String fileName = configDir + File.separator + getConfigFileName();
      ActivityRouteConfigChecker checker = new ActivityRouteConfigChecker();
      List<ActivityRouteConfig> newList = new ArrayList<>();
      Map<Integer, ActivityRouteConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          int parsedField1 = 0;
          String parsedField2 = null;
          int parsedField3 = 0;
          int parsedField4 = 0;
          int parsedField5 = 0;
          String parsedField6 = null;
          int parsedField7 = 0;
          String parsedField8 = null;
          int parsedField9 = 0;
          int parsedField10 = 0;
          int parsedField11 = 0;
          String parsedField12 = null;
          String parsedField13 = null;
          String parsedField14 = null;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("drop"), fileName + ":行" + lineNumber + ":字段 drop");
            parsedField2 = ly.ConfigValues.read(type2, values.get("rewardShow"), fileName + ":行" + lineNumber + ":字段 rewardShow");
            parsedField3 = ly.ConfigValues.read(type3, values.get("score"), fileName + ":行" + lineNumber + ":字段 score");
            parsedField4 = ly.ConfigValues.read(type4, values.get("array"), fileName + ":行" + lineNumber + ":字段 array");
            parsedField5 = ly.ConfigValues.read(type5, values.get("row"), fileName + ":行" + lineNumber + ":字段 row");
            parsedField6 = ly.ConfigValues.read(type6, values.get("front"), fileName + ":行" + lineNumber + ":字段 front");
            parsedField7 = ly.ConfigValues.read(type7, values.get("payGift"), fileName + ":行" + lineNumber + ":字段 payGift");
            parsedField8 = ly.ConfigValues.read(type8, values.get("payGiftShow"), fileName + ":行" + lineNumber + ":字段 payGiftShow");
            parsedField9 = ly.ConfigValues.read(type9, values.get("rechargeId"), fileName + ":行" + lineNumber + ":字段 rechargeId");
            parsedField10 = ly.ConfigValues.read(type10, values.get("isCrossNode"), fileName + ":行" + lineNumber + ":字段 isCrossNode");
            parsedField11 = ly.ConfigValues.read(type11, values.get("drop2"), fileName + ":行" + lineNumber + ":字段 drop2");
            parsedField12 = ly.ConfigValues.read(type12, values.get("rewardShow2"), fileName + ":行" + lineNumber + ":字段 rewardShow2");
            parsedField13 = ly.ConfigValues.read(type13, values.get("line"), fileName + ":行" + lineNumber + ":字段 line");
            parsedField14 = ly.ConfigValues.read(type14, values.get("circle"), fileName + ":行" + lineNumber + ":字段 circle");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          ActivityRouteConfig config = new ActivityRouteConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8, parsedField9, parsedField10, parsedField11, parsedField12, parsedField13, parsedField14);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (ActivityRouteConfig config : configList) config.afterLoad();
      new ActivityRouteConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<ActivityRouteConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, ActivityRouteConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "activityRoute.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
