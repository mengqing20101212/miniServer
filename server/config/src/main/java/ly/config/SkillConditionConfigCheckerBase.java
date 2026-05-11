package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class SkillConditionConfigCheckerBase extends AbstractConfigChecker<SkillConditionConfig> {
  @Override
  public String getConfigFileName() {
    return "skillCondition.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "type", "INT"),
        new ConfigColumnMeta(2, "des", "STRING"),
        new ConfigColumnMeta(3, "param_1", "STRING"),
        new ConfigColumnMeta(4, "param_2", "STRING"),
        new ConfigColumnMeta(5, "param_3", "STRING"),
        new ConfigColumnMeta(6, "param_4", "STRING"),
        new ConfigColumnMeta(7, "param_5", "STRING"),
        new ConfigColumnMeta(8, "param_6", "STRING"),
        new ConfigColumnMeta(9, "param_7", "STRING"),
        new ConfigColumnMeta(10, "param_8", "STRING"),
        new ConfigColumnMeta(11, "param_9", "STRING"),
        new ConfigColumnMeta(12, "param_10", "STRING"),
        new ConfigColumnMeta(13, "heroId", "INT"),
        new ConfigColumnMeta(14, "skillSequence", "INT"),
        new ConfigColumnMeta(15, "effectSequence", "INT"));
  }
}
