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
 * File: SupportTeamExpConfigManager
 */
public class SupportTeamExpConfigManager implements InterfaceConfigManagerProxy {
  public static SupportTeamExpConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(SupportTeamExpConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new SupportTeamExpConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "supportTeamExp.xml"; }

  public static class SupportTeamExpConfigManagerImpl extends AbstractConfigManger {
    private List<SupportTeamExpConfig> configList = List.of();
    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("int");
      String fileName = configDir + File.separator + getConfigFileName();
      SupportTeamExpConfigChecker checker = new SupportTeamExpConfigChecker();
      List<SupportTeamExpConfig> newList = new ArrayList<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          int parsedField1 = 0;
          int parsedField2 = 0;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("modelId"), fileName + ":行" + lineNumber + ":字段 modelId");
            parsedField1 = ly.ConfigValues.read(type1, values.get("level"), fileName + ":行" + lineNumber + ":字段 level");
            parsedField2 = ly.ConfigValues.read(type2, values.get("exp"), fileName + ":行" + lineNumber + ":字段 exp");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          SupportTeamExpConfig config = new SupportTeamExpConfig(parsedField0, parsedField1, parsedField2);
          newList.add(config);
      });
      configList = List.copyOf(newList);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (SupportTeamExpConfig config : configList) config.afterLoad();
      new SupportTeamExpConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<SupportTeamExpConfig> getConfigList() {
      return configList;
    }

    @Override
    public String getConfigFileName() {
      return "supportTeamExp.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
