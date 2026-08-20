package ly.logic.player.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ly.db.entry.PlayerEntry;
import ly.db.entry.PlayerEntryHelper;
import ly.db.entry.PlayerModuleEntry;
import ly.db.entry.PlayerModuleEntryHelper;

/** 基于 MySQL 的模块级持久化实现。 */
public final class MysqlPlayerModuleStore implements PlayerModuleStore {
    @Override
    public Map<Integer, PlayerModuleEntry> load(long playerId) {
        List<PlayerModuleEntry> rows = PlayerModuleEntryHelper.selectByPlayerId(playerId);
        Map<Integer, PlayerModuleEntry> entries = new HashMap<>();
        for (PlayerModuleEntry entry : rows) {
            entries.put(entry.getModuleId(), entry);
        }
        return entries;
    }

    @Override
    public boolean saveBatch(long playerId, List<PlayerModuleEntry> modules) {
        if (modules == null || modules.isEmpty()) {
            return true;
        }
        for (PlayerModuleEntry module : modules) {
            if (module.getPlayerId() == null || module.getPlayerId() != playerId) {
                throw new IllegalArgumentException("player module entry belongs to another player: " + module.getPlayerId());
            }
            boolean success = module.getId() == null
                    ? module.save()
                    : module.update();
            if (!success) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean clearLegacyModuleData(PlayerEntry playerEntry) {
        byte[] legacyData = playerEntry.getModules();
        playerEntry.setModules(null);
        if (PlayerEntryHelper.update(playerEntry, "modules")) {
            return true;
        }
        playerEntry.setModules(legacyData);
        return false;
    }

}
