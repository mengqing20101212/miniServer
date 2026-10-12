package ly.db;

import static org.junit.Assert.*;

import ly.db.entry.PlayerEntry;
import ly.logic.login.PlayerLogicModule;
import ly.logic.player.ModuleEnum;
import ly.logic.player.Player;
import ly.logic.player.PlayerData;
import ly.logic.player.PlayerDataTestFactory;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/** 验证 Blob 元数据 setter 不会绕过聚合边界产生额外 RocksDB 写入。 */
public class PlayerHybridRocksPersistenceTest {
    @Rule public TemporaryFolder temporary = new TemporaryFolder();

    @Test
    public void acceptsOneBlobSnapshotPerBoundaryIncludingOutsideScope() throws Exception {
        MysqlService mysqlService = MysqlService.getInstance();
        Field rocksBinding = MysqlService.class.getDeclaredField("rocksService");
        Field idBinding = MysqlService.class.getDeclaredField("idGenerator");
        rocksBinding.setAccessible(true);
        idBinding.setAccessible(true);
        Object previousRocks = rocksBinding.get(mysqlService);
        Object previousIds = idBinding.get(mysqlService);
        List<RocksService.Record> committedRecords = new ArrayList<>();
        try (GlobalIdGenerator ids =
                        new GlobalIdGenerator(124, temporary.newFolder().toPath().resolve("ids"));
                RocksService rocks =
                        new RocksService(
                                temporary.newFolder().toPath(),
                                (owner, records) -> committedRecords.addAll(records),
                                1000,
                                false)) {
            rocksBinding.set(mysqlService, rocks);
            idBinding.set(mysqlService, ids);
            try {
                PlayerDataTestFactory.MemoryStore persistenceStore =
                        new PlayerDataTestFactory.MemoryStore() {
                            @Override
                            public long generateEntityId() {
                                return mysqlService.generateEntityId();
                            }

                            @Override
                            public boolean acceptEntitySnapshot(AbstractEntry entry) {
                                return mysqlService.save(entry);
                            }
                        };
                PlayerEntry entry = new PlayerEntry();
                entry.setId(1001L);
                Player player = new Player();
                player.setPlayerData(new PlayerData(entry, persistenceStore));
                player.initAllModules();
                rocks.drainOnce();
                long initialVersion =
                        committedRecords.stream()
                                .mapToLong(record -> record.version)
                                .max()
                                .orElseThrow();
                committedRecords.clear();
                PlayerLogicModule module =
                        (PlayerLogicModule)
                                player.getPlayerData().getModule(ModuleEnum.PLAYER_LOGIC_MODULE);
                module.setLastLoginTimer(123);
                rocks.drainOnce();
                assertEquals(1, committedRecords.size());
                assertEquals(initialVersion + 1, committedRecords.getFirst().version);
                committedRecords.clear();
                try (PersistenceScope scope = PersistenceScope.open()) {
                    module.setLastLoginTimer(124);
                    module.setLastLoginTimer(125);
                    assertEquals(0, rocks.pendingCount());
                }
                rocks.drainOnce();
                assertEquals(1, committedRecords.size());
                assertEquals(initialVersion + 2, committedRecords.getFirst().version);
            } finally {
                rocksBinding.set(mysqlService, previousRocks);
                idBinding.set(mysqlService, previousIds);
            }
        }
    }
}
