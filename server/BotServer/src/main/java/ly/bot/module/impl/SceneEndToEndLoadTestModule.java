package ly.bot.module.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import ly.bot.scenario.SceneRobotScenario;
import ly.bot.session.RobotSession;

/**
 * 真实玩家链路的 SceneServer 万人容量回归。
 *
 * <p>每个测试玩家都拥有独立账号、HTTP 登录流程、Gate TCP 连接、GameServer Player 和
 * RobotSession。这里绝不连接 SceneServer 端口，也不伪造最终玩家 ID。batchSize 只限制
 * 同时注册/登录/进入的数量，已经成功进入的机器人会保持在线，直到全部玩家到图后统一
 * 释放屏障，继续执行 AOI、寻路、移动和离开。</p>
 */
public final class SceneEndToEndLoadTestModule {
    private static final int DEFAULT_PLAYERS = 10_000;
    private static final int DEFAULT_BATCH_SIZE = 32;
    private static final int MAX_PLAYERS = 10_000;
    private static final int MAX_BATCH_SIZE = 100;
    private static final long LOGIN_AND_ENTER_TIMEOUT_SECONDS = 120L;
    private static final long LIFECYCLE_TIMEOUT_SECONDS = 300L;
    private static final long FAILURE_CLEANUP_TIMEOUT_SECONDS = 60L;

    private SceneEndToEndLoadTestModule() {
    }

