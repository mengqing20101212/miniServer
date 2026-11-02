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
 * File: ActivitysupplyConfigManager
 */
public class ActivitysupplyConfigManager implements InterfaceConfigManagerProxy {
  public static ActivitysupplyConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(ActivitysupplyConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new ActivitysupplyConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "activitysupply.xml"; }

  public static class ActivitysupplyConfigManagerImpl extends AbstractConfigManger {
    private List<ActivitysupplyConfig> configList = List.of();
    private Map<Integer, ActivitysupplyConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type5 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type7 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type8 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type9 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type10 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type11 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type12 = ly.ConfigValueType.parse("int");
      String fileName = configDir + File.separator + getConfigFileName();
      ActivitysupplyConfigChecker checker = new ActivitysupplyConfigChecker();
      List<ActivitysupplyConfig> newList = new ArrayList<>();
      Map<Integer, ActivitysupplyConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          int parsedField1 = 0;
          int parsedField2 = 0;
          int parsedField3 = 0;
          int parsedField4 = 0;
          int parsedField5 = 0;
          String parsedField6 = null;
          int parsedField7 = 0;
          int parsedField8 = 0;
          int parsedField9 = 0;
          int parsedField10 = 0;
          int parsedField11 = 0;
          int parsedField12 = 0;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("openType"), fileName + ":行" + lineNumber + ":字段 openType");
            parsedField2 = ly.ConfigValues.read(type2, values.get("scheDuling"), fileName + ":行" + lineNumber + ":字段 scheDuling");
            parsedField3 = ly.ConfigValues.read(type3, values.get("shopId"), fileName + ":行" + lineNumber + ":字段 shopId");
            parsedField4 = ly.ConfigValues.read(type4, values.get("drop"), fileName + ":行" + lineNumber + ":字段 drop");
            parsedField5 = ly.ConfigValues.read(type5, values.get("rechargShow"), fileName + ":行" + lineNumber + ":字段 rechargShow");
            parsedField6 = ly.ConfigValues.read(type6, values.get("sloganName"), fileName + ":行" + lineNumber + ":字段 sloganName");
            parsedField7 = ly.ConfigValues.read(type7, values.get("sloganBg"), fileName + ":行" + lineNumber + ":字段 sloganBg");
            parsedField8 = ly.ConfigValues.read(type8, values.get("limitPara"), fileName + ":行" + lineNumber + ":字段 limitPara");
            parsedField9 = ly.ConfigValues.read(type9, values.get("rechargeShopId"), fileName + ":行" + lineNumber + ":字段 rechargeShopId");
            parsedField10 = ly.ConfigValues.read(type10, values.get("refreshTime"), fileName + ":行" + lineNumber + ":字段 refreshTime");
            parsedField11 = ly.ConfigValues.read(type11, values.get("moneyType"), fileName + ":行" + lineNumber + ":字段 moneyType");
            parsedField12 = ly.ConfigValues.read(type12, values.get("quantity"), fileName + ":行" + lineNumber + ":字段 quantity");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          ActivitysupplyConfig config = new ActivitysupplyConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8, parsedField9, parsedField10, parsedField11, parsedField12);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (ActivitysupplyConfig config : configList) config.afterLoad();
      new ActivitysupplyConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<ActivitysupplyConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, ActivitysupplyConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "activitysupply.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
