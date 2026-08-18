package ly.sceneserver.common.march;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import ly.sceneserver.common.ScenePoint;

/**
 * 经典 SLG 集结状态机。
 *
 * <p>创建、加入、到达、发车、战斗结果和返程都必须在集结所属 SceneShard 串行执行。
 * 联盟成员资格、队列占用和扣兵由 GameServer 在命令进入 SceneServer 前完成并冻结版本。
 */
public final class SceneRallyState {
    private final long rallyId;
    private final long leaderPlayerId;
    private final long allianceId;
    private final ScenePoint assemblyPoint;
    private final SceneTargetDescriptor target;
    private final int capacity;
    private final int minimumMembers;
    private final long launchAtMillis;
    private final Map<Long, SceneRallyMember> members = new LinkedHashMap<>();
    private SceneRallyStatus status = SceneRallyStatus.RECRUITING;
    private long appliedBattleResultId;
    private boolean victory;
    private int stateVersion = 1;

    public SceneRallyState(
            long rallyId,
            long leaderPlayerId,
            long allianceId,
            long leaderMarchId,
            ScenePoint assemblyPoint,
            SceneTargetDescriptor target,
            int capacity,
            int minimumMembers,
            long launchAtMillis,
            int leaderTroops,
            long leaderPower,
            long leaderArmySnapshotVersion) {
        if (rallyId <= 0 || leaderPlayerId <= 0 || allianceId <= 0 || leaderMarchId <= 0
                || assemblyPoint == null || target == null || capacity <= 0
                || minimumMembers <= 0 || minimumMembers > capacity || launchAtMillis <= 0) {
            throw new IllegalArgumentException("invalid rally");
        }
        if (!target.supports(SceneMarchType.RALLY_ARMY)) {
            throw new IllegalArgumentException("rally target must be attackable and rallyable");
        }
        this.rallyId = rallyId;
        this.leaderPlayerId = leaderPlayerId;
        this.allianceId = allianceId;
        this.assemblyPoint = assemblyPoint;
        this.target = target;
        this.capacity = capacity;
        this.minimumMembers = minimumMembers;
        this.launchAtMillis = launchAtMillis;
        members.put(leaderPlayerId, new SceneRallyMember(
                leaderPlayerId,
                leaderMarchId,
                leaderTroops,
                leaderPower,
                leaderArmySnapshotVersion,
                SceneRallyMemberStatus.READY));
    }

    /** 成员加入后先行军到集结点，未到达前为 JOINING。 */
    public void join(
            long playerId,
            long memberAllianceId,
            long marchId,
            int troopCount,
            long power,
            long armySnapshotVersion,
            long nowMillis) {
        requireRecruiting();
        if (nowMillis >= launchAtMillis) {
            throw new IllegalStateException("rally join deadline passed");
        }
        if (memberAllianceId != allianceId) {
            throw new IllegalArgumentException("rally member alliance mismatch");
        }
        if (members.size() >= capacity) {
            throw new IllegalStateException("rally is full");
        }
        if (members.containsKey(playerId)) {
            throw new IllegalStateException("player already joined rally: " + playerId);
        }
        members.put(playerId, new SceneRallyMember(
                playerId,
                marchId,
                troopCount,
                power,
                armySnapshotVersion,
                SceneRallyMemberStatus.JOINING));
        stateVersion++;
    }

    /** 成员车到达集结点后才能进入最终发车名单。 */
    public void memberArrived(long playerId) {
        requireRecruiting();
        SceneRallyMember member = requireMember(playerId);
        if (member.status() != SceneRallyMemberStatus.JOINING) {
            throw new IllegalStateException("rally member is not joining: " + member.status());
        }
        member.status(SceneRallyMemberStatus.READY);
        stateVersion++;
    }

    /** 发车前允许成员主动退出；队长退出应走 cancel，避免留下无主集结。 */
    public void leave(long playerId) {
        requireRecruiting();
        if (playerId == leaderPlayerId) {
            throw new IllegalStateException("rally leader must cancel rally");
        }
        SceneRallyMember member = requireMember(playerId);
        member.status(SceneRallyMemberStatus.LEFT);
        members.remove(playerId);
        stateVersion++;
    }

    /**
     * 到时发车并冻结成员；没赶到集结点的车标记为 EXCLUDED，不参与本次 BattleServer 计算。
     */
    public SceneRallyLaunchSnapshot launch(long operatorPlayerId, long nowMillis) {
        requireRecruiting();
        if (operatorPlayerId != leaderPlayerId) {
            throw new IllegalArgumentException("only rally leader can launch");
        }
        if (nowMillis < launchAtMillis) {
            throw new IllegalStateException("rally launch time not reached");
        }
        List<SceneRallyMember> readyMembers = members.values().stream()
                .filter(member -> member.status() == SceneRallyMemberStatus.READY)
                .toList();
        if (readyMembers.size() < minimumMembers) {
            throw new IllegalStateException("not enough ready rally members");
        }
        ArrayList<SceneRallyMemberSnapshot> launchedMembers = new ArrayList<>();
        int totalTroops = 0;
        long totalPower = 0L;
        for (SceneRallyMember member : members.values()) {
            if (member.status() == SceneRallyMemberStatus.READY) {
                member.status(SceneRallyMemberStatus.MARCHING);
                launchedMembers.add(member.snapshot());
                totalTroops = Math.addExact(totalTroops, member.troopCount());
                totalPower = Math.addExact(totalPower, member.power());
            } else if (member.status() == SceneRallyMemberStatus.JOINING) {
                member.status(SceneRallyMemberStatus.EXCLUDED);
            }
        }
        status = SceneRallyStatus.MARCHING;
        stateVersion++;
        return new SceneRallyLaunchSnapshot(
                rallyId,
                leaderPlayerId,
                allianceId,
                assemblyPoint,
                target,
                launchedMembers,
                totalTroops,
                totalPower,
                nowMillis,
                stateVersion);
    }

