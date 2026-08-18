package ly.sceneserver.common.persistence;

import java.util.List;

import ly.LoggerDef;
import ly.sceneserver.common.SceneObject;
import ly.sceneserver.common.SceneObjectType;
import ly.sceneserver.common.SceneRuntime;

/**
 * SceneServer 启动恢复编排：静态地图 -> 玩家场景投影 -> 主城对象与个人迷雾 -> 启动 Tick。
 */
public final class SceneRecoveryService {
    private static final int DEFAULT_PAGE_SIZE = 1_000;

    private final SceneStaticMapLoader staticMapLoader;
    private final PlayerSceneStore playerSceneStore;
    private final int pageSize;

    public SceneRecoveryService(
            SceneStaticMapLoader staticMapLoader,
            PlayerSceneStore playerSceneStore) {
        this(staticMapLoader, playerSceneStore,
                Math.max(1, Integer.getInteger("slg.scene.restore.page-size", DEFAULT_PAGE_SIZE)));
    }

    SceneRecoveryService(
            SceneStaticMapLoader staticMapLoader,
            PlayerSceneStore playerSceneStore,
            int pageSize) {
        if (staticMapLoader == null || playerSceneStore == null || pageSize <= 0) {
            throw new IllegalArgumentException("invalid scene recovery parameters");
        }
        this.staticMapLoader = staticMapLoader;
        this.playerSceneStore = playerSceneStore;
        this.pageSize = pageSize;
    }

    /** 数据库异常或坏坐标直接中断启动，禁止把“加载失败”误当成“空地图”。 */
    public SceneRecoveryReport restore(SceneRuntime runtime) {
        if (runtime == null || runtime.isStarted()) {
            throw new IllegalArgumentException("SceneRuntime must exist and not be started");
        }
        long startedAt = System.currentTimeMillis();
        long staticCells = 0L;
        long players = 0L;
        long cities = 0L;
        long fogBlocks = 0L;
        List<String> sceneIds = runtime.registeredSceneIds();
        for (String sceneId : sceneIds) {
            SceneRuntime.SceneInstance scene = runtime.scene(sceneId);
            long mapStartedAt = System.currentTimeMillis();
            staticMapLoader.load(scene.config(), scene.staticMap());
            staticCells += scene.staticMap().cellCount();
            LoggerDef.SystemLogger.info(
                    "scene static map initialized, sceneId={}, width={}, height={}, cells={}, cost={}ms",
                    sceneId, scene.config().width(), scene.config().height(),
                    scene.staticMap().cellCount(), System.currentTimeMillis() - mapStartedAt);

            long afterPlayerId = 0L;
            while (true) {
                List<PlayerSceneProjection> page = playerSceneStore.loadActivePage(
                        sceneId, afterPlayerId, pageSize);
                if (page.isEmpty()) {
                    break;
                }
                for (PlayerSceneProjection projection : page) {
                    validateProjection(scene, projection, afterPlayerId);
                    scene.seedObject(new SceneObject(
                            projection.cityObjectId(),
                            SceneObjectType.BUILDING,
                            projection.playerId(),
                            projection.cityPoint().x(),
                            projection.cityPoint().y(),
                            projection));
                    scene.restoreDiscoveredBlocksBeforeStart(
                            projection.playerId(), projection.discoveredBlocks());
                    players++;
                    cities++;
                    fogBlocks += projection.discoveredBlocks().cardinality();
                    afterPlayerId = projection.playerId();
                }
                if (page.size() < pageSize) {
                    break;
                }
            }
            LoggerDef.SystemLogger.info(
                    "scene player projection restored, sceneId={}, lastPlayerId={}, totalObjects={}",
                    sceneId, afterPlayerId, scene.totalObjectCount());
        }
        return new SceneRecoveryReport(
                sceneIds.size(),
                staticCells,
                players,
                cities,
                fogBlocks,
                System.currentTimeMillis() - startedAt);
    }

    private static void validateProjection(
            SceneRuntime.SceneInstance scene,
            PlayerSceneProjection projection,
            long previousPlayerId) {
        if (!scene.config().sceneId().equals(projection.sceneId())) {
            throw new IllegalStateException("player projection belongs to another scene: " + projection.sceneId());
        }
        if (projection.playerId() <= previousPlayerId) {
            throw new IllegalStateException("player projection page is not strictly ordered");
        }
        if (projection.cityPoint().x() < 0
                || projection.cityPoint().x() >= scene.config().width()
                || projection.cityPoint().y() < 0
                || projection.cityPoint().y() >= scene.config().height()) {
            throw new IllegalStateException(
                    "player city is outside scene, playerId=" + projection.playerId()
                            + ", point=" + projection.cityPoint());
        }
    }
}
