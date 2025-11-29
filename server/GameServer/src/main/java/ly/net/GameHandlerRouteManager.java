package ly.net;

import com.google.protobuf.AbstractMessage;
import ly.LoggerDef;
import ly.ProtoMessageFactory;
import ly.logic.player.Player;
import ly.logic.player.PlayerManager;
import ly.net.packet.S2SMessagePacket;
import ly.proto.Cmd;

import java.util.HashMap;
import java.util.Map;

public class GameHandlerRouteManager extends HandlerRouterManager {
    private static final GameHandlerRouteManager instance = new GameHandlerRouteManager();
    Map<Integer, GameHandlerRouter<? extends AbstractMessage>> gameHandlerRouterMap = new HashMap<>();
//    Map<Integer, Class<? extends AbstractMessage>> protoClassMap = new HashMap<>();

    public static GameHandlerRouteManager getInstance() {
        return instance;
    }

    private GameHandlerRouteManager() {
    }

    /**
     * 执行（自动类型检查 + 安全强转）
     */
    @SuppressWarnings("unchecked")
    public static void execute(GameConnectSession session, S2SMessagePacket packet) {
        try {
            final int cmd = packet.getCmd();
            GameHandlerRouteManager instance = getInstance();
            if (instance.gameHandlerRouterMap.containsKey(cmd)) {//game handler 单独处理
                Player player = PlayerManager.getInstance().getOnlinePlayer(packet.getGuid());
                if (!checkPacket(packet)) {
                    return;
                }
                if (player != null) {
                    player.getGamePlayer().addPacket(packet);
                } else {
                    LoggerDef.SystemLogger.error(" GameHandlerRouteManager execute error, player is null, cmd:{}, playerId:{}, seq:{}", cmd, packet.getGuid(), packet.getSeq());
                }
            } else {
                HandlerRouterManager.execute(session, packet);
            }
        } catch (Exception e) {
            e.printStackTrace();
            LoggerDef.SystemLogger.error("GameHandlerRouteManager execute error, e", e);
        }
    }

    private static boolean checkPacket(S2SMessagePacket packet) {
        final int cmd = packet.getCmd();
//        Class<? extends AbstractMessage> protoClass = instance.protoClassMap.get(cmd);
//        if (protoClass == null) {
//            LoggerDef.SystemLogger.error(" GameHandlerRouteManager checkPacket error, protoClass is null, cmd:{}", cmd);
//            return false;
//        }

        return true;
    }

    public <R extends AbstractMessage> void register(Cmd.CMD cmd, GameHandlerRouter<R> handler) {
        gameHandlerRouterMap.put(cmd.getNumber(), handler);
//        protoClassMap.put(cmd.getNumber(), protoClass);

        LoggerDef.SystemLogger.info(String.format("GameHandlerRouteManager register handler cmd:%s( %d ),handler:%s", cmd.toString(), cmd.getNumber(), handler.getClass().getSimpleName()));
    }

    /**
     * 执行路由
     */
    public static boolean execute(Player player, S2SMessagePacket packet) {
        if (player == null || packet == null) {
            LoggerDef.SystemLogger.error("execute route, param invalid: player={}, packet={}", player == null, packet == null);
            return false;
        }

        final int cmd = packet.getCmd();
        GameHandlerRouteManager instance = GameHandlerRouteManager.getInstance();
        GameHandlerRouter<?> router = instance.gameHandlerRouterMap.get(cmd);
        if (router == null) {
            LoggerDef.SystemLogger.error("execute cmd={} fail, router not exist", cmd);
            return false;
        }

        // 反序列化请求
        AbstractMessage request = ProtoMessageFactory.createProtoMessage(cmd, packet.getData());
        if (request == null) {
            LoggerDef.SystemLogger.error("execute cmd={} fail, request deserialize error", cmd);
            return false;
        }

        // 检查请求类型是否匹配
//        Class<? extends AbstractMessage> expectedType = instance.protoClassMap.get(cmd);
//        if (expectedType == null || !expectedType.isInstance(request)) {
//            LoggerDef.SystemLogger.error("execute cmd={} fail, request type mismatch: expected={}, actual={}",
//                    cmd, expectedType == null ? "unknown" : expectedType.getName(), request.getClass().getName());
//            return false;
//        }

        try {
            // 创建游戏处理器上下文并执行路由处理
            GameHandlerContext context = new GameHandlerContext(player, packet);
            // 进行安全的类型转换和调用
            @SuppressWarnings("unchecked")
            GameHandlerRouter<AbstractMessage> typedRouter = (GameHandlerRouter<AbstractMessage>) router;
            typedRouter.execute(context, request);
            return true;
        } catch (Throwable e) {
            LoggerDef.SystemLogger.error("execute cmd={} error", cmd, e);
            return false;
        }
    }

    public void processPacket(Player player, S2SMessagePacket packet) {
        execute(player, packet);
    }
}