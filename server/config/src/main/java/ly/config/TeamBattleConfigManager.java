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
 * File: TeamBattleConfigManager
 */
public class TeamBattleConfigManager implements InterfaceConfigManagerProxy {
  public static TeamBattleConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(TeamBattleConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new TeamBattleConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "teamBattle.xml"; }

  public static class TeamBattleConfigManagerImpl extends AbstractConfigManger {
    private List<TeamBattleConfig> configList = List.of();
    private Map<Integer, TeamBattleConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type5 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type7 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type8 = ly.ConfigValueType.parse("bool");
      ly.ConfigValueType type9 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type10 = ly.ConfigValueType.parse("int");
      String fileName = configDir + File.separator + getConfigFileName();
      TeamBattleConfigChecker checker = new TeamBattleConfigChecker();
      List<TeamBattleConfig> newList = new ArrayList<>();
      Map<Integer, TeamBattleConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          int parsedField1 = 0;
          int parsedField2 = 0;
          int parsedField3 = 0;
          String parsedField4 = null;
          int parsedField5 = 0;
          int parsedField6 = 0;
          int parsedField7 = 0;
          boolean parsedField8 = false;
          int parsedField9 = 0;
          int parsedField10 = 0;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("type"), fileName + ":行" + lineNumber + ":字段 type");
            parsedField2 = ly.ConfigValues.read(type2, values.get("activityId"), fileName + ":行" + lineNumber + ":字段 activityId");
            parsedField3 = ly.ConfigValues.read(type3, values.get("level"), fileName + ":行" + lineNumber + ":字段 level");
            parsedField4 = ly.ConfigValues.read(type4, values.get("name"), fileName + ":行" + lineNumber + ":字段 name");
            parsedField5 = ly.ConfigValues.read(type5, values.get("dungeonConfigType"), fileName + ":行" + lineNumber + ":字段 dungeonConfigType");
            parsedField6 = ly.ConfigValues.read(type6, values.get("dungeonConfigId"), fileName + ":行" + lineNumber + ":字段 dungeonConfigId");
            parsedField7 = ly.ConfigValues.read(type7, values.get("sceneId"), fileName + ":行" + lineNumber + ":字段 sceneId");
            parsedField8 = ly.ConfigValues.read(type8, values.get("isDynamic"), fileName + ":行" + lineNumber + ":字段 isDynamic");
            parsedField9 = ly.ConfigValues.read(type9, values.get("isOut"), fileName + ":行" + lineNumber + ":字段 isOut");
            parsedField10 = ly.ConfigValues.read(type10, values.get("stageType"), fileName + ":行" + lineNumber + ":字段 stageType");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          TeamBattleConfig config = new TeamBattleConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8, parsedField9, parsedField10);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (TeamBattleConfig config : configList) config.afterLoad();
      new TeamBattleConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<TeamBattleConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, TeamBattleConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "teamBattle.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
