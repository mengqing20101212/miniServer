package ly.bot.session;

import io.netty.channel.EventLoopGroup;
import ly.LoggerDef;
import ly.bot.command.RobotCommand;
import ly.bot.factory.RobotCommandFactory;
import ly.bot.observer.RobotObserver;
import ly.bot.observer.impl.LoggingObserver;
import ly.bot.state.RobotContext;
import ly.bot.state.impl.ConnectedState;
import ly.bot.state.impl.LoggedInState;
import ly.net.NetClient;
import ly.net.NetService;
import ly.proto.Cmd;
import ly.net.packet.AbstractMessagePacket;
import org.slf4j.Logger;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 机器人会话类 - 使用多种设计模式重构
 * 
 * 设计模式应用：
 * - 状态模式：管理机器人状态
 * - 观察者模式：通知状态变化
 * - 命令模式：封装各种操作
 * - 策略模式：不同的行为策略
 */
public class RobotSession {
    private static final Logger logger = LoggerDef.SystemLogger;
    
    private final int botId;
    private final String account;
    private final String token;
    private final long accountId;
    
    private String loginServerHost;
    private int loginServerPort;
    private NetClient loginClient;
    private NetClient gateClient;
    
    private volatile boolean isLoginSuccess = false;
    private volatile boolean isGateConnected = false;
    
    // 使用状态模式管理状态
    private RobotContext robotContext;
    
    // 使用观察者模式
    private final List<RobotObserver> observers = new CopyOnWriteArrayList<>();
    
    private final Random random = new Random();
    private final AtomicBoolean running = new AtomicBoolean(true);
    
    public RobotSession(int botId, String loginServerHost, int loginServerPort) {
        this.botId = botId;
        this.account = "robot_user_" + botId;
        this.token = "robot_token_" + botId;
        this.accountId = 1000000L + botId;
        this.loginServerHost = loginServerHost;
        this.loginServerPort = loginServerPort;
        
        // 初始化状态模式上下文
        this.robotContext = new RobotContext(botId, null); // 初始时没有NetClient
        
        // 添加默认观察者
        addObserver(new LoggingObserver());
    }
    
    /**
     * 开始完整的登录流程
     */
    public void startLoginProcess() {
        logger.info("机器人 #{} 开始登录流程", botId);
        
        // 第一步：连接LoginServer
        connectToLoginServer();
    }
    
    /**
     * 连接LoginServer
     */
    private void connectToLoginServer() {
        logger.info("机器人 #{} 正在连接LoginServer {}:{}", botId, loginServerHost, loginServerPort);
        
        try {
            loginClient = new NetClient(loginServerHost, loginServerPort, false);
            EventLoopGroup workerGroup = NetService.getInstance().getWorkerGroup();
            loginClient.start(workerGroup);
            
            // 更新上下文
            robotContext.setState(new ly.bot.state.impl.ConnectingState());
            notifyStateChanged(robotContext);
            
            // 等待连接就绪
            int maxWait = 50; // 最多等待5秒
            while (!loginClient.isReady() && maxWait > 0) {
                Thread.sleep(100);
                maxWait--;
            }
            
            if (loginClient.isReady()) {
                logger.info("机器人 #{} 成功连接到LoginServer", botId);
                
                // 获取服务器列表
                getServerList();
            } else {
                logger.error("机器人 #{} 连接LoginServer超时", botId);
            }
        } catch (Exception e) {
            logger.error("机器人 #{} 连接LoginServer失败", botId, e);
        }
    }
    
    /**
     * 获取服务器列表
     */
    private void getServerList() {
        logger.info("机器人 #{} 正在获取服务器列表", botId);
        
        try {
            // 构造获取服务器列表的请求
            // 这里需要根据实际的协议格式构造请求
            // 模拟一个简单的请求，实际可能需要使用特定的protobuf消息
            String requestJson = String.format("{\"account\":\"%s\",\"token\":\"%s\"}", account, token);
            
            // 发送获取服务器列表请求
            // 由于不知道具体的protobuf消息类型，暂时模拟
            logger.info("机器人 #{} 已获取服务器列表信息", botId);
            
            // 模拟获取到GateServer信息
            simulateGateServerInfo();
            
        } catch (Exception e) {
            logger.error("机器人 #{} 获取服务器列表失败", botId, e);
        }
    }
    
    /**
     * 模拟获取GateServer信息
     */
    private void simulateGateServerInfo() {
        // 模拟从服务器列表中获取GateServer信息
        // 在实际实现中，这里应该解析服务器返回的GateServer信息
        logger.info("机器人 #{} 模拟获取GateServer信息", botId);
        
        // 假设GateServer和LoginServer在同一主机和端口（实际情况可能不同）
        connectToGateServer();
    }
    
    /**
     * 连接到GateServer
     */
    private void connectToGateServer() {
        logger.info("机器人 #{} 正在连接GateServer", botId);
        
        // 这里应该使用从LoginServer获取的实际GateServer地址
        // 暂时使用模拟的地址
        String gateHost = loginServerHost; // 实际中应该使用从服务器列表获取的地址
        int gatePort = loginServerPort + 1000; // 假设GateServer在另一个端口
        
        try {
            gateClient = new NetClient(gateHost, gatePort, false);
            EventLoopGroup workerGroup = NetService.getInstance().getWorkerGroup();
            gateClient.start(workerGroup);
            
            // 等待连接就绪
            int maxWait = 50; // 最多等待5秒
            while (!gateClient.isReady() && maxWait > 0) {
                Thread.sleep(100);
                maxWait--;
            }
            
            if (gateClient.isReady()) {
                logger.info("机器人 #{} 成功连接到GateServer", botId);
                isGateConnected = true;
                
                // 更新机器人上下文的客户端
                robotContext = new RobotContext(botId, gateClient);
                
                // 发送登录请求到GateServer
                sendLoginToGateServer();
            } else {
                logger.error("机器人 #{} 连接GateServer超时", botId);
            }
        } catch (Exception e) {
            logger.error("机器人 #{} 连接GateServer失败", botId, e);
        }
    }
    
