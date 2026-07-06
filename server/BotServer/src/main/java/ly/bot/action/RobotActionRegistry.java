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
            /*
             * 当前注册表只按 response cmd 分发，适合 Bot 的模块级协议验证。
             * 如果同一个响应 cmd 对应多个有状态 Action，后注册者会覆盖先注册者，
             * 因此模块内部应复用同一个 Action 实例，或后续升级为 callId/seq 级 PendingRequest。
             */
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
