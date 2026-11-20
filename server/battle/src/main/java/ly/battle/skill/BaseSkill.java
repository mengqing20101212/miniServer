package ly.battle.skill;

import ly.battle.BattleRules;
import ly.battle.BattleMath;
import ly.battle.Stage;
import ly.battle.unit.BaseUnit;
import ly.battle.unit.BattleUnit;
import ly.proto.Battle.AttrTypeEnum;
import ly.proto.Battle.BattleBuffSpec;
import ly.proto.Battle.BattleSkillSpec;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.LongSupplier;
import static ly.proto.Battle.AttrTypeEnum.*;
import static ly.proto.Battle.PRT_BATTLE_EFFECT_TYPE.*;
/** 一个技能槽及其组合子技能各自拥有唯一实例ID。定义在入场时固定。 */

public final class BaseSkill extends BaseUnit {
    private final BattleSkillSpec spec;
    // 0为普攻，1为主动技能，2～5为被动技能；子技能继承根技能槽位。
    private final int slot;
    private final int level;
    private final List<BaseSkill> children;

    public BaseSkill(LongSupplier ids, BattleSkillSpec spec, int slot, int level) {
        this(ids, spec, slot, level, new HashSet<>());
    }

    private BaseSkill(LongSupplier ids, BattleSkillSpec spec, int slot, int level, Set<Integer> path) {
        super(ids.getAsLong(), spec.getId());
        if (slot < BattleRules.BATTLE_NORMAL_SKILL_SLOT
                || slot > BattleRules.BATTLE_MAX_SKILL_SLOT || level < 1 || !path.add(spec.getId())) {
            throw new IllegalArgumentException("技能槽、等级或子技能引用无效");
        }
        if (slot == BattleRules.BATTLE_NORMAL_SKILL_SLOT
                && spec.getEffectType() == BattleSkillSpec.Effect.HEAL) {
            throw new IllegalArgumentException("普攻及其子技能不能治疗");
        }
        this.spec = spec;
        this.slot = slot;
        this.level = level;
        List<BaseSkill> nested = new ArrayList<>();
        // 每次引用都创建独立实例；path只限制当前祖先链，阻止组合技能循环引用。
        for (BattleSkillSpec child : spec.getSubSkillsList()) {
            nested.add(new BaseSkill(ids, child, slot, level, path));
        }
        path.remove(spec.getId());
        children = List.copyOf(nested);
    }

    public int getSlot() { return slot; }
    public int getLevel() { return level; }

    /** 深度优先保持配表顺序；当前行先执行，再执行其关联子技能。 */
    public List<BaseSkill> getEffectSkills() {
        List<BaseSkill> result = new ArrayList<>();
        appendTo(result);
        return result;
    }

    private void appendTo(List<BaseSkill> result) {
        result.add(this);
        for (BaseSkill child : children) {
            child.appendTo(result);
        }
    }

    /**
     * 准备一次技能使用：按当前战场选目标，并计算未按护盾、剩余HP截断的效果值。
     * 此方法不修改任何单位；Stage先锁定本批全部技能，再调用settleDirectEffect落地。
     * 组合子技能各自调用一次，因此后续子技能可以看到前一个批次的战场变化。
     */
    public SkillEffectResult useSkill(BattleUnit owner, List<BattleUnit> units,
                                      BattleRules.BattleDamageRules damageRules) {
        List<BattleUnit> targets = selectTargets(owner, units);
        List<Hit> hits = new ArrayList<>();
        Map<AttrTypeEnum, Long> source = owner.getFinalAttrs();
        for (BattleUnit target : targets) {
            long value = 0;
            if (spec.getEffectType() == BattleSkillSpec.Effect.DAMAGE) {
                value = BattleMath.damage(source, target.getFinalAttrs(), spec.getActionRate(), damageRules);
            } else if (spec.getEffectType() == BattleSkillSpec.Effect.HEAL
                    || spec.getEffectType() == BattleSkillSpec.Effect.SHIELD) {
                value = BattleMath.multiplyDivide(BattleMath.attack(source), spec.getActionRate(),
                        BattleRules.BATTLE_RATE_BASE);
            }
            hits.add(new Hit(target, value));
        }
        return new SkillEffectResult(this, owner, hits);
    }

    /** 普攻入队前检查是否有合法目标；不产生效果或修改战场。 */
    public boolean hasTarget(BattleUnit owner, List<BattleUnit> units) {
        return !selectTargets(owner, units).isEmpty();
    }

