package ly.logic.player.coroutine;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import ly.db.entry.PlayerEntry;
import ly.logic.player.Player;
import ly.logic.player.PlayerData;
import ly.net.GamePlayer;

public class CoroutineUtilsTest {

    @Test
    public void shouldRunProxyMethodOnTargetPlayerQueue() throws Exception {
        Player target = newPlayer(2L, 20);
        CompletableFuture<Integer> result = CompletableFuture.supplyAsync(() -> CoroutineUtils.on(target).getLevel());

        waitQueue(target, 1);
        target.getGamePlayer().tickWorkItem();

        assertEquals(20, result.get(1, TimeUnit.SECONDS).intValue());
    }

    @Test
    public void shouldSupportPlayerToPlayerBlockingCall() throws Exception {
        Player source = newPlayer(1L, 10);
        Player target = newPlayer(2L, 30);
        PlayerCoroutineTask<Integer> sourceTask = new PlayerCoroutineTask<>(
                0L,
                source.getPlayerId(),
                "source-call-target",
                player -> CoroutineUtils.on(target).getLevel());
        source.getGamePlayer().addCoroutineTask(sourceTask);

        Thread sourceThread = Thread.ofVirtual().start(() -> tickOnce(source));
        waitQueue(target, 1);
        target.getGamePlayer().tickWorkItem();

        assertEquals(30, sourceTask.future().get(1, TimeUnit.SECONDS).intValue());
        sourceThread.join(1000);
    }

    @Test(expected = CoroutineDeadlockException.class)
    public void shouldFailFastWhenWaitGraphHasCycle() {
        Player source = newPlayer(1L, 10);
        Player target = newPlayer(2L, 20);
        CoroutineWaitGraph waitGraph = CoroutineWaitGraph.getInstance();
        waitGraph.addEdge(target.getPlayerId(), source.getPlayerId());
        PlayerThreadContext.enter(source.getPlayerId());
        try {
            CoroutineUtils.on(target).getLevel();
        } finally {
            PlayerThreadContext.exit();
            waitGraph.removeEdge(target.getPlayerId(), source.getPlayerId());
        }
    }

    @Test
    public void shouldBatchCallPlayersAndWaitAllSuccess() throws Exception {
        Player player2 = newPlayer(2L, 20);
        Player player3 = newPlayer(3L, 30);
        CompletableFuture<CoroutineBatchResult<Integer>> result = CompletableFuture.supplyAsync(
                () -> CoroutineUtils.batch(List.of(player2, player3))
                        .timeout(1000)
                        .call(Player::getLevel));

        waitQueue(player2, 1);
        waitQueue(player3, 1);
        player2.getGamePlayer().tickWorkItem();
        player3.getGamePlayer().tickWorkItem();

        CoroutineBatchResult<Integer> batchResult = result.get(1, TimeUnit.SECONDS);
        assertTrue(batchResult.isSuccess());
        assertEquals(20, batchResult.getSuccesses().get(2L).intValue());
        assertEquals(30, batchResult.getSuccesses().get(3L).intValue());
    }

    @Test(expected = ExecutionException.class)
    public void shouldCancelPendingCoroutineTasksWhenPlayerLeaves() throws Exception {
        Player target = newPlayer(2L, 20);
        PlayerCoroutineTask<Integer> task = new PlayerCoroutineTask<>(1L, target.getPlayerId(), "pending",
                Player::getLevel);
        target.getGamePlayer().addCoroutineTask(task);

        target.getGamePlayer().cancelPendingCoroutineTasks(new IllegalStateException("player offline"));

        task.future().get(1, TimeUnit.SECONDS);
    }

    @Test
    public void shouldAwaitOfflineDrainUntilQueuedTasksFinished() throws Exception {
        Player target = newPlayer(2L, 20);
        AtomicBoolean executed = new AtomicBoolean();
        PlayerCoroutineTask<Void> task = new PlayerCoroutineTask<>(
                1L,
                target.getPlayerId(),
                "offline-drain",
                player -> {
                    executed.set(true);
                    return null;
                });
        target.getGamePlayer().addCoroutineTask(task);

        CompletableFuture<Boolean> drainResult = CompletableFuture.supplyAsync(
                () -> {
                    try {
                        return target.getGamePlayer().awaitOfflineDrain(1000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return false;
                    }
                });

        Thread.sleep(50L);
        assertTrue(!drainResult.isDone());
        target.getGamePlayer().tickWorkItem();

        assertTrue(drainResult.get(1, TimeUnit.SECONDS));
        assertTrue(executed.get());
    }

    @Test
    public void shouldRejectNewCoroutineTasksAfterOfflineDrainBegins() {
        Player target = newPlayer(2L, 20);
        target.getGamePlayer().beginOfflineDrain();

        PlayerCoroutineTask<Integer> task = new PlayerCoroutineTask<>(1L, target.getPlayerId(), "reject-after-drain",
                Player::getLevel);

        assertTrue(!target.getGamePlayer().addCoroutineTask(task));
    }

    private void tickOnce(Player player) {
        try {
            player.getGamePlayer().tickWorkItem();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void waitQueue(Player player, int expectedSize) throws InterruptedException {
        long deadline = System.currentTimeMillis() + 1000L;
        while (System.currentTimeMillis() < deadline) {
            if (player.getGamePlayer().getWorkQueueSize() >= expectedSize) {
                return;
            }
            Thread.sleep(10L);
        }
        throw new AssertionError("wait queue timeout, playerId=" + player.getPlayerId());
    }

    private Player newPlayer(long playerId, int level) {
        GamePlayer gamePlayer = new GamePlayer(null);
        Player player = new Player();
        PlayerEntry entry = new PlayerEntry();
        entry.setId(playerId);
        entry.setAccount("account-" + playerId);
        entry.setName("player-" + playerId);
        entry.setLevel(level);
        player.setPlayerData(new PlayerData(entry));
        player.setGamePlayer(gamePlayer);
        gamePlayer.setPlayerId(playerId);
        return player;
    }
}
