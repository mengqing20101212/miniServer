package ly.logic.login;

import java.util.concurrent.ArrayBlockingQueue;

import ly.LoggerDef;
import ly.ServerContext;
import ly.logic.player.Player;
import ly.logic.player.PlayerManager;
import ly.logic.player.PlayerStatusEnum;
import ly.logic.player.PlayerUtils;
import ly.logic.player.event.PlayerEventType;
import ly.net.GamePlayer;
import ly.net.packet.MessagePacket;
import ly.net.packet.MessagePacketFactory;
import ly.proto.Cmd;
import ly.proto.ErrorMsg;
import ly.proto.Login;
import ly.proto.Server;
import ly.redis.RedisKeys;
import ly.redis.RedisUtils;

/**
 * 登录管理器。
 *
 * <p>
 * 登录流程涉及 DB/Redis 等同步 IO，统一放在独立登录协程里处理，避免阻塞 RPC 入站线程。
 */
public class LoginManager {
    private static final LoginManager instance = new LoginManager();

    private final ArrayBlockingQueue<LoginTask> loginTasks = new ArrayBlockingQueue<>(100);

    public static LoginManager getInstance() {
        return instance;
    }

    private LoginManager() {
        Thread.ofVirtual().start(() -> {
            while (true) {
                try {
                    LoginTask task = loginTasks.take();
                    long startTime = System.currentTimeMillis();
                    handleLogin(task);
                    long endTime = System.currentTimeMillis();
                    LoggerDef.SystemLogger.info(
                            "LoginManager handle login {} cost {} ms",
                            task.request,
                            endTime - startTime);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    LoggerDef.SystemLogger.error("LoginManager handler login interrupted", e);
                    return;
                } catch (Exception e) {
                    LoggerDef.SystemLogger.error("LoginManager handler login error", e);
                }
            }
        }).setName("LoginManager");
    }

    public void addLoginTask(LoginTask task) {
        loginTasks.add(task);
        LoggerDef.SystemLogger.info(
                "LoginManager add login task account:{} token:{} callId:{}",
                task.request.getAccount(),
                task.request.getToken(),
                task.callId);
    }

    private void handleLogin(LoginTask task) {
        final long playerId = task.request.getPlayerId();
        boolean firstLogin = false;

        if (!checkToken(task.request.getToken(), task.request.getAccount())) {
            LoggerDef.SystemLogger.error(
                    "LoginManager handle login token error {} account:{}",
                    task.request.getToken(),
                    task.request.getAccount());
            sendErrorMsg(task, ErrorMsg.ErrorCode.SYSTEM_ERROR);
            return;
        }

        Player onlinePlayer = PlayerManager.getInstance().getOnlinePlayer(playerId);
        if (onlinePlayer == null) {
            onlinePlayer = PlayerManager.getInstance().getPlayerByDB(playerId);
            if (onlinePlayer == null) {
                boolean lock = RedisUtils
                        .lock(RedisKeys.LOCK_CREATE_PLAYER_NAME_KEY.getKey(task.request.getPlayerName()));
                if (lock) {
                    try {
                        onlinePlayer = PlayerManager.getInstance().createNewPlayer(task.request);
                        firstLogin = true;
                    } finally {
                        RedisUtils.unlock(RedisKeys.LOCK_CREATE_PLAYER_NAME_KEY.getKey(task.request.getPlayerName()));
                    }
                } else {
                    LoggerDef.SystemLogger.info(
                            "LoginManager handle login {} lock fail create playerName",
                            task.request);
                    sendErrorMsg(task, ErrorMsg.ErrorCode.PLAYER_NAME_EXISTS);
                    return;
                }
            }

            PlayerManager.getInstance().addOnlinePlayer(onlinePlayer);

            GamePlayer gamePlayer = new GamePlayer(task.session);
            gamePlayer.setPlayerId(onlinePlayer.getPlayerId());
            gamePlayer.setLastSeq(task.packet.getSeq());
            gamePlayer.setLastClientCmd(task.packet.getCmd());
            gamePlayer.setLastSid(task.packet.getSid());
            gamePlayer.setLastCallId(task.callId);
            gamePlayer.bindPlayer(onlinePlayer);
            onlinePlayer.setGamePlayer(gamePlayer);
        }

        if (!task.request.getIsReconnect()) {
            onlinePlayer.setStatus(PlayerStatusEnum.LOGGING);
        }
        onlinePlayer.setStatus(PlayerStatusEnum.PLAYING);
        onlinePlayer.setToken(task.request.getToken());

        onlinePlayer.dispatchEvent(PlayerEventType.PLAYER_LOGIN_COMPLETE);
        if (firstLogin) {
            onlinePlayer.dispatchEvent(PlayerEventType.PLAYER_FIRST_LOGIN);
        }
        onlinePlayer.dispatchEvent(PlayerEventType.PLAYER_LOGIN_IS_RECONNECT, task.request.getIsReconnect());

        Login.scLogin response = Login.scLogin.newBuilder()
                .setAccount(onlinePlayer.getAccount())
                .setPlayerId(onlinePlayer.getPlayerId())
                .setToken(onlinePlayer.getToken())
                .setGameServerId(ServerContext.getServerId())
                .setPlayerInfo(PlayerUtils.genPlayerInfo(onlinePlayer))
                .build();
        sendLoginResponse(task, onlinePlayer, response);
        onlinePlayer.statPlay();
    }

