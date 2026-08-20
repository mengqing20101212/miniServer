package ly.bot.module.impl;

import java.util.EnumSet;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import ly.proto.Cmd;
import ly.proto.ErrorMsg;
import ly.proto.Scene;

/**
 * SceneServer 真实 TCP/RPC 功能回归测试。
 *
 * <p>虽然保留了历史类名和 {@code --test-scene-rpc} 命令以兼容已有脚本，但现在不再只是
 * 查询一次指标的 smoke test。它会覆盖本服和跨服路由、玩家生命周期、地图查询、AOI、
 * 分层数据、个人迷雾、异步 A*、跨分片拒绝语义、错误码和断线重连。
 */
public final class SceneRpcSmokeTestModule {
    /** 当前 SceneServer 标准 AOI/Region 边长；启动基线改变时测试应同步失败并提示。 */
    private static final int REGION_SIZE = 32;
    private static final int STANDARD_WIDTH = 1_000;
    private static final int STANDARD_HEIGHT = 1_000;
    private static final int STANDARD_ONLINE_TARGET = 10_000;
    private static final AtomicLong REQUEST_IDS = new AtomicLong(System.currentTimeMillis());
    private static final AtomicLong PLAYER_IDS = new AtomicLong(
            7_000_000_000L + Math.floorMod(System.currentTimeMillis(), 100_000_000L) * 10L);

    private SceneRpcSmokeTestModule() {
    }

    /** 使用普通运行服执行回归；假地图专属断言自动跳过。 */
    public static boolean runStandalone(String sceneHost, int scenePort) {
        return runStandalone(sceneHost, scenePort, false);
    }

    /**
     * 执行完整功能回归。
     *
     * @param requireFakeData true 时额外要求 SceneServer 使用 -Dslg.scene.fake-data=true 启动，
     *                        并断言 1 万假玩家、资源/怪物/农田/掉落物、行军和集结快照
     */
    public static boolean runStandalone(String sceneHost, int scenePort, boolean requireFakeData) {
        long localPlayer = nextPlayerId();
        long localObserver = nextPlayerId();
        long crossPlayer = nextPlayerId();
        SceneRpcTestClient client = null;
        try {
            client = SceneRpcTestClient.connect(sceneHost, scenePort);
            SceneRpcTestClient initialClient = client;
            System.out.printf("[SCENE-RPC] connected sid=%d%n", initialClient.sid());

            Scene.scSceneMetrics localBaseline = runCase("本服容量指标", () ->
                    queryMetrics(initialClient, SceneSpec.LOCAL));
            runCase("跨服容量指标", () -> queryMetrics(initialClient, SceneSpec.CROSS));
            runCase("参数与边界错误码", () -> testValidationErrors(initialClient));

            // 本服链路保留 localPlayer 不退出，下一段会主动断开 TCP 并验证场景状态不依赖连接对象。
            runCase("本服玩家、AOI、迷雾、寻路与移动", () ->
                    testLocalLifecycle(
                            initialClient, localBaseline, localPlayer, localObserver, requireFakeData));

            initialClient.close();
            client = SceneRpcTestClient.connect(sceneHost, scenePort);
            SceneRpcTestClient reconnected = client;
            runCase("断线重连后恢复查询与订阅", () ->
                    testReconnectAndCleanup(reconnected, localBaseline, localPlayer));

            runCase("跨服场景完整基础链路", () ->
                    testCrossLifecycle(reconnected, crossPlayer));

            System.out.println("[SCENE-RPC] ALL FUNCTIONAL CASES PASS");
            return true;
        } catch (Throwable error) {
            System.err.println("[SCENE-RPC] FAIL " + error.getMessage());
            error.printStackTrace();
            bestEffortCleanup(sceneHost, scenePort, localPlayer, localObserver, crossPlayer);
            return false;
        } finally {
            if (client != null) {
                client.close();
            }
        }
    }

