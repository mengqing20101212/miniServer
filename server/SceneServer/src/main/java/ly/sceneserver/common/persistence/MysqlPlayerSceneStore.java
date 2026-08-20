package ly.sceneserver.common.persistence;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

import ly.db.entry.PlayerSceneEntry;
import ly.db.entry.PlayerSceneEntryHelper;
import ly.sceneserver.common.ScenePoint;

/** MySQL 玩家场景投影实现，使用 revision 条件 UPSERT 防止异步旧快照覆盖新状态。 */
public final class MysqlPlayerSceneStore implements PlayerSceneStore {
    @Override
    public List<PlayerSceneProjection> loadActivePage(String sceneId, long afterPlayerId, int limit) {
        if (sceneId == null || sceneId.isBlank() || afterPlayerId < 0L || limit <= 0) {
            throw new IllegalArgumentException("invalid player scene page");
        }
        List<PlayerSceneEntry> entries = PlayerSceneEntryHelper.selectActivePage(
                sceneId, afterPlayerId, limit);
        ArrayList<PlayerSceneProjection> projections = new ArrayList<>(entries.size());
        for (PlayerSceneEntry entry : entries) {
            projections.add(toProjection(entry));
        }
        return List.copyOf(projections);
    }

    @Override
    public void upsert(PlayerSceneProjection projection) {
        toEntry(projection).upsertIfNewer();
    }

    /** 把业务不可变快照转换为统一数据库实体，SQL 生成和执行交给实体 Helper。 */
    private static PlayerSceneEntry toEntry(PlayerSceneProjection projection) {
        PlayerSceneEntry entry = new PlayerSceneEntry();
        entry.setPlayerId(projection.playerId());
        entry.setSceneId(projection.sceneId());
        entry.setCityObjectId(projection.cityObjectId());
        entry.setAllianceId(projection.allianceId());
        entry.setCityX(projection.cityPoint().x());
        entry.setCityY(projection.cityPoint().y());
        entry.setCityLevel(projection.cityLevel());
        entry.setCityStateVersion(projection.cityStateVersion());
        entry.setFogData(projection.discoveredBlocks().toByteArray());
        entry.setDataVersion(projection.dataVersion());
        entry.setRevision(projection.revision());
        entry.setDeleted(projection.deleted() ? 1 : 0);
        entry.setUpdateTime(projection.updateTime());
        return entry;
    }

    private static PlayerSceneProjection toProjection(PlayerSceneEntry entry) {
        return new PlayerSceneProjection(
                entry.getPlayerId(),
                entry.getSceneId(),
                entry.getCityObjectId(),
                entry.getAllianceId(),
                new ScenePoint(entry.getCityX(), entry.getCityY()),
                entry.getCityLevel(),
                entry.getCityStateVersion(),
                BitSet.valueOf(entry.getFogData()),
                entry.getDataVersion(),
                entry.getRevision(),
                entry.getDeleted() != 0,
                entry.getUpdateTime());
    }
}
