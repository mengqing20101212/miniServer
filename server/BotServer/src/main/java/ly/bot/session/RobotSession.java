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
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import ly.bot.module.ModuleManager;
import ly.bot.module.RobotModule;
import ly.bot.module.impl.HeartbeatModule;
import ly.bot.module.impl.MovementModule;
import ly.bot.entity.PlayerInfo;
import ly.bot.stats.PacketLatencyStats;
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
    
    // 消息队列，用于处理服务器响应
    private final java.util.concurrent.BlockingQueue<ly.net.packet.AbstractMessagePacket> messageQueue = new java.util.concurrent.LinkedBlockingQueue<>();
    
    private volatile boolean isLoginSuccess = false;
    private volatile boolean isGateConnected = false;
    
    // 使用状态模式管理状态
    private RobotContext robotContext;
    
    // 使用观察者模式
    private final List<RobotObserver> observers = new CopyOnWriteArrayList<>();
    
    // 模块管理器
    private ModuleManager moduleManager;
    
    // 玩家信息
    private PlayerInfo playerInfo;
    
    // 网络延迟统计
    private final PacketLatencyStats latencyStats = new PacketLatencyStats();
    
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
        this.robotContext = new RobotContext(botId, null, this); // 初始时没有NetClient
        
        // 添加默认观察者
        addObserver(new LoggingObserver());
        
        // 启动响应处理线程
        startResponseHandlingThread();
        
        // 初始化模块管理器
        initializeModuleManager();
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
                robotContext = new RobotContext(botId, gateClient, this);
                
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
            
            loginCommand.execute(gateClient, this);
            
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
        
        // 登录响应现在由响应处理线程统一处理
        // 这里只需要等待登录成功标志
        Thread.ofVirtual().name("Robot-" + botId + "-LoginWaiter").start(() -> {
            int attempts = 50; // 尝试5秒
            
            while (attempts > 0 && !isLoginSuccess) {
                try {
                    Thread.sleep(100);
                    attempts--;
                } catch (Exception e) {
                    logger.error("等待登录响应异常", botId, e);
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
            
            // 创建玩家信息对象（这里需要从响应数据中提取实际的玩家信息）
            // 暂时使用默认值，实际应用中应从响应包中解析
            this.playerInfo = new PlayerInfo(
                1000000L + botId,  // playerId
                accountId,         // accountId
                account,           // account
                "RobotPlayer" + botId, // nickname
                1,                 // level
                token              // token
            );
            
            logger.info("机器人 #{} 玩家信息已保存: {}", botId, playerInfo);
            
            // 使用状态模式更新状态
            robotContext.setState(new LoggedInState());
            robotContext.request(); // 触发状态处理
            
            logger.info("机器人 #{} 登录成功", botId);
            notifyLoginSuccess(robotContext);
            
            // 启动延迟统计报告线程
            startLatencyStatsReporter();
            
            // 登录成功后，可以开始执行其他游戏行为
            startGameActions();
        }
    }
    
    /**
     * 初始化模块管理器
     */
    private void initializeModuleManager() {
        // 创建模块列表
        List<RobotModule> modules = List.of(
            new HeartbeatModule(),
            new MovementModule(),
            new ly.bot.module.impl.CombatModule()
        );
        
        // 初始化模块管理器
        this.moduleManager = new ModuleManager(modules, this, gateClient);
    }
    
    /**
     * 启动延迟统计报告线程
     */
    private void startLatencyStatsReporter() {
        logger.info("机器人 #{} 启动延迟统计报告线程", botId);
        
        // 启动一个虚拟线程来定期输出延迟统计
        Thread.ofVirtual().name("Robot-" + botId + "-LatencyStats").start(() -> {
            try {
                while (running.get()) {
                    // 每30秒输出一次统计报告
                    Thread.sleep(30000);
                    
                    if (isLoginSuccess) {
                        logger.info("机器人 #{} 延迟统计:", botId);
                        latencyStats.printStats();
                    }
                }
            } catch (InterruptedException e) {
                // 线程被中断，正常退出
                logger.info("机器人 #{} 延迟统计报告虚拟线程结束", botId);
            }
        });
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
                    
                    // 使用模块管理器执行行为
                    if (moduleManager != null) {
                        moduleManager.executeStep();
                    }
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
                    heartbeatCmd.execute(gateClient, this);
                    break;
                case 1:
                    // 发送移动
                    RobotCommand moveCmd = RobotCommandFactory.createCommand(
                        RobotCommandFactory.CommandType.MOVE
                    );
                    moveCmd.execute(gateClient, this);
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
    
    /**
     * 启动响应处理线程
     */
    private void startResponseHandlingThread() {
        Thread.ofVirtual().name("Robot-" + botId + "-ResponseHandler").start(() -> {
            while (running.get()) {
                try {
                    // 从NetClient获取响应包并放入队列
                    AbstractMessagePacket response = gateClient != null ? gateClient.readPacket() : null;
                    if (response != null) {
                        // 将响应包放入消息队列
                        messageQueue.offer(response);
                    }
                    
                    // 处理队列中的消息
                    processMessageQueue();
                    
                    Thread.sleep(50); // 每50ms检查一次
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                } catch (Exception e) {
                    logger.error("响应处理线程异常", e);
                }
            }
        });
    }
    
    /**
     * 处理消息队列中的响应
     */
    private void processMessageQueue() {
        try {
            // 处理队列中的所有消息
            while (!messageQueue.isEmpty()) {
                AbstractMessagePacket response = messageQueue.poll();
                if (response != null) {
                    handleResponse(response);
                }
            }
        } catch (Exception e) {
            logger.error("处理消息队列异常", e);
        }
    }
    
    /**
     * 处理服务器响应
     */
    private void handleResponse(AbstractMessagePacket response) {
        try {
            // 根据命令类型创建相应的命令对象并处理响应
            ly.bot.command.RobotCommand command = null;
            switch (response.getCmd()) {
                case ly.proto.Cmd.CMD.SC_Login_VALUE:
                    command = ly.bot.factory.RobotCommandFactory.createCommand(ly.bot.factory.RobotCommandFactory.CommandType.LOGIN,
                        account, token, accountId, "robot_channel", "robot_device_" + botId);
                    break;
                case ly.proto.Cmd.CMD.SC_RpcPing_VALUE: // RPC心跳响应
                    command = ly.bot.factory.RobotCommandFactory.createCommand(ly.bot.factory.RobotCommandFactory.CommandType.HEARTBEAT);
                    break;
                default:
                    // 对于未知响应，可以根据需要创建通用处理命令
                    logger.debug("收到未知命令响应: {}", response.getCmd());
                    return;
            }
            
            if (command != null) {
                command.onResponse(response, gateClient, this); // 传递this作为RobotSession实例
            }
        } catch (Exception e) {
            logger.error("处理响应异常", e);
        }
    }
    
    /**
     * 设置登录成功状态
     */
    public void setLoginSuccess(boolean success) {
        this.isLoginSuccess = success;
    }
    
    /**
     * 获取延迟统计对象
     */
    public ly.bot.stats.PacketLatencyStats getLatencyStats() {
        return latencyStats;
    }
    
    /**
     * 获取玩家信息
     */
    public ly.bot.entity.PlayerInfo getPlayerInfo() {
        return playerInfo;
    }
    
    public void shutdown() {
        running.set(false);
        // loginClient不再使用，因为LoginServer通过HTTP访问
        if (gateClient != null) {
            gateClient.stop();
        }
    }
}