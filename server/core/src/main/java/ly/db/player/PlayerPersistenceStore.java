package ly.db.player;

import ly.db.AbstractEntry;
import ly.db.entry.PlayerHeroEntry;
import ly.db.entry.PlayerModuleEntry;
import ly.db.entry.PlayerResourceEntry;

import java.util.List;

/** 所有保存返回成功均表示持久化层已接收，生产环境由 RocksService 保证后续提交。 */
public interface PlayerPersistenceStore {
    List<PlayerModuleEntry> loadModuleEntries(long playerId);

    List<PlayerHeroEntry> loadHeroEntries(long playerId);

    List<PlayerResourceEntry> loadResourceEntries(long playerId);

    long generateEntityId();

    boolean acceptEntitySnapshot(AbstractEntry entry);

    boolean acceptEntityDeletion(AbstractEntry entry);
}
