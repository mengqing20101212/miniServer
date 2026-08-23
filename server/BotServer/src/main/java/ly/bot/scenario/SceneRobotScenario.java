package ly.bot.scenario;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

import ly.bot.action.RobotActionContext;
import ly.bot.action.RobotActionResult;
import ly.bot.action.impl.SceneRobotAction;
import ly.bot.session.RobotSession;
import ly.proto.Cmd;
import ly.proto.ErrorMsg;
import ly.proto.Scene;

/**
 * 单个登录玩家的完整场景生命周期。
 *
 * <p>执行链路固定为 RobotSession → GateServer → GameServer 玩家 FIFO → SceneServer：
 * 进入地图后先停在公共屏障，等全部机器人都进入后，再执行九宫格 AOI、个人迷雾寻路、
 * 同分片移动和离开。每个请求故意携带错误 playerId，只有 GameServer 正确绑定当前登录
 * Player 后 SceneServer 才会成功，用来防止测试再次绕开身份和玩家线程。</p>
 */
public final class SceneRobotScenario implements RobotScenario {
    private static final long REQUEST_TIMEOUT_SECONDS = 60L;
    private static final AtomicLong REQUEST_IDS = new AtomicLong(System.currentTimeMillis());

    private final int botIndex;
    private final CompletableFuture<Void> continueAfterAllEntered;
    private final CompletableFuture<Scene.scSceneEnter> enteredFuture = new CompletableFuture<>();
    private final CompletableFuture<Void> completionFuture = new CompletableFuture<>();
    private final AtomicBoolean started = new AtomicBoolean();
    private final AtomicBoolean abortRequested = new AtomicBoolean();
    private volatile RobotSession session;
    private volatile boolean entered;

    public SceneRobotScenario(int botIndex, CompletableFuture<Void> continueAfterAllEntered) {
        this.botIndex = botIndex;
        this.continueAfterAllEntered = continueAfterAllEntered;
    }

    @Override
    public void onLogin(RobotSession loggedInSession) {
        if (!started.compareAndSet(false, true)) {
            return;
        }
        this.session = loggedInSession;
        Thread.ofVirtual()
                .name("Scene-Robot-Scenario-" + botIndex)
                .start(this::runLifecycle);
    }

    private void runLifecycle() {
        Scene.ScenePoint origin = originFor(botIndex);
        Scene.ScenePoint target = point(origin.getX() + 1, origin.getY());
        // 客户端字段故意不可信；GameServer 必须覆盖成 session.getPlayerId()。
        long spoofedPlayerId = session.getPlayerId() + 9_000_000_000L;
        try {
            long enterRequestId = nextRequestId();
            Scene.scSceneEnter enter = invoke(new SceneRobotAction<>(
                    Cmd.CMD.CS_SceneEnter,
                    Cmd.CMD.SC_SceneEnter,
                    Scene.csSceneEnter.newBuilder()
                            .setSceneId("world-1")
                            .setScope(Scene.SceneScope.SCENE_SCOPE_LOCAL)
                            .setPlayerId(spoofedPlayerId)
                            .setPoint(origin)
                            .setRequestId(enterRequestId)
                            .build(),
                    Scene.scSceneEnter.class,
                    "SceneEnter"));
            requireResult(enter.getResult(), ErrorMsg.ErrorCode.Ok, "进入场景");
            require(enter.getRequestId() == enterRequestId, "进入场景 requestId 不一致");
            requirePoint(origin, enter.getCurrentPoint(), "进入场景坐标");
            entered = true;
            enteredFuture.complete(enter);

            // 容量测试必须先形成全部玩家同时在图的稳态，再开始移动和离开。
            continueAfterAllEntered.get(REQUEST_TIMEOUT_SECONDS * 5, TimeUnit.SECONDS);
            if (abortRequested.get()) {
                // 其他机器人启动失败时只做离场清理，不继续制造 AOI、A* 和移动负载。
                leave(spoofedPlayerId, false);
                completionFuture.complete(null);
                return;
            }

            long viewRequestId = nextRequestId();
            Scene.scSceneView view = invoke(new SceneRobotAction<>(
                    Cmd.CMD.CS_SceneView,
                    Cmd.CMD.SC_SceneView,
                    Scene.csSceneView.newBuilder()
                            .setSceneId("world-1")
                            .setScope(Scene.SceneScope.SCENE_SCOPE_LOCAL)
                            .setPlayerId(spoofedPlayerId)
                            .setCenterPoint(origin)
                            .setRadiusBlocks(1)
                            .setViewLevel(Scene.SceneViewLevel.SCENE_VIEW_DETAIL)
                            .setRequestId(viewRequestId)
                            .build(),
                    Scene.scSceneView.class,
                    "SceneView"));
            requireResult(view.getResult(), ErrorMsg.ErrorCode.Ok, "九宫格 AOI");
            require(view.getPlayerId() == session.getPlayerId(), "GameServer 没有覆盖伪造 playerId");
            require(view.getRequestId() == viewRequestId, "AOI requestId 不一致");
            require(view.getBlocksCount() >= 4 && view.getBlocksCount() <= 9, "AOI 块数量异常");
            require(view.getObjectsList().stream()
                    .anyMatch(object -> object.getObjectId() == session.getPlayerId()), "AOI 未返回玩家自身");

            long pathRequestId = nextRequestId();
            Scene.scScenePathFind path = invoke(new SceneRobotAction<>(
                    Cmd.CMD.CS_ScenePathFind,
                    Cmd.CMD.SC_ScenePathFind,
                    Scene.csScenePathFind.newBuilder()
                            .setSceneId("world-1")
                            .setScope(Scene.SceneScope.SCENE_SCOPE_LOCAL)
                            .setPlayerId(spoofedPlayerId)
                            .setStartPoint(origin)
                            .setTargetPoint(target)
                            .setFogPolicy(Scene.SceneFogPolicy.SCENE_FOG_DISCOVERED_ONLY)
                            .setMaxVisitedNodes(10_000)
                            .setRequestId(pathRequestId)
                            .build(),
                    Scene.scScenePathFind.class,
                    "ScenePathFind"));
            requireResult(path.getResult(), ErrorMsg.ErrorCode.Ok, "个人迷雾 A* 寻路");
            require(path.getRequestId() == pathRequestId, "寻路 requestId 不一致");
            require(path.getPointsCount() >= 2, "寻路没有返回完整路径");
            requirePoint(origin, path.getPoints(0), "寻路起点");
            requirePoint(target, path.getPoints(path.getPointsCount() - 1), "寻路终点");

            long moveRequestId = nextRequestId();
            Scene.scSceneMove move = invoke(new SceneRobotAction<>(
                    Cmd.CMD.CS_SceneMove,
                    Cmd.CMD.SC_SceneMove,
                    Scene.csSceneMove.newBuilder()
                            .setSceneId("world-1")
                            .setScope(Scene.SceneScope.SCENE_SCOPE_LOCAL)
                            .setPlayerId(spoofedPlayerId)
                            .setTargetPoint(target)
                            .setRequestId(moveRequestId)
                            .build(),
                    Scene.scSceneMove.class,
                    "SceneMove"));
            requireResult(move.getResult(), ErrorMsg.ErrorCode.Ok, "地图移动");
            require(move.getRequestId() == moveRequestId, "移动 requestId 不一致");
            requirePoint(target, move.getCurrentPoint(), "移动权威坐标");

            leave(spoofedPlayerId, true);
            completionFuture.complete(null);
        } catch (Throwable error) {
            enteredFuture.completeExceptionally(error);
            bestEffortLeave(spoofedPlayerId);
            completionFuture.completeExceptionally(error);
        }
    }

