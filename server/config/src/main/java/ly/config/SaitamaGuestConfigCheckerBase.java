package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class SaitamaGuestConfigCheckerBase extends AbstractConfigChecker<SaitamaGuestConfig> {
  @Override
  public String getConfigFileName() {
    return "saitamaGuest.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "probability", "int"),
        new ConfigColumnMeta(2, "weight", "int"),
        new ConfigColumnMeta(3, "dropId", "int"),
        new ConfigColumnMeta(4, "name", "string"),
        new ConfigColumnMeta(5, "model", "string"));
  }
}
