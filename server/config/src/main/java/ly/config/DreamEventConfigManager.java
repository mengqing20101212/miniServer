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
 * File: DreamEventConfigManager
 */
public class DreamEventConfigManager implements InterfaceConfigManagerProxy {
  public static DreamEventConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(DreamEventConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new DreamEventConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "dreamEvent.xml"; }

  public static class DreamEventConfigManagerImpl extends AbstractConfigManger {
    private List<DreamEventConfig> configList = List.of();
    private Map<Integer, DreamEventConfig> configMap = Map.of();

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
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type7 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type8 = ly.ConfigValueType.parse("string");
      String fileName = configDir + File.separator + getConfigFileName();
      DreamEventConfigChecker checker = new DreamEventConfigChecker();
      List<DreamEventConfig> newList = new ArrayList<>();
      Map<Integer, DreamEventConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          String parsedField1 = null;
          int parsedField2 = 0;
          int parsedField3 = 0;
          int parsedField4 = 0;
          String parsedField5 = null;
          int parsedField6 = 0;
          String parsedField7 = null;
          String parsedField8 = null;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("beizhu"), fileName + ":行" + lineNumber + ":字段 beizhu");
            parsedField2 = ly.ConfigValues.read(type2, values.get("type"), fileName + ":行" + lineNumber + ":字段 type");
            parsedField3 = ly.ConfigValues.read(type3, values.get("resourceId"), fileName + ":行" + lineNumber + ":字段 resourceId");
            parsedField4 = ly.ConfigValues.read(type4, values.get("headId"), fileName + ":行" + lineNumber + ":字段 headId");
            parsedField5 = ly.ConfigValues.read(type5, values.get("shift"), fileName + ":行" + lineNumber + ":字段 shift");
            parsedField6 = ly.ConfigValues.read(type6, values.get("effectResource"), fileName + ":行" + lineNumber + ":字段 effectResource");
            parsedField7 = ly.ConfigValues.read(type7, values.get("missionTitle"), fileName + ":行" + lineNumber + ":字段 missionTitle");
            parsedField8 = ly.ConfigValues.read(type8, values.get("para"), fileName + ":行" + lineNumber + ":字段 para");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          DreamEventConfig config = new DreamEventConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (DreamEventConfig config : configList) config.afterLoad();
      new DreamEventConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<DreamEventConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, DreamEventConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "dreamEvent.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
