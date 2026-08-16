package ly.bot.module;

import java.util.List;

import ly.bot.action.RobotAction;
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

    /**
     * 模块启动时需要注册的响应处理 Action。
     *
     * <p>模块可以只负责组织执行顺序，具体响应解析仍交给 Action 自己处理。</p>
     *
     * @return 需要注册到会话响应分发表的 Action 列表
     */
    default List<RobotAction> setupActions() {
        return List.of();
    }
}
