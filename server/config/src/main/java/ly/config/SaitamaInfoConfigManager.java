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
 * File: SaitamaInfoConfigManager
 */
public class SaitamaInfoConfigManager implements InterfaceConfigManagerProxy {
  public static SaitamaInfoConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(SaitamaInfoConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new SaitamaInfoConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "saitamaInfo.xml"; }

  public static class SaitamaInfoConfigManagerImpl extends AbstractConfigManger {
    private List<SaitamaInfoConfig> configList = List.of();
    private Map<Integer, SaitamaInfoConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type5 = ly.ConfigValueType.parse("string");
      String fileName = configDir + File.separator + getConfigFileName();
      SaitamaInfoConfigChecker checker = new SaitamaInfoConfigChecker();
      List<SaitamaInfoConfig> newList = new ArrayList<>();
      Map<Integer, SaitamaInfoConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          String parsedField1 = null;
          int parsedField2 = 0;
          String parsedField3 = null;
          String parsedField4 = null;
          String parsedField5 = null;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("title"), fileName + ":行" + lineNumber + ":字段 title");
            parsedField2 = ly.ConfigValues.read(type2, values.get("icon"), fileName + ":行" + lineNumber + ":字段 icon");
            parsedField3 = ly.ConfigValues.read(type3, values.get("label"), fileName + ":行" + lineNumber + ":字段 label");
            parsedField4 = ly.ConfigValues.read(type4, values.get("word"), fileName + ":行" + lineNumber + ":字段 word");
            parsedField5 = ly.ConfigValues.read(type5, values.get("preWord"), fileName + ":行" + lineNumber + ":字段 preWord");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          SaitamaInfoConfig config = new SaitamaInfoConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (SaitamaInfoConfig config : configList) config.afterLoad();
      new SaitamaInfoConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<SaitamaInfoConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, SaitamaInfoConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "saitamaInfo.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
