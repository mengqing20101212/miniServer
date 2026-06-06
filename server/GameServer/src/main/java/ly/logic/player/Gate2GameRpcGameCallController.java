package ly.logic.player;

import ly.net.GameConnectSession;
import ly.net.GamePlayer;
import ly.net.HandlerRouterManager;
import ly.net.IGameController;
import ly.net.packet.AbstractMessagePacket;
import ly.net.packet.MessagePacketFactory;
import ly.proto.Cmd;
import ly.proto.ErrorMsg;
import ly.proto.Server;
import ly.security.SecurityBanService;

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
                    AbstractMessagePacket clientPacket = new AbstractMessagePacket(guid, cmd, req.getSid(), seq, data);
                    if (cmd == Cmd.CMD.CS_Login_VALUE) {
                        HandlerRouterManager.execute(context.session(), clientPacket);
                        return;
                    }
                    if (SecurityBanService.getInstance().isPlayerBanned(guid)) {
                        SecurityBanService.getInstance()
                                .writeRejectEvent(null, null, null, guid, cmd, req.getSid(), seq, "Game 角色封禁");
                        sendErrorCode(context.session(), guid, seq, cmd, req.getSid(), callId, ErrorMsg.ErrorCode.SYSTEM_ERROR);
                        ly.LoggerDef.NetLogger.warn("[Gate2GameRpc] player banned, guid={}, innerCmd={}", guid, cmd);
                        return;
                    }
                    Player player = PlayerManager.getInstance().getOnlinePlayer(guid);
                    if (player == null) {
                        player = PlayerManager.getInstance().getPlayerByDB(guid);
                        if (player == null) {
                            sendErrorCode(context.session(), guid, seq, cmd, req.getSid(), callId, ErrorMsg.ErrorCode.PLAYER_NOT_EXIST);
                            ly.LoggerDef.NetLogger.warn("[Gate2GameRpc] player not found in DB, guid={}, innerCmd={}, callId={}", guid, cmd, callId);
                            return;
                        }
                        ly.LoggerDef.NetLogger.info("[Gate2GameRpc] player loaded from DB for lazy init, guid={}", guid);
                        GamePlayer gamePlayer = new GamePlayer(context.session());
                        gamePlayer.setPlayerId(player.getPlayerId());
                        gamePlayer.bindPlayer(player);
                        player.setGamePlayer(gamePlayer);
                        PlayerManager.getInstance().addOnlinePlayer(player);
                        player.statPlay();
                    }
                    player.getGamePlayer().setLastCallId(callId);
                    ly.LoggerDef.NetLogger.info("[Gate2GameRpc] dispatching to player queue, guid={}, innerCmd={}, innerSeq={}, callId={}", guid, cmd, seq, callId);
                    player.getGamePlayer().addPacket(clientPacket);
                });
    }

    /**
     * player 不存在时的错误码发送（内联，参数无法从 GamePlayer 取）。
     */
    private void sendErrorCode(GameConnectSession session, long guid, int seq, int cmd, int sid, long callId, ErrorMsg.ErrorCode errorCode) {
        ErrorMsg.scErrorCode errorMsg = ErrorMsg.scErrorCode.newBuilder()
                .setErrorCode(errorCode)
                .setMsgId(cmd)
                .build();
        if (callId > 0) {
            Server.scGate2GameRpcGameCall.Builder builder = Server.scGate2GameRpcGameCall.newBuilder();
            builder.setCmd(Cmd.CMD.SC_ErrorCode_VALUE);
            builder.setSid(sid);
            builder.setSeq(seq + 1);
            builder.setData(errorMsg.toByteString());
            builder.setCallId(callId);
            AbstractMessagePacket packet = MessagePacketFactory.createAbstractMessagePacket(
                    guid, Cmd.CMD.SC_Gate2GameRpcGameCall_VALUE, builder.build(), seq + 1, sid);
            session.addSendPacket(packet);
        } else {
            session.sendErrorMsg(guid, errorCode, seq, cmd);
        }
    }
}
