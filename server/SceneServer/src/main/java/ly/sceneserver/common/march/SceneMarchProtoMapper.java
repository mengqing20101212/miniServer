package ly.sceneserver.common.march;

import ly.proto.Scene;
import ly.sceneserver.common.ScenePoint;

/** 行军和集结领域快照到 Protobuf 的集中转换，避免 Handler 复制枚举映射。 */
public final class SceneMarchProtoMapper {
    private SceneMarchProtoMapper() {
    }

    public static Scene.SceneMarchSnapshot toProto(SceneMarchSnapshot snapshot) {
        Scene.SceneMarchSnapshot.Builder builder = Scene.SceneMarchSnapshot.newBuilder()
                .setMarchId(snapshot.marchId())
                .setOwnerPlayerId(snapshot.ownerPlayerId())
                .setAllianceId(snapshot.allianceId())
                .setType(toProto(snapshot.type()))
                .setTagMask(snapshot.tagMask())
                .setStatus(toProto(snapshot.status()))
                .setOrigin(toProto(snapshot.origin()))
                .setTarget(toProto(snapshot.target()))
                .setPathIndex(snapshot.pathIndex())
                .setTroopCount(snapshot.troopCount())
                .setPower(snapshot.power())
                .setArmySnapshotVersion(snapshot.armySnapshotVersion())
                .setDepartAtMillis(snapshot.departAtMillis())
                .setArrivalAtMillis(snapshot.arrivalAtMillis())
                .setStateVersion(snapshot.stateVersion());
        for (ScenePoint point : snapshot.path()) {
            builder.addPath(toProto(point));
        }
        return builder.build();
    }

    public static Scene.SceneRallySnapshot toProto(SceneRallySnapshot snapshot) {
        Scene.SceneRallySnapshot.Builder builder = Scene.SceneRallySnapshot.newBuilder()
                .setRallyId(snapshot.rallyId())
                .setLeaderPlayerId(snapshot.leaderPlayerId())
                .setAllianceId(snapshot.allianceId())
                .setAssemblyPoint(toProto(snapshot.assemblyPoint()))
                .setTarget(toProto(snapshot.target()))
                .setCapacity(snapshot.capacity())
                .setMinimumMembers(snapshot.minimumMembers())
                .setLaunchAtMillis(snapshot.launchAtMillis())
                .setStatus(toProto(snapshot.status()))
                .setAppliedBattleResultId(snapshot.appliedBattleResultId())
                .setVictory(snapshot.victory())
                .setStateVersion(snapshot.stateVersion());
        for (SceneRallyMemberSnapshot member : snapshot.members()) {
            builder.addMembers(toProto(member));
        }
        return builder.build();
    }

    public static Scene.SceneRallyLaunchSnapshot toProto(SceneRallyLaunchSnapshot snapshot) {
        Scene.SceneRallyLaunchSnapshot.Builder builder = Scene.SceneRallyLaunchSnapshot.newBuilder()
                .setRallyId(snapshot.rallyId())
                .setLeaderPlayerId(snapshot.leaderPlayerId())
                .setAllianceId(snapshot.allianceId())
                .setAssemblyPoint(toProto(snapshot.assemblyPoint()))
                .setTarget(toProto(snapshot.target()))
                .setTotalTroops(snapshot.totalTroops())
                .setTotalPower(snapshot.totalPower())
                .setLaunchedAtMillis(snapshot.launchedAtMillis())
                .setRallyVersion(snapshot.rallyVersion());
        for (SceneRallyMemberSnapshot member : snapshot.members()) {
            builder.addMembers(toProto(member));
        }
        return builder.build();
    }

    private static Scene.SceneMarchTarget toProto(SceneTargetDescriptor target) {
        return Scene.SceneMarchTarget.newBuilder()
                .setTargetId(target.targetId())
                .setType(toProto(target.type()))
                .setPoint(toProto(target.point()))
                .setTagMask(target.tagMask())
                .setTargetVersion(target.targetVersion())
                .build();
    }

    private static Scene.SceneRallyMemberSnapshot toProto(SceneRallyMemberSnapshot member) {
        return Scene.SceneRallyMemberSnapshot.newBuilder()
                .setPlayerId(member.playerId())
                .setMarchId(member.marchId())
                .setTroopCount(member.troopCount())
                .setPower(member.power())
                .setArmySnapshotVersion(member.armySnapshotVersion())
                .setStatus(toProto(member.status()))
                .setRemainingTroops(member.remainingTroops())
                .build();
    }

