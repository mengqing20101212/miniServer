package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class SystemmessageConfigCheckerBase extends AbstractConfigChecker<SystemmessageConfig> {
  @Override
  public String getConfigFileName() {
    return "systemmessage.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "comment", "string"),
        new ConfigColumnMeta(1, "id", "int"),
        new ConfigColumnMeta(2, "type", "int"),
        new ConfigColumnMeta(3, "item_type", "int"),
        new ConfigColumnMeta(4, "speed", "int"),
        new ConfigColumnMeta(5, "time", "int"),
        new ConfigColumnMeta(6, "message1", "string"),
        new ConfigColumnMeta(7, "message2", "string"),
        new ConfigColumnMeta(8, "delayTime", "int"));
  }
}
