package ly.logic.player;

import ly.db.AbstractEntry;
import ly.db.entry.*;
import ly.db.player.PlayerPersistenceStore;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public final class PlayerDataTestFactory {
    private PlayerDataTestFactory() {}

    public static class MemoryStore implements PlayerPersistenceStore {
        private static final AtomicLong IDS = new AtomicLong(10000);

        public List<PlayerModuleEntry> loadModuleEntries(long playerId) {
            return List.of();
        }

        public List<PlayerHeroEntry> loadHeroEntries(long playerId) {
            return List.of();
        }

        public List<PlayerResourceEntry> loadResourceEntries(long playerId) {
            return List.of();
        }

        public long generateEntityId() {
            return IDS.incrementAndGet();
        }

        public boolean acceptEntitySnapshot(AbstractEntry entry) {
            return true;
        }

        public boolean acceptEntityDeletion(AbstractEntry entry) {
            return true;
        }
    }

    public static PlayerData create(PlayerEntry entry) {
        return new PlayerData(entry, new MemoryStore());
    }
}
