package ly.net;

import com.google.protobuf.AbstractMessage;
import ly.net.packet.AbstractMessagePacket;
import ly.proto.Cmd;

/**
 * 协议控制器统一接口。
 * <p>
 * 每个控制器负责在启动阶段注册自己关心的 CMD 路由，不直接参与 Netty 生命周期。
 */
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
