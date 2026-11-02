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
 * File: HeroStoryMainConfigManager
 */
public class HeroStoryMainConfigManager implements InterfaceConfigManagerProxy {
  public static HeroStoryMainConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(HeroStoryMainConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new HeroStoryMainConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "heroStoryMain.xml"; }

  public static class HeroStoryMainConfigManagerImpl extends AbstractConfigManger {
    private List<HeroStoryMainConfig> configList = List.of();
    private Map<Integer, HeroStoryMainConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type5 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("string");
      String fileName = configDir + File.separator + getConfigFileName();
      HeroStoryMainConfigChecker checker = new HeroStoryMainConfigChecker();
      List<HeroStoryMainConfig> newList = new ArrayList<>();
      Map<Integer, HeroStoryMainConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          String parsedField1 = null;
          int parsedField2 = 0;
          int parsedField3 = 0;
          String parsedField4 = null;
          int parsedField5 = 0;
          String parsedField6 = null;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("storyName"), fileName + ":行" + lineNumber + ":字段 storyName");
            parsedField2 = ly.ConfigValues.read(type2, values.get("unlockLv"), fileName + ":行" + lineNumber + ":字段 unlockLv");
            parsedField3 = ly.ConfigValues.read(type3, values.get("show"), fileName + ":行" + lineNumber + ":字段 show");
            parsedField4 = ly.ConfigValues.read(type4, values.get("storyStage"), fileName + ":行" + lineNumber + ":字段 storyStage");
            parsedField5 = ly.ConfigValues.read(type5, values.get("isShow"), fileName + ":行" + lineNumber + ":字段 isShow");
            parsedField6 = ly.ConfigValues.read(type6, values.get("stageDec"), fileName + ":行" + lineNumber + ":字段 stageDec");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          HeroStoryMainConfig config = new HeroStoryMainConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (HeroStoryMainConfig config : configList) config.afterLoad();
      new HeroStoryMainConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<HeroStoryMainConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, HeroStoryMainConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "heroStoryMain.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
