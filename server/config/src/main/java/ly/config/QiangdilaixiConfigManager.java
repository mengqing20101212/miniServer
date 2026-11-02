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
 * File: QiangdilaixiConfigManager
 */
public class QiangdilaixiConfigManager implements InterfaceConfigManagerProxy {
  public static QiangdilaixiConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(QiangdilaixiConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new QiangdilaixiConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "qiangdilaixi.xml"; }

  public static class QiangdilaixiConfigManagerImpl extends AbstractConfigManger {
    private List<QiangdilaixiConfig> configList = List.of();
    private Map<Integer, QiangdilaixiConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type5 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type7 = ly.ConfigValueType.parse("string");
      String fileName = configDir + File.separator + getConfigFileName();
      QiangdilaixiConfigChecker checker = new QiangdilaixiConfigChecker();
      List<QiangdilaixiConfig> newList = new ArrayList<>();
      Map<Integer, QiangdilaixiConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          String parsedField1 = null;
          String parsedField2 = null;
          String parsedField3 = null;
          String parsedField4 = null;
          int parsedField5 = 0;
          String parsedField6 = null;
          String parsedField7 = null;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("config_name"), fileName + ":行" + lineNumber + ":字段 config_name");
            parsedField2 = ly.ConfigValues.read(type2, values.get("name"), fileName + ":行" + lineNumber + ":字段 name");
            parsedField3 = ly.ConfigValues.read(type3, values.get("detail"), fileName + ":行" + lineNumber + ":字段 detail");
            parsedField4 = ly.ConfigValues.read(type4, values.get("timeline"), fileName + ":行" + lineNumber + ":字段 timeline");
            parsedField5 = ly.ConfigValues.read(type5, values.get("UIResource"), fileName + ":行" + lineNumber + ":字段 UIResource");
            parsedField6 = ly.ConfigValues.read(type6, values.get("ScalePosOffset"), fileName + ":行" + lineNumber + ":字段 ScalePosOffset");
            parsedField7 = ly.ConfigValues.read(type7, values.get("stopAt"), fileName + ":行" + lineNumber + ":字段 stopAt");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          QiangdilaixiConfig config = new QiangdilaixiConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (QiangdilaixiConfig config : configList) config.afterLoad();
      new QiangdilaixiConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<QiangdilaixiConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, QiangdilaixiConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "qiangdilaixi.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
