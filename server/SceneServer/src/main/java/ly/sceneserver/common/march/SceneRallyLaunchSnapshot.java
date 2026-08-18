package ly.sceneserver.common.march;

import java.util.List;

import ly.sceneserver.common.ScenePoint;

/**
 * 集结发车瞬间冻结的战斗输入摘要。
 *
 * <p>BattleServer 必须使用该快照，而不是在战斗开始时重新读取成员部队，避免发车后换兵、
 * 退盟或战力变化导致同一集结在不同节点得到不同结果。
 */
public record SceneRallyLaunchSnapshot(
        long rallyId,
        long leaderPlayerId,
        long allianceId,
        ScenePoint assemblyPoint,
        SceneTargetDescriptor target,
        List<SceneRallyMemberSnapshot> members,
        int totalTroops,
        long totalPower,
        long launchedAtMillis,
        int rallyVersion) {

    public SceneRallyLaunchSnapshot {
        members = List.copyOf(members);
    }
}
