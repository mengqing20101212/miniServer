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
 * File: CurveConfigManager
 */
public class CurveConfigManager implements InterfaceConfigManagerProxy {
  public static CurveConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(CurveConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new CurveConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "curve.xml"; }

  public static class CurveConfigManagerImpl extends AbstractConfigManger {
    private List<CurveConfig> configList = List.of();
    private Map<Integer, CurveConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("string");
      String fileName = configDir + File.separator + getConfigFileName();
      CurveConfigChecker checker = new CurveConfigChecker();
      List<CurveConfig> newList = new ArrayList<>();
      Map<Integer, CurveConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          String parsedField1 = null;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("curve_resname"), fileName + ":行" + lineNumber + ":字段 curve_resname");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          CurveConfig config = new CurveConfig(parsedField0, parsedField1);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (CurveConfig config : configList) config.afterLoad();
      new CurveConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<CurveConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, CurveConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "curve.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
