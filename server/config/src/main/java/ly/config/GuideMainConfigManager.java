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
 * File: GuideMainConfigManager
 */
public class GuideMainConfigManager implements InterfaceConfigManagerProxy {
  public static GuideMainConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(GuideMainConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new GuideMainConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "guideMain.xml"; }

  public static class GuideMainConfigManagerImpl extends AbstractConfigManger {
    private List<GuideMainConfig> configList = List.of();
    private Map<Integer, GuideMainConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("list<int>");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type5 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("list<int>");
      ly.ConfigValueType type7 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type8 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type9 = ly.ConfigValueType.parse("list<int>");
      ly.ConfigValueType type10 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type11 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type12 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type13 = ly.ConfigValueType.parse("int");
      String fileName = configDir + File.separator + getConfigFileName();
      GuideMainConfigChecker checker = new GuideMainConfigChecker();
      List<GuideMainConfig> newList = new ArrayList<>();
      Map<Integer, GuideMainConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          String parsedField1 = null;
          int parsedField2 = 0;
          java.util.List<Integer> parsedField3 = null;
          String parsedField4 = null;
          int parsedField5 = 0;
          java.util.List<Integer> parsedField6 = null;
          int parsedField7 = 0;
          int parsedField8 = 0;
          java.util.List<Integer> parsedField9 = null;
          int parsedField10 = 0;
          int parsedField11 = 0;
          int parsedField12 = 0;
          int parsedField13 = 0;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("beizhu"), fileName + ":行" + lineNumber + ":字段 beizhu");
            parsedField2 = ly.ConfigValues.read(type2, values.get("checkNext"), fileName + ":行" + lineNumber + ":字段 checkNext");
            parsedField3 = ly.ConfigValues.read(type3, values.get("guideCond"), fileName + ":行" + lineNumber + ":字段 guideCond");
            parsedField4 = ly.ConfigValues.read(type4, values.get("guidePara1"), fileName + ":行" + lineNumber + ":字段 guidePara1");
            parsedField5 = ly.ConfigValues.read(type5, values.get("isMask"), fileName + ":行" + lineNumber + ":字段 isMask");
            parsedField6 = ly.ConfigValues.read(type6, values.get("stepList"), fileName + ":行" + lineNumber + ":字段 stepList");
            parsedField7 = ly.ConfigValues.read(type7, values.get("keyStep"), fileName + ":行" + lineNumber + ":字段 keyStep");
            parsedField8 = ly.ConfigValues.read(type8, values.get("vanishType"), fileName + ":行" + lineNumber + ":字段 vanishType");
            parsedField9 = ly.ConfigValues.read(type9, values.get("vanishPara"), fileName + ":行" + lineNumber + ":字段 vanishPara");
            parsedField10 = ly.ConfigValues.read(type10, values.get("canSkip"), fileName + ":行" + lineNumber + ":字段 canSkip");
            parsedField11 = ly.ConfigValues.read(type11, values.get("weight"), fileName + ":行" + lineNumber + ":字段 weight");
            parsedField12 = ly.ConfigValues.read(type12, values.get("isLocal"), fileName + ":行" + lineNumber + ":字段 isLocal");
            parsedField13 = ly.ConfigValues.read(type13, values.get("autoTrigger"), fileName + ":行" + lineNumber + ":字段 autoTrigger");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          GuideMainConfig config = new GuideMainConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8, parsedField9, parsedField10, parsedField11, parsedField12, parsedField13);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (GuideMainConfig config : configList) config.afterLoad();
      new GuideMainConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<GuideMainConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, GuideMainConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "guideMain.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
