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
 * File: RecruitPoolConfigManager
 */
public class RecruitPoolConfigManager implements InterfaceConfigManagerProxy {
  public static RecruitPoolConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(RecruitPoolConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new RecruitPoolConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "recruitPool.xml"; }

  public static class RecruitPoolConfigManagerImpl extends AbstractConfigManger {
    private List<RecruitPoolConfig> configList = List.of();
    private Map<Integer, RecruitPoolConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type5 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type7 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type8 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type9 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type10 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type11 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type12 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type13 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type14 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type15 = ly.ConfigValueType.parse("int");
      String fileName = configDir + File.separator + getConfigFileName();
      RecruitPoolConfigChecker checker = new RecruitPoolConfigChecker();
      List<RecruitPoolConfig> newList = new ArrayList<>();
      Map<Integer, RecruitPoolConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          String parsedField1 = null;
          int parsedField2 = 0;
          int parsedField3 = 0;
          int parsedField4 = 0;
          int parsedField5 = 0;
          int parsedField6 = 0;
          int parsedField7 = 0;
          String parsedField8 = null;
          String parsedField9 = null;
          String parsedField10 = null;
          String parsedField11 = null;
          int parsedField12 = 0;
          int parsedField13 = 0;
          int parsedField14 = 0;
          int parsedField15 = 0;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("name"), fileName + ":行" + lineNumber + ":字段 name");
            parsedField2 = ly.ConfigValues.read(type2, values.get("recruitType"), fileName + ":行" + lineNumber + ":字段 recruitType");
            parsedField3 = ly.ConfigValues.read(type3, values.get("showStyleType"), fileName + ":行" + lineNumber + ":字段 showStyleType");
            parsedField4 = ly.ConfigValues.read(type4, values.get("oneDrawId"), fileName + ":行" + lineNumber + ":字段 oneDrawId");
            parsedField5 = ly.ConfigValues.read(type5, values.get("tenDrawId"), fileName + ":行" + lineNumber + ":字段 tenDrawId");
            parsedField6 = ly.ConfigValues.read(type6, values.get("scheDuling"), fileName + ":行" + lineNumber + ":字段 scheDuling");
            parsedField7 = ly.ConfigValues.read(type7, values.get("type"), fileName + ":行" + lineNumber + ":字段 type");
            parsedField8 = ly.ConfigValues.read(type8, values.get("timesShowType"), fileName + ":行" + lineNumber + ":字段 timesShowType");
            parsedField9 = ly.ConfigValues.read(type9, values.get("video"), fileName + ":行" + lineNumber + ":字段 video");
            parsedField10 = ly.ConfigValues.read(type10, values.get("lastDateShow"), fileName + ":行" + lineNumber + ":字段 lastDateShow");
            parsedField11 = ly.ConfigValues.read(type11, values.get("chanceText"), fileName + ":行" + lineNumber + ":字段 chanceText");
            parsedField12 = ly.ConfigValues.read(type12, values.get("topId"), fileName + ":行" + lineNumber + ":字段 topId");
            parsedField13 = ly.ConfigValues.read(type13, values.get("recruitEndTopId"), fileName + ":行" + lineNumber + ":字段 recruitEndTopId");
            parsedField14 = ly.ConfigValues.read(type14, values.get("recruitmentshow"), fileName + ":行" + lineNumber + ":字段 recruitmentshow");
            parsedField15 = ly.ConfigValues.read(type15, values.get("trueActivityId"), fileName + ":行" + lineNumber + ":字段 trueActivityId");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          RecruitPoolConfig config = new RecruitPoolConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8, parsedField9, parsedField10, parsedField11, parsedField12, parsedField13, parsedField14, parsedField15);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (RecruitPoolConfig config : configList) config.afterLoad();
      new RecruitPoolConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<RecruitPoolConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, RecruitPoolConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "recruitPool.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
