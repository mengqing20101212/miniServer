package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class SystemmessageConfigCheckerBase extends AbstractConfigChecker<SystemmessageConfig> {
  @Override
  public String getConfigFileName() {
    return "systemmessage.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "comment", "STRING"),
        new ConfigColumnMeta(1, "id", "INT"),
        new ConfigColumnMeta(2, "type", "INT"),
        new ConfigColumnMeta(3, "item_type", "INT"),
        new ConfigColumnMeta(4, "speed", "INT"),
        new ConfigColumnMeta(5, "time", "INT"),
        new ConfigColumnMeta(6, "message1", "STRING"),
        new ConfigColumnMeta(7, "message2", "STRING"),
        new ConfigColumnMeta(8, "delayTime", "INT"));
  }
}
