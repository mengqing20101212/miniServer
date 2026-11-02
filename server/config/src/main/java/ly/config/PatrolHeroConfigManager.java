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
 * File: PatrolHeroConfigManager
 */
public class PatrolHeroConfigManager implements InterfaceConfigManagerProxy {
  public static PatrolHeroConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(PatrolHeroConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new PatrolHeroConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "patrolHero.xml"; }

  public static class PatrolHeroConfigManagerImpl extends AbstractConfigManger {
    private List<PatrolHeroConfig> configList = List.of();
    private Map<Integer, PatrolHeroConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type5 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type7 = ly.ConfigValueType.parse("int");
      String fileName = configDir + File.separator + getConfigFileName();
      PatrolHeroConfigChecker checker = new PatrolHeroConfigChecker();
      List<PatrolHeroConfig> newList = new ArrayList<>();
      Map<Integer, PatrolHeroConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          String parsedField1 = null;
          String parsedField2 = null;
          String parsedField3 = null;
          String parsedField4 = null;
          int parsedField5 = 0;
          int parsedField6 = 0;
          int parsedField7 = 0;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("name"), fileName + ":行" + lineNumber + ":字段 name");
            parsedField2 = ly.ConfigValues.read(type2, values.get("workSentiment"), fileName + ":行" + lineNumber + ":字段 workSentiment");
            parsedField3 = ly.ConfigValues.read(type3, values.get("workDay"), fileName + ":行" + lineNumber + ":字段 workDay");
            parsedField4 = ly.ConfigValues.read(type4, values.get("workDayDesc"), fileName + ":行" + lineNumber + ":字段 workDayDesc");
            parsedField5 = ly.ConfigValues.read(type5, values.get("heroFetter"), fileName + ":行" + lineNumber + ":字段 heroFetter");
            parsedField6 = ly.ConfigValues.read(type6, values.get("baseAdd"), fileName + ":行" + lineNumber + ":字段 baseAdd");
            parsedField7 = ly.ConfigValues.read(type7, values.get("extraAdd"), fileName + ":行" + lineNumber + ":字段 extraAdd");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          PatrolHeroConfig config = new PatrolHeroConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (PatrolHeroConfig config : configList) config.afterLoad();
      new PatrolHeroConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<PatrolHeroConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, PatrolHeroConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "patrolHero.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
