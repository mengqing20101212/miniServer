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
 * File: ReserverecruitConfigManager
 */
public class ReserverecruitConfigManager implements InterfaceConfigManagerProxy {
  public static ReserverecruitConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(ReserverecruitConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new ReserverecruitConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "reserverecruit.xml"; }

  public static class ReserverecruitConfigManagerImpl extends AbstractConfigManger {
    private List<ReserverecruitConfig> configList = List.of();
    private Map<Integer, ReserverecruitConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type5 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type7 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type8 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type9 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type10 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type11 = ly.ConfigValueType.parse("int");
      String fileName = configDir + File.separator + getConfigFileName();
      ReserverecruitConfigChecker checker = new ReserverecruitConfigChecker();
      List<ReserverecruitConfig> newList = new ArrayList<>();
      Map<Integer, ReserverecruitConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          int parsedField1 = 0;
          int parsedField2 = 0;
          String parsedField3 = null;
          String parsedField4 = null;
          String parsedField5 = null;
          int parsedField6 = 0;
          int parsedField7 = 0;
          String parsedField8 = null;
          int parsedField9 = 0;
          int parsedField10 = 0;
          int parsedField11 = 0;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("welfareId"), fileName + ":行" + lineNumber + ":字段 welfareId");
            parsedField2 = ly.ConfigValues.read(type2, values.get("recruitNum"), fileName + ":行" + lineNumber + ":字段 recruitNum");
            parsedField3 = ly.ConfigValues.read(type3, values.get("awardList"), fileName + ":行" + lineNumber + ":字段 awardList");
            parsedField4 = ly.ConfigValues.read(type4, values.get("awardRelativePro"), fileName + ":行" + lineNumber + ":字段 awardRelativePro");
            parsedField5 = ly.ConfigValues.read(type5, values.get("desc"), fileName + ":行" + lineNumber + ":字段 desc");
            parsedField6 = ly.ConfigValues.read(type6, values.get("dayLimit"), fileName + ":行" + lineNumber + ":字段 dayLimit");
            parsedField7 = ly.ConfigValues.read(type7, values.get("Guaranteed"), fileName + ":行" + lineNumber + ":字段 Guaranteed");
            parsedField8 = ly.ConfigValues.read(type8, values.get("Guaranteerange"), fileName + ":行" + lineNumber + ":字段 Guaranteerange");
            parsedField9 = ly.ConfigValues.read(type9, values.get("intervaltime"), fileName + ":行" + lineNumber + ":字段 intervaltime");
            parsedField10 = ly.ConfigValues.read(type10, values.get("Receiveaward"), fileName + ":行" + lineNumber + ":字段 Receiveaward");
            parsedField11 = ly.ConfigValues.read(type11, values.get("ssrupperlimit"), fileName + ":行" + lineNumber + ":字段 ssrupperlimit");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          ReserverecruitConfig config = new ReserverecruitConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8, parsedField9, parsedField10, parsedField11);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (ReserverecruitConfig config : configList) config.afterLoad();
      new ReserverecruitConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<ReserverecruitConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, ReserverecruitConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "reserverecruit.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
