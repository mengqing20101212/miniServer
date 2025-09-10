package ly.net;

import com.google.protobuf.AbstractMessage;
import ly.logic.player.Player;
import ly.net.packet.S2SMessagePacket;

@FunctionalInterface
public interface GameHandlerRouter<R extends AbstractMessage> {
    void execute(Player player, S2SMessagePacket packet, R request);
}
