package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class SkillBuffConfigCheckerBase extends AbstractConfigChecker<SkillBuffConfig> {
  @Override
  public String getConfigFileName() {
    return "skillBuff.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "effectType", "int"),
        new ConfigColumnMeta(2, "targetType", "string"),
        new ConfigColumnMeta(3, "name", "string"),
        new ConfigColumnMeta(4, "description", "string"),
        new ConfigColumnMeta(5, "icon", "int"),
        new ConfigColumnMeta(6, "suitId", "string"),
        new ConfigColumnMeta(7, "display", "int"),
        new ConfigColumnMeta(8, "displayType", "int"),
        new ConfigColumnMeta(9, "targetTypeEx", "int"),
        new ConfigColumnMeta(10, "targetTypeEx2", "string"),
        new ConfigColumnMeta(11, "originSkillGroupId", "int"),
        new ConfigColumnMeta(12, "banPassiveType", "int"),
        new ConfigColumnMeta(13, "isPassive", "int"),
        new ConfigColumnMeta(14, "isCircuit", "int"),
        new ConfigColumnMeta(15, "isTriggerCircuit", "int"),
        new ConfigColumnMeta(16, "isTriggerPassiveSkill", "int"),
        new ConfigColumnMeta(17, "casterStrikeFlyTriggerFlag", "int"),
        new ConfigColumnMeta(18, "targetStrikeFlyTriggerFlag", "int"),
        new ConfigColumnMeta(19, "targetStrikeFlyAddFlag", "int"),
        new ConfigColumnMeta(20, "mutexType", "int"),
        new ConfigColumnMeta(21, "mutexPriority", "int"),
        new ConfigColumnMeta(22, "rangeType", "int"),
        new ConfigColumnMeta(23, "buffInfluence", "string"),
        new ConfigColumnMeta(24, "initStack", "int"),
        new ConfigColumnMeta(25, "baseStack", "int"),
        new ConfigColumnMeta(26, "maxStack", "int"),
        new ConfigColumnMeta(27, "coexist", "int"),
        new ConfigColumnMeta(28, "refreshStack", "int"),
        new ConfigColumnMeta(29, "addProType", "int"),
        new ConfigColumnMeta(30, "addPro", "int"),
        new ConfigColumnMeta(31, "continuousType", "int"),
        new ConfigColumnMeta(32, "continuousValue", "string"),
        new ConfigColumnMeta(33, "triggerType", "int"),
        new ConfigColumnMeta(34, "triggerInterval", "int"),
        new ConfigColumnMeta(35, "triggerPro", "int"),
        new ConfigColumnMeta(36, "triggerLimitPerTurn", "int"),
        new ConfigColumnMeta(37, "triggerConsumeStack", "int"),
        new ConfigColumnMeta(38, "entityTagFilters", "string"),
        new ConfigColumnMeta(39, "breakType", "string"),
        new ConfigColumnMeta(40, "breakEffectPro", "int"),
        new ConfigColumnMeta(41, "breakEffects", "string"),
        new ConfigColumnMeta(42, "endEffects", "string"),
        new ConfigColumnMeta(43, "consumeEffects", "string"),
        new ConfigColumnMeta(44, "spCoa1", "int"),
        new ConfigColumnMeta(45, "spCoa2", "int"),
        new ConfigColumnMeta(46, "startPerformance", "string"),
        new ConfigColumnMeta(47, "runPerformance", "string"),
        new ConfigColumnMeta(48, "breakPerformance", "string"),
        new ConfigColumnMeta(49, "missPerformance", "string"),
        new ConfigColumnMeta(50, "endPerformance", "string"),
        new ConfigColumnMeta(51, "triggerLength", "float"),
        new ConfigColumnMeta(52, "param_1", "string"),
        new ConfigColumnMeta(53, "param_2", "string"),
        new ConfigColumnMeta(54, "param_3", "string"),
        new ConfigColumnMeta(55, "param_4", "string"),
        new ConfigColumnMeta(56, "param_5", "string"),
        new ConfigColumnMeta(57, "param_6", "string"),
        new ConfigColumnMeta(58, "param_7", "string"),
        new ConfigColumnMeta(59, "param_8", "string"),
        new ConfigColumnMeta(60, "param_9", "string"),
        new ConfigColumnMeta(61, "param_10", "string"),
        new ConfigColumnMeta(62, "heroId", "int"),
        new ConfigColumnMeta(63, "skillSequence", "int"),
        new ConfigColumnMeta(64, "effectSequence", "int"),
        new ConfigColumnMeta(65, "isReAdd", "int"),
        new ConfigColumnMeta(66, "effectRenew", "string"),
        new ConfigColumnMeta(67, "modelPerformance", "string"));
  }
}
