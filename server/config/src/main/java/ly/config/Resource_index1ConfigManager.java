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
 * File: Resource_index1ConfigManager
 */
public class Resource_index1ConfigManager implements InterfaceConfigManagerProxy {
  public static Resource_index1ConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(Resource_index1ConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new Resource_index1ConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "resource_index1.xml"; }

  public static class Resource_index1ConfigManagerImpl extends AbstractConfigManger {
    private List<Resource_index1Config> configList = List.of();
    private Map<Integer, Resource_index1Config> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type5 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("string");
      String fileName = configDir + File.separator + getConfigFileName();
      Resource_index1ConfigChecker checker = new Resource_index1ConfigChecker();
      List<Resource_index1Config> newList = new ArrayList<>();
      Map<Integer, Resource_index1Config> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          String parsedField1 = null;
          String parsedField2 = null;
          int parsedField3 = 0;
          String parsedField4 = null;
          int parsedField5 = 0;
          String parsedField6 = null;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("resname"), fileName + ":行" + lineNumber + ":字段 resname");
            parsedField2 = ly.ConfigValues.read(type2, values.get("respath"), fileName + ":行" + lineNumber + ":字段 respath");
            parsedField3 = ly.ConfigValues.read(type3, values.get("language"), fileName + ":行" + lineNumber + ":字段 language");
            parsedField4 = ly.ConfigValues.read(type4, values.get("restype"), fileName + ":行" + lineNumber + ":字段 restype");
            parsedField5 = ly.ConfigValues.read(type5, values.get("resident"), fileName + ":行" + lineNumber + ":字段 resident");
            parsedField6 = ly.ConfigValues.read(type6, values.get("des"), fileName + ":行" + lineNumber + ":字段 des");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          Resource_index1Config config = new Resource_index1Config(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (Resource_index1Config config : configList) config.afterLoad();
      new Resource_index1ConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<Resource_index1Config> getConfigList() {
      return configList;
    }

    public Map<Integer, Resource_index1Config> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "resource_index1.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
