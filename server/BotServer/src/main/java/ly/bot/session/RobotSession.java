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
    private NetClient gateClient;
    private static ly.bot.http.HttpServerListClient globalHttpClient;
    
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
        
        // 初始化全局HTTP客户端
        if (globalHttpClient == null) {
            globalHttpClient = new ly.bot.http.HttpServerListClient(loginServerHost, loginServerPort);
        }
        
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
        
        // 直接通过HTTP获取服务器列表，然后连接GateServer
        connectToLoginServer();
    }
    
    /**
     * 获取服务器列表（LoginServer使用HTTP协议，不需要NetClient连接）
     */
    private void connectToLoginServer() {
        logger.info("机器人 #{} 通过HTTP获取服务器列表 {}:{}", botId, loginServerHost, loginServerPort);
        
        // 直接通过HTTP请求获取服务器列表，不需要NetClient连接
        getServerList();
    }
    
    /**
     * 获取服务器列表
     */
    private void getServerList() {
        logger.info("机器人 #{} 正在通过HTTP获取服务器列表", botId);
        
        try {
            // 通过HTTP请求获取服务器列表，使用全局HTTP客户端
            ly.bot.http.HttpServerListClient.ServerListResult serverListResult = 
                globalHttpClient.getServerList(account);
            
            if (serverListResult != null) {
                logger.info("机器人 #{} 成功获取服务器列表", botId);
                
                // 更新账户ID和令牌（从服务器返回的信息）
                // 注意：这里不能直接修改final字段accountId和token，所以我们需要在连接时使用服务器返回的值
                long returnedAccountId = serverListResult.getAccountId();
                String returnedToken = serverListResult.getToken();
                String gameServerId = serverListResult.getFirstGameServerId(); // 获取游戏服务器ID
                
                // 获取GateServer信息
                ly.bot.http.HttpServerListClient.ServerNode gateServer = serverListResult.getGate();
                if (gateServer != null) {
                    logger.info("机器人 #{} 获取到GateServer信息: {}:{}", botId, gateServer.getServerIp(), gateServer.getServerPort());
                    
                    // 连接到GateServer
                    connectToGateServer(gateServer.getServerIp(), gateServer.getServerPort(), returnedAccountId, returnedToken, gameServerId);
                } else {
                    logger.error("机器人 #{} 未获取到GateServer信息", botId);
                }
            } else {
                logger.error("机器人 #{} 获取服务器列表失败", botId);
            }
        } catch (Exception e) {
            logger.error("机器人 #{} 获取服务器列表失败", botId, e);
        }
    }
    
    /**
     * 连接到GateServer
     */
    private void connectToGateServer(String gateHost, int gatePort, long accountId, String token, String gameServerId) {
        logger.info("机器人 #{} 正在连接GateServer {}:{}", botId, gateHost, gatePort);
        
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
                
                // 发送登录请求到GateServer，使用从服务器获取的所有必要信息
                sendLoginToGateServer(accountId, token, gameServerId);
            } else {
                logger.error("机器人 #{} 连接GateServer超时", botId);
            }
        } catch (Exception e) {
            logger.error("机器人 #{} 连接GateServer失败", botId, e);
        }
    }
    
    /**
     * 连接到GateServer
     */
    private void connectToGateServer(String gateHost, int gatePort, long accountId, String token) {
        // 默认调用带gameServerId的版本，如果没有gameServerId则传入null
        connectToGateServer(gateHost, gatePort, accountId, token, null);
    }
    
    /**
     * 连接到GateServer（使用默认参数）
     */
    private void connectToGateServer() {
        // 默认实现，使用原始参数
        connectToGateServer(loginServerHost, loginServerPort + 1000, this.accountId, this.token, null);
    }
    
    /**
     * 向GateServer发送登录请求
     */
    private void sendLoginToGateServer(long accountId, String token, String gameServerId) {
        logger.info("机器人 #{} 正在向GateServer发送登录请求", botId);
        
        try {
            // 使用命令模式创建登录命令，使用从服务器获取的账号ID和令牌
            RobotCommand loginCommand = RobotCommandFactory.createCommand(
                RobotCommandFactory.CommandType.LOGIN,
                account, token, accountId, "robot_channel", "robot_device_" + botId, gameServerId
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
     * 向GateServer发送登录请求
     */
    private void sendLoginToGateServer(long accountId, String token) {
        sendLoginToGateServer(accountId, token, null);
    }
    
    /**
     * 向GateServer发送登录请求（使用默认参数）
     */
    private void sendLoginToGateServer() {
        sendLoginToGateServer(this.accountId, this.token, null);
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
        // loginClient不再使用，因为LoginServer通过HTTP访问
        if (gateClient != null) {
            gateClient.stop();
        }
    }
}