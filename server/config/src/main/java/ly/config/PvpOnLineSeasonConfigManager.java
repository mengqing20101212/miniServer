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
 * File: PvpOnLineSeasonConfigManager
 */
public class PvpOnLineSeasonConfigManager implements InterfaceConfigManagerProxy {
  public static PvpOnLineSeasonConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(PvpOnLineSeasonConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new PvpOnLineSeasonConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "pvpOnLineSeason.xml"; }

  public static class PvpOnLineSeasonConfigManagerImpl extends AbstractConfigManger {
    private List<PvpOnLineSeasonConfig> configList = List.of();
    private Map<Integer, PvpOnLineSeasonConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type5 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type7 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type8 = ly.ConfigValueType.parse("string");
      String fileName = configDir + File.separator + getConfigFileName();
      PvpOnLineSeasonConfigChecker checker = new PvpOnLineSeasonConfigChecker();
      List<PvpOnLineSeasonConfig> newList = new ArrayList<>();
      Map<Integer, PvpOnLineSeasonConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          int parsedField1 = 0;
          int parsedField2 = 0;
          String parsedField3 = null;
          int parsedField4 = 0;
          int parsedField5 = 0;
          int parsedField6 = 0;
          int parsedField7 = 0;
          String parsedField8 = null;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("duanBig"), fileName + ":行" + lineNumber + ":字段 duanBig");
            parsedField2 = ly.ConfigValues.read(type2, values.get("duanSmall"), fileName + ":行" + lineNumber + ":字段 duanSmall");
            parsedField3 = ly.ConfigValues.read(type3, values.get("duan"), fileName + ":行" + lineNumber + ":字段 duan");
            parsedField4 = ly.ConfigValues.read(type4, values.get("duanReset"), fileName + ":行" + lineNumber + ":字段 duanReset");
            parsedField5 = ly.ConfigValues.read(type5, values.get("eloPercentage"), fileName + ":行" + lineNumber + ":字段 eloPercentage");
            parsedField6 = ly.ConfigValues.read(type6, values.get("eloFixedValue"), fileName + ":行" + lineNumber + ":字段 eloFixedValue");
            parsedField7 = ly.ConfigValues.read(type7, values.get("awardSeason"), fileName + ":行" + lineNumber + ":字段 awardSeason");
            parsedField8 = ly.ConfigValues.read(type8, values.get("awardSeasonPre"), fileName + ":行" + lineNumber + ":字段 awardSeasonPre");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          PvpOnLineSeasonConfig config = new PvpOnLineSeasonConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (PvpOnLineSeasonConfig config : configList) config.afterLoad();
      new PvpOnLineSeasonConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<PvpOnLineSeasonConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, PvpOnLineSeasonConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "pvpOnLineSeason.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
