package ly.battle.buff;

import ly.proto.Battle.AttrTypeEnum;
import ly.proto.Battle.BattleBuffSpec;
import java.util.Map;
import static ly.proto.Battle.AttrTypeEnum.DAMAGE_REDUCTION_RATE;

/** 激活时增加减伤千分比；移除时由BaseBuff撤销当时记录的增量。 */
public final class DamageReductionBuff extends BaseBuff {
    public DamageReductionBuff(long id, BattleBuffSpec spec, long sourceId, int round) {
        super(id, spec, sourceId, round);
    }

    @Override protected Map<AttrTypeEnum, Long> attributeDelta() {
        return Map.of(DAMAGE_REDUCTION_RATE, (long) getSpec().getValue());
    }
}
