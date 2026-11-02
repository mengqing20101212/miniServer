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
 * File: ContactEventConfigManager
 */
public class ContactEventConfigManager implements InterfaceConfigManagerProxy {
  public static ContactEventConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(ContactEventConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new ContactEventConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "contactEvent.xml"; }

  public static class ContactEventConfigManagerImpl extends AbstractConfigManger {
    private List<ContactEventConfig> configList = List.of();
    private Map<Integer, ContactEventConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type5 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type7 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type8 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type9 = ly.ConfigValueType.parse("int");
      String fileName = configDir + File.separator + getConfigFileName();
      ContactEventConfigChecker checker = new ContactEventConfigChecker();
      List<ContactEventConfig> newList = new ArrayList<>();
      Map<Integer, ContactEventConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          int parsedField1 = 0;
          String parsedField2 = null;
          int parsedField3 = 0;
          String parsedField4 = null;
          String parsedField5 = null;
          String parsedField6 = null;
          int parsedField7 = 0;
          int parsedField8 = 0;
          int parsedField9 = 0;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("type"), fileName + ":行" + lineNumber + ":字段 type");
            parsedField2 = ly.ConfigValues.read(type2, values.get("beizhu"), fileName + ":行" + lineNumber + ":字段 beizhu");
            parsedField3 = ly.ConfigValues.read(type3, values.get("para"), fileName + ":行" + lineNumber + ":字段 para");
            parsedField4 = ly.ConfigValues.read(type4, values.get("startDialog"), fileName + ":行" + lineNumber + ":字段 startDialog");
            parsedField5 = ly.ConfigValues.read(type5, values.get("rightDialog"), fileName + ":行" + lineNumber + ":字段 rightDialog");
            parsedField6 = ly.ConfigValues.read(type6, values.get("wrongDialog"), fileName + ":行" + lineNumber + ":字段 wrongDialog");
            parsedField7 = ly.ConfigValues.read(type7, values.get("dropSuccess"), fileName + ":行" + lineNumber + ":字段 dropSuccess");
            parsedField8 = ly.ConfigValues.read(type8, values.get("dropFail"), fileName + ":行" + lineNumber + ":字段 dropFail");
            parsedField9 = ly.ConfigValues.read(type9, values.get("basicMail"), fileName + ":行" + lineNumber + ":字段 basicMail");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          ContactEventConfig config = new ContactEventConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8, parsedField9);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (ContactEventConfig config : configList) config.afterLoad();
      new ContactEventConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<ContactEventConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, ContactEventConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "contactEvent.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
