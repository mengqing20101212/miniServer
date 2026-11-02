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
 * File: SceneChangeInfoConfigManager
 */
public class SceneChangeInfoConfigManager implements InterfaceConfigManagerProxy {
  public static SceneChangeInfoConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(SceneChangeInfoConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new SceneChangeInfoConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "sceneChangeInfo.xml"; }

  public static class SceneChangeInfoConfigManagerImpl extends AbstractConfigManger {
    private List<SceneChangeInfoConfig> configList = List.of();
    private Map<Integer, SceneChangeInfoConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("bool");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("bool");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("bool");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("bool");
      ly.ConfigValueType type5 = ly.ConfigValueType.parse("bool");
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("bool");
      ly.ConfigValueType type7 = ly.ConfigValueType.parse("bool");
      ly.ConfigValueType type8 = ly.ConfigValueType.parse("bool");
      ly.ConfigValueType type9 = ly.ConfigValueType.parse("bool");
      ly.ConfigValueType type10 = ly.ConfigValueType.parse("bool");
      ly.ConfigValueType type11 = ly.ConfigValueType.parse("bool");
      ly.ConfigValueType type12 = ly.ConfigValueType.parse("bool");
      String fileName = configDir + File.separator + getConfigFileName();
      SceneChangeInfoConfigChecker checker = new SceneChangeInfoConfigChecker();
      List<SceneChangeInfoConfig> newList = new ArrayList<>();
      Map<Integer, SceneChangeInfoConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          boolean parsedField1 = false;
          boolean parsedField2 = false;
          boolean parsedField3 = false;
          boolean parsedField4 = false;
          boolean parsedField5 = false;
          boolean parsedField6 = false;
          boolean parsedField7 = false;
          boolean parsedField8 = false;
          boolean parsedField9 = false;
          boolean parsedField10 = false;
          boolean parsedField11 = false;
          boolean parsedField12 = false;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("hpInherit"), fileName + ":行" + lineNumber + ":字段 hpInherit");
            parsedField2 = ly.ConfigValues.read(type2, values.get("spInherit"), fileName + ":行" + lineNumber + ":字段 spInherit");
            parsedField3 = ly.ConfigValues.read(type3, values.get("energyInherit"), fileName + ":行" + lineNumber + ":字段 energyInherit");
            parsedField4 = ly.ConfigValues.read(type4, values.get("energyBarInherit"), fileName + ":行" + lineNumber + ":字段 energyBarInherit");
            parsedField5 = ly.ConfigValues.read(type5, values.get("sPowerInherit"), fileName + ":行" + lineNumber + ":字段 sPowerInherit");
            parsedField6 = ly.ConfigValues.read(type6, values.get("actionBarInherit"), fileName + ":行" + lineNumber + ":字段 actionBarInherit");
            parsedField7 = ly.ConfigValues.read(type7, values.get("skillCDInherit"), fileName + ":行" + lineNumber + ":字段 skillCDInherit");
            parsedField8 = ly.ConfigValues.read(type8, values.get("bonusInherit"), fileName + ":行" + lineNumber + ":字段 bonusInherit");
            parsedField9 = ly.ConfigValues.read(type9, values.get("buffInherit"), fileName + ":行" + lineNumber + ":字段 buffInherit");
            parsedField10 = ly.ConfigValues.read(type10, values.get("globalRound"), fileName + ":行" + lineNumber + ":字段 globalRound");
            parsedField11 = ly.ConfigValues.read(type11, values.get("campRound"), fileName + ":行" + lineNumber + ":字段 campRound");
            parsedField12 = ly.ConfigValues.read(type12, values.get("charRound"), fileName + ":行" + lineNumber + ":字段 charRound");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          SceneChangeInfoConfig config = new SceneChangeInfoConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8, parsedField9, parsedField10, parsedField11, parsedField12);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (SceneChangeInfoConfig config : configList) config.afterLoad();
      new SceneChangeInfoConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<SceneChangeInfoConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, SceneChangeInfoConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "sceneChangeInfo.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
