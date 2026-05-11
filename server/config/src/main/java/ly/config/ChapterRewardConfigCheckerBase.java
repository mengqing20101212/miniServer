package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ChapterRewardConfigCheckerBase extends AbstractConfigChecker<ChapterRewardConfig> {
  @Override
  public String getConfigFileName() {
    return "chapterReward.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "StageId", "INT"),
        new ConfigColumnMeta(2, "type", "INT"),
        new ConfigColumnMeta(3, "rewardShow", "STRING"),
        new ConfigColumnMeta(4, "drop", "INT"),
        new ConfigColumnMeta(5, "word1", "STRING"),
        new ConfigColumnMeta(6, "word2", "STRING"),
        new ConfigColumnMeta(7, "outGradientColor", "STRING"),
        new ConfigColumnMeta(8, "word3", "STRING"),
        new ConfigColumnMeta(9, "word4", "STRING"),
        new ConfigColumnMeta(10, "inGradientColor", "STRING"),
        new ConfigColumnMeta(11, "word5", "STRING"),
        new ConfigColumnMeta(12, "icon", "INT"),
        new ConfigColumnMeta(13, "popUp", "INT"),
        new ConfigColumnMeta(14, "popUpUIIcon", "INT"));
  }
}
