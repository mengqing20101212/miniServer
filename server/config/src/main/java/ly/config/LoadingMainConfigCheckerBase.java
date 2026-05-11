package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class LoadingMainConfigCheckerBase extends AbstractConfigChecker<LoadingMainConfig> {
  @Override
  public String getConfigFileName() {
    return "loadingMain.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "type", "INT"),
        new ConfigColumnMeta(2, "name", "STRING"),
        new ConfigColumnMeta(3, "para", "INT"),
        new ConfigColumnMeta(4, "pool", "STRING"),
        new ConfigColumnMeta(5, "tipsPool", "STRING"),
        new ConfigColumnMeta(6, "isPriority", "INT"));
  }
}
