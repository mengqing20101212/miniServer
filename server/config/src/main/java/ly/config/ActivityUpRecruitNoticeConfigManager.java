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
 * File: ActivityUpRecruitNoticeConfigManager
 */
public class ActivityUpRecruitNoticeConfigManager implements InterfaceConfigManagerProxy {
  public static ActivityUpRecruitNoticeConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(ActivityUpRecruitNoticeConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new ActivityUpRecruitNoticeConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "activityUpRecruitNotice.xml"; }

  public static class ActivityUpRecruitNoticeConfigManagerImpl extends AbstractConfigManger {
    private List<ActivityUpRecruitNoticeConfig> configList = List.of();
    private Map<Integer, ActivityUpRecruitNoticeConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type5 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type7 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type8 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type9 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type10 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type11 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type12 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type13 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type14 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type15 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type16 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type17 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type18 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type19 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type20 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type21 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type22 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type23 = ly.ConfigValueType.parse("int");
      String fileName = configDir + File.separator + getConfigFileName();
      ActivityUpRecruitNoticeConfigChecker checker = new ActivityUpRecruitNoticeConfigChecker();
      List<ActivityUpRecruitNoticeConfig> newList = new ArrayList<>();
      Map<Integer, ActivityUpRecruitNoticeConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          int parsedField1 = 0;
          String parsedField2 = null;
          int parsedField3 = 0;
          int parsedField4 = 0;
          String parsedField5 = null;
          String parsedField6 = null;
          int parsedField7 = 0;
          int parsedField8 = 0;
          String parsedField9 = null;
          int parsedField10 = 0;
          String parsedField11 = null;
          int parsedField12 = 0;
          int parsedField13 = 0;
          int parsedField14 = 0;
          int parsedField15 = 0;
          String parsedField16 = null;
          String parsedField17 = null;
          String parsedField18 = null;
          String parsedField19 = null;
          int parsedField20 = 0;
          int parsedField21 = 0;
          int parsedField22 = 0;
          int parsedField23 = 0;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("scheDuling"), fileName + ":行" + lineNumber + ":字段 scheDuling");
            parsedField2 = ly.ConfigValues.read(type2, values.get("name"), fileName + ":行" + lineNumber + ":字段 name");
            parsedField3 = ly.ConfigValues.read(type3, values.get("heroShowStyleType"), fileName + ":行" + lineNumber + ":字段 heroShowStyleType");
            parsedField4 = ly.ConfigValues.read(type4, values.get("rolePic"), fileName + ":行" + lineNumber + ":字段 rolePic");
            parsedField5 = ly.ConfigValues.read(type5, values.get("rolePicPosOffset"), fileName + ":行" + lineNumber + ":字段 rolePicPosOffset");
            parsedField6 = ly.ConfigValues.read(type6, values.get("rolePicScaleOffset"), fileName + ":行" + lineNumber + ":字段 rolePicScaleOffset");
            parsedField7 = ly.ConfigValues.read(type7, values.get("getDesPicResId"), fileName + ":行" + lineNumber + ":字段 getDesPicResId");
            parsedField8 = ly.ConfigValues.read(type8, values.get("skillPicResId1"), fileName + ":行" + lineNumber + ":字段 skillPicResId1");
            parsedField9 = ly.ConfigValues.read(type9, values.get("skillPicResId1PosOffset"), fileName + ":行" + lineNumber + ":字段 skillPicResId1PosOffset");
            parsedField10 = ly.ConfigValues.read(type10, values.get("skillPicResId2"), fileName + ":行" + lineNumber + ":字段 skillPicResId2");
            parsedField11 = ly.ConfigValues.read(type11, values.get("skillPicResId2PosOffset"), fileName + ":行" + lineNumber + ":字段 skillPicResId2PosOffset");
            parsedField12 = ly.ConfigValues.read(type12, values.get("jumpType"), fileName + ":行" + lineNumber + ":字段 jumpType");
            parsedField13 = ly.ConfigValues.read(type13, values.get("sceneId"), fileName + ":行" + lineNumber + ":字段 sceneId");
            parsedField14 = ly.ConfigValues.read(type14, values.get("drawCardDecPic"), fileName + ":行" + lineNumber + ":字段 drawCardDecPic");
            parsedField15 = ly.ConfigValues.read(type15, values.get("bgPic"), fileName + ":行" + lineNumber + ":字段 bgPic");
            parsedField16 = ly.ConfigValues.read(type16, values.get("bgHeroPics"), fileName + ":行" + lineNumber + ":字段 bgHeroPics");
            parsedField17 = ly.ConfigValues.read(type17, values.get("bgPicPosOffset"), fileName + ":行" + lineNumber + ":字段 bgPicPosOffset");
            parsedField18 = ly.ConfigValues.read(type18, values.get("bgPicScaleOffset"), fileName + ":行" + lineNumber + ":字段 bgPicScaleOffset");
            parsedField19 = ly.ConfigValues.read(type19, values.get("numberPic"), fileName + ":行" + lineNumber + ":字段 numberPic");
            parsedField20 = ly.ConfigValues.read(type20, values.get("activityId"), fileName + ":行" + lineNumber + ":字段 activityId");
            parsedField21 = ly.ConfigValues.read(type21, values.get("trueActivityId"), fileName + ":行" + lineNumber + ":字段 trueActivityId");
            parsedField22 = ly.ConfigValues.read(type22, values.get("holographic"), fileName + ":行" + lineNumber + ":字段 holographic");
            parsedField23 = ly.ConfigValues.read(type23, values.get("SpecialEffects"), fileName + ":行" + lineNumber + ":字段 SpecialEffects");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          ActivityUpRecruitNoticeConfig config = new ActivityUpRecruitNoticeConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8, parsedField9, parsedField10, parsedField11, parsedField12, parsedField13, parsedField14, parsedField15, parsedField16, parsedField17, parsedField18, parsedField19, parsedField20, parsedField21, parsedField22, parsedField23);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (ActivityUpRecruitNoticeConfig config : configList) config.afterLoad();
      new ActivityUpRecruitNoticeConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<ActivityUpRecruitNoticeConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, ActivityUpRecruitNoticeConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "activityUpRecruitNotice.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
