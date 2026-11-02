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
 * File: RobotPoolConfigManager
 */
public class RobotPoolConfigManager implements InterfaceConfigManagerProxy {
  public static RobotPoolConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(RobotPoolConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new RobotPoolConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "robotPool.xml"; }

  public static class RobotPoolConfigManagerImpl extends AbstractConfigManger {
    private List<RobotPoolConfig> configList = List.of();
    private Map<Integer, RobotPoolConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("int");
      String fileName = configDir + File.separator + getConfigFileName();
      RobotPoolConfigChecker checker = new RobotPoolConfigChecker();
      List<RobotPoolConfig> newList = new ArrayList<>();
      Map<Integer, RobotPoolConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          String parsedField1 = null;
          String parsedField2 = null;
          int parsedField3 = 0;
          int parsedField4 = 0;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("robotId"), fileName + ":行" + lineNumber + ":字段 robotId");
            parsedField2 = ly.ConfigValues.read(type2, values.get("weight"), fileName + ":行" + lineNumber + ":字段 weight");
            parsedField3 = ly.ConfigValues.read(type3, values.get("levelmin"), fileName + ":行" + lineNumber + ":字段 levelmin");
            parsedField4 = ly.ConfigValues.read(type4, values.get("levelmax"), fileName + ":行" + lineNumber + ":字段 levelmax");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          RobotPoolConfig config = new RobotPoolConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (RobotPoolConfig config : configList) config.afterLoad();
      new RobotPoolConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<RobotPoolConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, RobotPoolConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "robotPool.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
