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
 * File: Channel_typeConfigManager
 */
public class Channel_typeConfigManager implements InterfaceConfigManagerProxy {
  public static Channel_typeConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(Channel_typeConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new Channel_typeConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "channel_type.xml"; }

  public static class Channel_typeConfigManagerImpl extends AbstractConfigManger {
    private List<Channel_typeConfig> configList = List.of();
    private Map<Integer, Channel_typeConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type5 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type7 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type8 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type9 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type10 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type11 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type12 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type13 = ly.ConfigValueType.parse("int");
      String fileName = configDir + File.separator + getConfigFileName();
      Channel_typeConfigChecker checker = new Channel_typeConfigChecker();
      List<Channel_typeConfig> newList = new ArrayList<>();
      Map<Integer, Channel_typeConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          String parsedField1 = null;
          int parsedField2 = 0;
          String parsedField3 = null;
          String parsedField4 = null;
          String parsedField5 = null;
          int parsedField6 = 0;
          int parsedField7 = 0;
          String parsedField8 = null;
          String parsedField9 = null;
          String parsedField10 = null;
          String parsedField11 = null;
          String parsedField12 = null;
          int parsedField13 = 0;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("spname"), fileName + ":行" + lineNumber + ":字段 spname");
            parsedField2 = ly.ConfigValues.read(type2, values.get("spid"), fileName + ":行" + lineNumber + ":字段 spid");
            parsedField3 = ly.ConfigValues.read(type3, values.get("spcode"), fileName + ":行" + lineNumber + ":字段 spcode");
            parsedField4 = ly.ConfigValues.read(type4, values.get("spdownloadurl"), fileName + ":行" + lineNumber + ":字段 spdownloadurl");
            parsedField5 = ly.ConfigValues.read(type5, values.get("checkserverip"), fileName + ":行" + lineNumber + ":字段 checkserverip");
            parsedField6 = ly.ConfigValues.read(type6, values.get("checkserverport"), fileName + ":行" + lineNumber + ":字段 checkserverport");
            parsedField7 = ly.ConfigValues.read(type7, values.get("groupid"), fileName + ":行" + lineNumber + ":字段 groupid");
            parsedField8 = ly.ConfigValues.read(type8, values.get("check_login_test_url"), fileName + ":行" + lineNumber + ":字段 check_login_test_url");
            parsedField9 = ly.ConfigValues.read(type9, values.get("check_login_url"), fileName + ":行" + lineNumber + ":字段 check_login_url");
            parsedField10 = ly.ConfigValues.read(type10, values.get("login_interface"), fileName + ":行" + lineNumber + ":字段 login_interface");
            parsedField11 = ly.ConfigValues.read(type11, values.get("appid"), fileName + ":行" + lineNumber + ":字段 appid");
            parsedField12 = ly.ConfigValues.read(type12, values.get("appsec"), fileName + ":行" + lineNumber + ":字段 appsec");
            parsedField13 = ly.ConfigValues.read(type13, values.get("isExteral"), fileName + ":行" + lineNumber + ":字段 isExteral");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          Channel_typeConfig config = new Channel_typeConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8, parsedField9, parsedField10, parsedField11, parsedField12, parsedField13);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (Channel_typeConfig config : configList) config.afterLoad();
      new Channel_typeConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<Channel_typeConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, Channel_typeConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "channel_type.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