    /**
     * 向GateServer发送登录请求
     */
    private void sendLoginToGateServer() {
        logger.info("机器人 #{} 正在向GateServer发送登录请求", botId);
        
        try {
            // 使用命令模式创建登录命令
            RobotCommand loginCommand = RobotCommandFactory.createCommand(
                RobotCommandFactory.CommandType.LOGIN,
                account, token, accountId, "robot_channel", "robot_device_" + botId
            );
            
            loginCommand.execute(gateClient);
            
            logger.info("机器人 #{} 登录请求已发送", botId);
            
            // 等待登录响应
            waitForLoginResponse();
            
        } catch (Exception e) {
            logger.error("机器人 #{} 发送登录请求失败", botId, e);
        }
    }
    
    /**
     * 等待登录响应
     */
    private void waitForLoginResponse() {
        logger.info("机器人 #{} 等待登录响应", botId);
        
        // 设置一个线程来监听响应
        Thread.ofVirtual().name("Robot-" + botId + "-Response").start(() -> {
            int attempts = 50; // 尝试5秒
            
            while (attempts > 0 && !isLoginSuccess) {
                try {
                    // 尝试从GateServer接收响应
                    AbstractMessagePacket response = gateClient.readPacket();
                    if (response != null) {
                        // 处理响应
                        handleLoginResponse(response);
                        break;
                    }
                    
                    Thread.sleep(100);
                    attempts--;
                } catch (Exception e) {
                    logger.error("机器人 #{} 接收登录响应失败", botId, e);
                    break;
                }
            }
            
            if (!isLoginSuccess) {
                logger.warn("机器人 #{} 登录超时", botId);
            }
        });
    }
    
    /**
     * 处理登录响应
     */
    private void handleLoginResponse(AbstractMessagePacket response) {
        logger.info("机器人 #{} 收到登录响应: {}", botId, response);
        
        // 根据响应内容判断登录是否成功
        // 这里需要根据实际的响应格式进行处理
        if (response.getCmd() == Cmd.CMD.SC_Login_VALUE) {
            isLoginSuccess = true;
            
            // 使用状态模式更新状态
            robotContext.setState(new LoggedInState());
            robotContext.request(); // 触发状态处理
            
            logger.info("机器人 #{} 登录成功", botId);
            notifyLoginSuccess(robotContext);
            
            // 登录成功后，可以开始执行其他游戏行为
            startGameActions();
        }
    }
    
    /**
     * 开始游戏行为
     */
    private void startGameActions() {
        logger.info("机器人 #{} 开始执行游戏行为", botId);
        
        // 启动一个虚拟线程来定期执行游戏行为
        Thread.ofVirtual().name("Robot-" + botId + "-GameActions").start(() -> {
            try {
                while (running.get() && isLoginSuccess) {
                    // 随机延迟，模拟真实用户行为
                    Thread.sleep(2000 + random.nextInt(3000)); // 2-5秒
                    
                    // 执行一些游戏行为
                    performRandomAction();
                }
            } catch (InterruptedException e) {
                // 线程被中断，正常退出
                logger.info("机器人 #{} 游戏行为虚拟线程结束", botId);
            }
        });
    }
    
    /**
     * 执行随机游戏行为
     */
    private void performRandomAction() {
        if (!isLoginSuccess || gateClient == null || !gateClient.isConnected()) {
            return;
        }
        
        try {
            // 随机选择一种行为
            int actionType = random.nextInt(3); // 0-2 的行为类型
            
            switch (actionType) {
                case 0:
                    // 发送心跳
                    RobotCommand heartbeatCmd = RobotCommandFactory.createCommand(
                        RobotCommandFactory.CommandType.HEARTBEAT
                    );
                    heartbeatCmd.execute(gateClient);
                    break;
                case 1:
                    // 发送移动
                    RobotCommand moveCmd = RobotCommandFactory.createCommand(
                        RobotCommandFactory.CommandType.MOVE
                    );
                    moveCmd.execute(gateClient);
                    break;
                case 2:
                    // 其他行为
                    logger.debug("机器人 #{} 执行其他行为", botId);
                    break;
            }
        } catch (Exception e) {
            logger.error("机器人 #{} 执行随机行为失败", botId, e);
        }
    }
    
    // 观察者模式相关方法
    public void addObserver(RobotObserver observer) {
        observers.add(observer);
    }
    
    public void removeObserver(RobotObserver observer) {
        observers.remove(observer);
    }
    
    private void notifyStateChanged(RobotContext context) {
        for (RobotObserver observer : observers) {
            observer.onStateChanged(context);
        }
    }
    
    private void notifyLoginSuccess(RobotContext context) {
        for (RobotObserver observer : observers) {
            observer.onLoginSuccess(context);
        }
    }
    
    private void notifyDisconnected(RobotContext context) {
        for (RobotObserver observer : observers) {
            observer.onDisconnected(context);
        }
    }
    
    // Getter方法
    public boolean isLoginSuccess() {
        return isLoginSuccess;
    }
    
    public boolean isGateConnected() {
        return isGateConnected;
    }
    
    public int getBotId() {
        return botId;
    }
    
    public void shutdown() {
        running.set(false);
        if (loginClient != null) {
            loginClient.stop();
        }
        if (gateClient != null) {
            gateClient.stop();
        }
    }
}