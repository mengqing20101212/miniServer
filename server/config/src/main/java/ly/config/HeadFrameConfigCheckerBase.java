package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class HeadFrameConfigCheckerBase extends AbstractConfigChecker<HeadFrameConfig> {
  @Override
  public String getConfigFileName() {
    return "headFrame.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "name", "STRING"),
        new ConfigColumnMeta(2, "desc", "STRING"),
        new ConfigColumnMeta(3, "priority", "INT"),
        new ConfigColumnMeta(4, "value", "STRING"),
        new ConfigColumnMeta(5, "duration", "INT"),
        new ConfigColumnMeta(6, "isInitial", "INT"),
        new ConfigColumnMeta(7, "channel", "INT"));
  }
}
