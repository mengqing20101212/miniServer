package ly.battle.event;

import ly.proto.Battle.AttrTypeEnum;
import ly.proto.Battle.*;
import java.util.List;
import java.util.Map;

/** 战场操作的战报出口。效果事件保存操作落地时的增量和Buff快照。 */
public sealed interface BattleEvent {
    /** 开始一个逻辑行动回合，对应PRT_BATTLE_ROUND；不包含真实等待时间。 */
    record RoundStarted(int round) implements BattleEvent {}
    /** 开始一批行动，对应PRT_BATTLE_BATCH；批内行动先锁定，再落地。 */
    record BatchStarted(PRT_BATTLE_PHASE phase) implements BattleEvent {}
    /**
     * 登记行动发生源；即使没有命中目标，也保留这次技能/普攻行动。
     * rootAction表示根技能，组合子步骤为false；单位统计只计根行动，技能详情各实例分别计次，均不依赖命中数量。
     */
    record ActionStarted(long sourceId, boolean rootAction) implements BattleEvent {}
    /**
     * 对一个目标已实际落地的效果，对应PRT_BATTLE_TARGET_RESULT。
     * deltas为有符号属性增量；Buff增删改另行记录，客户端不能据Buff配置再次重复加减属性。
     * sourceId引用技能/Buff实例；系统加能使用0。Stage同步消费事件，传入集合在消费期间不修改。
     */
    record Effect(long sourceId, long targetId, PRT_BATTLE_EFFECT_TYPE type,
                  Map<AttrTypeEnum, Long> deltas, List<PRT_BATTLE_BUFF_STATE> added,
                  List<PRT_BATTLE_BUFF_STATE> updated, List<Long> removed) implements BattleEvent {}
    /** 回合末判胜后通知战报收尾，并携带此时的最终状态；累计统计由BattleReport负责。 */
    record Ended(PRT_BATTLE_SIDE winner, PRT_BATTLE_END_REASON reason,
                 List<PRT_BATTLE_UNIT_STATE> states) implements BattleEvent {}
}
