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
 * File: HeroAwakenConfigManager
 */
public class HeroAwakenConfigManager implements InterfaceConfigManagerProxy {
  public static HeroAwakenConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(HeroAwakenConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new HeroAwakenConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "heroAwaken.xml"; }

  public static class HeroAwakenConfigManagerImpl extends AbstractConfigManger {
    private List<HeroAwakenConfig> configList = List.of();
    private Map<Integer, HeroAwakenConfig> configMap = Map.of();

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
      ly.ConfigValueType type14 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type15 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type16 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type17 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type18 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type19 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type20 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type21 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type22 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type23 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type24 = ly.ConfigValueType.parse("int");
      String fileName = configDir + File.separator + getConfigFileName();
      HeroAwakenConfigChecker checker = new HeroAwakenConfigChecker();
      List<HeroAwakenConfig> newList = new ArrayList<>();
      Map<Integer, HeroAwakenConfig> newMap = new HashMap<>();
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
          String parsedField14 = null;
          int parsedField15 = 0;
          String parsedField16 = null;
          String parsedField17 = null;
          String parsedField18 = null;
          int parsedField19 = 0;
          int parsedField20 = 0;
          int parsedField21 = 0;
          String parsedField22 = null;
          int parsedField23 = 0;
          int parsedField24 = 0;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("modelName"), fileName + ":行" + lineNumber + ":字段 modelName");
            parsedField2 = ly.ConfigValues.read(type2, values.get("sequence"), fileName + ":行" + lineNumber + ":字段 sequence");
            parsedField3 = ly.ConfigValues.read(type3, values.get("awakenType"), fileName + ":行" + lineNumber + ":字段 awakenType");
            parsedField4 = ly.ConfigValues.read(type4, values.get("awakenskill_1"), fileName + ":行" + lineNumber + ":字段 awakenskill_1");
            parsedField5 = ly.ConfigValues.read(type5, values.get("awakenskill_2"), fileName + ":行" + lineNumber + ":字段 awakenskill_2");
            parsedField6 = ly.ConfigValues.read(type6, values.get("awakenskill_3"), fileName + ":行" + lineNumber + ":字段 awakenskill_3");
            parsedField7 = ly.ConfigValues.read(type7, values.get("awakenskill_s1"), fileName + ":行" + lineNumber + ":字段 awakenskill_s1");
            parsedField8 = ly.ConfigValues.read(type8, values.get("awakenskill_s2"), fileName + ":行" + lineNumber + ":字段 awakenskill_s2");
            parsedField9 = ly.ConfigValues.read(type9, values.get("moreAwakenSkill1"), fileName + ":行" + lineNumber + ":字段 moreAwakenSkill1");
            parsedField10 = ly.ConfigValues.read(type10, values.get("moreAwakenSkill2"), fileName + ":行" + lineNumber + ":字段 moreAwakenSkill2");
            parsedField11 = ly.ConfigValues.read(type11, values.get("moreAwakenSkill3"), fileName + ":行" + lineNumber + ":字段 moreAwakenSkill3");
            parsedField12 = ly.ConfigValues.read(type12, values.get("attrType"), fileName + ":行" + lineNumber + ":字段 attrType");
            parsedField13 = ly.ConfigValues.read(type13, values.get("attrNum"), fileName + ":行" + lineNumber + ":字段 attrNum");
            parsedField14 = ly.ConfigValues.read(type14, values.get("awakenPhase"), fileName + ":行" + lineNumber + ":字段 awakenPhase");
            parsedField15 = ly.ConfigValues.read(type15, values.get("awakenIcon"), fileName + ":行" + lineNumber + ":字段 awakenIcon");
            parsedField16 = ly.ConfigValues.read(type16, values.get("awakenTitle"), fileName + ":行" + lineNumber + ":字段 awakenTitle");
            parsedField17 = ly.ConfigValues.read(type17, values.get("awakenAttrDes"), fileName + ":行" + lineNumber + ":字段 awakenAttrDes");
            parsedField18 = ly.ConfigValues.read(type18, values.get("awakenItem"), fileName + ":行" + lineNumber + ":字段 awakenItem");
            parsedField19 = ly.ConfigValues.read(type19, values.get("awakenCurrencyType"), fileName + ":行" + lineNumber + ":字段 awakenCurrencyType");
            parsedField20 = ly.ConfigValues.read(type20, values.get("awakenCurrencyNum"), fileName + ":行" + lineNumber + ":字段 awakenCurrencyNum");
            parsedField21 = ly.ConfigValues.read(type21, values.get("isReset"), fileName + ":行" + lineNumber + ":字段 isReset");
            parsedField22 = ly.ConfigValues.read(type22, values.get("retainItem"), fileName + ":行" + lineNumber + ":字段 retainItem");
            parsedField23 = ly.ConfigValues.read(type23, values.get("currencyType"), fileName + ":行" + lineNumber + ":字段 currencyType");
            parsedField24 = ly.ConfigValues.read(type24, values.get("currencyNum"), fileName + ":行" + lineNumber + ":字段 currencyNum");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          HeroAwakenConfig config = new HeroAwakenConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8, parsedField9, parsedField10, parsedField11, parsedField12, parsedField13, parsedField14, parsedField15, parsedField16, parsedField17, parsedField18, parsedField19, parsedField20, parsedField21, parsedField22, parsedField23, parsedField24);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (HeroAwakenConfig config : configList) config.afterLoad();
      new HeroAwakenConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<HeroAwakenConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, HeroAwakenConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "heroAwaken.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {}

    /**
     * 根据英雄觉醒模板ID和觉醒等级获取配置
     */
    public HeroAwakenConfig getByHeroAwakenDataAndLevel(int heroAwakenData, int awakenLevel) {
      for (HeroAwakenConfig config : configList) {
        if (config.modelName == heroAwakenData && config.sequence == awakenLevel) {
          return config;
        }
      }
      return null;
    }
    // @@@@@自定义方法结束区@@@@@
  }
}
