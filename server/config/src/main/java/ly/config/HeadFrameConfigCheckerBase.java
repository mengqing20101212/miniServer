package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class HeadFrameConfigCheckerBase extends AbstractConfigChecker<HeadFrameConfig> {
  @Override
  public String getConfigFileName() {
    return "headFrame.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "name", "string"),
        new ConfigColumnMeta(2, "desc", "string"),
        new ConfigColumnMeta(3, "priority", "int"),
        new ConfigColumnMeta(4, "value", "string"),
        new ConfigColumnMeta(5, "duration", "int"),
        new ConfigColumnMeta(6, "isInitial", "int"),
        new ConfigColumnMeta(7, "channel", "int"));
  }
}
