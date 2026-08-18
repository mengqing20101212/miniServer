package ly.sceneserver.common.march;

/** 集结成员的不可变快照。 */
public record SceneRallyMemberSnapshot(
        long playerId,
        long marchId,
        int troopCount,
        long power,
        long armySnapshotVersion,
        SceneRallyMemberStatus status,
        int remainingTroops) {
}