    private static Scene.scSceneMetrics queryMetrics(SceneRpcTestClient client, SceneSpec scene)
            throws InterruptedException {
        Scene.scSceneMetrics metrics = client.exchange(
                1L,
                Cmd.CMD.CS_SceneMetrics,
                Scene.csSceneMetrics.newBuilder()
                        .setSceneId(scene.sceneId)
                        .setScope(scene.scope)
                        .build(),
                Cmd.CMD.SC_SceneMetrics,
                Scene.scSceneMetrics.class);
        assertResult(metrics.getResult(), ErrorMsg.ErrorCode.Ok, scene.label + "指标");
        assertEquals(scene.sceneId, metrics.getSceneId(), scene.label + "场景 ID");
        assertEquals(STANDARD_WIDTH, metrics.getWidth(), scene.label + "地图宽度");
        assertEquals(STANDARD_HEIGHT, metrics.getHeight(), scene.label + "地图高度");
        assertTrue(metrics.getShardCount() > 0, scene.label + " SceneShard 数量必须大于 0");
        assertEquals(STANDARD_ONLINE_TARGET, metrics.getOnlineTarget(), scene.label + "万人在线基线");
        assertTrue(metrics.getTickNumber() > 0, scene.label + " Tick 尚未启动");
        assertTrue(metrics.getLastTickMillis() > 0, scene.label + "最近 Tick 时间无效");
        System.out.printf(
                "[SCENE-RPC] %s metrics size=%dx%d shards=%d tick=%d objects=%d onlineTarget=%d%n",
                scene.label, metrics.getWidth(), metrics.getHeight(), metrics.getShardCount(),
                metrics.getTickNumber(), metrics.getObjectCount(), metrics.getOnlineTarget());
        return metrics;
    }

    private static void testValidationErrors(SceneRpcTestClient client) throws InterruptedException {
        Scene.scSceneQuery query = client.exchange(
                1L,
                Cmd.CMD.CS_SceneQuery,
                Scene.csSceneQuery.newBuilder()
                        .setSceneId(SceneSpec.LOCAL.sceneId)
                        .setScope(SceneSpec.LOCAL.scope)
                        .setPoint(point(-1, 0))
                        .build(),
                Cmd.CMD.SC_SceneQuery,
                Scene.scSceneQuery.class);
        assertResult(query.getResult(), ErrorMsg.ErrorCode.SCENE_OUT_OF_BOUNDS, "越界地图查询");

        long requestId = nextRequestId();
        Scene.scSceneEnter enter = client.exchange(
                1L,
                Cmd.CMD.CS_SceneEnter,
                Scene.csSceneEnter.newBuilder()
                        .setSceneId(SceneSpec.LOCAL.sceneId)
                        .setScope(SceneSpec.LOCAL.scope)
                        .setPlayerId(0)
                        .setPoint(point(64, 64))
                        .setRequestId(requestId)
                        .build(),
                Cmd.CMD.SC_SceneEnter,
                Scene.scSceneEnter.class);
        assertResult(enter.getResult(), ErrorMsg.ErrorCode.PARAM_ERROR, "非法玩家进入");
        assertEquals(requestId, enter.getRequestId(), "进入失败 requestId");

        requestId = nextRequestId();
        Scene.scSceneView view = client.exchange(
                1L,
                Cmd.CMD.CS_SceneView,
                Scene.csSceneView.newBuilder()
                        .setSceneId(SceneSpec.LOCAL.sceneId)
                        .setScope(SceneSpec.LOCAL.scope)
                        .setPlayerId(1)
                        .setCenterPoint(point(64, 64))
                        .setRadiusBlocks(-1)
                        .setViewLevel(Scene.SceneViewLevel.SCENE_VIEW_DETAIL)
                        .setRequestId(requestId)
                        .build(),
                Cmd.CMD.SC_SceneView,
                Scene.scSceneView.class);
        assertResult(view.getResult(), ErrorMsg.ErrorCode.PARAM_ERROR, "非法 AOI 半径");
        assertEquals(requestId, view.getRequestId(), "AOI 失败 requestId");

        requestId = nextRequestId();
        Scene.scScenePathFind path = client.exchange(
                1L,
                Cmd.CMD.CS_ScenePathFind,
                Scene.csScenePathFind.newBuilder()
                        .setSceneId(SceneSpec.LOCAL.sceneId)
                        .setScope(SceneSpec.LOCAL.scope)
                        .setPlayerId(1)
                        .setStartPoint(point(64, 64))
                        .setTargetPoint(point(65, 64))
                        .setFogPolicy(Scene.SceneFogPolicy.SCENE_FOG_IGNORE)
                        .setMaxVisitedNodes(-1)
                        .setRequestId(requestId)
                        .build(),
                Cmd.CMD.SC_ScenePathFind,
                Scene.scScenePathFind.class);
        assertResult(path.getResult(), ErrorMsg.ErrorCode.PARAM_ERROR, "非法寻路节点上限");
        assertEquals(requestId, path.getRequestId(), "寻路失败 requestId");
    }

