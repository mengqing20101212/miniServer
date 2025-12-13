package ly.logic.player;

import ly.ProtoMessageFactory;
import ly.ServerContext;
import ly.net.GameConnectSession;
import ly.net.HandlerRouterManager;
import ly.net.IGameController;
import ly.net.packet.MessagePacketFactory;
import ly.net.packet.S2SMessagePacket;
import ly.proto.Cmd;
import ly.proto.ErrorMsg;
import ly.proto.Server;

public class Gate2GameRpcGameCallController implements IGameController {
    static {
        ServerContext.addController(new Gate2GameRpcGameCallController());
    }

    @Override
    public void registerHandlerRouter() {
        register(Cmd.CMD.CS_Gate2GameRpcGameCall, GameConnectSession.class, S2SMessagePacket.class, Server.csGate2GameRpcGameCall.class,
                (context, req) -> {
                    final int cmd = req.getCmd();
                    final int seq = req.getSeq();
                    final long guid = req.getGuid();
                    final byte[] data = req.getData().toByteArray();
                    if (cmd == Cmd.CMD.CS_Login_VALUE) {// 登录
                        S2SMessagePacket loginPack = MessagePacketFactory.createS2SMessagePacket(guid, cmd, ProtoMessageFactory.createProtoMessage(cmd, data), seq, req.getSid());
                        HandlerRouterManager.execute(context.session(), loginPack);
                        return;
                    }
                    Player player = PlayerManager.getInstance().getOnlinePlayer(guid);
                    if (player == null) {
                        context.session().sendErrorMsg(guid, ErrorMsg.ErrorCode.PLAYER_NOT_EXIST, seq, cmd);
                        return;
                    }
                    player.getGamePlayer().addPacket(context.packet());
//                    AbstractMessage clientReq = ProtoMessageFactory.createProtoMessage(cmd, data);
//                    assert clientReq != null;
//                    GameHandlerRouteManager.execute(player, cmd, seq, req.getSid(), clientReq);
                });
    }

}
