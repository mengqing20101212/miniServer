package ly.logic.player.coroutine;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import ly.logic.player.Player;

/** 玩家协程同步等待关系图，用于在阻塞前发现互相等待。 */
final class CoroutineWaitGraph {
    private static final CoroutineWaitGraph INSTANCE = new CoroutineWaitGraph();

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
