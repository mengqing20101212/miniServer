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
 * File: Device_switchConfigManager
 */
public class Device_switchConfigManager implements InterfaceConfigManagerProxy {
  public static Device_switchConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(Device_switchConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new Device_switchConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "device_switch.xml"; }

  public static class Device_switchConfigManagerImpl extends AbstractConfigManger {
    private List<Device_switchConfig> configList = List.of();
    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("int");
      String fileName = configDir + File.separator + getConfigFileName();
      Device_switchConfigChecker checker = new Device_switchConfigChecker();
      List<Device_switchConfig> newList = new ArrayList<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          String parsedField0 = null;
          int parsedField1 = 0;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("model"), fileName + ":行" + lineNumber + ":字段 model");
            parsedField1 = ly.ConfigValues.read(type1, values.get("grade"), fileName + ":行" + lineNumber + ":字段 grade");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          Device_switchConfig config = new Device_switchConfig(parsedField0, parsedField1);
          newList.add(config);
      });
      configList = List.copyOf(newList);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (Device_switchConfig config : configList) config.afterLoad();
      new Device_switchConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<Device_switchConfig> getConfigList() {
      return configList;
    }

    @Override
    public String getConfigFileName() {
      return "device_switch.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
