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
 * File: RechargeShopConfigManager
 */
public class RechargeShopConfigManager implements InterfaceConfigManagerProxy {
  public static RechargeShopConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(RechargeShopConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new RechargeShopConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "RechargeShop.xml"; }

  public static class RechargeShopConfigManagerImpl extends AbstractConfigManger {
    private List<RechargeShopConfig> configList = List.of();
    private Map<Integer, RechargeShopConfig> configMap = Map.of();

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
      ly.ConfigValueType type9 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type10 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type11 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type12 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type13 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type14 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type15 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type16 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type17 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type18 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type19 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type20 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type21 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type22 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type23 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type24 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type25 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type26 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type27 = ly.ConfigValueType.parse("int");
      String fileName = configDir + File.separator + getConfigFileName();
      RechargeShopConfigChecker checker = new RechargeShopConfigChecker();
      List<RechargeShopConfig> newList = new ArrayList<>();
      Map<Integer, RechargeShopConfig> newMap = new HashMap<>();
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
          String parsedField9 = null;
          int parsedField10 = 0;
          int parsedField11 = 0;
          int parsedField12 = 0;
          int parsedField13 = 0;
          String parsedField14 = null;
          int parsedField15 = 0;
          int parsedField16 = 0;
          int parsedField17 = 0;
          int parsedField18 = 0;
          int parsedField19 = 0;
          int parsedField20 = 0;
          String parsedField21 = null;
          int parsedField22 = 0;
          String parsedField23 = null;
          int parsedField24 = 0;
          int parsedField25 = 0;
          int parsedField26 = 0;
          int parsedField27 = 0;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("name"), fileName + ":行" + lineNumber + ":字段 name");
            parsedField2 = ly.ConfigValues.read(type2, values.get("bugFDesc"), fileName + ":行" + lineNumber + ":字段 bugFDesc");
            parsedField3 = ly.ConfigValues.read(type3, values.get("bugDesc"), fileName + ":行" + lineNumber + ":字段 bugDesc");
            parsedField4 = ly.ConfigValues.read(type4, values.get("bugType"), fileName + ":行" + lineNumber + ":字段 bugType");
            parsedField5 = ly.ConfigValues.read(type5, values.get("levelUpperLimit"), fileName + ":行" + lineNumber + ":字段 levelUpperLimit");
            parsedField6 = ly.ConfigValues.read(type6, values.get("drop"), fileName + ":行" + lineNumber + ":字段 drop");
            parsedField7 = ly.ConfigValues.read(type7, values.get("dropShow"), fileName + ":行" + lineNumber + ":字段 dropShow");
            parsedField8 = ly.ConfigValues.read(type8, values.get("reward"), fileName + ":行" + lineNumber + ":字段 reward");
            parsedField9 = ly.ConfigValues.read(type9, values.get("give"), fileName + ":行" + lineNumber + ":字段 give");
            parsedField10 = ly.ConfigValues.read(type10, values.get("priceType"), fileName + ":行" + lineNumber + ":字段 priceType");
            parsedField11 = ly.ConfigValues.read(type11, values.get("price"), fileName + ":行" + lineNumber + ":字段 price");
            parsedField12 = ly.ConfigValues.read(type12, values.get("priceShow"), fileName + ":行" + lineNumber + ":字段 priceShow");
            parsedField13 = ly.ConfigValues.read(type13, values.get("PrePriceShow"), fileName + ":行" + lineNumber + ":字段 PrePriceShow");
            parsedField14 = ly.ConfigValues.read(type14, values.get("page"), fileName + ":行" + lineNumber + ":字段 page");
            parsedField15 = ly.ConfigValues.read(type15, values.get("page1"), fileName + ":行" + lineNumber + ":字段 page1");
            parsedField16 = ly.ConfigValues.read(type16, values.get("priority"), fileName + ":行" + lineNumber + ":字段 priority");
            parsedField17 = ly.ConfigValues.read(type17, values.get("missionID"), fileName + ":行" + lineNumber + ":字段 missionID");
            parsedField18 = ly.ConfigValues.read(type18, values.get("cardPower"), fileName + ":行" + lineNumber + ":字段 cardPower");
            parsedField19 = ly.ConfigValues.read(type19, values.get("limtType"), fileName + ":行" + lineNumber + ":字段 limtType");
            parsedField20 = ly.ConfigValues.read(type20, values.get("limt"), fileName + ":行" + lineNumber + ":字段 limt");
            parsedField21 = ly.ConfigValues.read(type21, values.get("icon"), fileName + ":行" + lineNumber + ":字段 icon");
            parsedField22 = ly.ConfigValues.read(type22, values.get("duration"), fileName + ":行" + lineNumber + ":字段 duration");
            parsedField23 = ly.ConfigValues.read(type23, values.get("closeTime"), fileName + ":行" + lineNumber + ":字段 closeTime");
            parsedField24 = ly.ConfigValues.read(type24, values.get("giveCloseTime"), fileName + ":行" + lineNumber + ":字段 giveCloseTime");
            parsedField25 = ly.ConfigValues.read(type25, values.get("mail"), fileName + ":行" + lineNumber + ":字段 mail");
            parsedField26 = ly.ConfigValues.read(type26, values.get("activityId"), fileName + ":行" + lineNumber + ":字段 activityId");
            parsedField27 = ly.ConfigValues.read(type27, values.get("buySupermarket"), fileName + ":行" + lineNumber + ":字段 buySupermarket");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          RechargeShopConfig config = new RechargeShopConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8, parsedField9, parsedField10, parsedField11, parsedField12, parsedField13, parsedField14, parsedField15, parsedField16, parsedField17, parsedField18, parsedField19, parsedField20, parsedField21, parsedField22, parsedField23, parsedField24, parsedField25, parsedField26, parsedField27);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (RechargeShopConfig config : configList) config.afterLoad();
      new RechargeShopConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<RechargeShopConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, RechargeShopConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "RechargeShop.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
