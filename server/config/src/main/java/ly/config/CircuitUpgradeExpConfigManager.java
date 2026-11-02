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
 * File: CircuitUpgradeExpConfigManager
 */
public class CircuitUpgradeExpConfigManager implements InterfaceConfigManagerProxy {
  public static CircuitUpgradeExpConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(CircuitUpgradeExpConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new CircuitUpgradeExpConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "circuitUpgradeExp.xml"; }

  public static class CircuitUpgradeExpConfigManagerImpl extends AbstractConfigManger {
    private List<CircuitUpgradeExpConfig> configList = List.of();
    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type5 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("int");
      String fileName = configDir + File.separator + getConfigFileName();
      CircuitUpgradeExpConfigChecker checker = new CircuitUpgradeExpConfigChecker();
      List<CircuitUpgradeExpConfig> newList = new ArrayList<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          int parsedField1 = 0;
          int parsedField2 = 0;
          int parsedField3 = 0;
          int parsedField4 = 0;
          int parsedField5 = 0;
          int parsedField6 = 0;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("upgradeType"), fileName + ":行" + lineNumber + ":字段 upgradeType");
            parsedField1 = ly.ConfigValues.read(type1, values.get("level"), fileName + ":行" + lineNumber + ":字段 level");
            parsedField2 = ly.ConfigValues.read(type2, values.get("exp"), fileName + ":行" + lineNumber + ":字段 exp");
            parsedField3 = ly.ConfigValues.read(type3, values.get("eatExp"), fileName + ":行" + lineNumber + ":字段 eatExp");
            parsedField4 = ly.ConfigValues.read(type4, values.get("consumeGold"), fileName + ":行" + lineNumber + ":字段 consumeGold");
            parsedField5 = ly.ConfigValues.read(type5, values.get("newSubAttrProb"), fileName + ":行" + lineNumber + ":字段 newSubAttrProb");
            parsedField6 = ly.ConfigValues.read(type6, values.get("upgrageGold"), fileName + ":行" + lineNumber + ":字段 upgrageGold");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          CircuitUpgradeExpConfig config = new CircuitUpgradeExpConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6);
          newList.add(config);
      });
      configList = List.copyOf(newList);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (CircuitUpgradeExpConfig config : configList) config.afterLoad();
      new CircuitUpgradeExpConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<CircuitUpgradeExpConfig> getConfigList() {
      return configList;
    }

    @Override
    public String getConfigFileName() {
      return "circuitUpgradeExp.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
