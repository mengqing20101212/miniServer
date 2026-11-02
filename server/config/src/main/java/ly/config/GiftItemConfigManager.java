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
 * File: GiftItemConfigManager
 */
public class GiftItemConfigManager implements InterfaceConfigManagerProxy {
  public static GiftItemConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(GiftItemConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new GiftItemConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "giftItem.xml"; }

  public static class GiftItemConfigManagerImpl extends AbstractConfigManger {
    private List<GiftItemConfig> configList = List.of();
    private Map<Integer, GiftItemConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type5 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type7 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type8 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type9 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type10 = ly.ConfigValueType.parse("int");
      String fileName = configDir + File.separator + getConfigFileName();
      GiftItemConfigChecker checker = new GiftItemConfigChecker();
      List<GiftItemConfig> newList = new ArrayList<>();
      Map<Integer, GiftItemConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          String parsedField1 = null;
          int parsedField2 = 0;
          int parsedField3 = 0;
          int parsedField4 = 0;
          int parsedField5 = 0;
          int parsedField6 = 0;
          String parsedField7 = null;
          String parsedField8 = null;
          String parsedField9 = null;
          int parsedField10 = 0;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("description"), fileName + ":行" + lineNumber + ":字段 description");
            parsedField2 = ly.ConfigValues.read(type2, values.get("type"), fileName + ":行" + lineNumber + ":字段 type");
            parsedField3 = ly.ConfigValues.read(type3, values.get("school"), fileName + ":行" + lineNumber + ":字段 school");
            parsedField4 = ly.ConfigValues.read(type4, values.get("grade"), fileName + ":行" + lineNumber + ":字段 grade");
            parsedField5 = ly.ConfigValues.read(type5, values.get("sequence"), fileName + ":行" + lineNumber + ":字段 sequence");
            parsedField6 = ly.ConfigValues.read(type6, values.get("dropGroup"), fileName + ":行" + lineNumber + ":字段 dropGroup");
            parsedField7 = ly.ConfigValues.read(type7, values.get("selectDropGroup"), fileName + ":行" + lineNumber + ":字段 selectDropGroup");
            parsedField8 = ly.ConfigValues.read(type8, values.get("dropSelectShow"), fileName + ":行" + lineNumber + ":字段 dropSelectShow");
            parsedField9 = ly.ConfigValues.read(type9, values.get("giftTypeShow"), fileName + ":行" + lineNumber + ":字段 giftTypeShow");
            parsedField10 = ly.ConfigValues.read(type10, values.get("typeJudge"), fileName + ":行" + lineNumber + ":字段 typeJudge");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          GiftItemConfig config = new GiftItemConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8, parsedField9, parsedField10);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (GiftItemConfig config : configList) config.afterLoad();
      new GiftItemConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<GiftItemConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, GiftItemConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "giftItem.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
