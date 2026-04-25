package ly.bot.module.impl;

import ly.bot.command.RobotCommand;
import ly.bot.factory.RobotCommandFactory;
import ly.bot.module.RobotModule;
import ly.bot.session.RobotSession;
import ly.net.NetClient;

import java.util.Random;

/**
 * 机器人行为模块，封装登录、心跳、移动、战斗等可组合行为能力。
 */
public class GachaModule implements RobotModule {
    private boolean completed = false;
    private int step = 0;
    private static final int MAX_STEPS = 5; // 执行5次抽卡
    private static final int GACHA_COST = 100; // 单次抽卡消耗
    private final Random random = new Random();
    
    @Override
    public boolean executeStep(NetClient client, RobotSession session) {
        // 从会话数据存储中获取当前货币余额
        Integer currencyBalance = session.getDataStore().getAs("currency", "balance", Integer.class);
        if (currencyBalance == null) {
            currencyBalance = 0;
        }
        
        // 检查是否有足够货币进行抽卡
        if (currencyBalance >= GACHA_COST) {
            // 扣除抽卡费用
            currencyBalance -= GACHA_COST;
            session.getDataStore().put("currency", "balance", currencyBalance);
            
            // 执行抽卡操作
            boolean success = random.nextDouble() < 0.1; // 10% 概率获得稀有物品
            String result = success ? "rare_item" : "common_item";
            
            // 记录抽卡结果
            session.getDataStore().put("gacha", "lastResult", result);
            session.getDataStore().put("gacha", "lastCost", GACHA_COST);
            session.getDataStore().put("gacha", "successRate", success);
            Integer currentTotalSpent = session.getDataStore().getAs("gacha", "totalSpent", Integer.class);
            if (currentTotalSpent == null) {
                currentTotalSpent = 0;
            }
            session.getDataStore().put("gacha", "totalSpent", currentTotalSpent + GACHA_COST);
            
            step++;
        } else {
            // 货币不足，无法抽卡
            session.getDataStore().put("gacha", "insufficientFunds", true);
            completed = true; // 结束抽卡模块
        }
        
        if (step >= MAX_STEPS) {
            completed = true;
        }
        
        return completed;
    }
    
    @Override
    public void reset() {
        completed = false;
        step = 0;
        // 注意：不重置会话级别的数据，因为货币数据可能被其他模块使用
    }
    
    @Override
    public boolean isCompleted() {
        return completed;
    }
    
    @Override
    public String getName() {
        return "GachaModule";
    }
}
