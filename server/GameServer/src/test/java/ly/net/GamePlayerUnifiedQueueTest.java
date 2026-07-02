package ly.net;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import ly.db.entry.PlayerEntry;
import ly.logic.player.Player;
import ly.logic.player.PlayerData;
import ly.logic.player.event.IPlayerEvent;
import ly.logic.player.event.PlayerEventManager;
import ly.logic.player.event.PlayerEventParam;
import ly.logic.player.event.PlayerEventSource;
import ly.logic.player.event.PlayerEventType;
import ly.net.packet.AbstractMessagePacket;
import ly.net.packet.MessagePacketFactory;
import ly.proto.Cmd;
import ly.proto.Hero;
import org.junit.Test;

public class GamePlayerUnifiedQueueTest {

    @Test
    public void shouldProcessEventAndPacketByEnqueueOrder() throws Exception {
        List<String> order = new ArrayList<>();
        GamePlayer gamePlayer = newBoundGamePlayer(order, "packet");

        gamePlayer.addEvent(PlayerEventParam.of(
                gamePlayerPlayer(gamePlayer), PlayerEventType.PASS_ONE_SECOND, PlayerEventSource.MODULE, 0L));
        gamePlayer.addPacket(heroListPacket(1));

        gamePlayer.tickWorkItem();
        gamePlayer.tickWorkItem();

        assertEquals(List.of("event", "packet"), order);
    }

    @Test
    public void shouldProcessPacketAndEventByEnqueueOrder() throws Exception {
        List<String> order = new ArrayList<>();
        GamePlayer gamePlayer = newBoundGamePlayer(order, "packet");

        gamePlayer.addPacket(heroListPacket(2));
        gamePlayer.addEvent(PlayerEventParam.of(
                gamePlayerPlayer(gamePlayer), PlayerEventType.PASS_ONE_SECOND, PlayerEventSource.MODULE, 0L));

        gamePlayer.tickWorkItem();
        gamePlayer.tickWorkItem();

        assertEquals(List.of("packet", "event"), order);
    }

    private GamePlayer newBoundGamePlayer(List<String> order, String packetMark) {
        GamePlayer gamePlayer = new GamePlayer(null);
        Player player = new Player();
        player.setPlayerData(testPlayerData());
        player.setGamePlayer(gamePlayer);

        PlayerEventManager eventManager = player.getEventManager();
        eventManager.register(PlayerEventType.PASS_ONE_SECOND, new IPlayerEvent() {
            @Override
            public void onEvent(PlayerEventParam param) {
                order.add("event");
            }

            @Override
            public List<PlayerEventType> getRegisterEventTypes() {
                return List.of(PlayerEventType.PASS_ONE_SECOND);
            }
        });

        GameHandlerRouteManager.getInstance()
                .register(Cmd.CMD.CS_HeroList, (context, request) -> order.add(packetMark));
        return gamePlayer;
    }

    private Player gamePlayerPlayer(GamePlayer gamePlayer) {
        try {
            var field = GamePlayer.class.getDeclaredField("player");
            field.setAccessible(true);
            return (Player) field.get(gamePlayer);
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException(e);
        }
    }

    private AbstractMessagePacket heroListPacket(int seq) {
        Hero.CS_HeroList request = Hero.CS_HeroList.newBuilder().build();
        return MessagePacketFactory.createAbstractMessagePacket(
                1L, Cmd.CMD.CS_HeroList_VALUE, request, seq, 100);
    }

    private PlayerData testPlayerData() {
        PlayerEntry entry = new PlayerEntry();
        entry.setId(1L);
        entry.setAccount("queue-test");
        entry.setName("QueueTest");
        return new PlayerData(entry);
    }
}
