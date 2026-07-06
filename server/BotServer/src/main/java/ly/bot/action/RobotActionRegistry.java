package ly.bot.action;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;

import ly.LoggerDef;
import ly.net.packet.MessagePacket;

/**
 * 机器人响应分发表。
 *
 * <p>当前先按响应 cmd 分发，后续如果要严格校验 seq/sid，可以在这里扩展 PendingRequest。</p>
 */
public class RobotActionRegistry {
    private static final Logger logger = LoggerDef.SystemLogger;

    private final Map<Integer, RobotAction> responseActions = new ConcurrentHashMap<>();

    public void register(RobotAction action) {
        if (action.responseCmd() > 0) {
            responseActions.put(action.responseCmd(), action);
        }
    }

    public boolean dispatch(MessagePacket response, RobotActionContext context) {
        RobotAction action = responseActions.get(response.getCmd());
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
}
