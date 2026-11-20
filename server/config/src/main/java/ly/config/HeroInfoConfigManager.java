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
 * File: HeroInfoConfigManager
 */
public class HeroInfoConfigManager implements InterfaceConfigManagerProxy {
  public static HeroInfoConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(HeroInfoConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new HeroInfoConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "heroInfo.xml"; }

  public static class HeroInfoConfigManagerImpl extends AbstractConfigManger {
    private List<HeroInfoConfig> configList = List.of();
    private Map<Integer, HeroInfoConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type5 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type7 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type8 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type9 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type10 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type11 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type12 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type13 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type14 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type15 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type16 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type17 = ly.ConfigValueType.parse("string");
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
      ly.ConfigValueType type28 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type29 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type30 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type31 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type32 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type33 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type34 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type35 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type36 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type37 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type38 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type39 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type40 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type41 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type42 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type43 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type44 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type45 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type46 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type47 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type48 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type49 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type50 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type51 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type52 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type53 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type54 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type55 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type56 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type57 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type58 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type59 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type60 = ly.ConfigValueType.parse("int");
      String fileName = configDir + File.separator + getConfigFileName();
      HeroInfoConfigChecker checker = new HeroInfoConfigChecker();
      List<HeroInfoConfig> newList = new ArrayList<>();
      Map<Integer, HeroInfoConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          String parsedField1 = null;
          int parsedField2 = 0;
          int parsedField3 = 0;
          int parsedField4 = 0;
          String parsedField5 = null;
          int parsedField6 = 0;
          int parsedField7 = 0;
          String parsedField8 = null;
          int parsedField9 = 0;
          int parsedField10 = 0;
          int parsedField11 = 0;
          int parsedField12 = 0;
          String parsedField13 = null;
          String parsedField14 = null;
          String parsedField15 = null;
          String parsedField16 = null;
          String parsedField17 = null;
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
          String parsedField28 = null;
          int parsedField29 = 0;
          int parsedField30 = 0;
          int parsedField31 = 0;
          int parsedField32 = 0;
          int parsedField33 = 0;
          int parsedField34 = 0;
          int parsedField35 = 0;
          int parsedField36 = 0;
          int parsedField37 = 0;
          String parsedField38 = null;
          String parsedField39 = null;
          String parsedField40 = null;
          int parsedField41 = 0;
          int parsedField42 = 0;
          int parsedField43 = 0;
          int parsedField44 = 0;
          int parsedField45 = 0;
          int parsedField46 = 0;
          int parsedField47 = 0;
          int parsedField48 = 0;
          String parsedField49 = null;
          int parsedField50 = 0;
          int parsedField51 = 0;
          int parsedField52 = 0;
          int parsedField53 = 0;
          String parsedField54 = null;
          String parsedField55 = null;
          int parsedField56 = 0;
          int parsedField57 = 0;
          int parsedField58 = 0;
          int parsedField59 = 0;
          int parsedField60 = 0;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("name"), fileName + ":行" + lineNumber + ":字段 name");
            parsedField2 = ly.ConfigValues.read(type2, values.get("heroType"), fileName + ":行" + lineNumber + ":字段 heroType");
            parsedField3 = ly.ConfigValues.read(type3, values.get("heroSex"), fileName + ":行" + lineNumber + ":字段 heroSex");
            parsedField4 = ly.ConfigValues.read(type4, values.get("modelResource"), fileName + ":行" + lineNumber + ":字段 modelResource");
            parsedField5 = ly.ConfigValues.read(type5, values.get("modelResourceScale"), fileName + ":行" + lineNumber + ":字段 modelResourceScale");
            parsedField6 = ly.ConfigValues.read(type6, values.get("battleResource"), fileName + ":行" + lineNumber + ":字段 battleResource");
            parsedField7 = ly.ConfigValues.read(type7, values.get("showResource"), fileName + ":行" + lineNumber + ":字段 showResource");
            parsedField8 = ly.ConfigValues.read(type8, values.get("showResourceScale"), fileName + ":行" + lineNumber + ":字段 showResourceScale");
            parsedField9 = ly.ConfigValues.read(type9, values.get("headResource"), fileName + ":行" + lineNumber + ":字段 headResource");
            parsedField10 = ly.ConfigValues.read(type10, values.get("headResource_2"), fileName + ":行" + lineNumber + ":字段 headResource_2");
            parsedField11 = ly.ConfigValues.read(type11, values.get("headResource_3"), fileName + ":行" + lineNumber + ":字段 headResource_3");
            parsedField12 = ly.ConfigValues.read(type12, values.get("headResource_4"), fileName + ":行" + lineNumber + ":字段 headResource_4");
            parsedField13 = ly.ConfigValues.read(type13, values.get("heropinyin"), fileName + ":行" + lineNumber + ":字段 heropinyin");
            parsedField14 = ly.ConfigValues.read(type14, values.get("heroPerformance"), fileName + ":行" + lineNumber + ":字段 heroPerformance");
            parsedField15 = ly.ConfigValues.read(type15, values.get("heroCutUp"), fileName + ":行" + lineNumber + ":字段 heroCutUp");
            parsedField16 = ly.ConfigValues.read(type16, values.get("sSkillCutUp"), fileName + ":行" + lineNumber + ":字段 sSkillCutUp");
            parsedField17 = ly.ConfigValues.read(type17, values.get("sSkipCutUp"), fileName + ":行" + lineNumber + ":字段 sSkipCutUp");
            parsedField18 = ly.ConfigValues.read(type18, values.get("isSP"), fileName + ":行" + lineNumber + ":字段 isSP");
            parsedField19 = ly.ConfigValues.read(type19, values.get("quality"), fileName + ":行" + lineNumber + ":字段 quality");
            parsedField20 = ly.ConfigValues.read(type20, values.get("skill_1"), fileName + ":行" + lineNumber + ":字段 skill_1");
            parsedField21 = ly.ConfigValues.read(type21, values.get("skill_2"), fileName + ":行" + lineNumber + ":字段 skill_2");
            parsedField22 = ly.ConfigValues.read(type22, values.get("skill_3"), fileName + ":行" + lineNumber + ":字段 skill_3");
            parsedField23 = ly.ConfigValues.read(type23, values.get("skill_s1"), fileName + ":行" + lineNumber + ":字段 skill_s1");
            parsedField24 = ly.ConfigValues.read(type24, values.get("skill_s2"), fileName + ":行" + lineNumber + ":字段 skill_s2");
            parsedField25 = ly.ConfigValues.read(type25, values.get("moreAwakenSkill1"), fileName + ":行" + lineNumber + ":字段 moreAwakenSkill1");
            parsedField26 = ly.ConfigValues.read(type26, values.get("moreAwakenSkill2"), fileName + ":行" + lineNumber + ":字段 moreAwakenSkill2");
            parsedField27 = ly.ConfigValues.read(type27, values.get("moreAwakenSkill3"), fileName + ":行" + lineNumber + ":字段 moreAwakenSkill3");
            parsedField28 = ly.ConfigValues.read(type28, values.get("endPerformance"), fileName + ":行" + lineNumber + ":字段 endPerformance");
            parsedField29 = ly.ConfigValues.read(type29, values.get("heroDebris"), fileName + ":行" + lineNumber + ":字段 heroDebris");
            parsedField30 = ly.ConfigValues.read(type30, values.get("herogachadoc"), fileName + ":行" + lineNumber + ":字段 herogachadoc");
            parsedField31 = ly.ConfigValues.read(type31, values.get("heroStateData"), fileName + ":行" + lineNumber + ":字段 heroStateData");
            parsedField32 = ly.ConfigValues.read(type32, values.get("heroAwakenData"), fileName + ":行" + lineNumber + ":字段 heroAwakenData");
            parsedField33 = ly.ConfigValues.read(type33, values.get("expModelId"), fileName + ":行" + lineNumber + ":字段 expModelId");
            parsedField34 = ly.ConfigValues.read(type34, values.get("starModelId"), fileName + ":行" + lineNumber + ":字段 starModelId");
            parsedField35 = ly.ConfigValues.read(type35, values.get("circuitId"), fileName + ":行" + lineNumber + ":字段 circuitId");
            parsedField36 = ly.ConfigValues.read(type36, values.get("perloadId"), fileName + ":行" + lineNumber + ":字段 perloadId");
            parsedField37 = ly.ConfigValues.read(type37, values.get("heroListPreloadId"), fileName + ":行" + lineNumber + ":字段 heroListPreloadId");
            parsedField38 = ly.ConfigValues.read(type38, values.get("pvpCamera"), fileName + ":行" + lineNumber + ":字段 pvpCamera");
            parsedField39 = ly.ConfigValues.read(type39, values.get("teamCamera"), fileName + ":行" + lineNumber + ":字段 teamCamera");
            parsedField40 = ly.ConfigValues.read(type40, values.get("aiName"), fileName + ":行" + lineNumber + ":字段 aiName");
            parsedField41 = ly.ConfigValues.read(type41, values.get("attrRankId"), fileName + ":行" + lineNumber + ":字段 attrRankId");
            parsedField42 = ly.ConfigValues.read(type42, values.get("getPpos"), fileName + ":行" + lineNumber + ":字段 getPpos");
            parsedField43 = ly.ConfigValues.read(type43, values.get("speicalBg"), fileName + ":行" + lineNumber + ":字段 speicalBg");
            parsedField44 = ly.ConfigValues.read(type44, values.get("unlockBg"), fileName + ":行" + lineNumber + ":字段 unlockBg");
            parsedField45 = ly.ConfigValues.read(type45, values.get("qualityIcon"), fileName + ":行" + lineNumber + ":字段 qualityIcon");
            parsedField46 = ly.ConfigValues.read(type46, values.get("activityNpcId"), fileName + ":行" + lineNumber + ":字段 activityNpcId");
            parsedField47 = ly.ConfigValues.read(type47, values.get("background"), fileName + ":行" + lineNumber + ":字段 background");
            parsedField48 = ly.ConfigValues.read(type48, values.get("cardBust"), fileName + ":行" + lineNumber + ":字段 cardBust");
            parsedField49 = ly.ConfigValues.read(type49, values.get("supportTeamAttr"), fileName + ":行" + lineNumber + ":字段 supportTeamAttr");
            parsedField50 = ly.ConfigValues.read(type50, values.get("supportItem"), fileName + ":行" + lineNumber + ":字段 supportItem");
            parsedField51 = ly.ConfigValues.read(type51, values.get("exchangeItem"), fileName + ":行" + lineNumber + ":字段 exchangeItem");
            parsedField52 = ly.ConfigValues.read(type52, values.get("backgroundId"), fileName + ":行" + lineNumber + ":字段 backgroundId");
            parsedField53 = ly.ConfigValues.read(type53, values.get("timeType"), fileName + ":行" + lineNumber + ":字段 timeType");
            parsedField54 = ly.ConfigValues.read(type54, values.get("startTime"), fileName + ":行" + lineNumber + ":字段 startTime");
            parsedField55 = ly.ConfigValues.read(type55, values.get("specialEndTime"), fileName + ":行" + lineNumber + ":字段 specialEndTime");
            parsedField56 = ly.ConfigValues.read(type56, values.get("heroshare"), fileName + ":行" + lineNumber + ":字段 heroshare");
            parsedField57 = ly.ConfigValues.read(type57, values.get("herogroup"), fileName + ":行" + lineNumber + ":字段 herogroup");
            parsedField58 = ly.ConfigValues.read(type58, values.get("OpenServiceActivity"), fileName + ":行" + lineNumber + ":字段 OpenServiceActivity");
            parsedField59 = ly.ConfigValues.read(type59, values.get("characterType"), fileName + ":行" + lineNumber + ":字段 characterType");
            parsedField60 = ly.ConfigValues.read(type60, values.get("unlockFragments"), fileName + ":行" + lineNumber + ":字段 unlockFragments");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          HeroInfoConfig config = new HeroInfoConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8, parsedField9, parsedField10, parsedField11, parsedField12, parsedField13, parsedField14, parsedField15, parsedField16, parsedField17, parsedField18, parsedField19, parsedField20, parsedField21, parsedField22, parsedField23, parsedField24, parsedField25, parsedField26, parsedField27, parsedField28, parsedField29, parsedField30, parsedField31, parsedField32, parsedField33, parsedField34, parsedField35, parsedField36, parsedField37, parsedField38, parsedField39, parsedField40, parsedField41, parsedField42, parsedField43, parsedField44, parsedField45, parsedField46, parsedField47, parsedField48, parsedField49, parsedField50, parsedField51, parsedField52, parsedField53, parsedField54, parsedField55, parsedField56, parsedField57, parsedField58, parsedField59, parsedField60);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (HeroInfoConfig config : configList) config.afterLoad();
      new HeroInfoConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<HeroInfoConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, HeroInfoConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "heroInfo.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {
    }
    // @@@@@自定义方法结束区@@@@@
  }
}
