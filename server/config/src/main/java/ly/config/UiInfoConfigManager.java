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
 * File: UiInfoConfigManager
 */
public class UiInfoConfigManager implements InterfaceConfigManagerProxy {
  public static UiInfoConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(UiInfoConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new UiInfoConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "uiInfo.xml"; }

  public static class UiInfoConfigManagerImpl extends AbstractConfigManger {
    private List<UiInfoConfig> configList = List.of();
    private Map<Integer, UiInfoConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("int");
      String fileName = configDir + File.separator + getConfigFileName();
      UiInfoConfigChecker checker = new UiInfoConfigChecker();
      List<UiInfoConfig> newList = new ArrayList<>();
      Map<Integer, UiInfoConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          int parsedField1 = 0;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("BGM"), fileName + ":行" + lineNumber + ":字段 BGM");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          UiInfoConfig config = new UiInfoConfig(parsedField0, parsedField1);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (UiInfoConfig config : configList) config.afterLoad();
      new UiInfoConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<UiInfoConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, UiInfoConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "uiInfo.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
