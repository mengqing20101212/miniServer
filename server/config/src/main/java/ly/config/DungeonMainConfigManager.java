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
 * File: DungeonMainConfigManager
 */
public class DungeonMainConfigManager implements InterfaceConfigManagerProxy {
  public static DungeonMainConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(DungeonMainConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new DungeonMainConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "dungeonMain.xml"; }

  public static class DungeonMainConfigManagerImpl extends AbstractConfigManger {
    private List<DungeonMainConfig> configList = List.of();
    private Map<Integer, DungeonMainConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type5 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type7 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type8 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type9 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type10 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type11 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type12 = ly.ConfigValueType.parse("string");
      String fileName = configDir + File.separator + getConfigFileName();
      DungeonMainConfigChecker checker = new DungeonMainConfigChecker();
      List<DungeonMainConfig> newList = new ArrayList<>();
      Map<Integer, DungeonMainConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          String parsedField1 = null;
          String parsedField2 = null;
          String parsedField3 = null;
          int parsedField4 = 0;
          String parsedField5 = null;
          String parsedField6 = null;
          int parsedField7 = 0;
          int parsedField8 = 0;
          String parsedField9 = null;
          int parsedField10 = 0;
          String parsedField11 = null;
          String parsedField12 = null;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("beizhu"), fileName + ":行" + lineNumber + ":字段 beizhu");
            parsedField2 = ly.ConfigValues.read(type2, values.get("name"), fileName + ":行" + lineNumber + ":字段 name");
            parsedField3 = ly.ConfigValues.read(type3, values.get("timeShow"), fileName + ":行" + lineNumber + ":字段 timeShow");
            parsedField4 = ly.ConfigValues.read(type4, values.get("titleWordPic"), fileName + ":行" + lineNumber + ":字段 titleWordPic");
            parsedField5 = ly.ConfigValues.read(type5, values.get("title"), fileName + ":行" + lineNumber + ":字段 title");
            parsedField6 = ly.ConfigValues.read(type6, values.get("rewards"), fileName + ":行" + lineNumber + ":字段 rewards");
            parsedField7 = ly.ConfigValues.read(type7, values.get("background"), fileName + ":行" + lineNumber + ":字段 background");
            parsedField8 = ly.ConfigValues.read(type8, values.get("bossModel"), fileName + ":行" + lineNumber + ":字段 bossModel");
            parsedField9 = ly.ConfigValues.read(type9, values.get("bossAction"), fileName + ":行" + lineNumber + ":字段 bossAction");
            parsedField10 = ly.ConfigValues.read(type10, values.get("scale"), fileName + ":行" + lineNumber + ":字段 scale");
            parsedField11 = ly.ConfigValues.read(type11, values.get("rotation"), fileName + ":行" + lineNumber + ":字段 rotation");
            parsedField12 = ly.ConfigValues.read(type12, values.get("modelShift"), fileName + ":行" + lineNumber + ":字段 modelShift");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          DungeonMainConfig config = new DungeonMainConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8, parsedField9, parsedField10, parsedField11, parsedField12);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (DungeonMainConfig config : configList) config.afterLoad();
      new DungeonMainConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<DungeonMainConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, DungeonMainConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "dungeonMain.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
