package ly.logic.login;

import ly.GateClientManager;
import ly.LoggerDef;
import ly.net.GateClient;
import ly.net.GateConnectSession;
import ly.net.IGateController;
import ly.net.packet.C2SMessagePacket;
import ly.proto.Cmd;
import ly.proto.ErrorMsg;
import ly.proto.Login;
import ly.redis.RedisKeys;
import ly.redis.RedisUtils;
import ly.rpc.RpcNodeConnector;
import ly.rpc.RpcService;
import ly.rpc.RpcUtils;

import java.util.concurrent.TimeUnit;

public class GateLoginController implements IGateController {


    @Override
    public void registerHandlerRouter() {
        clientHandlerRegister(Cmd.CMD.CS_Login, Login.csLogin.class, this::handleLogin);
    }


    private void handleLogin(GateConnectSession session, C2SMessagePacket packet, Login.csLogin request) {
        Thread.ofVirtual().name("loginThread_" + request.getAccount()).start(() -> {
            try {
                //校验 token
                if (!checkToken(request.getToken(), request.getAccount())) {
                    LoggerDef.SystemLogger.error(String.format("GateLoginController Invalid token %s for account %s", request.getToken(), request.getAccount()));
                    sendClientErrorCode(session, packet.getCmd(), ErrorMsg.ErrorCode.param_error);
                    return;
                }

                //玩家在别的节点
                try {
                    RedisUtils.lock(RedisKeys.LOCK_LOGIN_ACCOUNT_ID_KEY.getKey(request.getAccount()), 3, TimeUnit.SECONDS);


                    RpcNodeConnector targetGameServer = RpcService.getInstance().getRpcNodeConnector(request.getGameServerId());
                    if (targetGameServer == null) {
                        LoggerDef.SystemLogger.error("GateLoginController getRpcNodeConnector failed, serverId={}", request.getGameServerId());
                        sendClientErrorCode(session, packet.getCmd(), ErrorMsg.ErrorCode.system_error);
                        return;
                    }

                    String gameServerId = RedisUtils.get(RedisKeys.ACCOUNT_GAME_SERVER_ID_KEY.getKey(request.getAccount()));
                    if (gameServerId != null) {
                        //玩家在别的节点
//                        通知别的节点玩家下线
                        Login.csLogout csLogout = Login.csLogout.newBuilder()
                                .setAccount(request.getAccount())
                                .setAccountId(request.getAccountId())
                                .setGameServerId(gameServerId).setLogoutReason("player login by other node server").build();
                        Login.scLogout logoutRes = RpcUtils.syncRequest(gameServerId, session.getGuid(), Cmd.CMD.CS_Logout_VALUE, csLogout);
                        if (logoutRes != null) {
                            LoggerDef.SystemLogger.info("player {} logout success, reason: {}", request.getAccount(), csLogout.getLogoutReason());
                        }
                    }


                    //bind game server node
                    RedisUtils.set(RedisKeys.LOCK_LOGIN_ACCOUNT_ID_KEY.getKey(request.getAccount()), request.getGameServerId());

                    //登录成功
                    GateClient client = new GateClient(session);
                    client.setAccount(request.getAccount());
                    client.setAccountId(request.getAccountId());
                    client.setToken(request.getToken());
                    client.setPlayerId(request.getPlayerId());
                    GateClientManager.getInstance().addClient(client);
                    //通知游戏服务器继续处理登录相关
                    Login.scLogin scLogin = RpcUtils.syncRequest(request.getGameServerId(), session.getGuid(), packet.getCmd(), request);
                    assert scLogin != null;
                    session.sendClientMsg(Cmd.CMD.SC_Login_VALUE, scLogin);

                } finally {
                    RedisUtils.unlock(RedisKeys.LOCK_LOGIN_ACCOUNT_ID_KEY.getKey(request.getAccount()));
                }
            } catch (Exception e) {
                e.printStackTrace();
                sendClientErrorCode(session, packet.getCmd(), ErrorMsg.ErrorCode.system_error);
            }
        });
    }

    private boolean checkToken(String token, String account) {
        String cacheTokens = RedisUtils.get(RedisKeys.LOGIN_ACCOUNT_TOKEN_KEY.getKey(account));
        if (cacheTokens == null) {
            return false;
        }
        return cacheTokens.equals(token);
    }

}
