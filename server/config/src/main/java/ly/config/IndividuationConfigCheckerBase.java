package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class IndividuationConfigCheckerBase extends AbstractConfigChecker<IndividuationConfig> {
  @Override
  public String getConfigFileName() {
    return "individuation.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "type", "int"),
        new ConfigColumnMeta(2, "item", "int"),
        new ConfigColumnMeta(3, "param_1", "string"),
        new ConfigColumnMeta(4, "sceneInfoId", "string"),
        new ConfigColumnMeta(5, "picRes", "string"),
        new ConfigColumnMeta(6, "name", "string"),
        new ConfigColumnMeta(7, "dec", "string"));
  }
}
