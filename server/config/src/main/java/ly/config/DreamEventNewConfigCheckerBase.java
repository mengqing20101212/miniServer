package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class DreamEventNewConfigCheckerBase extends AbstractConfigChecker<DreamEventNewConfig> {
  @Override
  public String getConfigFileName() {
    return "dreamEventNew.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "resourceId", "int"),
        new ConfigColumnMeta(2, "mechanism", "string"),
        new ConfigColumnMeta(3, "heroCount", "int"),
        new ConfigColumnMeta(4, "limit", "list<int>"));
  }
}
