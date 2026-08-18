package ly.sceneserver.common.persistence;

import java.time.LocalDateTime;
import java.util.BitSet;

import ly.sceneserver.common.ScenePoint;

/**
 * GameServer/SceneServer 共同维护的玩家场景投影。
 *
 * <p>这里只保留重建地图需要的字段，禁止把整份玩家模块对象或 JSON 字符串塞进 SceneServer。
 */
public record PlayerSceneProjection(
        long playerId,
        String sceneId,
        long cityObjectId,
        long allianceId,
        ScenePoint cityPoint,
        int cityLevel,
        int cityStateVersion,
        BitSet discoveredBlocks,
        int dataVersion,
        long revision,
        boolean deleted,
        LocalDateTime updateTime) {

    public PlayerSceneProjection {
        if (playerId <= 0L || sceneId == null || sceneId.isBlank() || cityObjectId <= 0L
                || allianceId < 0L || cityPoint == null || cityLevel <= 0
                || cityStateVersion <= 0 || dataVersion <= 0 || revision <= 0L
                || updateTime == null) {
            throw new IllegalArgumentException("invalid player scene projection");
        }
        discoveredBlocks = discoveredBlocks == null
                ? new BitSet()
                : (BitSet) discoveredBlocks.clone();
    }

    @Override
    public BitSet discoveredBlocks() {
        return (BitSet) discoveredBlocks.clone();
    }

    public PlayerSceneProjection withDiscoveredBlocks(BitSet latestDiscoveredBlocks) {
        return new PlayerSceneProjection(
                playerId,
                sceneId,
                cityObjectId,
                allianceId,
                cityPoint,
                cityLevel,
                cityStateVersion,
                latestDiscoveredBlocks,
                dataVersion,
                revision,
                deleted,
                updateTime);
    }
}
