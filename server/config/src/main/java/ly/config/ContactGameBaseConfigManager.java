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
 * File: ContactGameBaseConfigManager
 */
public class ContactGameBaseConfigManager implements InterfaceConfigManagerProxy {
  public static ContactGameBaseConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(ContactGameBaseConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new ContactGameBaseConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "contactGameBase.xml"; }

  public static class ContactGameBaseConfigManagerImpl extends AbstractConfigManger {
    private List<ContactGameBaseConfig> configList = List.of();
    private Map<Integer, ContactGameBaseConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("string");
      String fileName = configDir + File.separator + getConfigFileName();
      ContactGameBaseConfigChecker checker = new ContactGameBaseConfigChecker();
      List<ContactGameBaseConfig> newList = new ArrayList<>();
      Map<Integer, ContactGameBaseConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          String parsedField1 = null;
          int parsedField2 = 0;
          String parsedField3 = null;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("name"), fileName + ":行" + lineNumber + ":字段 name");
            parsedField2 = ly.ConfigValues.read(type2, values.get("resource"), fileName + ":行" + lineNumber + ":字段 resource");
            parsedField3 = ly.ConfigValues.read(type3, values.get("tag"), fileName + ":行" + lineNumber + ":字段 tag");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          ContactGameBaseConfig config = new ContactGameBaseConfig(parsedField0, parsedField1, parsedField2, parsedField3);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (ContactGameBaseConfig config : configList) config.afterLoad();
      new ContactGameBaseConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<ContactGameBaseConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, ContactGameBaseConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "contactGameBase.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
