package ly.logic.login;

import ly.GameClientManager;
import ly.LoggerDef;
import ly.net.GameClient;
import ly.net.GameConnectSession;
import ly.net.IGameController;
import ly.net.packet.S2SMessagePacket;
import ly.proto.Cmd;
import ly.proto.Login;
import ly.redis.RedisKeys;
import ly.redis.RedisUtils;

public class GameLogoutController implements IGameController {
    @Override
    public void registerHandlerRouter() {
        register(Cmd.CMD.SC_Logout, GameConnectSession.class, S2SMessagePacket.class, Login.scLogout.class, this::logout);
    }

    private void logout(GameConnectSession session, S2SMessagePacket packet, Login.scLogout request) {
        long guid = request.getAccountId();
        GameClient gateClient = GameClientManager.getInstance().getClient(guid);
        if (gateClient != null) {
            Thread.ofVirtual().start(() -> {
                gateClient.sendPacketToClient(packet);
                RedisUtils.del(RedisKeys.ACCOUNT_GAME_SERVER_ID_KEY.getKey(request.getAccount()));
                GameClientManager.getInstance().removeClient(guid);
                gateClient.closeConnection();
                LoggerDef.SystemLogger.info(String.format("Logout successfully! scLogout:%s", request));
            });
        }

    }
}
