package ly.sceneserver.common.march;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import ly.config.SlgWorldObjectConfig;
import ly.config.SlgMonsterConfig;
import ly.config.SlgMonsterConfigManager;
import ly.ConfigLoadException;
import ly.LoggerDef;
import ly.proto.Battle.*;
import ly.battle.BattleEngine;
import ly.battle.BattleRules;
import ly.slg.SlgBattleUnits;
import ly.sceneserver.common.*;

/** 每个配置区域独立持有行军；整条生命周期（包括返程）由目标区域线程处理。 */
public final class SceneMarchService {
    private static final BattleRules.BattleDamageRules DAMAGE_RULES = new BattleRules.BattleDamageRules(1000, 800, 600, 1670);
    private final SceneShard shard;
    private final Map<Long, SceneMarchState> marches = new LinkedHashMap<>();
    private final Map<Long, SceneMarchSnapshot> completed = new HashMap<>();
    private Consumer<SceneMarchSnapshot> home;

    private final Map<Long, Long> occupiedTargets = new HashMap<>();
    private Supplier<Map<Integer, SlgWorldObjectConfig>> rules;
    private Consumer<SceneMarchSnapshot> changed = snapshot -> {};
    public void onChanged(Consumer<SceneMarchSnapshot> changed) { this.changed = changed; }
    private BiConsumer<SceneObject, Boolean> persistence = (object, deleted) -> {};

    public SceneMarchService(SceneShard shard) { this.shard = shard; }
    public void configure(Supplier<Map<Integer, SlgWorldObjectConfig>> rules, BiConsumer<SceneObject, Boolean> persistence,
            Consumer<SceneMarchSnapshot> home) {
        this.home = home;
        this.rules = rules;
        this.persistence = persistence;
    }

    public void restore(SceneMarchState march) {
        SceneMarchSnapshot snapshot = march.snapshot();
        if (snapshot.status() == SceneMarchStatus.FINISHED || snapshot.status() == SceneMarchStatus.CANCELLED) {
            completed.put(snapshot.marchId(), snapshot);
            return;
        }
        marches.put(snapshot.marchId(), march);
        if (snapshot.status() == SceneMarchStatus.GATHERING)
            occupiedTargets.put(snapshot.target().targetId(), snapshot.marchId());
    }

