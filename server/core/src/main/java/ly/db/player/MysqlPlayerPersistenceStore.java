package ly.db.player;

import ly.db.AbstractEntry;
import ly.db.MysqlService;
import ly.db.entry.PlayerHeroEntry;
import ly.db.entry.PlayerModuleEntry;
import ly.db.entry.PlayerResourceEntry;

import java.util.List;

public final class MysqlPlayerPersistenceStore implements PlayerPersistenceStore {
    public List<PlayerModuleEntry> loadModuleEntries(long playerId) {
        return loadPlayerEntries(PlayerModuleEntry.class, playerId);
    }

    public List<PlayerHeroEntry> loadHeroEntries(long playerId) {
        return loadPlayerEntries(PlayerHeroEntry.class, playerId);
    }

    public List<PlayerResourceEntry> loadResourceEntries(long playerId) {
        return loadPlayerEntries(PlayerResourceEntry.class, playerId);
    }

    private <T extends AbstractEntry> List<T> loadPlayerEntries(Class<T> type, long playerId) {
        return MysqlService.getInstance()
                .selectAllStrict(type, new String[] {"player_id"}, playerId);
    }

    public long generateEntityId() {
        return MysqlService.getInstance().generateEntityId();
    }

    public boolean acceptBatch(List<AbstractEntry> saves, List<AbstractEntry> deletes) {
        return MysqlService.getInstance().acceptEntityBatch(saves, deletes);
    }

    public boolean acceptEntitySnapshot(AbstractEntry entry) {
        return MysqlService.getInstance().save(entry);
    }

    public boolean acceptEntityDeletion(AbstractEntry entry) {
        return MysqlService.getInstance().delete(entry);
    }
}
