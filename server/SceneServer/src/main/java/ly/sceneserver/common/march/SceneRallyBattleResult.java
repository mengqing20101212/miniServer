package ly.sceneserver.common.march;

import java.util.Map;

/** BattleServer 返回并已落库的集结战斗结果摘要。 */
public record SceneRallyBattleResult(
        long battleResultId,
        long rallyId,
        long targetId,
        long targetVersion,
        boolean victory,
        int targetDurabilityLoss,
        Map<Long, Integer> remainingTroopsByPlayer) {

    public SceneRallyBattleResult {
        if (battleResultId <= 0 || rallyId <= 0 || targetId < 0 || targetVersion < 0
                || targetDurabilityLoss < 0 || remainingTroopsByPlayer == null) {
            throw new IllegalArgumentException("invalid rally battle result");
        }
        remainingTroopsByPlayer = Map.copyOf(remainingTroopsByPlayer);
    }
}
