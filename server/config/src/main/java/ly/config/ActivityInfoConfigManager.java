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
 * File: ActivityInfoConfigManager
 */
public class ActivityInfoConfigManager implements InterfaceConfigManagerProxy {
  public static ActivityInfoConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(ActivityInfoConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new ActivityInfoConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "activityInfo.xml"; }

  public static class ActivityInfoConfigManagerImpl extends AbstractConfigManger {
    private List<ActivityInfoConfig> configList = List.of();
    private Map<Integer, ActivityInfoConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type5 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type7 = ly.ConfigValueType.parse("int");
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
      ly.ConfigValueType type25 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type26 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type27 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type28 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type29 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type30 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type31 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type32 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type33 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type34 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type35 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type36 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type37 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type38 = ly.ConfigValueType.parse("string");
      String fileName = configDir + File.separator + getConfigFileName();
      ActivityInfoConfigChecker checker = new ActivityInfoConfigChecker();
      List<ActivityInfoConfig> newList = new ArrayList<>();
      Map<Integer, ActivityInfoConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          String parsedField1 = null;
          int parsedField2 = 0;
          int parsedField3 = 0;
          int parsedField4 = 0;
          int parsedField5 = 0;
          int parsedField6 = 0;
          int parsedField7 = 0;
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
          int parsedField25 = 0;
          String parsedField26 = null;
          int parsedField27 = 0;
          int parsedField28 = 0;
          int parsedField29 = 0;
          String parsedField30 = null;
          int parsedField31 = 0;
          String parsedField32 = null;
          String parsedField33 = null;
          int parsedField34 = 0;
          int parsedField35 = 0;
          int parsedField36 = 0;
          int parsedField37 = 0;
          String parsedField38 = null;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("name"), fileName + ":行" + lineNumber + ":字段 name");
            parsedField2 = ly.ConfigValues.read(type2, values.get("openType"), fileName + ":行" + lineNumber + ":字段 openType");
            parsedField3 = ly.ConfigValues.read(type3, values.get("scheDuling"), fileName + ":行" + lineNumber + ":字段 scheDuling");
            parsedField4 = ly.ConfigValues.read(type4, values.get("openPara1"), fileName + ":行" + lineNumber + ":字段 openPara1");
            parsedField5 = ly.ConfigValues.read(type5, values.get("openPara2"), fileName + ":行" + lineNumber + ":字段 openPara2");
            parsedField6 = ly.ConfigValues.read(type6, values.get("openPara3"), fileName + ":行" + lineNumber + ":字段 openPara3");
            parsedField7 = ly.ConfigValues.read(type7, values.get("timeType"), fileName + ":行" + lineNumber + ":字段 timeType");
            parsedField8 = ly.ConfigValues.read(type8, values.get("startTime"), fileName + ":行" + lineNumber + ":字段 startTime");
            parsedField9 = ly.ConfigValues.read(type9, values.get("endTime"), fileName + ":行" + lineNumber + ":字段 endTime");
            parsedField10 = ly.ConfigValues.read(type10, values.get("specialEndTime"), fileName + ":行" + lineNumber + ":字段 specialEndTime");
            parsedField11 = ly.ConfigValues.read(type11, values.get("freshTime"), fileName + ":行" + lineNumber + ":字段 freshTime");
            parsedField12 = ly.ConfigValues.read(type12, values.get("closeTime"), fileName + ":行" + lineNumber + ":字段 closeTime");
            parsedField13 = ly.ConfigValues.read(type13, values.get("closeActivity"), fileName + ":行" + lineNumber + ":字段 closeActivity");
            parsedField14 = ly.ConfigValues.read(type14, values.get("OpenServiceActivity"), fileName + ":行" + lineNumber + ":字段 OpenServiceActivity");
            parsedField15 = ly.ConfigValues.read(type15, values.get("integralType"), fileName + ":行" + lineNumber + ":字段 integralType");
            parsedField16 = ly.ConfigValues.read(type16, values.get("integralStage"), fileName + ":行" + lineNumber + ":字段 integralStage");
            parsedField17 = ly.ConfigValues.read(type17, values.get("integralReward"), fileName + ":行" + lineNumber + ":字段 integralReward");
            parsedField18 = ly.ConfigValues.read(type18, values.get("integralRewardShow"), fileName + ":行" + lineNumber + ":字段 integralRewardShow");
            parsedField19 = ly.ConfigValues.read(type19, values.get("title"), fileName + ":行" + lineNumber + ":字段 title");
            parsedField20 = ly.ConfigValues.read(type20, values.get("picture"), fileName + ":行" + lineNumber + ":字段 picture");
            parsedField21 = ly.ConfigValues.read(type21, values.get("description"), fileName + ":行" + lineNumber + ":字段 description");
            parsedField22 = ly.ConfigValues.read(type22, values.get("para1"), fileName + ":行" + lineNumber + ":字段 para1");
            parsedField23 = ly.ConfigValues.read(type23, values.get("para2"), fileName + ":行" + lineNumber + ":字段 para2");
            parsedField24 = ly.ConfigValues.read(type24, values.get("para3"), fileName + ":行" + lineNumber + ":字段 para3");
            parsedField25 = ly.ConfigValues.read(type25, values.get("mailTemplateId"), fileName + ":行" + lineNumber + ":字段 mailTemplateId");
            parsedField26 = ly.ConfigValues.read(type26, values.get("exchangeResources"), fileName + ":行" + lineNumber + ":字段 exchangeResources");
            parsedField27 = ly.ConfigValues.read(type27, values.get("enterType"), fileName + ":行" + lineNumber + ":字段 enterType");
            parsedField28 = ly.ConfigValues.read(type28, values.get("sort"), fileName + ":行" + lineNumber + ":字段 sort");
            parsedField29 = ly.ConfigValues.read(type29, values.get("des"), fileName + ":行" + lineNumber + ":字段 des");
            parsedField30 = ly.ConfigValues.read(type30, values.get("destime"), fileName + ":行" + lineNumber + ":字段 destime");
            parsedField31 = ly.ConfigValues.read(type31, values.get("timeDown"), fileName + ":行" + lineNumber + ":字段 timeDown");
            parsedField32 = ly.ConfigValues.read(type32, values.get("desPic"), fileName + ":行" + lineNumber + ":字段 desPic");
            parsedField33 = ly.ConfigValues.read(type33, values.get("RechargeId"), fileName + ":行" + lineNumber + ":字段 RechargeId");
            parsedField34 = ly.ConfigValues.read(type34, values.get("iACTIVITYTYPE"), fileName + ":行" + lineNumber + ":字段 iACTIVITYTYPE");
            parsedField35 = ly.ConfigValues.read(type35, values.get("topId"), fileName + ":行" + lineNumber + ":字段 topId");
            parsedField36 = ly.ConfigValues.read(type36, values.get("NoShow"), fileName + ":行" + lineNumber + ":字段 NoShow");
            parsedField37 = ly.ConfigValues.read(type37, values.get("DisplayFunctionType"), fileName + ":行" + lineNumber + ":字段 DisplayFunctionType");
            parsedField38 = ly.ConfigValues.read(type38, values.get("DisplayFunctionParam"), fileName + ":行" + lineNumber + ":字段 DisplayFunctionParam");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          ActivityInfoConfig config = new ActivityInfoConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8, parsedField9, parsedField10, parsedField11, parsedField12, parsedField13, parsedField14, parsedField15, parsedField16, parsedField17, parsedField18, parsedField19, parsedField20, parsedField21, parsedField22, parsedField23, parsedField24, parsedField25, parsedField26, parsedField27, parsedField28, parsedField29, parsedField30, parsedField31, parsedField32, parsedField33, parsedField34, parsedField35, parsedField36, parsedField37, parsedField38);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (ActivityInfoConfig config : configList) config.afterLoad();
      new ActivityInfoConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<ActivityInfoConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, ActivityInfoConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "activityInfo.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
