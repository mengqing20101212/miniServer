package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class DropShow2ConfigCheckerBase extends AbstractConfigChecker<DropShow2Config> {
  @Override
  public String getConfigFileName() {
    return "dropShow2.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "beizhu", "STRING"),
        new ConfigColumnMeta(2, "beizhu2", "STRING"),
        new ConfigColumnMeta(3, "itemShow", "STRING"),
        new ConfigColumnMeta(4, "cornerMark", "STRING"));
  }
}
