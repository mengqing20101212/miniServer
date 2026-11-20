package ly.battle;

import com.google.protobuf.TextFormat;
import ly.battle.attr.AttrMap;
import ly.proto.Battle.*;
import org.junit.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HexFormat;

import static ly.proto.Battle.AttrTypeEnum.*;
import static org.junit.Assert.*;

public class BattleEngineTest {
    private static final BattleRules.BattleDamageRules RULES = new BattleRules.BattleDamageRules(1000, 800, 600, 1670);
    private static final BattleSkillSpec NORMAL = BattleSkillSpec.newBuilder().setId(320000)
            .setEffectType(BattleSkillSpec.Effect.DAMAGE).setTargetRule(BattleSkillSpec.Target.FRONT_COLUMN)
            .setTargetCount(1).setActionRate(1000).build();

    @Test
    public void fiveHeroesReproduceCompanyReportByteForByte() throws Exception {
        BattleSideSnapshot.Builder input = BattleSideSnapshot.newBuilder();
        try (InputStream file = getClass().getResourceAsStream("/five-heroes.textproto");
             InputStreamReader reader = new InputStreamReader(file, StandardCharsets.UTF_8)) {
            TextFormat.merge(reader, input);
        }
        BattleSideSnapshot attacker = BattleSideSnapshot.parseFrom(input.build().toByteArray());
        BattleSideSnapshot defender = input.clearPlayer().build();
        PRT_BATTLE_STAGE result = BattleEngine.fight(7, 123456789L, 0, 20, RULES, attacker, defender);
        assertEquals(19, result.getReport().getRoundsCount());
        assertEquals(PRT_BATTLE_SIDE.DEFENDER, result.getLog().getWinner());
        assertEquals(PRT_BATTLE_END_REASON.ELIMINATION, result.getLog().getEndReason());
        assertEquals(10, result.getLog().getUnitResultsCount());
        // 公司服 Stage 使用相同入场值和公式实跑所得，覆盖完整录像、技能统计和各兵阶入场信息。
        assertEquals("ed7d5f32a24fa7e9943d6e03b5c7b8bde1d2c586e5701c609a5618cd612d8b75",
                HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(result.toByteArray())));
        assertEquals(result, BattleEngine.fight(7, 123456789L, 0, 20, RULES, attacker, defender));
    }

    @Test
    public void simultaneousLethalNormalsBothLandAndDefenderWins() {
        BattleSideSnapshot side = side(unit(new AttrMap("HP;100|ATK;200|ARM;100")));
        PRT_BATTLE_STAGE result = fight(1, side, side);
        assertEquals(PRT_BATTLE_END_REASON.ELIMINATION, result.getLog().getEndReason());
        assertEquals(PRT_BATTLE_SIDE.DEFENDER, result.getLog().getWinner());
        for (PRT_BATTLE_UNIT_RESULT unit : result.getLog().getUnitResultsList()) {
            assertEquals(1, unit.getNormalAttackCount());
            assertEquals(100, unit.getDamageDealt());
            assertEquals(0, unit.getRemainingSoldiers());
            assertEquals(100, unit.getLostSoldiers());
        }
    }

    @Test
    public void roundLimitUsesRemainingHpExcludingShieldAndAwardsTieToDefender() {
        BattleUnitSnapshot.Builder unit = unit(new AttrMap("HP;200|ATK;10|ARM;100|SHIELD;50"));
        BattleSideSnapshot weak = side(unit.setRemainingHp(100));
        BattleSideSnapshot strong = side(unit.setRemainingHp(101));
        PRT_BATTLE_STAGE result = fight(1, strong, weak);
        assertEquals(PRT_BATTLE_END_REASON.ROUND_LIMIT, result.getLog().getEndReason());
        assertEquals(PRT_BATTLE_SIDE.ATTACKER, result.getLog().getWinner());
        assertEquals(PRT_BATTLE_SIDE.DEFENDER,
                fight(1, side(unit.setRemainingHp(100).putEntryAttrs(SHIELD.getNumber(), 500)), weak).getLog().getWinner());
    }

    @Test
    public void activeKillPreventsDefenderCastingOrAttacking() {
        BattleSkillSpec active = NORMAL.toBuilder().setId(320002).setActionRate(3000).build();
        BattleUnitSnapshot.Builder unit = unit(new AttrMap("HP;200|ATK;100|ARM;100"))
                .setInitialEnergy(80).addSkills(slot(1, active));
        PRT_BATTLE_STAGE result = fight(20, side(unit), side(unit));
        assertEquals(PRT_BATTLE_SIDE.ATTACKER, result.getLog().getWinner());
        assertEquals(1, result.getLog().getUnitResults(0).getSkillCastCount());
        assertEquals(0, result.getLog().getUnitResults(1).getSkillCastCount());
        assertEquals(0, result.getLog().getUnitResults(0).getNormalAttackCount());
    }

    @Test
    public void childSkillSeesImmediateBuffAndExpiryAppliesSuccessor() {
        BattleBuffSpec successor = BattleBuffSpec.newBuilder().setId(330002)
                .setEffectType(BattleBuffSpec.Effect.DAMAGE_REDUCTION).setValue(300)
                .setDurationRounds(3).setEffectTiming(1).build();
        BattleBuffSpec boost = BattleBuffSpec.newBuilder().setId(330003)
                .setEffectType(BattleBuffSpec.Effect.ATTACK_BOOST).setValue(500)
                .setDurationRounds(1).setEffectTiming(1).addOnRemoveBuffs(successor).build();
        BattleSkillSpec shield = BattleSkillSpec.newBuilder().setId(12)
                .setEffectType(BattleSkillSpec.Effect.SHIELD).setActionRate(1000)
                .setTargetRule(BattleSkillSpec.Target.ALLY_LOWEST_HP).setTargetCount(1).build();
        BattleSkillSpec root = BattleSkillSpec.newBuilder().setId(11)
                .setEffectType(BattleSkillSpec.Effect.NONE).setTargetRule(BattleSkillSpec.Target.ALL_ENEMIES)
                .addSelfBuffs(boost).addSubSkills(shield).build();
        BattleUnitSnapshot.Builder unit = unit(new AttrMap("HP;1000|ATK;100|ARM;100"));
        BattleSideSnapshot defender = side(unit);
        PRT_BATTLE_STAGE result = fight(2, side(unit.addSkills(slot(1, root)).setInitialEnergy(80)), defender);
        PRT_BATTLE_UNIT_RESULT attacker = result.getLog().getUnitResults(0);
        assertEquals(3, attacker.getSkillsCount());
        assertEquals(1, attacker.getSkills(2).getCastCount());
        assertEquals(300, attacker.getNormalAttackDamage());
        assertEquals(950, attr(attacker.getFinalState().getAttr(), CURRENT_HP));
        assertEquals(0, attr(attacker.getFinalState().getAttr(), SHIELD));
        assertEquals(0, attr(attacker.getFinalState().getAttr(), ATTACK_BOOST_RATE));
        assertEquals(300, attr(attacker.getFinalState().getAttr(), DAMAGE_REDUCTION_RATE));
        assertEquals(330002, attacker.getFinalState().getBuffs(0).getBuffTid());
    }

    @Test
    public void freezeKeepsEquipmentAndGrowthFromBeingAppliedTwice() {
        AttrMap panel = new AttrMap("HP;300|ATK;100|ATK_BASE_ADD;20|ATK_RATE;500|ATK_EXT_ADD;5|EQUIP_ATK;50|EQUIP_ATK_RATE;200|ARM;100");
        BattleSideSnapshot side = side(unit(panel));
        PRT_BATTLE_STAGE result = fight(1, side, side);
        assertEquals(245, attr(result.getAtkUnits(0).getAttr(), ATK));
        assertEquals(245, result.getLog().getUnitResults(0).getDamageDealt());
        assertEquals(0, attr(result.getAtkUnits(0).getAttr(), SKILL_ENERGY));
        assertEquals(20, attr(result.getLog().getUnitResults(0).getFinalState().getAttr(), SKILL_ENERGY));
    }

    private static long attr(PRT_ATTR_MAP attrs, AttrTypeEnum type) {
        return attrs.getFinalAttrsList().stream().filter(value -> value.getAttrType() == type.getNumber())
                .mapToLong(PRT_ATTR::getAtrrVal).findFirst().orElse(0);
    }

    private static BattleSkillSlot slot(int slot, BattleSkillSpec skill) {
        return BattleSkillSlot.newBuilder().setSlot(slot).setLevel(1).setDefinition(skill).build();
    }

    private static BattleUnitSnapshot.Builder unit(AttrMap attrs) {
        return BattleUnitSnapshot.newBuilder().setPosition(1).setHero(PRT_HERO.newBuilder().setHeroTid(101))
                .putAllEntryAttrs(attrs.battleEntryValues()).addSkills(slot(0, NORMAL))
                .addSoldiers(PRT_FORMATION_SOLDIER.newBuilder().setSoldierDid(1).setCount(100));
    }

    private static BattleSideSnapshot side(BattleUnitSnapshot.Builder unit) {
        return BattleSideSnapshot.newBuilder().addUnits(unit).build();
    }

    private static PRT_BATTLE_STAGE fight(int roundLimit, BattleSideSnapshot attacker, BattleSideSnapshot defender) {
        return BattleEngine.fight(1, 1, 0, roundLimit, RULES, attacker, defender);
    }
}
