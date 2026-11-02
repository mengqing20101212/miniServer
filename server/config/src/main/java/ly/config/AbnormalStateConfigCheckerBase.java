package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class AbnormalStateConfigCheckerBase extends AbstractConfigChecker<AbnormalStateConfig> {
  @Override
  public String getConfigFileName() {
    return "abnormalState.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "name", "string"),
        new ConfigColumnMeta(2, "type", "int"),
        new ConfigColumnMeta(3, "canReTrigger", "int"),
        new ConfigColumnMeta(4, "playAni", "int"),
        new ConfigColumnMeta(5, "playShowAni", "int"),
        new ConfigColumnMeta(6, "skipTurn", "int"),
        new ConfigColumnMeta(7, "banEnergySkill", "int"),
        new ConfigColumnMeta(8, "banPassiveSkill", "int"),
        new ConfigColumnMeta(9, "banSSkill", "int"),
        new ConfigColumnMeta(10, "canTriggerSkill", "int"),
        new ConfigColumnMeta(11, "selectType", "int"),
        new ConfigColumnMeta(12, "dispelList", "string"),
        new ConfigColumnMeta(13, "preventList", "string"),
        new ConfigColumnMeta(14, "isPlayAnim", "int"),
        new ConfigColumnMeta(15, "stateAnim", "string"),
        new ConfigColumnMeta(16, "stateDead", "string"),
        new ConfigColumnMeta(17, "stateStart", "string"),
        new ConfigColumnMeta(18, "stateFinish", "string"),
        new ConfigColumnMeta(19, "startEffect", "string"),
        new ConfigColumnMeta(20, "runEffect", "string"),
        new ConfigColumnMeta(21, "endEffect", "string"),
        new ConfigColumnMeta(22, "statePriority", "int"),
        new ConfigColumnMeta(23, "damageHitPro", "int"));
  }
}
