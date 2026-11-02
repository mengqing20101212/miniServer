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
 * File: CookMixConfigManager
 */
public class CookMixConfigManager implements InterfaceConfigManagerProxy {
  public static CookMixConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(CookMixConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new CookMixConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "cookMix.xml"; }

  public static class CookMixConfigManagerImpl extends AbstractConfigManger {
    private List<CookMixConfig> configList = List.of();
    private Map<Integer, CookMixConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("int");
      String fileName = configDir + File.separator + getConfigFileName();
      CookMixConfigChecker checker = new CookMixConfigChecker();
      List<CookMixConfig> newList = new ArrayList<>();
      Map<Integer, CookMixConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          int parsedField1 = 0;
          int parsedField2 = 0;
          int parsedField3 = 0;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("minArea"), fileName + ":行" + lineNumber + ":字段 minArea");
            parsedField2 = ly.ConfigValues.read(type2, values.get("maxArea"), fileName + ":行" + lineNumber + ":字段 maxArea");
            parsedField3 = ly.ConfigValues.read(type3, values.get("dropId"), fileName + ":行" + lineNumber + ":字段 dropId");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          CookMixConfig config = new CookMixConfig(parsedField0, parsedField1, parsedField2, parsedField3);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (CookMixConfig config : configList) config.afterLoad();
      new CookMixConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<CookMixConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, CookMixConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "cookMix.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
