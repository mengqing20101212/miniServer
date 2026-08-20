package ly.sceneserver.common.persistence;

import java.util.List;

import ly.LoggerDef;
import ly.sceneserver.common.SceneObject;
import ly.sceneserver.common.SceneObjectType;
import ly.sceneserver.common.SceneDataTag;
import ly.sceneserver.common.ScenePoint;
import ly.sceneserver.common.SceneRuntime;
import ly.sceneserver.common.march.SceneMarchState;
import ly.sceneserver.common.march.SceneRallyState;

/**
 * SceneServer 启动恢复编排：静态地图 -> 玩家场景投影 -> 动态实体 -> 启动 Tick。
 *
 * <p>所有数据库记录已经由 Store 从 Entry 转换为不可变投影。本类只校验并恢复领域对象，
 * 不包含 SQL，也不允许数据库异常退化成空地图继续启动。
 */
public final class SceneRecoveryService {
    private static final int DEFAULT_PAGE_SIZE = 1_000;

    private final SceneStaticMapLoader staticMapLoader;
    private final PlayerSceneStore playerSceneStore;
    /** 可为 null，仅用于兼容还没有动态聚合仓储的独立测试启动器。生产启动必须提供。 */
    private final SceneAggregateStore sceneAggregateStore;
    private final int pageSize;

    public SceneRecoveryService(
            SceneStaticMapLoader staticMapLoader,
            PlayerSceneStore playerSceneStore) {
        this(staticMapLoader, playerSceneStore, null,
                Math.max(1, Integer.getInteger("slg.scene.restore.page-size", DEFAULT_PAGE_SIZE)));
    }

    /** 生产启动入口：同时恢复玩家实体、普通动态对象、行军和集结实体。 */
    public SceneRecoveryService(
            SceneStaticMapLoader staticMapLoader,
            PlayerSceneStore playerSceneStore,
            SceneAggregateStore sceneAggregateStore) {
        this(staticMapLoader, playerSceneStore, sceneAggregateStore,
                Math.max(1, Integer.getInteger("slg.scene.restore.page-size", DEFAULT_PAGE_SIZE)));
    }

    SceneRecoveryService(
            SceneStaticMapLoader staticMapLoader,
            PlayerSceneStore playerSceneStore,
            int pageSize) {
        this(staticMapLoader, playerSceneStore, null, pageSize);
    }

