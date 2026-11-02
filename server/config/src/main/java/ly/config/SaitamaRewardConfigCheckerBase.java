package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class SaitamaRewardConfigCheckerBase extends AbstractConfigChecker<SaitamaRewardConfig> {
  @Override
  public String getConfigFileName() {
    return "saitamaReward.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "from", "int"),
        new ConfigColumnMeta(2, "name", "string"),
        new ConfigColumnMeta(3, "title", "string"),
        new ConfigColumnMeta(4, "titleRes", "int"),
        new ConfigColumnMeta(5, "headId", "int"),
        new ConfigColumnMeta(6, "word", "string"));
  }
}
