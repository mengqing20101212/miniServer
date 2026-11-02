package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class LoadingMainConfigCheckerBase extends AbstractConfigChecker<LoadingMainConfig> {
  @Override
  public String getConfigFileName() {
    return "loadingMain.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "type", "int"),
        new ConfigColumnMeta(2, "name", "string"),
        new ConfigColumnMeta(3, "para", "int"),
        new ConfigColumnMeta(4, "pool", "string"),
        new ConfigColumnMeta(5, "tipsPool", "string"),
        new ConfigColumnMeta(6, "isPriority", "int"));
  }
}
