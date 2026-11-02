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
 * File: SceneStarConfigManager
 */
public class SceneStarConfigManager implements InterfaceConfigManagerProxy {
  public static SceneStarConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(SceneStarConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new SceneStarConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "sceneStar.xml"; }

  public static class SceneStarConfigManagerImpl extends AbstractConfigManger {
    private List<SceneStarConfig> configList = List.of();
    private Map<Integer, SceneStarConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("string");
      String fileName = configDir + File.separator + getConfigFileName();
      SceneStarConfigChecker checker = new SceneStarConfigChecker();
      List<SceneStarConfig> newList = new ArrayList<>();
      Map<Integer, SceneStarConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          int parsedField1 = 0;
          int parsedField2 = 0;
          int parsedField3 = 0;
          String parsedField4 = null;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("type"), fileName + ":行" + lineNumber + ":字段 type");
            parsedField2 = ly.ConfigValues.read(type2, values.get("parax"), fileName + ":行" + lineNumber + ":字段 parax");
            parsedField3 = ly.ConfigValues.read(type3, values.get("paray"), fileName + ":行" + lineNumber + ":字段 paray");
            parsedField4 = ly.ConfigValues.read(type4, values.get("description"), fileName + ":行" + lineNumber + ":字段 description");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          SceneStarConfig config = new SceneStarConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (SceneStarConfig config : configList) config.afterLoad();
      new SceneStarConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<SceneStarConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, SceneStarConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "sceneStar.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
