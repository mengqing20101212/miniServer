package ly.bot.module.impl;

import ly.bot.command.RobotCommand;
import ly.bot.factory.RobotCommandFactory;
import ly.bot.module.RobotModule;
import ly.bot.session.RobotSession;
import ly.net.NetClient;

/**
 * 机器人行为模块，封装登录、心跳、移动、战斗等可组合行为能力。
 */
public class LoginModule implements RobotModule {
    private boolean completed = false;
    private int step = 0;
    
    @Override
    public boolean executeStep(NetClient client, RobotSession session) {
        // 登录通常在初始化时完成，这里只是标记为已完成
        if (step == 0) {
            // 如果尚未登录，则发送登录请求
            if (!session.isLoginSuccess()) {
                RobotCommand loginCommand = RobotCommandFactory.createCommand(
                    RobotCommandFactory.CommandType.LOGIN
                );
                loginCommand.execute(client, session);
                
                // 存储登录相关的数据到会话级别存储
                session.getDataStore().put("login", "loginStartTime", System.currentTimeMillis());
                session.getDataStore().put("login", "loginAttemptCount", 1);
            }
            step++;
        } else if (step == 1) {
            // 检查是否登录成功
            if (session.isLoginSuccess()) {
                completed = true;
                session.getDataStore().put("login", "loginSuccessTime", System.currentTimeMillis());
                session.getDataStore().put("login", "loginSuccessful", true);
            }
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
        return "LoginModule";
    }
}
