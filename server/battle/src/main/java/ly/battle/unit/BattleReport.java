package ly.battle.unit;

import ly.battle.BattleRules;
import ly.battle.BattleMath;
import ly.battle.Stage;
import ly.battle.event.BattleEvent;
import ly.battle.skill.BaseSkill;
import ly.proto.Battle.AttrTypeEnum;
import ly.proto.Battle.*;

import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.function.Consumer;

import static ly.proto.Battle.AttrTypeEnum.*;
/** 消费战场事件，集中维护每个单位的累计统计并封装协议，不修改战斗运行状态。 */
public final class BattleReport implements Consumer<BattleEvent> {
    /**
     * 单位运行状态在BattleUnit中；本场统计只由战报按unitId保存。
     */
    private final Map<Long, UnitStatistics> statistics = new HashMap<>();
    /**
     * 实例来源映射：包含单位、技能和Buff；Buff移除后仍保留归属供后继效果追溯。
     */
    private final Map<Long, Source> sources = new HashMap<>();
    private PRT_BATTLE_STAGE.Builder stageBuilder;
    private PRT_BATTLE_REPORT.Builder reportBuilder;
    private PRT_BATTLE_ROUND.Builder roundBuilder;
    private PRT_BATTLE_BATCH.Builder batchBuilder;
    private PRT_BATTLE_ACTION.Builder actionBuilder;
    private PRT_BATTLE_STAGE respBuilder;
    // 同批技能会分多轮落地效果，按sourceId归到同一个action，并保留首次登记顺序。
    private final Map<Long, PRT_BATTLE_ACTION.Builder> skillActions = new LinkedHashMap<>();

    /**
     * 开战时立即读取Stage，固定入场属性、技能和Buff；之后不再从单位读取初始状态。
     */
    public void startReport(Stage battlefield) {
        if (stageBuilder != null || respBuilder != null) {
            throw new IllegalStateException("战报重复开始");
        }
        stageBuilder = PRT_BATTLE_STAGE.newBuilder();
        stageBuilder.setBattleId(battlefield.getId());
        stageBuilder.setBattleTime(battlefield.getTime());
        stageBuilder.setType(battlefield.getType());
        stageBuilder.setRoundLimit(battlefield.getRoundLimit());
        if (battlefield.getAttacker() != null) {
            stageBuilder.setAtkPlayer(battlefield.getAttacker());
        }
        if (battlefield.getDefender() != null) {
            stageBuilder.setDefPlayer(battlefield.getDefender());
        }
        // 系统来源不归属任何参战单位；其伤害仍可计入目标承伤。
        sources.put(0L, new Source(0, 0, false));
        List<PRT_BATTLE_BUFF_STATE> initialBuffs = new ArrayList<>();
        for (BattleUnit unit : battlefield.getUnits()) {
            UnitStatistics totals = new UnitStatistics(unit.getInitialSoldiers());
            statistics.put(unit.getId(), totals);
            sources.put(unit.getId(), new Source(unit.getId(), 0, false));
            for (BaseSkill root : unit.getSkills()) {
                for (BaseSkill skill : root.getEffectSkills()) {
                    // 子技能继承根槽位，归类按技能实例判断，不依赖它在哪个批次落地。
                    sources.put(skill.getId(), new Source(unit.getId(), skill.getId(),
                            skill.getSlot() == BattleRules.BATTLE_NORMAL_SKILL_SLOT));
                    totals.skills.put(skill.getId(), new SkillStatistics());
                }
            }
            PRT_BATTLE_UNIT_STATE initialState = unit.toPb();
            initialBuffs.addAll(initialState.getBuffsList());
            PRT_BATTLE_UNIT snapshot = unitToPb(unit, initialState);
            if (unit.isAttacker()) {
                stageBuilder.addAtkUnits(snapshot);
            } else {
                stageBuilder.addDefUnits(snapshot);
            }
        }
        registerInitialBuffs(initialBuffs);
        reportBuilder = PRT_BATTLE_REPORT.newBuilder();
    }

