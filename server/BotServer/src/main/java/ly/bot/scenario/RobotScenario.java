package ly.bot.scenario;

import ly.bot.session.RobotSession;

/**
 * 登录成功后由真实 RobotSession 执行的确定性业务场景。
 *
 * <p>普通 {@code --run-bots} 仍使用原有随机模块；专项回归可以实现本接口，在同一条
 * Login → Gate 长连接上按固定顺序发送协议。场景不得自行创建直连 Game/Scene 的
 * NetClient，所有请求必须调用 RobotSession 的 Action 发送入口。</p>
 */
@FunctionalInterface
public interface RobotScenario {
    void onLogin(RobotSession session);
}
