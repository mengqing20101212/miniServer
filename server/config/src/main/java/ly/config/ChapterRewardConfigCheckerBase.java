package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ChapterRewardConfigCheckerBase extends AbstractConfigChecker<ChapterRewardConfig> {
  @Override
  public String getConfigFileName() {
    return "chapterReward.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "StageId", "int"),
        new ConfigColumnMeta(2, "type", "int"),
        new ConfigColumnMeta(3, "rewardShow", "string"),
        new ConfigColumnMeta(4, "drop", "int"),
        new ConfigColumnMeta(5, "word1", "string"),
        new ConfigColumnMeta(6, "word2", "string"),
        new ConfigColumnMeta(7, "outGradientColor", "string"),
        new ConfigColumnMeta(8, "word3", "string"),
        new ConfigColumnMeta(9, "word4", "string"),
        new ConfigColumnMeta(10, "inGradientColor", "string"),
        new ConfigColumnMeta(11, "word5", "string"),
        new ConfigColumnMeta(12, "icon", "int"),
        new ConfigColumnMeta(13, "popUp", "int"),
        new ConfigColumnMeta(14, "popUpUIIcon", "int"));
  }
}
