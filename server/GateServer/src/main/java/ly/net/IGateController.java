package ly.net;

import com.google.protobuf.AbstractMessage;
import ly.net.packet.C2SMessagePacket;
import ly.proto.Cmd;

public interface IGateController extends IController {
    default <R extends AbstractMessage> void clientHandlerRegister(Cmd.CMD cmd, Class<? extends AbstractMessage> requestType, IHandlerRouter<GateConnectSession, C2SMessagePacket, R> handler) {
        register(cmd, GateConnectSession.class, C2SMessagePacket.class, requestType, (IHandlerRouter) handler);
    }

}
