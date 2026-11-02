package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class SkillBaseConfigCheckerBase extends AbstractConfigChecker<SkillBaseConfig> {
  @Override
  public String getConfigFileName() {
    return "skillBase.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "name", "string"),
        new ConfigColumnMeta(2, "description", "string"),
        new ConfigColumnMeta(3, "upgradeDes", "string"),
        new ConfigColumnMeta(4, "detail", "string"),
        new ConfigColumnMeta(5, "bonusTrigger", "int"),
        new ConfigColumnMeta(6, "popLocId", "string"),
        new ConfigColumnMeta(7, "isPopShow", "int"),
        new ConfigColumnMeta(8, "popType", "int"),
        new ConfigColumnMeta(9, "icon", "int"),
        new ConfigColumnMeta(10, "showSGId", "int"),
        new ConfigColumnMeta(11, "skillBase", "int"),
        new ConfigColumnMeta(12, "skillGroupId", "int"),
        new ConfigColumnMeta(13, "mutexType", "int"),
        new ConfigColumnMeta(14, "priority", "int"),
        new ConfigColumnMeta(15, "group", "int"),
        new ConfigColumnMeta(16, "isTriggerInState", "int"),
        new ConfigColumnMeta(17, "transType", "int"),
        new ConfigColumnMeta(18, "transCondition", "string"),
        new ConfigColumnMeta(19, "transSkills", "string"),
        new ConfigColumnMeta(20, "skillTargetType", "int"),
        new ConfigColumnMeta(21, "canZombie", "int"),
        new ConfigColumnMeta(22, "isDelayDeath", "int"),
        new ConfigColumnMeta(23, "isCasterDieInSkill", "int"),
        new ConfigColumnMeta(24, "isBasic", "int"),
        new ConfigColumnMeta(25, "isAttack", "int"),
        new ConfigColumnMeta(26, "isPassive", "int"),
        new ConfigColumnMeta(27, "isEnergy", "int"),
        new ConfigColumnMeta(28, "isTrigger", "int"),
        new ConfigColumnMeta(29, "isSuper", "int"),
        new ConfigColumnMeta(30, "isSummon", "int"),
        new ConfigColumnMeta(31, "consumeEnergy", "int"),
        new ConfigColumnMeta(32, "isUseAtStart", "int"),
        new ConfigColumnMeta(33, "cd", "int"),
        new ConfigColumnMeta(34, "behaviorTreeName", "string"),
        new ConfigColumnMeta(35, "skillLv", "int"),
        new ConfigColumnMeta(36, "upgradeItems", "string"),
        new ConfigColumnMeta(37, "replaceItems", "int"),
        new ConfigColumnMeta(38, "flags", "string"));
  }
}
