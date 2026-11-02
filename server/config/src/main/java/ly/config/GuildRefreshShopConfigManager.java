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
 * File: GuildRefreshShopConfigManager
 */
public class GuildRefreshShopConfigManager implements InterfaceConfigManagerProxy {
  public static GuildRefreshShopConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(GuildRefreshShopConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new GuildRefreshShopConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "guildRefreshShop.xml"; }

  public static class GuildRefreshShopConfigManagerImpl extends AbstractConfigManger {
    private List<GuildRefreshShopConfig> configList = List.of();
    private Map<Integer, GuildRefreshShopConfig> configMap = Map.of();

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
      ly.ConfigValueType type8 = ly.ConfigValueType.parse("string");
      String fileName = configDir + File.separator + getConfigFileName();
      GuildRefreshShopConfigChecker checker = new GuildRefreshShopConfigChecker();
      List<GuildRefreshShopConfig> newList = new ArrayList<>();
      Map<Integer, GuildRefreshShopConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          int parsedField1 = 0;
          int parsedField2 = 0;
          int parsedField3 = 0;
          int parsedField4 = 0;
          int parsedField5 = 0;
          int parsedField6 = 0;
          int parsedField7 = 0;
          String parsedField8 = null;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("itemId"), fileName + ":行" + lineNumber + ":字段 itemId");
            parsedField2 = ly.ConfigValues.read(type2, values.get("itemNum"), fileName + ":行" + lineNumber + ":字段 itemNum");
            parsedField3 = ly.ConfigValues.read(type3, values.get("weight"), fileName + ":行" + lineNumber + ":字段 weight");
            parsedField4 = ly.ConfigValues.read(type4, values.get("currencyType"), fileName + ":行" + lineNumber + ":字段 currencyType");
            parsedField5 = ly.ConfigValues.read(type5, values.get("price"), fileName + ":行" + lineNumber + ":字段 price");
            parsedField6 = ly.ConfigValues.read(type6, values.get("discount"), fileName + ":行" + lineNumber + ":字段 discount");
            parsedField7 = ly.ConfigValues.read(type7, values.get("level"), fileName + ":行" + lineNumber + ":字段 level");
            parsedField8 = ly.ConfigValues.read(type8, values.get("coloredEggs"), fileName + ":行" + lineNumber + ":字段 coloredEggs");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          GuildRefreshShopConfig config = new GuildRefreshShopConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (GuildRefreshShopConfig config : configList) config.afterLoad();
      new GuildRefreshShopConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<GuildRefreshShopConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, GuildRefreshShopConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "guildRefreshShop.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
