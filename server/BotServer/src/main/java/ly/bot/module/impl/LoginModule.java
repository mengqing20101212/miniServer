package ly.bot.module.impl;

import ly.bot.command.RobotCommand;
import ly.bot.command.RobotCommand;
import ly.bot.factory.RobotCommandFactory;
import ly.bot.module.RobotModule;
import ly.bot.session.RobotSession;
import ly.net.NetClient;

/**
 * 登录模块 - 处理登录相关行为
 * 
 * Author: OpenClaw AI Assistant
 * Date: 2026/2/5
 * File: LoginModule
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
            }
            step++;
        } else if (step == 1) {
            // 检查是否登录成功
            if (session.isLoginSuccess()) {
                completed = true;
            }
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
        return "LoginModule";
    }
}