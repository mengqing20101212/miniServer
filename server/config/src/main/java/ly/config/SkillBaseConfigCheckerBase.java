package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class SkillBaseConfigCheckerBase extends AbstractConfigChecker<SkillBaseConfig> {
  @Override
  public String getConfigFileName() {
    return "skillBase.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "name", "STRING"),
        new ConfigColumnMeta(2, "description", "STRING"),
        new ConfigColumnMeta(3, "upgradeDes", "STRING"),
        new ConfigColumnMeta(4, "detail", "STRING"),
        new ConfigColumnMeta(5, "bonusTrigger", "INT"),
        new ConfigColumnMeta(6, "popLocId", "STRING"),
        new ConfigColumnMeta(7, "isPopShow", "INT"),
        new ConfigColumnMeta(8, "popType", "INT"),
        new ConfigColumnMeta(9, "icon", "INT"),
        new ConfigColumnMeta(10, "showSGId", "INT"),
        new ConfigColumnMeta(11, "skillBase", "INT"),
        new ConfigColumnMeta(12, "skillGroupId", "INT"),
        new ConfigColumnMeta(13, "mutexType", "INT"),
        new ConfigColumnMeta(14, "priority", "INT"),
        new ConfigColumnMeta(15, "group", "INT"),
        new ConfigColumnMeta(16, "isTriggerInState", "INT"),
        new ConfigColumnMeta(17, "transType", "INT"),
        new ConfigColumnMeta(18, "transCondition", "STRING"),
        new ConfigColumnMeta(19, "transSkills", "STRING"),
        new ConfigColumnMeta(20, "skillTargetType", "INT"),
        new ConfigColumnMeta(21, "canZombie", "INT"),
        new ConfigColumnMeta(22, "isDelayDeath", "INT"),
        new ConfigColumnMeta(23, "isCasterDieInSkill", "INT"),
        new ConfigColumnMeta(24, "isBasic", "INT"),
        new ConfigColumnMeta(25, "isAttack", "INT"),
        new ConfigColumnMeta(26, "isPassive", "INT"),
        new ConfigColumnMeta(27, "isEnergy", "INT"),
        new ConfigColumnMeta(28, "isTrigger", "INT"),
        new ConfigColumnMeta(29, "isSuper", "INT"),
        new ConfigColumnMeta(30, "isSummon", "INT"),
        new ConfigColumnMeta(31, "consumeEnergy", "INT"),
        new ConfigColumnMeta(32, "isUseAtStart", "INT"),
        new ConfigColumnMeta(33, "cd", "INT"),
        new ConfigColumnMeta(34, "behaviorTreeName", "STRING"),
        new ConfigColumnMeta(35, "skillLv", "INT"),
        new ConfigColumnMeta(36, "upgradeItems", "STRING"),
        new ConfigColumnMeta(37, "replaceItems", "INT"),
        new ConfigColumnMeta(38, "flags", "STRING"));
  }
}
