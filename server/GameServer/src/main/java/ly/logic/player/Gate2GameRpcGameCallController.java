package ly.logic.player;

import com.google.protobuf.AbstractMessage;
import ly.ProtoMessageFactory;
import ly.ServerContext;
import ly.net.GameConnectSession;
import ly.net.GameHandlerRouteManager;
import ly.net.IGameController;
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
                    Player player = PlayerManager.getInstance().getOnlinePlayer(guid);
                    if (player == null) {
                        context.session().sendErrorMsg(guid, ErrorMsg.ErrorCode.PLAYER_NOT_EXIST, seq, cmd);
                        return;
                    }
                    AbstractMessage clientReq = ProtoMessageFactory.createProtoMessage(cmd, data);
                    assert clientReq != null;
                    GameHandlerRouteManager.execute(player, cmd, seq, req.getSid(), clientReq);
                });
    }

}
