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
 * File: CookMaterialConfigManager
 */
public class CookMaterialConfigManager implements InterfaceConfigManagerProxy {
  public static CookMaterialConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(CookMaterialConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new CookMaterialConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "cookMaterial.xml"; }

  public static class CookMaterialConfigManagerImpl extends AbstractConfigManger {
    private List<CookMaterialConfig> configList = List.of();
    private Map<Integer, CookMaterialConfig> configMap = Map.of();

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
      ly.ConfigValueType type9 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type10 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type11 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type12 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type13 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type14 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type15 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type16 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type17 = ly.ConfigValueType.parse("string");
      String fileName = configDir + File.separator + getConfigFileName();
      CookMaterialConfigChecker checker = new CookMaterialConfigChecker();
      List<CookMaterialConfig> newList = new ArrayList<>();
      Map<Integer, CookMaterialConfig> newMap = new HashMap<>();
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
          int parsedField9 = 0;
          int parsedField10 = 0;
          int parsedField11 = 0;
          int parsedField12 = 0;
          int parsedField13 = 0;
          int parsedField14 = 0;
          int parsedField15 = 0;
          String parsedField16 = null;
          String parsedField17 = null;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("name"), fileName + ":行" + lineNumber + ":字段 name");
            parsedField2 = ly.ConfigValues.read(type2, values.get("type"), fileName + ":行" + lineNumber + ":字段 type");
            parsedField3 = ly.ConfigValues.read(type3, values.get("classify"), fileName + ":行" + lineNumber + ":字段 classify");
            parsedField4 = ly.ConfigValues.read(type4, values.get("para"), fileName + ":行" + lineNumber + ":字段 para");
            parsedField5 = ly.ConfigValues.read(type5, values.get("star"), fileName + ":行" + lineNumber + ":字段 star");
            parsedField6 = ly.ConfigValues.read(type6, values.get("showId"), fileName + ":行" + lineNumber + ":字段 showId");
            parsedField7 = ly.ConfigValues.read(type7, values.get("price"), fileName + ":行" + lineNumber + ":字段 price");
            parsedField8 = ly.ConfigValues.read(type8, values.get("note"), fileName + ":行" + lineNumber + ":字段 note");
            parsedField9 = ly.ConfigValues.read(type9, values.get("rewardId"), fileName + ":行" + lineNumber + ":字段 rewardId");
            parsedField10 = ly.ConfigValues.read(type10, values.get("rewardNum"), fileName + ":行" + lineNumber + ":字段 rewardNum");
            parsedField11 = ly.ConfigValues.read(type11, values.get("friendNum"), fileName + ":行" + lineNumber + ":字段 friendNum");
            parsedField12 = ly.ConfigValues.read(type12, values.get("critRewardNum"), fileName + ":行" + lineNumber + ":字段 critRewardNum");
            parsedField13 = ly.ConfigValues.read(type13, values.get("critFriendNum"), fileName + ":行" + lineNumber + ":字段 critFriendNum");
            parsedField14 = ly.ConfigValues.read(type14, values.get("exRewardNum"), fileName + ":行" + lineNumber + ":字段 exRewardNum");
            parsedField15 = ly.ConfigValues.read(type15, values.get("exFriendNum"), fileName + ":行" + lineNumber + ":字段 exFriendNum");
            parsedField16 = ly.ConfigValues.read(type16, values.get("qteRewardNum"), fileName + ":行" + lineNumber + ":字段 qteRewardNum");
            parsedField17 = ly.ConfigValues.read(type17, values.get("qteFriendNum"), fileName + ":行" + lineNumber + ":字段 qteFriendNum");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          CookMaterialConfig config = new CookMaterialConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8, parsedField9, parsedField10, parsedField11, parsedField12, parsedField13, parsedField14, parsedField15, parsedField16, parsedField17);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (CookMaterialConfig config : configList) config.afterLoad();
      new CookMaterialConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<CookMaterialConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, CookMaterialConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "cookMaterial.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
