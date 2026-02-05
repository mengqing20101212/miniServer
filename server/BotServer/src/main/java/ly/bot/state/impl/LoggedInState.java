package ly.bot.state.impl;

import ly.bot.state.RobotState;
import ly.bot.state.RobotContext;
import ly.net.NetClient;
import org.slf4j.Logger;
import ly.LoggerDef;

/**
 * 已登录状态
 */
public class LoggedInState implements RobotState {
    private static final Logger logger = LoggerDef.SystemLogger;
    
    @Override
    public void handle(RobotContext context) {
        logger.debug("机器人 {} 处于已登录状态", context.getRobotId());
        // 执行登录后的行为
        context.performPostLoginActions();
    }
    
    @Override
    public String getStateName() {
        return "LoggedInState";
    }
}