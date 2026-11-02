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
 * File: TrialawardConfigManager
 */
public class TrialawardConfigManager implements InterfaceConfigManagerProxy {
  public static TrialawardConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(TrialawardConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new TrialawardConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "trialaward.xml"; }

  public static class TrialawardConfigManagerImpl extends AbstractConfigManger {
    private List<TrialawardConfig> configList = List.of();
    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("int");
      String fileName = configDir + File.separator + getConfigFileName();
      TrialawardConfigChecker checker = new TrialawardConfigChecker();
      List<TrialawardConfig> newList = new ArrayList<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          String parsedField0 = null;
          int parsedField1 = 0;
          int parsedField2 = 0;
          int parsedField3 = 0;
          int parsedField4 = 0;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("desc"), fileName + ":行" + lineNumber + ":字段 desc");
            parsedField1 = ly.ConfigValues.read(type1, values.get("type"), fileName + ":行" + lineNumber + ":字段 type");
            parsedField2 = ly.ConfigValues.read(type2, values.get("condition"), fileName + ":行" + lineNumber + ":字段 condition");
            parsedField3 = ly.ConfigValues.read(type3, values.get("conditiondesc"), fileName + ":行" + lineNumber + ":字段 conditiondesc");
            parsedField4 = ly.ConfigValues.read(type4, values.get("award"), fileName + ":行" + lineNumber + ":字段 award");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          TrialawardConfig config = new TrialawardConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4);
          newList.add(config);
      });
      configList = List.copyOf(newList);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (TrialawardConfig config : configList) config.afterLoad();
      new TrialawardConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<TrialawardConfig> getConfigList() {
      return configList;
    }

    @Override
    public String getConfigFileName() {
      return "trialaward.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