    private static void testLocalLifecycle(
            SceneRpcTestClient client,
            Scene.scSceneMetrics baseline,
            long playerId,
            long observerId,
            boolean requireFakeData) throws InterruptedException {
        Scene.ScenePoint origin = point(64, 64);
        Scene.ScenePoint moved = point(65, 64);
        Scene.ScenePoint shiftedCamera = point(128, 64);
        Scene.ScenePoint observerPoint = point(800, 800);

        Scene.scSceneEnter entered = enter(client, SceneSpec.LOCAL, playerId, origin);
        assertResult(entered.getResult(), ErrorMsg.ErrorCode.Ok, "本服玩家进入");
        assertPoint(origin, entered.getCurrentPoint(), "本服进入坐标");

        Scene.scSceneQuery originQuery = query(client, SceneSpec.LOCAL, origin);
        assertResult(originQuery.getResult(), ErrorMsg.ErrorCode.Ok, "进入后地图查询");
        assertObject(originQuery.getObjectsList(), playerId, Scene.SceneObjectKind.SCENE_OBJECT_PLAYER, true,
                "进入后玩家对象");

        Scene.scSceneView detail = view(
                client, SceneSpec.LOCAL, playerId, origin, 1,
                Scene.SceneViewLevel.SCENE_VIEW_DETAIL, 0L);
        assertResult(detail.getResult(), ErrorMsg.ErrorCode.Ok, "九宫格 DETAIL AOI");
        assertPoint(point(2, 2), detail.getCenterBlock(), "DETAIL 中心块");
        assertEquals(9, detail.getBlocksCount(), "标准九宫格块数");
        assertObject(detail.getObjectsList(), playerId, Scene.SceneObjectKind.SCENE_OBJECT_PLAYER, true,
                "DETAIL 玩家对象");
        for (Scene.SceneBlockSnapshot block : detail.getBlocksList()) {
            assertTrue(block.getVisible(), "九宫格块必须处于当前可见集合: " + block.getBlockIndex());
            assertTrue(block.getDiscovered(), "九宫格块必须进入玩家探索集合: " + block.getBlockIndex());
            assertTrue(detail.getDiscoveredBlockIndicesList().contains(block.getBlockIndex()),
                    "探索块列表缺少当前块: " + block.getBlockIndex());
        }

        Scene.scSceneView world = view(
                client, SceneSpec.LOCAL, playerId, origin, 1,
                Scene.SceneViewLevel.SCENE_VIEW_WORLD, 0L);
        assertResult(world.getResult(), ErrorMsg.ErrorCode.Ok, "WORLD AOI");
        assertObject(world.getObjectsList(), playerId, Scene.SceneObjectKind.SCENE_OBJECT_PLAYER, false,
                "WORLD 层不应下发普通玩家明细");

        Scene.scScenePathFind ignoreFog = path(
                client, SceneSpec.LOCAL, playerId, origin, point(68, 64),
                Scene.SceneFogPolicy.SCENE_FOG_IGNORE, 10_000);
        assertSuccessfulPath(ignoreFog, origin, point(68, 64), "忽略迷雾异步 A*");

        Scene.scScenePathFind discoveredPath = path(
                client, SceneSpec.LOCAL, playerId, origin, point(68, 64),
                Scene.SceneFogPolicy.SCENE_FOG_DISCOVERED_ONLY, 10_000);
        assertSuccessfulPath(discoveredPath, origin, point(68, 64), "已探索区域异步 A*");

        Scene.scSceneView shifted = view(
                client, SceneSpec.LOCAL, playerId, shiftedCamera, 1,
                Scene.SceneViewLevel.SCENE_VIEW_DETAIL, 0L);
        assertResult(shifted.getResult(), ErrorMsg.ErrorCode.Ok, "移动相机 AOI");
        assertPoint(point(4, 2), shifted.getCenterBlock(), "移动后的中心块");
        int oldCenterBlock = blockIndex(2, 2, STANDARD_WIDTH);
        int newCenterBlock = blockIndex(4, 2, STANDARD_WIDTH);
        assertTrue(shifted.getDiscoveredBlockIndicesList().contains(oldCenterBlock),
                "AOI 移动后历史探索块丢失");
        assertTrue(shifted.getDiscoveredBlockIndicesList().contains(newCenterBlock),
                "AOI 移动后新探索块未记录");

        Scene.scScenePathFind noLongerVisible = path(
                client, SceneSpec.LOCAL, playerId, origin, point(68, 64),
                Scene.SceneFogPolicy.SCENE_FOG_VISIBLE_ONLY, 10_000);
        assertResult(noLongerVisible.getResult(), ErrorMsg.ErrorCode.SCENE_FOG_BLOCKED,
                "相机移开后的当前可见寻路");

        Scene.scScenePathFind outsideFog = path(
                client, SceneSpec.LOCAL, playerId, origin, point(936, 64),
                Scene.SceneFogPolicy.SCENE_FOG_DISCOVERED_ONLY, 10_000);
        assertResult(outsideFog.getResult(), ErrorMsg.ErrorCode.SCENE_FOG_BLOCKED,
                "未探索区域寻路");

        Scene.scSceneEnter observerEntered = enter(client, SceneSpec.LOCAL, observerId, observerPoint);
        assertResult(observerEntered.getResult(), ErrorMsg.ErrorCode.Ok, "第二玩家进入");
        Scene.scSceneView observerView = view(
                client, SceneSpec.LOCAL, observerId, observerPoint, 1,
                Scene.SceneViewLevel.SCENE_VIEW_DETAIL, 0L);
        assertResult(observerView.getResult(), ErrorMsg.ErrorCode.Ok, "第二玩家 AOI");
        assertTrue(!observerView.getDiscoveredBlockIndicesList().contains(oldCenterBlock),
                "玩家个人迷雾发生串号，第二玩家继承了第一玩家探索块");

        Scene.scSceneMetrics afterEnter = queryMetrics(client, SceneSpec.LOCAL);
        assertEquals(baseline.getObjectCount() + 2, afterEnter.getObjectCount(),
                "两个测试玩家进入后的对象数");

        Scene.scSceneMove movedResponse = move(client, SceneSpec.LOCAL, playerId, moved);
        assertResult(movedResponse.getResult(), ErrorMsg.ErrorCode.Ok, "同 SceneShard 移动");
        assertPoint(moved, movedResponse.getCurrentPoint(), "移动后的权威坐标");
        assertObject(query(client, SceneSpec.LOCAL, origin).getObjectsList(), playerId,
                Scene.SceneObjectKind.SCENE_OBJECT_PLAYER, false, "旧格玩家对象");
        assertObject(query(client, SceneSpec.LOCAL, moved).getObjectsList(), playerId,
                Scene.SceneObjectKind.SCENE_OBJECT_PLAYER, true, "新格玩家对象");

        Scene.scSceneMove crossShard = move(client, SceneSpec.LOCAL, playerId, point(936, 64));
        assertResult(crossShard.getResult(), ErrorMsg.ErrorCode.SCENE_UNSUPPORTED,
                "第一阶段跨 SceneShard 移动拒绝");
        assertPoint(moved, crossShard.getCurrentPoint(), "跨分片拒绝后的权威坐标");

        Scene.scSceneMove outOfBounds = move(client, SceneSpec.LOCAL, playerId, point(-1, 64));
        assertResult(outOfBounds.getResult(), ErrorMsg.ErrorCode.SCENE_OUT_OF_BOUNDS, "越界移动");

        if (requireFakeData) {
            testFakeWorldSnapshots(client, playerId);
        } else {
            System.out.println("[SCENE-RPC] SKIP 假世界对象/行军/集结快照（未要求 fake-data）");
        }

        Scene.scSceneLeave observerLeave = leave(client, SceneSpec.LOCAL, observerId);
        assertResult(observerLeave.getResult(), ErrorMsg.ErrorCode.Ok, "第二玩家离开");
        assertObject(query(client, SceneSpec.LOCAL, observerPoint).getObjectsList(), observerId,
                Scene.SceneObjectKind.SCENE_OBJECT_PLAYER, false, "第二玩家离开后对象清理");
        // playerId 故意保留到连接关闭后，验证 SceneServer 状态不依赖当前 TCP 连接生命周期。
    }

