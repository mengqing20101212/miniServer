package ly.logic.login;

import ly.GateClientManager;
import ly.LoggerDef;
import ly.ServerContext;
import ly.net.GateClient;
import ly.net.GateConnectSession;
import ly.net.HandlerContext;
import ly.net.IGateController;
import ly.net.packet.S2SMessagePacket;
import ly.proto.Cmd;
import ly.proto.Login;
import ly.redis.RedisKeys;
import ly.redis.RedisUtils;

public class GateLogoutController implements IGateController {

    static {
        ServerContext.addController(new GateLogoutController());
    }

    @Override
    public void registerHandlerRouter() {
        register(Cmd.CMD.SC_Logout, GateConnectSession.class, S2SMessagePacket.class, Login.scLogout.class, this::logout);
    }

    public void logout(HandlerContext<GateConnectSession, S2SMessagePacket> context, Login.scLogout request) {
        long guid = request.getAccountId();
        GateClient gateClient = GateClientManager.getInstance().getClient(guid);
        if (gateClient != null) {
            Thread.ofVirtual().start(() -> {
                gateClient.sendPacketToClient(context.packet());
                RedisUtils.del(RedisKeys.ACCOUNT_GAME_SERVER_ID_KEY.getKey(request.getAccount()));
                GateClientManager.getInstance().removeClient(guid);
                gateClient.closeConnection();
                LoggerDef.SystemLogger.info(String.format("Logout successfully! scLogout:%s", request));
            });
        }

    }
}
