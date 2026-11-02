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
 * File: CookSkillConfigManager
 */
public class CookSkillConfigManager implements InterfaceConfigManagerProxy {
  public static CookSkillConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(CookSkillConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new CookSkillConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "cookSkill.xml"; }

  public static class CookSkillConfigManagerImpl extends AbstractConfigManger {
    private List<CookSkillConfig> configList = List.of();
    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("int");
      String fileName = configDir + File.separator + getConfigFileName();
      CookSkillConfigChecker checker = new CookSkillConfigChecker();
      List<CookSkillConfig> newList = new ArrayList<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          int parsedField1 = 0;
          int parsedField2 = 0;
          int parsedField3 = 0;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("skill"), fileName + ":行" + lineNumber + ":字段 skill");
            parsedField1 = ly.ConfigValues.read(type1, values.get("group"), fileName + ":行" + lineNumber + ":字段 group");
            parsedField2 = ly.ConfigValues.read(type2, values.get("nextExp"), fileName + ":行" + lineNumber + ":字段 nextExp");
            parsedField3 = ly.ConfigValues.read(type3, values.get("bonus"), fileName + ":行" + lineNumber + ":字段 bonus");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          CookSkillConfig config = new CookSkillConfig(parsedField0, parsedField1, parsedField2, parsedField3);
          newList.add(config);
      });
      configList = List.copyOf(newList);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (CookSkillConfig config : configList) config.afterLoad();
      new CookSkillConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<CookSkillConfig> getConfigList() {
      return configList;
    }

    @Override
    public String getConfigFileName() {
      return "cookSkill.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
