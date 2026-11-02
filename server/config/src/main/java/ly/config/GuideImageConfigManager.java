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
 * File: GuideImageConfigManager
 */
public class GuideImageConfigManager implements InterfaceConfigManagerProxy {
  public static GuideImageConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(GuideImageConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new GuideImageConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "guideImage.xml"; }

  public static class GuideImageConfigManagerImpl extends AbstractConfigManger {
    private List<GuideImageConfig> configList = List.of();
    private Map<Integer, GuideImageConfig> configMap = Map.of();

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
      GuideImageConfigChecker checker = new GuideImageConfigChecker();
      List<GuideImageConfig> newList = new ArrayList<>();
      Map<Integer, GuideImageConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          String parsedField1 = null;
          String parsedField2 = null;
          String parsedField3 = null;
          int parsedField4 = 0;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("beizhu"), fileName + ":行" + lineNumber + ":字段 beizhu");
            parsedField2 = ly.ConfigValues.read(type2, values.get("title"), fileName + ":行" + lineNumber + ":字段 title");
            parsedField3 = ly.ConfigValues.read(type3, values.get("resourceList"), fileName + ":行" + lineNumber + ":字段 resourceList");
            parsedField4 = ly.ConfigValues.read(type4, values.get("triggerNext"), fileName + ":行" + lineNumber + ":字段 triggerNext");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          GuideImageConfig config = new GuideImageConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (GuideImageConfig config : configList) config.afterLoad();
      new GuideImageConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<GuideImageConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, GuideImageConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "guideImage.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
