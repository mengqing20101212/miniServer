package ly.net;

import java.util.ArrayList;
import java.util.List;
import ly.GateClientManager;
import ly.net.packet.MessagePacket;
import ly.proto.Cmd;
import junit.framework.TestCase;
import java.util.Arrays;

public class GateGamePushTest extends TestCase {
    public void testWorldAndRewardPushesUseTheOwningClientAndItsDownstreamSequence() {
        List<MessagePacket> sent = new ArrayList<>();
        GateClient client = new GateClient(null) {
            @Override public int getClientSid() { return 31; }
            @Override public void sendPacketToClient(MessagePacket packet) { sent.add(packet); }
        };
        client.setPlayerId(75);
        GateClientManager.getInstance().addClient(client);
        try {
            for (Cmd.CMD cmd : List.of(Cmd.CMD.SC_SceneChanged, Cmd.CMD.SC_HeroList, Cmd.CMD.SC_ResourceChange)) {
                assertTrue(GateClient.forwardGamePush(new MessagePacket(75, cmd.getNumber(), 0, 0, new byte[]{1})));
                MessagePacket packet = sent.getLast();
                assertEquals(cmd.getNumber(), packet.getCmd());
                assertTrue(Arrays.equals(new byte[]{1}, packet.getData()));
                assertEquals(31, packet.getSid());
                assertEquals(sent.size(), packet.getSeq());
            }
            assertFalse(GateClient.forwardGamePush(new MessagePacket(75, Cmd.CMD.SC_Login_VALUE, 0, 0, new byte[0])));
            assertTrue(GateClient.forwardGamePush(new MessagePacket(76, Cmd.CMD.SC_HeroList_VALUE, 0, 0, new byte[0])));
            assertEquals(3, sent.size());
        } finally {
            GateClientManager.getInstance().removeClient(75L);
        }
    }
}
