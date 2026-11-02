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
 * File: MinigameRewardSquareConfigManager
 */
public class MinigameRewardSquareConfigManager implements InterfaceConfigManagerProxy {
  public static MinigameRewardSquareConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(MinigameRewardSquareConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new MinigameRewardSquareConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "minigameRewardSquare.xml"; }

  public static class MinigameRewardSquareConfigManagerImpl extends AbstractConfigManger {
    private List<MinigameRewardSquareConfig> configList = List.of();
    private Map<Integer, MinigameRewardSquareConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("int");
      String fileName = configDir + File.separator + getConfigFileName();
      MinigameRewardSquareConfigChecker checker = new MinigameRewardSquareConfigChecker();
      List<MinigameRewardSquareConfig> newList = new ArrayList<>();
      Map<Integer, MinigameRewardSquareConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          String parsedField1 = null;
          String parsedField2 = null;
          String parsedField3 = null;
          int parsedField4 = 0;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("rankShow"), fileName + ":行" + lineNumber + ":字段 rankShow");
            parsedField2 = ly.ConfigValues.read(type2, values.get("hpScore"), fileName + ":行" + lineNumber + ":字段 hpScore");
            parsedField3 = ly.ConfigValues.read(type3, values.get("timeScore"), fileName + ":行" + lineNumber + ":字段 timeScore");
            parsedField4 = ly.ConfigValues.read(type4, values.get("dropId"), fileName + ":行" + lineNumber + ":字段 dropId");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          MinigameRewardSquareConfig config = new MinigameRewardSquareConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (MinigameRewardSquareConfig config : configList) config.afterLoad();
      new MinigameRewardSquareConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<MinigameRewardSquareConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, MinigameRewardSquareConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "minigameRewardSquare.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
