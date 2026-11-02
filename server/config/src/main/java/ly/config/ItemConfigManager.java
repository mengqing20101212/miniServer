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
 * File: ItemConfigManager
 */
public class ItemConfigManager implements InterfaceConfigManagerProxy {
  public static ItemConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(ItemConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new ItemConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "item.xml"; }

  public static class ItemConfigManagerImpl extends AbstractConfigManger {
    private List<ItemConfig> configList = List.of();
    private Map<Integer, ItemConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("string");
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
      ly.ConfigValueType type15 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type16 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type17 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type18 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type19 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type20 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type21 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type22 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type23 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type24 = ly.ConfigValueType.parse("int");
      String fileName = configDir + File.separator + getConfigFileName();
      ItemConfigChecker checker = new ItemConfigChecker();
      List<ItemConfig> newList = new ArrayList<>();
      Map<Integer, ItemConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          String parsedField1 = null;
          String parsedField2 = null;
          String parsedField3 = null;
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
          int parsedField15 = 0;
          String parsedField16 = null;
          int parsedField17 = 0;
          int parsedField18 = 0;
          String parsedField19 = null;
          int parsedField20 = 0;
          int parsedField21 = 0;
          int parsedField22 = 0;
          String parsedField23 = null;
          int parsedField24 = 0;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("name"), fileName + ":行" + lineNumber + ":字段 name");
            parsedField2 = ly.ConfigValues.read(type2, values.get("description"), fileName + ":行" + lineNumber + ":字段 description");
            parsedField3 = ly.ConfigValues.read(type3, values.get("index"), fileName + ":行" + lineNumber + ":字段 index");
            parsedField4 = ly.ConfigValues.read(type4, values.get("icon"), fileName + ":行" + lineNumber + ":字段 icon");
            parsedField5 = ly.ConfigValues.read(type5, values.get("subIcon"), fileName + ":行" + lineNumber + ":字段 subIcon");
            parsedField6 = ly.ConfigValues.read(type6, values.get("quality"), fileName + ":行" + lineNumber + ":字段 quality");
            parsedField7 = ly.ConfigValues.read(type7, values.get("type"), fileName + ":行" + lineNumber + ":字段 type");
            parsedField8 = ly.ConfigValues.read(type8, values.get("bagTag"), fileName + ":行" + lineNumber + ":字段 bagTag");
            parsedField9 = ly.ConfigValues.read(type9, values.get("school"), fileName + ":行" + lineNumber + ":字段 school");
            parsedField10 = ly.ConfigValues.read(type10, values.get("grade"), fileName + ":行" + lineNumber + ":字段 grade");
            parsedField11 = ly.ConfigValues.read(type11, values.get("sequence"), fileName + ":行" + lineNumber + ":字段 sequence");
            parsedField12 = ly.ConfigValues.read(type12, values.get("stack"), fileName + ":行" + lineNumber + ":字段 stack");
            parsedField13 = ly.ConfigValues.read(type13, values.get("existType"), fileName + ":行" + lineNumber + ":字段 existType");
            parsedField14 = ly.ConfigValues.read(type14, values.get("existTime"), fileName + ":行" + lineNumber + ":字段 existTime");
            parsedField15 = ly.ConfigValues.read(type15, values.get("canSell"), fileName + ":行" + lineNumber + ":字段 canSell");
            parsedField16 = ly.ConfigValues.read(type16, values.get("sellItem"), fileName + ":行" + lineNumber + ":字段 sellItem");
            parsedField17 = ly.ConfigValues.read(type17, values.get("onlyServer"), fileName + ":行" + lineNumber + ":字段 onlyServer");
            parsedField18 = ly.ConfigValues.read(type18, values.get("knapsackType"), fileName + ":行" + lineNumber + ":字段 knapsackType");
            parsedField19 = ly.ConfigValues.read(type19, values.get("accessWay"), fileName + ":行" + lineNumber + ":字段 accessWay");
            parsedField20 = ly.ConfigValues.read(type20, values.get("useType"), fileName + ":行" + lineNumber + ":字段 useType");
            parsedField21 = ly.ConfigValues.read(type21, values.get("turnId"), fileName + ":行" + lineNumber + ":字段 turnId");
            parsedField22 = ly.ConfigValues.read(type22, values.get("newTips"), fileName + ":行" + lineNumber + ":字段 newTips");
            parsedField23 = ly.ConfigValues.read(type23, values.get("func"), fileName + ":行" + lineNumber + ":字段 func");
            parsedField24 = ly.ConfigValues.read(type24, values.get("level"), fileName + ":行" + lineNumber + ":字段 level");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          ItemConfig config = new ItemConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8, parsedField9, parsedField10, parsedField11, parsedField12, parsedField13, parsedField14, parsedField15, parsedField16, parsedField17, parsedField18, parsedField19, parsedField20, parsedField21, parsedField22, parsedField23, parsedField24);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (ItemConfig config : configList) config.afterLoad();
      new ItemConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<ItemConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, ItemConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "item.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
