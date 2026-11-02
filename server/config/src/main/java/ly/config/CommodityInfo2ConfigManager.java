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
 * File: CommodityInfo2ConfigManager
 */
public class CommodityInfo2ConfigManager implements InterfaceConfigManagerProxy {
  public static CommodityInfo2ConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(CommodityInfo2ConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new CommodityInfo2ConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "commodityInfo2.xml"; }

  public static class CommodityInfo2ConfigManagerImpl extends AbstractConfigManger {
    private List<CommodityInfo2Config> configList = List.of();
    private Map<Integer, CommodityInfo2Config> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type5 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type7 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type8 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type9 = ly.ConfigValueType.parse("int");
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
      ly.ConfigValueType type21 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type22 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type23 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type24 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type25 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type26 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type27 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type28 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type29 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type30 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type31 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type32 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type33 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type34 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type35 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type36 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type37 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type38 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type39 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type40 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type41 = ly.ConfigValueType.parse("int");
      String fileName = configDir + File.separator + getConfigFileName();
      CommodityInfo2ConfigChecker checker = new CommodityInfo2ConfigChecker();
      List<CommodityInfo2Config> newList = new ArrayList<>();
      Map<Integer, CommodityInfo2Config> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          int parsedField1 = 0;
          String parsedField2 = null;
          int parsedField3 = 0;
          int parsedField4 = 0;
          int parsedField5 = 0;
          int parsedField6 = 0;
          String parsedField7 = null;
          String parsedField8 = null;
          int parsedField9 = 0;
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
          int parsedField21 = 0;
          String parsedField22 = null;
          String parsedField23 = null;
          String parsedField24 = null;
          int parsedField25 = 0;
          String parsedField26 = null;
          int parsedField27 = 0;
          String parsedField28 = null;
          int parsedField29 = 0;
          int parsedField30 = 0;
          int parsedField31 = 0;
          int parsedField32 = 0;
          int parsedField33 = 0;
          int parsedField34 = 0;
          int parsedField35 = 0;
          int parsedField36 = 0;
          String parsedField37 = null;
          String parsedField38 = null;
          int parsedField39 = 0;
          int parsedField40 = 0;
          int parsedField41 = 0;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("type"), fileName + ":行" + lineNumber + ":字段 type");
            parsedField2 = ly.ConfigValues.read(type2, values.get("typePara"), fileName + ":行" + lineNumber + ":字段 typePara");
            parsedField3 = ly.ConfigValues.read(type3, values.get("grade"), fileName + ":行" + lineNumber + ":字段 grade");
            parsedField4 = ly.ConfigValues.read(type4, values.get("school"), fileName + ":行" + lineNumber + ":字段 school");
            parsedField5 = ly.ConfigValues.read(type5, values.get("group"), fileName + ":行" + lineNumber + ":字段 group");
            parsedField6 = ly.ConfigValues.read(type6, values.get("sequence"), fileName + ":行" + lineNumber + ":字段 sequence");
            parsedField7 = ly.ConfigValues.read(type7, values.get("name"), fileName + ":行" + lineNumber + ":字段 name");
            parsedField8 = ly.ConfigValues.read(type8, values.get("des"), fileName + ":行" + lineNumber + ":字段 des");
            parsedField9 = ly.ConfigValues.read(type9, values.get("icon"), fileName + ":行" + lineNumber + ":字段 icon");
            parsedField10 = ly.ConfigValues.read(type10, values.get("itemList"), fileName + ":行" + lineNumber + ":字段 itemList");
            parsedField11 = ly.ConfigValues.read(type11, values.get("limitType"), fileName + ":行" + lineNumber + ":字段 limitType");
            parsedField12 = ly.ConfigValues.read(type12, values.get("limitPara"), fileName + ":行" + lineNumber + ":字段 limitPara");
            parsedField13 = ly.ConfigValues.read(type13, values.get("levelLowerLimit"), fileName + ":行" + lineNumber + ":字段 levelLowerLimit");
            parsedField14 = ly.ConfigValues.read(type14, values.get("levelUpperLimit"), fileName + ":行" + lineNumber + ":字段 levelUpperLimit");
            parsedField15 = ly.ConfigValues.read(type15, values.get("moneyType"), fileName + ":行" + lineNumber + ":字段 moneyType");
            parsedField16 = ly.ConfigValues.read(type16, values.get("price"), fileName + ":行" + lineNumber + ":字段 price");
            parsedField17 = ly.ConfigValues.read(type17, values.get("priceShow"), fileName + ":行" + lineNumber + ":字段 priceShow");
            parsedField18 = ly.ConfigValues.read(type18, values.get("PrePriceShow"), fileName + ":行" + lineNumber + ":字段 PrePriceShow");
            parsedField19 = ly.ConfigValues.read(type19, values.get("priceStepValue"), fileName + ":行" + lineNumber + ":字段 priceStepValue");
            parsedField20 = ly.ConfigValues.read(type20, values.get("timeType"), fileName + ":行" + lineNumber + ":字段 timeType");
            parsedField21 = ly.ConfigValues.read(type21, values.get("activityID"), fileName + ":行" + lineNumber + ":字段 activityID");
            parsedField22 = ly.ConfigValues.read(type22, values.get("startTime"), fileName + ":行" + lineNumber + ":字段 startTime");
            parsedField23 = ly.ConfigValues.read(type23, values.get("endTime"), fileName + ":行" + lineNumber + ":字段 endTime");
            parsedField24 = ly.ConfigValues.read(type24, values.get("specialEndTime"), fileName + ":行" + lineNumber + ":字段 specialEndTime");
            parsedField25 = ly.ConfigValues.read(type25, values.get("isShowTime"), fileName + ":行" + lineNumber + ":字段 isShowTime");
            parsedField26 = ly.ConfigValues.read(type26, values.get("sloganType"), fileName + ":行" + lineNumber + ":字段 sloganType");
            parsedField27 = ly.ConfigValues.read(type27, values.get("startTimeTips"), fileName + ":行" + lineNumber + ":字段 startTimeTips");
            parsedField28 = ly.ConfigValues.read(type28, values.get("startTimeWord"), fileName + ":行" + lineNumber + ":字段 startTimeWord");
            parsedField29 = ly.ConfigValues.read(type29, values.get("endTimeTips"), fileName + ":行" + lineNumber + ":字段 endTimeTips");
            parsedField30 = ly.ConfigValues.read(type30, values.get("groupId"), fileName + ":行" + lineNumber + ":字段 groupId");
            parsedField31 = ly.ConfigValues.read(type31, values.get("batch"), fileName + ":行" + lineNumber + ":字段 batch");
            parsedField32 = ly.ConfigValues.read(type32, values.get("rechargeShopId"), fileName + ":行" + lineNumber + ":字段 rechargeShopId");
            parsedField33 = ly.ConfigValues.read(type33, values.get("rechargeShopId1"), fileName + ":行" + lineNumber + ":字段 rechargeShopId1");
            parsedField34 = ly.ConfigValues.read(type34, values.get("tabshow"), fileName + ":行" + lineNumber + ":字段 tabshow");
            parsedField35 = ly.ConfigValues.read(type35, values.get("herocondition"), fileName + ":行" + lineNumber + ":字段 herocondition");
            parsedField36 = ly.ConfigValues.read(type36, values.get("rechargecondition"), fileName + ":行" + lineNumber + ":字段 rechargecondition");
            parsedField37 = ly.ConfigValues.read(type37, values.get("firstCharge"), fileName + ":行" + lineNumber + ":字段 firstCharge");
            parsedField38 = ly.ConfigValues.read(type38, values.get("followupCharge"), fileName + ":行" + lineNumber + ":字段 followupCharge");
            parsedField39 = ly.ConfigValues.read(type39, values.get("autoOpenGift"), fileName + ":行" + lineNumber + ":字段 autoOpenGift");
            parsedField40 = ly.ConfigValues.read(type40, values.get("extraShowItemId"), fileName + ":行" + lineNumber + ":字段 extraShowItemId");
            parsedField41 = ly.ConfigValues.read(type41, values.get("OpenServiceActivity"), fileName + ":行" + lineNumber + ":字段 OpenServiceActivity");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          CommodityInfo2Config config = new CommodityInfo2Config(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8, parsedField9, parsedField10, parsedField11, parsedField12, parsedField13, parsedField14, parsedField15, parsedField16, parsedField17, parsedField18, parsedField19, parsedField20, parsedField21, parsedField22, parsedField23, parsedField24, parsedField25, parsedField26, parsedField27, parsedField28, parsedField29, parsedField30, parsedField31, parsedField32, parsedField33, parsedField34, parsedField35, parsedField36, parsedField37, parsedField38, parsedField39, parsedField40, parsedField41);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (CommodityInfo2Config config : configList) config.afterLoad();
      new CommodityInfo2ConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<CommodityInfo2Config> getConfigList() {
      return configList;
    }

    public Map<Integer, CommodityInfo2Config> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "commodityInfo2.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
