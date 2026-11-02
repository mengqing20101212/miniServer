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
 * File: AchievementMainConfigManager
 */
public class AchievementMainConfigManager implements InterfaceConfigManagerProxy {
  public static AchievementMainConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(AchievementMainConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new AchievementMainConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "achievementMain.xml"; }

  public static class AchievementMainConfigManagerImpl extends AbstractConfigManger {
    private List<AchievementMainConfig> configList = List.of();
    private Map<Integer, AchievementMainConfig> configMap = Map.of();

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
      ly.ConfigValueType type8 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type9 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type10 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type11 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type12 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type13 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type14 = ly.ConfigValueType.parse("int");
      String fileName = configDir + File.separator + getConfigFileName();
      AchievementMainConfigChecker checker = new AchievementMainConfigChecker();
      List<AchievementMainConfig> newList = new ArrayList<>();
      Map<Integer, AchievementMainConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          int parsedField1 = 0;
          int parsedField2 = 0;
          int parsedField3 = 0;
          int parsedField4 = 0;
          int parsedField5 = 0;
          int parsedField6 = 0;
          int parsedField7 = 0;
          String parsedField8 = null;
          String parsedField9 = null;
          int parsedField10 = 0;
          int parsedField11 = 0;
          String parsedField12 = null;
          int parsedField13 = 0;
          int parsedField14 = 0;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("group"), fileName + ":行" + lineNumber + ":字段 group");
            parsedField2 = ly.ConfigValues.read(type2, values.get("groupServer"), fileName + ":行" + lineNumber + ":字段 groupServer");
            parsedField3 = ly.ConfigValues.read(type3, values.get("isIfGroup"), fileName + ":行" + lineNumber + ":字段 isIfGroup");
            parsedField4 = ly.ConfigValues.read(type4, values.get("groupLv"), fileName + ":行" + lineNumber + ":字段 groupLv");
            parsedField5 = ly.ConfigValues.read(type5, values.get("priority"), fileName + ":行" + lineNumber + ":字段 priority");
            parsedField6 = ly.ConfigValues.read(type6, values.get("firstType"), fileName + ":行" + lineNumber + ":字段 firstType");
            parsedField7 = ly.ConfigValues.read(type7, values.get("secondType"), fileName + ":行" + lineNumber + ":字段 secondType");
            parsedField8 = ly.ConfigValues.read(type8, values.get("name"), fileName + ":行" + lineNumber + ":字段 name");
            parsedField9 = ly.ConfigValues.read(type9, values.get("des"), fileName + ":行" + lineNumber + ":字段 des");
            parsedField10 = ly.ConfigValues.read(type10, values.get("missionId"), fileName + ":行" + lineNumber + ":字段 missionId");
            parsedField11 = ly.ConfigValues.read(type11, values.get("point"), fileName + ":行" + lineNumber + ":字段 point");
            parsedField12 = ly.ConfigValues.read(type12, values.get("rewardShow"), fileName + ":行" + lineNumber + ":字段 rewardShow");
            parsedField13 = ly.ConfigValues.read(type13, values.get("dropId"), fileName + ":行" + lineNumber + ":字段 dropId");
            parsedField14 = ly.ConfigValues.read(type14, values.get("index"), fileName + ":行" + lineNumber + ":字段 index");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          AchievementMainConfig config = new AchievementMainConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8, parsedField9, parsedField10, parsedField11, parsedField12, parsedField13, parsedField14);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (AchievementMainConfig config : configList) config.afterLoad();
      new AchievementMainConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<AchievementMainConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, AchievementMainConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "achievementMain.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