    /**
     * 按事件顺序构建round→batch→action→targets，开始下一层时收尾前一层。
     */
    @Override
    public void accept(BattleEvent event) {
        if (stageBuilder == null) {
            throw new IllegalStateException("战报尚未开始");
        }
        if (respBuilder != null) {
            throw new IllegalStateException("战报已完成");
        }
        if (event instanceof BattleEvent.RoundStarted start) {
            finishRound();
            roundBuilder = PRT_BATTLE_ROUND.newBuilder();
            roundBuilder.setRound(start.round());
        } else if (event instanceof BattleEvent.BatchStarted start) {
            finishBatch();
            batchBuilder = PRT_BATTLE_BATCH.newBuilder();
            batchBuilder.setPhase(start.phase());
        } else if (event instanceof BattleEvent.ActionStarted start) {
            Source source = requireSource(start.sourceId());
            UnitStatistics unit = requireStatistics(source.unitId());
            // 技能实例各自计次；Buff后续触发只累计效果，不当作再次施放来源技能。
            if (start.sourceId() == source.skillId()) {
                SkillStatistics skill = unit.skills.get(source.skillId());
                skill.castCount = Math.incrementExact(skill.castCount);
            }
            if (start.rootAction()) {
                if (source.normalAttack()) {
                    unit.normalAttackCount = Math.incrementExact(unit.normalAttackCount);
                } else {
                    unit.skillCastCount = Math.incrementExact(unit.skillCastCount);
                }
            }
            startAction(start.sourceId());
        } else if (event instanceof BattleEvent.Effect effect) {
            collectStatistics(effect);
            for (PRT_BATTLE_BUFF_STATE buff : effect.added()) {
                registerBuff(buff);
            }
            for (PRT_BATTLE_BUFF_STATE buff : effect.updated()) {
                // Buff自身发出的计时/生效事件不改变归属；再次施加才更新来源快照。
                if (effect.sourceId() != buff.getBuffId()) {
                    registerBuff(buff);
                }
            }
            if (actionBuilder == null || actionBuilder.getSourceId() != effect.sourceId()) {
                startAction(effect.sourceId());
            }
            PRT_BATTLE_TARGET_RESULT.Builder result = PRT_BATTLE_TARGET_RESULT.newBuilder();
            result.setTargetUnitId(effect.targetId());
            result.setEffectType(effect.type());
            // 这里保存服务器已经执行的增量，不重新计算伤害或从配表推导Buff属性。
            result.setAttrDeltas(attrs(effect.deltas()));
            result.addAllAddedBuffs(effect.added());
            result.addAllUpdatedBuffs(effect.updated());
            result.addAllRemovedBuffIds(effect.removed());
            actionBuilder.addTargets(result);
        } else if (event instanceof BattleEvent.Ended end) {
            finishRound();
            PRT_BATTLE_LOG.Builder log = PRT_BATTLE_LOG.newBuilder();
            // 玩法结算时可替换成具体标题，例如“调试挑战胜利”。
            log.setTitle("战斗结果");
            log.setWinner(end.winner());
            log.setEndReason(end.reason());
            for (PRT_BATTLE_UNIT_STATE finalState : end.states()) {
                UnitStatistics totals = requireStatistics(finalState.getUnitId());
                // 兵力统计按最终HP比例向下取整；损失数不等于玩法已判定的死兵数。
                long hp = attrValue(finalState.getAttr(), CURRENT_HP);
                long maxHp = attrValue(finalState.getAttr(), HP);
                long remaining = BattleMath.multiplyDivide(totals.initialSoldiers, hp, maxHp);
                PRT_BATTLE_UNIT_RESULT.Builder target = PRT_BATTLE_UNIT_RESULT.newBuilder();
                target.setUnitId(finalState.getUnitId());
                target.setFinalState(finalState);
                target.setRemainingSoldiers(remaining);
                target.setLostSoldiers(totals.initialSoldiers - remaining);
                long damageDealt = Math.addExact(totals.normalAttackDamage, totals.skillDamage);
                target.setDamageDealt(damageDealt);
                target.setDamageTaken(totals.damageTaken);
                target.setHealingDone(totals.healingDone);
                target.setNormalAttackCount(totals.normalAttackCount);
                target.setSkillCastCount(totals.skillCastCount);
                target.setNormalAttackDamage(totals.normalAttackDamage);
                target.setSkillDamage(totals.skillDamage);
                for (Map.Entry<Long, SkillStatistics> entry : totals.skills.entrySet()) {
                    SkillStatistics values = entry.getValue();
                    PRT_BATTLE_SKILL_RESULT.Builder skill = PRT_BATTLE_SKILL_RESULT.newBuilder();
                    skill.setSkillId(entry.getKey());
                    skill.setCastCount(values.castCount);
                    skill.setDamageDealt(values.damageDealt);
                    skill.setHealingDone(values.healingDone);
                    target.addSkills(skill);
                }
                log.addUnitResults(target);
            }
            stageBuilder.setLog(log);
            stageBuilder.setReport(reportBuilder);
            respBuilder = stageBuilder.build();
        }
    }

