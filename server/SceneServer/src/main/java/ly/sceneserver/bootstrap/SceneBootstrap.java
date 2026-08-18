package ly.sceneserver.bootstrap;

import ly.sceneserver.common.SceneConfig;
import ly.sceneserver.common.FakeSceneDataGenerator;
import ly.sceneserver.common.SceneRuntime;
import ly.sceneserver.common.persistence.FlatSceneStaticMapLoader;
import ly.sceneserver.common.persistence.MysqlPlayerSceneStore;
import ly.sceneserver.common.persistence.PlayerSceneStore;
import ly.sceneserver.common.persistence.ScenePlayerPersistenceService;
import ly.sceneserver.common.persistence.SceneRecoveryReport;
import ly.sceneserver.common.persistence.SceneRecoveryService;
import ly.sceneserver.common.persistence.SceneStaticMapLoader;

import ly.LoggerDef;

/** SceneServer 中 SLG 场景运行时的启动和关闭入口。 */
public final class SceneBootstrap {
    private static volatile SceneRuntime runtime;
    private static volatile ScenePlayerPersistenceService playerPersistenceService;

    private SceneBootstrap() {
    }

    /**
     * 按 JVM 参数创建默认世界地图。
     *
     * <p>SceneServer 默认开启；地图宽高、分片数和 tick 可通过 JVM 参数覆盖。假在线玩家数据
     * 默认关闭，容量验证时使用 {@code -Dslg.scene.fake-data=true}。
     */
    public static synchronized SceneRuntime start() {
        if (runtime != null) {
            return runtime;
        }
        if (!Boolean.parseBoolean(System.getProperty("slg.scene.enabled", "true"))) {
            LoggerDef.SystemLogger.info("SLG SceneRuntime is disabled");
            return null;
        }

        String localSceneId = System.getProperty("slg.scene.local-id", "world-1");
        String crossSceneId = System.getProperty("slg.scene.cross-id", "cross-1");
        int width = intProperty("slg.scene.width", 1_000);
        int height = intProperty("slg.scene.height", 1_000);
        int shardCount = intProperty("slg.scene.shards", 4);
        int regionSize = intProperty("slg.scene.region-size", 32);
        int tickMillis = intProperty("slg.scene.tick-millis", 100);

        boolean fakeDataEnabled = Boolean.parseBoolean(System.getProperty("slg.scene.fake-data", "false"));
        SceneRuntime created = new SceneRuntime();
        ScenePlayerPersistenceService createdPersistence = null;
        try {
            created.addScene(new SceneConfig(localSceneId, width, height, shardCount, regionSize, tickMillis));
            created.addScene(new SceneConfig(crossSceneId, width, height, shardCount, regionSize, tickMillis));

            PlayerSceneStore playerSceneStore = new MysqlPlayerSceneStore();
            SceneStaticMapLoader mapLoader = fakeDataEnabled
                    ? (config, map) -> FakeSceneDataGenerator.fillStaticMap(map)
                    : new FlatSceneStaticMapLoader();
            SceneRecoveryReport recovery = new SceneRecoveryService(mapLoader, playerSceneStore).restore(created);
            LoggerDef.SystemLogger.info(
                    "SceneServer recovery completed, scenes={}, staticCells={}, players={}, cities={}, fogBlocks={}, cost={}ms",
                    recovery.sceneCount(), recovery.staticCellCount(), recovery.restoredPlayerCount(),
                    recovery.restoredCityCount(), recovery.restoredFogBlockCount(), recovery.costMillis());

            createdPersistence = new ScenePlayerPersistenceService(playerSceneStore);
            if (fakeDataEnabled) {
                int targetOnline = intProperty("slg.scene.fake-online", 10_000);
                int worldObjects = FakeSceneDataGenerator.seedWorldObjects(created.scene(localSceneId));
                int seeded = FakeSceneDataGenerator.seedPlayers(created.scene(localSceneId), targetOnline);
                LoggerDef.SystemLogger.info(
                        "fake scene data seeded, sceneId={}, onlinePlayers={}, worldObjects={}",
                        localSceneId, seeded, worldObjects);
            }
            created.start();
            playerPersistenceService = createdPersistence;
            runtime = created;
            return created;
        } catch (RuntimeException | Error error) {
            if (createdPersistence != null) {
                createdPersistence.close();
            }
            created.close();
            throw error;
        }
    }

    public static SceneRuntime getRuntime() {
        return runtime;
    }

    /** 玩家场景投影异步入库入口；Future 成功后才能清理对应内存脏状态。 */
    public static ScenePlayerPersistenceService getPlayerPersistenceService() {
        ScenePlayerPersistenceService service = playerPersistenceService;
        if (service == null) {
            throw new IllegalStateException("Scene player persistence is not ready");
        }
        return service;
    }

    public static synchronized void stop() {
        SceneRuntime current = runtime;
        ScenePlayerPersistenceService persistence = playerPersistenceService;
        runtime = null;
        playerPersistenceService = null;
        if (persistence != null) {
            persistence.close();
        }
        if (current != null) {
            current.close();
        }
    }

    private static int intProperty(String name, int defaultValue) {
        String value = System.getProperty(name);
        return value == null || value.isBlank() ? defaultValue : Integer.parseInt(value);
    }
}
