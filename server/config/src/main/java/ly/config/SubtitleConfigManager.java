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
 * File: SubtitleConfigManager
 */
public class SubtitleConfigManager implements InterfaceConfigManagerProxy {
  public static SubtitleConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(SubtitleConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new SubtitleConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "subtitle.xml"; }

  public static class SubtitleConfigManagerImpl extends AbstractConfigManger {
    private List<SubtitleConfig> configList = List.of();
    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("list<int>");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("float");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("float");
      ly.ConfigValueType type5 = ly.ConfigValueType.parse("string");
      String fileName = configDir + File.separator + getConfigFileName();
      SubtitleConfigChecker checker = new SubtitleConfigChecker();
      List<SubtitleConfig> newList = new ArrayList<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          String parsedField0 = null;
          int parsedField1 = 0;
          java.util.List<Integer> parsedField2 = null;
          float parsedField3 = 0F;
          float parsedField4 = 0F;
          String parsedField5 = null;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("name"), fileName + ":行" + lineNumber + ":字段 name");
            parsedField1 = ly.ConfigValues.read(type1, values.get("index"), fileName + ":行" + lineNumber + ":字段 index");
            parsedField2 = ly.ConfigValues.read(type2, values.get("style"), fileName + ":行" + lineNumber + ":字段 style");
            parsedField3 = ly.ConfigValues.read(type3, values.get("start_time"), fileName + ":行" + lineNumber + ":字段 start_time");
            parsedField4 = ly.ConfigValues.read(type4, values.get("end_time"), fileName + ":行" + lineNumber + ":字段 end_time");
            parsedField5 = ly.ConfigValues.read(type5, values.get("content"), fileName + ":行" + lineNumber + ":字段 content");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          SubtitleConfig config = new SubtitleConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5);
          newList.add(config);
      });
      configList = List.copyOf(newList);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (SubtitleConfig config : configList) config.afterLoad();
      new SubtitleConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<SubtitleConfig> getConfigList() {
      return configList;
    }

    @Override
    public String getConfigFileName() {
      return "subtitle.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
