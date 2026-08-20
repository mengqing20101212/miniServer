package ly.bot.module.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import ly.net.packet.MessagePacket;
import ly.proto.Cmd;
import ly.proto.ErrorMsg;
import ly.proto.Scene;

/**
 * SceneServer 万人 AOI 状态容量回归。
 *
 * <p>多个 BotServer TCP 连接批量模拟 10000 个 playerId，目标是压 SceneShard 的玩家对象、
 * 九宫格订阅、个人迷雾、命令队列和清理链路，而不是模拟 10000 条客户端网关连接。真正的
 * Gate/WebSocket 连接压测应继续使用 RobotManager；这里专门验证 SceneServer 的地图状态容量。
 *
 * <p>不能只用一条连接：同一 Netty Channel 的 Handler 固定在一个 EventLoop 上，而当前进入场景
 * Handler 会等待 Tick 线程返回结果，单连接只能串行推进。这里使用固定数量的真实连接并行请求，
 * 每条连接内部仍保持有界流水线，既覆盖网络层，也不会通过无限发包掩盖背压问题。
 */
public final class SceneRpcLoadTestModule {
    private static final int DEFAULT_PLAYERS = 10_000;
    private static final int DEFAULT_BATCH_SIZE = 128;
    private static final int MAX_PLAYERS = 10_000;
    private static final int MAX_CONNECTIONS = 32;
    // 32 条连接同时各发 128 个请求时，同一玩家条带可能累计百余个等待任务。
    // 单个 Handler 需要跨多个 Tick Future，30 秒会把正常背压误判成丢包；90 秒仍能及时发现死锁。
    private static final long BATCH_TIMEOUT_MILLIS = 90_000L;
    private static final AtomicLong RUN_IDS = new AtomicLong(System.currentTimeMillis());

    private SceneRpcLoadTestModule() {
    }

    public static boolean runStandalone(String host, int port, int playerCount, int batchSize) {
        if (playerCount <= 0 || playerCount > MAX_PLAYERS) {
            throw new IllegalArgumentException("playerCount must be in [1," + MAX_PLAYERS + "]");
        }
        if (batchSize <= 0 || batchSize > 512) {
            throw new IllegalArgumentException("batchSize must be in [1,512]");
        }

        long playerBase = 8_000_000_000L + Math.floorMod(RUN_IDS.incrementAndGet(), 100_000_000L) * 20_000L;
        AtomicInteger enteredPlayers = new AtomicInteger();
        try (SceneRpcTestClient client = SceneRpcTestClient.connect(host, port)) {
            Scene.scSceneMetrics baseline = metrics(client);
            long enterStart = System.nanoTime();
            int connectionCount = Math.min(MAX_CONNECTIONS, playerCount);
            enterPlayers(host, port, playerBase, playerCount, batchSize, connectionCount, enteredPlayers);
            long enterMillis = elapsedMillis(enterStart);

            Scene.scSceneMetrics loaded = metrics(client);
            assertEquals(
                    baseline.getObjectCount() + playerCount,
                    loaded.getObjectCount(),
                    "容量测试进入后的对象数");
            System.out.printf(
                    "[SCENE-LOAD] ENTER PASS players=%d connections=%d batch=%d cost=%dms "
                            + "throughput=%.2f/s objects=%d tick=%d%n",
                    playerCount, connectionCount, batchSize, enterMillis, perSecond(playerCount, enterMillis),
                    loaded.getObjectCount(), loaded.getTickNumber());

            // 抽样验证容量状态下 AOI 仍然能生成九宫格，而不是只看对象数变化。
            verifyAoiSample(client, playerBase, playerCount);

            long leaveStart = System.nanoTime();
            leavePlayers(host, port, playerBase, playerCount, batchSize, connectionCount);
            enteredPlayers.set(0);
            long leaveMillis = elapsedMillis(leaveStart);
            Scene.scSceneMetrics cleaned = metrics(client);
            assertEquals(baseline.getObjectCount(), cleaned.getObjectCount(), "容量测试清理后的对象数");
            System.out.printf(
                    "[SCENE-LOAD] LEAVE PASS players=%d batch=%d cost=%dms throughput=%.2f/s objects=%d%n",
                    playerCount, batchSize, leaveMillis, perSecond(playerCount, leaveMillis),
                    cleaned.getObjectCount());
            System.out.println("[SCENE-LOAD] ALL PASS");
            return true;
        } catch (Throwable error) {
            System.err.println("[SCENE-LOAD] FAIL enteredPlayers=" + enteredPlayers.get() + " " + error.getMessage());
            error.printStackTrace();
            cleanupAfterFailure(host, port, playerBase, playerCount, batchSize);
            return false;
        }
    }

    public static int defaultPlayers() {
        return DEFAULT_PLAYERS;
    }

    public static int defaultBatchSize() {
        return DEFAULT_BATCH_SIZE;
    }

