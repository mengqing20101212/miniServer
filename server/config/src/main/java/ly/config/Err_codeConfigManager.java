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
 * File: Err_codeConfigManager
 */
public class Err_codeConfigManager implements InterfaceConfigManagerProxy {
  public static Err_codeConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(Err_codeConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new Err_codeConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "err_code.xml"; }

  public static class Err_codeConfigManagerImpl extends AbstractConfigManger {
    private List<Err_codeConfig> configList = List.of();
    private Map<Integer, Err_codeConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("int");
      String fileName = configDir + File.separator + getConfigFileName();
      Err_codeConfigChecker checker = new Err_codeConfigChecker();
      List<Err_codeConfig> newList = new ArrayList<>();
      Map<Integer, Err_codeConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          String parsedField1 = null;
          String parsedField2 = null;
          int parsedField3 = 0;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("define"), fileName + ":行" + lineNumber + ":字段 define");
            parsedField2 = ly.ConfigValues.read(type2, values.get("des"), fileName + ":行" + lineNumber + ":字段 des");
            parsedField3 = ly.ConfigValues.read(type3, values.get("handletype"), fileName + ":行" + lineNumber + ":字段 handletype");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          Err_codeConfig config = new Err_codeConfig(parsedField0, parsedField1, parsedField2, parsedField3);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (Err_codeConfig config : configList) config.afterLoad();
      new Err_codeConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<Err_codeConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, Err_codeConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "err_code.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