    private void leave(long spoofedPlayerId, boolean validate) throws Exception {
        if (!entered) {
            return;
        }
        long requestId = nextRequestId();
        Scene.scSceneLeave leave = invoke(new SceneRobotAction<>(
                Cmd.CMD.CS_SceneLeave,
                Cmd.CMD.SC_SceneLeave,
                Scene.csSceneLeave.newBuilder()
                        .setSceneId("world-1")
                        .setScope(Scene.SceneScope.SCENE_SCOPE_LOCAL)
                        .setPlayerId(spoofedPlayerId)
                        .setRequestId(requestId)
                        .build(),
                Scene.scSceneLeave.class,
                "SceneLeave"));
        if (validate) {
            requireResult(leave.getResult(), ErrorMsg.ErrorCode.Ok, "离开场景");
            require(leave.getPlayerId() == session.getPlayerId(), "离开回包玩家身份错误");
            require(leave.getRequestId() == requestId, "离开 requestId 不一致");
        }
        entered = false;
    }

    private void bestEffortLeave(long spoofedPlayerId) {
        try {
            leave(spoofedPlayerId, false);
        } catch (Exception ignored) {
            // 保留首个业务错误；测试编排器最终仍会关闭 Gate 连接。
        }
    }

    private <T extends com.google.protobuf.AbstractMessage> T invoke(SceneRobotAction<T> action) throws Exception {
        RobotActionResult result = action.execute(new RobotActionContext(null, session));
        if (!result.isSuccess()) {
            throw new IllegalStateException(action.getName() + " 执行失败: " + result.getMessage());
        }
        return action.responseFuture().get(REQUEST_TIMEOUT_SECONDS, TimeUnit.SECONDS);
    }

    public CompletableFuture<Scene.scSceneEnter> enteredFuture() {
        return enteredFuture;
    }

    public CompletableFuture<Void> completionFuture() {
        return completionFuture;
    }

    /** 编排失败时释放已进入玩家，避免其他机器人永久等待公共屏障。 */
    public void release() {
        continueAfterAllEntered.complete(null);
    }

    /** 启动编排失败时通知已经进图的机器人直接离场，并释放公共屏障。 */
    public void abort() {
        abortRequested.set(true);
        continueAfterAllEntered.complete(null);
    }

    public boolean isEntered() {
        return entered;
    }

    private static Scene.ScenePoint originFor(int index) {
        // 使用 AOI 块内部偏移 8，移动一格不会跨 Region；1024 个块循环分布 1 万玩家。
        int block = Math.floorMod(index - 1, 32 * 32);
        int blockX = block % 32;
        int blockY = block / 32;
        int x = Math.min(999, blockX * 32 + 8);
        int y = Math.min(999, blockY * 32 + 8);
        return point(x, y);
    }

    private static Scene.ScenePoint point(int x, int y) {
        return Scene.ScenePoint.newBuilder().setX(x).setY(y).build();
    }

    private static long nextRequestId() {
        return REQUEST_IDS.incrementAndGet();
    }

    private static void requireResult(
            ErrorMsg.ErrorCode actual, ErrorMsg.ErrorCode expected, String operation) {
        require(actual == expected,
                operation + "失败: expected=" + expected + ", actual=" + actual);
    }

    private static void requirePoint(Scene.ScenePoint expected, Scene.ScenePoint actual, String operation) {
        require(expected.getX() == actual.getX() && expected.getY() == actual.getY(),
                operation + "错误: expected=(" + expected.getX() + "," + expected.getY()
                        + "), actual=(" + actual.getX() + "," + actual.getY() + ")");
    }

    private static void require(boolean condition, String message) {
        if (!condition) {
            throw new IllegalStateException(message);
        }
    }
}
