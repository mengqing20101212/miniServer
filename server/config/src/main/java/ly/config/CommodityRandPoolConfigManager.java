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
 * File: CommodityRandPoolConfigManager
 */
public class CommodityRandPoolConfigManager implements InterfaceConfigManagerProxy {
  public static CommodityRandPoolConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(CommodityRandPoolConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new CommodityRandPoolConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "commodityRandPool.xml"; }

  public static class CommodityRandPoolConfigManagerImpl extends AbstractConfigManger {
    private List<CommodityRandPoolConfig> configList = List.of();
    private Map<Integer, CommodityRandPoolConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type5 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type7 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type8 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type9 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type10 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type11 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type12 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type13 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type14 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type15 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type16 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type17 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type18 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type19 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type20 = ly.ConfigValueType.parse("string");
      String fileName = configDir + File.separator + getConfigFileName();
      CommodityRandPoolConfigChecker checker = new CommodityRandPoolConfigChecker();
      List<CommodityRandPoolConfig> newList = new ArrayList<>();
      Map<Integer, CommodityRandPoolConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          int parsedField1 = 0;
          int parsedField2 = 0;
          int parsedField3 = 0;
          String parsedField4 = null;
          String parsedField5 = null;
          String parsedField6 = null;
          String parsedField7 = null;
          String parsedField8 = null;
          String parsedField9 = null;
          String parsedField10 = null;
          String parsedField11 = null;
          String parsedField12 = null;
          String parsedField13 = null;
          String parsedField14 = null;
          String parsedField15 = null;
          String parsedField16 = null;
          String parsedField17 = null;
          String parsedField18 = null;
          String parsedField19 = null;
          String parsedField20 = null;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("grade"), fileName + ":行" + lineNumber + ":字段 grade");
            parsedField2 = ly.ConfigValues.read(type2, values.get("school"), fileName + ":行" + lineNumber + ":字段 school");
            parsedField3 = ly.ConfigValues.read(type3, values.get("group"), fileName + ":行" + lineNumber + ":字段 group");
            parsedField4 = ly.ConfigValues.read(type4, values.get("des"), fileName + ":行" + lineNumber + ":字段 des");
            parsedField5 = ly.ConfigValues.read(type5, values.get("poolOneLevel"), fileName + ":行" + lineNumber + ":字段 poolOneLevel");
            parsedField6 = ly.ConfigValues.read(type6, values.get("poolOne"), fileName + ":行" + lineNumber + ":字段 poolOne");
            parsedField7 = ly.ConfigValues.read(type7, values.get("poolTwoLevel"), fileName + ":行" + lineNumber + ":字段 poolTwoLevel");
            parsedField8 = ly.ConfigValues.read(type8, values.get("poolTwo"), fileName + ":行" + lineNumber + ":字段 poolTwo");
            parsedField9 = ly.ConfigValues.read(type9, values.get("poolThreeLevel"), fileName + ":行" + lineNumber + ":字段 poolThreeLevel");
            parsedField10 = ly.ConfigValues.read(type10, values.get("poolThree"), fileName + ":行" + lineNumber + ":字段 poolThree");
            parsedField11 = ly.ConfigValues.read(type11, values.get("poolFourLevel"), fileName + ":行" + lineNumber + ":字段 poolFourLevel");
            parsedField12 = ly.ConfigValues.read(type12, values.get("poolFour"), fileName + ":行" + lineNumber + ":字段 poolFour");
            parsedField13 = ly.ConfigValues.read(type13, values.get("poolFiveLevel"), fileName + ":行" + lineNumber + ":字段 poolFiveLevel");
            parsedField14 = ly.ConfigValues.read(type14, values.get("poolFive"), fileName + ":行" + lineNumber + ":字段 poolFive");
            parsedField15 = ly.ConfigValues.read(type15, values.get("poolSixLevel"), fileName + ":行" + lineNumber + ":字段 poolSixLevel");
            parsedField16 = ly.ConfigValues.read(type16, values.get("poolSix"), fileName + ":行" + lineNumber + ":字段 poolSix");
            parsedField17 = ly.ConfigValues.read(type17, values.get("poolSevenLevel"), fileName + ":行" + lineNumber + ":字段 poolSevenLevel");
            parsedField18 = ly.ConfigValues.read(type18, values.get("poolSeven"), fileName + ":行" + lineNumber + ":字段 poolSeven");
            parsedField19 = ly.ConfigValues.read(type19, values.get("poolEightLevel"), fileName + ":行" + lineNumber + ":字段 poolEightLevel");
            parsedField20 = ly.ConfigValues.read(type20, values.get("poolEight"), fileName + ":行" + lineNumber + ":字段 poolEight");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          CommodityRandPoolConfig config = new CommodityRandPoolConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8, parsedField9, parsedField10, parsedField11, parsedField12, parsedField13, parsedField14, parsedField15, parsedField16, parsedField17, parsedField18, parsedField19, parsedField20);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (CommodityRandPoolConfig config : configList) config.afterLoad();
      new CommodityRandPoolConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<CommodityRandPoolConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, CommodityRandPoolConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "commodityRandPool.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
