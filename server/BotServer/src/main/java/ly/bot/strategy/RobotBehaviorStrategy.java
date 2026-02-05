package ly.bot.strategy;

import ly.net.NetClient;
import ly.bot.session.RobotSession;

/**
 * 机器人行为策略接口 - 策略模式
 */
public interface RobotBehaviorStrategy {
    void execute(NetClient client, RobotSession session);
    String getStrategyName();
}