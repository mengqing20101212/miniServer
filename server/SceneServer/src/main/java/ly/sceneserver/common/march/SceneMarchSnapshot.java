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
        int stateVersion) {

    public SceneMarchSnapshot {
        path = List.copyOf(path);
    }
}
