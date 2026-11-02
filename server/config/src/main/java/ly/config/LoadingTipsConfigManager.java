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
 * File: LoadingTipsConfigManager
 */
public class LoadingTipsConfigManager implements InterfaceConfigManagerProxy {
  public static LoadingTipsConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(LoadingTipsConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new LoadingTipsConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "loadingTips.xml"; }

  public static class LoadingTipsConfigManagerImpl extends AbstractConfigManger {
    private List<LoadingTipsConfig> configList = List.of();
    private Map<Integer, LoadingTipsConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("string");
      String fileName = configDir + File.separator + getConfigFileName();
      LoadingTipsConfigChecker checker = new LoadingTipsConfigChecker();
      List<LoadingTipsConfig> newList = new ArrayList<>();
      Map<Integer, LoadingTipsConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          String parsedField1 = null;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("describe"), fileName + ":行" + lineNumber + ":字段 describe");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          LoadingTipsConfig config = new LoadingTipsConfig(parsedField0, parsedField1);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (LoadingTipsConfig config : configList) config.afterLoad();
      new LoadingTipsConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<LoadingTipsConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, LoadingTipsConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "loadingTips.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
