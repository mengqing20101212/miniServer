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
 * File: HeroStarConfigManager
 */
public class HeroStarConfigManager implements InterfaceConfigManagerProxy {
  public static HeroStarConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(HeroStarConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new HeroStarConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "heroStar.xml"; }

  public static class HeroStarConfigManagerImpl extends AbstractConfigManger {
    private List<HeroStarConfig> configList = List.of();
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
      ly.ConfigValueType type7 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type8 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type9 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type10 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type11 = ly.ConfigValueType.parse("int");
      String fileName = configDir + File.separator + getConfigFileName();
      HeroStarConfigChecker checker = new HeroStarConfigChecker();
      List<HeroStarConfig> newList = new ArrayList<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          int parsedField1 = 0;
          int parsedField2 = 0;
          int parsedField3 = 0;
          String parsedField4 = null;
          String parsedField5 = null;
          String parsedField6 = null;
          int parsedField7 = 0;
          String parsedField8 = null;
          String parsedField9 = null;
          String parsedField10 = null;
          int parsedField11 = 0;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("star"), fileName + ":行" + lineNumber + ":字段 star");
            parsedField1 = ly.ConfigValues.read(type1, values.get("modelId"), fileName + ":行" + lineNumber + ":字段 modelId");
            parsedField2 = ly.ConfigValues.read(type2, values.get("maxLevel"), fileName + ":行" + lineNumber + ":字段 maxLevel");
            parsedField3 = ly.ConfigValues.read(type3, values.get("awakenLevel"), fileName + ":行" + lineNumber + ":字段 awakenLevel");
            parsedField4 = ly.ConfigValues.read(type4, values.get("starItem"), fileName + ":行" + lineNumber + ":字段 starItem");
            parsedField5 = ly.ConfigValues.read(type5, values.get("starItem2"), fileName + ":行" + lineNumber + ":字段 starItem2");
            parsedField6 = ly.ConfigValues.read(type6, values.get("retainItem"), fileName + ":行" + lineNumber + ":字段 retainItem");
            parsedField7 = ly.ConfigValues.read(type7, values.get("currencyType"), fileName + ":行" + lineNumber + ":字段 currencyType");
            parsedField8 = ly.ConfigValues.read(type8, values.get("currencyNum"), fileName + ":行" + lineNumber + ":字段 currencyNum");
            parsedField9 = ly.ConfigValues.read(type9, values.get("item"), fileName + ":行" + lineNumber + ":字段 item");
            parsedField10 = ly.ConfigValues.read(type10, values.get("circuitSlot"), fileName + ":行" + lineNumber + ":字段 circuitSlot");
            parsedField11 = ly.ConfigValues.read(type11, values.get("followAwaken"), fileName + ":行" + lineNumber + ":字段 followAwaken");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          HeroStarConfig config = new HeroStarConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8, parsedField9, parsedField10, parsedField11);
          newList.add(config);
      });
      configList = List.copyOf(newList);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (HeroStarConfig config : configList) config.afterLoad();
      new HeroStarConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<HeroStarConfig> getConfigList() {
      return configList;
    }

    @Override
    public String getConfigFileName() {
      return "heroStar.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {}

    /**
     * 根据模板ID和星级获取配置
     */
    public HeroStarConfig getByModelIdAndStar(int modelId, int star) {
      for (HeroStarConfig config : configList) {
        if (config.modelId == modelId && config.star == star) {
          return config;
        }
      }
      return null;
    }
    // @@@@@自定义方法结束区@@@@@
  }
}
