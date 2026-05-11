package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class HeroStoryStatusConfigCheckerBase extends AbstractConfigChecker<HeroStoryStatusConfig> {
  @Override
  public String getConfigFileName() {
    return "heroStoryStatus.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "statusType", "INT"),
        new ConfigColumnMeta(2, "effectWord", "STRING"),
        new ConfigColumnMeta(3, "paraType", "INT"),
        new ConfigColumnMeta(4, "para", "INT"),
        new ConfigColumnMeta(5, "statusShow", "INT"));
  }
}
