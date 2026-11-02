package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class FunctionPushConfigCheckerBase extends AbstractConfigChecker<FunctionPushConfig> {
  @Override
  public String getConfigFileName() {
    return "functionPush.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "functionId", "int"),
        new ConfigColumnMeta(2, "sort", "int"),
        new ConfigColumnMeta(3, "icon", "int"),
        new ConfigColumnMeta(4, "turnId", "int"),
        new ConfigColumnMeta(5, "des", "int"));
  }
}
