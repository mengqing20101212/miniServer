package ly.bot.state;

import ly.net.NetClient;

/**
 * 机器人状态接口 - 状态模式
 */
public interface RobotState {
    void handle(RobotContext context);
    String getStateName();
}