    private static void testFakeWorldSnapshots(SceneRpcTestClient client, long playerId)
            throws InterruptedException {
        Scene.scSceneMetrics metrics = queryMetrics(client, SceneSpec.LOCAL);
        assertEquals(STANDARD_ONLINE_TARGET, metrics.getOnlineTarget(), "假数据在线目标");
        assertTrue(metrics.getObjectCount() >= STANDARD_ONLINE_TARGET,
                "假数据没有达到 1 万在线对象基线: " + metrics.getObjectCount());

        // 1000 / 32 向上取整后每个方向都是 32 块，逻辑全图共 1024 个 AOI 块。
        // 不能为了验证这个数字把 1 万多个对象塞进一个响应：现有 MessagePacket 的 length
        // 是 2 字节有符号 short，单包上限不足 32 KiB。真实客户端也只会订阅视野附近的块。
        int blockColumns = (metrics.getWidth() + 31) / 32;
        int blockRows = (metrics.getHeight() + 31) / 32;
        assertEquals(1_024, blockColumns * blockRows, "100 万格地图的 AOI 块数");

        // 假数据生成器会在前四个 64 格刷新点依次放置资源、怪物、农田和掉落物，
        // 行军与集结位于地图起点附近。逐块查询既覆盖全部类型，也验证响应保持小包化。
        EnumSet<Scene.SceneObjectKind> worldKinds = EnumSet.noneOf(Scene.SceneObjectKind.class);
        Scene.SceneObjectSnapshot march = null;
        Scene.SceneObjectSnapshot rally = null;
        int sampledObjects = 0;
        for (int x : List.of(0, 64, 128, 192)) {
            Scene.scSceneView blockView = view(
                    client,
                    SceneSpec.LOCAL,
                    playerId,
                    point(x, 0),
                    0,
                    Scene.SceneViewLevel.SCENE_VIEW_DETAIL,
                    0L);
            assertResult(blockView.getResult(), ErrorMsg.ErrorCode.Ok, "假数据单块 AOI: x=" + x);
            assertEquals(1, blockView.getBlocksCount(), "半径为 0 时只返回中心 AOI 块");
            sampledObjects += blockView.getObjectsCount();
            for (Scene.SceneObjectSnapshot object : blockView.getObjectsList()) {
                worldKinds.add(object.getKind());
                if (object.getKind() == Scene.SceneObjectKind.SCENE_OBJECT_MARCH) {
                    march = object;
                } else if (object.getKind() == Scene.SceneObjectKind.SCENE_OBJECT_RALLY) {
                    rally = object;
                }
            }
        }
        for (Scene.SceneObjectKind kind : List.of(
                Scene.SceneObjectKind.SCENE_OBJECT_RESOURCE,
                Scene.SceneObjectKind.SCENE_OBJECT_MONSTER,
                Scene.SceneObjectKind.SCENE_OBJECT_FARM,
                Scene.SceneObjectKind.SCENE_OBJECT_DROP)) {
            assertTrue(worldKinds.contains(kind), "假世界缺少对象类型: " + kind);
        }
        assertTrue(march != null && march.hasMarch(), "行军对象没有携带 SceneMarchSnapshot");
        assertTrue(march.getMarch().getPathCount() >= 2, "行军快照缺少完整路径");
        assertTrue(march.getMarch().hasTarget(), "行军快照缺少目标标记");
        assertTrue(rally != null && rally.hasRally(), "集结对象没有携带 SceneRallySnapshot");
        assertTrue(rally.getRally().hasTarget(), "集结快照缺少目标标记");
        assertTrue(rally.getRally().getStateVersion() > 0, "集结快照状态版本无效");
        System.out.printf(
                "[SCENE-RPC] fake-data totalObjects=%d sampledObjects=%d kinds=%s%n",
                metrics.getObjectCount(), sampledObjects, worldKinds);
    }

