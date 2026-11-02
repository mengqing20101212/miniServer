package ly.net;

import static org.junit.Assert.*;
import java.util.concurrent.atomic.AtomicReference;
import ly.net.packet.MessagePacket;
import ly.net.packet.MessagePacketFactory;
import ly.proto.*;
import org.junit.Test;

public class RpcUnsolicitedPacketTest {
    @Test public void rpcClientRoutesScenePushWithoutConsumingUnrelatedPackets() {
        NetClient client = new NetClient("127.0.0.1", 9101, false);
        client.enableRpcMode();
        AtomicReference<MessagePacket> received = new AtomicReference<>();
        client.setUnsolicitedPacketHandler(packet -> {
            if (packet.getCmd() != Cmd.CMD.SC_SceneChanged_VALUE) return false;
            received.set(packet);
            return true;
        });
        MessagePacket push = MessagePacketFactory.createMessagePacket(123, Cmd.CMD.SC_SceneChanged_VALUE,
                Scene.scSceneChanged.newBuilder().build(), 0, 0);
        client.addReceivePacket(push);
        assertSame(push, received.get());
        assertNull(client.readPacket());
        MessagePacket unrelated = MessagePacketFactory.createMessagePacket(123, Cmd.CMD.SC_SceneQuery_VALUE,
                Scene.scSceneQuery.newBuilder().build(), 0, 0);
        client.addReceivePacket(unrelated);
        assertSame(unrelated, client.readPacket());
    }
}
