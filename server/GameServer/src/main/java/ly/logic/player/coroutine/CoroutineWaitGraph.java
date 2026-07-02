package ly.logic.player.coroutine;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import ly.logic.player.Player;

/**
 * 玩家协程同步等待关系图，用于在阻塞前发现互相等待。
 *
 * <p>例如玩家 A 的队列正在等待玩家 B，玩家 B 又准备等待玩家 A，如果不提前拦截，
 * 两个玩家队列都会阻塞到超时。这里用 playerId 记录等待边，不保存 Player 引用，避免玩家下线后被等待图持有。
 */
final class CoroutineWaitGraph {
    private static final CoroutineWaitGraph INSTANCE = new CoroutineWaitGraph();

    /** key 是发起等待的玩家 id，value 是它正在等待的目标玩家 id 集合。 */
    private final Map<Long, Set<Long>> waits = new HashMap<>();

    private CoroutineWaitGraph() {
    }

    static CoroutineWaitGraph getInstance() {
        return INSTANCE;
    }

    synchronized void addEdge(long sourcePlayerId, long targetPlayerId) {
        if (sourcePlayerId <= 0 || targetPlayerId <= 0 || sourcePlayerId == targetPlayerId) {
            return;
        }
        // 如果 target 已经能沿等待关系走回 source，再加 source -> target 就会形成环。
        if (hasPath(targetPlayerId, sourcePlayerId, new HashSet<>())) {
            throw new CoroutineDeadlockException(
                    "player coroutine deadlock detected, source="
                            + sourcePlayerId
                            + ", target="
                            + targetPlayerId
                            + ", waits="
                            + waits);
        }
        waits.computeIfAbsent(sourcePlayerId, ignored -> new HashSet<>()).add(targetPlayerId);
    }

    synchronized void addEdges(long sourcePlayerId, Collection<Player> targets) {
        if (sourcePlayerId <= 0 || targets == null || targets.isEmpty()) {
            return;
        }
        for (Player target : targets) {
            if (target == null || target.getPlayerId() == sourcePlayerId) {
                continue;
            }
            // 批量调用先只检测，不立刻写入，避免检测到一半失败后留下部分等待边。
            if (hasPath(target.getPlayerId(), sourcePlayerId, new HashSet<>())) {
                throw new CoroutineDeadlockException(
                        "player coroutine batch deadlock detected, source="
                                + sourcePlayerId
                                + ", target="
                                + target.getPlayerId()
                                + ", waits="
                                + waits);
            }
        }
        for (Player target : targets) {
            if (target != null && target.getPlayerId() > 0 && target.getPlayerId() != sourcePlayerId) {
                waits.computeIfAbsent(sourcePlayerId, ignored -> new HashSet<>()).add(target.getPlayerId());
            }
        }
    }

    synchronized void removeEdge(long sourcePlayerId, long targetPlayerId) {
        Set<Long> targets = waits.get(sourcePlayerId);
        if (targets == null) {
            return;
        }
        targets.remove(targetPlayerId);
        if (targets.isEmpty()) {
            waits.remove(sourcePlayerId);
        }
    }

    synchronized void removeEdges(long sourcePlayerId, Collection<Player> targets) {
        if (targets == null) {
            return;
        }
        for (Player target : targets) {
            if (target != null) {
                removeEdge(sourcePlayerId, target.getPlayerId());
            }
        }
    }

    private boolean hasPath(long fromPlayerId, long toPlayerId, Set<Long> visited) {
        // 深度优先搜索等待链：from -> ... -> to。
        if (fromPlayerId == toPlayerId) {
            return true;
        }
        if (!visited.add(fromPlayerId)) {
            return false;
        }
        Set<Long> nextPlayers = waits.get(fromPlayerId);
        if (nextPlayers == null || nextPlayers.isEmpty()) {
            return false;
        }
        for (Long next : nextPlayers) {
            if (next != null && hasPath(next, toPlayerId, visited)) {
                return true;
            }
        }
        return false;
    }
}
