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
    Map<Integer, Class<? extends AbstractMessage>> protoClassMap = new HashMap<>();

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
        Class<? extends AbstractMessage> protoClass = instance.protoClassMap.get(cmd);
        if (protoClass == null) {
            LoggerDef.SystemLogger.error(" GameHandlerRouteManager checkPacket error, protoClass is null, cmd:{}", cmd);
            return false;
        }

        return true;
    }

    public <R extends AbstractMessage> void register(Cmd.CMD cmd, Class<R> protoClass, GameHandlerRouter<R> handler) {
        gameHandlerRouterMap.put(cmd.getNumber(), handler);
        protoClassMap.put(cmd.getNumber(), protoClass);
        LoggerDef.SystemLogger.info(String.format("GameHandlerRouteManager register handler cmd:%s( %d ), class:%s, handler:%s", cmd.getNumber(), cmd.getNumber(), protoClass.getName(), handler.getClass().getName()));
    }

    public void processPacket(Player player, S2SMessagePacket packet) {
        final int cmd = packet.getCmd();
        GameHandlerRouter<?> router = instance.gameHandlerRouterMap.get(cmd);
        AbstractMessage request = ProtoMessageFactory.createProtoMessage(cmd, packet.getData());
        Class<? extends AbstractMessage> protoClass = instance.protoClassMap.get(cmd);
        if (request == null) {
            LoggerDef.SystemLogger.error(" GameHandlerRouteManager processPacket error, request is null, cmd:{}", cmd);
            return;
        }
        if (!protoClass.isInstance(request)) {
            LoggerDef.SystemLogger.error(" GameHandlerRouteManager processPacket error, request class not match, cmd:{}, expect:{}", cmd, protoClass.getName());
            return;
        }
        if (router == null) {
            LoggerDef.SystemLogger.error(" GameHandlerRouteManager processPacket error, router is null, cmd:{}", cmd);
            return;
        }
        player.getGamePlayer().setLastSeq(packet.getSeq());
        ((GameHandlerRouter<AbstractMessage>) router).execute(player, packet, request);
    }
}
