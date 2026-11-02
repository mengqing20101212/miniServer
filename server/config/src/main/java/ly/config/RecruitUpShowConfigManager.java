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
 * File: RecruitUpShowConfigManager
 */
public class RecruitUpShowConfigManager implements InterfaceConfigManagerProxy {
  public static RecruitUpShowConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(RecruitUpShowConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new RecruitUpShowConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "recruitUpShow.xml"; }

  public static class RecruitUpShowConfigManagerImpl extends AbstractConfigManger {
    private List<RecruitUpShowConfig> configList = List.of();
    private Map<Integer, RecruitUpShowConfig> configMap = Map.of();

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
      ly.ConfigValueType type8 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type9 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type10 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type11 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type12 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type13 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type14 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type15 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type16 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type17 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type18 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type19 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type20 = ly.ConfigValueType.parse("int");
      String fileName = configDir + File.separator + getConfigFileName();
      RecruitUpShowConfigChecker checker = new RecruitUpShowConfigChecker();
      List<RecruitUpShowConfig> newList = new ArrayList<>();
      Map<Integer, RecruitUpShowConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          int parsedField1 = 0;
          int parsedField2 = 0;
          int parsedField3 = 0;
          String parsedField4 = null;
          int parsedField5 = 0;
          int parsedField6 = 0;
          int parsedField7 = 0;
          int parsedField8 = 0;
          String parsedField9 = null;
          String parsedField10 = null;
          String parsedField11 = null;
          int parsedField12 = 0;
          int parsedField13 = 0;
          String parsedField14 = null;
          int parsedField15 = 0;
          int parsedField16 = 0;
          int parsedField17 = 0;
          int parsedField18 = 0;
          int parsedField19 = 0;
          int parsedField20 = 0;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("scheDuling"), fileName + ":行" + lineNumber + ":字段 scheDuling");
            parsedField2 = ly.ConfigValues.read(type2, values.get("piecePicResId"), fileName + ":行" + lineNumber + ":字段 piecePicResId");
            parsedField3 = ly.ConfigValues.read(type3, values.get("heroId"), fileName + ":行" + lineNumber + ":字段 heroId");
            parsedField4 = ly.ConfigValues.read(type4, values.get("heroName"), fileName + ":行" + lineNumber + ":字段 heroName");
            parsedField5 = ly.ConfigValues.read(type5, values.get("contentPicResId"), fileName + ":行" + lineNumber + ":字段 contentPicResId");
            parsedField6 = ly.ConfigValues.read(type6, values.get("jumpType"), fileName + ":行" + lineNumber + ":字段 jumpType");
            parsedField7 = ly.ConfigValues.read(type7, values.get("sceneId"), fileName + ":行" + lineNumber + ":字段 sceneId");
            parsedField8 = ly.ConfigValues.read(type8, values.get("heroShowStyleType"), fileName + ":行" + lineNumber + ":字段 heroShowStyleType");
            parsedField9 = ly.ConfigValues.read(type9, values.get("heroPicResId"), fileName + ":行" + lineNumber + ":字段 heroPicResId");
            parsedField10 = ly.ConfigValues.read(type10, values.get("heroJumpId"), fileName + ":行" + lineNumber + ":字段 heroJumpId");
            parsedField11 = ly.ConfigValues.read(type11, values.get("videoName"), fileName + ":行" + lineNumber + ":字段 videoName");
            parsedField12 = ly.ConfigValues.read(type12, values.get("videoHeroPic"), fileName + ":行" + lineNumber + ":字段 videoHeroPic");
            parsedField13 = ly.ConfigValues.read(type13, values.get("videoHeroInfoPic"), fileName + ":行" + lineNumber + ":字段 videoHeroInfoPic");
            parsedField14 = ly.ConfigValues.read(type14, values.get("videoHeroPicPosOffset"), fileName + ":行" + lineNumber + ":字段 videoHeroPicPosOffset");
            parsedField15 = ly.ConfigValues.read(type15, values.get("isShowHolograpicLogo"), fileName + ":行" + lineNumber + ":字段 isShowHolograpicLogo");
            parsedField16 = ly.ConfigValues.read(type16, values.get("isShowReturnLogo"), fileName + ":行" + lineNumber + ":字段 isShowReturnLogo");
            parsedField17 = ly.ConfigValues.read(type17, values.get("gifBagIcon"), fileName + ":行" + lineNumber + ":字段 gifBagIcon");
            parsedField18 = ly.ConfigValues.read(type18, values.get("gifBagTurn"), fileName + ":行" + lineNumber + ":字段 gifBagTurn");
            parsedField19 = ly.ConfigValues.read(type19, values.get("turnId"), fileName + ":行" + lineNumber + ":字段 turnId");
            parsedField20 = ly.ConfigValues.read(type20, values.get("trueActivityId"), fileName + ":行" + lineNumber + ":字段 trueActivityId");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          RecruitUpShowConfig config = new RecruitUpShowConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8, parsedField9, parsedField10, parsedField11, parsedField12, parsedField13, parsedField14, parsedField15, parsedField16, parsedField17, parsedField18, parsedField19, parsedField20);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (RecruitUpShowConfig config : configList) config.afterLoad();
      new RecruitUpShowConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<RecruitUpShowConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, RecruitUpShowConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "recruitUpShow.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
