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
 * File: DreamEventNewConfigManager
 */
public class DreamEventNewConfigManager implements InterfaceConfigManagerProxy {
  public static DreamEventNewConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(DreamEventNewConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new DreamEventNewConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "dreamEventNew.xml"; }

  public static class DreamEventNewConfigManagerImpl extends AbstractConfigManger {
    private List<DreamEventNewConfig> configList = List.of();
    private Map<Integer, DreamEventNewConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("list<int>");
      String fileName = configDir + File.separator + getConfigFileName();
      DreamEventNewConfigChecker checker = new DreamEventNewConfigChecker();
      List<DreamEventNewConfig> newList = new ArrayList<>();
      Map<Integer, DreamEventNewConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          int parsedField1 = 0;
          String parsedField2 = null;
          int parsedField3 = 0;
          java.util.List<Integer> parsedField4 = null;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("resourceId"), fileName + ":行" + lineNumber + ":字段 resourceId");
            parsedField2 = ly.ConfigValues.read(type2, values.get("mechanism"), fileName + ":行" + lineNumber + ":字段 mechanism");
            parsedField3 = ly.ConfigValues.read(type3, values.get("heroCount"), fileName + ":行" + lineNumber + ":字段 heroCount");
            parsedField4 = ly.ConfigValues.read(type4, values.get("limit"), fileName + ":行" + lineNumber + ":字段 limit");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          DreamEventNewConfig config = new DreamEventNewConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (DreamEventNewConfig config : configList) config.afterLoad();
      new DreamEventNewConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<DreamEventNewConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, DreamEventNewConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "dreamEventNew.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
