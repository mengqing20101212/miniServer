package ly.bot.state.impl;

import ly.bot.state.RobotState;
import ly.bot.state.RobotContext;
import ly.net.NetClient;
import org.slf4j.Logger;
import ly.LoggerDef;

/**
 * 连接中状态
 */
public class ConnectingState implements RobotState {
    private static final Logger logger = LoggerDef.SystemLogger;
    
    @Override
    public void handle(RobotContext context) {
        logger.debug("机器人 {} 处于连接中状态", context.getRobotId());
        // 实际的连接逻辑在RobotContext中处理
    }
    
    @Override
    public String getStateName() {
        return "ConnectingState";
    }
}