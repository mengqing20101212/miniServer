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
 * File: PlayerExpConfigManager
 */
public class PlayerExpConfigManager implements InterfaceConfigManagerProxy {
  public static PlayerExpConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(PlayerExpConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new PlayerExpConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "playerExp.xml"; }

  public static class PlayerExpConfigManagerImpl extends AbstractConfigManger {
    private List<PlayerExpConfig> configList = List.of();
    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("int");
      String fileName = configDir + File.separator + getConfigFileName();
      PlayerExpConfigChecker checker = new PlayerExpConfigChecker();
      List<PlayerExpConfig> newList = new ArrayList<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          int parsedField1 = 0;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("level"), fileName + ":行" + lineNumber + ":字段 level");
            parsedField1 = ly.ConfigValues.read(type1, values.get("exp"), fileName + ":行" + lineNumber + ":字段 exp");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          PlayerExpConfig config = new PlayerExpConfig(parsedField0, parsedField1);
          newList.add(config);
      });
      configList = List.copyOf(newList);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (PlayerExpConfig config : configList) config.afterLoad();
      new PlayerExpConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<PlayerExpConfig> getConfigList() {
      return configList;
    }

    @Override
    public String getConfigFileName() {
      return "playerExp.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
