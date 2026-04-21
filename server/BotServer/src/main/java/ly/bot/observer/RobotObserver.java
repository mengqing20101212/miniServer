package ly.bot.observer;

import ly.bot.state.RobotContext;

/**
 * 机器人观察者接口 - 观察者模式
 */
public interface RobotObserver {
    void onStateChanged(RobotContext context);
    void onLoginSuccess(RobotContext context);
    void onDisconnected(RobotContext context);
}