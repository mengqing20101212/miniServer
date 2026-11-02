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
 * File: ActivitywishingConfigManager
 */
public class ActivitywishingConfigManager implements InterfaceConfigManagerProxy {
  public static ActivitywishingConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(ActivitywishingConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new ActivitywishingConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "activitywishing.xml"; }

  public static class ActivitywishingConfigManagerImpl extends AbstractConfigManger {
    private List<ActivitywishingConfig> configList = List.of();
    private Map<Integer, ActivitywishingConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type5 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type7 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type8 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type9 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type10 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type11 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type12 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type13 = ly.ConfigValueType.parse("string");
      String fileName = configDir + File.separator + getConfigFileName();
      ActivitywishingConfigChecker checker = new ActivitywishingConfigChecker();
      List<ActivitywishingConfig> newList = new ArrayList<>();
      Map<Integer, ActivitywishingConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          int parsedField1 = 0;
          String parsedField2 = null;
          String parsedField3 = null;
          String parsedField4 = null;
          int parsedField5 = 0;
          int parsedField6 = 0;
          String parsedField7 = null;
          String parsedField8 = null;
          int parsedField9 = 0;
          int parsedField10 = 0;
          String parsedField11 = null;
          String parsedField12 = null;
          String parsedField13 = null;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("wishingNum"), fileName + ":行" + lineNumber + ":字段 wishingNum");
            parsedField2 = ly.ConfigValues.read(type2, values.get("awardList"), fileName + ":行" + lineNumber + ":字段 awardList");
            parsedField3 = ly.ConfigValues.read(type3, values.get("awardRelativePro"), fileName + ":行" + lineNumber + ":字段 awardRelativePro");
            parsedField4 = ly.ConfigValues.read(type4, values.get("desc"), fileName + ":行" + lineNumber + ":字段 desc");
            parsedField5 = ly.ConfigValues.read(type5, values.get("dayLimit"), fileName + ":行" + lineNumber + ":字段 dayLimit");
            parsedField6 = ly.ConfigValues.read(type6, values.get("Guaranteed"), fileName + ":行" + lineNumber + ":字段 Guaranteed");
            parsedField7 = ly.ConfigValues.read(type7, values.get("Guaranteerange"), fileName + ":行" + lineNumber + ":字段 Guaranteerange");
            parsedField8 = ly.ConfigValues.read(type8, values.get("GuaranteerangeWeights"), fileName + ":行" + lineNumber + ":字段 GuaranteerangeWeights");
            parsedField9 = ly.ConfigValues.read(type9, values.get("intervaltime"), fileName + ":行" + lineNumber + ":字段 intervaltime");
            parsedField10 = ly.ConfigValues.read(type10, values.get("Receiveaward"), fileName + ":行" + lineNumber + ":字段 Receiveaward");
            parsedField11 = ly.ConfigValues.read(type11, values.get("rewardShow"), fileName + ":行" + lineNumber + ":字段 rewardShow");
            parsedField12 = ly.ConfigValues.read(type12, values.get("pictures1"), fileName + ":行" + lineNumber + ":字段 pictures1");
            parsedField13 = ly.ConfigValues.read(type13, values.get("pictures2"), fileName + ":行" + lineNumber + ":字段 pictures2");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          ActivitywishingConfig config = new ActivitywishingConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8, parsedField9, parsedField10, parsedField11, parsedField12, parsedField13);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (ActivitywishingConfig config : configList) config.afterLoad();
      new ActivitywishingConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<ActivitywishingConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, ActivitywishingConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "activitywishing.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
