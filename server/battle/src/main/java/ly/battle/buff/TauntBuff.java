package ly.battle.buff;

import ly.proto.Battle.BattleBuffSpec;

/** 嘲讽BUFF生效期间将敌方技能目标引导到持有者。 */
public final class TauntBuff extends BaseBuff {
    public TauntBuff(long id, BattleBuffSpec spec, long sourceId, int round) {
        super(id, spec, sourceId, round);
    }

    @Override public boolean isTaunting() { return isActive(); }
}
