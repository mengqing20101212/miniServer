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
                    final long callId = req.getCallId();
                    final byte[] data = req.getData().toByteArray();
                    ly.LoggerDef.NetLogger.info(
                            "[Gate2GameRpc] received CS_Gate2GameRpcGameCall, innerCmd={}, innerSeq={}, guid={}, sid={}, callId={}",
                            cmd, seq, guid, req.getSid(), callId);
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
                        sendErrorViaRpc(context.session(), guid, ErrorMsg.ErrorCode.SYSTEM_ERROR, seq, cmd, callId);
                        ly.LoggerDef.NetLogger.warn("[Gate2GameRpc] player banned, guid={}, innerCmd={}", guid, cmd);
                        return;
                    }
                    Player player = PlayerManager.getInstance().getOnlinePlayer(guid);
                    if (player == null) {
                        // GameServer 重启后内存在线态会丢失，可靠 RPC 重放时需要从 DB 懒加载玩家再继续处理。
                        player = PlayerManager.getInstance().getPlayerByDB(guid);
                        if (player == null) {
                            sendErrorViaRpc(context.session(), guid, ErrorMsg.ErrorCode.PLAYER_NOT_EXIST, seq, cmd, callId);
                            ly.LoggerDef.NetLogger.warn("[Gate2GameRpc] player not found in DB, guid={}, innerCmd={}, callId={}", guid, cmd, callId);
                            return;
                        }
                        ly.LoggerDef.NetLogger.info("[Gate2GameRpc] player loaded from DB for lazy init, guid={}", guid);
                        GamePlayer gamePlayer = new GamePlayer(context.session());
                        gamePlayer.setPlayerId(player.getPlayerId());
                        gamePlayer.bindPlayer(player);  // 绑定 Player，否则 tickWorkItem 会丢弃包
                        player.setGamePlayer(gamePlayer);
                        PlayerManager.getInstance().addOnlinePlayer(player);
                        player.statPlay();
                    }
                    // 设置 callId，回包时需要带上（必须在获取 player 之后，因为 DB 加载会创建新 GamePlayer）
                    player.getGamePlayer().setLastCallId(callId);
                    // Game 后续业务只看客户端原始包，seq/sid 保持 Gate 收到客户端时的值。
                    ly.LoggerDef.NetLogger.info("[Gate2GameRpc] dispatching to player queue, guid={}, innerCmd={}, innerSeq={}, callId={}", guid, cmd, seq, callId);
                    player.getGamePlayer().addPacket(clientPacket);
//                    AbstractMessage clientReq = ProtoMessageFactory.createProtoMessage(cmd, data);
//                    assert clientReq != null;
//                    GameHandlerRouteManager.execute(player, cmd, seq, req.getSid(), clientReq);
                });
    }

    /**
     * 通过 scGate2GameRpcGameCall 封装错误码回包，携带 callId。
     * 当 callId > 0 时（可靠 RPC 补发），错误码必须封装到 scGate2GameRpcGameCall 里，
     * Gate 侧 replay handler 才能通过 callId 匹配并删除 Redis 消息。
     * 当 callId == 0 时（普通转发），走原逻辑直接发 scErrorCode。
     */
    private void sendErrorViaRpc(GameConnectSession session, long guid, ErrorMsg.ErrorCode errorCode, int seq, int cmd, long callId) {
        if (callId == 0) {
            // 普通转发，走原逻辑
            session.sendErrorMsg(guid, errorCode, seq, cmd);
            return;
        }
        // 可靠 RPC 补发，封装到 scGate2GameRpcGameCall 里
        ErrorMsg.scErrorCode errorMsg = ErrorMsg.scErrorCode.newBuilder()
                .setErrorCode(errorCode)
                .setMsgId(cmd)
                .build();
        Server.scGate2GameRpcGameCall.Builder builder = Server.scGate2GameRpcGameCall.newBuilder();
        builder.setCmd(Cmd.CMD.SC_ErrorCode_VALUE);
        builder.setSid(0);
        builder.setSeq(seq + 1);
        builder.setData(errorMsg.toByteString());
        builder.setCallId(callId);
        ly.net.packet.AbstractMessagePacket packet =
                ly.net.packet.MessagePacketFactory.createAbstractMessagePacket(
                        guid, Cmd.CMD.SC_Gate2GameRpcGameCall_VALUE, builder.build(), seq + 1, 0);
        session.addSendPacket(packet);
    }

}
