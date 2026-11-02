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
 * File: SpeakTemplateConfigManager
 */
public class SpeakTemplateConfigManager implements InterfaceConfigManagerProxy {
  public static SpeakTemplateConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(SpeakTemplateConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new SpeakTemplateConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "speakTemplate.xml"; }

  public static class SpeakTemplateConfigManagerImpl extends AbstractConfigManger {
    private List<SpeakTemplateConfig> configList = List.of();
    private Map<Integer, SpeakTemplateConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type5 = ly.ConfigValueType.parse("int");
      String fileName = configDir + File.separator + getConfigFileName();
      SpeakTemplateConfigChecker checker = new SpeakTemplateConfigChecker();
      List<SpeakTemplateConfig> newList = new ArrayList<>();
      Map<Integer, SpeakTemplateConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          int parsedField1 = 0;
          int parsedField2 = 0;
          String parsedField3 = null;
          String parsedField4 = null;
          int parsedField5 = 0;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("templateId"), fileName + ":行" + lineNumber + ":字段 templateId");
            parsedField2 = ly.ConfigValues.read(type2, values.get("standId"), fileName + ":行" + lineNumber + ":字段 standId");
            parsedField3 = ly.ConfigValues.read(type3, values.get("mosterName"), fileName + ":行" + lineNumber + ":字段 mosterName");
            parsedField4 = ly.ConfigValues.read(type4, values.get("content"), fileName + ":行" + lineNumber + ":字段 content");
            parsedField5 = ly.ConfigValues.read(type5, values.get("answer"), fileName + ":行" + lineNumber + ":字段 answer");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          SpeakTemplateConfig config = new SpeakTemplateConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (SpeakTemplateConfig config : configList) config.afterLoad();
      new SpeakTemplateConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<SpeakTemplateConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, SpeakTemplateConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "speakTemplate.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
