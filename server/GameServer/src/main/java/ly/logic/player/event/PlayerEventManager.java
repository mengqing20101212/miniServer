package ly.logic.player.event;

import ly.LoggerDef;
import ly.logic.player.Player;
import ly.utils.TimeUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.function.Consumer;

/**
 * 游戏服管理器，维护对应业务对象的生命周期和查询入口。
 */
public class PlayerEventManager {
    Map<PlayerEventType, List<IPlayerEvent>> eventHandlerMap = new HashMap<>();
    private final ArrayBlockingQueue<PlayerEventParam> pendingEventQueue = new ArrayBlockingQueue<>(1024);

    public void register(PlayerEventType eventType, IPlayerEvent eventHandler) {
        List<IPlayerEvent> eventHandlers = eventHandlerMap.get(eventType);
        if (eventHandlers == null) {
            eventHandlers = new ArrayList<>();
            eventHandlerMap.put(eventType, eventHandlers);
        }
        eventHandlers.add(eventHandler);
    }

    public void dispatchEvent(Player player, PlayerEventType eventType, Object... args) {
        dispatchEvent(new PlayerEventParam(player, eventType, args));
    }

    public void dispatchEvent(PlayerEventParam param) {
        if (!pendingEventQueue.offer(param)) {
            LoggerDef.SystemLogger.error(
                    "PlayerEventManager pending queue full, playerId={}, eventType={}",
                    param.getPlayer() == null ? 0L : param.getPlayer().getPlayerId(),
                    param.getEventType());
        }
    }

    public void drainPendingEvents(Consumer<PlayerEventParam> consumer) {
        PlayerEventParam param;
        while ((param = pendingEventQueue.poll()) != null) {
            consumer.accept(param);
        }
    }

    public void handleEvent(Player player, PlayerEventType eventType, Object... args) {
        handleEvent(new PlayerEventParam(player, eventType, args));
    }

    public void handleEvent(PlayerEventParam param) {
        Player player = param.getPlayer();
        PlayerEventType eventType = param.getEventType();
        List<IPlayerEvent> eventHandlers = eventHandlerMap.get(eventType);
        long beginT1 = TimeUtils.nowMillis();

        if (eventHandlers != null) {
            for (IPlayerEvent eventHandler : eventHandlers) {
                try {
                    long beginT2 = TimeUtils.nowMillis();
                    eventHandler.onEvent(param);
                    long endT2 = TimeUtils.nowMillis();
                    long cost2 = endT2 - beginT2;
                    if (cost2 > 50) {
                        LoggerDef.SystemLogger.warn("PlayerEventManager handleEvent handler:{}  param:{} too long cost:{} ms", eventHandler.getClass().getSimpleName(), param.toString(), cost2);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    LoggerDef.SystemLogger.error("PlayerEventManager dispatchEvent error, handler:%s  eventType:{}, e:{}", eventHandler.getClass().getSimpleName(), eventType, e);
                }
            }
        }
        long endT1 = TimeUtils.nowMillis();
        long cost1 = endT1 - beginT1;
        if (cost1 > 1000) {
            LoggerDef.SystemLogger.error("PlayerEventManager dispatchEvent time out, eventType:{}, cost:{}", eventType, cost1);
        }
    }
}
