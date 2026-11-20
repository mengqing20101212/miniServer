package ly.battle.buff;

import ly.battle.BattleRules;
import ly.battle.Stage;
import ly.battle.unit.BaseUnit;
import ly.battle.unit.BattleUnit;
import ly.proto.Battle.PRT_BATTLE_BUFF_STATE;
import ly.proto.Battle.PRT_BATTLE_EFFECT_TYPE;
import ly.proto.Battle.AttrTypeEnum;
import ly.proto.Battle.BattleBuffSpec;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * @author liuyang
 * @date 2026/10/9
 * @description Buff的生命周期入口。相同效果刷新原实例，不叠加数值；施加或刷新当回合不衰减。
 * 子类通过attributeDelta或生命周期方法定义具体行为；Stage提供回合和事件上下文。
 */
public class BaseBuff extends BaseUnit {
    private BattleBuffSpec spec;
    // 最近一次施加的来源实例ID，可以是技能，也可以是移除后触发此Buff的另一个Buff。
    private long sourceId;
    private int remainingRounds;
    // 最早生效的行动回合；effectTiming为0时从下一回合开始，为1时本回合即可生效。
    private int effectiveFromRound;
    // 回合末以此判断是否本回合刚新增/刷新，保证刷新后保留完整持续回合。
    private int lastAppliedRound;
    // 是否已将效果应用到单位；已生效Buff刷新时保留效果，防止重复叠加属性。
    private boolean active;
    // 只在首次激活时保存实际写入属性表的增量；移除时按这份记录撤销，不重新读取配置计算。
    private Map<AttrTypeEnum, Long> activeAttrDeltas = Map.of();

    protected BaseBuff(long id, BattleBuffSpec spec, long sourceId, int round) {
        super(id, spec.getId());
        this.spec = spec;
        refresh(spec, sourceId, round);
    }

    /** 按效果类型创建行为子类；新增效果类型时在这里登记，未知类型直接报错。 */
    public static BaseBuff create(long id, BattleBuffSpec spec, long sourceId, int round) {
        BattleBuffSpec.Effect kind = spec.getEffectType();
        switch (kind) {
            case TAUNT: return new TauntBuff(id, spec, sourceId, round);
            case DAMAGE_REDUCTION: return new DamageReductionBuff(id, spec, sourceId, round);
            case ATTACK_BOOST: return new AttackBoostBuff(id, spec, sourceId, round);
            case ATTACK_ADD, DEFENSE_ADD, HEALTH_ADD:
                return new FixedAttributeBuff(id, spec, sourceId, round);
            default: throw new IllegalArgumentException("未实现Buff效果类型：" + kind);
        }
    }

    /** 同类同值Buff复用实例并重置时长；已生效的效果保持有效，未生效的按新配置确定时机。 */
    protected void refresh(BattleBuffSpec definition, long source, int round) {
        if (spec.getEffectType() != definition.getEffectType() || spec.getValue() != definition.getValue()) {
            throw new IllegalArgumentException("同类Buff只能刷新持续时间，不能叠加或改变倍率");
        }
        spec = definition;
        sourceId = source;
        remainingRounds = definition.getDurationRounds();
        lastAppliedRound = round;
        if (!active) {
            effectiveFromRound = round + 1;
            if (definition.getEffectTiming() == BattleRules.BATTLE_BUFF_IMMEDIATE) {
                effectiveFromRound = round;
            }
        }
    }

    @Override public int getTid() { return spec.getId(); }
    public BattleBuffSpec getSpec() { return spec; }
    public boolean isActive() { return active; }
    public int getRemainingRounds() { return remainingRounds; }

    /** 首次挂载入口：按配置决定是否立即生效，并记录一次新增事件。 */
    public void onAdded(Stage stage, BattleUnit owner) {
        Map<AttrTypeEnum, Long> delta = Map.of();
        if (canActivate(stage.getRound())) {
            delta = activate(owner);
        }
        owner.recordEffect(stage, sourceId, PRT_BATTLE_EFFECT_TYPE.EFFECT_BUFF,
                delta, List.of(toPb()), List.of(), List.of());
    }

