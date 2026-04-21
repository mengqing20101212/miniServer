package ly.bot.observer.impl;

import ly.bot.observer.RobotObserver;
import ly.bot.state.RobotContext;
import org.slf4j.Logger;
import ly.LoggerDef;

/**
 * 日志观察者 - 记录机器人状态变化
 */
public class LoggingObserver implements RobotObserver {
    private static final Logger logger = LoggerDef.SystemLogger;
    
    @Override
    public void onStateChanged(RobotContext context) {
        logger.info("机器人 {} 状态变更为: {}", context.getRobotId(), context.getCurrentState().getStateName());
    }
    
    @Override
    public void onLoginSuccess(RobotContext context) {
        logger.info("机器人 {} 登录成功", context.getRobotId());
    }
    
    @Override
    public void onDisconnected(RobotContext context) {
        logger.info("机器人 {} 断开连接", context.getRobotId());
    }
}