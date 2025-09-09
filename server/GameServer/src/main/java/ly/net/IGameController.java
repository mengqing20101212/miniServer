package ly.net;

import com.google.protobuf.AbstractMessage;
import ly.net.packet.C2SMessagePacket;
import ly.proto.Cmd;
import ly.proto.ErrorMsg;

public interface IGameController extends IController {
    default <R extends AbstractMessage> void clientHandlerRegister(Cmd.CMD cmd, Class<? extends AbstractMessage> requestType, IHandlerRouter<GameConnectSession, C2SMessagePacket, R> handler) {
        register(cmd, GameConnectSession.class, C2SMessagePacket.class, requestType, (IHandlerRouter) handler);
    }

    default void sendClientErrorCode(GameConnectSession session, int msgId, ErrorMsg.ErrorCode errorCode) {
        ErrorMsg.csErrorCode.Builder resp = ErrorMsg.csErrorCode.newBuilder();
        resp.setMsgId(msgId);
        resp.setErrorCode(errorCode);
        session.sendClientMsg(Cmd.CMD.CS_ErrorCode_VALUE, resp.build());
    }

}
