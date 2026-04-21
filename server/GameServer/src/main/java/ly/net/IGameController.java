package ly.net;

import com.google.protobuf.AbstractMessage;
import ly.net.packet.AbstractMessagePacket;
import ly.proto.Cmd;

public interface IGameController extends IController {
    default <R extends AbstractMessage> void clientHandlerRegister(Cmd.CMD cmd, Class<? extends AbstractMessage> requestType, IHandlerRouter<GameConnectSession, AbstractMessagePacket, R> handler) {
        register(cmd, GameConnectSession.class, AbstractMessagePacket.class, requestType, (IHandlerRouter) handler);
    }

    default <R extends AbstractMessage> void gameHandlerRegister(Cmd.CMD cmd, GameHandlerRouter<R> handler) {
        GameHandlerRouteManager.getInstance().register(cmd, handler);
    }


}
