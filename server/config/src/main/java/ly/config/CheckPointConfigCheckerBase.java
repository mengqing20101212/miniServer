package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class CheckPointConfigCheckerBase extends AbstractConfigChecker<CheckPointConfig> {
  @Override
  public String getConfigFileName() {
    return "checkPoint.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "des", "STRING"),
        new ConfigColumnMeta(2, "pointType", "INT"),
        new ConfigColumnMeta(3, "para", "STRING"),
        new ConfigColumnMeta(4, "functionName", "STRING"));
  }
}
