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
                    AbstractMessagePacket clientPacket =
                            MessagePacketFactory.createAbstractMessagePacket(
                                    guid,
                                    cmd,
                                    ProtoMessageFactory.createProtoMessage(cmd, data),
                                    seq,
                                    req.getSid());
                    if (cmd == Cmd.CMD.CS_Login_VALUE) {// 登录
                        HandlerRouterManager.execute(context.session(), clientPacket);
                        return;
                    }
                    Player player = PlayerManager.getInstance().getOnlinePlayer(guid);
                    if (player == null) {
                        context.session().sendErrorMsg(guid, ErrorMsg.ErrorCode.PLAYER_NOT_EXIST, seq, cmd);
                        return;
                    }
                    // Game 后续业务只看客户端原始包，seq/sid 保持 Gate 收到客户端时的值。
                    player.getGamePlayer().addPacket(clientPacket);
//                    AbstractMessage clientReq = ProtoMessageFactory.createProtoMessage(cmd, data);
//                    assert clientReq != null;
//                    GameHandlerRouteManager.execute(player, cmd, seq, req.getSid(), clientReq);
                });
    }

}
