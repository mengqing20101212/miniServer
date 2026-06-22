package ly.logic.player.coroutine;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 批量玩家协程调用的结果集合。
 *
 * <p>成功和失败都按 playerId 保存，并保持写入顺序，方便调用方按目标玩家定位问题。
 */
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

    /** 返回成功结果快照，外部不能修改内部集合。 */
    public Map<Long, T> getSuccesses() {
        return Collections.unmodifiableMap(successes);
    }

    /** 返回失败结果快照，key 是目标玩家 id，value 是该玩家执行失败或等待失败的原因。 */
    public Map<Long, Throwable> getFailures() {
        return Collections.unmodifiableMap(failures);
    }
}
