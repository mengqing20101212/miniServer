package ly.bot.action;

import ly.net.packet.AbstractMessagePacket;

/**
 * 机器人协议动作。
 *
 * <p>Action 是 BotServer 发起协议的最小单位，一个 Action 对应一次明确的协议请求和可选的响应处理。
 * RobotModule 只负责组织 Action，不再直接关心具体协议包的构造细节。</p>
 */
public interface RobotAction {

    /**
     * 执行动作，通常会构造并发送一个协议包。
     */
    RobotActionResult execute(RobotActionContext context);

    /**
     * 处理服务端响应。没有响应语义的动作可以保持默认实现。
     */
    default void onResponse(AbstractMessagePacket response, RobotActionContext context) {
    }

    /**
     * 请求协议号，用于统计和排查。
     */
    int requestCmd();

    /**
     * 期望响应协议号。没有明确响应的动作返回 0。
     */
    default int responseCmd() {
        return 0;
    }

    /**
     * 动作名称。
     */
    String getName();
}
