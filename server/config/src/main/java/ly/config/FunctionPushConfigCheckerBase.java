package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class FunctionPushConfigCheckerBase extends AbstractConfigChecker<FunctionPushConfig> {
  @Override
  public String getConfigFileName() {
    return "functionPush.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "functionId", "INT"),
        new ConfigColumnMeta(2, "sort", "INT"),
        new ConfigColumnMeta(3, "icon", "INT"),
        new ConfigColumnMeta(4, "turnId", "INT"),
        new ConfigColumnMeta(5, "des", "INT"));
  }
}
