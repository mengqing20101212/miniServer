package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class MinigameRewardSquareConfigCheckerBase extends AbstractConfigChecker<MinigameRewardSquareConfig> {
  @Override
  public String getConfigFileName() {
    return "minigameRewardSquare.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "rankShow", "string"),
        new ConfigColumnMeta(2, "hpScore", "string"),
        new ConfigColumnMeta(3, "timeScore", "string"),
        new ConfigColumnMeta(4, "dropId", "int"));
  }
}
