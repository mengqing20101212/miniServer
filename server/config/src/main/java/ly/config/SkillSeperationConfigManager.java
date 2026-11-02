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
 * File: SkillSeperationConfigManager
 */
public class SkillSeperationConfigManager implements InterfaceConfigManagerProxy {
  public static SkillSeperationConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(SkillSeperationConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new SkillSeperationConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "skillSeperation.xml"; }

  public static class SkillSeperationConfigManagerImpl extends AbstractConfigManger {
    private List<SkillSeperationConfig> configList = List.of();
    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("string");
      String fileName = configDir + File.separator + getConfigFileName();
      SkillSeperationConfigChecker checker = new SkillSeperationConfigChecker();
      List<SkillSeperationConfig> newList = new ArrayList<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          String parsedField0 = null;
          String parsedField1 = null;
          String parsedField2 = null;
          String parsedField3 = null;
          String parsedField4 = null;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("mainAni"), fileName + ":行" + lineNumber + ":字段 mainAni");
            parsedField1 = ly.ConfigValues.read(type1, values.get("sperationAni_1"), fileName + ":行" + lineNumber + ":字段 sperationAni_1");
            parsedField2 = ly.ConfigValues.read(type2, values.get("sperationAni_2"), fileName + ":行" + lineNumber + ":字段 sperationAni_2");
            parsedField3 = ly.ConfigValues.read(type3, values.get("sperationAni_3"), fileName + ":行" + lineNumber + ":字段 sperationAni_3");
            parsedField4 = ly.ConfigValues.read(type4, values.get("sperationAni_4"), fileName + ":行" + lineNumber + ":字段 sperationAni_4");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          SkillSeperationConfig config = new SkillSeperationConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4);
          newList.add(config);
      });
      configList = List.copyOf(newList);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (SkillSeperationConfig config : configList) config.afterLoad();
      new SkillSeperationConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<SkillSeperationConfig> getConfigList() {
      return configList;
    }

    @Override
    public String getConfigFileName() {
      return "skillSeperation.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
