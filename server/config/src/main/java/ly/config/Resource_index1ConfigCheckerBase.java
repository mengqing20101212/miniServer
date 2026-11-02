package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class Resource_index1ConfigCheckerBase extends AbstractConfigChecker<Resource_index1Config> {
  @Override
  public String getConfigFileName() {
    return "resource_index1.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "resname", "string"),
        new ConfigColumnMeta(2, "respath", "string"),
        new ConfigColumnMeta(3, "language", "int"),
        new ConfigColumnMeta(4, "restype", "string"),
        new ConfigColumnMeta(5, "resident", "int"),
        new ConfigColumnMeta(6, "des", "string"));
  }
}
