package ly.logic.login;

import ly.LoggerDef;
import ly.logic.player.Player;
import ly.logic.player.PlayerManager;
import ly.logic.player.PlayerStatusEnum;
import ly.logic.player.event.PlayerEventType;
import ly.proto.Cmd;
import ly.proto.ErrorMsg;
import ly.redis.RedisKeys;
import ly.redis.RedisUtils;

import java.util.concurrent.ArrayBlockingQueue;

public class LoginManager {
    private static LoginManager instance = new LoginManager();
    ArrayBlockingQueue<LoginTask> loginTasks = new ArrayBlockingQueue<>(100);

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
                    LoggerDef.SystemLogger.info(String.format("LoginManager handle login %s cost %d ms", task.request.toString(), endTime - startTime));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    LoggerDef.SystemLogger.error(String.format("LoginManager handler login error %s", e));
                }
            }
        }).setName("LoginManager");
    }

    public void addLoginTask(LoginTask task) {
        loginTasks.add(task);
        LoggerDef.SystemLogger.info(String.format("LoginManager add login task account:%s token:%s", task.request.getAccount(), task.request.getToken()));
    }


    private void handleLogin(LoginTask task) {
        final long playerId = task.request.getPlayerId();
        if (!checkToken(task.request.getToken(), task.request.getAccount())) {
            LoggerDef.SystemLogger.error(String.format("LoginManager handle login token error %s account:%s", task.request.getToken(), task.request.getAccount()));
            sendErrorMsg(task, ErrorMsg.ErrorCode.system_error);
            return;
        }
        Player onlinePlayer = PlayerManager.getInstance().getOnlinePlayer(playerId);
        if (onlinePlayer == null) {
            onlinePlayer = PlayerManager.getInstance().getPlayerByDB(playerId);
            if (onlinePlayer == null) {
                boolean lock = RedisUtils.lock(RedisKeys.LOCK_CREATE_PLAYER_NAME_KEY.getKey(task.request.getPlayerName()));
                if (lock) {
                    try {
                        onlinePlayer = PlayerManager.getInstance().createNewPlayer(task.request);
                    } finally {
                        RedisUtils.unlock(RedisKeys.LOCK_CREATE_PLAYER_NAME_KEY.getKey(task.request.getPlayerName()));
                    }
                } else {
                    LoggerDef.SystemLogger.info(String.format("LoginManager handle login %s lock fail create playerName", task.request.toString()));
                    sendErrorMsg(task, ErrorMsg.ErrorCode.player_name_exists);
                    return;
                }
            }
            PlayerManager.getInstance().addOnlinePlayer(onlinePlayer);
        }
        if (!task.request.getIsReconnect()) {//不是重连
            onlinePlayer.setStatus(PlayerStatusEnum.LOGGING);
        }
        onlinePlayer.setStatus(PlayerStatusEnum.PLAYING);
        onlinePlayer.dispatchEvent(PlayerEventType.PLAYER_LOGIN_COMPLETE);
        //TODO  first login today
        onlinePlayer.dispatchEvent(PlayerEventType.PLAYER_LOGIN_IS_RECONNECT, task.request.getIsReconnect());


    }

    private void sendErrorMsg(LoginTask task, ErrorMsg.ErrorCode errorCode) {
        ErrorMsg.csErrorCode.Builder req = ErrorMsg.csErrorCode.newBuilder();
        req.setMsgId(task.packet.getCmd());
        req.setErrorCode(errorCode);
        task.session.sendClientMsg(Cmd.CMD.CS_ErrorCode_VALUE, task.packet.getSeq(), 0, req.build());
    }

    private boolean checkToken(String token, String account) {
        String cacheTokens = RedisUtils.get(RedisKeys.LOGIN_ACCOUNT_TOKEN_KEY.getKey(account));
        if (cacheTokens == null) {
            return false;
        }
        return cacheTokens.equals(token);
    }

}
