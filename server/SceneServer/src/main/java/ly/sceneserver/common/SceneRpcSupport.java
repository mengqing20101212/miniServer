package ly.sceneserver.common;

import com.google.protobuf.AbstractMessage;

import ly.net.ConnectSession;
import ly.net.packet.MessagePacketFactory;
import ly.proto.Cmd;

/** SceneServer 公共 RPC Handler 的响应封装，底层仍复用 core 的通用 callId 包装。 */
public final class SceneRpcSupport {
    private SceneRpcSupport() {
    }

    public static void sendResponse(ConnectSession session, Cmd.CMD cmd, AbstractMessage response) {
        session.addSendPacket(MessagePacketFactory.createMessagePacket(
                session.getGuid(), cmd.getNumber(), response, 0, 0));
    }
}