    private void sendErrorMsg(LoginTask task, ErrorMsg.ErrorCode errorCode) {
        ErrorMsg.scErrorCode errorMsg = ErrorMsg.scErrorCode.newBuilder()
                .setMsgId(task.packet.getCmd())
                .setErrorCode(errorCode)
                .build();
        if (task.packet.getSid() != 0) {
            sendWrappedGateResponse(task, task.packet.getGuid(), Cmd.CMD.SC_ErrorCode_VALUE, errorMsg);
            return;
        }
        task.session.sendClientMsg(Cmd.CMD.CS_ErrorCode_VALUE, 0, errorMsg);
    }

    private void sendLoginResponse(LoginTask task, Player onlinePlayer, Login.scLogin response) {
        if (task.packet.getSid() != 0) {
            sendWrappedGateResponse(task, onlinePlayer.getPlayerId(), Cmd.CMD.SC_Login_VALUE, response);
            LoggerDef.SystemLogger.info(
                    "LoginManager send wrapped login response, account={}, playerId={}, clientSid={}, callId={}",
                    onlinePlayer.getAccount(),
                    onlinePlayer.getPlayerId(),
                    task.packet.getSid(),
                    task.callId);
            return;
        }
        onlinePlayer.sendMsg(Cmd.CMD.SC_Login, response);
    }

    /**
     * 通过 Gate 登录时，Game 不生成客户端下行 seq，只返回 clientCmd/clientSid/data/callId。
     */
    private void sendWrappedGateResponse(
            LoginTask task,
            long guid,
            int clientCmd,
            com.google.protobuf.AbstractMessage response) {
        Server.scGate2GameRpcGameCall builder = Server.scGate2GameRpcGameCall.newBuilder()
                .setClientCmd(clientCmd)
                .setClientSid(task.packet.getSid())
                .setData(response.toByteString())
                .setCallId(task.callId)
                .build();
        MessagePacket packet = MessagePacketFactory.createMessagePacket(
                guid,
                Cmd.CMD.SC_Gate2GameRpcGameCall_VALUE,
                builder,
                0,
                0);
        task.session.addSendPacket(packet);
    }

    private boolean checkToken(String token, String account) {
        String cacheTokens = RedisUtils.get(RedisKeys.LOGIN_ACCOUNT_TOKEN_KEY.getKey(account));
        return cacheTokens != null && cacheTokens.equals(token);
    }
}
