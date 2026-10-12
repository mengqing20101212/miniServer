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

    /** 测试存储可覆盖；生产实现必须以单一持久化批次接收全部实体。 */
    default boolean acceptBatch(List<AbstractEntry> saves, List<AbstractEntry> deletes) {
        for (AbstractEntry entry : saves) if (!acceptEntitySnapshot(entry)) return false;
        for (AbstractEntry entry : deletes) if (!acceptEntityDeletion(entry)) return false;
        return true;
    }
}
