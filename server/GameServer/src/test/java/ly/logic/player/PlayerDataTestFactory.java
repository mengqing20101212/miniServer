package ly.logic.player;

import java.util.List;
import java.util.Map;
import ly.db.entry.PlayerEntry;
import ly.logic.player.persistence.PlayerModulePersistenceService;
import ly.logic.player.persistence.PlayerModuleRecord;
import ly.logic.player.persistence.PlayerModuleStore;

/** Creates PlayerData without requiring a test database. */
public final class PlayerDataTestFactory {
    private PlayerDataTestFactory() {
    }

    public static PlayerData create(PlayerEntry entry) {
        PlayerModuleStore store = new PlayerModuleStore() {
            @Override
            public Map<Integer, PlayerModuleRecord> load(long playerId) {
                return Map.of();
            }

            @Override
            public boolean saveBatch(long playerId, List<PlayerModuleRecord> modules) {
                return true;
            }
        };
        return new PlayerData(entry, new PlayerModulePersistenceService(store, false));
    }
}
