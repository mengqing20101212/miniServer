package ly.net;

import com.google.protobuf.AbstractMessage;
import ly.net.packet.S2SMessagePacket;
import ly.proto.Cmd;

public interface IGameController extends IController {
    default <R extends AbstractMessage> void clientHandlerRegister(Cmd.CMD cmd, Class<? extends AbstractMessage> requestType, IHandlerRouter<GameConnectSession, S2SMessagePacket, R> handler) {
        register(cmd, GameConnectSession.class, S2SMessagePacket.class, requestType, (IHandlerRouter) handler);
    }

    default <R extends AbstractMessage> void gameHandlerRegister(Cmd.CMD cmd, Class<R> protoCLass, GameHandlerRouter<R> handler) {
        GameHandlerRouteManager.getInstance().register(cmd, protoCLass, handler);
    }


}
