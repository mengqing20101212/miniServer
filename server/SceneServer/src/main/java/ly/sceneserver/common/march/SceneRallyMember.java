package ly.sceneserver.common.march;

/** SceneRallyState 内部成员状态，只能由集结所属 SceneShard 修改。 */
final class SceneRallyMember {
    private final long playerId;
    private final long marchId;
    private final int troopCount;
    private final long power;
    private final long armySnapshotVersion;
    private SceneRallyMemberStatus status;
    private int remainingTroops;

    SceneRallyMember(
            long playerId,
            long marchId,
            int troopCount,
            long power,
            long armySnapshotVersion,
            SceneRallyMemberStatus status) {
        if (playerId <= 0 || marchId <= 0 || troopCount <= 0 || power < 0 || armySnapshotVersion < 0
                || status == null) {
            throw new IllegalArgumentException("invalid rally member");
        }
        this.playerId = playerId;
        this.marchId = marchId;
        this.troopCount = troopCount;
        this.power = power;
        this.armySnapshotVersion = armySnapshotVersion;
        this.status = status;
        this.remainingTroops = troopCount;
    }

    /** 从集结聚合快照恢复成员；成员不单独落库。 */
    static SceneRallyMember restore(SceneRallyMemberSnapshot snapshot) {
        if (snapshot == null) {
            throw new IllegalArgumentException("rally member snapshot cannot be null");
        }
        SceneRallyMember member = new SceneRallyMember(
                snapshot.playerId(),
                snapshot.marchId(),
                snapshot.troopCount(),
                snapshot.power(),
                snapshot.armySnapshotVersion(),
                snapshot.status());
        member.applyRemainingTroops(snapshot.remainingTroops());
        return member;
    }

    long playerId() {
        return playerId;
    }

    int troopCount() {
        return troopCount;
    }

    long power() {
        return power;
    }

    SceneRallyMemberStatus status() {
        return status;
    }

    void status(SceneRallyMemberStatus status) {
        this.status = status;
    }

    void applyRemainingTroops(int remainingTroops) {
        if (remainingTroops < 0 || remainingTroops > troopCount) {
            throw new IllegalArgumentException("invalid remaining troops");
        }
        this.remainingTroops = remainingTroops;
    }

    SceneRallyMemberSnapshot snapshot() {
        return new SceneRallyMemberSnapshot(
                playerId,
                marchId,
                troopCount,
                power,
                armySnapshotVersion,
                status,
                remainingTroops);
    }
}