    /** Hit只保存锁定目标和公式值；落地时按当前护盾、生命上限和剩余生命确定实际增量。 */
    public void settleDirectEffect(SkillEffectResult use, Stage stage) {
        BattleSkillSpec.Effect kind = spec.getEffectType();
        if (kind == BattleSkillSpec.Effect.NONE) {
            return;
        }
        for (Hit hit : use.hits()) {
            BattleUnit target = hit.target();
            if (kind == BattleSkillSpec.Effect.DAMAGE) {
                // 伤害先由SHIELD吸收，剩余伤害最多扣到CURRENT_HP为0；溢出部分不写属性表。
                long absorbed = Math.min(target.getAttrValue(SHIELD), hit.value());
                long hp = Math.min(target.getAttrValue(CURRENT_HP), hit.value() - absorbed);
                target.addAttrDeltasAndRecord(stage, getId(), EFFECT_DAMAGE,
                        Map.of(SHIELD, -absorbed, CURRENT_HP, -hp));
            } else if (kind == BattleSkillSpec.Effect.HEAL) {
                // HP是本场最大生命，CURRENT_HP是当前生命；治疗只补两者差额，不增加HP上限。
                long healed = Math.min(target.getAttrValue(HP) - target.getAttrValue(CURRENT_HP), hit.value());
                target.addAttrDeltasAndRecord(stage, getId(), EFFECT_HEAL, Map.of(CURRENT_HP, healed));
            } else if (kind == BattleSkillSpec.Effect.SHIELD) {
                target.addAttrDeltasAndRecord(stage, getId(), EFFECT_SHIELD, Map.of(SHIELD, hit.value()));
            }
        }
    }

    /** 直接效果落地后，按自身、友方、已锁定目标顺序登记Buff。 */
    public void addSkillBuffs(SkillEffectResult use, List<BattleUnit> units, Stage stage) {
        for (BattleBuffSpec buff : spec.getSelfBuffsList()) {
            use.owner().addOrRefreshBuff(stage, buff, getId());
        }
        for (BattleUnit battleUnit : units) {
            if (battleUnit.isAttacker() != use.owner().isAttacker() || !battleUnit.isAlive()) {
                continue;
            }
            for (BattleBuffSpec buff : spec.getAllyBuffsList()) {
                battleUnit.addOrRefreshBuff(stage, buff, getId());
            }
        }
        for (Hit hit : use.hits()) {
            for (BattleBuffSpec buff : spec.getEnemyBuffsList()) {
                hit.target().addOrRefreshBuff(stage, buff, getId());
            }
        }
    }

    /** 对敌目标先检查已生效的嘲讽，再按技能配置选择范围和顺序。 */
    private List<BattleUnit> selectTargets(BattleUnit owner, List<BattleUnit> units) {
        boolean friendly = spec.getTargetRule() == BattleSkillSpec.Target.ALLY_LOWEST_HP;
        List<BattleUnit> candidates = new ArrayList<>();
        for (BattleUnit unit : units) {
            boolean sameSide = unit.isAttacker() == owner.isAttacker();
            if (unit.isAlive() && sameSide == friendly) {
                candidates.add(unit);
            }
        }
        if (!friendly) {
            for (BattleUnit unit : candidates) {
                if (unit.isTaunting()) {
                    return List.of(unit);
                }
            }
        }
        if (spec.getTargetRule() == BattleSkillSpec.Target.ALL_ENEMIES) {
            return candidates;
        }
        if (spec.getTargetRule() == BattleSkillSpec.Target.FRONT_COLUMN) {
            boolean front = false;
            for (BattleUnit unit : candidates) {
                if (unit.getPos() <= BattleRules.BATTLE_FRONT_ROW_MAX_POSITION) {
                    front = true;
                }
            }
            List<BattleUnit> row = new ArrayList<>();
            for (BattleUnit unit : candidates) {
                if (!front || unit.getPos() <= BattleRules.BATTLE_FRONT_ROW_MAX_POSITION) {
                    row.add(unit);
                }
            }
            int sourceColumn = column(owner.getPos());
            row.sort((left, right) -> {
                int distance = Integer.compare(Math.abs(column(left.getPos()) - sourceColumn),
                        Math.abs(column(right.getPos()) - sourceColumn));
                if (distance != 0) {
                    return distance;
                }
                return Integer.compare(column(left.getPos()), column(right.getPos()));
            });
            if (row.isEmpty()) {
                return List.of();
            }
            return List.of(row.get(0));
        }
        candidates.sort((left, right) -> {
            int hp = Long.compare(left.getAttrValue(CURRENT_HP), right.getAttrValue(CURRENT_HP));
            if (hp != 0) {
                return hp;
            }
            return Integer.compare(left.getPos(), right.getPos());
        });
        int count = Math.min(spec.getTargetCount(), candidates.size());
        return new ArrayList<>(candidates.subList(0, count));
    }

    private static int column(int pos) {
        switch (pos) {
            case 1, 3: return 0;
            case 4: return 1;
            case 2, 5: return 2;
            default: throw new IllegalArgumentException("未知战斗位置");
        }
    }

    /** 单个锁定目标与理论效果值；value不是最终伤害/治疗量，尚未处理护盾和上限。 */
    public record Hit(BattleUnit target, long value) {}
    /** 本次技能使用锁定的目标与公式值；友方Buff在落地时从战场单位中筛选。 */
    public record SkillEffectResult(BaseSkill skill, BattleUnit owner, List<Hit> hits) {}

}
