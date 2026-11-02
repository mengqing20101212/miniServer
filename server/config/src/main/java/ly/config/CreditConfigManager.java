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
 * File: CreditConfigManager
 */
public class CreditConfigManager implements InterfaceConfigManagerProxy {
  public static CreditConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(CreditConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new CreditConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "credit.xml"; }

  public static class CreditConfigManagerImpl extends AbstractConfigManger {
    private List<CreditConfig> configList = List.of();
    private Map<Integer, CreditConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type5 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type7 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type8 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type9 = ly.ConfigValueType.parse("string");
      String fileName = configDir + File.separator + getConfigFileName();
      CreditConfigChecker checker = new CreditConfigChecker();
      List<CreditConfig> newList = new ArrayList<>();
      Map<Integer, CreditConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          int parsedField1 = 0;
          String parsedField2 = null;
          int parsedField3 = 0;
          int parsedField4 = 0;
          int parsedField5 = 0;
          String parsedField6 = null;
          int parsedField7 = 0;
          int parsedField8 = 0;
          String parsedField9 = null;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("activityList"), fileName + ":行" + lineNumber + ":字段 activityList");
            parsedField2 = ly.ConfigValues.read(type2, values.get("sceneId"), fileName + ":行" + lineNumber + ":字段 sceneId");
            parsedField3 = ly.ConfigValues.read(type3, values.get("grouoId"), fileName + ":行" + lineNumber + ":字段 grouoId");
            parsedField4 = ly.ConfigValues.read(type4, values.get("ScoreMin"), fileName + ":行" + lineNumber + ":字段 ScoreMin");
            parsedField5 = ly.ConfigValues.read(type5, values.get("ScoreMax"), fileName + ":行" + lineNumber + ":字段 ScoreMax");
            parsedField6 = ly.ConfigValues.read(type6, values.get("lable"), fileName + ":行" + lineNumber + ":字段 lable");
            parsedField7 = ly.ConfigValues.read(type7, values.get("lableMin"), fileName + ":行" + lineNumber + ":字段 lableMin");
            parsedField8 = ly.ConfigValues.read(type8, values.get("lableMax"), fileName + ":行" + lineNumber + ":字段 lableMax");
            parsedField9 = ly.ConfigValues.read(type9, values.get("dec"), fileName + ":行" + lineNumber + ":字段 dec");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          CreditConfig config = new CreditConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8, parsedField9);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (CreditConfig config : configList) config.afterLoad();
      new CreditConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<CreditConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, CreditConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "credit.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
