package ly.sceneserver.common.march;

import java.util.List;

import ly.sceneserver.common.ScenePoint;

/** 可交给 RPC/AOI 的不可变行军快照。 */
public record SceneMarchSnapshot(
        long marchId,
        long ownerPlayerId,
        long allianceId,
        SceneMarchType type,
        long tagMask,
        SceneMarchStatus status,
        ScenePoint origin,
        SceneTargetDescriptor target,
        List<ScenePoint> path,
        int pathIndex,
        int troopCount,
        long power,
        long armySnapshotVersion,
        long departAtMillis,
        long arrivalAtMillis,
        int stateVersion,
        int targetRegionId,
        int resourceId,
        long rewardAmount,
        long gatherEndAtMillis,
        boolean victory,
        String gameServerId, ly.proto.Battle.BattleSideSnapshot attacker) {

    public SceneMarchSnapshot(long marchId, long ownerPlayerId, long allianceId,
            SceneMarchType type, long tagMask, SceneMarchStatus status, ScenePoint origin,
            SceneTargetDescriptor target, List<ScenePoint> path, int pathIndex, int troopCount,
            long power, long armySnapshotVersion, long departAtMillis, long arrivalAtMillis, int stateVersion) {
        this(marchId, ownerPlayerId, allianceId, type, tagMask, status, origin, target, path,
                pathIndex, troopCount, power, armySnapshotVersion, departAtMillis, arrivalAtMillis,
                stateVersion, 0, 0, 0, 0, false, "", null);
    }

    public SceneMarchSnapshot {
        path = List.copyOf(path);
    }
}
