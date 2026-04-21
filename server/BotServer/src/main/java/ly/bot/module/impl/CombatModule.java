package ly.bot.module.impl;

import ly.bot.command.RobotCommand;
import ly.bot.factory.RobotCommandFactory;
import ly.bot.module.RobotModule;
import ly.bot.session.RobotSession;
import ly.net.NetClient;

import java.util.Random;

/**
 * 战斗模块 - 处理战斗相关行为
 * 
 * Author: OpenClaw AI Assistant
 * Date: 2026/2/5
 * File: CombatModule
 */
public class CombatModule implements RobotModule {
    private boolean completed = false;
    private int step = 0;
    private static final int MAX_COMBAT_ACTIONS = 10; // 战斗模块执行10次战斗动作
    private final Random random = new Random();
    
    @Override
    public boolean executeStep(NetClient client, RobotSession session) {
        // 执行战斗相关命令
        // 这里我们假设有一个战斗命令，暂时使用移动命令替代
        RobotCommand combatCommand = RobotCommandFactory.createCommand(
            RobotCommandFactory.CommandType.MOVE  // 暂时使用移动命令，实际应用中应有专门的战斗命令
        );
        combatCommand.execute(client, session);
        
        // 存储战斗相关的数据到会话级别存储
        session.getDataStore().put("combat", "lastCombatActionTime", System.currentTimeMillis());
        session.getDataStore().put("combat", "combatActionCount", step + 1);
        session.getDataStore().put("combat", "currentHp", 100 - (step * 5)); // 模拟血量变化
        session.getDataStore().put("combat", "currentMp", 80 - (step * 3)); // 模拟魔法值变化
        session.getDataStore().put("combat", "opponentHp", 100 - (random.nextInt(20))); // 模拟对手血量
        
        // 模拟战斗结果
        if (random.nextBoolean()) {
            session.getDataStore().put("combat", "lastCombatResult", "hit");
        } else {
            session.getDataStore().put("combat", "lastCombatResult", "miss");
        }
        
        step++;
        if (step >= MAX_COMBAT_ACTIONS) {
            completed = true;
            session.getDataStore().put("combat", "combatFinished", true);
            session.getDataStore().put("combat", "totalDamageDealt", step * 10); // 模拟造成的总伤害
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
        return "CombatModule";
    }
}