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
 * File: SkillGroupConfigManager
 */
public class SkillGroupConfigManager implements InterfaceConfigManagerProxy {
  public static SkillGroupConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(SkillGroupConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new SkillGroupConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "skillGroup.xml"; }

  public static class SkillGroupConfigManagerImpl extends AbstractConfigManger {
    private List<SkillGroupConfig> configList = List.of();
    private Map<Integer, SkillGroupConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("list<int>");
      String fileName = configDir + File.separator + getConfigFileName();
      SkillGroupConfigChecker checker = new SkillGroupConfigChecker();
      List<SkillGroupConfig> newList = new ArrayList<>();
      Map<Integer, SkillGroupConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          String parsedField1 = null;
          String parsedField2 = null;
          String parsedField3 = null;
          java.util.List<Integer> parsedField4 = null;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("name"), fileName + ":行" + lineNumber + ":字段 name");
            parsedField2 = ly.ConfigValues.read(type2, values.get("description"), fileName + ":行" + lineNumber + ":字段 description");
            parsedField3 = ly.ConfigValues.read(type3, values.get("skillList"), fileName + ":行" + lineNumber + ":字段 skillList");
            parsedField4 = ly.ConfigValues.read(type4, values.get("skillIcon"), fileName + ":行" + lineNumber + ":字段 skillIcon");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          SkillGroupConfig config = new SkillGroupConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (SkillGroupConfig config : configList) config.afterLoad();
      new SkillGroupConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<SkillGroupConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, SkillGroupConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "skillGroup.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
