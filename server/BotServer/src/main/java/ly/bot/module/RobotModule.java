package ly.bot.module;

import ly.bot.session.RobotSession;
import ly.net.NetClient;

import java.util.List;

/**
 * 机器人模块接口 - 定义行为模块
 * 
 * Author: OpenClaw AI Assistant
 * Date: 2026/2/5
 * File: RobotModule
 */
public interface RobotModule {
    
    /**
     * 执行模块的一个步骤
     * @param client 网络客户端
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
     * @return 是否完成
     */
    boolean isCompleted();
    
    /**
     * 获取模块名称
     * @return 模块名称
     */
    String getName();
}