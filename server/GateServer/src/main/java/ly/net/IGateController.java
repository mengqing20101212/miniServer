package ly.net;

import com.google.protobuf.AbstractMessage;
import ly.net.packet.AbstractMessagePacket;
import ly.proto.Cmd;
import ly.proto.ErrorMsg;

/**
 * 网关侧协议控制器，处理客户端登录/登出并转发到后端游戏服。
 */
public interface IGateController extends IController {
    default <R extends AbstractMessage> void clientHandlerRegister(Cmd.CMD cmd, Class<? extends AbstractMessage> requestType, IHandlerRouter<GateConnectSession, AbstractMessagePacket, R> handler) {
        register(cmd, GateConnectSession.class, AbstractMessagePacket.class, requestType, (IHandlerRouter) handler);
    }

    default void sendClientErrorCode(GateConnectSession session, int msgId, ErrorMsg.ErrorCode errorCode) {
        ErrorMsg.scErrorCode.Builder resp = ErrorMsg.scErrorCode.newBuilder();
        resp.setMsgId(msgId);
        resp.setErrorCode(errorCode);
        session.sendClientMsg(Cmd.CMD.CS_ErrorCode_VALUE, resp.build());
    }

}
