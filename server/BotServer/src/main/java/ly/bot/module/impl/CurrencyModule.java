package ly.bot.module.impl;

import ly.bot.command.RobotCommand;
import ly.bot.factory.RobotCommandFactory;
import ly.bot.module.RobotModule;
import ly.bot.session.RobotSession;
import ly.net.NetClient;

/**
 * 货币模块 - 管理虚拟货币
 * 
 * Author: OpenClaw AI Assistant
 * Date: 2026/2/5
 * File: CurrencyModule
 */
public class CurrencyModule implements RobotModule {
    private boolean completed = false;
    private int step = 0;
    private static final int MAX_STEPS = 3; // 执行3次货币相关操作
    
    @Override
    public boolean executeStep(NetClient client, RobotSession session) {
        // 模拟获取货币的操作
        Integer currentCurrencyObj = session.getDataStore().getAs("currency", "balance", Integer.class);
        int currentCurrency;
        if (currentCurrencyObj == null) {
            currentCurrency = 1000; // 初始货币
        } else {
            currentCurrency = currentCurrencyObj;
        }
        
        // 模拟获得奖励
        int reward = 100;
        currentCurrency += reward;
        
        // 存储更新后的货币数量到会话级别存储
        session.getDataStore().put("currency", "balance", currentCurrency);
        session.getDataStore().put("currency", "lastReward", reward);
        session.getDataStore().put("currency", "lastUpdateTime", System.currentTimeMillis());
        
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
        // 注意：不重置会话级别的数据，因为其他模块可能需要这些数据
    }
    
    @Override
    public boolean isCompleted() {
        return completed;
    }
    
    @Override
    public String getName() {
        return "CurrencyModule";
    }
}