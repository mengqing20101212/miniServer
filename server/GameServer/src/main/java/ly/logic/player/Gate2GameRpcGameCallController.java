package ly.logic.player;

import ly.ProtoMessageFactory;
import ly.net.GameConnectSession;
import ly.net.HandlerRouterManager;
import ly.net.IGameController;
import ly.net.packet.MessagePacketFactory;
import ly.net.packet.AbstractMessagePacket;
import ly.proto.Cmd;
import ly.proto.ErrorMsg;
import ly.proto.Server;

/**
 * 游戏服协议控制器，负责注册并处理对应业务消息。
 */
public class Gate2GameRpcGameCallController implements IGameController {

    @Override
    public void registerHandlerRouter() {
        register(Cmd.CMD.CS_Gate2GameRpcGameCall, GameConnectSession.class, AbstractMessagePacket.class, Server.csGate2GameRpcGameCall.class,
                (context, req) -> {
                    final int cmd = req.getCmd();
                    final int seq = req.getSeq();
                    final long guid = req.getGuid();
                    final byte[] data = req.getData().toByteArray();
                    if (cmd == Cmd.CMD.CS_Login_VALUE) {// 登录
                        AbstractMessagePacket loginPack = MessagePacketFactory.createAbstractMessagePacket(guid, cmd, ProtoMessageFactory.createProtoMessage(cmd, data), seq, req.getSid());
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
