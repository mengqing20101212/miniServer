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
 * File: OppoentPushConfigManager
 */
public class OppoentPushConfigManager implements InterfaceConfigManagerProxy {
  public static OppoentPushConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(OppoentPushConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new OppoentPushConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "oppoentPush.xml"; }

  public static class OppoentPushConfigManagerImpl extends AbstractConfigManger {
    private List<OppoentPushConfig> configList = List.of();
    private Map<Integer, OppoentPushConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type5 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type7 = ly.ConfigValueType.parse("int");
      String fileName = configDir + File.separator + getConfigFileName();
      OppoentPushConfigChecker checker = new OppoentPushConfigChecker();
      List<OppoentPushConfig> newList = new ArrayList<>();
      Map<Integer, OppoentPushConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          int parsedField1 = 0;
          int parsedField2 = 0;
          int parsedField3 = 0;
          int parsedField4 = 0;
          int parsedField5 = 0;
          int parsedField6 = 0;
          int parsedField7 = 0;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("rounds"), fileName + ":行" + lineNumber + ":字段 rounds");
            parsedField2 = ly.ConfigValues.read(type2, values.get("levelMy"), fileName + ":行" + lineNumber + ":字段 levelMy");
            parsedField3 = ly.ConfigValues.read(type3, values.get("levelOpponent"), fileName + ":行" + lineNumber + ":字段 levelOpponent");
            parsedField4 = ly.ConfigValues.read(type4, values.get("difficultNum"), fileName + ":行" + lineNumber + ":字段 difficultNum");
            parsedField5 = ly.ConfigValues.read(type5, values.get("normalNum"), fileName + ":行" + lineNumber + ":字段 normalNum");
            parsedField6 = ly.ConfigValues.read(type6, values.get("easyNum"), fileName + ":行" + lineNumber + ":字段 easyNum");
            parsedField7 = ly.ConfigValues.read(type7, values.get("robotNum"), fileName + ":行" + lineNumber + ":字段 robotNum");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          OppoentPushConfig config = new OppoentPushConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (OppoentPushConfig config : configList) config.afterLoad();
      new OppoentPushConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<OppoentPushConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, OppoentPushConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "oppoentPush.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
