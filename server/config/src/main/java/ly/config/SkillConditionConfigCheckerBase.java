package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class SkillConditionConfigCheckerBase extends AbstractConfigChecker<SkillConditionConfig> {
  @Override
  public String getConfigFileName() {
    return "skillCondition.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "type", "int"),
        new ConfigColumnMeta(2, "des", "string"),
        new ConfigColumnMeta(3, "param_1", "string"),
        new ConfigColumnMeta(4, "param_2", "string"),
        new ConfigColumnMeta(5, "param_3", "string"),
        new ConfigColumnMeta(6, "param_4", "string"),
        new ConfigColumnMeta(7, "param_5", "string"),
        new ConfigColumnMeta(8, "param_6", "string"),
        new ConfigColumnMeta(9, "param_7", "string"),
        new ConfigColumnMeta(10, "param_8", "string"),
        new ConfigColumnMeta(11, "param_9", "string"),
        new ConfigColumnMeta(12, "param_10", "string"),
        new ConfigColumnMeta(13, "heroId", "int"),
        new ConfigColumnMeta(14, "skillSequence", "int"),
        new ConfigColumnMeta(15, "effectSequence", "int"));
  }
}
