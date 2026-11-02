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
 * File: RecruitWelfareConfigManager
 */
public class RecruitWelfareConfigManager implements InterfaceConfigManagerProxy {
  public static RecruitWelfareConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(RecruitWelfareConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new RecruitWelfareConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "recruitWelfare.xml"; }

  public static class RecruitWelfareConfigManagerImpl extends AbstractConfigManger {
    private List<RecruitWelfareConfig> configList = List.of();
    private Map<Integer, RecruitWelfareConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("int");
      String fileName = configDir + File.separator + getConfigFileName();
      RecruitWelfareConfigChecker checker = new RecruitWelfareConfigChecker();
      List<RecruitWelfareConfig> newList = new ArrayList<>();
      Map<Integer, RecruitWelfareConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          String parsedField1 = null;
          String parsedField2 = null;
          String parsedField3 = null;
          int parsedField4 = 0;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("welfareQuality"), fileName + ":行" + lineNumber + ":字段 welfareQuality");
            parsedField2 = ly.ConfigValues.read(type2, values.get("qualityWelfareInterval"), fileName + ":行" + lineNumber + ":字段 qualityWelfareInterval");
            parsedField3 = ly.ConfigValues.read(type3, values.get("qualityWelfareAwardId"), fileName + ":行" + lineNumber + ":字段 qualityWelfareAwardId");
            parsedField4 = ly.ConfigValues.read(type4, values.get("welfareHero"), fileName + ":行" + lineNumber + ":字段 welfareHero");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          RecruitWelfareConfig config = new RecruitWelfareConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (RecruitWelfareConfig config : configList) config.afterLoad();
      new RecruitWelfareConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<RecruitWelfareConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, RecruitWelfareConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "recruitWelfare.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
