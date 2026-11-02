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
 * File: ShopType1ConfigManager
 */
public class ShopType1ConfigManager implements InterfaceConfigManagerProxy {
  public static ShopType1ConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(ShopType1ConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new ShopType1ConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "shopType1.xml"; }

  public static class ShopType1ConfigManagerImpl extends AbstractConfigManger {
    private List<ShopType1Config> configList = List.of();
    private Map<Integer, ShopType1Config> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type5 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type7 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type8 = ly.ConfigValueType.parse("int");
      String fileName = configDir + File.separator + getConfigFileName();
      ShopType1ConfigChecker checker = new ShopType1ConfigChecker();
      List<ShopType1Config> newList = new ArrayList<>();
      Map<Integer, ShopType1Config> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          String parsedField1 = null;
          int parsedField2 = 0;
          String parsedField3 = null;
          int parsedField4 = 0;
          int parsedField5 = 0;
          int parsedField6 = 0;
          int parsedField7 = 0;
          int parsedField8 = 0;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("name"), fileName + ":行" + lineNumber + ":字段 name");
            parsedField2 = ly.ConfigValues.read(type2, values.get("type"), fileName + ":行" + lineNumber + ":字段 type");
            parsedField3 = ly.ConfigValues.read(type3, values.get("subShopList"), fileName + ":行" + lineNumber + ":字段 subShopList");
            parsedField4 = ly.ConfigValues.read(type4, values.get("openType"), fileName + ":行" + lineNumber + ":字段 openType");
            parsedField5 = ly.ConfigValues.read(type5, values.get("openPara1"), fileName + ":行" + lineNumber + ":字段 openPara1");
            parsedField6 = ly.ConfigValues.read(type6, values.get("openPara2"), fileName + ":行" + lineNumber + ":字段 openPara2");
            parsedField7 = ly.ConfigValues.read(type7, values.get("isShow"), fileName + ":行" + lineNumber + ":字段 isShow");
            parsedField8 = ly.ConfigValues.read(type8, values.get("showSort"), fileName + ":行" + lineNumber + ":字段 showSort");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          ShopType1Config config = new ShopType1Config(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (ShopType1Config config : configList) config.afterLoad();
      new ShopType1ConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<ShopType1Config> getConfigList() {
      return configList;
    }

    public Map<Integer, ShopType1Config> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "shopType1.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
