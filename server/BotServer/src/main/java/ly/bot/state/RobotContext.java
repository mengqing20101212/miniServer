package ly.bot.state;

import ly.net.NetClient;
import ly.bot.session.RobotSession;
import ly.bot.strategy.RobotBehaviorStrategy;
import org.slf4j.Logger;
import ly.LoggerDef;

/**
 * 机器人上下文 - 状态模式的上下文类
 */
public class RobotContext {
    private static final Logger logger = LoggerDef.SystemLogger;
    
    private RobotState currentState;
    private final int robotId;
    private final NetClient netClient;
    private final RobotSession robotSession;
    private RobotBehaviorStrategy strategy;
    
    public RobotContext(int robotId, NetClient netClient) {
        this.robotId = robotId;
        this.netClient = netClient;
        this.robotSession = null;
        this.currentState = null; // 初始状态为空
    }
    
    public RobotContext(int robotId, NetClient netClient, RobotSession robotSession) {
        this.robotId = robotId;
        this.netClient = netClient;
        this.robotSession = robotSession;
        this.currentState = null; // 初始状态为空
    }
    
    public void setState(RobotState state) {
        this.currentState = state;
        logger.info("机器人 {} 状态变更为: {}", robotId, state.getStateName());
    }
    
    public void request() {
        if (currentState != null) {
            currentState.handle(this);
        } else {
            logger.warn("机器人 {} 状态未设置", robotId);
        }
    }
    
    public void performPostLoginActions() {
        if (strategy != null && netClient != null) {
            if (robotSession != null) {
                strategy.execute(netClient, robotSession);
            } else {
                strategy.execute(netClient, null);
            }
        }
    }
    
    // Getters
    public int getRobotId() { return robotId; }
    public NetClient getNetClient() { return netClient; }
    public RobotState getCurrentState() { return currentState; }
    
    public void setStrategy(RobotBehaviorStrategy strategy) {
        this.strategy = strategy;
    }
}