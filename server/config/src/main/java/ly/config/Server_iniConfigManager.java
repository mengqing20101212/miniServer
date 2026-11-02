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
 * File: Server_iniConfigManager
 */
public class Server_iniConfigManager implements InterfaceConfigManagerProxy {
  public static Server_iniConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(Server_iniConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new Server_iniConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "server_ini.xml"; }

  public static class Server_iniConfigManagerImpl extends AbstractConfigManger {
    private List<Server_iniConfig> configList = List.of();
    private Map<Integer, Server_iniConfig> configMap = Map.of();

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
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type7 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type8 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type9 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type10 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type11 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type12 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type13 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type14 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type15 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type16 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type17 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type18 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type19 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type20 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type21 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type22 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type23 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type24 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type25 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type26 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type27 = ly.ConfigValueType.parse("string");
      String fileName = configDir + File.separator + getConfigFileName();
      Server_iniConfigChecker checker = new Server_iniConfigChecker();
      List<Server_iniConfig> newList = new ArrayList<>();
      Map<Integer, Server_iniConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          String parsedField1 = null;
          String parsedField2 = null;
          String parsedField3 = null;
          String parsedField4 = null;
          String parsedField5 = null;
          String parsedField6 = null;
          String parsedField7 = null;
          String parsedField8 = null;
          String parsedField9 = null;
          String parsedField10 = null;
          String parsedField11 = null;
          String parsedField12 = null;
          String parsedField13 = null;
          String parsedField14 = null;
          String parsedField15 = null;
          String parsedField16 = null;
          String parsedField17 = null;
          String parsedField18 = null;
          String parsedField19 = null;
          String parsedField20 = null;
          String parsedField21 = null;
          String parsedField22 = null;
          String parsedField23 = null;
          String parsedField24 = null;
          String parsedField25 = null;
          String parsedField26 = null;
          String parsedField27 = null;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("name"), fileName + ":行" + lineNumber + ":字段 name");
            parsedField2 = ly.ConfigValues.read(type2, values.get("des"), fileName + ":行" + lineNumber + ":字段 des");
            parsedField3 = ly.ConfigValues.read(type3, values.get("dev_default"), fileName + ":行" + lineNumber + ":字段 dev_default");
            parsedField4 = ly.ConfigValues.read(type4, values.get("a01_default"), fileName + ":行" + lineNumber + ":字段 a01_default");
            parsedField5 = ly.ConfigValues.read(type5, values.get("b02_default"), fileName + ":行" + lineNumber + ":字段 b02_default");
            parsedField6 = ly.ConfigValues.read(type6, values.get("c03_default"), fileName + ":行" + lineNumber + ":字段 c03_default");
            parsedField7 = ly.ConfigValues.read(type7, values.get("d04_default"), fileName + ":行" + lineNumber + ":字段 d04_default");
            parsedField8 = ly.ConfigValues.read(type8, values.get("e05_default"), fileName + ":行" + lineNumber + ":字段 e05_default");
            parsedField9 = ly.ConfigValues.read(type9, values.get("f06_default"), fileName + ":行" + lineNumber + ":字段 f06_default");
            parsedField10 = ly.ConfigValues.read(type10, values.get("g07_default"), fileName + ":行" + lineNumber + ":字段 g07_default");
            parsedField11 = ly.ConfigValues.read(type11, values.get("h08_default"), fileName + ":行" + lineNumber + ":字段 h08_default");
            parsedField12 = ly.ConfigValues.read(type12, values.get("i09_default"), fileName + ":行" + lineNumber + ":字段 i09_default");
            parsedField13 = ly.ConfigValues.read(type13, values.get("weekly_multinode"), fileName + ":行" + lineNumber + ":字段 weekly_multinode");
            parsedField14 = ly.ConfigValues.read(type14, values.get("korea_dev_default"), fileName + ":行" + lineNumber + ":字段 korea_dev_default");
            parsedField15 = ly.ConfigValues.read(type15, values.get("korea_weekly_default"), fileName + ":行" + lineNumber + ":字段 korea_weekly_default");
            parsedField16 = ly.ConfigValues.read(type16, values.get("korea_gray_default"), fileName + ":行" + lineNumber + ":字段 korea_gray_default");
            parsedField17 = ly.ConfigValues.read(type17, values.get("tencent_dev1_default"), fileName + ":行" + lineNumber + ":字段 tencent_dev1_default");
            parsedField18 = ly.ConfigValues.read(type18, values.get("tencent_dev2_default"), fileName + ":行" + lineNumber + ":字段 tencent_dev2_default");
            parsedField19 = ly.ConfigValues.read(type19, values.get("tencent_dev3_default"), fileName + ":行" + lineNumber + ":字段 tencent_dev3_default");
            parsedField20 = ly.ConfigValues.read(type20, values.get("tencent_dev4_default"), fileName + ":行" + lineNumber + ":字段 tencent_dev4_default");
            parsedField21 = ly.ConfigValues.read(type21, values.get("tencent_dev5_default"), fileName + ":行" + lineNumber + ":字段 tencent_dev5_default");
            parsedField22 = ly.ConfigValues.read(type22, values.get("tencent_gray_default"), fileName + ":行" + lineNumber + ":字段 tencent_gray_default");
            parsedField23 = ly.ConfigValues.read(type23, values.get("tencent_official_default"), fileName + ":行" + lineNumber + ":字段 tencent_official_default");
            parsedField24 = ly.ConfigValues.read(type24, values.get("tencent_ios_cert_default"), fileName + ":行" + lineNumber + ":字段 tencent_ios_cert_default");
            parsedField25 = ly.ConfigValues.read(type25, values.get("tencent_release_default"), fileName + ":行" + lineNumber + ":字段 tencent_release_default");
            parsedField26 = ly.ConfigValues.read(type26, values.get("tencent_stress_test_default"), fileName + ":行" + lineNumber + ":字段 tencent_stress_test_default");
            parsedField27 = ly.ConfigValues.read(type27, values.get("tencent_idc_test_default"), fileName + ":行" + lineNumber + ":字段 tencent_idc_test_default");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          Server_iniConfig config = new Server_iniConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8, parsedField9, parsedField10, parsedField11, parsedField12, parsedField13, parsedField14, parsedField15, parsedField16, parsedField17, parsedField18, parsedField19, parsedField20, parsedField21, parsedField22, parsedField23, parsedField24, parsedField25, parsedField26, parsedField27);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (Server_iniConfig config : configList) config.afterLoad();
      new Server_iniConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<Server_iniConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, Server_iniConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "server_ini.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
