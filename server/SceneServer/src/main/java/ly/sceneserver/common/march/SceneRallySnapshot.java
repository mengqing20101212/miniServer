package ly.sceneserver.common.march;

import java.util.List;

import ly.sceneserver.common.ScenePoint;

/** 可用于 RPC、场景快照和恢复的集结只读数据。 */
public record SceneRallySnapshot(
        long rallyId,
        long leaderPlayerId,
        long allianceId,
        ScenePoint assemblyPoint,
        SceneTargetDescriptor target,
        int capacity,
        int minimumMembers,
        long launchAtMillis,
        SceneRallyStatus status,
        List<SceneRallyMemberSnapshot> members,
        long appliedBattleResultId,
        boolean victory,
        int stateVersion) {

    public SceneRallySnapshot {
        members = List.copyOf(members);
    }
}
