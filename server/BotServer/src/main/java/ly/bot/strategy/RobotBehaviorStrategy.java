package ly.bot.strategy;

import ly.net.NetClient;

/**
 * 机器人行为策略接口 - 策略模式
 */
public interface RobotBehaviorStrategy {
    void execute(NetClient client);
    String getStrategyName();
}