    private static void enterPlayers(
            String host,
            int port,
            long playerBase,
            int playerCount,
            int batchSize,
            int connectionCount,
            AtomicInteger enteredPlayers) throws Exception {
        runParallelRanges(playerCount, connectionCount, (startInclusive, endExclusive) -> {
            try (SceneRpcTestClient client = SceneRpcTestClient.connect(host, port)) {
                enterRange(client, playerBase, startInclusive, endExclusive, batchSize, enteredPlayers);
            }
        });
    }

    /** 一条连接只处理自己的连续玩家区间，便于发生错误时准确定位和全量清理。 */
    private static void enterRange(
            SceneRpcTestClient client,
            long playerBase,
            int startInclusive,
            int endExclusive,
            int batchSize,
            AtomicInteger enteredPlayers) throws InterruptedException {
        int completed = startInclusive;
        while (completed < endExclusive) {
            int currentBatch = Math.min(batchSize, endExclusive - completed);
            Map<Long, Long> requests = new HashMap<>(currentBatch * 2);
            for (int offset = 0; offset < currentBatch; offset++) {
                int playerIndex = completed + offset;
                long playerId = playerBase + playerIndex;
                long requestId = playerId;
                requests.put(requestId, playerId);
                client.send(
                        playerId,
                        Cmd.CMD.CS_SceneEnter,
                        Scene.csSceneEnter.newBuilder()
                                .setSceneId("world-1")
                                .setScope(Scene.SceneScope.SCENE_SCOPE_LOCAL)
                                .setPlayerId(playerId)
                                .setPoint(pointFor(playerIndex))
                                .setRequestId(requestId)
                                .build());
            }
            for (int responseIndex = 0; responseIndex < currentBatch; responseIndex++) {
                MessagePacket packet = client.await(Cmd.CMD.SC_SceneEnter, BATCH_TIMEOUT_MILLIS);
                Scene.scSceneEnter response = client.decode(packet, Scene.scSceneEnter.class);
                Long playerId = requests.remove(response.getRequestId());
                if (playerId == null) {
                    throw new IllegalStateException("收到未知或重复的进入响应 requestId=" + response.getRequestId());
                }
                if (response.getResult() != ErrorMsg.ErrorCode.Ok) {
                    throw new IllegalStateException(
                            "容量玩家进入失败 playerId=" + playerId + ", result=" + response.getResult());
                }
                enteredPlayers.incrementAndGet();
            }
            completed += currentBatch;
        }
    }

    private static void leavePlayers(
            String host,
            int port,
            long playerBase,
            int playerCount,
            int batchSize,
            int connectionCount) throws Exception {
        runParallelRanges(playerCount, connectionCount, (startInclusive, endExclusive) -> {
            try (SceneRpcTestClient client = SceneRpcTestClient.connect(host, port)) {
                leaveRange(client, playerBase, startInclusive, endExclusive, batchSize);
            }
        });
    }

    private static void leaveRange(
            SceneRpcTestClient client,
            long playerBase,
            int startInclusive,
            int endExclusive,
            int batchSize) throws InterruptedException {
        int completed = startInclusive;
        while (completed < endExclusive) {
            int currentBatch = Math.min(batchSize, endExclusive - completed);
            Map<Long, Long> requests = new HashMap<>(currentBatch * 2);
            for (int offset = 0; offset < currentBatch; offset++) {
                long playerId = playerBase + completed + offset;
                long requestId = -playerId;
                requests.put(requestId, playerId);
                client.send(
                        playerId,
                        Cmd.CMD.CS_SceneLeave,
                        Scene.csSceneLeave.newBuilder()
                                .setSceneId("world-1")
                                .setScope(Scene.SceneScope.SCENE_SCOPE_LOCAL)
                                .setPlayerId(playerId)
                                .setRequestId(requestId)
                                .build());
            }
            for (int responseIndex = 0; responseIndex < currentBatch; responseIndex++) {
                MessagePacket packet = client.await(Cmd.CMD.SC_SceneLeave, BATCH_TIMEOUT_MILLIS);
                Scene.scSceneLeave response = client.decode(packet, Scene.scSceneLeave.class);
                Long playerId = requests.remove(response.getRequestId());
                if (playerId == null) {
                    throw new IllegalStateException("收到未知或重复的离开响应 requestId=" + response.getRequestId());
                }
                if (response.getResult() != ErrorMsg.ErrorCode.Ok
                        && response.getResult() != ErrorMsg.ErrorCode.SCENE_NOT_FOUND) {
                    throw new IllegalStateException(
                            "容量玩家离开失败 playerId=" + playerId + ", result=" + response.getResult());
                }
            }
            completed += currentBatch;
        }
    }

