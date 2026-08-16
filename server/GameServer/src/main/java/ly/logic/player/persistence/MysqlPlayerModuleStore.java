package ly.logic.player.persistence;

import java.sql.Blob;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ly.db.MysqlConnector;
import ly.db.MysqlService;

/** 基于 MySQL 的模块级持久化实现。 */
public final class MysqlPlayerModuleStore implements PlayerModuleStore {
    private static final String SELECT_SQL = """
            SELECT module_id, data_version, revision, module_data
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
    public Map<Integer, PlayerModuleRecord> load(long playerId) {
        MysqlConnector connector = MysqlService.getInstance().getMysqlConnector();
        List<Map<String, Object>> rows = connector.selectStrict(SELECT_SQL, playerId);
        Map<Integer, PlayerModuleRecord> records = new HashMap<>();
        for (Map<String, Object> row : rows) {
            try {
                int moduleId = number(row.get("module_id")).intValue();
                int dataVersion = number(row.get("data_version")).intValue();
                long revision = number(row.get("revision")).longValue();
                records.put(moduleId, new PlayerModuleRecord(moduleId, dataVersion, revision, bytes(row.get("module_data"))));
            } catch (Exception e) {
                throw new IllegalStateException("load player module row failed, playerId=" + playerId + ", row=" + row, e);
            }
        }
        return records;
    }

    @Override
    public boolean saveBatch(long playerId, List<PlayerModuleRecord> modules) {
        if (modules == null || modules.isEmpty()) {
            return true;
        }
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        List<Object[]> params = new ArrayList<>(modules.size());
        for (PlayerModuleRecord module : modules) {
            params.add(new Object[] {
                    playerId,
                    module.moduleId(),
                    module.dataVersion(),
                    module.revision(),
                    module.data(),
                    now
            });
        }
        return MysqlService.getInstance().getMysqlConnector().executeBatchTransaction(UPSERT_SQL, params);
    }

    private static Number number(Object value) {
        if (value instanceof Number number) {
            return number;
        }
        return Long.parseLong(String.valueOf(value));
    }

    private static byte[] bytes(Object value) throws Exception {
        if (value instanceof byte[] data) {
            return data;
        }
        if (value instanceof Blob blob) {
            return blob.getBytes(1, (int) blob.length());
        }
        throw new IllegalArgumentException("unsupported module_data type: " + (value == null ? "null" : value.getClass()));
    }
}
