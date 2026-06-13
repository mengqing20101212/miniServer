package ly.logic.login;

import java.util.concurrent.TimeUnit;

import ly.GateClientManager;
import ly.LoggerDef;
import ly.ProtoMessageFactory;
import ly.net.GateClient;
import ly.net.GateConnectSession;
import ly.net.HandlerContext;
import ly.net.IGateController;
import ly.net.packet.AbstractMessagePacket;
import ly.proto.Cmd;
import ly.proto.ErrorMsg;
import ly.proto.Login;
import ly.proto.Server;
import ly.redis.RedisKeys;
import ly.redis.RedisUtils;
import ly.rpc.RpcFailSavePolicy;
import ly.rpc.RpcNodeConnector;
import ly.rpc.RpcService;
import ly.rpc.RpcUtils;

/**
 * Gate 侧登录控制器，负责校验 token、绑定 GateClient，并把登录包转发到目标 GameServer。
 */
public class GateLoginController implements IGateController {

    @Override
    public void registerHandlerRouter() {
        clientHandlerRegister(Cmd.CMD.CS_Login, Login.csLogin.class, this::handleLogin);
    }

    public void handleLogin(
            HandlerContext<GateConnectSession, AbstractMessagePacket> context, Login.csLogin request) {
        GateConnectSession session = context.session();
        AbstractMessagePacket packet = context.packet();

        Thread.ofVirtual().name("loginThread_" + request.getAccount()).start(() -> {
            try {
                if (!checkToken(request.getToken(), request.getAccount())) {
                    LoggerDef.SystemLogger.error(
                            "GateLoginController invalid token {} for account {}",
                            request.getToken(),
                            request.getAccount());
                    sendClientErrorCode(context, ErrorMsg.ErrorCode.PARAM_ERROR);
                    return;
                }

                try {
                    RedisUtils.lock(
                            RedisKeys.LOCK_LOGIN_ACCOUNT_ID_KEY.getKey(request.getAccount()),
                            10,
                            TimeUnit.SECONDS);

                    RpcNodeConnector targetGameServer = RpcService.getInstance()
                            .getRpcNodeConnector(request.getGameServerId());
                    if (targetGameServer == null) {
                        LoggerDef.SystemLogger.error(
                                "GateLoginController getRpcNodeConnector failed, serverId={}",
                                request.getGameServerId());
                        sendClientErrorCode(context, ErrorMsg.ErrorCode.SYSTEM_ERROR);
                        return;
                    }

                    String oldGameServerId = RedisUtils.get(
                            RedisKeys.ACCOUNT_GAME_SERVER_ID_KEY.getKey(request.getAccount()));
                    if (oldGameServerId != null && !oldGameServerId.equals(request.getGameServerId())) {
                        Login.csLogout csLogout = Login.csLogout.newBuilder()
                                .setAccount(request.getAccount())
                                .setAccountId(request.getAccountId())
                                .setGameServerId(oldGameServerId)
                                .setLogoutReason("player login by other node server")
                                .build();
                        // 旧 Game 下线是状态修正类 RPC，超时也要保存，避免玩家同时挂在两个 GameServer。
                        Login.scLogout logoutRes = RpcUtils.syncRequestOrSaveOnFail(
                                oldGameServerId,
                                session.getGuid(),
                                Cmd.CMD.CS_Logout_VALUE,
                                csLogout,
                                RpcFailSavePolicy.SEND_FAILED_OR_TIMEOUT);
                        if (logoutRes != null) {
                            LoggerDef.SystemLogger.info(
                                    "player {} logout success, reason: {}",
                                    request.getAccount(),
                                    csLogout.getLogoutReason());
                        }
                    }

                    RedisUtils.set(
                            RedisKeys.ACCOUNT_GAME_SERVER_ID_KEY.getKey(request.getAccount()),
                            request.getGameServerId());

                    GateClient client = new GateClient(session);
                    client.setAccount(request.getAccount());
                    client.setAccountId(request.getAccountId());
                    client.setToken(request.getToken());
                    client.setPlayerId(request.getPlayerId());
                    client.setGameServerId(request.getGameServerId());
                    GateClientManager.getInstance().addClient(client);

                    // 登录主流程仍然同步等待 Game 回包；失败后不能异步补发，避免客户端已失败但 Game 后续登录成功。
                    Server.scGate2GameRpcGameCall resp = client.sendPacketToGameServerSync(packet);
                    if (resp == null) {
                        LoggerDef.SystemLogger.error(
                                "GateLoginController game login rpc timeout, account={}, gameServerId={}",
                                request.getAccount(),
                                request.getGameServerId());
                        GateClientManager.getInstance().removeClient(request.getAccountId());
                        sendClientErrorCode(context, ErrorMsg.ErrorCode.SYSTEM_ERROR);
                        return;
                    }

                    Login.scLogin scLogin = (Login.scLogin) ProtoMessageFactory.createProtoMessage(
                            Cmd.CMD.SC_Login_VALUE, resp.getData().toByteArray());
                    if (scLogin == null) {
                        LoggerDef.SystemLogger.error(
                                "GateLoginController parse scLogin failed, account={}, gameServerId={}",
                                request.getAccount(),
                                request.getGameServerId());
                        GateClientManager.getInstance().removeClient(request.getAccountId());
                        sendClientErrorCode(context, ErrorMsg.ErrorCode.SYSTEM_ERROR);
                        return;
                    }

                    // 登录响应解析成功后仍走统一转发入口，由 Gate 分配客户端下行 seq。
                    client.sendGameResponseToClient(resp);
                } finally {
                    RedisUtils.unlock(
                            RedisKeys.LOCK_LOGIN_ACCOUNT_ID_KEY.getKey(request.getAccount()));
                }
            } catch (Exception e) {
                LoggerDef.SystemLogger.error(
                        "GateLoginController handleLogin failed, account={}",
                        request.getAccount(),
                        e);
                sendClientErrorCode(context, ErrorMsg.ErrorCode.SYSTEM_ERROR);
            }
        });
    }

    private boolean checkToken(String token, String account) {
        String cacheTokens = RedisUtils.get(RedisKeys.LOGIN_ACCOUNT_TOKEN_KEY.getKey(account));
        return cacheTokens != null && cacheTokens.equals(token);
    }
}
