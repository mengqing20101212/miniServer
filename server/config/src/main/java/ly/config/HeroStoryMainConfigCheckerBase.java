package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class HeroStoryMainConfigCheckerBase extends AbstractConfigChecker<HeroStoryMainConfig> {
  @Override
  public String getConfigFileName() {
    return "heroStoryMain.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "storyName", "STRING"),
        new ConfigColumnMeta(2, "unlockLv", "INT"),
        new ConfigColumnMeta(3, "show", "INT"),
        new ConfigColumnMeta(4, "storyStage", "STRING"),
        new ConfigColumnMeta(5, "isShow", "INT"),
        new ConfigColumnMeta(6, "stageDec", "STRING"));
  }
}
