package ly.bot.module.impl;

import ly.bot.command.RobotCommand;
import ly.bot.factory.RobotCommandFactory;
import ly.bot.module.RobotModule;
import ly.bot.session.RobotSession;
import ly.net.NetClient;

/**
 * 心跳模块 - 处理心跳相关行为
 * 
 * Author: OpenClaw AI Assistant
 * Date: 2026/2/5
 * File: HeartbeatModule
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
        heartbeatCommand.execute(client);
        
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