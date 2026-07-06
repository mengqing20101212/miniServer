package ly.logic.player;

import ly.LoggerDef;
import ly.ProtoMessageFactory;
import ly.logic.login.LoginManager;
import ly.logic.login.LoginTask;
import ly.net.GameConnectSession;
import ly.net.GamePlayer;
import ly.net.IGameController;
import ly.net.packet.MessagePacket;
import ly.net.packet.MessagePacketFactory;
import ly.proto.Cmd;
import ly.proto.ErrorMsg;
import ly.proto.Login;
import ly.proto.Server;
import ly.security.SecurityBanService;
import ly.utils.CommonUtils;

public class Gate2GameRpcGameCallController implements IGameController {

        @Override
        public void registerHandlerRouter() {
                register(
                                Cmd.CMD.CS_Gate2GameRpcGameCall,
                                GameConnectSession.class,
                                MessagePacket.class,
                                Server.csGate2GameRpcGameCall.class,
                                (context, req) -> {
                                        final int clientCmd = req.getClientCmd();
                                        final int clientReqSeq = req.getClientReqSeq();
                                        final int clientSid = req.getClientSid();
                                        final long guid = req.getGuid();
                                        final long callId = req.getCallId();
                                        final byte[] data = req.getData().toByteArray();
                                        ly.LoggerDef.NetLogger.info(
                                                        "[Gate2GameRpc] received, clientCmd={}, clientReqSeq={}, guid={}, clientSid={}, callId={}",
                                                        clientCmd, clientReqSeq, guid, clientSid, callId);
                                        MessagePacket clientPacket = new MessagePacket(guid, clientCmd,
                                                        clientSid, clientReqSeq, data);
                                        if (clientCmd == Cmd.CMD.CS_Login_VALUE) {
                                                handleLoginPacket(context.session(), clientPacket, callId);
                                                return;
                                        }

                                        if (SecurityBanService.getInstance().isPlayerBanned(guid)) {
                                                SecurityBanService.getInstance()
                                                                .writeRejectEvent(
                                                                                null,
                                                                                null,
                                                                                null,
                                                                                guid,
                                                                                clientCmd,
                                                                                clientSid,
                                                                                clientReqSeq,
                                                                                "Game 角色封禁");
                                                sendErrorCode(
                                                                context.session(),
                                                                guid,
                                                                clientCmd,
                                                                clientSid,
                                                                callId,
                                                                ErrorMsg.ErrorCode.SYSTEM_ERROR);
                                                ly.LoggerDef.NetLogger.warn(
                                                                "[Gate2GameRpc] player banned, guid={}, clientCmd={}",
                                                                guid, clientCmd);
                                                return;
                                        }

                                        Player player = PlayerManager.getInstance().getOnlinePlayer(guid);
                                        if (player == null) {
                                                // 离线玩家 lazy load 可能访问 DB，投递到独立协程处理，避免阻塞 RPC 入站线程。
                                                GameRpcPlayerLoadManager.getInstance()
                                                                .addTask(new GameRpcPlayerTask(
                                                                                context.session(),
                                                                                clientPacket,
                                                                                callId));
                                                return;
                                        }
                                        player.getGamePlayer().setLastCallId(callId);
                                        // ly.LoggerDef.NetLogger.info(
                                        // "[Gate2GameRpc] dispatching to player queue, guid={}, clientCmd={},
                                        // clientReqSeq={}, callId={}",
                                        // guid,
                                        // clientCmd,
                                        // clientReqSeq,
                                        // callId);
                                        player.getGamePlayer().addPacket(clientPacket);
                                });
        }

        private void handleLoginPacket(GameConnectSession session, MessagePacket clientPacket, long callId) {
                Login.csLogin request = (Login.csLogin) ProtoMessageFactory.createProtoMessage(Cmd.CMD.CS_Login_VALUE,
                                clientPacket.getData());
                if (request == null) {
                        sendErrorCode(
                                        session,
                                        clientPacket.getGuid(),
                                        clientPacket.getCmd(),
                                        clientPacket.getSid(),
                                        callId,
                                        ErrorMsg.ErrorCode.PARAM_ERROR);
                        return;
                }
                LoggerDef.LogProto(String.format(
                                "receive clientCmd=%s|clientReqSeq=%d|guid=%d|clientSid=%d|callId=%d|%s",
                                Cmd.CMD.forNumber(clientPacket.getCmd()).name(), clientPacket.getSeq(),
                                clientPacket.getGuid(),
                                clientPacket.getSid(), callId, CommonUtils.logProto(request)));
                // 登录涉及 DB/Redis，同步 IO 放到 LoginManager 登录协程里，避免阻塞 RPC 入站处理线程。
                LoginManager.getInstance().addLoginTask(new LoginTask(session, clientPacket, request, callId));
        }

        /**
         * 玩家不存在、封禁或登录解析失败时直接构造 Gate 转发响应。
         */
        private void sendErrorCode(
                        GameConnectSession session,
                        long guid,
                        int clientCmd,
                        int clientSid,
                        long callId,
                        ErrorMsg.ErrorCode errorCode) {
                ErrorMsg.scErrorCode errorMsg = ErrorMsg.scErrorCode.newBuilder()
                                .setErrorCode(errorCode)
                                .setMsgId(clientCmd)
                                .build();
                if (callId > 0 || clientSid != 0) {
                        Server.scGate2GameRpcGameCall builder = Server.scGate2GameRpcGameCall.newBuilder()
                                        .setClientCmd(Cmd.CMD.SC_ErrorCode_VALUE)
                                        .setClientSid(clientSid)
                                        .setData(errorMsg.toByteString())
                                        .setCallId(callId)
                                        .build();
                        MessagePacket packet = MessagePacketFactory.createMessagePacket(
                                        guid,
                                        Cmd.CMD.SC_Gate2GameRpcGameCall_VALUE,
                                        builder,
                                        0,
                                        0);
                        session.addSendPacket(packet);
                } else {
                        session.sendErrorMsg(guid, errorCode, clientCmd);
                }
        }
}