    /**
     * 把玩家编号切成互不重叠的连续区间，每个区间由一条虚拟线程和一条 TCP 连接处理。
     * 虚拟线程只负责等待网络响应；场景业务仍然严格在 SceneShard Tick 线程执行。
     */
    private static void runParallelRanges(
            int playerCount,
            int connectionCount,
            RangeOperation operation) throws Exception {
        List<Future<?>> futures = new ArrayList<>(connectionCount);
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int connectionIndex = 0; connectionIndex < connectionCount; connectionIndex++) {
                int startInclusive = connectionIndex * playerCount / connectionCount;
                int endExclusive = (connectionIndex + 1) * playerCount / connectionCount;
                futures.add(executor.submit(() -> {
                    operation.run(startInclusive, endExclusive);
                    return null;
                }));
            }
            for (Future<?> future : futures) {
                try {
                    future.get();
                } catch (ExecutionException error) {
                    Throwable cause = error.getCause();
                    if (cause instanceof Exception exception) {
                        throw exception;
                    }
                    throw new IllegalStateException("容量测试并行任务异常", cause);
                }
            }
        }
    }

    private static void verifyAoiSample(SceneRpcTestClient client, long playerBase, int playerCount)
            throws InterruptedException {
        int[] samples = playerCount == 1
                ? new int[] {0}
                : new int[] {0, playerCount / 2, playerCount - 1};
        for (int index : samples) {
            long playerId = playerBase + index;
            Scene.ScenePoint center = pointFor(index);
            Scene.scSceneView view = client.exchange(
                    playerId,
                    Cmd.CMD.CS_SceneView,
                    Scene.csSceneView.newBuilder()
                            .setSceneId("world-1")
                            .setScope(Scene.SceneScope.SCENE_SCOPE_LOCAL)
                            .setPlayerId(playerId)
                            .setCenterPoint(center)
                            .setRadiusBlocks(1)
                            .setViewLevel(Scene.SceneViewLevel.SCENE_VIEW_DETAIL)
                            .setRequestId(playerId + 1)
                            .build(),
                    Cmd.CMD.SC_SceneView,
                    Scene.scSceneView.class);
            if (view.getResult() != ErrorMsg.ErrorCode.Ok || view.getBlocksCount() < 4 || view.getBlocksCount() > 9) {
                throw new IllegalStateException(
                        "容量状态 AOI 抽样失败 playerId=" + playerId + ", result=" + view.getResult()
                                + ", blocks=" + view.getBlocksCount());
            }
            boolean containsSelf = view.getObjectsList().stream()
                    .anyMatch(object -> object.getObjectId() == playerId);
            if (!containsSelf) {
                throw new IllegalStateException("容量状态 AOI 没有返回玩家自身: " + playerId);
            }
        }
        System.out.printf("[SCENE-LOAD] AOI SAMPLE PASS samples=%d%n", samples.length);
    }

    private static Scene.scSceneMetrics metrics(SceneRpcTestClient client) throws InterruptedException {
        Scene.scSceneMetrics response = client.exchange(
                1L,
                Cmd.CMD.CS_SceneMetrics,
                Scene.csSceneMetrics.newBuilder()
                        .setSceneId("world-1")
                        .setScope(Scene.SceneScope.SCENE_SCOPE_LOCAL)
                        .build(),
                Cmd.CMD.SC_SceneMetrics,
                Scene.scSceneMetrics.class);
        if (response.getResult() != ErrorMsg.ErrorCode.Ok) {
            throw new IllegalStateException("容量测试指标查询失败: " + response.getResult());
        }
        return response;
    }

    private static Scene.ScenePoint pointFor(int playerIndex) {
        // 使用确定性散列把玩家均匀散到 1000 x 1000 地图，避免所有九宫格集中在单一热点块。
        int x = Math.floorMod(playerIndex * 37, 1_000);
        int y = Math.floorMod(playerIndex * 73 + playerIndex / 1_000, 1_000);
        return Scene.ScenePoint.newBuilder().setX(x).setY(y).build();
    }

    private static void cleanupAfterFailure(
            String host, int port, long playerBase, int playerCount, int batchSize) {
        try {
            // 对完整 ID 区间发送离开；未成功进入的玩家返回 NOT_FOUND，也视为幂等清理成功。
            leavePlayers(
                    host,
                    port,
                    playerBase,
                    playerCount,
                    Math.min(batchSize, 128),
                    Math.min(MAX_CONNECTIONS, playerCount));
            System.out.println("[SCENE-LOAD] failure cleanup completed playerRange=" + playerCount);
        } catch (Exception cleanupError) {
            System.err.println("[SCENE-LOAD] failure cleanup failed: " + cleanupError.getMessage());
        }
    }

    private static long elapsedMillis(long startNanos) {
        return Math.max(1L, TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNanos));
    }

    private static double perSecond(int operations, long millis) {
        return operations * 1_000.0d / Math.max(1L, millis);
    }

    private static void assertEquals(long expected, long actual, String name) {
        if (expected != actual) {
            throw new IllegalStateException(name + "错误: expected=" + expected + ", actual=" + actual);
        }
    }

    @FunctionalInterface
    private interface RangeOperation {
        void run(int startInclusive, int endExclusive) throws Exception;
    }
}
