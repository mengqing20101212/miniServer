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
 * File: Random_nameConfigManager
 */
public class Random_nameConfigManager implements InterfaceConfigManagerProxy {
  public static Random_nameConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(Random_nameConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new Random_nameConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "random_name.xml"; }

  public static class Random_nameConfigManagerImpl extends AbstractConfigManger {
    private List<Random_nameConfig> configList = List.of();
    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("string");
      String fileName = configDir + File.separator + getConfigFileName();
      Random_nameConfigChecker checker = new Random_nameConfigChecker();
      List<Random_nameConfig> newList = new ArrayList<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          String parsedField0 = null;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("name2"), fileName + ":行" + lineNumber + ":字段 name2");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          Random_nameConfig config = new Random_nameConfig(parsedField0);
          newList.add(config);
      });
      configList = List.copyOf(newList);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (Random_nameConfig config : configList) config.afterLoad();
      new Random_nameConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<Random_nameConfig> getConfigList() {
      return configList;
    }

    @Override
    public String getConfigFileName() {
      return "random_name.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
