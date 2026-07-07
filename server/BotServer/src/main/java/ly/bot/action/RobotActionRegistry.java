package ly.bot.action;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.slf4j.Logger;

import ly.LoggerDef;
import ly.net.packet.MessagePacket;

/**
 * 机器人响应分发表。
 *
 * <p>Action 发包成功后会登记 PendingRequest，回包优先按 pending 队列分发。
 * 普通客户端协议目前没有 callId，也不会回显上行 seq，所以这里用 responseCmd
 * 维护先入先出的请求队列，避免同一个响应 cmd 被后注册 Action 覆盖。</p>
 */
public class RobotActionRegistry {
    private static final Logger logger = LoggerDef.SystemLogger;
    private static final long PENDING_TIMEOUT_MILLIS = 60_000L;

    private final Map<Integer, RobotAction> responseActions = new ConcurrentHashMap<>();
    private final Map<Integer, Queue<PendingRequest>> pendingRequests = new ConcurrentHashMap<>();

    public void register(RobotAction action) {
        if (action.responseCmd() > 0) {
            RobotAction oldAction = responseActions.put(action.responseCmd(), action);
            if (oldAction != null && oldAction != action) {
                logger.warn(
                        "机器人响应 cmd 重复注册，旧 Action 会被覆盖, cmd: {}, old: {}, new: {}",
                        action.responseCmd(),
                        oldAction.getName(),
                        action.getName());
            }
        }
    }

    public void registerPending(RobotAction action, MessagePacket request) {
        int responseCmd = action.responseCmd();
        if (responseCmd <= 0) {
            return;
        }
        cleanupExpired(responseCmd);
        pendingRequests
                .computeIfAbsent(responseCmd, ignored -> new ConcurrentLinkedQueue<>())
                .offer(new PendingRequest(action, request.getSeq(), System.currentTimeMillis()));
    }

    public boolean dispatch(MessagePacket response, RobotActionContext context) {
        RobotAction action = pollPendingAction(response);
        if (action == null) {
            action = responseActions.get(response.getCmd());
        }
        if (action == null) {
            return false;
        }

        try {
            action.onResponse(response, context);
            return true;
        } catch (Exception e) {
            logger.error("机器人 Action 响应处理失败, action: {}, cmd: {}", action.getName(), response.getCmd(), e);
            return true;
        }
    }

    private RobotAction pollPendingAction(MessagePacket response) {
        cleanupExpired(response.getCmd());
        Queue<PendingRequest> queue = pendingRequests.get(response.getCmd());
        if (queue == null) {
            return null;
        }
        PendingRequest pending = queue.poll();
        if (queue.isEmpty()) {
            pendingRequests.remove(response.getCmd(), queue);
        }
        return pending == null ? null : pending.action();
    }

    private void cleanupExpired(int responseCmd) {
        Queue<PendingRequest> queue = pendingRequests.get(responseCmd);
        if (queue == null) {
            return;
        }
        long now = System.currentTimeMillis();
        while (true) {
            PendingRequest pending = queue.peek();
            if (pending == null || now - pending.createTimeMillis() <= PENDING_TIMEOUT_MILLIS) {
                break;
            }
            queue.poll();
            logger.warn(
                    "机器人 PendingRequest 超时清理, responseCmd={}, requestSeq={}, action={}",
                    responseCmd,
                    pending.requestSeq(),
                    pending.action().getName());
        }
        if (queue.isEmpty()) {
            pendingRequests.remove(responseCmd, queue);
        }
    }

    private record PendingRequest(RobotAction action, int requestSeq, long createTimeMillis) {}
}
