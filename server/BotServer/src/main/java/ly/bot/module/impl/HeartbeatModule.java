package ly.bot.module.impl;

import ly.bot.command.RobotCommand;
import ly.bot.factory.RobotCommandFactory;
import ly.bot.module.RobotModule;
import ly.bot.session.RobotSession;
import ly.net.NetClient;

/**
 * 机器人行为模块，封装登录、心跳、移动、战斗等可组合行为能力。
 */
public class HeartbeatModule implements RobotModule {
    private boolean completed = false;
    private int step = 0;
    private static final int MAX_STEPS = 5; // 心跳模块执行5次心跳
    
    @Override
    public boolean executeStep(NetClient client, RobotSession session) {
        // 发送心跳包
        RobotCommand heartbeatCommand = RobotCommandFactory.createCommand(
            RobotCommandFactory.CommandType.HEARTBEAT
        );
        heartbeatCommand.execute(client, session);
        
        // 存储心跳相关的数据到会话级别存储
        session.getDataStore().put("heartbeat", "lastHeartbeatTime", System.currentTimeMillis());
        session.getDataStore().put("heartbeat", "heartbeatCount", step + 1);
        
        step++;
        if (step >= MAX_STEPS) {
            completed = true;
        }
        
        return completed;
    }
    
    @Override
    public void reset() {
        completed = false;
        step = 0;
        // 注意：不再清除会话级别的数据，因为其他模块可能需要这些数据
    }
    
    @Override
    public boolean isCompleted() {
        return completed;
    }
    
    @Override
    public String getName() {
        return "HeartbeatModule";
    }
}