    private static void testReconnectAndCleanup(
            SceneRpcTestClient client,
            Scene.scSceneMetrics baseline,
            long playerId) throws InterruptedException {
        Scene.ScenePoint current = point(65, 64);
        Scene.scSceneQuery query = query(client, SceneSpec.LOCAL, current);
        assertObject(query.getObjectsList(), playerId, Scene.SceneObjectKind.SCENE_OBJECT_PLAYER, true,
                "重连后玩家对象");

        Scene.scSceneView view = view(
                client, SceneSpec.LOCAL, playerId, current, 1,
                Scene.SceneViewLevel.SCENE_VIEW_DETAIL, 0L);
        assertResult(view.getResult(), ErrorMsg.ErrorCode.Ok, "重连后恢复 AOI");
        assertObject(view.getObjectsList(), playerId, Scene.SceneObjectKind.SCENE_OBJECT_PLAYER, true,
                "重连后 AOI 玩家对象");

        Scene.scSceneLeave leave = leave(client, SceneSpec.LOCAL, playerId);
        assertResult(leave.getResult(), ErrorMsg.ErrorCode.Ok, "重连后玩家离开");
        assertObject(query(client, SceneSpec.LOCAL, current).getObjectsList(), playerId,
                Scene.SceneObjectKind.SCENE_OBJECT_PLAYER, false, "重连清理后的玩家对象");

        Scene.scSceneLeave repeatedLeave = leave(client, SceneSpec.LOCAL, playerId);
        assertResult(repeatedLeave.getResult(), ErrorMsg.ErrorCode.SCENE_NOT_FOUND, "重复离开幂等结果");

        Scene.scSceneMetrics afterCleanup = queryMetrics(client, SceneSpec.LOCAL);
        assertEquals(baseline.getObjectCount(), afterCleanup.getObjectCount(), "本服测试对象清理后的对象数");
    }

