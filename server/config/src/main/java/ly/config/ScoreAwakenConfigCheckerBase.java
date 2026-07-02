package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ScoreAwakenConfigCheckerBase extends AbstractConfigChecker<ScoreAwakenConfig> {
  @Override
  public String getConfigFileName() {
    return "scoreAwaken.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "quality", "INT"),
        new ConfigColumnMeta(2, "awaken", "INT"),
        new ConfigColumnMeta(3, "score", "INT"));
  }
}
