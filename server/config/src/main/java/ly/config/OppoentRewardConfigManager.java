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
 * File: OppoentRewardConfigManager
 */
public class OppoentRewardConfigManager implements InterfaceConfigManagerProxy {
  public static OppoentRewardConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(OppoentRewardConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new OppoentRewardConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "oppoentReward.xml"; }

  public static class OppoentRewardConfigManagerImpl extends AbstractConfigManger {
    private List<OppoentRewardConfig> configList = List.of();
    private Map<Integer, OppoentRewardConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("int");
      String fileName = configDir + File.separator + getConfigFileName();
      OppoentRewardConfigChecker checker = new OppoentRewardConfigChecker();
      List<OppoentRewardConfig> newList = new ArrayList<>();
      Map<Integer, OppoentRewardConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          String parsedField1 = null;
          int parsedField2 = 0;
          int parsedField3 = 0;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("rank"), fileName + ":行" + lineNumber + ":字段 rank");
            parsedField2 = ly.ConfigValues.read(type2, values.get("reward"), fileName + ":行" + lineNumber + ":字段 reward");
            parsedField3 = ly.ConfigValues.read(type3, values.get("rewardShow"), fileName + ":行" + lineNumber + ":字段 rewardShow");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          OppoentRewardConfig config = new OppoentRewardConfig(parsedField0, parsedField1, parsedField2, parsedField3);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (OppoentRewardConfig config : configList) config.afterLoad();
      new OppoentRewardConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<OppoentRewardConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, OppoentRewardConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "oppoentReward.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
