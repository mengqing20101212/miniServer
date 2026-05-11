package ly.net;

import ly.logic.player.event.PlayerEventParam;
import ly.net.packet.AbstractMessagePacket;

/**
 * 玩家串行执行队列中的工作项。
 *
 * <p>客户端包和玩家事件共用同一个 FIFO 队列，保证谁先入队谁先执行。
 */
class GamePlayerWorkItem {
    enum Type {
        PACKET,
        EVENT
    }

    private final Type type;
    private final AbstractMessagePacket packet;
    private final PlayerEventParam event;

    private GamePlayerWorkItem(Type type, AbstractMessagePacket packet, PlayerEventParam event) {
        this.type = type;
        this.packet = packet;
        this.event = event;
    }

    static GamePlayerWorkItem packet(AbstractMessagePacket packet) {
        return new GamePlayerWorkItem(Type.PACKET, packet, null);
    }

    static GamePlayerWorkItem event(PlayerEventParam event) {
        return new GamePlayerWorkItem(Type.EVENT, null, event);
    }

    Type getType() {
        return type;
    }

    AbstractMessagePacket getPacket() {
        return packet;
    }

    PlayerEventParam getEvent() {
        return event;
    }
}
