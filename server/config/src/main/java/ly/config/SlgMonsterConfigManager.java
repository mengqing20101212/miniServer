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
 * File: SlgMonsterConfigManager
 */
public class SlgMonsterConfigManager implements InterfaceConfigManagerProxy {
  public static SlgMonsterConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(SlgMonsterConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new SlgMonsterConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "slgMonster.xml"; }

  public static class SlgMonsterConfigManagerImpl extends AbstractConfigManger {
    private List<SlgMonsterConfig> configList = List.of();
    private Map<Integer, SlgMonsterConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("long");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("long");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("long");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("int");
      String fileName = configDir + File.separator + getConfigFileName();
      SlgMonsterConfigChecker checker = new SlgMonsterConfigChecker();
      List<SlgMonsterConfig> newList = new ArrayList<>();
      Map<Integer, SlgMonsterConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          long parsedField1 = 0L;
          long parsedField2 = 0L;
          long parsedField3 = 0L;
          int parsedField4 = 0;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("hp"), fileName + ":行" + lineNumber + ":字段 hp");
            parsedField2 = ly.ConfigValues.read(type2, values.get("attack"), fileName + ":行" + lineNumber + ":字段 attack");
            parsedField3 = ly.ConfigValues.read(type3, values.get("defence"), fileName + ":行" + lineNumber + ":字段 defence");
            parsedField4 = ly.ConfigValues.read(type4, values.get("activeSkill"), fileName + ":行" + lineNumber + ":字段 activeSkill");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          SlgMonsterConfig config = new SlgMonsterConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (SlgMonsterConfig config : configList) config.afterLoad();
      new SlgMonsterConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<SlgMonsterConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, SlgMonsterConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "slgMonster.xml";
    }

    // @@@@@自定义方法开始区@@@@@
    @Override
    protected void afterLoad() {
    }

    // @@@@@自定义方法结束区@@@@@
  }
}
