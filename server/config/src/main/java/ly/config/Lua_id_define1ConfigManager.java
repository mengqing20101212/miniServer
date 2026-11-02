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
 * File: Lua_id_define1ConfigManager
 */
public class Lua_id_define1ConfigManager implements InterfaceConfigManagerProxy {
  public static Lua_id_define1ConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(Lua_id_define1ConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new Lua_id_define1ConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "lua_id_define1.xml"; }

  public static class Lua_id_define1ConfigManagerImpl extends AbstractConfigManger {
    private List<Lua_id_define1Config> configList = List.of();
    private Map<String, Lua_id_define1Config> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("int");
      String fileName = configDir + File.separator + getConfigFileName();
      Lua_id_define1ConfigChecker checker = new Lua_id_define1ConfigChecker();
      List<Lua_id_define1Config> newList = new ArrayList<>();
      Map<String, Lua_id_define1Config> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          String parsedField0 = null;
          String parsedField1 = null;
          int parsedField2 = 0;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("desc"), fileName + ":行" + lineNumber + ":字段 desc");
            parsedField2 = ly.ConfigValues.read(type2, values.get("resid"), fileName + ":行" + lineNumber + ":字段 resid");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          Lua_id_define1Config config = new Lua_id_define1Config(parsedField0, parsedField1, parsedField2);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (Lua_id_define1Config config : configList) config.afterLoad();
      new Lua_id_define1ConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<Lua_id_define1Config> getConfigList() {
      return configList;
    }

    public Map<String, Lua_id_define1Config> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "lua_id_define1.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
