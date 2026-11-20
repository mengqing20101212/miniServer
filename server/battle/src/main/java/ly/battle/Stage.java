package ly.battle;

import ly.battle.buff.BaseBuff;
import ly.battle.event.BattleEvent;
import ly.battle.skill.BaseSkill;
import ly.battle.unit.BattleQueue;
import ly.battle.unit.BattleReport;
import ly.battle.unit.BattleUnit;
import ly.proto.Battle.AttrTypeEnum;
import ly.proto.Battle.*;
import ly.proto.Battle.PRT_MINI_PLAYER;

import java.math.BigInteger;
import java.util.*;

import static ly.proto.Battle.AttrTypeEnum.*;
import static ly.proto.Battle.PRT_BATTLE_EFFECT_TYPE.*;
/** 单场同步战斗。编排批次并统一输出BattleEvent，具体技能与Buff效果由实体执行。 */

public final class Stage {
    private final long id;
    private final long time;
    private final int type;
    private final int roundLimit;
    private final PRT_MINI_PLAYER attacker;
    private final PRT_MINI_PLAYER defender;
    private final List<BattleUnit> units = new ArrayList<>();
    private final BattleRules.BattleDamageRules damageRules;
    /**
     * 每场战斗独占的事件收集器，战报随Stage一起生成。
     */
    private final BattleReport reportCollector;
    // 单位、技能和 Buff 共用本场的实例 ID。
    private long idCreated = 1;
    private final Set<BattleUnit> aliveAttackers = new HashSet<>();
    private final Set<BattleUnit> aliveDefenders = new HashSet<>();
    private int round;

    Stage(long id, long time, int type, int roundLimit, PRT_MINI_PLAYER attacker,
          PRT_MINI_PLAYER defender, BattleRules.BattleDamageRules damageRules) {
        if (id <= 0 || roundLimit < BattleRules.BATTLE_MIN_ROUND_LIMIT
                || roundLimit > BattleRules.BATTLE_MAX_ROUND_LIMIT) {
            throw new IllegalArgumentException("战斗ID或回合上限无效");
        }
        this.id = id;
        this.time = time;
        this.type = type;
        this.roundLimit = roundLimit;
        this.attacker = attacker;
        this.defender = defender;
        this.damageRules = Objects.requireNonNull(damageRules);
        reportCollector = new BattleReport();
    }

    /**
     * 创建技能、单位和Buff时共用本场自增ID。
     */
    public long nextId() {
        return idCreated++;
    }

    /**
     * 入场时登记单位，战斗开始后不再改变阵容。
     */
    void addUnit(BattleUnit unit) {
        for (BattleUnit existing : units) {
            if (existing.isAttacker() == unit.isAttacker()
                    && (existing.getPos() == unit.getPos() || existing.getTid() == unit.getTid())) {
                throw new IllegalArgumentException("同方位置或英雄重复");
            }
        }
        units.add(unit);
    }

    /**
     * 每次快照结算创建新战场，同步跑完所有回合。
     */
    void run() {
        units.sort(Stage::compareUnits);
        BattleQueue attackerQueue = new BattleQueue();
        BattleQueue defenderQueue = new BattleQueue();
        for (BattleUnit unit : units) {
            if (unit.isAttacker()) {
                attackerQueue.add(unit);
            } else {
                defenderQueue.add(unit);
            }
        }
        // 第一回合任何属性变化之前，立即把入场数据写入本场战报。
        reportCollector.startReport(this);
        // 服务端连续执行逻辑回合，满足结束条件立即退出；没有tick、定时器或动画等待。
        for (round = BattleRules.BATTLE_MIN_ROUND_LIMIT; round <= roundLimit; round++) {
            startRound();
            castActiveSkills(attackerQueue.skillOrder(), defenderQueue.skillOrder());
            if (!isEliminated()) {
                castNormalAttacks();
            }
            if (!isEliminated()) {
                finishBuffs();
            }
            if (finishBattle()) {
                break;
            }
        }
    }

    private void castActiveSkills(List<BattleUnit> attackers, List<BattleUnit> defenders) {
        // 队列在加能后锁定。同序号先攻后守，阵亡跳过原位，后续单位不补位。
        for (int index = 0; index < Math.max(attackers.size(), defenders.size()); index++) {
            castActiveSkill(attackers, index);
            if (isEliminated()) {
                return;
            }
            castActiveSkill(defenders, index);
            if (isEliminated()) {
                return;
            }
        }
    }

