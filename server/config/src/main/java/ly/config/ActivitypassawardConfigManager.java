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
 * File: ActivitypassawardConfigManager
 */
public class ActivitypassawardConfigManager implements InterfaceConfigManagerProxy {
  public static ActivitypassawardConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(ActivitypassawardConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new ActivitypassawardConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "activitypassaward.xml"; }

  public static class ActivitypassawardConfigManagerImpl extends AbstractConfigManger {
    private List<ActivitypassawardConfig> configList = List.of();
    private Map<Integer, ActivitypassawardConfig> configMap = Map.of();

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
      String fileName = configDir + File.separator + getConfigFileName();
      ActivitypassawardConfigChecker checker = new ActivitypassawardConfigChecker();
      List<ActivitypassawardConfig> newList = new ArrayList<>();
      Map<Integer, ActivitypassawardConfig> newMap = new HashMap<>();
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
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("scheDuling"), fileName + ":行" + lineNumber + ":字段 scheDuling");
            parsedField2 = ly.ConfigValues.read(type2, values.get("level"), fileName + ":行" + lineNumber + ":字段 level");
            parsedField3 = ly.ConfigValues.read(type3, values.get("payLevel"), fileName + ":行" + lineNumber + ":字段 payLevel");
            parsedField4 = ly.ConfigValues.read(type4, values.get("rechargeShopId"), fileName + ":行" + lineNumber + ":字段 rechargeShopId");
            parsedField5 = ly.ConfigValues.read(type5, values.get("score"), fileName + ":行" + lineNumber + ":字段 score");
            parsedField6 = ly.ConfigValues.read(type6, values.get("freeGift"), fileName + ":行" + lineNumber + ":字段 freeGift");
            parsedField7 = ly.ConfigValues.read(type7, values.get("freeGiftShow"), fileName + ":行" + lineNumber + ":字段 freeGiftShow");
            parsedField8 = ly.ConfigValues.read(type8, values.get("payGift"), fileName + ":行" + lineNumber + ":字段 payGift");
            parsedField9 = ly.ConfigValues.read(type9, values.get("payGiftShow"), fileName + ":行" + lineNumber + ":字段 payGiftShow");
            parsedField10 = ly.ConfigValues.read(type10, values.get("redirectionId"), fileName + ":行" + lineNumber + ":字段 redirectionId");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          ActivitypassawardConfig config = new ActivitypassawardConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8, parsedField9, parsedField10);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (ActivitypassawardConfig config : configList) config.afterLoad();
      new ActivitypassawardConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<ActivitypassawardConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, ActivitypassawardConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "activitypassaward.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