    /**
     * 统计只读取已截断的实际增量；属性变化、挂Buff和授予护盾本身不算伤害或治疗。
     */
    private void collectStatistics(BattleEvent.Effect effect) {
        if (effect.type() == PRT_BATTLE_EFFECT_TYPE.EFFECT_DAMAGE) {
            long hpDamage = Math.negateExact(effect.deltas().getOrDefault(CURRENT_HP, 0L));
            long shieldDamage = Math.negateExact(effect.deltas().getOrDefault(SHIELD, 0L));
            long damage = Math.addExact(hpDamage, shieldDamage);
            UnitStatistics target = requireStatistics(effect.targetId());
            target.damageTaken = Math.addExact(target.damageTaken, damage);
            Source source = requireSource(effect.sourceId());
            if (source.unitId() != 0) {
                UnitStatistics owner = requireStatistics(source.unitId());
                if (source.skillId() != 0) {
                    SkillStatistics skill = owner.skills.get(source.skillId());
                    skill.damageDealt = Math.addExact(skill.damageDealt, damage);
                }
                if (source.normalAttack()) {
                    owner.normalAttackDamage = Math.addExact(owner.normalAttackDamage, damage);
                } else {
                    owner.skillDamage = Math.addExact(owner.skillDamage, damage);
                }
            }
        } else if (effect.type() == PRT_BATTLE_EFFECT_TYPE.EFFECT_HEAL) {
            Source source = requireSource(effect.sourceId());
            if (source.unitId() != 0) {
                long healing = effect.deltas().getOrDefault(CURRENT_HP, 0L);
                UnitStatistics owner = requireStatistics(source.unitId());
                owner.healingDone = Math.addExact(owner.healingDone, healing);
                if (source.skillId() != 0) {
                    SkillStatistics skill = owner.skills.get(source.skillId());
                    skill.healingDone = Math.addExact(skill.healingDone, healing);
                }
            }
        }
    }

    /**
     * 入场Buff允许引用其他入场Buff，按来源可解析的顺序登记，避免依赖单位列表顺序。
     */
    private void registerInitialBuffs(List<PRT_BATTLE_BUFF_STATE> pending) {
        while (!pending.isEmpty()) {
            boolean progressed = false;
            Iterator<PRT_BATTLE_BUFF_STATE> iterator = pending.iterator();
            while (iterator.hasNext()) {
                PRT_BATTLE_BUFF_STATE buff = iterator.next();
                if (sources.containsKey(buff.getSourceId())) {
                    registerBuff(buff);
                    iterator.remove();
                    progressed = true;
                }
            }
            if (!progressed) {
                throw new IllegalStateException("入场Buff来源缺失或循环引用");
            }
        }
    }

    private void registerBuff(PRT_BATTLE_BUFF_STATE buff) {
        // 保存施加这一刻的归属；后续原Buff被刷新或移除，不改写已经产生的后继Buff归属。
        sources.put(buff.getBuffId(), requireSource(buff.getSourceId()));
    }

    private Source requireSource(long id) {
        Source source = sources.get(id);
        if (source == null) {
            throw new IllegalStateException("战报来源实例未登记：" + id);
        }
        return source;
    }

    private UnitStatistics requireStatistics(long unitId) {
        UnitStatistics unit = statistics.get(unitId);
        if (unit == null) {
            throw new IllegalStateException("战报单位未登记：" + unitId);
        }
        return unit;
    }

    public PRT_BATTLE_STAGE getRespBuilder() {
        if (respBuilder == null) {
            throw new IllegalStateException("战斗尚未完成，不能保存半份战报");
        }
        return respBuilder;
    }

    /**
     * 技能/普攻按发生源合并多轮效果；计时、到期移除等事件严格保留发生顺序。
     */
    private void startAction(long sourceId) {
        if (batchBuilder.getPhase() == PRT_BATTLE_PHASE.SKILL || batchBuilder.getPhase() == PRT_BATTLE_PHASE.NORMAL_ATTACK) {
            actionBuilder = skillActions.get(sourceId);
            if (actionBuilder == null) {
                actionBuilder = PRT_BATTLE_ACTION.newBuilder();
                actionBuilder.setSourceId(sourceId);
                skillActions.put(sourceId, actionBuilder);
            }
        } else {
            finishAction();
            actionBuilder = PRT_BATTLE_ACTION.newBuilder();
            actionBuilder.setSourceId(sourceId);
        }
    }

