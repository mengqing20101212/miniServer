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
 * File: SaitamaLevelConfigManager
 */
public class SaitamaLevelConfigManager implements InterfaceConfigManagerProxy {
  public static SaitamaLevelConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(SaitamaLevelConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new SaitamaLevelConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "saitamaLevel.xml"; }

  public static class SaitamaLevelConfigManagerImpl extends AbstractConfigManger {
    private List<SaitamaLevelConfig> configList = List.of();
    private Map<Integer, SaitamaLevelConfig> configMap = Map.of();

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
      ly.ConfigValueType type12 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type13 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type14 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type15 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type16 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type17 = ly.ConfigValueType.parse("int");
      String fileName = configDir + File.separator + getConfigFileName();
      SaitamaLevelConfigChecker checker = new SaitamaLevelConfigChecker();
      List<SaitamaLevelConfig> newList = new ArrayList<>();
      Map<Integer, SaitamaLevelConfig> newMap = new HashMap<>();
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
          int parsedField12 = 0;
          int parsedField13 = 0;
          int parsedField14 = 0;
          String parsedField15 = null;
          String parsedField16 = null;
          int parsedField17 = 0;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("nextLv"), fileName + ":行" + lineNumber + ":字段 nextLv");
            parsedField2 = ly.ConfigValues.read(type2, values.get("dailyGift"), fileName + ":行" + lineNumber + ":字段 dailyGift");
            parsedField3 = ly.ConfigValues.read(type3, values.get("dailyGiftShow"), fileName + ":行" + lineNumber + ":字段 dailyGiftShow");
            parsedField4 = ly.ConfigValues.read(type4, values.get("giftTimes"), fileName + ":行" + lineNumber + ":字段 giftTimes");
            parsedField5 = ly.ConfigValues.read(type5, values.get("summonLevel"), fileName + ":行" + lineNumber + ":字段 summonLevel");
            parsedField6 = ly.ConfigValues.read(type6, values.get("exdropLevel"), fileName + ":行" + lineNumber + ":字段 exdropLevel");
            parsedField7 = ly.ConfigValues.read(type7, values.get("cookStar"), fileName + ":行" + lineNumber + ":字段 cookStar");
            parsedField8 = ly.ConfigValues.read(type8, values.get("cookmixPara"), fileName + ":行" + lineNumber + ":字段 cookmixPara");
            parsedField9 = ly.ConfigValues.read(type9, values.get("cookCritical"), fileName + ":行" + lineNumber + ":字段 cookCritical");
            parsedField10 = ly.ConfigValues.read(type10, values.get("cookSlot"), fileName + ":行" + lineNumber + ":字段 cookSlot");
            parsedField11 = ly.ConfigValues.read(type11, values.get("overflowMax"), fileName + ":行" + lineNumber + ":字段 overflowMax");
            parsedField12 = ly.ConfigValues.read(type12, values.get("conversionRate"), fileName + ":行" + lineNumber + ":字段 conversionRate");
            parsedField13 = ly.ConfigValues.read(type13, values.get("eggRewardTimes"), fileName + ":行" + lineNumber + ":字段 eggRewardTimes");
            parsedField14 = ly.ConfigValues.read(type14, values.get("trianningRewardLv"), fileName + ":行" + lineNumber + ":字段 trianningRewardLv");
            parsedField15 = ly.ConfigValues.read(type15, values.get("infoList"), fileName + ":行" + lineNumber + ":字段 infoList");
            parsedField16 = ly.ConfigValues.read(type16, values.get("upgradeInfo"), fileName + ":行" + lineNumber + ":字段 upgradeInfo");
            parsedField17 = ly.ConfigValues.read(type17, values.get("headIcon"), fileName + ":行" + lineNumber + ":字段 headIcon");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          SaitamaLevelConfig config = new SaitamaLevelConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8, parsedField9, parsedField10, parsedField11, parsedField12, parsedField13, parsedField14, parsedField15, parsedField16, parsedField17);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (SaitamaLevelConfig config : configList) config.afterLoad();
      new SaitamaLevelConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<SaitamaLevelConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, SaitamaLevelConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "saitamaLevel.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