    private static void testCrossLifecycle(SceneRpcTestClient client, long playerId)
            throws InterruptedException {
        Scene.ScenePoint origin = point(64, 64);
        Scene.ScenePoint moved = point(66, 64);
        Scene.scSceneMetrics baseline = queryMetrics(client, SceneSpec.CROSS);

        Scene.scSceneEnter enter = enter(client, SceneSpec.CROSS, playerId, origin);
        assertResult(enter.getResult(), ErrorMsg.ErrorCode.Ok, "跨服玩家进入");
        assertObject(query(client, SceneSpec.CROSS, origin).getObjectsList(), playerId,
                Scene.SceneObjectKind.SCENE_OBJECT_PLAYER, true, "跨服查询玩家对象");

        Scene.scSceneView view = view(
                client, SceneSpec.CROSS, playerId, origin, 1,
                Scene.SceneViewLevel.SCENE_VIEW_DETAIL, 0L);
        assertResult(view.getResult(), ErrorMsg.ErrorCode.Ok, "跨服九宫格 AOI");
        assertEquals(9, view.getBlocksCount(), "跨服九宫格块数");

        Scene.scScenePathFind path = path(
                client, SceneSpec.CROSS, playerId, origin, moved,
                Scene.SceneFogPolicy.SCENE_FOG_DISCOVERED_ONLY, 10_000);
        assertSuccessfulPath(path, origin, moved, "跨服异步 A*");

        Scene.scSceneMove move = move(client, SceneSpec.CROSS, playerId, moved);
        assertResult(move.getResult(), ErrorMsg.ErrorCode.Ok, "跨服同分片移动");
        assertPoint(moved, move.getCurrentPoint(), "跨服移动坐标");

        assertResult(leave(client, SceneSpec.CROSS, playerId).getResult(), ErrorMsg.ErrorCode.Ok,
                "跨服玩家离开");
        Scene.scSceneMetrics after = queryMetrics(client, SceneSpec.CROSS);
        assertEquals(baseline.getObjectCount(), after.getObjectCount(), "跨服测试对象清理后的对象数");
    }

    private static Scene.scSceneEnter enter(
            SceneRpcTestClient client, SceneSpec scene, long playerId, Scene.ScenePoint point)
            throws InterruptedException {
        long requestId = nextRequestId();
        Scene.scSceneEnter response = client.exchange(
                playerId,
                Cmd.CMD.CS_SceneEnter,
                Scene.csSceneEnter.newBuilder()
                        .setSceneId(scene.sceneId)
                        .setScope(scene.scope)
                        .setPlayerId(playerId)
                        .setPoint(point)
                        .setRequestId(requestId)
                        .build(),
                Cmd.CMD.SC_SceneEnter,
                Scene.scSceneEnter.class);
        assertEquals(requestId, response.getRequestId(), scene.label + "进入 requestId");
        return response;
    }

    private static Scene.scSceneQuery query(
            SceneRpcTestClient client, SceneSpec scene, Scene.ScenePoint point)
            throws InterruptedException {
        return client.exchange(
                1L,
                Cmd.CMD.CS_SceneQuery,
                Scene.csSceneQuery.newBuilder()
                        .setSceneId(scene.sceneId)
                        .setScope(scene.scope)
                        .setPoint(point)
                        .setRadius(0)
                        .build(),
                Cmd.CMD.SC_SceneQuery,
                Scene.scSceneQuery.class);
    }

