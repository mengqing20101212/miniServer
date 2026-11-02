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
 * File: WhiteaccountConfigManager
 */
public class WhiteaccountConfigManager implements InterfaceConfigManagerProxy {
  public static WhiteaccountConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(WhiteaccountConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new WhiteaccountConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "whiteaccount.xml"; }

  public static class WhiteaccountConfigManagerImpl extends AbstractConfigManger {
    private List<WhiteaccountConfig> configList = List.of();
    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      String fileName = configDir + File.separator + getConfigFileName();
      WhiteaccountConfigChecker checker = new WhiteaccountConfigChecker();
      List<WhiteaccountConfig> newList = new ArrayList<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          try {
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          WhiteaccountConfig config = new WhiteaccountConfig();
          newList.add(config);
      });
      configList = List.copyOf(newList);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (WhiteaccountConfig config : configList) config.afterLoad();
      new WhiteaccountConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<WhiteaccountConfig> getConfigList() {
      return configList;
    }

    @Override
    public String getConfigFileName() {
      return "whiteaccount.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
