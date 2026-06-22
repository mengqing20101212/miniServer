package ly.logic.player.coroutine;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/** 批量玩家协程调用的结果集合。 */
public class CoroutineBatchResult<T> {
    private final Map<Long, T> successes = new LinkedHashMap<>();
    private final Map<Long, Throwable> failures = new LinkedHashMap<>();

    void addSuccess(long playerId, T result) {
        successes.put(playerId, result);
    }

    void addFailure(long playerId, Throwable error) {
        failures.put(playerId, error);
    }

    public boolean isSuccess() {
        return failures.isEmpty();
    }

    public Map<Long, T> getSuccesses() {
        return Collections.unmodifiableMap(successes);
    }

    public Map<Long, Throwable> getFailures() {
        return Collections.unmodifiableMap(failures);
    }
}
