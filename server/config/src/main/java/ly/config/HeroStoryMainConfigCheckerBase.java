package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class HeroStoryMainConfigCheckerBase extends AbstractConfigChecker<HeroStoryMainConfig> {
  @Override
  public String getConfigFileName() {
    return "heroStoryMain.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "storyName", "string"),
        new ConfigColumnMeta(2, "unlockLv", "int"),
        new ConfigColumnMeta(3, "show", "int"),
        new ConfigColumnMeta(4, "storyStage", "string"),
        new ConfigColumnMeta(5, "isShow", "int"),
        new ConfigColumnMeta(6, "stageDec", "string"));
  }
}
