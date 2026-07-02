package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class SocietyQuestConfigCheckerBase extends AbstractConfigChecker<SocietyQuestConfig> {
  @Override
  public String getConfigFileName() {
    return "societyQuest.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "questId", "INT"),
        new ConfigColumnMeta(2, "questId2", "INT"),
        new ConfigColumnMeta(3, "minLevel", "INT"),
        new ConfigColumnMeta(4, "maxLevel", "INT"),
        new ConfigColumnMeta(5, "title", "STRING"),
        new ConfigColumnMeta(6, "name", "STRING"),
        new ConfigColumnMeta(7, "name2", "STRING"),
        new ConfigColumnMeta(8, "star", "INT"),
        new ConfigColumnMeta(9, "rewardType", "INT"),
        new ConfigColumnMeta(10, "beizhu1", "STRING"),
        new ConfigColumnMeta(11, "weights", "INT"),
        new ConfigColumnMeta(12, "isCooperate", "INT"),
        new ConfigColumnMeta(13, "isRare", "INT"),
        new ConfigColumnMeta(14, "rewardShow", "STRING"),
        new ConfigColumnMeta(15, "drop", "INT"),
        new ConfigColumnMeta(16, "beizhu2", "STRING"),
        new ConfigColumnMeta(17, "beizhu3", "STRING"),
        new ConfigColumnMeta(18, "beizhu4", "STRING"),
        new ConfigColumnMeta(19, "beizhu5", "STRING"),
        new ConfigColumnMeta(20, "redirectionId", "INT"),
        new ConfigColumnMeta(21, "redirectionId2", "INT"),
        new ConfigColumnMeta(22, "goundId", "INT"));
  }
}