    private static Scene.scSceneMove move(
            SceneRpcTestClient client, SceneSpec scene, long playerId, Scene.ScenePoint target)
            throws InterruptedException {
        long requestId = nextRequestId();
        Scene.scSceneMove response = client.exchange(
                playerId,
                Cmd.CMD.CS_SceneMove,
                Scene.csSceneMove.newBuilder()
                        .setSceneId(scene.sceneId)
                        .setScope(scene.scope)
                        .setPlayerId(playerId)
                        .setTargetPoint(target)
                        .setRequestId(requestId)
                        .build(),
                Cmd.CMD.SC_SceneMove,
                Scene.scSceneMove.class);
        assertEquals(requestId, response.getRequestId(), scene.label + "移动 requestId");
        return response;
    }

    private static Scene.scSceneView view(
            SceneRpcTestClient client,
            SceneSpec scene,
            long playerId,
            Scene.ScenePoint center,
            int radiusBlocks,
            Scene.SceneViewLevel viewLevel,
            long requestedTagMask) throws InterruptedException {
        long requestId = nextRequestId();
        Scene.scSceneView response = client.exchange(
                playerId,
                Cmd.CMD.CS_SceneView,
                Scene.csSceneView.newBuilder()
                        .setSceneId(scene.sceneId)
                        .setScope(scene.scope)
                        .setPlayerId(playerId)
                        .setCenterPoint(center)
                        .setRadiusBlocks(radiusBlocks)
                        .setViewLevel(viewLevel)
                        .setRequestedTagMask(requestedTagMask)
                        .setRequestId(requestId)
                        .build(),
                Cmd.CMD.SC_SceneView,
                Scene.scSceneView.class);
        assertEquals(requestId, response.getRequestId(), scene.label + " AOI requestId");
        return response;
    }

    private static Scene.scScenePathFind path(
            SceneRpcTestClient client,
            SceneSpec scene,
            long playerId,
            Scene.ScenePoint start,
            Scene.ScenePoint target,
            Scene.SceneFogPolicy fogPolicy,
            int maxVisitedNodes) throws InterruptedException {
        long requestId = nextRequestId();
        Scene.scScenePathFind response = client.exchange(
                playerId,
                Cmd.CMD.CS_ScenePathFind,
                Scene.csScenePathFind.newBuilder()
                        .setSceneId(scene.sceneId)
                        .setScope(scene.scope)
                        .setPlayerId(playerId)
                        .setStartPoint(start)
                        .setTargetPoint(target)
                        .setFogPolicy(fogPolicy)
                        .setMaxVisitedNodes(maxVisitedNodes)
                        .setRequestId(requestId)
                        .build(),
                Cmd.CMD.SC_ScenePathFind,
                Scene.scScenePathFind.class);
        assertEquals(requestId, response.getRequestId(), scene.label + "寻路 requestId");
        return response;
    }

    private static Scene.scSceneLeave leave(
            SceneRpcTestClient client, SceneSpec scene, long playerId) throws InterruptedException {
        long requestId = nextRequestId();
        Scene.scSceneLeave response = client.exchange(
                playerId,
                Cmd.CMD.CS_SceneLeave,
                Scene.csSceneLeave.newBuilder()
                        .setSceneId(scene.sceneId)
                        .setScope(scene.scope)
                        .setPlayerId(playerId)
                        .setRequestId(requestId)
                        .build(),
                Cmd.CMD.SC_SceneLeave,
                Scene.scSceneLeave.class);
        assertEquals(requestId, response.getRequestId(), scene.label + "离开 requestId");
        return response;
    }

    private static void assertSuccessfulPath(
            Scene.scScenePathFind response,
            Scene.ScenePoint start,
            Scene.ScenePoint target,
            String name) {
        assertResult(response.getResult(), ErrorMsg.ErrorCode.Ok, name);
        assertTrue(response.getPointsCount() >= 2, name + "没有返回完整路径");
        assertPoint(start, response.getPoints(0), name + "起点");
        assertPoint(target, response.getPoints(response.getPointsCount() - 1), name + "终点");
        assertTrue(response.getTotalCost() > 0, name + "总移动成本必须大于 0");
        assertTrue(response.getVisitedNodes() > 0, name + "展开节点数必须大于 0");
        assertTrue(response.getCompletedTick() > 0, name + "结果没有回投 SceneShard Tick");
    }

