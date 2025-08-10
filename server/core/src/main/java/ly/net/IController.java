package ly.net;

import com.google.protobuf.AbstractMessage;
import ly.net.packet.AbstractMessagePacket;
import ly.proto.Cmd;

public interface IController {

    void registerHandlerRouter();

    default <S extends ConnectSession, P extends AbstractMessagePacket, R extends AbstractMessage>
    void register(Cmd.CMD cmd,
                  Class<S> sessionType,
                  Class<P> packetType,
                  Class<R> requestType,
                  IHandlerRouter<S, P, R> handler) {
        HandlerRouterManager.getInstance()
                .addHandlerRouter(cmd, sessionType, packetType, requestType, handler);
    }
}
