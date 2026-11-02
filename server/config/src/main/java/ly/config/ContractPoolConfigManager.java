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
 * File: ContractPoolConfigManager
 */
public class ContractPoolConfigManager implements InterfaceConfigManagerProxy {
  public static ContractPoolConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(ContractPoolConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new ContractPoolConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "contractPool.xml"; }

  public static class ContractPoolConfigManagerImpl extends AbstractConfigManger {
    private List<ContractPoolConfig> configList = List.of();
    private Map<Integer, ContractPoolConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type5 = ly.ConfigValueType.parse("int");
      String fileName = configDir + File.separator + getConfigFileName();
      ContractPoolConfigChecker checker = new ContractPoolConfigChecker();
      List<ContractPoolConfig> newList = new ArrayList<>();
      Map<Integer, ContractPoolConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          String parsedField1 = null;
          int parsedField2 = 0;
          int parsedField3 = 0;
          String parsedField4 = null;
          int parsedField5 = 0;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("name"), fileName + ":行" + lineNumber + ":字段 name");
            parsedField2 = ly.ConfigValues.read(type2, values.get("oneDrawId"), fileName + ":行" + lineNumber + ":字段 oneDrawId");
            parsedField3 = ly.ConfigValues.read(type3, values.get("tenDrawId"), fileName + ":行" + lineNumber + ":字段 tenDrawId");
            parsedField4 = ly.ConfigValues.read(type4, values.get("chanceText"), fileName + ":行" + lineNumber + ":字段 chanceText");
            parsedField5 = ly.ConfigValues.read(type5, values.get("topId"), fileName + ":行" + lineNumber + ":字段 topId");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          ContractPoolConfig config = new ContractPoolConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (ContractPoolConfig config : configList) config.afterLoad();
      new ContractPoolConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<ContractPoolConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, ContractPoolConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "contractPool.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
