package ly.bot.module.impl;

import ly.bot.command.RobotCommand;
import ly.bot.data.ModuleDataStore;
import ly.bot.data.impl.ConcurrentModuleDataStore;
import ly.bot.factory.RobotCommandFactory;
import ly.bot.module.RobotModule;
import ly.bot.session.RobotSession;
import ly.net.NetClient;

/**
 * 移动模块 - 处理移动相关行为
 * 
 * Author: OpenClaw AI Assistant
 * Date: 2026/2/5
 * File: MovementModule
 */
public class MovementModule implements RobotModule {
    private boolean completed = false;
    private int step = 0;
    private static final int MAX_MOVEMENTS = 3; // 移动模块执行3次移动
    
    // 模块专属数据存储
    private final ModuleDataStore<Object> dataStore = new ConcurrentModuleDataStore<>();
    
    @Override
    public boolean executeStep(NetClient client, RobotSession session) {
        // 发送移动命令
        RobotCommand moveCommand = RobotCommandFactory.createCommand(
            RobotCommandFactory.CommandType.MOVE
        );
        moveCommand.execute(client, session);
        
        // 存储移动相关的数据
        dataStore.put("lastMoveTime", System.currentTimeMillis());
        dataStore.put("moveCount", step + 1);
        dataStore.put("currentPosition", "x:" + (step * 10) + ", y:" + (step * 5));
        
        step++;
        if (step >= MAX_MOVEMENTS) {
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
        return "MovementModule";
    }
    
    @Override
    public ModuleDataStore<Object> getDataStore() {
        return dataStore;
    }
}