    private static void assertObject(
            List<Scene.SceneObjectSnapshot> objects,
            long objectId,
            Scene.SceneObjectKind kind,
            boolean expected,
            String name) {
        boolean found = objects.stream()
                .anyMatch(object -> object.getObjectId() == objectId && object.getKind() == kind);
        if (found != expected) {
            throw new IllegalStateException(
                    name + "断言失败: objectId=" + objectId + ", kind=" + kind
                            + ", expectedPresent=" + expected + ", actualPresent=" + found);
        }
    }

    private static void assertResult(
            ErrorMsg.ErrorCode actual, ErrorMsg.ErrorCode expected, String name) {
        if (actual != expected) {
            throw new IllegalStateException(
                    name + "结果码错误: expected=" + expected + ", actual=" + actual);
        }
    }

    private static void assertPoint(Scene.ScenePoint expected, Scene.ScenePoint actual, String name) {
        if (expected.getX() != actual.getX() || expected.getY() != actual.getY()) {
            throw new IllegalStateException(
                    name + "错误: expected=(" + expected.getX() + "," + expected.getY()
                            + "), actual=(" + actual.getX() + "," + actual.getY() + ")");
        }
    }

    private static void assertEquals(long expected, long actual, String name) {
        if (expected != actual) {
            throw new IllegalStateException(name + "错误: expected=" + expected + ", actual=" + actual);
        }
    }

    private static void assertEquals(String expected, String actual, String name) {
        if (!expected.equals(actual)) {
            throw new IllegalStateException(name + "错误: expected=" + expected + ", actual=" + actual);
        }
    }

    private static void assertTrue(boolean value, String message) {
        if (!value) {
            throw new IllegalStateException(message);
        }
    }

    private static int blockIndex(int blockX, int blockY, int mapWidth) {
        int blockColumns = (mapWidth + REGION_SIZE - 1) / REGION_SIZE;
        return blockY * blockColumns + blockX;
    }

    private static Scene.ScenePoint point(int x, int y) {
        return Scene.ScenePoint.newBuilder().setX(x).setY(y).build();
    }

    private static long nextRequestId() {
        return REQUEST_IDS.incrementAndGet();
    }

    private static long nextPlayerId() {
        return PLAYER_IDS.incrementAndGet();
    }

    private static <T> T runCase(String name, CheckedSupplier<T> test) throws Exception {
        long start = System.nanoTime();
        try {
            T result = test.get();
            System.out.printf("[SCENE-RPC] PASS %-28s cost=%dms%n",
                    name, (System.nanoTime() - start) / 1_000_000L);
            return result;
        } catch (Throwable error) {
            throw new IllegalStateException("用例失败 [" + name + "]: " + error.getMessage(), error);
        }
    }

    private static void runCase(String name, CheckedRunnable test) throws Exception {
        runCase(name, () -> {
            test.run();
            return null;
        });
    }

    /** 测试中途失败也尽量释放玩家对象和 AOI 订阅，避免污染开发服后续观察。 */
    private static void bestEffortCleanup(
            String host, int port, long localPlayer, long localObserver, long crossPlayer) {
        try (SceneRpcTestClient cleanup = SceneRpcTestClient.connect(host, port)) {
            bestEffortLeave(cleanup, SceneSpec.LOCAL, localPlayer);
            bestEffortLeave(cleanup, SceneSpec.LOCAL, localObserver);
            bestEffortLeave(cleanup, SceneSpec.CROSS, crossPlayer);
        } catch (Exception cleanupError) {
            System.err.println("[SCENE-RPC] cleanup failed: " + cleanupError.getMessage());
        }
    }

    private static void bestEffortLeave(SceneRpcTestClient client, SceneSpec scene, long playerId) {
        try {
            leave(client, scene, playerId);
        } catch (Exception ignored) {
            // 原用例可能在创建玩家前失败，SCENE_NOT_FOUND 或连接关闭都无需覆盖首个失败原因。
        }
    }

    private enum SceneSpec {
        LOCAL("本服", "world-1", Scene.SceneScope.SCENE_SCOPE_LOCAL),
        CROSS("跨服", "cross-1", Scene.SceneScope.SCENE_SCOPE_CROSS);

        private final String label;
        private final String sceneId;
        private final Scene.SceneScope scope;

        SceneSpec(String label, String sceneId, Scene.SceneScope scope) {
            this.label = label;
            this.sceneId = sceneId;
            this.scope = scope;
        }
    }

    @FunctionalInterface
    private interface CheckedRunnable {
        void run() throws Exception;
    }

    @FunctionalInterface
    private interface CheckedSupplier<T> {
        T get() throws Exception;
    }
}
