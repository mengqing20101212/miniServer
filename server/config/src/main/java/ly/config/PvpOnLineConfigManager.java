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
 * File: PvpOnLineConfigManager
 */
public class PvpOnLineConfigManager implements InterfaceConfigManagerProxy {
  public static PvpOnLineConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(PvpOnLineConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new PvpOnLineConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "pvpOnLine.xml"; }

  public static class PvpOnLineConfigManagerImpl extends AbstractConfigManger {
    private List<PvpOnLineConfig> configList = List.of();
    private Map<Integer, PvpOnLineConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type5 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type7 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type8 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type9 = ly.ConfigValueType.parse("float");
      ly.ConfigValueType type10 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type11 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type12 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type13 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type14 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type15 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type16 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type17 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type18 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type19 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type20 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type21 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type22 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type23 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type24 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type25 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type26 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type27 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type28 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type29 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type30 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type31 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type32 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type33 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type34 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type35 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type36 = ly.ConfigValueType.parse("int");
      String fileName = configDir + File.separator + getConfigFileName();
      PvpOnLineConfigChecker checker = new PvpOnLineConfigChecker();
      List<PvpOnLineConfig> newList = new ArrayList<>();
      Map<Integer, PvpOnLineConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          String parsedField1 = null;
          String parsedField2 = null;
          int parsedField3 = 0;
          int parsedField4 = 0;
          int parsedField5 = 0;
          int parsedField6 = 0;
          int parsedField7 = 0;
          int parsedField8 = 0;
          float parsedField9 = 0F;
          int parsedField10 = 0;
          int parsedField11 = 0;
          int parsedField12 = 0;
          int parsedField13 = 0;
          int parsedField14 = 0;
          int parsedField15 = 0;
          int parsedField16 = 0;
          int parsedField17 = 0;
          int parsedField18 = 0;
          int parsedField19 = 0;
          int parsedField20 = 0;
          int parsedField21 = 0;
          int parsedField22 = 0;
          int parsedField23 = 0;
          int parsedField24 = 0;
          int parsedField25 = 0;
          int parsedField26 = 0;
          int parsedField27 = 0;
          int parsedField28 = 0;
          int parsedField29 = 0;
          int parsedField30 = 0;
          String parsedField31 = null;
          String parsedField32 = null;
          String parsedField33 = null;
          int parsedField34 = 0;
          int parsedField35 = 0;
          int parsedField36 = 0;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("duan"), fileName + ":行" + lineNumber + ":字段 duan");
            parsedField2 = ly.ConfigValues.read(type2, values.get("duanBigName"), fileName + ":行" + lineNumber + ":字段 duanBigName");
            parsedField3 = ly.ConfigValues.read(type3, values.get("duanBig"), fileName + ":行" + lineNumber + ":字段 duanBig");
            parsedField4 = ly.ConfigValues.read(type4, values.get("duanSmall"), fileName + ":行" + lineNumber + ":字段 duanSmall");
            parsedField5 = ly.ConfigValues.read(type5, values.get("duanIconBig"), fileName + ":行" + lineNumber + ":字段 duanIconBig");
            parsedField6 = ly.ConfigValues.read(type6, values.get("duanIconSmall"), fileName + ":行" + lineNumber + ":字段 duanIconSmall");
            parsedField7 = ly.ConfigValues.read(type7, values.get("starNum"), fileName + ":行" + lineNumber + ":字段 starNum");
            parsedField8 = ly.ConfigValues.read(type8, values.get("starCollect"), fileName + ":行" + lineNumber + ":字段 starCollect");
            parsedField9 = ly.ConfigValues.read(type9, values.get("eloKValue"), fileName + ":行" + lineNumber + ":字段 eloKValue");
            parsedField10 = ly.ConfigValues.read(type10, values.get("scoreMax"), fileName + ":行" + lineNumber + ":字段 scoreMax");
            parsedField11 = ly.ConfigValues.read(type11, values.get("scoreProtection"), fileName + ":行" + lineNumber + ":字段 scoreProtection");
            parsedField12 = ly.ConfigValues.read(type12, values.get("isScoreProtection"), fileName + ":行" + lineNumber + ":字段 isScoreProtection");
            parsedField13 = ly.ConfigValues.read(type13, values.get("winningStreak"), fileName + ":行" + lineNumber + ":字段 winningStreak");
            parsedField14 = ly.ConfigValues.read(type14, values.get("dropProtection"), fileName + ":行" + lineNumber + ":字段 dropProtection");
            parsedField15 = ly.ConfigValues.read(type15, values.get("duanProtection"), fileName + ":行" + lineNumber + ":字段 duanProtection");
            parsedField16 = ly.ConfigValues.read(type16, values.get("bestMatchScore"), fileName + ":行" + lineNumber + ":字段 bestMatchScore");
            parsedField17 = ly.ConfigValues.read(type17, values.get("bestMatchTime"), fileName + ":行" + lineNumber + ":字段 bestMatchTime");
            parsedField18 = ly.ConfigValues.read(type18, values.get("bestMatchStar"), fileName + ":行" + lineNumber + ":字段 bestMatchStar");
            parsedField19 = ly.ConfigValues.read(type19, values.get("fuzzyMatchScore"), fileName + ":行" + lineNumber + ":字段 fuzzyMatchScore");
            parsedField20 = ly.ConfigValues.read(type20, values.get("fuzzyMatchTime"), fileName + ":行" + lineNumber + ":字段 fuzzyMatchTime");
            parsedField21 = ly.ConfigValues.read(type21, values.get("fuzzyMatchStar"), fileName + ":行" + lineNumber + ":字段 fuzzyMatchStar");
            parsedField22 = ly.ConfigValues.read(type22, values.get("leastMatchScore"), fileName + ":行" + lineNumber + ":字段 leastMatchScore");
            parsedField23 = ly.ConfigValues.read(type23, values.get("leastMatchTime"), fileName + ":行" + lineNumber + ":字段 leastMatchTime");
            parsedField24 = ly.ConfigValues.read(type24, values.get("leastMatchStar"), fileName + ":行" + lineNumber + ":字段 leastMatchStar");
            parsedField25 = ly.ConfigValues.read(type25, values.get("isTimeOutRebotMatch"), fileName + ":行" + lineNumber + ":字段 isTimeOutRebotMatch");
            parsedField26 = ly.ConfigValues.read(type26, values.get("isLoseRobotMatch"), fileName + ":行" + lineNumber + ":字段 isLoseRobotMatch");
            parsedField27 = ly.ConfigValues.read(type27, values.get("isPick"), fileName + ":行" + lineNumber + ":字段 isPick");
            parsedField28 = ly.ConfigValues.read(type28, values.get("winReward"), fileName + ":行" + lineNumber + ":字段 winReward");
            parsedField29 = ly.ConfigValues.read(type29, values.get("loseReward"), fileName + ":行" + lineNumber + ":字段 loseReward");
            parsedField30 = ly.ConfigValues.read(type30, values.get("honorLimit"), fileName + ":行" + lineNumber + ":字段 honorLimit");
            parsedField31 = ly.ConfigValues.read(type31, values.get("awardWeek"), fileName + ":行" + lineNumber + ":字段 awardWeek");
            parsedField32 = ly.ConfigValues.read(type32, values.get("awardWeekPre"), fileName + ":行" + lineNumber + ":字段 awardWeekPre");
            parsedField33 = ly.ConfigValues.read(type33, values.get("bossTimeSetting"), fileName + ":行" + lineNumber + ":字段 bossTimeSetting");
            parsedField34 = ly.ConfigValues.read(type34, values.get("robotPool"), fileName + ":行" + lineNumber + ":字段 robotPool");
            parsedField35 = ly.ConfigValues.read(type35, values.get("sceneMatch"), fileName + ":行" + lineNumber + ":字段 sceneMatch");
            parsedField36 = ly.ConfigValues.read(type36, values.get("bestMatchLevelDiff"), fileName + ":行" + lineNumber + ":字段 bestMatchLevelDiff");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          PvpOnLineConfig config = new PvpOnLineConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8, parsedField9, parsedField10, parsedField11, parsedField12, parsedField13, parsedField14, parsedField15, parsedField16, parsedField17, parsedField18, parsedField19, parsedField20, parsedField21, parsedField22, parsedField23, parsedField24, parsedField25, parsedField26, parsedField27, parsedField28, parsedField29, parsedField30, parsedField31, parsedField32, parsedField33, parsedField34, parsedField35, parsedField36);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (PvpOnLineConfig config : configList) config.afterLoad();
      new PvpOnLineConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<PvpOnLineConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, PvpOnLineConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "pvpOnLine.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
