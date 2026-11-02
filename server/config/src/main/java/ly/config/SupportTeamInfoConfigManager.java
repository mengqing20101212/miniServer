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
 * File: SupportTeamInfoConfigManager
 */
public class SupportTeamInfoConfigManager implements InterfaceConfigManagerProxy {
  public static SupportTeamInfoConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(SupportTeamInfoConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new SupportTeamInfoConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "supportTeamInfo.xml"; }

  public static class SupportTeamInfoConfigManagerImpl extends AbstractConfigManger {
    private List<SupportTeamInfoConfig> configList = List.of();
    private Map<Integer, SupportTeamInfoConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("string");
      String fileName = configDir + File.separator + getConfigFileName();
      SupportTeamInfoConfigChecker checker = new SupportTeamInfoConfigChecker();
      List<SupportTeamInfoConfig> newList = new ArrayList<>();
      Map<Integer, SupportTeamInfoConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          String parsedField1 = null;
          String parsedField2 = null;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("teamName"), fileName + ":行" + lineNumber + ":字段 teamName");
            parsedField2 = ly.ConfigValues.read(type2, values.get("teamSkill"), fileName + ":行" + lineNumber + ":字段 teamSkill");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          SupportTeamInfoConfig config = new SupportTeamInfoConfig(parsedField0, parsedField1, parsedField2);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (SupportTeamInfoConfig config : configList) config.afterLoad();
      new SupportTeamInfoConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<SupportTeamInfoConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, SupportTeamInfoConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "supportTeamInfo.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
