package ly.logic.login;

import ly.LoggerDef;
import ly.ServerContext;
import ly.logic.player.Player;
import ly.logic.player.PlayerManager;
import ly.logic.player.PlayerStatusEnum;
import ly.logic.player.PlayerUtils;
import ly.logic.player.event.PlayerEventType;
import ly.net.GamePlayer;
import ly.proto.Cmd;
import ly.proto.ErrorMsg;
import ly.proto.Login;
import ly.redis.RedisKeys;
import ly.redis.RedisUtils;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * 登录管理器类
 * <p>
 * 负责处理玩家登录请求，包括玩家身份验证、玩家对象创建与加载、登录状态管理等功能
 * 使用单例模式设计，通过虚拟线程异步处理登录任务
 */
public class LoginManager {
    // 单例实例
    private static LoginManager instance = new LoginManager();
    // 登录任务队列，容量为100
    ArrayBlockingQueue<LoginTask> loginTasks = new ArrayBlockingQueue<>(100);

    /**
     * 获取登录管理器单例
     *
     * @return LoginManager 单例实例
     */
    public static LoginManager getInstance() {
        return instance;
    }

    /**
     * 私有构造方法
     * <p>
     * 初始化时启动一个虚拟线程，用于异步处理登录任务队列
     * 线程会一直运行，不断从队列中获取任务并处理
     */
    private LoginManager() {
        Thread.ofVirtual().start(() -> {
            while (true) {
                try {
                    // 从队列中取出登录任务（如果队列为空则阻塞等待）
                    LoginTask task = loginTasks.take();
                    // 记录任务处理开始时间
                    long startTime = System.currentTimeMillis();
                    // 处理登录任务
                    handleLogin(task);
                    // 记录任务处理结束时间并计算耗时
                    long endTime = System.currentTimeMillis();
                    LoggerDef.SystemLogger.info(String.format("LoginManager handle login %s cost %d ms", task.request.toString(), endTime - startTime));
                } catch (InterruptedException e) {
                    // 捕获线程中断异常
                    e.printStackTrace();
                    LoggerDef.SystemLogger.error(String.format("LoginManager handler login error %s", e));
                }
            }
        }).setName("LoginManager"); // 设置线程名称
    }

    /**
     * 添加登录任务到队列
     *
     * @param task 登录任务对象
     */
    public void addLoginTask(LoginTask task) {
        // 将任务添加到队列
        loginTasks.add(task);
        // 记录日志，包含账号和令牌信息
        LoggerDef.SystemLogger.info(String.format("LoginManager add login task account:%s token:%s", task.request.getAccount(), task.request.getToken()));
    }

    /**
     * 处理登录任务的核心方法
     * <p>
     * 执行令牌验证、玩家加载/创建、状态更新等登录流程
     *
     * @param task 登录任务对象
     */
    private void handleLogin(LoginTask task) {
        // 获取玩家ID
        final long playerId = task.request.getPlayerId();

        // 1. 验证令牌
        if (!checkToken(task.request.getToken(), task.request.getAccount())) {
            LoggerDef.SystemLogger.error(String.format("LoginManager handle login token error %s account:%s", task.request.getToken(), task.request.getAccount()));
            sendErrorMsg(task, ErrorMsg.ErrorCode.SYSTEM_ERROR);
            return;
        }

        // 2. 查找在线玩家
        Player onlinePlayer = PlayerManager.getInstance().getOnlinePlayer(playerId);
        if (onlinePlayer == null) {
            // 3. 如果玩家不在线，尝试从数据库加载
            onlinePlayer = PlayerManager.getInstance().getPlayerByDB(playerId);
            if (onlinePlayer == null) {
                // 4. 如果数据库中也不存在，则创建新玩家
                // 使用Redis分布式锁防止玩家名称重复创建
                boolean lock = RedisUtils.lock(RedisKeys.LOCK_CREATE_PLAYER_NAME_KEY.getKey(task.request.getPlayerName()));
                if (lock) {
                    try {
                        // 创建新玩家对象
                        onlinePlayer = PlayerManager.getInstance().createNewPlayer(task.request);
                    } finally {
                        // 确保释放锁
                        RedisUtils.unlock(RedisKeys.LOCK_CREATE_PLAYER_NAME_KEY.getKey(task.request.getPlayerName()));
                    }
                } else {
                    // 获取锁失败，说明玩家名可能已被占用
                    LoggerDef.SystemLogger.info(String.format("LoginManager handle login %s lock fail create playerName", task.request.toString()));
                    sendErrorMsg(task, ErrorMsg.ErrorCode.PLAYER_NAME_EXISTS);
                    return;
                }
            }
            // 将玩家添加到在线玩家管理器
            PlayerManager.getInstance().addOnlinePlayer(onlinePlayer);

            onlinePlayer.setGamePlayer(new GamePlayer(task.session));
            onlinePlayer.getGamePlayer().setLastSeq(task.packet.getSeq());
            onlinePlayer.getGamePlayer().setLastClientCmd(task.packet.getCmd());
        }


        // 5. 更新玩家状态
        if (!task.request.getIsReconnect()) { // 不是重连
            onlinePlayer.setStatus(PlayerStatusEnum.LOGGING);
        }
        // 设置玩家为游戏中状态
        onlinePlayer.setStatus(PlayerStatusEnum.PLAYING);

        // 6. 派发登录完成事件
        onlinePlayer.dispatchEvent(PlayerEventType.PLAYER_LOGIN_COMPLETE);
        //TODO: 首次登录逻辑待实现

        // 7. 派发重连状态事件
        onlinePlayer.dispatchEvent(PlayerEventType.PLAYER_LOGIN_IS_RECONNECT, task.request.getIsReconnect());


        Login.scLogin.Builder res = Login.scLogin.newBuilder();
        res.setAccount(onlinePlayer.getAccount());
        res.setPlayerId(onlinePlayer.getPlayerId());
        res.setToken(onlinePlayer.getToken());
        res.setGameServerId(ServerContext.getServerId());
        res.setPlayerInfo(PlayerUtils.genPlayerInfo(onlinePlayer));
        onlinePlayer.sendMsg(Cmd.CMD.SC_Logout, res.build());
        onlinePlayer.statPlay();


// 登录协议响应
//        message scLogin
//        {
//            string account = 1;
//            int64  playerId = 2;
//            string token = 3;
//            string gameServerId = 4;
//            PlayerInfo playerInfo = 5;
//        }
    }

    /**
     * 发送错误消息给客户端
     *
     * @param task      登录任务对象
     * @param errorCode 错误码
     */
    private void sendErrorMsg(LoginTask task, ErrorMsg.ErrorCode errorCode) {
        // 创建错误响应消息构建器
        ErrorMsg.scErrorCode.Builder req = ErrorMsg.scErrorCode.newBuilder();
        // 设置原始消息ID
        req.setMsgId(task.packet.getCmd());
        // 设置错误码
        req.setErrorCode(errorCode);
        // 发送错误消息到客户端
        task.session.sendClientMsg(Cmd.CMD.CS_ErrorCode_VALUE, task.packet.getSeq(), 0, req.build());
    }

    /**
     * 验证令牌的有效性
     *
     * @param token   待验证的令牌
     * @param account 账号名
     * @return boolean 令牌是否有效
     */
    private boolean checkToken(String token, String account) {
        // 从Redis获取缓存的令牌
        String cacheTokens = RedisUtils.get(RedisKeys.LOGIN_ACCOUNT_TOKEN_KEY.getKey(account));
        if (cacheTokens == null) {
            return false;
        }
        // 比较令牌是否匹配
        return cacheTokens.equals(token);
    }

}