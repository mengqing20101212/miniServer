package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class SkillBuffConfigCheckerBase extends AbstractConfigChecker<SkillBuffConfig> {
  @Override
  public String getConfigFileName() {
    return "skillBuff.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "effectType", "INT"),
        new ConfigColumnMeta(2, "targetType", "STRING"),
        new ConfigColumnMeta(3, "name", "STRING"),
        new ConfigColumnMeta(4, "description", "STRING"),
        new ConfigColumnMeta(5, "icon", "INT"),
        new ConfigColumnMeta(6, "suitId", "STRING"),
        new ConfigColumnMeta(7, "display", "INT"),
        new ConfigColumnMeta(8, "displayType", "INT"),
        new ConfigColumnMeta(9, "targetTypeEx", "INT"),
        new ConfigColumnMeta(10, "targetTypeEx2", "STRING"),
        new ConfigColumnMeta(11, "originSkillGroupId", "INT"),
        new ConfigColumnMeta(12, "banPassiveType", "INT"),
        new ConfigColumnMeta(13, "isPassive", "INT"),
        new ConfigColumnMeta(14, "isCircuit", "INT"),
        new ConfigColumnMeta(15, "isTriggerCircuit", "INT"),
        new ConfigColumnMeta(16, "isTriggerPassiveSkill", "INT"),
        new ConfigColumnMeta(17, "casterStrikeFlyTriggerFlag", "INT"),
        new ConfigColumnMeta(18, "targetStrikeFlyTriggerFlag", "INT"),
        new ConfigColumnMeta(19, "targetStrikeFlyAddFlag", "INT"),
        new ConfigColumnMeta(20, "mutexType", "INT"),
        new ConfigColumnMeta(21, "mutexPriority", "INT"),
        new ConfigColumnMeta(22, "rangeType", "INT"),
        new ConfigColumnMeta(23, "buffInfluence", "STRING"),
        new ConfigColumnMeta(24, "initStack", "INT"),
        new ConfigColumnMeta(25, "baseStack", "INT"),
        new ConfigColumnMeta(26, "maxStack", "INT"),
        new ConfigColumnMeta(27, "coexist", "INT"),
        new ConfigColumnMeta(28, "refreshStack", "INT"),
        new ConfigColumnMeta(29, "addProType", "INT"),
        new ConfigColumnMeta(30, "addPro", "INT"),
        new ConfigColumnMeta(31, "continuousType", "INT"),
        new ConfigColumnMeta(32, "continuousValue", "STRING"),
        new ConfigColumnMeta(33, "triggerType", "INT"),
        new ConfigColumnMeta(34, "triggerInterval", "INT"),
        new ConfigColumnMeta(35, "triggerPro", "INT"),
        new ConfigColumnMeta(36, "triggerLimitPerTurn", "INT"),
        new ConfigColumnMeta(37, "triggerConsumeStack", "INT"),
        new ConfigColumnMeta(38, "entityTagFilters", "STRING"),
        new ConfigColumnMeta(39, "breakType", "STRING"),
        new ConfigColumnMeta(40, "breakEffectPro", "INT"),
        new ConfigColumnMeta(41, "breakEffects", "STRING"),
        new ConfigColumnMeta(42, "endEffects", "STRING"),
        new ConfigColumnMeta(43, "consumeEffects", "STRING"),
        new ConfigColumnMeta(44, "spCoa1", "INT"),
        new ConfigColumnMeta(45, "spCoa2", "INT"),
        new ConfigColumnMeta(46, "startPerformance", "STRING"),
        new ConfigColumnMeta(47, "runPerformance", "STRING"),
        new ConfigColumnMeta(48, "breakPerformance", "STRING"),
        new ConfigColumnMeta(49, "missPerformance", "STRING"),
        new ConfigColumnMeta(50, "endPerformance", "STRING"),
        new ConfigColumnMeta(51, "triggerLength", "FLOAT"),
        new ConfigColumnMeta(52, "param_1", "STRING"),
        new ConfigColumnMeta(53, "param_2", "STRING"),
        new ConfigColumnMeta(54, "param_3", "STRING"),
        new ConfigColumnMeta(55, "param_4", "STRING"),
        new ConfigColumnMeta(56, "param_5", "STRING"),
        new ConfigColumnMeta(57, "param_6", "STRING"),
        new ConfigColumnMeta(58, "param_7", "STRING"),
        new ConfigColumnMeta(59, "param_8", "STRING"),
        new ConfigColumnMeta(60, "param_9", "STRING"),
        new ConfigColumnMeta(61, "param_10", "STRING"),
        new ConfigColumnMeta(62, "heroId", "INT"),
        new ConfigColumnMeta(63, "skillSequence", "INT"),
        new ConfigColumnMeta(64, "effectSequence", "INT"),
        new ConfigColumnMeta(65, "isReAdd", "INT"),
        new ConfigColumnMeta(66, "effectRenew", "STRING"),
        new ConfigColumnMeta(67, "modelPerformance", "STRING"));
  }
}
