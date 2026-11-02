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
 * File: AcCommanderGiftConfigManager
 */
public class AcCommanderGiftConfigManager implements InterfaceConfigManagerProxy {
  public static AcCommanderGiftConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(AcCommanderGiftConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new AcCommanderGiftConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "acCommanderGift.xml"; }

  public static class AcCommanderGiftConfigManagerImpl extends AbstractConfigManger {
    private List<AcCommanderGiftConfig> configList = List.of();
    private Map<Integer, AcCommanderGiftConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type5 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type7 = ly.ConfigValueType.parse("string");
      String fileName = configDir + File.separator + getConfigFileName();
      AcCommanderGiftConfigChecker checker = new AcCommanderGiftConfigChecker();
      List<AcCommanderGiftConfig> newList = new ArrayList<>();
      Map<Integer, AcCommanderGiftConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          String parsedField1 = null;
          int parsedField2 = 0;
          String parsedField3 = null;
          int parsedField4 = 0;
          int parsedField5 = 0;
          int parsedField6 = 0;
          String parsedField7 = null;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("heroid"), fileName + ":行" + lineNumber + ":字段 heroid");
            parsedField2 = ly.ConfigValues.read(type2, values.get("page"), fileName + ":行" + lineNumber + ":字段 page");
            parsedField3 = ly.ConfigValues.read(type3, values.get("name"), fileName + ":行" + lineNumber + ":字段 name");
            parsedField4 = ly.ConfigValues.read(type4, values.get("rewardShow"), fileName + ":行" + lineNumber + ":字段 rewardShow");
            parsedField5 = ly.ConfigValues.read(type5, values.get("drop"), fileName + ":行" + lineNumber + ":字段 drop");
            parsedField6 = ly.ConfigValues.read(type6, values.get("redirectionId"), fileName + ":行" + lineNumber + ":字段 redirectionId");
            parsedField7 = ly.ConfigValues.read(type7, values.get("commanderShow"), fileName + ":行" + lineNumber + ":字段 commanderShow");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          AcCommanderGiftConfig config = new AcCommanderGiftConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (AcCommanderGiftConfig config : configList) config.afterLoad();
      new AcCommanderGiftConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<AcCommanderGiftConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, AcCommanderGiftConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "acCommanderGift.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
