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
 * File: SocietyCleanCityBossConfigManager
 */
public class SocietyCleanCityBossConfigManager implements InterfaceConfigManagerProxy {
  public static SocietyCleanCityBossConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(SocietyCleanCityBossConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new SocietyCleanCityBossConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "societyCleanCityBoss.xml"; }

  public static class SocietyCleanCityBossConfigManagerImpl extends AbstractConfigManger {
    private List<SocietyCleanCityBossConfig> configList = List.of();
    private Map<Integer, SocietyCleanCityBossConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type5 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type7 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type8 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type9 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type10 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type11 = ly.ConfigValueType.parse("string");
      String fileName = configDir + File.separator + getConfigFileName();
      SocietyCleanCityBossConfigChecker checker = new SocietyCleanCityBossConfigChecker();
      List<SocietyCleanCityBossConfig> newList = new ArrayList<>();
      Map<Integer, SocietyCleanCityBossConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          int parsedField1 = 0;
          String parsedField2 = null;
          int parsedField3 = 0;
          String parsedField4 = null;
          String parsedField5 = null;
          int parsedField6 = 0;
          int parsedField7 = 0;
          int parsedField8 = 0;
          String parsedField9 = null;
          int parsedField10 = 0;
          String parsedField11 = null;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("sceneId"), fileName + ":行" + lineNumber + ":字段 sceneId");
            parsedField2 = ly.ConfigValues.read(type2, values.get("battleReward"), fileName + ":行" + lineNumber + ":字段 battleReward");
            parsedField3 = ly.ConfigValues.read(type3, values.get("killReward"), fileName + ":行" + lineNumber + ":字段 killReward");
            parsedField4 = ly.ConfigValues.read(type4, values.get("disPlayBattleReward"), fileName + ":行" + lineNumber + ":字段 disPlayBattleReward");
            parsedField5 = ly.ConfigValues.read(type5, values.get("disPlayKillReward"), fileName + ":行" + lineNumber + ":字段 disPlayKillReward");
            parsedField6 = ly.ConfigValues.read(type6, values.get("bossId"), fileName + ":行" + lineNumber + ":字段 bossId");
            parsedField7 = ly.ConfigValues.read(type7, values.get("bossDamageStatisticsType"), fileName + ":行" + lineNumber + ":字段 bossDamageStatisticsType");
            parsedField8 = ly.ConfigValues.read(type8, values.get("spineModelResId"), fileName + ":行" + lineNumber + ":字段 spineModelResId");
            parsedField9 = ly.ConfigValues.read(type9, values.get("spineAnimation"), fileName + ":行" + lineNumber + ":字段 spineAnimation");
            parsedField10 = ly.ConfigValues.read(type10, values.get("scale"), fileName + ":行" + lineNumber + ":字段 scale");
            parsedField11 = ly.ConfigValues.read(type11, values.get("coordinate"), fileName + ":行" + lineNumber + ":字段 coordinate");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          SocietyCleanCityBossConfig config = new SocietyCleanCityBossConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8, parsedField9, parsedField10, parsedField11);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (SocietyCleanCityBossConfig config : configList) config.afterLoad();
      new SocietyCleanCityBossConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<SocietyCleanCityBossConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, SocietyCleanCityBossConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "societyCleanCityBoss.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
