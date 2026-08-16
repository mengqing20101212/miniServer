package ly.net;

import com.google.protobuf.AbstractMessage;
import ly.net.packet.MessagePacket;
import ly.proto.Cmd;

/**
 * 游戏服协议控制器，负责注册并处理对应业务消息。
 */
public interface IGameController extends IController {
    default <R extends AbstractMessage> void clientHandlerRegister(Cmd.CMD cmd, Class<? extends AbstractMessage> requestType, IHandlerRouter<GameConnectSession, MessagePacket, R> handler) {
        register(cmd, GameConnectSession.class, MessagePacket.class, requestType, (IHandlerRouter) handler);
    }

    default <R extends AbstractMessage> void gameHandlerRegister(Cmd.CMD cmd, GameHandlerRouter<R> handler) {
        GameHandlerRouteManager.getInstance().register(cmd, handler);
    }


}