    private static Scene.SceneMarchType toProto(SceneMarchType type) {
        return switch (type) {
            case ATTACK -> Scene.SceneMarchType.SCENE_MARCH_ATTACK;
            case RALLY_MEMBER -> Scene.SceneMarchType.SCENE_MARCH_RALLY_MEMBER;
            case RALLY_ARMY -> Scene.SceneMarchType.SCENE_MARCH_RALLY_ARMY;
            case REINFORCE -> Scene.SceneMarchType.SCENE_MARCH_REINFORCE;
            case GARRISON -> Scene.SceneMarchType.SCENE_MARCH_GARRISON;
            case GATHER -> Scene.SceneMarchType.SCENE_MARCH_GATHER;
            case SCOUT -> Scene.SceneMarchType.SCENE_MARCH_SCOUT;
            case TRANSPORT -> Scene.SceneMarchType.SCENE_MARCH_TRANSPORT;
            case RETURN -> Scene.SceneMarchType.SCENE_MARCH_RETURN;
        };
    }

    private static Scene.SceneMarchStatus toProto(SceneMarchStatus status) {
        return switch (status) {
            case PREPARING -> Scene.SceneMarchStatus.SCENE_MARCH_PREPARING;
            case MARCHING -> Scene.SceneMarchStatus.SCENE_MARCH_MOVING;
            case WAITING_RALLY -> Scene.SceneMarchStatus.SCENE_MARCH_WAITING_RALLY;
            case ARRIVED -> Scene.SceneMarchStatus.SCENE_MARCH_ARRIVED;
            case BATTLE_PENDING -> Scene.SceneMarchStatus.SCENE_MARCH_BATTLE_PENDING;
            case RETURNING -> Scene.SceneMarchStatus.SCENE_MARCH_RETURNING;
            case FINISHED -> Scene.SceneMarchStatus.SCENE_MARCH_FINISHED;
            case CANCELLED -> Scene.SceneMarchStatus.SCENE_MARCH_CANCELLED;
        };
    }

    private static Scene.SceneTargetType toProto(SceneTargetType type) {
        return switch (type) {
            case POINT -> Scene.SceneTargetType.SCENE_TARGET_POINT;
            case PLAYER_CITY -> Scene.SceneTargetType.SCENE_TARGET_PLAYER_CITY;
            case ALLIANCE_CITY -> Scene.SceneTargetType.SCENE_TARGET_ALLIANCE_CITY;
            case ALLIANCE_BUILDING -> Scene.SceneTargetType.SCENE_TARGET_ALLIANCE_BUILDING;
            case RESOURCE -> Scene.SceneTargetType.SCENE_TARGET_RESOURCE;
            case MONSTER -> Scene.SceneTargetType.SCENE_TARGET_MONSTER;
            case RALLY_CAMP -> Scene.SceneTargetType.SCENE_TARGET_RALLY_CAMP;
            case TROOP -> Scene.SceneTargetType.SCENE_TARGET_TROOP;
            case CROSS_SERVER_OBJECT -> Scene.SceneTargetType.SCENE_TARGET_CROSS_SERVER_OBJECT;
        };
    }

    private static Scene.SceneRallyStatus toProto(SceneRallyStatus status) {
        return switch (status) {
            case RECRUITING -> Scene.SceneRallyStatus.SCENE_RALLY_RECRUITING;
            case MARCHING -> Scene.SceneRallyStatus.SCENE_RALLY_MOVING;
            case BATTLE_PENDING -> Scene.SceneRallyStatus.SCENE_RALLY_BATTLE_PENDING;
            case RETURNING -> Scene.SceneRallyStatus.SCENE_RALLY_RETURNING;
            case FINISHED -> Scene.SceneRallyStatus.SCENE_RALLY_FINISHED;
            case CANCELLED -> Scene.SceneRallyStatus.SCENE_RALLY_CANCELLED;
        };
    }

    private static Scene.SceneRallyMemberStatus toProto(SceneRallyMemberStatus status) {
        return switch (status) {
            case JOINING -> Scene.SceneRallyMemberStatus.SCENE_RALLY_MEMBER_JOINING;
            case READY -> Scene.SceneRallyMemberStatus.SCENE_RALLY_MEMBER_READY;
            case MARCHING -> Scene.SceneRallyMemberStatus.SCENE_RALLY_MEMBER_MOVING;
            case BATTLE_PENDING -> Scene.SceneRallyMemberStatus.SCENE_RALLY_MEMBER_BATTLE_PENDING;
            case RETURNING -> Scene.SceneRallyMemberStatus.SCENE_RALLY_MEMBER_RETURNING;
            case FINISHED -> Scene.SceneRallyMemberStatus.SCENE_RALLY_MEMBER_FINISHED;
            case LEFT -> Scene.SceneRallyMemberStatus.SCENE_RALLY_MEMBER_LEFT;
            case EXCLUDED -> Scene.SceneRallyMemberStatus.SCENE_RALLY_MEMBER_EXCLUDED;
        };
    }

    private static Scene.ScenePoint toProto(ScenePoint point) {
        return Scene.ScenePoint.newBuilder().setX(point.x()).setY(point.y()).build();
    }
}