    private void castActiveSkill(List<BattleUnit> order, int index) {
        if (index < order.size()) {
            BattleUnit owner = order.get(index);
            if (owner.isAlive()) {
                battleOnce(List.of(new Cast(owner, owner.getSkill(BattleRules.BATTLE_ACTIVE_SKILL_SLOT))),
                        PRT_BATTLE_PHASE.SKILL);
            }
        }
    }

    private void castNormalAttacks() {
        List<Cast> normals = new ArrayList<>();
        for (BattleUnit unit : units) {
            BaseSkill skill = unit.getSkill(BattleRules.BATTLE_NORMAL_SKILL_SLOT);
            if (unit.isAlive() && skill.hasTarget(unit, units)) {
                normals.add(new Cast(unit, skill));
            }
        }
        normals.sort(Comparator.comparingInt((Cast cast) -> cast.owner().getPos())
                .thenComparing(Cast::owner, Stage::compareUnits));
        // 同一批次锁定的普攻，即使来源在结算中阵亡也会落地。
        battleOnce(normals, PRT_BATTLE_PHASE.NORMAL_ATTACK);
    }

    /**
     * 仅在战斗结束后返回完整战报；未结束时由收集器明确报错。
     */
    PRT_BATTLE_STAGE getReport() {
        return reportCollector.getRespBuilder();
    }

    public long getId() {
        return id;
    }

    public long getTime() {
        return time;
    }

    public int getType() {
        return type;
    }

    public int getRoundLimit() {
        return roundLimit;
    }

    public PRT_MINI_PLAYER getAttacker() {
        return attacker;
    }

    public PRT_MINI_PLAYER getDefender() {
        return defender;
    }

    public List<BattleUnit> getUnits() {
        return List.copyOf(units);
    }

    private static int compareUnits(BattleUnit left, BattleUnit right) {
        if (left.isAttacker() != right.isAttacker()) {
            if (left.isAttacker()) {
                return -1;
            }
            return 1;
        }
        return Integer.compare(left.getPos(), right.getPos());
    }

    /**
     * 先激活已到生效回合的Buff，再为存活单位加能；上限200，随后仅组队列一次。
     */
    private void startRound() {
        aliveAttackers.clear();
        aliveDefenders.clear();
        for (BattleUnit unit : units) {
            updateAliveUnit(unit);
        }
        emit(new BattleEvent.RoundStarted(round));
        emit(new BattleEvent.BatchStarted(PRT_BATTLE_PHASE.ENERGY));
        for (BattleUnit unit : units) {
            if (!unit.isAlive()) {
                continue;
            }
            for (BaseBuff buff : unit.getBuffs()) {
                buff.onRoundStart(this, unit);
            }
            long rate = Math.addExact(BattleRules.BATTLE_RATE_BASE, unit.getAttrValue(SKILL_ENERGY_GAIN_RATE));
            long gain = BattleMath.multiplyDivide(BattleRules.BATTLE_ROUND_ENERGY_GAIN,
                    Math.max(0, rate), BattleRules.BATTLE_RATE_BASE);
            gain = Math.min(BattleRules.BATTLE_MAX_ENERGY - unit.getAttrValue(SKILL_ENERGY), gain);
            unit.addAttrDeltasAndRecord(this, 0, EFFECT_ATTRIBUTE, Map.of(SKILL_ENERGY, gain));
        }
    }

