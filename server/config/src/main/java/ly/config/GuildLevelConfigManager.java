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
 * File: GuildLevelConfigManager
 */
public class GuildLevelConfigManager implements InterfaceConfigManagerProxy {
  public static GuildLevelConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(GuildLevelConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new GuildLevelConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "guildLevel.xml"; }

  public static class GuildLevelConfigManagerImpl extends AbstractConfigManger {
    private List<GuildLevelConfig> configList = List.of();
    private Map<Integer, GuildLevelConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type5 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type7 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type8 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type9 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type10 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type11 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type12 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type13 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type14 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type15 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type16 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type17 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type18 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type19 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type20 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type21 = ly.ConfigValueType.parse("string");
      String fileName = configDir + File.separator + getConfigFileName();
      GuildLevelConfigChecker checker = new GuildLevelConfigChecker();
      List<GuildLevelConfig> newList = new ArrayList<>();
      Map<Integer, GuildLevelConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          int parsedField1 = 0;
          int parsedField2 = 0;
          int parsedField3 = 0;
          String parsedField4 = null;
          int parsedField5 = 0;
          int parsedField6 = 0;
          int parsedField7 = 0;
          int parsedField8 = 0;
          String parsedField9 = null;
          String parsedField10 = null;
          int parsedField11 = 0;
          int parsedField12 = 0;
          int parsedField13 = 0;
          int parsedField14 = 0;
          int parsedField15 = 0;
          int parsedField16 = 0;
          int parsedField17 = 0;
          int parsedField18 = 0;
          int parsedField19 = 0;
          int parsedField20 = 0;
          String parsedField21 = null;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("level"), fileName + ":行" + lineNumber + ":字段 level");
            parsedField2 = ly.ConfigValues.read(type2, values.get("fundsCost"), fileName + ":行" + lineNumber + ":字段 fundsCost");
            parsedField3 = ly.ConfigValues.read(type3, values.get("donateNum"), fileName + ":行" + lineNumber + ":字段 donateNum");
            parsedField4 = ly.ConfigValues.read(type4, values.get("Contribution"), fileName + ":行" + lineNumber + ":字段 Contribution");
            parsedField5 = ly.ConfigValues.read(type5, values.get("contributionMax"), fileName + ":行" + lineNumber + ":字段 contributionMax");
            parsedField6 = ly.ConfigValues.read(type6, values.get("maintainCost"), fileName + ":行" + lineNumber + ":字段 maintainCost");
            parsedField7 = ly.ConfigValues.read(type7, values.get("maintainCostUrgent"), fileName + ":行" + lineNumber + ":字段 maintainCostUrgent");
            parsedField8 = ly.ConfigValues.read(type8, values.get("numberLimit"), fileName + ":行" + lineNumber + ":字段 numberLimit");
            parsedField9 = ly.ConfigValues.read(type9, values.get("officialLimit"), fileName + ":行" + lineNumber + ":字段 officialLimit");
            parsedField10 = ly.ConfigValues.read(type10, values.get("functionList"), fileName + ":行" + lineNumber + ":字段 functionList");
            parsedField11 = ly.ConfigValues.read(type11, values.get("wealShopLv"), fileName + ":行" + lineNumber + ":字段 wealShopLv");
            parsedField12 = ly.ConfigValues.read(type12, values.get("prestigeShopLV"), fileName + ":行" + lineNumber + ":字段 prestigeShopLV");
            parsedField13 = ly.ConfigValues.read(type13, values.get("badgeMax"), fileName + ":行" + lineNumber + ":字段 badgeMax");
            parsedField14 = ly.ConfigValues.read(type14, values.get("dailyAffordableNum"), fileName + ":行" + lineNumber + ":字段 dailyAffordableNum");
            parsedField15 = ly.ConfigValues.read(type15, values.get("dailyLimitNum"), fileName + ":行" + lineNumber + ":字段 dailyLimitNum");
            parsedField16 = ly.ConfigValues.read(type16, values.get("weeklyAffordableNum"), fileName + ":行" + lineNumber + ":字段 weeklyAffordableNum");
            parsedField17 = ly.ConfigValues.read(type17, values.get("weeklyLimitNum"), fileName + ":行" + lineNumber + ":字段 weeklyLimitNum");
            parsedField18 = ly.ConfigValues.read(type18, values.get("refreshShopNum"), fileName + ":行" + lineNumber + ":字段 refreshShopNum");
            parsedField19 = ly.ConfigValues.read(type19, values.get("refreshShopPositionNum"), fileName + ":行" + lineNumber + ":字段 refreshShopPositionNum");
            parsedField20 = ly.ConfigValues.read(type20, values.get("refreshShopBuyNum"), fileName + ":行" + lineNumber + ":字段 refreshShopBuyNum");
            parsedField21 = ly.ConfigValues.read(type21, values.get("giftBagNum"), fileName + ":行" + lineNumber + ":字段 giftBagNum");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          GuildLevelConfig config = new GuildLevelConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8, parsedField9, parsedField10, parsedField11, parsedField12, parsedField13, parsedField14, parsedField15, parsedField16, parsedField17, parsedField18, parsedField19, parsedField20, parsedField21);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (GuildLevelConfig config : configList) config.afterLoad();
      new GuildLevelConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<GuildLevelConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, GuildLevelConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "guildLevel.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
