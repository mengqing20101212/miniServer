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
 * File: RedDotConfigManager
 */
public class RedDotConfigManager implements InterfaceConfigManagerProxy {
  public static RedDotConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(RedDotConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new RedDotConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "redDot.xml"; }

  public static class RedDotConfigManagerImpl extends AbstractConfigManger {
    private List<RedDotConfig> configList = List.of();
    private Map<Integer, RedDotConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type5 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("string");
      String fileName = configDir + File.separator + getConfigFileName();
      RedDotConfigChecker checker = new RedDotConfigChecker();
      List<RedDotConfig> newList = new ArrayList<>();
      Map<Integer, RedDotConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          int parsedField1 = 0;
          int parsedField2 = 0;
          int parsedField3 = 0;
          int parsedField4 = 0;
          String parsedField5 = null;
          String parsedField6 = null;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("SWITCH"), fileName + ":行" + lineNumber + ":字段 SWITCH");
            parsedField2 = ly.ConfigValues.read(type2, values.get("number"), fileName + ":行" + lineNumber + ":字段 number");
            parsedField3 = ly.ConfigValues.read(type3, values.get("type"), fileName + ":行" + lineNumber + ":字段 type");
            parsedField4 = ly.ConfigValues.read(type4, values.get("father"), fileName + ":行" + lineNumber + ":字段 father");
            parsedField5 = ly.ConfigValues.read(type5, values.get("subclass"), fileName + ":行" + lineNumber + ":字段 subclass");
            parsedField6 = ly.ConfigValues.read(type6, values.get("des"), fileName + ":行" + lineNumber + ":字段 des");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          RedDotConfig config = new RedDotConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (RedDotConfig config : configList) config.afterLoad();
      new RedDotConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<RedDotConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, RedDotConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "redDot.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
