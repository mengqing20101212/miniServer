package ly.battle;

import ly.battle.skill.BaseSkill;
import ly.battle.unit.BattleUnit;
import ly.proto.Battle.BattleSideSnapshot;
import ly.proto.Battle.BattleSkillSlot;
import ly.proto.Battle.BattleUnitSnapshot;
import ly.proto.Battle.PRT_BATTLE_STAGE;

import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

public final class BattleEngine {
    private BattleEngine() {}

    /** 双方和公式参数都来自开战快照，每次结算独占单位、技能、Buff 和战报。 */
    public static PRT_BATTLE_STAGE fight(long battleId, long battleTime, int type, int roundLimit,
                                         BattleRules.BattleDamageRules rules,
                                         BattleSideSnapshot attacker, BattleSideSnapshot defender) {
        Stage stage = new Stage(battleId, battleTime, type, roundLimit,
                attacker.hasPlayer() ? attacker.getPlayer() : null,
                defender.hasPlayer() ? defender.getPlayer() : null, rules);
        enter(stage, attacker, true);
        enter(stage, defender, false);
        stage.run();
        return stage.getReport();
    }

    private static void enter(Stage stage, BattleSideSnapshot side, boolean attacker) {
        if (side.getUnitsCount() == 0) {
            throw new IllegalArgumentException("双方必须有参战单位");
        }
        for (BattleUnitSnapshot snapshot : side.getUnitsList()) {
            List<BaseSkill> skills = new ArrayList<>();
            Set<Integer> slots = new HashSet<>();
            for (BattleSkillSlot skill : snapshot.getSkillsList()) {
                if (!slots.add(skill.getSlot())) {
                    throw new IllegalArgumentException("重复技能槽：" + skill.getSlot());
                }
                skills.add(new BaseSkill(stage::nextId, skill.getDefinition(), skill.getSlot(), skill.getLevel()));
            }
            stage.addUnit(new BattleUnit(stage.nextId(), attacker, snapshot, skills));
        }
    }
}
