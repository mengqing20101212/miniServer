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
 * File: AdvancedTypeConfigManager
 */
public class AdvancedTypeConfigManager implements InterfaceConfigManagerProxy {
  public static AdvancedTypeConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(AdvancedTypeConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new AdvancedTypeConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "advancedType.xml"; }

  public static class AdvancedTypeConfigManagerImpl extends AbstractConfigManger {
    private List<AdvancedTypeConfig> configList = List.of();
    private Map<Integer, AdvancedTypeConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("list<int>");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("list<int>");
      ly.ConfigValueType type5 = ly.ConfigValueType.parse("list<int>");
      String fileName = configDir + File.separator + getConfigFileName();
      AdvancedTypeConfigChecker checker = new AdvancedTypeConfigChecker();
      List<AdvancedTypeConfig> newList = new ArrayList<>();
      Map<Integer, AdvancedTypeConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          java.util.List<Integer> parsedField1 = null;
          int parsedField2 = 0;
          String parsedField3 = null;
          java.util.List<Integer> parsedField4 = null;
          java.util.List<Integer> parsedField5 = null;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("stageTypeList"), fileName + ":行" + lineNumber + ":字段 stageTypeList");
            parsedField2 = ly.ConfigValues.read(type2, values.get("activityId"), fileName + ":行" + lineNumber + ":字段 activityId");
            parsedField3 = ly.ConfigValues.read(type3, values.get("nameList"), fileName + ":行" + lineNumber + ":字段 nameList");
            parsedField4 = ly.ConfigValues.read(type4, values.get("iconList"), fileName + ":行" + lineNumber + ":字段 iconList");
            parsedField5 = ly.ConfigValues.read(type5, values.get("outlineList"), fileName + ":行" + lineNumber + ":字段 outlineList");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          AdvancedTypeConfig config = new AdvancedTypeConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (AdvancedTypeConfig config : configList) config.afterLoad();
      new AdvancedTypeConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<AdvancedTypeConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, AdvancedTypeConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "advancedType.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