    private void finishAction() {
        if (actionBuilder != null && skillActions.isEmpty()) {
            batchBuilder.addActions(actionBuilder);
        }
        actionBuilder = null;
    }

    private void finishBatch() {
        finishAction();
        if (batchBuilder != null) {
            for (PRT_BATTLE_ACTION.Builder skillAction : skillActions.values()) {
                batchBuilder.addActions(skillAction);
            }
            skillActions.clear();
            // 没有任何行动的阶段无需写入战报，批次先后顺序由列表本身表示。
            if (batchBuilder.getActionsCount() > 0) {
                roundBuilder.addBatches(batchBuilder);
            }
            batchBuilder = null;
        }
    }

    private void finishRound() {
        finishBatch();
        if (roundBuilder != null) {
            reportBuilder.addRounds(roundBuilder);
            roundBuilder = null;
        }
    }

    private static PRT_ATTR_MAP attrs(Map<AttrTypeEnum, Long> values) {
        PRT_ATTR_MAP.Builder result = PRT_ATTR_MAP.newBuilder();
        // 稳定的枚举顺序便于比较战报，零值不占空间。
        for (AttrTypeEnum type : AttrTypeEnum.values()) {
            long value = values.getOrDefault(type, 0L);
            if (value != 0) {
                PRT_ATTR.Builder attr = PRT_ATTR.newBuilder();
                attr.setAttrType(type.getNumber());
                attr.setAtrrVal(value);
                result.addFinalAttrs(attr);
            }
        }
        return result.build();
    }

    private static long attrValue(PRT_ATTR_MAP values, AttrTypeEnum type) {
        for (PRT_ATTR attr : values.getFinalAttrsList()) {
            if (attr.getAttrType() == type.getNumber()) {
                return attr.getAtrrVal();
            }
        }
        return 0;
    }

    private static PRT_BATTLE_UNIT unitToPb(BattleUnit value, PRT_BATTLE_UNIT_STATE initialState) {
        PRT_BATTLE_UNIT.Builder result = PRT_BATTLE_UNIT.newBuilder();
        result.setUnitId(value.getId());
        result.setPos(value.getPos());
        result.setUnitType(value.getType());
        result.setHero(value.getHero());
        result.setSoldierDid(value.getSoldierDid());
        result.setInitialSoldiers(value.getInitialSoldiers());
        result.addAllSoldiers(value.getSoldiers());
        result.setMonsterTid(value.getMonsterTid());
        result.setAttr(initialState.getAttr());
        result.addAllInitBuffs(initialState.getBuffsList());
        for (BaseSkill root : value.getSkills()) {
            for (BaseSkill skill : root.getEffectSkills()) {
                PRT_BATTLE_SKILL.Builder entry = PRT_BATTLE_SKILL.newBuilder();
                entry.setSkillId(skill.getId());
                entry.setSkillTid(skill.getTid());
                entry.setSlot(skill.getSlot());
                entry.setLevel(skill.getLevel());
                result.addSkills(entry);
            }
        }
        return result.build();
    }

    /**
     * 保存单位、来源技能与伤害分类；不持有BattleUnit，Buff链沿用施加时的归属快照。
     */
    private record Source(long unitId, long skillId, boolean normalAttack) {
    }

    /**
     * 单个技能实例统计；子技能单独记录，不再重复汇入根技能。
     */
    private static final class SkillStatistics {
        private int castCount;
        private long damageDealt;
        private long healingDone;
    }

    /**
     * 每个参战单位的一份整场累计统计，生命周期随本战报结束。
     */
    private static final class UnitStatistics {
        private final long initialSoldiers;
        /**
         * 按入场顺序记录，客户端通过skillId关联技能配置。
         */
        private final Map<Long, SkillStatistics> skills = new LinkedHashMap<>();
        /**
         * 累计承受的实际伤害：自身护盾吸收加实际扣血。
         */
        private long damageTaken;
        /**
         * 累计有效治疗量，不计溢出治疗和授予的护盾。
         */
        private long healingDone;
        /**
         * 普攻及其组合子技能累计造成的实际伤害。
         */
        private long normalAttackDamage;
        /**
         * 主动技能及其组合子技能累计造成的实际伤害。
         */
        private long skillDamage;
        /**
         * 普攻根行动次数；群攻、多次效果和组合子技能不额外计次。
         */
        private int normalAttackCount;
        /**
         * 主动技能根行动次数；群攻、多次效果和组合子技能不额外计次。
         */
        private int skillCastCount;

        private UnitStatistics(long initialSoldiers) {
            this.initialSoldiers = initialSoldiers;
        }
    }
}