    /** 主车到达城市并成功提交 BattleServer 后进入等待结果状态。 */
    public void markBattlePending() {
        if (status != SceneRallyStatus.MARCHING) {
            throw new IllegalStateException("rally is not marching");
        }
        status = SceneRallyStatus.BATTLE_PENDING;
        for (SceneRallyMember member : members.values()) {
            if (member.status() == SceneRallyMemberStatus.MARCHING) {
                member.status(SceneRallyMemberStatus.BATTLE_PENDING);
            }
        }
        stateVersion++;
    }

    /**
     * 幂等应用已落库的 BattleServer 结果；同一 resultId 重放直接忽略，不同结果禁止覆盖。
     */
    public boolean applyBattleResult(SceneRallyBattleResult result) {
        if (result.rallyId() != rallyId || result.targetId() != target.targetId()
                || result.targetVersion() != target.targetVersion()) {
            throw new IllegalArgumentException("battle result target mismatch");
        }
        if (appliedBattleResultId != 0L) {
            if (appliedBattleResultId == result.battleResultId()) {
                return false;
            }
            throw new IllegalStateException("different battle result already applied");
        }
        if (status != SceneRallyStatus.BATTLE_PENDING) {
            throw new IllegalStateException("rally is not waiting battle result");
        }
        for (Map.Entry<Long, Integer> entry : result.remainingTroopsByPlayer().entrySet()) {
            SceneRallyMember member = members.get(entry.getKey());
            if (member != null && member.status() == SceneRallyMemberStatus.BATTLE_PENDING) {
                member.applyRemainingTroops(entry.getValue());
            }
        }
        for (SceneRallyMember member : members.values()) {
            if (member.status() == SceneRallyMemberStatus.BATTLE_PENDING) {
                member.status(SceneRallyMemberStatus.RETURNING);
            }
        }
        appliedBattleResultId = result.battleResultId();
        victory = result.victory();
        status = SceneRallyStatus.RETURNING;
        stateVersion++;
        return true;
    }

    /** 每个成员返城后独立完成；所有参战成员返城才回收集结。 */
    public void memberReturned(long playerId) {
        if (status != SceneRallyStatus.RETURNING) {
            throw new IllegalStateException("rally is not returning");
        }
        SceneRallyMember member = requireMember(playerId);
        if (member.status() != SceneRallyMemberStatus.RETURNING) {
            throw new IllegalStateException("member is not returning: " + member.status());
        }
        member.status(SceneRallyMemberStatus.FINISHED);
        boolean allFinished = members.values().stream().allMatch(value ->
                value.status() == SceneRallyMemberStatus.FINISHED
                        || value.status() == SceneRallyMemberStatus.EXCLUDED
                        || value.status() == SceneRallyMemberStatus.LEFT);
        if (allFinished) {
            status = SceneRallyStatus.FINISHED;
        }
        stateVersion++;
    }

    public void cancel(long operatorPlayerId) {
        requireRecruiting();
        if (operatorPlayerId != leaderPlayerId) {
            throw new IllegalArgumentException("only rally leader can cancel");
        }
        for (SceneRallyMember member : members.values()) {
            if (member.status() == SceneRallyMemberStatus.JOINING
                    || member.status() == SceneRallyMemberStatus.READY) {
                member.status(SceneRallyMemberStatus.LEFT);
            }
        }
        status = SceneRallyStatus.CANCELLED;
        stateVersion++;
    }

    public SceneRallySnapshot snapshot() {
        List<SceneRallyMemberSnapshot> memberSnapshots = members.values().stream()
                .map(SceneRallyMember::snapshot)
                .toList();
        return new SceneRallySnapshot(
                rallyId,
                leaderPlayerId,
                allianceId,
                assemblyPoint,
                target,
                capacity,
                minimumMembers,
                launchAtMillis,
                status,
                memberSnapshots,
                appliedBattleResultId,
                victory,
                stateVersion);
    }

    private SceneRallyMember requireMember(long playerId) {
        SceneRallyMember member = members.get(playerId);
        if (member == null) {
            throw new IllegalArgumentException("rally member not found: " + playerId);
        }
        return member;
    }

    private void requireRecruiting() {
        if (status != SceneRallyStatus.RECRUITING) {
            throw new IllegalStateException("rally is not recruiting: " + status);
        }
    }
}
