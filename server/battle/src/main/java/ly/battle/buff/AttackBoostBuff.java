package ly.battle.buff;

import ly.proto.Battle.AttrTypeEnum;
import ly.proto.Battle.BattleBuffSpec;
import java.util.Map;
import static ly.proto.Battle.AttrTypeEnum.ATTACK_BOOST_RATE;

/** 激活时增加攻击千分比；移除时由BaseBuff撤销当时记录的增量。 */
public final class AttackBoostBuff extends BaseBuff {
    public AttackBoostBuff(long id, BattleBuffSpec spec, long sourceId, int round) {
        super(id, spec, sourceId, round);
    }

    @Override protected Map<AttrTypeEnum, Long> attributeDelta() {
        return Map.of(ATTACK_BOOST_RATE, (long) getSpec().getValue());
    }
}
