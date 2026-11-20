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
 * File: SlgBattleSkillConfigManager
 */
public class SlgBattleSkillConfigManager implements InterfaceConfigManagerProxy {
  public static SlgBattleSkillConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(SlgBattleSkillConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new SlgBattleSkillConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "slgBattleSkill.xml"; }

  public static class SlgBattleSkillConfigManagerImpl extends AbstractConfigManger {
    private List<SlgBattleSkillConfig> configList = List.of();
    private Map<Integer, SlgBattleSkillConfig> configMap = Map.of();

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
      String fileName = configDir + File.separator + getConfigFileName();
      SlgBattleSkillConfigChecker checker = new SlgBattleSkillConfigChecker();
      List<SlgBattleSkillConfig> newList = new ArrayList<>();
      Map<Integer, SlgBattleSkillConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          int parsedField1 = 0;
          int parsedField2 = 0;
          int parsedField3 = 0;
          int parsedField4 = 0;
          int parsedField5 = 0;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("heroSkillId"), fileName + ":行" + lineNumber + ":字段 heroSkillId");
            parsedField2 = ly.ConfigValues.read(type2, values.get("effectType"), fileName + ":行" + lineNumber + ":字段 effectType");
            parsedField3 = ly.ConfigValues.read(type3, values.get("targetRule"), fileName + ":行" + lineNumber + ":字段 targetRule");
            parsedField4 = ly.ConfigValues.read(type4, values.get("targetCount"), fileName + ":行" + lineNumber + ":字段 targetCount");
            parsedField5 = ly.ConfigValues.read(type5, values.get("actionRate"), fileName + ":行" + lineNumber + ":字段 actionRate");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          SlgBattleSkillConfig config = new SlgBattleSkillConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (SlgBattleSkillConfig config : configList) config.afterLoad();
      new SlgBattleSkillConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<SlgBattleSkillConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, SlgBattleSkillConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "slgBattleSkill.xml";
    }

    // @@@@@自定义方法开始区@@@@@
    @Override
    protected void afterLoad() {
    }

    // @@@@@自定义方法结束区@@@@@
  }
}
