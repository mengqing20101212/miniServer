package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class HeroStoryStageConfigCheckerBase extends AbstractConfigChecker<HeroStoryStageConfig> {
  @Override
  public String getConfigFileName() {
    return "heroStoryStage.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "stageName", "STRING"),
        new ConfigColumnMeta(2, "groupId", "INT"),
        new ConfigColumnMeta(3, "nextId", "INT"),
        new ConfigColumnMeta(4, "lastId", "INT"),
        new ConfigColumnMeta(5, "sceneId", "INT"),
        new ConfigColumnMeta(6, "statusBonus", "INT"),
        new ConfigColumnMeta(7, "dropShow", "INT"),
        new ConfigColumnMeta(8, "storyBanner", "INT"),
        new ConfigColumnMeta(9, "storyWord", "STRING"),
        new ConfigColumnMeta(10, "mechanism", "STRING"));
  }
}
