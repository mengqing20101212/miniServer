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
 * File: RechargeRebateConfigManager
 */
public class RechargeRebateConfigManager implements InterfaceConfigManagerProxy {
  public static RechargeRebateConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(RechargeRebateConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new RechargeRebateConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "RechargeRebate.xml"; }

  public static class RechargeRebateConfigManagerImpl extends AbstractConfigManger {
    private List<RechargeRebateConfig> configList = List.of();
    private Map<Integer, RechargeRebateConfig> configMap = Map.of();

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
      ly.ConfigValueType type8 = ly.ConfigValueType.parse("int");
      String fileName = configDir + File.separator + getConfigFileName();
      RechargeRebateConfigChecker checker = new RechargeRebateConfigChecker();
      List<RechargeRebateConfig> newList = new ArrayList<>();
      Map<Integer, RechargeRebateConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          String parsedField1 = null;
          int parsedField2 = 0;
          int parsedField3 = 0;
          int parsedField4 = 0;
          int parsedField5 = 0;
          int parsedField6 = 0;
          int parsedField7 = 0;
          int parsedField8 = 0;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("openId"), fileName + ":行" + lineNumber + ":字段 openId");
            parsedField2 = ly.ConfigValues.read(type2, values.get("testRecharge_1"), fileName + ":行" + lineNumber + ":字段 testRecharge_1");
            parsedField3 = ly.ConfigValues.read(type3, values.get("testRecharge_2"), fileName + ":行" + lineNumber + ":字段 testRecharge_2");
            parsedField4 = ly.ConfigValues.read(type4, values.get("testRecharge_3"), fileName + ":行" + lineNumber + ":字段 testRecharge_3");
            parsedField5 = ly.ConfigValues.read(type5, values.get("testRecharge_4"), fileName + ":行" + lineNumber + ":字段 testRecharge_4");
            parsedField6 = ly.ConfigValues.read(type6, values.get("testRecharge_5"), fileName + ":行" + lineNumber + ":字段 testRecharge_5");
            parsedField7 = ly.ConfigValues.read(type7, values.get("testRecharge_6"), fileName + ":行" + lineNumber + ":字段 testRecharge_6");
            parsedField8 = ly.ConfigValues.read(type8, values.get("testRecharge_7"), fileName + ":行" + lineNumber + ":字段 testRecharge_7");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          RechargeRebateConfig config = new RechargeRebateConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (RechargeRebateConfig config : configList) config.afterLoad();
      new RechargeRebateConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<RechargeRebateConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, RechargeRebateConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "RechargeRebate.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
