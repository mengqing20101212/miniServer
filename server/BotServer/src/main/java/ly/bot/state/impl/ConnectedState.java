package ly.bot.state.impl;

import ly.bot.state.RobotState;
import ly.bot.state.RobotContext;
import ly.net.NetClient;
import org.slf4j.Logger;
import ly.LoggerDef;

/**
 * 已连接状态
 */
public class ConnectedState implements RobotState {
    private static final Logger logger = LoggerDef.SystemLogger;
    
    @Override
    public void handle(RobotContext context) {
        logger.debug("机器人 {} 处于已连接状态", context.getRobotId());
        // 在此状态下可以进行登录等操作
    }
    
    @Override
    public String getStateName() {
        return "ConnectedState";
    }
}