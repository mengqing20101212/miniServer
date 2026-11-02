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
 * File: ExtractConfigManager
 */
public class ExtractConfigManager implements InterfaceConfigManagerProxy {
  public static ExtractConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(ExtractConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new ExtractConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "extract.xml"; }

  public static class ExtractConfigManagerImpl extends AbstractConfigManger {
    private List<ExtractConfig> configList = List.of();
    private Map<Integer, ExtractConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type5 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type7 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type8 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type9 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type10 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type11 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type12 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type13 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type14 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type15 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type16 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type17 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type18 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type19 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type20 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type21 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type22 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type23 = ly.ConfigValueType.parse("string");
      String fileName = configDir + File.separator + getConfigFileName();
      ExtractConfigChecker checker = new ExtractConfigChecker();
      List<ExtractConfig> newList = new ArrayList<>();
      Map<Integer, ExtractConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          String parsedField1 = null;
          int parsedField2 = 0;
          String parsedField3 = null;
          int parsedField4 = 0;
          String parsedField5 = null;
          String parsedField6 = null;
          int parsedField7 = 0;
          int parsedField8 = 0;
          String parsedField9 = null;
          String parsedField10 = null;
          int parsedField11 = 0;
          String parsedField12 = null;
          int parsedField13 = 0;
          String parsedField14 = null;
          String parsedField15 = null;
          String parsedField16 = null;
          int parsedField17 = 0;
          int parsedField18 = 0;
          int parsedField19 = 0;
          int parsedField20 = 0;
          int parsedField21 = 0;
          String parsedField22 = null;
          String parsedField23 = null;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("name"), fileName + ":行" + lineNumber + ":字段 name");
            parsedField2 = ly.ConfigValues.read(type2, values.get("level"), fileName + ":行" + lineNumber + ":字段 level");
            parsedField3 = ly.ConfigValues.read(type3, values.get("ranking"), fileName + ":行" + lineNumber + ":字段 ranking");
            parsedField4 = ly.ConfigValues.read(type4, values.get("rankNum"), fileName + ":行" + lineNumber + ":字段 rankNum");
            parsedField5 = ly.ConfigValues.read(type5, values.get("city"), fileName + ":行" + lineNumber + ":字段 city");
            parsedField6 = ly.ConfigValues.read(type6, values.get("lines"), fileName + ":行" + lineNumber + ":字段 lines");
            parsedField7 = ly.ConfigValues.read(type7, values.get("random"), fileName + ":行" + lineNumber + ":字段 random");
            parsedField8 = ly.ConfigValues.read(type8, values.get("bodyPic"), fileName + ":行" + lineNumber + ":字段 bodyPic");
            parsedField9 = ly.ConfigValues.read(type9, values.get("features"), fileName + ":行" + lineNumber + ":字段 features");
            parsedField10 = ly.ConfigValues.read(type10, values.get("timeline"), fileName + ":行" + lineNumber + ":字段 timeline");
            parsedField11 = ly.ConfigValues.read(type11, values.get("bg"), fileName + ":行" + lineNumber + ":字段 bg");
            parsedField12 = ly.ConfigValues.read(type12, values.get("englishName"), fileName + ":行" + lineNumber + ":字段 englishName");
            parsedField13 = ly.ConfigValues.read(type13, values.get("smallPicResId"), fileName + ":行" + lineNumber + ":字段 smallPicResId");
            parsedField14 = ly.ConfigValues.read(type14, values.get("smallPicPosOffset"), fileName + ":行" + lineNumber + ":字段 smallPicPosOffset");
            parsedField15 = ly.ConfigValues.read(type15, values.get("smallPicRotationOffset"), fileName + ":行" + lineNumber + ":字段 smallPicRotationOffset");
            parsedField16 = ly.ConfigValues.read(type16, values.get("smallPicScale"), fileName + ":行" + lineNumber + ":字段 smallPicScale");
            parsedField17 = ly.ConfigValues.read(type17, values.get("bodyQualityBgResId"), fileName + ":行" + lineNumber + ":字段 bodyQualityBgResId");
            parsedField18 = ly.ConfigValues.read(type18, values.get("nameQualityBgResId"), fileName + ":行" + lineNumber + ":字段 nameQualityBgResId");
            parsedField19 = ly.ConfigValues.read(type19, values.get("isRepeatPlay"), fileName + ":行" + lineNumber + ":字段 isRepeatPlay");
            parsedField20 = ly.ConfigValues.read(type20, values.get("backgroundId"), fileName + ":行" + lineNumber + ":字段 backgroundId");
            parsedField21 = ly.ConfigValues.read(type21, values.get("shareAnimation"), fileName + ":行" + lineNumber + ":字段 shareAnimation");
            parsedField22 = ly.ConfigValues.read(type22, values.get("sharePicScale"), fileName + ":行" + lineNumber + ":字段 sharePicScale");
            parsedField23 = ly.ConfigValues.read(type23, values.get("sharePicPosOffset"), fileName + ":行" + lineNumber + ":字段 sharePicPosOffset");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          ExtractConfig config = new ExtractConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8, parsedField9, parsedField10, parsedField11, parsedField12, parsedField13, parsedField14, parsedField15, parsedField16, parsedField17, parsedField18, parsedField19, parsedField20, parsedField21, parsedField22, parsedField23);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (ExtractConfig config : configList) config.afterLoad();
      new ExtractConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<ExtractConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, ExtractConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "extract.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
