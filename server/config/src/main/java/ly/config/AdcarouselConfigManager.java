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
 * File: AdcarouselConfigManager
 */
public class AdcarouselConfigManager implements InterfaceConfigManagerProxy {
  public static AdcarouselConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(AdcarouselConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new AdcarouselConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "adcarousel.xml"; }

  public static class AdcarouselConfigManagerImpl extends AbstractConfigManger {
    private List<AdcarouselConfig> configList = List.of();
    private Map<Integer, AdcarouselConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type5 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type7 = ly.ConfigValueType.parse("list<int>");
      ly.ConfigValueType type8 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type9 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type10 = ly.ConfigValueType.parse("list<int>");
      ly.ConfigValueType type11 = ly.ConfigValueType.parse("int");
      String fileName = configDir + File.separator + getConfigFileName();
      AdcarouselConfigChecker checker = new AdcarouselConfigChecker();
      List<AdcarouselConfig> newList = new ArrayList<>();
      Map<Integer, AdcarouselConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          String parsedField1 = null;
          int parsedField2 = 0;
          int parsedField3 = 0;
          int parsedField4 = 0;
          String parsedField5 = null;
          String parsedField6 = null;
          java.util.List<Integer> parsedField7 = null;
          int parsedField8 = 0;
          int parsedField9 = 0;
          java.util.List<Integer> parsedField10 = null;
          int parsedField11 = 0;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("beizhu"), fileName + ":行" + lineNumber + ":字段 beizhu");
            parsedField2 = ly.ConfigValues.read(type2, values.get("adcarousel"), fileName + ":行" + lineNumber + ":字段 adcarousel");
            parsedField3 = ly.ConfigValues.read(type3, values.get("adcarousel2"), fileName + ":行" + lineNumber + ":字段 adcarousel2");
            parsedField4 = ly.ConfigValues.read(type4, values.get("timeType"), fileName + ":行" + lineNumber + ":字段 timeType");
            parsedField5 = ly.ConfigValues.read(type5, values.get("startTime"), fileName + ":行" + lineNumber + ":字段 startTime");
            parsedField6 = ly.ConfigValues.read(type6, values.get("endTime"), fileName + ":行" + lineNumber + ":字段 endTime");
            parsedField7 = ly.ConfigValues.read(type7, values.get("specialEndTime"), fileName + ":行" + lineNumber + ":字段 specialEndTime");
            parsedField8 = ly.ConfigValues.read(type8, values.get("jump1"), fileName + ":行" + lineNumber + ":字段 jump1");
            parsedField9 = ly.ConfigValues.read(type9, values.get("order"), fileName + ":行" + lineNumber + ":字段 order");
            parsedField10 = ly.ConfigValues.read(type10, values.get("level_limit"), fileName + ":行" + lineNumber + ":字段 level_limit");
            parsedField11 = ly.ConfigValues.read(type11, values.get("OpenServiceActivity"), fileName + ":行" + lineNumber + ":字段 OpenServiceActivity");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          AdcarouselConfig config = new AdcarouselConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8, parsedField9, parsedField10, parsedField11);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (AdcarouselConfig config : configList) config.afterLoad();
      new AdcarouselConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<AdcarouselConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, AdcarouselConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "adcarousel.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
