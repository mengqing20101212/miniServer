package ly.net;

import com.google.protobuf.AbstractMessage;
import ly.LoggerDef;
import ly.ProtoMessageFactory;
import ly.net.packet.AbstractMessagePacket;

import java.util.HashMap;
import java.util.Map;

public class HandlerRouterManager {
    private static HandlerRouterManager instance = new HandlerRouterManager();
    private final Map<Integer, IHandlerRouter> handlerRouterMap = new HashMap<>();

    public static HandlerRouterManager getInstance() {
        return instance;
    }

    private HandlerRouterManager() {
    }

    /**
     * 添加路由
     *
     * @param cmd    命令
     * @param router 路由
     */
    public void addHandlerRouter(int cmd, IHandlerRouter router) {
        LoggerDef.LogSystem("addHandlerRouter cmd={} router={}", cmd, router.getClass().getSimpleName());
        if (handlerRouterMap.containsKey(cmd)) {
            LoggerDef.SystemLogger.error("addHandlerRouter cmd={} router={} fail, already exist", cmd, router.getClass().getSimpleName());
            return;
        }
        handlerRouterMap.put(cmd, router);
    }

    public IHandlerRouter getHandlerRouter(int cmd) {
        return handlerRouterMap.get(cmd);
    }

    public static void execute(ConnectSession session, AbstractMessagePacket packet) {
        final int cmd = packet.getCmd();
        HandlerRouterManager instance = HandlerRouterManager.getInstance();
        IHandlerRouter router = instance.getHandlerRouter(cmd);
        if (router == null) {
            LoggerDef.SystemLogger.error("execute cmd={} router={} fail, not exist", cmd, router.getClass().getSimpleName());
            return;
        }
        AbstractMessage request = ProtoMessageFactory.createProtoMessage(cmd, packet.getData());
        router.execute(session, packet, request);
    }

}
