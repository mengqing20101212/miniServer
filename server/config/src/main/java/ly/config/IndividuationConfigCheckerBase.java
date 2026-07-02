package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class IndividuationConfigCheckerBase extends AbstractConfigChecker<IndividuationConfig> {
  @Override
  public String getConfigFileName() {
    return "individuation.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "type", "INT"),
        new ConfigColumnMeta(2, "item", "INT"),
        new ConfigColumnMeta(3, "param_1", "STRING"),
        new ConfigColumnMeta(4, "sceneInfoId", "STRING"),
        new ConfigColumnMeta(5, "picRes", "STRING"),
        new ConfigColumnMeta(6, "name", "STRING"),
        new ConfigColumnMeta(7, "dec", "STRING"));
  }
}
