package ly.net;

import com.google.protobuf.AbstractMessage;
import ly.LoggerDef;
import ly.ProtoMessageFactory;
import ly.net.packet.AbstractMessagePacket;
import ly.proto.Cmd;

import java.util.HashMap;
import java.util.Map;

public class HandlerRouterManager {

    private static HandlerRouterManager instance = new HandlerRouterManager();

    private final Map<Integer, RouterHolder<?, ?, ?>> handlerRouterMap = new HashMap<>();

    private static class RouterHolder<S extends ConnectSession,
            P extends AbstractMessagePacket,
            R extends AbstractMessage> {
        final Class<S> sessionType;
        final Class<P> packetType;
        final Class<R> requestType;
        final IHandlerRouter<S, P, R> router;

        RouterHolder(Class<S> sessionType,
                     Class<P> packetType,
                     Class<R> requestType,
                     IHandlerRouter<S, P, R> router) {
            this.sessionType = sessionType;
            this.packetType = packetType;
            this.requestType = requestType;
            this.router = router;
        }
    }

    public static HandlerRouterManager getInstance() {
        return instance;
    }

    private HandlerRouterManager() {
    }

    /**
     * 泛型注册（自动类型安全）
     */
    public <S extends ConnectSession, P extends AbstractMessagePacket, R extends AbstractMessage>
    void addHandlerRouter(Cmd.CMD cmd,
                          Class<S> sessionType,
                          Class<P> packetType,
                          Class<R> requestType,
                          IHandlerRouter<S, P, R> router) {
        int cmdNum = cmd.getNumber();
        LoggerDef.LogSystem("addHandlerRouter cmd={} router={}", cmd, router.getClass().getSimpleName());
        if (handlerRouterMap.containsKey(cmdNum)) {
            LoggerDef.SystemLogger.error("addHandlerRouter cmd={} fail, already exist", cmdNum);
            return;
        }
        handlerRouterMap.put(cmdNum, new RouterHolder<>(sessionType, packetType, requestType, router));
    }

    private RouterHolder<?, ?, ?> getHandlerRouter(int cmd) {
        return handlerRouterMap.get(cmd);
    }

    /**
     * 执行（自动类型检查 + 安全强转）
     */
    @SuppressWarnings("unchecked")
    public static void execute(ConnectSession session, AbstractMessagePacket packet) {
        final int cmd = packet.getCmd();
        HandlerRouterManager instance = HandlerRouterManager.getInstance();
        RouterHolder<?, ?, ?> holder = instance.getHandlerRouter(cmd);

        if (holder == null) {
            LoggerDef.SystemLogger.error("execute cmd={} fail, router not exist", cmd);
            return;
        }

        // Session 类型检查
        if (!holder.sessionType.isInstance(session)) {
            LoggerDef.SystemLogger.error("execute cmd={} fail, session type mismatch: need {}, got {}",
                    cmd, holder.sessionType.getSimpleName(), session.getClass().getSimpleName());
            return;
        }

        // Packet 类型检查
        if (!holder.packetType.isInstance(packet)) {
            LoggerDef.SystemLogger.error("execute cmd={} fail, packet type mismatch: need {}, got {}",
                    cmd, holder.packetType.getSimpleName(), packet.getClass().getSimpleName());
            return;
        }

        // 反序列化 request
        AbstractMessage request = ProtoMessageFactory.createProtoMessage(cmd, packet.getData());
        if (request == null || !holder.requestType.isInstance(request)) {
            LoggerDef.SystemLogger.error("execute cmd={} fail, request type mismatch or null: need {}, got {}",
                    cmd,
                    holder.requestType.getSimpleName(),
                    request == null ? "null" : request.getClass().getSimpleName());
            return;
        }

        // 安全执行
        IHandlerRouter<ConnectSession, AbstractMessagePacket, AbstractMessage> safeRouter =
                (IHandlerRouter<ConnectSession, AbstractMessagePacket, AbstractMessage>) holder.router;

        safeRouter.execute(session, packet, request);
    }
}
