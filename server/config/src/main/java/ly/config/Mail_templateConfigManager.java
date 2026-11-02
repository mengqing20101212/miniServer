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
 * File: Mail_templateConfigManager
 */
public class Mail_templateConfigManager implements InterfaceConfigManagerProxy {
  public static Mail_templateConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(Mail_templateConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new Mail_templateConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "mail_template.xml"; }

  public static class Mail_templateConfigManagerImpl extends AbstractConfigManger {
    private List<Mail_templateConfig> configList = List.of();
    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type5 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type7 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type8 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type9 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type10 = ly.ConfigValueType.parse("string");
      String fileName = configDir + File.separator + getConfigFileName();
      Mail_templateConfigChecker checker = new Mail_templateConfigChecker();
      List<Mail_templateConfig> newList = new ArrayList<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          String parsedField1 = null;
          int parsedField2 = 0;
          int parsedField3 = 0;
          int parsedField4 = 0;
          String parsedField5 = null;
          int parsedField6 = 0;
          String parsedField7 = null;
          String parsedField8 = null;
          String parsedField9 = null;
          String parsedField10 = null;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("icon"), fileName + ":行" + lineNumber + ":字段 icon");
            parsedField1 = ly.ConfigValues.read(type1, values.get("des"), fileName + ":行" + lineNumber + ":字段 des");
            parsedField2 = ly.ConfigValues.read(type2, values.get("part_begin"), fileName + ":行" + lineNumber + ":字段 part_begin");
            parsedField3 = ly.ConfigValues.read(type3, values.get("part_end"), fileName + ":行" + lineNumber + ":字段 part_end");
            parsedField4 = ly.ConfigValues.read(type4, values.get("type"), fileName + ":行" + lineNumber + ":字段 type");
            parsedField5 = ly.ConfigValues.read(type5, values.get("stamp"), fileName + ":行" + lineNumber + ":字段 stamp");
            parsedField6 = ly.ConfigValues.read(type6, values.get("valid_time"), fileName + ":行" + lineNumber + ":字段 valid_time");
            parsedField7 = ly.ConfigValues.read(type7, values.get("sender"), fileName + ":行" + lineNumber + ":字段 sender");
            parsedField8 = ly.ConfigValues.read(type8, values.get("titile"), fileName + ":行" + lineNumber + ":字段 titile");
            parsedField9 = ly.ConfigValues.read(type9, values.get("content"), fileName + ":行" + lineNumber + ":字段 content");
            parsedField10 = ly.ConfigValues.read(type10, values.get("attach"), fileName + ":行" + lineNumber + ":字段 attach");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          Mail_templateConfig config = new Mail_templateConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8, parsedField9, parsedField10);
          newList.add(config);
      });
      configList = List.copyOf(newList);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (Mail_templateConfig config : configList) config.afterLoad();
      new Mail_templateConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<Mail_templateConfig> getConfigList() {
      return configList;
    }

    @Override
    public String getConfigFileName() {
      return "mail_template.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
