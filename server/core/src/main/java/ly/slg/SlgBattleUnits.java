package ly.slg;

import ly.ConfigLoadException;
import ly.config.SlgBattleSkillConfig;
import ly.config.SlgBattleSkillConfigManager;
import ly.proto.Battle.*;

import static ly.proto.Battle.AttrTypeEnum.*;

/** 双方共用的一号阵位；技能和属性在这里展开，快照不持有读表器引用。 */
public final class SlgBattleUnits {
    private SlgBattleUnits() {}

    public static BattleUnitSnapshot.Builder single(PRT_HERO hero, long hp, long attack, long defence,
                                                    int capacity, SlgBattleSkillConfig active) {
        BattleUnitSnapshot.Builder unit = BattleUnitSnapshot.newBuilder().setPosition(1).setHero(hero)
                .putEntryAttrs(HP.getNumber(), hp).putEntryAttrs(ATK.getNumber(), attack)
                .putEntryAttrs(DEF.getNumber(), defence).putEntryAttrs(ARM.getNumber(), capacity)
                .setInitialEnergy(80).addSkills(slot(0, skill(320000)));
        if (active != null) unit.addSkills(slot(1, active));
        return unit;
    }

    public static SlgBattleSkillConfig skill(int id) {
        SlgBattleSkillConfig skill = SlgBattleSkillConfigManager.getInstance().getConfigMap().get(id);
        if (skill == null) throw new ConfigLoadException("slgBattleSkill 缺少技能 " + id);
        return skill;
    }

    private static BattleSkillSlot slot(int index, SlgBattleSkillConfig skill) {
        return BattleSkillSlot.newBuilder().setSlot(index).setLevel(1).setDefinition(BattleSkillSpec.newBuilder()
                .setId(skill.id).setEffectTypeValue(skill.effectType).setTargetRuleValue(skill.targetRule)
                .setTargetCount(skill.targetCount).setActionRate(skill.actionRate)).build();
    }
}
