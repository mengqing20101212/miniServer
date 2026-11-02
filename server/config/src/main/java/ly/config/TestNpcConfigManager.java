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
 * File: TestNpcConfigManager
 */
public class TestNpcConfigManager implements InterfaceConfigManagerProxy {
  public static TestNpcConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(TestNpcConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new TestNpcConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "testNpc.xml"; }

  public static class TestNpcConfigManagerImpl extends AbstractConfigManger {
    private List<TestNpcConfig> configList = List.of();
    private Map<Integer, TestNpcConfig> configMap = Map.of();

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
      ly.ConfigValueType type9 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type10 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type11 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type12 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type13 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type14 = ly.ConfigValueType.parse("int");
      String fileName = configDir + File.separator + getConfigFileName();
      TestNpcConfigChecker checker = new TestNpcConfigChecker();
      List<TestNpcConfig> newList = new ArrayList<>();
      Map<Integer, TestNpcConfig> newMap = new HashMap<>();
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
          int parsedField9 = 0;
          int parsedField10 = 0;
          String parsedField11 = null;
          String parsedField12 = null;
          String parsedField13 = null;
          int parsedField14 = 0;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("name"), fileName + ":行" + lineNumber + ":字段 name");
            parsedField2 = ly.ConfigValues.read(type2, values.get("level"), fileName + ":行" + lineNumber + ":字段 level");
            parsedField3 = ly.ConfigValues.read(type3, values.get("maxHP"), fileName + ":行" + lineNumber + ":字段 maxHP");
            parsedField4 = ly.ConfigValues.read(type4, values.get("attack"), fileName + ":行" + lineNumber + ":字段 attack");
            parsedField5 = ly.ConfigValues.read(type5, values.get("defence"), fileName + ":行" + lineNumber + ":字段 defence");
            parsedField6 = ly.ConfigValues.read(type6, values.get("speed"), fileName + ":行" + lineNumber + ":字段 speed");
            parsedField7 = ly.ConfigValues.read(type7, values.get("crit"), fileName + ":行" + lineNumber + ":字段 crit");
            parsedField8 = ly.ConfigValues.read(type8, values.get("critRatio"), fileName + ":行" + lineNumber + ":字段 critRatio");
            parsedField9 = ly.ConfigValues.read(type9, values.get("effectHit"), fileName + ":行" + lineNumber + ":字段 effectHit");
            parsedField10 = ly.ConfigValues.read(type10, values.get("effectDodge"), fileName + ":行" + lineNumber + ":字段 effectDodge");
            parsedField11 = ly.ConfigValues.read(type11, values.get("skillList"), fileName + ":行" + lineNumber + ":字段 skillList");
            parsedField12 = ly.ConfigValues.read(type12, values.get("Sskill"), fileName + ":行" + lineNumber + ":字段 Sskill");
            parsedField13 = ly.ConfigValues.read(type13, values.get("aiName"), fileName + ":行" + lineNumber + ":字段 aiName");
            parsedField14 = ly.ConfigValues.read(type14, values.get("modelId"), fileName + ":行" + lineNumber + ":字段 modelId");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          TestNpcConfig config = new TestNpcConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8, parsedField9, parsedField10, parsedField11, parsedField12, parsedField13, parsedField14);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (TestNpcConfig config : configList) config.afterLoad();
      new TestNpcConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<TestNpcConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, TestNpcConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "testNpc.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
