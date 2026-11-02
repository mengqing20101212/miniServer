package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ActivityPuzzleConfigCheckerBase extends AbstractConfigChecker<ActivityPuzzleConfig> {
  @Override
  public String getConfigFileName() {
    return "activityPuzzle.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "Puzzleid", "int"),
        new ConfigColumnMeta(2, "lossItem", "string"),
        new ConfigColumnMeta(3, "poolGift", "int"));
  }
}