    /** 刷新入口：重置持续回合，已生效的效果不重复叠加，并记录更新事件。 */
    public void onRefreshed(Stage stage, BattleUnit owner, BattleBuffSpec definition, long source) {
        refresh(definition, source, stage.getRound());
        Map<AttrTypeEnum, Long> delta = Map.of();
        if (canActivate(stage.getRound())) {
            delta = activate(owner);
        }
        owner.recordEffect(stage, sourceId, PRT_BATTLE_EFFECT_TYPE.EFFECT_BUFF,
                delta, List.of(), List.of(toPb()), List.of());
    }

    /** 每回合开始检查生效时机；只有此刻首次激活才修改属性并记录更新事件。 */
    public void onRoundStart(Stage stage, BattleUnit owner) {
        if (!canActivate(stage.getRound())) {
            return;
        }
        Map<AttrTypeEnum, Long> delta = activate(owner);
        owner.recordEffect(stage, getId(), PRT_BATTLE_EFFECT_TYPE.EFFECT_BUFF,
                delta, List.of(), List.of(toPb()), List.of());
    }

    /** 回合末计时入口：刷新当回合不衰减；到期由Stage统一移除。 */
    public boolean onRoundEnd(Stage stage, BattleUnit owner) {
        if (lastAppliedRound == stage.getRound()) {
            return false;
        }
        remainingRounds--;
        if (remainingRounds == 0) {
            return true;
        }
        owner.recordEffect(stage, getId(), PRT_BATTLE_EFFECT_TYPE.EFFECT_BUFF,
                Map.of(), List.of(), List.of(toPb()), List.of());
        return false;
    }

    /** 移除入口：先撤销属性并记录删除，再把后继Buff加到原持有者。 */
    public void onRemoved(Stage stage, BattleUnit owner) {
        Map<AttrTypeEnum, Long> delta = Map.of();
        if (active) {
            delta = removalDelta(owner, activeAttrDeltas);
            owner.addAttrDeltas(delta);
            activeAttrDeltas = Map.of();
            active = false;
        }
        owner.recordEffect(stage, getId(), PRT_BATTLE_EFFECT_TYPE.EFFECT_BUFF,
                delta, List.of(), List.of(), List.of(getId()));
        // 后继Buff以当前Buff实例为来源；移除事件必须先于后继新增事件。
        for (BattleBuffSpec next : spec.getOnRemoveBuffsList()) {
            owner.addOrRefreshBuff(stage, next, getId());
        }
    }

    /** 嘲讽也是Buff自身行为，技能选目标时只询问此入口。 */
    public boolean isTaunting() { return false; }

    /** 子类只计算激活时要写入的属性增量；撤销使用激活时保存的增量。 */
    protected Map<AttrTypeEnum, Long> attributeDelta() { return Map.of(); }

    protected Map<AttrTypeEnum, Long> attributeDelta(BattleUnit owner) {
        return attributeDelta();
    }

    /** 默认撤销首次生效的增量；生命Buff按移除时的当前血量处理上限。 */
    protected Map<AttrTypeEnum, Long> removalDelta(BattleUnit owner, Map<AttrTypeEnum, Long> applied) {
        EnumMap<AttrTypeEnum, Long> reversal = new EnumMap<>(AttrTypeEnum.class);
        for (Map.Entry<AttrTypeEnum, Long> entry : applied.entrySet()) {
            reversal.put(entry.getKey(), Math.negateExact(entry.getValue()));
        }
        return reversal;
    }

    private boolean canActivate(int round) {
        return !active && effectiveFromRound <= round;
    }

    private Map<AttrTypeEnum, Long> activate(BattleUnit owner) {
        Map<AttrTypeEnum, Long> delta = attributeDelta(owner);
        owner.addAttrDeltas(delta);
        activeAttrDeltas = Map.copyOf(delta);
        active = true;
        return delta;
    }

    /** 在状态变化发生时生成协议值，后续刷新不会改写已记录的战报。 */
    public PRT_BATTLE_BUFF_STATE toPb() {
        PRT_BATTLE_BUFF_STATE.Builder state = PRT_BATTLE_BUFF_STATE.newBuilder();
        state.setBuffId(getId());
        state.setBuffTid(getTid());
        state.setSourceId(sourceId);
        state.setRemainingRounds(remainingRounds);
        state.setEffectiveFromRound(effectiveFromRound);
        state.setLastAppliedRound(lastAppliedRound);
        state.setActive(active);
        return state.build();
    }
}
