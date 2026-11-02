package ly.sceneserver.common.march;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;
import ly.config.SlgWorldObjectConfig;
import ly.sceneserver.common.*;

/** 每个配置区域独立持有行军；整条生命周期（包括返程）由目标区域线程处理。 */
public final class SceneMarchService {
    private final SceneShard shard;
    private final Map<Long, SceneMarchState> marches = new LinkedHashMap<>();
    private final Map<Long, Long> occupiedTargets = new HashMap<>();
    private Supplier<Map<Integer, SlgWorldObjectConfig>> rules;
    private Consumer<SceneMarchSnapshot> changed = snapshot -> {};
    public void onChanged(Consumer<SceneMarchSnapshot> changed) { this.changed = changed; }
    private Consumer<SceneObject> persistence = object -> {};

    public SceneMarchService(SceneShard shard) { this.shard = shard; }
    public void configure(Supplier<Map<Integer, SlgWorldObjectConfig>> rules, Consumer<SceneObject> persistence) {
        this.rules = rules;
        this.persistence = persistence;
    }

    public void restore(SceneMarchState march) {
        SceneMarchSnapshot snapshot = march.snapshot();
        marches.put(snapshot.marchId(), march);
        if (snapshot.status() == SceneMarchStatus.GATHERING)
            occupiedTargets.put(snapshot.target().targetId(), snapshot.marchId());
    }

    public SceneMarchSnapshot start(long marchId, long playerId, ScenePoint origin,
            long targetId, ScenePoint targetPoint, SceneMarchType type, int troops, long power, long now) {
        SceneMarchState existing = marches.get(marchId);
        if (existing != null) return requireOwned(marchId, playerId).snapshot();
        SceneObject target = requireTarget(targetId, targetPoint, type);
        boolean gather = type == SceneMarchType.GATHER;
        long tags = gather ? SceneTargetTag.GATHERABLE.mask() : SceneTargetTag.ATTACKABLE.mask();
        SceneTargetDescriptor descriptor = new SceneTargetDescriptor(targetId,
                gather ? SceneTargetType.RESOURCE : SceneTargetType.MONSTER, targetPoint, tags, target.stateVersion());
        SceneMarchState march = new SceneMarchState(marchId, playerId, 0, type,
                SceneMarchTag.SOLO.mask(), origin, descriptor, troops, power, 0);
        march.hostInRegion(shard.staticMap().regionId(targetPoint.x(), targetPoint.y()));
        march.assignPath(List.of(origin, targetPoint), travelCost(origin, targetPoint), speed(), now);
        marches.put(marchId, march);
        persist(march);
        return march.snapshot();
    }

    private SceneObject requireTarget(long targetId, ScenePoint point, SceneMarchType type) {
        SceneObject target = shard.object(targetId);
        if (target == null || target.x() != point.x() || target.y() != point.y())
            throw new IllegalArgumentException("目标不存在或坐标不符");
        if (!(target.state() instanceof SceneWorldObjectState state))
            throw new IllegalArgumentException("目标不是采集点或怪物");
        boolean gather = type == SceneMarchType.GATHER && target.type() == SceneObjectType.RESOURCE;
        boolean attack = type == SceneMarchType.ATTACK && target.type() == SceneObjectType.MONSTER;
        if ((!gather && !attack) || (gather ? state.amount() <= 0 : state.health() <= 0))
            throw new IllegalArgumentException("目标不支持该动作或已经耗尽");
        return target;
    }

    public SceneMarchState requireOwned(long marchId, long playerId) {
        SceneMarchState march = marches.get(marchId);
        if (march == null || march.snapshot().ownerPlayerId() != playerId)
            throw new IllegalArgumentException("行军不存在或不属于当前玩家");
        return march;
    }

    public SceneMarchState march(long marchId) { return marches.get(marchId); }

    public Collection<SceneMarchState> marches() { return marches.values(); }

    public void tick(long now) {
        if (rules == null) return;
        for (SceneMarchState march : marches.values()) {
            SceneMarchSnapshot snapshot = march.snapshot();
            if (snapshot.status() == SceneMarchStatus.MARCHING && now >= snapshot.arrivalAtMillis()) {
                march.arrive();
                arrive(march, now);
                persist(march);
            } else if (snapshot.status() == SceneMarchStatus.GATHERING && now >= snapshot.gatherEndAtMillis()) {
                gather(march, now);
                persist(march);
            } else if (snapshot.status() == SceneMarchStatus.RETURNING && now >= snapshot.arrivalAtMillis()) {
                march.arrive();
                persist(march);
            }
        }
    }

    private void arrive(SceneMarchState march, long now) {
        SceneMarchSnapshot snapshot = march.snapshot();
        SceneObject target = shard.object(snapshot.target().targetId());
        SceneWorldObjectState state = target == null ? null : (SceneWorldObjectState) target.state();
        if (state == null || (snapshot.type() == SceneMarchType.GATHER ? state.amount() <= 0 : state.health() <= 0)) {
            returnHome(march, now);
            return;
        }
        SlgWorldObjectConfig rule = rules.get().get(state.configId());
        if (snapshot.type() == SceneMarchType.GATHER) {
            if (occupiedTargets.putIfAbsent(target.objectId(), snapshot.marchId()) != null) {
                returnHome(march, now);
                return;
            }
            march.beginGathering(now + rule.gatherMillis);
        } else {
            // 尚未接入战斗模块：用冻结战力与怪物血量比较，不产生战损。
            boolean victory = snapshot.power() >= state.health();
            march.setOutcome(rule.resourceId, victory ? rule.reward : 0, victory);
            if (victory) {
                target.state(withAmounts(state, state.amount(), 0));
                persistence.accept(target);
            }
            returnHome(march, now);
        }
    }

    private void gather(SceneMarchState march, long now) {
        SceneMarchSnapshot snapshot = march.snapshot();
        SceneObject target = shard.object(snapshot.target().targetId());
        SceneWorldObjectState state = (SceneWorldObjectState) target.state();
        SlgWorldObjectConfig rule = rules.get().get(state.configId());
        long amount = Math.min(state.amount(), snapshot.troopCount());
        target.state(withAmounts(state, state.amount() - amount, state.health()));
        persistence.accept(target);
        occupiedTargets.remove(target.objectId());
        march.setOutcome(rule.resourceId, amount, true);
        returnHome(march, now);
    }

    private void returnHome(SceneMarchState march, long now) {
        SceneMarchSnapshot snapshot = march.snapshot();
        march.beginReturn(List.of(snapshot.target().point(), snapshot.origin()),
                travelCost(snapshot.origin(), snapshot.target().point()), speed(), now);
    }

    private void persist(SceneMarchState march) {
        SceneMarchSnapshot snapshot = march.snapshot();
        persistence.accept(SceneObject.restore(snapshot.marchId(), SceneObjectType.MARCH,
                snapshot.ownerPlayerId(), snapshot.target().point().x(), snapshot.target().point().y(),
                march, snapshot.stateVersion(), snapshot.tagMask()));
        changed.accept(snapshot);
    }

    private static SceneWorldObjectState withAmounts(SceneWorldObjectState state, long amount, long health) {
        return new SceneWorldObjectState(state.dataVersion(), state.configId(), state.ruleId(), state.level(),
                amount, health, state.refreshAtMillis(), state.expireAtMillis(), state.stateFlags(), state.extensionData());
    }
    private static int travelCost(ScenePoint from, ScenePoint to) {
        return Math.max(1, (int) Math.ceil(Math.hypot(to.x() - from.x(), to.y() - from.y())));
    }
    private static int speed() { return Integer.getInteger("slg.march.speed", 10); }
}
