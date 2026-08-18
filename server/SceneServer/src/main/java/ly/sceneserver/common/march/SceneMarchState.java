package ly.sceneserver.common.march;

import java.util.List;

import ly.sceneserver.common.ScenePoint;

/**
 * SceneServer 权威行军状态。
 *
 * <p>GameServer 在发车前冻结兵种、兵量、英雄和战力版本；SceneServer 只保存地图移动所需
 * 摘要，不直接修改玩家养成数据。战斗完成后再把结果投递给 GameServer 玩家队列。
 */
public final class SceneMarchState {
    private final long marchId;
    private final long ownerPlayerId;
    private final long allianceId;
    private final SceneMarchType type;
    private final ScenePoint origin;
    private final SceneTargetDescriptor target;
    private final int troopCount;
    private final long power;
    private final long armySnapshotVersion;
    private long tagMask;
    private SceneMarchStatus status = SceneMarchStatus.PREPARING;
    private List<ScenePoint> path = List.of();
    private int pathIndex;
    private long departAtMillis;
    private long arrivalAtMillis;
    private int stateVersion = 1;

    public SceneMarchState(
            long marchId,
            long ownerPlayerId,
            long allianceId,
            SceneMarchType type,
            long tagMask,
            ScenePoint origin,
            SceneTargetDescriptor target,
            int troopCount,
            long power,
            long armySnapshotVersion) {
        if (marchId <= 0 || ownerPlayerId <= 0 || type == null || origin == null || target == null
                || troopCount <= 0 || power < 0 || armySnapshotVersion < 0) {
            throw new IllegalArgumentException("invalid scene march");
        }
        if (!target.supports(type)) {
            throw new IllegalArgumentException("target does not support march type: " + type);
        }
        this.marchId = marchId;
        this.ownerPlayerId = ownerPlayerId;
        this.allianceId = allianceId;
        this.type = type;
        this.tagMask = tagMask;
        this.origin = origin;
        this.target = target;
        this.troopCount = troopCount;
        this.power = power;
        this.armySnapshotVersion = armySnapshotVersion;
    }

    /** 寻路完成后进入行军；速度单位是“地形代价/秒”，到达时间由服务端统一计算。 */
    public void assignPath(List<ScenePoint> path, int totalPathCost, int speedCostPerSecond, long nowMillis) {
        requireStatus(SceneMarchStatus.PREPARING);
        if (path == null || path.size() < 2 || !path.getFirst().equals(origin)
                || !path.getLast().equals(target.point()) || totalPathCost <= 0 || speedCostPerSecond <= 0) {
            throw new IllegalArgumentException("invalid march path");
        }
        this.path = List.copyOf(path);
        this.pathIndex = 0;
        this.departAtMillis = nowMillis;
        long travelMillis = Math.max(1L, (totalPathCost * 1_000L + speedCostPerSecond - 1)
                / speedCostPerSecond);
        this.arrivalAtMillis = Math.addExact(nowMillis, travelMillis);
        this.status = SceneMarchStatus.MARCHING;
        stateVersion++;
    }

    /** Tick 推进到新的路径下标；跨 SceneShard 时应由 SceneRuntime 完成对象所有权转移。 */
    public void advanceTo(int newPathIndex) {
        if (status != SceneMarchStatus.MARCHING && status != SceneMarchStatus.RETURNING) {
            throw new IllegalStateException("march is not moving: " + status);
        }
        if (newPathIndex <= pathIndex || newPathIndex >= path.size()) {
            throw new IllegalArgumentException("invalid path index: " + newPathIndex);
        }
        pathIndex = newPathIndex;
        stateVersion++;
    }

    /** 到达目标后按车辆类型进入等待集结、等待战斗或普通到达状态。 */
    public void arrive() {
        if (status != SceneMarchStatus.MARCHING && status != SceneMarchStatus.RETURNING) {
            throw new IllegalStateException("march cannot arrive from: " + status);
        }
        pathIndex = path.size() - 1;
        if (status == SceneMarchStatus.RETURNING || type == SceneMarchType.RETURN) {
            status = SceneMarchStatus.FINISHED;
        } else if (type == SceneMarchType.RALLY_MEMBER) {
            status = SceneMarchStatus.WAITING_RALLY;
        } else if (type == SceneMarchType.ATTACK || type == SceneMarchType.RALLY_ARMY) {
            status = SceneMarchStatus.BATTLE_PENDING;
            tagMask |= SceneMarchTag.BATTLE_PENDING.mask();
        } else {
            status = SceneMarchStatus.ARRIVED;
        }
        stateVersion++;
    }

    /** 战斗、采集或增援完成后使用返程路径；返程仍由同一行军对象继续推进。 */
    public void beginReturn(
            List<ScenePoint> returnPath,
            int totalPathCost,
            int speedCostPerSecond,
            long nowMillis) {
        if (status != SceneMarchStatus.ARRIVED
                && status != SceneMarchStatus.WAITING_RALLY
                && status != SceneMarchStatus.BATTLE_PENDING) {
            throw new IllegalStateException("march cannot return from: " + status);
        }
        if (returnPath == null || returnPath.size() < 2
                || !returnPath.getFirst().equals(target.point())
                || !returnPath.getLast().equals(origin)
                || totalPathCost <= 0 || speedCostPerSecond <= 0) {
            throw new IllegalArgumentException("invalid return path");
        }
        path = List.copyOf(returnPath);
        pathIndex = 0;
        departAtMillis = nowMillis;
        arrivalAtMillis = Math.addExact(nowMillis,
                Math.max(1L, (totalPathCost * 1_000L + speedCostPerSecond - 1)
                        / speedCostPerSecond));
        status = SceneMarchStatus.RETURNING;
        tagMask &= ~SceneMarchTag.BATTLE_PENDING.mask();
        tagMask |= SceneMarchTag.RETURNING.mask();
        stateVersion++;
    }

    public void cancel() {
        if (status != SceneMarchStatus.PREPARING && status != SceneMarchStatus.MARCHING) {
            throw new IllegalStateException("march cannot cancel from: " + status);
        }
        status = SceneMarchStatus.CANCELLED;
        stateVersion++;
    }

    public SceneMarchSnapshot snapshot() {
        return new SceneMarchSnapshot(
                marchId,
                ownerPlayerId,
                allianceId,
                type,
                tagMask,
                status,
                origin,
                target,
                path,
                pathIndex,
                troopCount,
                power,
                armySnapshotVersion,
                departAtMillis,
                arrivalAtMillis,
                stateVersion);
    }

    private void requireStatus(SceneMarchStatus expected) {
        if (status != expected) {
            throw new IllegalStateException("expected " + expected + " but was " + status);
        }
    }
}
