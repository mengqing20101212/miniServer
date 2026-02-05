package ly.bot.module.impl;

import ly.bot.command.RobotCommand;
import ly.bot.data.ModuleDataStore;
import ly.bot.data.impl.ConcurrentModuleDataStore;
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
    
    // 模块专属数据存储
    private final ModuleDataStore<Object> dataStore = new ConcurrentModuleDataStore<>();
    
    @Override
    public boolean executeStep(NetClient client, RobotSession session) {
        // 发送心跳包
        RobotCommand heartbeatCommand = RobotCommandFactory.createCommand(
            RobotCommandFactory.CommandType.HEARTBEAT
        );
        heartbeatCommand.execute(client, session);
        
        // 存储心跳相关的数据
        dataStore.put("lastHeartbeatTime", System.currentTimeMillis());
        dataStore.put("heartbeatCount", step + 1);
        
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
        // 清除模块数据
        dataStore.clear();
    }
    
    @Override
    public boolean isCompleted() {
        return completed;
    }
    
    @Override
    public String getName() {
        return "HeartbeatModule";
    }
    
    @Override
    public ModuleDataStore<Object> getDataStore() {
        return dataStore;
    }
}