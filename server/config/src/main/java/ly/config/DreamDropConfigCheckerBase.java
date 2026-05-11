package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class DreamDropConfigCheckerBase extends AbstractConfigChecker<DreamDropConfig> {
  @Override
  public String getConfigFileName() {
    return "dreamDrop.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "dreamMainId", "INT"),
        new ConfigColumnMeta(2, "appraise", "STRING"),
        new ConfigColumnMeta(3, "heroCount", "INT"),
        new ConfigColumnMeta(4, "dropShow", "STRING"),
        new ConfigColumnMeta(5, "drop", "INT"),
        new ConfigColumnMeta(6, "settlement", "INT"),
        new ConfigColumnMeta(7, "display", "STRING"));
  }
}
