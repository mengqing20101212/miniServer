package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class CurveConfigCheckerBase extends AbstractConfigChecker<CurveConfig> {
  @Override
  public String getConfigFileName() {
    return "curve.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "curve_resname", "STRING"));
  }
}
