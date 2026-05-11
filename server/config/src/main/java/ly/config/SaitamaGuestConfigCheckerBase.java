package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class SaitamaGuestConfigCheckerBase extends AbstractConfigChecker<SaitamaGuestConfig> {
  @Override
  public String getConfigFileName() {
    return "saitamaGuest.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "probability", "INT"),
        new ConfigColumnMeta(2, "weight", "INT"),
        new ConfigColumnMeta(3, "dropId", "INT"),
        new ConfigColumnMeta(4, "name", "STRING"),
        new ConfigColumnMeta(5, "model", "STRING"));
  }
}
