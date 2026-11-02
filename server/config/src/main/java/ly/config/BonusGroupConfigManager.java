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
 * File: BonusGroupConfigManager
 */
public class BonusGroupConfigManager implements InterfaceConfigManagerProxy {
  public static BonusGroupConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(BonusGroupConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new BonusGroupConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "bonusGroup.xml"; }

  public static class BonusGroupConfigManagerImpl extends AbstractConfigManger {
    private List<BonusGroupConfig> configList = List.of();
    private Map<Integer, BonusGroupConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type5 = ly.ConfigValueType.parse("string");
      String fileName = configDir + File.separator + getConfigFileName();
      BonusGroupConfigChecker checker = new BonusGroupConfigChecker();
      List<BonusGroupConfig> newList = new ArrayList<>();
      Map<Integer, BonusGroupConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          String parsedField1 = null;
          String parsedField2 = null;
          String parsedField3 = null;
          String parsedField4 = null;
          String parsedField5 = null;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("bonusList"), fileName + ":行" + lineNumber + ":字段 bonusList");
            parsedField2 = ly.ConfigValues.read(type2, values.get("bonusPro"), fileName + ":行" + lineNumber + ":字段 bonusPro");
            parsedField3 = ly.ConfigValues.read(type3, values.get("bonusTimes"), fileName + ":行" + lineNumber + ":字段 bonusTimes");
            parsedField4 = ly.ConfigValues.read(type4, values.get("bonusRelativePro"), fileName + ":行" + lineNumber + ":字段 bonusRelativePro");
            parsedField5 = ly.ConfigValues.read(type5, values.get("beizhu"), fileName + ":行" + lineNumber + ":字段 beizhu");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          BonusGroupConfig config = new BonusGroupConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (BonusGroupConfig config : configList) config.afterLoad();
      new BonusGroupConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<BonusGroupConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, BonusGroupConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "bonusGroup.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
