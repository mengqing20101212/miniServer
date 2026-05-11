package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class DreamEventNewConfigCheckerBase extends AbstractConfigChecker<DreamEventNewConfig> {
  @Override
  public String getConfigFileName() {
    return "dreamEventNew.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "resourceId", "INT"),
        new ConfigColumnMeta(2, "mechanism", "STRING"),
        new ConfigColumnMeta(3, "heroCount", "INT"),
        new ConfigColumnMeta(4, "limit", "LIST<INT>"));
  }
}
