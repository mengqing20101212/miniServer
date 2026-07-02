package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class Resource_index2ConfigCheckerBase extends AbstractConfigChecker<Resource_index2Config> {
  @Override
  public String getConfigFileName() {
    return "resource_index2.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "resname", "STRING"),
        new ConfigColumnMeta(2, "respath", "STRING"),
        new ConfigColumnMeta(3, "language", "INT"),
        new ConfigColumnMeta(4, "restype", "STRING"),
        new ConfigColumnMeta(5, "resident", "INT"),
        new ConfigColumnMeta(6, "des", "STRING"));
  }
}
