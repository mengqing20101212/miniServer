package ly.logic.player;

import ly.net.GameConnectSession;
import ly.net.GamePlayer;
import ly.net.HandlerRouterManager;
import ly.net.IGameController;
import ly.net.packet.AbstractMessagePacket;
import ly.proto.Cmd;
import ly.proto.ErrorMsg;
import ly.proto.Server;
import ly.security.SecurityBanService;

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
                    // Gate 已经把客户端原始包的 cmd/seq/sid/data 放进 RPC 载荷，这里只还原包头。
                    // 具体业务 proto 由后续路由层按 cmd 反序列化，避免工厂漏映射时在转发入口丢包。
                    AbstractMessagePacket clientPacket = new AbstractMessagePacket(guid, cmd, req.getSid(), seq, data);
                    if (cmd == Cmd.CMD.CS_Login_VALUE) {// 登录
                        HandlerRouterManager.execute(context.session(), clientPacket);
                        return;
                    }
                    if (SecurityBanService.getInstance().isPlayerBanned(guid)) {
                        SecurityBanService.getInstance()
                                .writeRejectEvent(null, null, null, guid, cmd, req.getSid(), seq, "Game 角色封禁");
                        context.session().sendErrorMsg(guid, ErrorMsg.ErrorCode.SYSTEM_ERROR, seq, cmd);
                        return;
                    }
                    Player player = PlayerManager.getInstance().getOnlinePlayer(guid);
                    if (player == null) {
                        // GameServer 重启后内存在线态会丢失，可靠 RPC 重放时需要从 DB 懒加载玩家再继续处理。
                        player = PlayerManager.getInstance().getPlayerByDB(guid);
                        if (player == null) {
                            context.session().sendErrorMsg(guid, ErrorMsg.ErrorCode.PLAYER_NOT_EXIST, seq, cmd);
                            return;
                        }
                        GamePlayer gamePlayer = new GamePlayer(context.session());
                        gamePlayer.setPlayerId(player.getPlayerId());
                        gamePlayer.setLastSeq(seq);
                        gamePlayer.setLastClientCmd(cmd);
                        gamePlayer.setLastSid(req.getSid());
                        player.setGamePlayer(gamePlayer);
                        PlayerManager.getInstance().addOnlinePlayer(player);
                        player.statPlay();
                    }
                    // Game 后续业务只看客户端原始包，seq/sid 保持 Gate 收到客户端时的值。
                    player.getGamePlayer().addPacket(clientPacket);
//                    AbstractMessage clientReq = ProtoMessageFactory.createProtoMessage(cmd, data);
//                    assert clientReq != null;
//                    GameHandlerRouteManager.execute(player, cmd, seq, req.getSid(), clientReq);
                });
    }

}
