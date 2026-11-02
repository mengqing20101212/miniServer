package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class DreamDropConfigCheckerBase extends AbstractConfigChecker<DreamDropConfig> {
  @Override
  public String getConfigFileName() {
    return "dreamDrop.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "dreamMainId", "int"),
        new ConfigColumnMeta(2, "appraise", "string"),
        new ConfigColumnMeta(3, "heroCount", "int"),
        new ConfigColumnMeta(4, "dropShow", "string"),
        new ConfigColumnMeta(5, "drop", "int"),
        new ConfigColumnMeta(6, "settlement", "int"),
        new ConfigColumnMeta(7, "display", "string"));
  }
}
