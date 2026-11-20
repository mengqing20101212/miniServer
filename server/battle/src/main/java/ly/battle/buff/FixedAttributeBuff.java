package ly.battle.buff;

import ly.battle.unit.BattleUnit;
import ly.proto.Battle.AttrTypeEnum;
import ly.proto.Battle.BattleBuffSpec;
import java.util.Map;
import static ly.proto.Battle.AttrTypeEnum.*;

/** 固定属性加值。首次生效增加生命，刷新不叠加；到期只截断超出恢复后上限的血量。 */
public final class FixedAttributeBuff extends BaseBuff {
    public FixedAttributeBuff(long id, BattleBuffSpec spec, long sourceId, int round) {
        super(id, spec, sourceId, round);
    }

    @Override
    protected Map<AttrTypeEnum, Long> attributeDelta(BattleUnit owner) {
        long value = getSpec().getValue();
        BattleBuffSpec.Effect effect = getSpec().getEffectType();
        switch (effect) {
            case ATTACK_ADD:
                return Map.of(ATK, value);
            case DEFENSE_ADD:
                return Map.of(DEF, value);
            case HEALTH_ADD:
                if (owner.isAlive()) {
                    return Map.of(HP, value, CURRENT_HP, value);
                }
                return Map.of(HP, value);
            default:
                throw new IllegalArgumentException("非固定属性Buff：" + effect);
        }
    }

    @Override
    protected Map<AttrTypeEnum, Long> removalDelta(BattleUnit owner, Map<AttrTypeEnum, Long> applied) {
        if (getSpec().getEffectType() != BattleBuffSpec.Effect.HEALTH_ADD) {
            return super.removalDelta(owner, applied);
        }
        long addedMaxHp = applied.get(HP);
        long restoredMaxHp = owner.getAttrValue(HP) - addedMaxHp;
        long overflow = Math.max(0L, owner.getAttrValue(CURRENT_HP) - restoredMaxHp);
        return Map.of(HP, -addedMaxHp, CURRENT_HP, -overflow);
    }
}
