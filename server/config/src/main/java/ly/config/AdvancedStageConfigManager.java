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
 * File: AdvancedStageConfigManager
 */
public class AdvancedStageConfigManager implements InterfaceConfigManagerProxy {
  public static AdvancedStageConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(AdvancedStageConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new AdvancedStageConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "advancedStage.xml"; }

  public static class AdvancedStageConfigManagerImpl extends AbstractConfigManger {
    private List<AdvancedStageConfig> configList = List.of();
    private Map<Integer, AdvancedStageConfig> configMap = Map.of();

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
      ly.ConfigValueType type7 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type8 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type9 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type10 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type11 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type12 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type13 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type14 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type15 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type16 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type17 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type18 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type19 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type20 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type21 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type22 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type23 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type24 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type25 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type26 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type27 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type28 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type29 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type30 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type31 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type32 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type33 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type34 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type35 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type36 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type37 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type38 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type39 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type40 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type41 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type42 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type43 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type44 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type45 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type46 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type47 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type48 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type49 = ly.ConfigValueType.parse("int");
      String fileName = configDir + File.separator + getConfigFileName();
      AdvancedStageConfigChecker checker = new AdvancedStageConfigChecker();
      List<AdvancedStageConfig> newList = new ArrayList<>();
      Map<Integer, AdvancedStageConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          int parsedField1 = 0;
          int parsedField2 = 0;
          int parsedField3 = 0;
          int parsedField4 = 0;
          int parsedField5 = 0;
          int parsedField6 = 0;
          String parsedField7 = null;
          String parsedField8 = null;
          String parsedField9 = null;
          String parsedField10 = null;
          String parsedField11 = null;
          int parsedField12 = 0;
          int parsedField13 = 0;
          int parsedField14 = 0;
          int parsedField15 = 0;
          String parsedField16 = null;
          String parsedField17 = null;
          String parsedField18 = null;
          String parsedField19 = null;
          String parsedField20 = null;
          String parsedField21 = null;
          String parsedField22 = null;
          String parsedField23 = null;
          String parsedField24 = null;
          String parsedField25 = null;
          String parsedField26 = null;
          String parsedField27 = null;
          String parsedField28 = null;
          String parsedField29 = null;
          int parsedField30 = 0;
          int parsedField31 = 0;
          String parsedField32 = null;
          String parsedField33 = null;
          String parsedField34 = null;
          int parsedField35 = 0;
          int parsedField36 = 0;
          String parsedField37 = null;
          String parsedField38 = null;
          int parsedField39 = 0;
          int parsedField40 = 0;
          int parsedField41 = 0;
          String parsedField42 = null;
          String parsedField43 = null;
          int parsedField44 = 0;
          int parsedField45 = 0;
          int parsedField46 = 0;
          int parsedField47 = 0;
          int parsedField48 = 0;
          int parsedField49 = 0;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("stageType"), fileName + ":行" + lineNumber + ":字段 stageType");
            parsedField2 = ly.ConfigValues.read(type2, values.get("floor"), fileName + ":行" + lineNumber + ":字段 floor");
            parsedField3 = ly.ConfigValues.read(type3, values.get("cost"), fileName + ":行" + lineNumber + ":字段 cost");
            parsedField4 = ly.ConfigValues.read(type4, values.get("advance"), fileName + ":行" + lineNumber + ":字段 advance");
            parsedField5 = ly.ConfigValues.read(type5, values.get("sceneId"), fileName + ":行" + lineNumber + ":字段 sceneId");
            parsedField6 = ly.ConfigValues.read(type6, values.get("dropSelection"), fileName + ":行" + lineNumber + ":字段 dropSelection");
            parsedField7 = ly.ConfigValues.read(type7, values.get("dropList"), fileName + ":行" + lineNumber + ":字段 dropList");
            parsedField8 = ly.ConfigValues.read(type8, values.get("upIcon"), fileName + ":行" + lineNumber + ":字段 upIcon");
            parsedField9 = ly.ConfigValues.read(type9, values.get("dropGroup"), fileName + ":行" + lineNumber + ":字段 dropGroup");
            parsedField10 = ly.ConfigValues.read(type10, values.get("name"), fileName + ":行" + lineNumber + ":字段 name");
            parsedField11 = ly.ConfigValues.read(type11, values.get("lockTips"), fileName + ":行" + lineNumber + ":字段 lockTips");
            parsedField12 = ly.ConfigValues.read(type12, values.get("preStage"), fileName + ":行" + lineNumber + ":字段 preStage");
            parsedField13 = ly.ConfigValues.read(type13, values.get("nextStage"), fileName + ":行" + lineNumber + ":字段 nextStage");
            parsedField14 = ly.ConfigValues.read(type14, values.get("needLv"), fileName + ":行" + lineNumber + ":字段 needLv");
            parsedField15 = ly.ConfigValues.read(type15, values.get("bossHead"), fileName + ":行" + lineNumber + ":字段 bossHead");
            parsedField16 = ly.ConfigValues.read(type16, values.get("model"), fileName + ":行" + lineNumber + ":字段 model");
            parsedField17 = ly.ConfigValues.read(type17, values.get("action"), fileName + ":行" + lineNumber + ":字段 action");
            parsedField18 = ly.ConfigValues.read(type18, values.get("word"), fileName + ":行" + lineNumber + ":字段 word");
            parsedField19 = ly.ConfigValues.read(type19, values.get("decorate1"), fileName + ":行" + lineNumber + ":字段 decorate1");
            parsedField20 = ly.ConfigValues.read(type20, values.get("decorate2"), fileName + ":行" + lineNumber + ":字段 decorate2");
            parsedField21 = ly.ConfigValues.read(type21, values.get("decorate3"), fileName + ":行" + lineNumber + ":字段 decorate3");
            parsedField22 = ly.ConfigValues.read(type22, values.get("bossId"), fileName + ":行" + lineNumber + ":字段 bossId");
            parsedField23 = ly.ConfigValues.read(type23, values.get("scaling"), fileName + ":行" + lineNumber + ":字段 scaling");
            parsedField24 = ly.ConfigValues.read(type24, values.get("offset"), fileName + ":行" + lineNumber + ":字段 offset");
            parsedField25 = ly.ConfigValues.read(type25, values.get("offsetX"), fileName + ":行" + lineNumber + ":字段 offsetX");
            parsedField26 = ly.ConfigValues.read(type26, values.get("offsetAngle"), fileName + ":行" + lineNumber + ":字段 offsetAngle");
            parsedField27 = ly.ConfigValues.read(type27, values.get("showType"), fileName + ":行" + lineNumber + ":字段 showType");
            parsedField28 = ly.ConfigValues.read(type28, values.get("selectionDis"), fileName + ":行" + lineNumber + ":字段 selectionDis");
            parsedField29 = ly.ConfigValues.read(type29, values.get("trigger"), fileName + ":行" + lineNumber + ":字段 trigger");
            parsedField30 = ly.ConfigValues.read(type30, values.get("spineModelResId"), fileName + ":行" + lineNumber + ":字段 spineModelResId");
            parsedField31 = ly.ConfigValues.read(type31, values.get("spineScale"), fileName + ":行" + lineNumber + ":字段 spineScale");
            parsedField32 = ly.ConfigValues.read(type32, values.get("spinePosOffset"), fileName + ":行" + lineNumber + ":字段 spinePosOffset");
            parsedField33 = ly.ConfigValues.read(type33, values.get("spineAnimation"), fileName + ":行" + lineNumber + ":字段 spineAnimation");
            parsedField34 = ly.ConfigValues.read(type34, values.get("dropExpect"), fileName + ":行" + lineNumber + ":字段 dropExpect");
            parsedField35 = ly.ConfigValues.read(type35, values.get("recommendLv"), fileName + ":行" + lineNumber + ":字段 recommendLv");
            parsedField36 = ly.ConfigValues.read(type36, values.get("recommendtype"), fileName + ":行" + lineNumber + ":字段 recommendtype");
            parsedField37 = ly.ConfigValues.read(type37, values.get("recommendhero"), fileName + ":行" + lineNumber + ":字段 recommendhero");
            parsedField38 = ly.ConfigValues.read(type38, values.get("battleTipText"), fileName + ":行" + lineNumber + ":字段 battleTipText");
            parsedField39 = ly.ConfigValues.read(type39, values.get("selectionType"), fileName + ":行" + lineNumber + ":字段 selectionType");
            parsedField40 = ly.ConfigValues.read(type40, values.get("rankType"), fileName + ":行" + lineNumber + ":字段 rankType");
            parsedField41 = ly.ConfigValues.read(type41, values.get("activityControlId"), fileName + ":行" + lineNumber + ":字段 activityControlId");
            parsedField42 = ly.ConfigValues.read(type42, values.get("webDes"), fileName + ":行" + lineNumber + ":字段 webDes");
            parsedField43 = ly.ConfigValues.read(type43, values.get("dropExpectAdd"), fileName + ":行" + lineNumber + ":字段 dropExpectAdd");
            parsedField44 = ly.ConfigValues.read(type44, values.get("recommendHero1"), fileName + ":行" + lineNumber + ":字段 recommendHero1");
            parsedField45 = ly.ConfigValues.read(type45, values.get("recommendHero2"), fileName + ":行" + lineNumber + ":字段 recommendHero2");
            parsedField46 = ly.ConfigValues.read(type46, values.get("iconType"), fileName + ":行" + lineNumber + ":字段 iconType");
            parsedField47 = ly.ConfigValues.read(type47, values.get("noticeIcon"), fileName + ":行" + lineNumber + ":字段 noticeIcon");
            parsedField48 = ly.ConfigValues.read(type48, values.get("firstDropExpect"), fileName + ":行" + lineNumber + ":字段 firstDropExpect");
            parsedField49 = ly.ConfigValues.read(type49, values.get("decorate2Background"), fileName + ":行" + lineNumber + ":字段 decorate2Background");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          AdvancedStageConfig config = new AdvancedStageConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8, parsedField9, parsedField10, parsedField11, parsedField12, parsedField13, parsedField14, parsedField15, parsedField16, parsedField17, parsedField18, parsedField19, parsedField20, parsedField21, parsedField22, parsedField23, parsedField24, parsedField25, parsedField26, parsedField27, parsedField28, parsedField29, parsedField30, parsedField31, parsedField32, parsedField33, parsedField34, parsedField35, parsedField36, parsedField37, parsedField38, parsedField39, parsedField40, parsedField41, parsedField42, parsedField43, parsedField44, parsedField45, parsedField46, parsedField47, parsedField48, parsedField49);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (AdvancedStageConfig config : configList) config.afterLoad();
      new AdvancedStageConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<AdvancedStageConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, AdvancedStageConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "advancedStage.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