    SceneRecoveryService(
            SceneStaticMapLoader staticMapLoader,
            PlayerSceneStore playerSceneStore,
            SceneAggregateStore sceneAggregateStore,
            int pageSize) {
        if (staticMapLoader == null || playerSceneStore == null || pageSize <= 0) {
            throw new IllegalArgumentException("invalid scene recovery parameters");
        }
        this.staticMapLoader = staticMapLoader;
        this.playerSceneStore = playerSceneStore;
        this.sceneAggregateStore = sceneAggregateStore;
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
        long sceneObjects = 0L;
        long marches = 0L;
        long rallies = 0L;
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

            if (sceneAggregateStore != null) {
                long[] restored = restoreSceneAggregates(scene);
                sceneObjects += restored[0];
                marches += restored[1];
                rallies += restored[2];
                LoggerDef.SystemLogger.info(
                        "scene aggregate entities restored, sceneId={}, objects={}, marches={}, rallies={}, totalObjects={}",
                        sceneId, restored[0], restored[1], restored[2], scene.totalObjectCount());
            }
        }
        return new SceneRecoveryReport(
                sceneIds.size(),
                staticCells,
                players,
                cities,
                fogBlocks,
                sceneObjects,
                marches,
                rallies,
                System.currentTimeMillis() - startedAt);
    }

    /** 分表按主键游标恢复，避免一次性把万人同图的全部动态状态装进临时 List。 */
    private long[] restoreSceneAggregates(SceneRuntime.SceneInstance scene) {
        long objectCount = restoreObjects(scene);
        long marchCount = restoreMarches(scene);
        long rallyCount = restoreRallies(scene);
        return new long[] {objectCount, marchCount, rallyCount};
    }

    private long restoreObjects(SceneRuntime.SceneInstance scene) {
        long count = 0L;
        long afterObjectId = 0L;
        while (true) {
            List<SceneObjectProjection> page = sceneAggregateStore.loadActiveObjectPage(
                    scene.config().sceneId(), afterObjectId, pageSize);
            if (page.isEmpty()) {
                return count;
            }
            for (SceneObjectProjection projection : page) {
                validateAggregate(scene, projection, afterObjectId, projection.point());
                if (projection.deleted() || !projection.objectType().usesSceneObjectEntry()) {
                    throw new IllegalStateException(
                            "invalid active scene object entity: " + projection.objectId());
                }
                scene.seedObject(SceneObject.restore(
                        projection.objectId(),
                        projection.objectType(),
                        projection.ownerId(),
                        projection.point().x(),
                        projection.point().y(),
                        projection.state(),
                        projection.stateVersion(),
                        projection.dataTagMask()));
                afterObjectId = projection.objectId();
                count++;
            }
            if (page.size() < pageSize) {
                return count;
            }
        }
    }

    private long restoreMarches(SceneRuntime.SceneInstance scene) {
        long count = 0L;
        long afterMarchId = 0L;
        while (true) {
            List<SceneMarchProjection> page = sceneAggregateStore.loadActiveMarchPage(
                    scene.config().sceneId(), afterMarchId, pageSize);
            if (page.isEmpty()) {
                return count;
            }
            for (SceneMarchProjection projection : page) {
                validateAggregate(scene, projection, afterMarchId, projection.currentPoint());
                if (projection.deleted()) {
                    throw new IllegalStateException(
                            "deleted march returned by active query: " + projection.aggregateId());
                }
                int objectVersion = checkedObjectVersion(projection);
                SceneMarchState state = SceneMarchState.restore(projection.snapshot());
                scene.seedObject(SceneObject.restore(
                        projection.snapshot().marchId(),
                        SceneObjectType.MARCH,
                        projection.snapshot().ownerPlayerId(),
                        projection.currentPoint().x(),
                        projection.currentPoint().y(),
                        state,
                        objectVersion,
                        SceneDataTag.defaultMask(SceneObjectType.MARCH)));
                afterMarchId = projection.snapshot().marchId();
                count++;
            }
            if (page.size() < pageSize) {
                return count;
            }
        }
    }

    private long restoreRallies(SceneRuntime.SceneInstance scene) {
        long count = 0L;
        long afterRallyId = 0L;
        while (true) {
            List<SceneRallyProjection> page = sceneAggregateStore.loadActiveRallyPage(
                    scene.config().sceneId(), afterRallyId, pageSize);
            if (page.isEmpty()) {
                return count;
            }
            for (SceneRallyProjection projection : page) {
                validateAggregate(scene, projection, afterRallyId, projection.currentPoint());
                if (projection.deleted()) {
                    throw new IllegalStateException(
                            "deleted rally returned by active query: " + projection.aggregateId());
                }
                int objectVersion = checkedObjectVersion(projection);
                SceneRallyState state = SceneRallyState.restore(projection.snapshot());
                scene.seedObject(SceneObject.restore(
                        projection.snapshot().rallyId(),
                        SceneObjectType.RALLY,
                        projection.snapshot().allianceId(),
                        projection.currentPoint().x(),
                        projection.currentPoint().y(),
                        state,
                        objectVersion,
                        SceneDataTag.defaultMask(SceneObjectType.RALLY)));
                afterRallyId = projection.snapshot().rallyId();
                count++;
            }
            if (page.size() < pageSize) {
                return count;
            }
        }
    }

    private static void validateAggregate(
            SceneRuntime.SceneInstance scene,
            SceneAggregateProjection projection,
            long previousId,
            ScenePoint point) {
        if (!scene.config().sceneId().equals(projection.sceneId())) {
            throw new IllegalStateException(
                    "scene aggregate belongs to another scene: " + projection.sceneId());
        }
        if (projection.aggregateId() <= previousId) {
            throw new IllegalStateException("scene aggregate page is not strictly ordered");
        }
        if (point.x() < 0 || point.x() >= scene.config().width()
                || point.y() < 0 || point.y() >= scene.config().height()) {
            throw new IllegalStateException(
                    "scene aggregate is outside scene, aggregateId=" + projection.aggregateId()
                            + ", point=" + point);
        }
    }

    /** 当前通用 SceneObject 版本是 int；恢复时显式拒绝溢出，而不是截断成旧 revision。 */
    private static int checkedObjectVersion(SceneAggregateProjection projection) {
        if (projection.revision() <= 0L || projection.revision() > Integer.MAX_VALUE) {
            throw new IllegalStateException(
                    "scene aggregate revision is outside object version range: " + projection.revision());
        }
        return Math.toIntExact(projection.revision());
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
