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
 * File: SlgWorldObjectConfigManager
 */
public class SlgWorldObjectConfigManager implements InterfaceConfigManagerProxy {
  public static SlgWorldObjectConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(SlgWorldObjectConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new SlgWorldObjectConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "slgWorldObject.xml"; }

  public static class SlgWorldObjectConfigManagerImpl extends AbstractConfigManger {
    private List<SlgWorldObjectConfig> configList = List.of();
    private Map<Integer, SlgWorldObjectConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("long");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("long");
      ly.ConfigValueType type5 = ly.ConfigValueType.parse("long");
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("long");
      ly.ConfigValueType type7 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type8 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type9 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type10 = ly.ConfigValueType.parse("int");
      String fileName = configDir + File.separator + getConfigFileName();
      SlgWorldObjectConfigChecker checker = new SlgWorldObjectConfigChecker();
      List<SlgWorldObjectConfig> newList = new ArrayList<>();
      Map<Integer, SlgWorldObjectConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          String parsedField1 = null;
          int parsedField2 = 0;
          long parsedField3 = 0L;
          long parsedField4 = 0L;
          long parsedField5 = 0L;
          long parsedField6 = 0L;
          int parsedField7 = 0;
          int parsedField8 = 0;
          int parsedField9 = 0;
          int parsedField10 = 0;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("kind"), fileName + ":行" + lineNumber + ":字段 kind");
            parsedField2 = ly.ConfigValues.read(type2, values.get("resourceId"), fileName + ":行" + lineNumber + ":字段 resourceId");
            parsedField3 = ly.ConfigValues.read(type3, values.get("amount"), fileName + ":行" + lineNumber + ":字段 amount");
            parsedField4 = ly.ConfigValues.read(type4, values.get("health"), fileName + ":行" + lineNumber + ":字段 health");
            parsedField5 = ly.ConfigValues.read(type5, values.get("reward"), fileName + ":行" + lineNumber + ":字段 reward");
            parsedField6 = ly.ConfigValues.read(type6, values.get("gatherMillis"), fileName + ":行" + lineNumber + ":字段 gatherMillis");
            parsedField7 = ly.ConfigValues.read(type7, values.get("countPerRegion"), fileName + ":行" + lineNumber + ":字段 countPerRegion");
            parsedField8 = ly.ConfigValues.read(type8, values.get("checkIntervalSec"), fileName + ":行" + lineNumber + ":字段 checkIntervalSec");
            parsedField9 = ly.ConfigValues.read(type9, values.get("jitterSec"), fileName + ":行" + lineNumber + ":字段 jitterSec");
            parsedField10 = ly.ConfigValues.read(type10, values.get("batchSize"), fileName + ":行" + lineNumber + ":字段 batchSize");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          SlgWorldObjectConfig config = new SlgWorldObjectConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8, parsedField9, parsedField10);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (SlgWorldObjectConfig config : configList) config.afterLoad();
      new SlgWorldObjectConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<SlgWorldObjectConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, SlgWorldObjectConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "slgWorldObject.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {
    }
    // @@@@@自定义方法结束区@@@@@
  }
}
