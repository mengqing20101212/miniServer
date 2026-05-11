package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class SaitamaRewardConfigCheckerBase extends AbstractConfigChecker<SaitamaRewardConfig> {
  @Override
  public String getConfigFileName() {
    return "saitamaReward.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "from", "INT"),
        new ConfigColumnMeta(2, "name", "STRING"),
        new ConfigColumnMeta(3, "title", "STRING"),
        new ConfigColumnMeta(4, "titleRes", "INT"),
        new ConfigColumnMeta(5, "headId", "INT"),
        new ConfigColumnMeta(6, "word", "STRING"));
  }
}