    public SceneMarchSnapshot start(long marchId, long playerId, ScenePoint origin,
            long targetId, ScenePoint targetPoint, SceneMarchType type, int troops, long power, long now, String gameServerId, BattleSideSnapshot attacker) {
        if (marches.containsKey(marchId) || completed.containsKey(marchId)) return ownedSnapshot(marchId, playerId);
        SceneObject target = requireTarget(targetId, targetPoint, type);
        boolean gather = type == SceneMarchType.GATHER;
        long tags = gather ? SceneTargetTag.GATHERABLE.mask() : SceneTargetTag.ATTACKABLE.mask();
        SceneTargetDescriptor descriptor = new SceneTargetDescriptor(targetId,
                gather ? SceneTargetType.RESOURCE : SceneTargetType.MONSTER, targetPoint, tags, target.stateVersion());
        SceneMarchState march = new SceneMarchState(marchId, playerId, 0, type,
                SceneMarchTag.SOLO.mask(), origin, descriptor, troops, power, 0);
        march.hostInRegion(shard.staticMap().regionId(targetPoint.x(), targetPoint.y()), gameServerId, attacker);
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

    public SceneMarchSnapshot ownedSnapshot(long marchId, long playerId) {
        SceneMarchState march = marches.get(marchId);
        SceneMarchSnapshot snapshot = march == null ? completed.get(marchId) : march.snapshot();
        if (snapshot == null || snapshot.ownerPlayerId() != playerId)
            throw new IllegalArgumentException("行军不存在或不属于当前玩家");
        return snapshot;
    }

    public SceneMarchState march(long marchId) { return marches.get(marchId); }

    public Collection<SceneMarchState> marches() { return marches.values(); }

    public void tick(long now) {
        if (rules == null) return;
        for (Iterator<SceneMarchState> it = marches.values().iterator(); it.hasNext();) {
            SceneMarchState march = it.next();
            SceneMarchSnapshot snapshot = march.snapshot();
            if (snapshot.status() == SceneMarchStatus.MARCHING && now >= snapshot.arrivalAtMillis()) {
                march.arrive();
                arrive(march, now);
                persist(march);
            } else if (snapshot.status() == SceneMarchStatus.GATHERING && now >= snapshot.gatherEndAtMillis()) {
                gather(march, now);
                persist(march);
            } else if (snapshot.status() == SceneMarchStatus.RETURNING && now >= snapshot.arrivalAtMillis()) {
                if (snapshot.rewardAmount() > 0) home.accept(snapshot);
                march.arrive();
                SceneMarchSnapshot finished = march.snapshot();
                completed.put(finished.marchId(), finished);
                it.remove();
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
            // 旧版已发出的行军没有攻方快照，直接空手回城。
            boolean victory = false;
            if (snapshot.attacker() != null) {
                try {
                    victory = fightMonster(snapshot, state, rule, now);
                } catch (ConfigLoadException | IllegalArgumentException error) {
                    LoggerDef.SystemLogger.error("野怪战斗配置错误，marchId={}，怪物模板={}，{}",
                            snapshot.marchId(), rule.monsterTemplateId, error.getMessage());
                }
            }
            march.setOutcome(rule.resourceId, victory ? rule.reward : 0, victory);
            if (victory) {
                removeDepletedTarget(target, rule);
            }
            returnHome(march, now);
        }
    }

    private boolean fightMonster(SceneMarchSnapshot march, SceneWorldObjectState state, SlgWorldObjectConfig rule, long now) {
        SlgMonsterConfig monster = SlgMonsterConfigManager.getInstance().getConfigMap().get(rule.monsterTemplateId);
        if (monster == null) throw new ConfigLoadException("slgMonster 缺少模板 " + rule.monsterTemplateId);
        BattleUnitSnapshot.Builder unit = SlgBattleUnits.single(PRT_HERO.newBuilder()
                        .setHeroTid(monster.id).setLevel(state.level()).build(), monster.hp, monster.attack, monster.defence, 1,
                monster.activeSkill == 0 ? null : SlgBattleUnits.skill(monster.activeSkill));
        unit.setUnitType(1).setMonsterTid(monster.id).setRemainingHp(Math.min(state.health(), monster.hp));
        BattleSideSnapshot defender = BattleSideSnapshot.newBuilder().addUnits(unit).build();
        return BattleEngine.fight(march.marchId(), now, 0, 20, DAMAGE_RULES, march.attacker(), defender)
                .getLog().getWinner() == PRT_BATTLE_SIDE.ATTACKER;
    }

    private void gather(SceneMarchState march, long now) {
        SceneMarchSnapshot snapshot = march.snapshot();
        SceneObject target = shard.object(snapshot.target().targetId());
        SceneWorldObjectState state = (SceneWorldObjectState) target.state();
        SlgWorldObjectConfig rule = rules.get().get(state.configId());
        long amount = Math.min(state.amount(), snapshot.troopCount());
        if (amount == state.amount()) {
            removeDepletedTarget(target, rule);
        } else {
            target.state(withAmounts(state, state.amount() - amount, state.health()));
            persistence.accept(target, false);
        }
        occupiedTargets.remove(target.objectId());
        march.setOutcome(rule.resourceId, amount, true);
        returnHome(march, now);
    }

    private void removeDepletedTarget(SceneObject target, SlgWorldObjectConfig rule) {
        persistence.accept(target, true);
        shard.removeDepletedTarget(target, rule);
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
                march, snapshot.stateVersion(), snapshot.tagMask()), false);
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
