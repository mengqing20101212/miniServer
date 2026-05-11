package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class SkillEffectConfigCheckerBase extends AbstractConfigChecker<SkillEffectConfig> {
  @Override
  public String getConfigFileName() {
    return "skillEffect.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "effectType", "INT"),
        new ConfigColumnMeta(2, "targetType", "STRING"),
        new ConfigColumnMeta(3, "name", "STRING"),
        new ConfigColumnMeta(4, "description", "STRING"),
        new ConfigColumnMeta(5, "isTriggerCircuit", "INT"),
        new ConfigColumnMeta(6, "isTriggerPassiveSkill", "INT"),
        new ConfigColumnMeta(7, "casterStrikeFlyTriggerFlag", "INT"),
        new ConfigColumnMeta(8, "targetStrikeFlyTriggerFlag", "INT"),
        new ConfigColumnMeta(9, "targetTypeEx", "INT"),
        new ConfigColumnMeta(10, "targetTypeEx2", "STRING"),
        new ConfigColumnMeta(11, "rangeType", "INT"),
        new ConfigColumnMeta(12, "buffInfluence", "STRING"),
        new ConfigColumnMeta(13, "addProType", "INT"),
        new ConfigColumnMeta(14, "addPro", "INT"),
        new ConfigColumnMeta(15, "entityTagFilters", "STRING"),
        new ConfigColumnMeta(16, "endEffects", "STRING"),
        new ConfigColumnMeta(17, "spCoa1", "INT"),
        new ConfigColumnMeta(18, "spCoa2", "INT"),
        new ConfigColumnMeta(19, "startPerformance", "STRING"),
        new ConfigColumnMeta(20, "runPerformance", "STRING"),
        new ConfigColumnMeta(21, "missPerformance", "STRING"),
        new ConfigColumnMeta(22, "endPerformance", "STRING"),
        new ConfigColumnMeta(23, "param_1", "STRING"),
        new ConfigColumnMeta(24, "param_2", "STRING"),
        new ConfigColumnMeta(25, "param_3", "STRING"),
        new ConfigColumnMeta(26, "param_4", "STRING"),
        new ConfigColumnMeta(27, "param_5", "STRING"),
        new ConfigColumnMeta(28, "param_6", "STRING"),
        new ConfigColumnMeta(29, "param_7", "STRING"),
        new ConfigColumnMeta(30, "param_8", "STRING"),
        new ConfigColumnMeta(31, "param_9", "STRING"),
        new ConfigColumnMeta(32, "param_10", "STRING"),
        new ConfigColumnMeta(33, "heroId", "INT"),
        new ConfigColumnMeta(34, "skillSequence", "INT"),
        new ConfigColumnMeta(35, "effectSequence", "INT"),
        new ConfigColumnMeta(36, "effectRenew", "STRING"));
  }
}
