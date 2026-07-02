package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class BravePointConfigCheckerBase extends AbstractConfigChecker<BravePointConfig> {
  @Override
  public String getConfigFileName() {
    return "bravePoint.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "type", "INT"),
        new ConfigColumnMeta(2, "para", "INT"),
        new ConfigColumnMeta(3, "addCount", "INT"),
        new ConfigColumnMeta(4, "beizhu", "STRING"),
        new ConfigColumnMeta(5, "triggerReason", "INT"));
  }
}
