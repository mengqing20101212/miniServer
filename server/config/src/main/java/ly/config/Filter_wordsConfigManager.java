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
 * File: Filter_wordsConfigManager
 */
public class Filter_wordsConfigManager implements InterfaceConfigManagerProxy {
  public static Filter_wordsConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(Filter_wordsConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new Filter_wordsConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "filter_words.xml"; }

  public static class Filter_wordsConfigManagerImpl extends AbstractConfigManger {
    private List<Filter_wordsConfig> configList = List.of();
    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      String fileName = configDir + File.separator + getConfigFileName();
      Filter_wordsConfigChecker checker = new Filter_wordsConfigChecker();
      List<Filter_wordsConfig> newList = new ArrayList<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("flag"), fileName + ":行" + lineNumber + ":字段 flag");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          Filter_wordsConfig config = new Filter_wordsConfig(parsedField0);
          newList.add(config);
      });
      configList = List.copyOf(newList);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (Filter_wordsConfig config : configList) config.afterLoad();
      new Filter_wordsConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<Filter_wordsConfig> getConfigList() {
      return configList;
    }

    @Override
    public String getConfigFileName() {
      return "filter_words.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
