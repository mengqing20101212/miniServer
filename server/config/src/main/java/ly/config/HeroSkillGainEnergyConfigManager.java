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
 * File: HeroSkillGainEnergyConfigManager
 */
public class HeroSkillGainEnergyConfigManager implements InterfaceConfigManagerProxy {
  public static HeroSkillGainEnergyConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(HeroSkillGainEnergyConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new HeroSkillGainEnergyConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "heroSkillGainEnergy.xml"; }

  public static class HeroSkillGainEnergyConfigManagerImpl extends AbstractConfigManger {
    private List<HeroSkillGainEnergyConfig> configList = List.of();
    private Map<Integer, HeroSkillGainEnergyConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type5 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type7 = ly.ConfigValueType.parse("int");
      String fileName = configDir + File.separator + getConfigFileName();
      HeroSkillGainEnergyConfigChecker checker = new HeroSkillGainEnergyConfigChecker();
      List<HeroSkillGainEnergyConfig> newList = new ArrayList<>();
      Map<Integer, HeroSkillGainEnergyConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          int parsedField1 = 0;
          String parsedField2 = null;
          int parsedField3 = 0;
          int parsedField4 = 0;
          int parsedField5 = 0;
          int parsedField6 = 0;
          int parsedField7 = 0;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("heroId"), fileName + ":行" + lineNumber + ":字段 heroId");
            parsedField2 = ly.ConfigValues.read(type2, values.get("beizhu"), fileName + ":行" + lineNumber + ":字段 beizhu");
            parsedField3 = ly.ConfigValues.read(type3, values.get("skillPos"), fileName + ":行" + lineNumber + ":字段 skillPos");
            parsedField4 = ly.ConfigValues.read(type4, values.get("skillLevel"), fileName + ":行" + lineNumber + ":字段 skillLevel");
            parsedField5 = ly.ConfigValues.read(type5, values.get("gainEnergy"), fileName + ":行" + lineNumber + ":字段 gainEnergy");
            parsedField6 = ly.ConfigValues.read(type6, values.get("awakeGainEnergy"), fileName + ":行" + lineNumber + ":字段 awakeGainEnergy");
            parsedField7 = ly.ConfigValues.read(type7, values.get("characteristicId"), fileName + ":行" + lineNumber + ":字段 characteristicId");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          HeroSkillGainEnergyConfig config = new HeroSkillGainEnergyConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (HeroSkillGainEnergyConfig config : configList) config.afterLoad();
      new HeroSkillGainEnergyConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<HeroSkillGainEnergyConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, HeroSkillGainEnergyConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "heroSkillGainEnergy.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
