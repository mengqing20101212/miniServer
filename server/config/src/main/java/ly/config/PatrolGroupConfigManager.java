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
 * File: PatrolGroupConfigManager
 */
public class PatrolGroupConfigManager implements InterfaceConfigManagerProxy {
  public static PatrolGroupConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(PatrolGroupConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new PatrolGroupConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "patrolGroup.xml"; }

  public static class PatrolGroupConfigManagerImpl extends AbstractConfigManger {
    private List<PatrolGroupConfig> configList = List.of();
    private Map<Integer, PatrolGroupConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("int");
      String fileName = configDir + File.separator + getConfigFileName();
      PatrolGroupConfigChecker checker = new PatrolGroupConfigChecker();
      List<PatrolGroupConfig> newList = new ArrayList<>();
      Map<Integer, PatrolGroupConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          int parsedField1 = 0;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("unlockLv"), fileName + ":行" + lineNumber + ":字段 unlockLv");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          PatrolGroupConfig config = new PatrolGroupConfig(parsedField0, parsedField1);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (PatrolGroupConfig config : configList) config.afterLoad();
      new PatrolGroupConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<PatrolGroupConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, PatrolGroupConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "patrolGroup.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
