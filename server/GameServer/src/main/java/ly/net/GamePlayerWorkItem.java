package ly.net;

import ly.logic.player.event.PlayerEventParam;
import ly.logic.player.coroutine.PlayerCoroutineTask;
import ly.net.packet.MessagePacket;

/**
 * 玩家串行执行队列中的工作项。
 *
 * <p>客户端包和玩家事件共用同一个 FIFO 队列，保证谁先入队谁先执行。
 */
class GamePlayerWorkItem {
    enum Type {
        PACKET,
        EVENT,
        COROUTINE
    }

    private final Type type;
    private final MessagePacket packet;
    private final PlayerEventParam event;
    private final PlayerCoroutineTask<?> coroutineTask;

    private GamePlayerWorkItem(
            Type type,
            MessagePacket packet,
            PlayerEventParam event,
            PlayerCoroutineTask<?> coroutineTask) {
        this.type = type;
        this.packet = packet;
        this.event = event;
        this.coroutineTask = coroutineTask;
    }

    static GamePlayerWorkItem packet(MessagePacket packet) {
        return new GamePlayerWorkItem(Type.PACKET, packet, null, null);
    }

    static GamePlayerWorkItem event(PlayerEventParam event) {
        return new GamePlayerWorkItem(Type.EVENT, null, event, null);
    }

    static GamePlayerWorkItem coroutine(PlayerCoroutineTask<?> coroutineTask) {
        return new GamePlayerWorkItem(Type.COROUTINE, null, null, coroutineTask);
    }

    Type getType() {
        return type;
    }

    MessagePacket getPacket() {
        return packet;
    }

    PlayerEventParam getEvent() {
        return event;
    }

    PlayerCoroutineTask<?> getCoroutineTask() {
        return coroutineTask;
    }
}
