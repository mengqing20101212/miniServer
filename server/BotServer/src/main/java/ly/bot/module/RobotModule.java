package ly.bot.module;

import ly.bot.session.RobotSession;
import ly.net.NetClient;

/**
 * 机器人行为模块，封装登录、心跳、移动、战斗等可组合行为能力。
 */
public interface RobotModule {

    /**
     * 执行模块的一个步骤
     * 
     * @param client  网络客户端
     * @param session 机器人会话
     * @return 是否完成此模块
     */
    boolean executeStep(NetClient client, RobotSession session);

    /**
     * 重置模块状态
     */
    void reset();

    /**
     * 检查模块是否完成
     * 
     * @return 是否完成
     */
    boolean isCompleted();

    /**
     * 获取模块名称
     * 
     * @return 模块名称
     */
    String getName();
}
