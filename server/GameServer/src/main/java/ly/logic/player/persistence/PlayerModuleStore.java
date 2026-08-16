package ly.logic.player.persistence;

import java.util.List;
import java.util.Map;
import ly.db.entry.PlayerModuleEntry;

/** 玩家模块数据库访问边界，便于事务实现和无数据库单元测试。 */
public interface PlayerModuleStore {
    Map<Integer, PlayerModuleEntry> load(long playerId);

    boolean saveBatch(long playerId, List<PlayerModuleEntry> modules);
}
