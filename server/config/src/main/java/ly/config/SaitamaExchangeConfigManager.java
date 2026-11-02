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
 * File: SaitamaExchangeConfigManager
 */
public class SaitamaExchangeConfigManager implements InterfaceConfigManagerProxy {
  public static SaitamaExchangeConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(SaitamaExchangeConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new SaitamaExchangeConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "saitamaExchange.xml"; }

  public static class SaitamaExchangeConfigManagerImpl extends AbstractConfigManger {
    private List<SaitamaExchangeConfig> configList = List.of();
    private Map<Integer, SaitamaExchangeConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("string");
      String fileName = configDir + File.separator + getConfigFileName();
      SaitamaExchangeConfigChecker checker = new SaitamaExchangeConfigChecker();
      List<SaitamaExchangeConfig> newList = new ArrayList<>();
      Map<Integer, SaitamaExchangeConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          int parsedField1 = 0;
          String parsedField2 = null;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("ratio"), fileName + ":行" + lineNumber + ":字段 ratio");
            parsedField2 = ly.ConfigValues.read(type2, values.get("changeList"), fileName + ":行" + lineNumber + ":字段 changeList");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          SaitamaExchangeConfig config = new SaitamaExchangeConfig(parsedField0, parsedField1, parsedField2);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (SaitamaExchangeConfig config : configList) config.afterLoad();
      new SaitamaExchangeConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<SaitamaExchangeConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, SaitamaExchangeConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "saitamaExchange.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
