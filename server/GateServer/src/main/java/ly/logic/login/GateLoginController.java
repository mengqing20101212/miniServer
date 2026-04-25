package ly.logic.login;

import java.util.concurrent.TimeUnit;
import ly.GateClientManager;
import ly.LoggerDef;
import ly.ProtoMessageFactory;
import ly.ServerContext;
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
import ly.rpc.RpcNodeConnector;
import ly.rpc.RpcService;
import ly.rpc.RpcUtils;

public class GateLoginController implements IGateController {
    static {
        ServerContext.addController(new GateLoginController());
    }

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
                    sendClientErrorCode(session, packet.getCmd(), ErrorMsg.ErrorCode.PARAM_ERROR);
                    return;
                }

                try {
                    RedisUtils.lock(
                            RedisKeys.LOCK_LOGIN_ACCOUNT_ID_KEY.getKey(request.getAccount()),
                            10,
                            TimeUnit.SECONDS);

                    RpcNodeConnector targetGameServer =
                            RpcService.getInstance().getRpcNodeConnector(request.getGameServerId());
                    if (targetGameServer == null) {
                        LoggerDef.SystemLogger.error(
                                "GateLoginController getRpcNodeConnector failed, serverId={}",
                                request.getGameServerId());
                        sendClientErrorCode(session, packet.getCmd(), ErrorMsg.ErrorCode.SYSTEM_ERROR);
                        return;
                    }

                    String oldGameServerId =
                            RedisUtils.get(
                                    RedisKeys.ACCOUNT_GAME_SERVER_ID_KEY.getKey(request.getAccount()));
                    if (oldGameServerId != null && !oldGameServerId.equals(request.getGameServerId())) {
                        Login.csLogout csLogout =
                                Login.csLogout.newBuilder()
                                        .setAccount(request.getAccount())
                                        .setAccountId(request.getAccountId())
                                        .setGameServerId(oldGameServerId)
                                        .setLogoutReason("player login by other node server")
                                        .build();
                        Login.scLogout logoutRes =
                                RpcUtils.syncRequest(
                                        oldGameServerId,
                                        session.getGuid(),
                                        Cmd.CMD.CS_Logout_VALUE,
                                        csLogout);
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

                    Server.scGate2GameRpcGameCall resp = client.sendPacketToGameServerSync(packet);
                    if (resp == null) {
                        LoggerDef.SystemLogger.error(
                                "GateLoginController game login rpc timeout, account={}, gameServerId={}",
                                request.getAccount(),
                                request.getGameServerId());
                        GateClientManager.getInstance().removeClient(request.getAccountId());
                        sendClientErrorCode(session, packet.getCmd(), ErrorMsg.ErrorCode.SYSTEM_ERROR);
                        return;
                    }

                    Login.scLogin scLogin =
                            (Login.scLogin)
                                    ProtoMessageFactory.createProtoMessage(
                                            Cmd.CMD.SC_Login_VALUE, resp.getData().toByteArray());
                    if (scLogin == null) {
                        LoggerDef.SystemLogger.error(
                                "GateLoginController parse scLogin failed, account={}, gameServerId={}",
                                request.getAccount(),
                                request.getGameServerId());
                        GateClientManager.getInstance().removeClient(request.getAccountId());
                        sendClientErrorCode(session, packet.getCmd(), ErrorMsg.ErrorCode.SYSTEM_ERROR);
                        return;
                    }

                    session.sendClientMsg(Cmd.CMD.SC_Login_VALUE, scLogin);
                } finally {
                    RedisUtils.unlock(
                            RedisKeys.LOCK_LOGIN_ACCOUNT_ID_KEY.getKey(request.getAccount()));
                }
            } catch (Exception e) {
                LoggerDef.SystemLogger.error(
                        "GateLoginController handleLogin failed, account={}",
                        request.getAccount(),
                        e);
                sendClientErrorCode(session, packet.getCmd(), ErrorMsg.ErrorCode.SYSTEM_ERROR);
            }
        });
    }

    private boolean checkToken(String token, String account) {
        String cacheTokens = RedisUtils.get(RedisKeys.LOGIN_ACCOUNT_TOKEN_KEY.getKey(account));
        return cacheTokens != null && cacheTokens.equals(token);
    }
}
