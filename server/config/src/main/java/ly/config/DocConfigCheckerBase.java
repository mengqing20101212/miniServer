package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class DocConfigCheckerBase extends AbstractConfigChecker<DocConfig> {
  @Override
  public String getConfigFileName() {
    return "doc.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "Nouse", "int"),
        new ConfigColumnMeta(1, "id", "int"),
        new ConfigColumnMeta(2, "value", "string"));
  }
}
