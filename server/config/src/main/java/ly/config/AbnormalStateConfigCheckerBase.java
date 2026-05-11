package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class AbnormalStateConfigCheckerBase extends AbstractConfigChecker<AbnormalStateConfig> {
  @Override
  public String getConfigFileName() {
    return "abnormalState.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "name", "STRING"),
        new ConfigColumnMeta(2, "type", "INT"),
        new ConfigColumnMeta(3, "canReTrigger", "INT"),
        new ConfigColumnMeta(4, "playAni", "INT"),
        new ConfigColumnMeta(5, "playShowAni", "INT"),
        new ConfigColumnMeta(6, "skipTurn", "INT"),
        new ConfigColumnMeta(7, "banEnergySkill", "INT"),
        new ConfigColumnMeta(8, "banPassiveSkill", "INT"),
        new ConfigColumnMeta(9, "banSSkill", "INT"),
        new ConfigColumnMeta(10, "canTriggerSkill", "INT"),
        new ConfigColumnMeta(11, "selectType", "INT"),
        new ConfigColumnMeta(12, "dispelList", "STRING"),
        new ConfigColumnMeta(13, "preventList", "STRING"),
        new ConfigColumnMeta(14, "isPlayAnim", "INT"),
        new ConfigColumnMeta(15, "stateAnim", "STRING"),
        new ConfigColumnMeta(16, "stateDead", "STRING"),
        new ConfigColumnMeta(17, "stateStart", "STRING"),
        new ConfigColumnMeta(18, "stateFinish", "STRING"),
        new ConfigColumnMeta(19, "startEffect", "STRING"),
        new ConfigColumnMeta(20, "runEffect", "STRING"),
        new ConfigColumnMeta(21, "endEffect", "STRING"),
        new ConfigColumnMeta(22, "statePriority", "INT"),
        new ConfigColumnMeta(23, "damageHitPro", "INT"));
  }
}
