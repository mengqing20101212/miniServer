package ly.bot;

import ly.bot.session.RobotSession;
import ly.LoggerDef;
import org.slf4j.Logger;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 机器人管理器 - 使用多种设计模式重构
 * 
 * 设计模式应用：
 * - 单一职责原则：每个类只负责一个特定功能
 * - 工厂模式：RobotCommandFactory创建命令对象
 * - 状态模式：管理机器人状态
 * - 观察者模式：通知状态变化
 * - 命令模式：封装各种操作
 * - 策略模式：不同的行为策略
 * 
 * Author: OpenClaw AI Assistant
 * Date: 2026/2/5
 * File: RobotManager
 */
public class RobotManager {
    private static final Logger logger = LoggerDef.SystemLogger;
    
    private final List<ly.bot.session.RobotSession> robotSessions = new CopyOnWriteArrayList<>();
    private final AtomicInteger connectionCounter = new AtomicInteger(0);
    
    private String loginServerHost;
    private int loginServerPort;
    private int numBots;
    
    public RobotManager(String loginServerHost, int loginServerPort, int numBots) {
        this.loginServerHost = loginServerHost;
        this.loginServerPort = loginServerPort;
        this.numBots = numBots;
    }
    
    public void start() {
        logger.info("开始启动 {} 个机器人", numBots);
        
        for (int i = 0; i < numBots; i++) {
            final int botId = i + 1;
            try {
                ly.bot.session.RobotSession robot = new ly.bot.session.RobotSession(botId, loginServerHost, loginServerPort);
                robotSessions.add(robot);
                
                // 为每个机器人创建一个虚拟线程进行管理
                Thread.ofVirtual().name("Robot-" + botId).start(() -> {
                    try {
                        // 启动登录流程
                        robot.startLoginProcess();
                    } catch (Exception e) {
                        logger.error("机器人 {} 登录流程异常", botId, e);
                    }
                });
                
                // 延迟启动，避免瞬间连接过多
                Thread.sleep(50); // 减少延迟时间，因为使用虚拟线程
                
            } catch (Exception e) {
                logger.error("创建机器人 {} 失败", botId, e);
            }
        }
        
        logger.info("机器人启动完成，总数: {}", robotSessions.size());
    }
    
    /**
     * 获取统计信息
     */
    public RobotStats getStats() {
        int total = robotSessions.size();
        int connectedToGate = 0;
        int loginSuccess = 0;
        
        for (ly.bot.session.RobotSession robot : robotSessions) {
            if (robot.isGateConnected()) connectedToGate++;
            if (robot.isLoginSuccess()) loginSuccess++;
        }
        
        return new RobotStats(total, connectedToGate, loginSuccess, connectionCounter.get());
    }
    
    public static class RobotStats {
        public final int total;
        public final int connectedToGate;
        public final int loginSuccess;
        public final int currentConnections;
        
        public RobotStats(int total, int connectedToGate, int loginSuccess, int currentConnections) {
            this.total = total;
            this.connectedToGate = connectedToGate;
            this.loginSuccess = loginSuccess;
            this.currentConnections = currentConnections;
        }
        
        @Override
        public String toString() {
            return String.format("总机器人: %d, 已连Gate: %d, 登录成功: %d, 当前连接: %d", 
                total, connectedToGate, loginSuccess, currentConnections);
        }
    }
}