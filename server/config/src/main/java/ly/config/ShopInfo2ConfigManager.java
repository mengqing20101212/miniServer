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
 * File: ShopInfo2ConfigManager
 */
public class ShopInfo2ConfigManager implements InterfaceConfigManagerProxy {
  public static ShopInfo2ConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(ShopInfo2ConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new ShopInfo2ConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "shopInfo2.xml"; }

  public static class ShopInfo2ConfigManagerImpl extends AbstractConfigManger {
    private List<ShopInfo2Config> configList = List.of();
    private Map<Integer, ShopInfo2Config> configMap = Map.of();

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
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type7 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type8 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type9 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type10 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type11 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type12 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type13 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type14 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type15 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type16 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type17 = ly.ConfigValueType.parse("int");
      String fileName = configDir + File.separator + getConfigFileName();
      ShopInfo2ConfigChecker checker = new ShopInfo2ConfigChecker();
      List<ShopInfo2Config> newList = new ArrayList<>();
      Map<Integer, ShopInfo2Config> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          int parsedField1 = 0;
          int parsedField2 = 0;
          String parsedField3 = null;
          int parsedField4 = 0;
          int parsedField5 = 0;
          String parsedField6 = null;
          String parsedField7 = null;
          int parsedField8 = 0;
          int parsedField9 = 0;
          int parsedField10 = 0;
          int parsedField11 = 0;
          int parsedField12 = 0;
          int parsedField13 = 0;
          int parsedField14 = 0;
          int parsedField15 = 0;
          int parsedField16 = 0;
          int parsedField17 = 0;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("grade"), fileName + ":行" + lineNumber + ":字段 grade");
            parsedField2 = ly.ConfigValues.read(type2, values.get("school"), fileName + ":行" + lineNumber + ":字段 school");
            parsedField3 = ly.ConfigValues.read(type3, values.get("name"), fileName + ":行" + lineNumber + ":字段 name");
            parsedField4 = ly.ConfigValues.read(type4, values.get("type"), fileName + ":行" + lineNumber + ":字段 type");
            parsedField5 = ly.ConfigValues.read(type5, values.get("showType"), fileName + ":行" + lineNumber + ":字段 showType");
            parsedField6 = ly.ConfigValues.read(type6, values.get("shopPara"), fileName + ":行" + lineNumber + ":字段 shopPara");
            parsedField7 = ly.ConfigValues.read(type7, values.get("commodityList"), fileName + ":行" + lineNumber + ":字段 commodityList");
            parsedField8 = ly.ConfigValues.read(type8, values.get("openType"), fileName + ":行" + lineNumber + ":字段 openType");
            parsedField9 = ly.ConfigValues.read(type9, values.get("RefreshBarType"), fileName + ":行" + lineNumber + ":字段 RefreshBarType");
            parsedField10 = ly.ConfigValues.read(type10, values.get("openPara1"), fileName + ":行" + lineNumber + ":字段 openPara1");
            parsedField11 = ly.ConfigValues.read(type11, values.get("openPara2"), fileName + ":行" + lineNumber + ":字段 openPara2");
            parsedField12 = ly.ConfigValues.read(type12, values.get("openType2"), fileName + ":行" + lineNumber + ":字段 openType2");
            parsedField13 = ly.ConfigValues.read(type13, values.get("openPara3"), fileName + ":行" + lineNumber + ":字段 openPara3");
            parsedField14 = ly.ConfigValues.read(type14, values.get("topId"), fileName + ":行" + lineNumber + ":字段 topId");
            parsedField15 = ly.ConfigValues.read(type15, values.get("background"), fileName + ":行" + lineNumber + ":字段 background");
            parsedField16 = ly.ConfigValues.read(type16, values.get("subStoreShow"), fileName + ":行" + lineNumber + ":字段 subStoreShow");
            parsedField17 = ly.ConfigValues.read(type17, values.get("shopShow"), fileName + ":行" + lineNumber + ":字段 shopShow");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          ShopInfo2Config config = new ShopInfo2Config(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8, parsedField9, parsedField10, parsedField11, parsedField12, parsedField13, parsedField14, parsedField15, parsedField16, parsedField17);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (ShopInfo2Config config : configList) config.afterLoad();
      new ShopInfo2ConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<ShopInfo2Config> getConfigList() {
      return configList;
    }

    public Map<Integer, ShopInfo2Config> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "shopInfo2.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
