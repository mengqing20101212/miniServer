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
 * File: PeakCompetitionAssemblyConfigManager
 */
public class PeakCompetitionAssemblyConfigManager implements InterfaceConfigManagerProxy {
  public static PeakCompetitionAssemblyConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(PeakCompetitionAssemblyConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new PeakCompetitionAssemblyConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "peakCompetitionAssembly.xml"; }

  public static class PeakCompetitionAssemblyConfigManagerImpl extends AbstractConfigManger {
    private List<PeakCompetitionAssemblyConfig> configList = List.of();
    private Map<Integer, PeakCompetitionAssemblyConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type5 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type7 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type8 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type9 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type10 = ly.ConfigValueType.parse("string");
      String fileName = configDir + File.separator + getConfigFileName();
      PeakCompetitionAssemblyConfigChecker checker = new PeakCompetitionAssemblyConfigChecker();
      List<PeakCompetitionAssemblyConfig> newList = new ArrayList<>();
      Map<Integer, PeakCompetitionAssemblyConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          String parsedField1 = null;
          int parsedField2 = 0;
          int parsedField3 = 0;
          int parsedField4 = 0;
          int parsedField5 = 0;
          int parsedField6 = 0;
          int parsedField7 = 0;
          String parsedField8 = null;
          int parsedField9 = 0;
          String parsedField10 = null;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("name"), fileName + ":行" + lineNumber + ":字段 name");
            parsedField2 = ly.ConfigValues.read(type2, values.get("type"), fileName + ":行" + lineNumber + ":字段 type");
            parsedField3 = ly.ConfigValues.read(type3, values.get("skillID"), fileName + ":行" + lineNumber + ":字段 skillID");
            parsedField4 = ly.ConfigValues.read(type4, values.get("attrType"), fileName + ":行" + lineNumber + ":字段 attrType");
            parsedField5 = ly.ConfigValues.read(type5, values.get("attrNum"), fileName + ":行" + lineNumber + ":字段 attrNum");
            parsedField6 = ly.ConfigValues.read(type6, values.get("heroAdvance"), fileName + ":行" + lineNumber + ":字段 heroAdvance");
            parsedField7 = ly.ConfigValues.read(type7, values.get("skillLv"), fileName + ":行" + lineNumber + ":字段 skillLv");
            parsedField8 = ly.ConfigValues.read(type8, values.get("quality"), fileName + ":行" + lineNumber + ":字段 quality");
            parsedField9 = ly.ConfigValues.read(type9, values.get("limit"), fileName + ":行" + lineNumber + ":字段 limit");
            parsedField10 = ly.ConfigValues.read(type10, values.get("description"), fileName + ":行" + lineNumber + ":字段 description");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          PeakCompetitionAssemblyConfig config = new PeakCompetitionAssemblyConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8, parsedField9, parsedField10);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (PeakCompetitionAssemblyConfig config : configList) config.afterLoad();
      new PeakCompetitionAssemblyConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<PeakCompetitionAssemblyConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, PeakCompetitionAssemblyConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "peakCompetitionAssembly.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
