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
 * File: HeroExpConfigManager
 */
public class HeroExpConfigManager implements InterfaceConfigManagerProxy {
  public static HeroExpConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(HeroExpConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new HeroExpConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "heroExp.xml"; }

  public static class HeroExpConfigManagerImpl extends AbstractConfigManger {
    private List<HeroExpConfig> configList = List.of();
    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type5 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("string");
      String fileName = configDir + File.separator + getConfigFileName();
      HeroExpConfigChecker checker = new HeroExpConfigChecker();
      List<HeroExpConfig> newList = new ArrayList<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          int parsedField1 = 0;
          int parsedField2 = 0;
          int parsedField3 = 0;
          int parsedField4 = 0;
          String parsedField5 = null;
          String parsedField6 = null;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("level"), fileName + ":行" + lineNumber + ":字段 level");
            parsedField1 = ly.ConfigValues.read(type1, values.get("modelId"), fileName + ":行" + lineNumber + ":字段 modelId");
            parsedField2 = ly.ConfigValues.read(type2, values.get("exp"), fileName + ":行" + lineNumber + ":字段 exp");
            parsedField3 = ly.ConfigValues.read(type3, values.get("retainExp"), fileName + ":行" + lineNumber + ":字段 retainExp");
            parsedField4 = ly.ConfigValues.read(type4, values.get("currencyType"), fileName + ":行" + lineNumber + ":字段 currencyType");
            parsedField5 = ly.ConfigValues.read(type5, values.get("currencyNum"), fileName + ":行" + lineNumber + ":字段 currencyNum");
            parsedField6 = ly.ConfigValues.read(type6, values.get("item"), fileName + ":行" + lineNumber + ":字段 item");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          HeroExpConfig config = new HeroExpConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6);
          newList.add(config);
      });
      configList = List.copyOf(newList);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (HeroExpConfig config : configList) config.afterLoad();
      new HeroExpConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<HeroExpConfig> getConfigList() {
      return configList;
    }

    @Override
    public String getConfigFileName() {
      return "heroExp.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {}

    /**
     * 根据模板ID和等级获取配置
     */
    public HeroExpConfig getByModelIdAndLevel(int modelId, int level) {
      for (HeroExpConfig config : configList) {
        if (config.modelId == modelId && config.level == level) {
          return config;
        }
      }
      return null;
    }
    // @@@@@自定义方法结束区@@@@@
  }
}
