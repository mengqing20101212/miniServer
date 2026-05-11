package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class MinigameRewardSquareConfigCheckerBase extends AbstractConfigChecker<MinigameRewardSquareConfig> {
  @Override
  public String getConfigFileName() {
    return "minigameRewardSquare.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "rankShow", "STRING"),
        new ConfigColumnMeta(2, "hpScore", "STRING"),
        new ConfigColumnMeta(3, "timeScore", "STRING"),
        new ConfigColumnMeta(4, "dropId", "INT"));
  }
}
