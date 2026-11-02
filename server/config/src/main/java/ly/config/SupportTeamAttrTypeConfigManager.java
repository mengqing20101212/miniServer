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
 * File: SupportTeamAttrTypeConfigManager
 */
public class SupportTeamAttrTypeConfigManager implements InterfaceConfigManagerProxy {
  public static SupportTeamAttrTypeConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(SupportTeamAttrTypeConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new SupportTeamAttrTypeConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "supportTeamAttrType.xml"; }

  public static class SupportTeamAttrTypeConfigManagerImpl extends AbstractConfigManger {
    private List<SupportTeamAttrTypeConfig> configList = List.of();
    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("int");
      String fileName = configDir + File.separator + getConfigFileName();
      SupportTeamAttrTypeConfigChecker checker = new SupportTeamAttrTypeConfigChecker();
      List<SupportTeamAttrTypeConfig> newList = new ArrayList<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          String parsedField1 = null;
          String parsedField2 = null;
          int parsedField3 = 0;
          int parsedField4 = 0;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("attrType"), fileName + ":行" + lineNumber + ":字段 attrType");
            parsedField1 = ly.ConfigValues.read(type1, values.get("function"), fileName + ":行" + lineNumber + ":字段 function");
            parsedField2 = ly.ConfigValues.read(type2, values.get("des"), fileName + ":行" + lineNumber + ":字段 des");
            parsedField3 = ly.ConfigValues.read(type3, values.get("icon"), fileName + ":行" + lineNumber + ":字段 icon");
            parsedField4 = ly.ConfigValues.read(type4, values.get("isPercent"), fileName + ":行" + lineNumber + ":字段 isPercent");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          SupportTeamAttrTypeConfig config = new SupportTeamAttrTypeConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4);
          newList.add(config);
      });
      configList = List.copyOf(newList);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (SupportTeamAttrTypeConfig config : configList) config.afterLoad();
      new SupportTeamAttrTypeConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<SupportTeamAttrTypeConfig> getConfigList() {
      return configList;
    }

    @Override
    public String getConfigFileName() {
      return "supportTeamAttrType.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