    public static boolean runStandalone(
            String loginHost,
            int loginHttpPort,
            int playerCount,
            int batchSize,
            String gameServerId,
            String accountPrefix) {
        validate(playerCount, batchSize, gameServerId, accountPrefix);
        CompletableFuture<Void> allEnteredBarrier = new CompletableFuture<>();
        SessionBundle[] bundles = new SessionBundle[playerCount];
        Semaphore loginPermits = new Semaphore(batchSize);
        AtomicInteger enteredCount = new AtomicInteger();
        long startNanos = System.nanoTime();
        Throwable firstFailure = null;

        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            List<Future<?>> startupTasks = new ArrayList<>(playerCount);
            for (int index = 0; index < playerCount; index++) {
                final int arrayIndex = index;
                startupTasks.add(executor.submit(() -> {
                    loginPermits.acquire();
                    try {
                        int botIndex = arrayIndex + 1;
                        SceneRobotScenario scenario = new SceneRobotScenario(botIndex, allEnteredBarrier);
                        String account = accountPrefix + "_" + botIndex;
                        RobotSession session = new RobotSession(
                                botIndex,
                                loginHost,
                                loginHttpPort,
                                account,
                                gameServerId,
                                scenario);
                        bundles[arrayIndex] = new SessionBundle(session, scenario);

                        // 真实链路：HTTP 注册/拉服 → Gate TCP → Game 登录 → SceneEnter 回包。
                        session.startLoginProcess();
                        session.loginFuture().get(LOGIN_AND_ENTER_TIMEOUT_SECONDS, TimeUnit.SECONDS);
                        scenario.enteredFuture().get(LOGIN_AND_ENTER_TIMEOUT_SECONDS, TimeUnit.SECONDS);
                        int entered = enteredCount.incrementAndGet();
                        if (entered % Math.max(1, Math.min(1_000, playerCount)) == 0 || entered == playerCount) {
                            System.out.printf("[SCENE-E2E-LOAD] entered=%d/%d%n", entered, playerCount);
                        }
                    } finally {
                        loginPermits.release();
                    }
                    return null;
                }));
            }

            for (Future<?> task : startupTasks) {
                try {
                    task.get();
                } catch (ExecutionException error) {
                    if (firstFailure == null) {
                        firstFailure = error.getCause();
                    }
                }
            }
            if (firstFailure != null) {
                throw new IllegalStateException("真实机器人登录或进入场景失败", firstFailure);
            }

            long enterMillis = elapsedMillis(startNanos);
            System.out.printf(
                    "[SCENE-E2E-LOAD] ENTER PASS players=%d realGateConnections=%d batch=%d cost=%dms throughput=%.2f/s%n",
                    playerCount,
                    playerCount,
                    batchSize,
                    enterMillis,
                    perSecond(playerCount, enterMillis));

            long lifecycleStart = System.nanoTime();
            allEnteredBarrier.complete(null);
            CompletableFuture<?>[] completions = new CompletableFuture<?>[playerCount];
            for (int index = 0; index < playerCount; index++) {
                completions[index] = bundles[index].scenario().completionFuture();
            }
            CompletableFuture.allOf(completions).get(LIFECYCLE_TIMEOUT_SECONDS, TimeUnit.SECONDS);
            long lifecycleMillis = elapsedMillis(lifecycleStart);
            System.out.printf(
                    "[SCENE-E2E-LOAD] AOI/PATH/MOVE/LEAVE PASS players=%d cost=%dms throughput=%.2f lifecycle/s%n",
                    playerCount,
                    lifecycleMillis,
                    perSecond(playerCount, lifecycleMillis));
            System.out.println("[SCENE-E2E-LOAD] ALL PASS Login->Gate->GamePlayer->Scene->Game->Gate->RobotSession");
            return true;
        } catch (Throwable error) {
            System.err.printf(
                    "[SCENE-E2E-LOAD] FAIL entered=%d/%d reason=%s%n",
                    enteredCount.get(),
                    playerCount,
                    error.getMessage());
            error.printStackTrace();
            abortAndAwaitEnteredPlayers(bundles);
            return false;
        } finally {
            allEnteredBarrier.complete(null);
            for (SessionBundle bundle : bundles) {
                if (bundle != null) {
                    bundle.scenario().release();
                    bundle.session().shutdown();
                }
            }
        }
    }

    public static int defaultPlayers() {
        return DEFAULT_PLAYERS;
    }

    public static int defaultBatchSize() {
        return DEFAULT_BATCH_SIZE;
    }

    public static String defaultAccountPrefix() {
        return "scene_e2e_" + System.currentTimeMillis();
    }

    private static void validate(
            int playerCount,
            int batchSize,
            String gameServerId,
            String accountPrefix) {
        if (playerCount <= 0 || playerCount > MAX_PLAYERS) {
            throw new IllegalArgumentException("playerCount must be in [1," + MAX_PLAYERS + "]");
        }
        if (batchSize <= 0 || batchSize > MAX_BATCH_SIZE) {
            throw new IllegalArgumentException("batchSize must be in [1," + MAX_BATCH_SIZE + "]");
        }
        if (gameServerId == null || gameServerId.isBlank()) {
            throw new IllegalArgumentException("gameServerId must not be blank");
        }
        if (accountPrefix == null || accountPrefix.isBlank()) {
            throw new IllegalArgumentException("accountPrefix must not be blank");
        }
    }

    private static long elapsedMillis(long startNanos) {
        return Math.max(1L, TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNanos));
    }

    private static double perSecond(int operations, long millis) {
        return operations * 1_000.0d / Math.max(1L, millis);
    }

    /**
     * 启动阶段部分失败时，先让已经进图的真实玩家走 SceneLeave，再关闭 Gate 连接。
     * 直接 shutdown 会让 SceneServer 残留玩家对象，污染下一轮地图容量测试。
     */
    private static void abortAndAwaitEnteredPlayers(SessionBundle[] bundles) {
        List<CompletableFuture<Void>> cleanupFutures = new ArrayList<>();
        for (SessionBundle bundle : bundles) {
            if (bundle == null) {
                continue;
            }
            if (bundle.scenario().isEntered()) {
                cleanupFutures.add(bundle.scenario().completionFuture());
            }
            bundle.scenario().abort();
        }
        if (cleanupFutures.isEmpty()) {
            return;
        }
        try {
            CompletableFuture.allOf(cleanupFutures.toArray(CompletableFuture[]::new))
                    .get(FAILURE_CLEANUP_TIMEOUT_SECONDS, TimeUnit.SECONDS);
        } catch (Exception cleanupError) {
            System.err.printf(
                    "[SCENE-E2E-LOAD] cleanup timeout/failure, pendingEnteredPlayers=%d reason=%s%n",
                    cleanupFutures.size(), cleanupError.getMessage());
        }
    }

    private record SessionBundle(RobotSession session, SceneRobotScenario scenario) {
    }
}
