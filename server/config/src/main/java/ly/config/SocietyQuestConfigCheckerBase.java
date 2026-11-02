package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class SocietyQuestConfigCheckerBase extends AbstractConfigChecker<SocietyQuestConfig> {
  @Override
  public String getConfigFileName() {
    return "societyQuest.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "questId", "int"),
        new ConfigColumnMeta(2, "questId2", "int"),
        new ConfigColumnMeta(3, "minLevel", "int"),
        new ConfigColumnMeta(4, "maxLevel", "int"),
        new ConfigColumnMeta(5, "title", "string"),
        new ConfigColumnMeta(6, "name", "string"),
        new ConfigColumnMeta(7, "name2", "string"),
        new ConfigColumnMeta(8, "star", "int"),
        new ConfigColumnMeta(9, "rewardType", "int"),
        new ConfigColumnMeta(10, "beizhu1", "string"),
        new ConfigColumnMeta(11, "weights", "int"),
        new ConfigColumnMeta(12, "isCooperate", "int"),
        new ConfigColumnMeta(13, "isRare", "int"),
        new ConfigColumnMeta(14, "rewardShow", "string"),
        new ConfigColumnMeta(15, "drop", "int"),
        new ConfigColumnMeta(16, "beizhu2", "string"),
        new ConfigColumnMeta(17, "beizhu3", "string"),
        new ConfigColumnMeta(18, "beizhu4", "string"),
        new ConfigColumnMeta(19, "beizhu5", "string"),
        new ConfigColumnMeta(20, "redirectionId", "int"),
        new ConfigColumnMeta(21, "redirectionId2", "int"),
        new ConfigColumnMeta(22, "goundId", "int"));
  }
}
