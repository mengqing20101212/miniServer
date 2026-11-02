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
 * File: ActivitycashGiftConfigManager
 */
public class ActivitycashGiftConfigManager implements InterfaceConfigManagerProxy {
  public static ActivitycashGiftConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(ActivitycashGiftConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new ActivitycashGiftConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "activitycashGift.xml"; }

  public static class ActivitycashGiftConfigManagerImpl extends AbstractConfigManger {
    private List<ActivitycashGiftConfig> configList = List.of();
    private Map<Integer, ActivitycashGiftConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("int");
      String fileName = configDir + File.separator + getConfigFileName();
      ActivitycashGiftConfigChecker checker = new ActivitycashGiftConfigChecker();
      List<ActivitycashGiftConfig> newList = new ArrayList<>();
      Map<Integer, ActivitycashGiftConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          String parsedField1 = null;
          String parsedField2 = null;
          int parsedField3 = 0;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("RecommendedPackage"), fileName + ":行" + lineNumber + ":字段 RecommendedPackage");
            parsedField2 = ly.ConfigValues.read(type2, values.get("GradeInterval"), fileName + ":行" + lineNumber + ":字段 GradeInterval");
            parsedField3 = ly.ConfigValues.read(type3, values.get("IsTopGift"), fileName + ":行" + lineNumber + ":字段 IsTopGift");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          ActivitycashGiftConfig config = new ActivitycashGiftConfig(parsedField0, parsedField1, parsedField2, parsedField3);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (ActivitycashGiftConfig config : configList) config.afterLoad();
      new ActivitycashGiftConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<ActivitycashGiftConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, ActivitycashGiftConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "activitycashGift.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
