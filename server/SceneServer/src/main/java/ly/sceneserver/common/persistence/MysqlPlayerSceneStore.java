package ly.sceneserver.common.persistence;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Map;

import ly.db.MysqlConnector;
import ly.db.MysqlService;
import ly.db.entry.PlayerSceneEntry;
import ly.sceneserver.common.ScenePoint;

/** MySQL 玩家场景投影实现，使用 revision 条件 UPSERT 防止异步旧快照覆盖新状态。 */
public final class MysqlPlayerSceneStore implements PlayerSceneStore {
    private static final String LOAD_SQL = """
            SELECT id, player_id, scene_id, city_object_id, alliance_id, city_x, city_y,
                   city_level, city_state_version, fog_data, data_version, revision, deleted, update_time
            FROM player_scene
            WHERE scene_id=? AND player_id>? AND deleted=0
            ORDER BY player_id
            LIMIT ?
            """;

    private static final String UPSERT_SQL = """
            INSERT INTO player_scene (
                player_id, scene_id, city_object_id, alliance_id, city_x, city_y,
                city_level, city_state_version, fog_data, data_version, revision, deleted, update_time)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            ON DUPLICATE KEY UPDATE
                city_object_id=IF(VALUES(revision)>revision, VALUES(city_object_id), city_object_id),
                alliance_id=IF(VALUES(revision)>revision, VALUES(alliance_id), alliance_id),
                city_x=IF(VALUES(revision)>revision, VALUES(city_x), city_x),
                city_y=IF(VALUES(revision)>revision, VALUES(city_y), city_y),
                city_level=IF(VALUES(revision)>revision, VALUES(city_level), city_level),
                city_state_version=IF(VALUES(revision)>revision, VALUES(city_state_version), city_state_version),
                fog_data=IF(VALUES(revision)>revision, VALUES(fog_data), fog_data),
                data_version=IF(VALUES(revision)>revision, VALUES(data_version), data_version),
                deleted=IF(VALUES(revision)>revision, VALUES(deleted), deleted),
                update_time=IF(VALUES(revision)>revision, VALUES(update_time), update_time),
                revision=GREATEST(revision, VALUES(revision))
            """;

    @Override
    public List<PlayerSceneProjection> loadActivePage(String sceneId, long afterPlayerId, int limit) {
        if (sceneId == null || sceneId.isBlank() || afterPlayerId < 0L || limit <= 0) {
            throw new IllegalArgumentException("invalid player scene page");
        }
        MysqlConnector connector = MysqlService.getInstance().getMysqlConnector();
        List<Map<String, Object>> rows = connector.selectStrict(LOAD_SQL, sceneId, afterPlayerId, limit);
        ArrayList<PlayerSceneProjection> projections = new ArrayList<>(rows.size());
        for (Map<String, Object> row : rows) {
            PlayerSceneEntry entry = MysqlService.packetEntry(row, PlayerSceneEntry.class);
            if (entry == null) {
                throw new IllegalStateException("cannot decode player_scene row: " + row);
            }
            projections.add(toProjection(entry));
        }
        return List.copyOf(projections);
    }

    @Override
    public void upsert(PlayerSceneProjection projection) {
        MysqlService.getInstance().getMysqlConnector().executeUpdateStrict(
                UPSERT_SQL,
                projection.playerId(),
                projection.sceneId(),
                projection.cityObjectId(),
                projection.allianceId(),
                projection.cityPoint().x(),
                projection.cityPoint().y(),
                projection.cityLevel(),
                projection.cityStateVersion(),
                projection.discoveredBlocks().toByteArray(),
                projection.dataVersion(),
                projection.revision(),
                projection.deleted() ? 1 : 0,
                projection.updateTime());
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
