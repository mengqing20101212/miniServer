package ly.logic.player.persistence;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ly.db.MysqlConnector;
import ly.db.MysqlService;
import ly.db.entry.PlayerModuleEntry;

/** 基于 MySQL 的模块级持久化实现。 */
public final class MysqlPlayerModuleStore implements PlayerModuleStore {
    private static final String SELECT_SQL = """
            SELECT player_id, module_id, data_version, revision, module_data, update_time
            FROM player_module
            WHERE player_id=?
            """;

    private static final String UPSERT_SQL = """
            INSERT INTO player_module
                (player_id, module_id, data_version, revision, module_data, update_time)
            VALUES (?, ?, ?, ?, ?, ?)
            ON DUPLICATE KEY UPDATE
                data_version=IF(VALUES(revision) >= revision, VALUES(data_version), data_version),
                module_data=IF(VALUES(revision) >= revision, VALUES(module_data), module_data),
                update_time=IF(VALUES(revision) >= revision, VALUES(update_time), update_time),
                revision=GREATEST(revision, VALUES(revision))
            """;

    @Override
    public Map<Integer, PlayerModuleEntry> load(long playerId) {
        MysqlConnector connector = MysqlService.getInstance().getMysqlConnector();
        List<Map<String, Object>> rows = connector.selectStrict(SELECT_SQL, playerId);
        Map<Integer, PlayerModuleEntry> entries = new HashMap<>();
        for (Map<String, Object> row : rows) {
            PlayerModuleEntry entry = MysqlService.packetEntry(row, PlayerModuleEntry.class);
            if (entry == null) {
                throw new IllegalStateException("load player module row failed, playerId=" + playerId + ", row=" + row);
            }
            entries.put(entry.getModuleId(), entry);
        }
        return entries;
    }

    @Override
    public boolean saveBatch(long playerId, List<PlayerModuleEntry> modules) {
        if (modules == null || modules.isEmpty()) {
            return true;
        }
        List<Object[]> params = new ArrayList<>(modules.size());
        for (PlayerModuleEntry module : modules) {
            if (module.getPlayerId() == null || module.getPlayerId() != playerId) {
                throw new IllegalArgumentException("player module entry belongs to another player: " + module.getPlayerId());
            }
            params.add(new Object[] {
                    playerId,
                    module.getModuleId(),
                    module.getDataVersion(),
                    module.getRevision(),
                    module.getModuleData(),
                    Timestamp.valueOf(module.getUpdateTime())
            });
        }
        return MysqlService.getInstance().getMysqlConnector().executeBatchTransaction(UPSERT_SQL, params);
    }

}
