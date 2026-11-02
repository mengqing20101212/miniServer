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
 * File: SupportTeamHeroAttrConfigManager
 */
public class SupportTeamHeroAttrConfigManager implements InterfaceConfigManagerProxy {
  public static SupportTeamHeroAttrConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(SupportTeamHeroAttrConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new SupportTeamHeroAttrConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "supportTeamHeroAttr.xml"; }

  public static class SupportTeamHeroAttrConfigManagerImpl extends AbstractConfigManger {
    private List<SupportTeamHeroAttrConfig> configList = List.of();
    private Map<Integer, SupportTeamHeroAttrConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type5 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type7 = ly.ConfigValueType.parse("string");
      String fileName = configDir + File.separator + getConfigFileName();
      SupportTeamHeroAttrConfigChecker checker = new SupportTeamHeroAttrConfigChecker();
      List<SupportTeamHeroAttrConfig> newList = new ArrayList<>();
      Map<Integer, SupportTeamHeroAttrConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          int parsedField1 = 0;
          int parsedField2 = 0;
          int parsedField3 = 0;
          String parsedField4 = null;
          String parsedField5 = null;
          int parsedField6 = 0;
          String parsedField7 = null;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("attrClass"), fileName + ":行" + lineNumber + ":字段 attrClass");
            parsedField2 = ly.ConfigValues.read(type2, values.get("attrLevel"), fileName + ":行" + lineNumber + ":字段 attrLevel");
            parsedField3 = ly.ConfigValues.read(type3, values.get("upCostItemNum"), fileName + ":行" + lineNumber + ":字段 upCostItemNum");
            parsedField4 = ly.ConfigValues.read(type4, values.get("attrType"), fileName + ":行" + lineNumber + ":字段 attrType");
            parsedField5 = ly.ConfigValues.read(type5, values.get("attrName"), fileName + ":行" + lineNumber + ":字段 attrName");
            parsedField6 = ly.ConfigValues.read(type6, values.get("unlcok"), fileName + ":行" + lineNumber + ":字段 unlcok");
            parsedField7 = ly.ConfigValues.read(type7, values.get("icon"), fileName + ":行" + lineNumber + ":字段 icon");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          SupportTeamHeroAttrConfig config = new SupportTeamHeroAttrConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (SupportTeamHeroAttrConfig config : configList) config.afterLoad();
      new SupportTeamHeroAttrConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<SupportTeamHeroAttrConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, SupportTeamHeroAttrConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "supportTeamHeroAttr.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
