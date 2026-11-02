package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class HeroStoryStatusConfigCheckerBase extends AbstractConfigChecker<HeroStoryStatusConfig> {
  @Override
  public String getConfigFileName() {
    return "heroStoryStatus.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "statusType", "int"),
        new ConfigColumnMeta(2, "effectWord", "string"),
        new ConfigColumnMeta(3, "paraType", "int"),
        new ConfigColumnMeta(4, "para", "int"),
        new ConfigColumnMeta(5, "statusShow", "int"));
  }
}
