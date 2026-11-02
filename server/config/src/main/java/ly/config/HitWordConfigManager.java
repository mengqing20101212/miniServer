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
 * File: HitWordConfigManager
 */
public class HitWordConfigManager implements InterfaceConfigManagerProxy {
  public static HitWordConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(HitWordConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new HitWordConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "hitWord.xml"; }

  public static class HitWordConfigManagerImpl extends AbstractConfigManger {
    private List<HitWordConfig> configList = List.of();
    private Map<Integer, HitWordConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type5 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type7 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type8 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type9 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type10 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type11 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type12 = ly.ConfigValueType.parse("string");
      String fileName = configDir + File.separator + getConfigFileName();
      HitWordConfigChecker checker = new HitWordConfigChecker();
      List<HitWordConfig> newList = new ArrayList<>();
      Map<Integer, HitWordConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          String parsedField1 = null;
          String parsedField2 = null;
          String parsedField3 = null;
          String parsedField4 = null;
          String parsedField5 = null;
          String parsedField6 = null;
          String parsedField7 = null;
          String parsedField8 = null;
          String parsedField9 = null;
          String parsedField10 = null;
          String parsedField11 = null;
          String parsedField12 = null;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("name"), fileName + ":行" + lineNumber + ":字段 name");
            parsedField2 = ly.ConfigValues.read(type2, values.get("initialOffset"), fileName + ":行" + lineNumber + ":字段 initialOffset");
            parsedField3 = ly.ConfigValues.read(type3, values.get("initialScale"), fileName + ":行" + lineNumber + ":字段 initialScale");
            parsedField4 = ly.ConfigValues.read(type4, values.get("initialAlpha"), fileName + ":行" + lineNumber + ":字段 initialAlpha");
            parsedField5 = ly.ConfigValues.read(type5, values.get("appearScale"), fileName + ":行" + lineNumber + ":字段 appearScale");
            parsedField6 = ly.ConfigValues.read(type6, values.get("appearAlpha"), fileName + ":行" + lineNumber + ":字段 appearAlpha");
            parsedField7 = ly.ConfigValues.read(type7, values.get("toAppearTime"), fileName + ":行" + lineNumber + ":字段 toAppearTime");
            parsedField8 = ly.ConfigValues.read(type8, values.get("appearLastTime"), fileName + ":行" + lineNumber + ":字段 appearLastTime");
            parsedField9 = ly.ConfigValues.read(type9, values.get("disappearScale"), fileName + ":行" + lineNumber + ":字段 disappearScale");
            parsedField10 = ly.ConfigValues.read(type10, values.get("disappearAlpha"), fileName + ":行" + lineNumber + ":字段 disappearAlpha");
            parsedField11 = ly.ConfigValues.read(type11, values.get("disappearTime"), fileName + ":行" + lineNumber + ":字段 disappearTime");
            parsedField12 = ly.ConfigValues.read(type12, values.get("nextPosOffset"), fileName + ":行" + lineNumber + ":字段 nextPosOffset");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          HitWordConfig config = new HitWordConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8, parsedField9, parsedField10, parsedField11, parsedField12);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (HitWordConfig config : configList) config.afterLoad();
      new HitWordConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<HitWordConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, HitWordConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "hitWord.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
