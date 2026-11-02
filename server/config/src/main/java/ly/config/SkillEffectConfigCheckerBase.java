package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class SkillEffectConfigCheckerBase extends AbstractConfigChecker<SkillEffectConfig> {
  @Override
  public String getConfigFileName() {
    return "skillEffect.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "effectType", "int"),
        new ConfigColumnMeta(2, "targetType", "string"),
        new ConfigColumnMeta(3, "name", "string"),
        new ConfigColumnMeta(4, "description", "string"),
        new ConfigColumnMeta(5, "isTriggerCircuit", "int"),
        new ConfigColumnMeta(6, "isTriggerPassiveSkill", "int"),
        new ConfigColumnMeta(7, "casterStrikeFlyTriggerFlag", "int"),
        new ConfigColumnMeta(8, "targetStrikeFlyTriggerFlag", "int"),
        new ConfigColumnMeta(9, "targetTypeEx", "int"),
        new ConfigColumnMeta(10, "targetTypeEx2", "string"),
        new ConfigColumnMeta(11, "rangeType", "int"),
        new ConfigColumnMeta(12, "buffInfluence", "string"),
        new ConfigColumnMeta(13, "addProType", "int"),
        new ConfigColumnMeta(14, "addPro", "int"),
        new ConfigColumnMeta(15, "entityTagFilters", "string"),
        new ConfigColumnMeta(16, "endEffects", "string"),
        new ConfigColumnMeta(17, "spCoa1", "int"),
        new ConfigColumnMeta(18, "spCoa2", "int"),
        new ConfigColumnMeta(19, "startPerformance", "string"),
        new ConfigColumnMeta(20, "runPerformance", "string"),
        new ConfigColumnMeta(21, "missPerformance", "string"),
        new ConfigColumnMeta(22, "endPerformance", "string"),
        new ConfigColumnMeta(23, "param_1", "string"),
        new ConfigColumnMeta(24, "param_2", "string"),
        new ConfigColumnMeta(25, "param_3", "string"),
        new ConfigColumnMeta(26, "param_4", "string"),
        new ConfigColumnMeta(27, "param_5", "string"),
        new ConfigColumnMeta(28, "param_6", "string"),
        new ConfigColumnMeta(29, "param_7", "string"),
        new ConfigColumnMeta(30, "param_8", "string"),
        new ConfigColumnMeta(31, "param_9", "string"),
        new ConfigColumnMeta(32, "param_10", "string"),
        new ConfigColumnMeta(33, "heroId", "int"),
        new ConfigColumnMeta(34, "skillSequence", "int"),
        new ConfigColumnMeta(35, "effectSequence", "int"),
        new ConfigColumnMeta(36, "effectRenew", "string"));
  }
}
