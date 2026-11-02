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
 * File: AssociationTestConfigManager
 */
public class AssociationTestConfigManager implements InterfaceConfigManagerProxy {
  public static AssociationTestConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(AssociationTestConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new AssociationTestConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "associationTest.xml"; }

  public static class AssociationTestConfigManagerImpl extends AbstractConfigManger {
    private List<AssociationTestConfig> configList = List.of();
    private Map<Integer, AssociationTestConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type5 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type7 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type8 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type9 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type10 = ly.ConfigValueType.parse("string");
      String fileName = configDir + File.separator + getConfigFileName();
      AssociationTestConfigChecker checker = new AssociationTestConfigChecker();
      List<AssociationTestConfig> newList = new ArrayList<>();
      Map<Integer, AssociationTestConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          int parsedField1 = 0;
          int parsedField2 = 0;
          String parsedField3 = null;
          String parsedField4 = null;
          String parsedField5 = null;
          String parsedField6 = null;
          String parsedField7 = null;
          int parsedField8 = 0;
          int parsedField9 = 0;
          String parsedField10 = null;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("week"), fileName + ":行" + lineNumber + ":字段 week");
            parsedField2 = ly.ConfigValues.read(type2, values.get("day"), fileName + ":行" + lineNumber + ":字段 day");
            parsedField3 = ly.ConfigValues.read(type3, values.get("heroID"), fileName + ":行" + lineNumber + ":字段 heroID");
            parsedField4 = ly.ConfigValues.read(type4, values.get("beizhu"), fileName + ":行" + lineNumber + ":字段 beizhu");
            parsedField5 = ly.ConfigValues.read(type5, values.get("dropSuccess"), fileName + ":行" + lineNumber + ":字段 dropSuccess");
            parsedField6 = ly.ConfigValues.read(type6, values.get("dropFail"), fileName + ":行" + lineNumber + ":字段 dropFail");
            parsedField7 = ly.ConfigValues.read(type7, values.get("dropShow"), fileName + ":行" + lineNumber + ":字段 dropShow");
            parsedField8 = ly.ConfigValues.read(type8, values.get("testTimes"), fileName + ":行" + lineNumber + ":字段 testTimes");
            parsedField9 = ly.ConfigValues.read(type9, values.get("stamina"), fileName + ":行" + lineNumber + ":字段 stamina");
            parsedField10 = ly.ConfigValues.read(type10, values.get("questionPool"), fileName + ":行" + lineNumber + ":字段 questionPool");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          AssociationTestConfig config = new AssociationTestConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8, parsedField9, parsedField10);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (AssociationTestConfig config : configList) config.afterLoad();
      new AssociationTestConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<AssociationTestConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, AssociationTestConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "associationTest.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