    /**
     * 技能阶段按同序号进攻方、防守方分别调用；普攻阶段传入本回合所有普攻单位。
     * 同一组合技能步骤先锁定目标和数值，再依次落地直接效果和Buff。
     */
    private void battleOnce(List<Cast> casts, PRT_BATTLE_PHASE phase) {
        if (casts.isEmpty()) {
            return;
        }
        int steps = 0;
        for (Cast cast : casts) {
            steps = Math.max(steps, cast.skills().size());
        }
        for (int step = 0; step < steps; step++) {
            emit(new BattleEvent.BatchStarted(phase));
            // 在任何效果落地前锁完本批全部目标和数值，等价于共享同一时刻的战场快照。
            List<BaseSkill.SkillEffectResult> effects = new ArrayList<>();
            for (Cast cast : casts) {
                if (step >= cast.skills().size()) {
                    continue;
                }
                BaseSkill skill = cast.skills().get(step);
                effects.add(skill.useSkill(cast.owner(), units, damageRules));
                // 引擎只标记根行动，累计次数由战报收集器负责。
                emit(new BattleEvent.ActionStarted(skill.getId(), step == 0));
            }
            // 本次主动技能已锁定目标和数值，开始结算时扣一次蓄能。
            if (step == 0 && phase == PRT_BATTLE_PHASE.SKILL) {
                for (BaseSkill.SkillEffectResult use : effects) {
                    use.owner().subEnergy(this, use.skill().getId());
                }
            }
            // 同序号主动技能已分开结算；普攻没有治疗效果，直接效果只需遍历一次。
            // 本批已锁定的普攻不会因来源单位在落地中阵亡而取消。
            for (BaseSkill.SkillEffectResult effect : effects) {
                effect.skill().settleDirectEffect(effect, this);
            }
            for (BaseSkill.SkillEffectResult use : effects) {
                use.skill().addSkillBuffs(use, units, this);
            }
            // 同一步骤完整落地后判全灭，保留普攻整批同时结算的语义。
            if (isEliminated()) {
                break;
            }
        }
    }

    private void finishBuffs() {
        emit(new BattleEvent.BatchStarted(PRT_BATTLE_PHASE.ROUND_END));
        // 两遍处理：先统一计时，再移除到期实例，避免移除链刷新另一个Buff后又扣回合。
        List<Expired> expired = new ArrayList<>();
        for (BattleUnit unit : units) {
            for (BaseBuff buff : List.copyOf(unit.getBuffs())) {
                if (buff.onRoundEnd(this, unit)) {
                    expired.add(new Expired(unit, buff));
                }
            }
        }
        for (Expired item : expired) {
            item.unit().removeExpiredBuff(this, item.buff());
        }
    }

    /**
     * Buff生命周期读取当前行动回合，不包含任何展示时间。
     */
    public int getRound() {
        return round;
    }

    /** 单位记录状态变化时同步所属方的存活集合，不修改单位内部状态。 */
    public void updateAliveUnit(BattleUnit unit) {
        Set<BattleUnit> aliveUnits = aliveDefenders;
        if (unit.isAttacker()) {
            aliveUnits = aliveAttackers;
        }
        if (unit.isAlive()) {
            aliveUnits.add(unit);
        } else {
            aliveUnits.remove(unit);
        }
    }

    private boolean isEliminated() {
        return aliveAttackers.isEmpty() || aliveDefenders.isEmpty();
    }

    /**
     * 全灭时立即判胜；回合上限仅在回合末检查。比较生命不含护盾，等血防守方胜。
     */
    private boolean finishBattle() {
        PRT_BATTLE_END_REASON reason;
        if (isEliminated()) {
            reason = PRT_BATTLE_END_REASON.ELIMINATION;
        } else if (round == roundLimit) {
            reason = PRT_BATTLE_END_REASON.ROUND_LIMIT;
        } else {
            return false;
        }
        BigInteger atk = BigInteger.ZERO;
        BigInteger def = BigInteger.ZERO;
        for (BattleUnit unit : units) {
            BigInteger hp = BigInteger.valueOf(unit.getAttrValue(CURRENT_HP));
            if (unit.isAttacker()) {
                atk = atk.add(hp);
            } else {
                def = def.add(hp);
            }
        }
        PRT_BATTLE_SIDE winner = PRT_BATTLE_SIDE.DEFENDER;
        if (atk.compareTo(def) > 0) {
            winner = PRT_BATTLE_SIDE.ATTACKER;
        }
        List<PRT_BATTLE_UNIT_STATE> states = new ArrayList<>();
        for (BattleUnit unit : units) {
            states.add(unit.toPb());
        }
        emit(new BattleEvent.Ended(winner, reason, states));
        return true;
    }

    /**
     * 战场事件的统一出口，目标单位负责构造自身实际落地的效果。
     */
    public void emit(BattleEvent event) {
        reportCollector.accept(event);
    }

    // Cast保存一名出手单位及其组合技能顺序；SkillEffectResult保存本批锁定的目标与数值。
    private record Cast(BattleUnit owner, List<BaseSkill> skills) {
        private Cast(BattleUnit owner, BaseSkill root) {
            this(owner, root.getEffectSkills());
        }
    }

    // 先统一计时，第二遍再移除；保留Buff的持有者以执行移除后的连锁效果。
    private record Expired(BattleUnit unit, BaseBuff buff) {
    }
}
