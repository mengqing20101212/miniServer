package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ActivityPuzzleConfigCheckerBase extends AbstractConfigChecker<ActivityPuzzleConfig> {
  @Override
  public String getConfigFileName() {
    return "activityPuzzle.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "Puzzleid", "INT"),
        new ConfigColumnMeta(2, "lossItem", "STRING"),
        new ConfigColumnMeta(3, "poolGift", "INT"));
  }
}
