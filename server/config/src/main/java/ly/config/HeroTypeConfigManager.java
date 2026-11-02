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
 * File: HeroTypeConfigManager
 */
public class HeroTypeConfigManager implements InterfaceConfigManagerProxy {
  public static HeroTypeConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(HeroTypeConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new HeroTypeConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "heroType.xml"; }

  public static class HeroTypeConfigManagerImpl extends AbstractConfigManger {
    private List<HeroTypeConfig> configList = List.of();
    private Map<Integer, HeroTypeConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("int");
      String fileName = configDir + File.separator + getConfigFileName();
      HeroTypeConfigChecker checker = new HeroTypeConfigChecker();
      List<HeroTypeConfig> newList = new ArrayList<>();
      Map<Integer, HeroTypeConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          String parsedField1 = null;
          int parsedField2 = 0;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("name"), fileName + ":行" + lineNumber + ":字段 name");
            parsedField2 = ly.ConfigValues.read(type2, values.get("icon"), fileName + ":行" + lineNumber + ":字段 icon");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          HeroTypeConfig config = new HeroTypeConfig(parsedField0, parsedField1, parsedField2);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (HeroTypeConfig config : configList) config.afterLoad();
      new HeroTypeConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<HeroTypeConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, HeroTypeConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "heroType.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
