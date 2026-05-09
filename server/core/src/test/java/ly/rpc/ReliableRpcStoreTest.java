package ly.rpc;

import static org.junit.Assert.assertEquals;

import ly.net.packet.AbstractMessagePacket;
import org.junit.Test;

public class ReliableRpcStoreTest {

  @Test
  public void retryBackoffMaxDelayIsEightHours() {
    ReliableRpcStore store = ReliableRpcStore.getInstance();

    assertEquals(5_000L, store.calculateRetryDelayMillis(1));
    assertEquals(10_000L, store.calculateRetryDelayMillis(2));
    assertEquals(8L * 60 * 60 * 1000, store.calculateRetryDelayMillis(40));
  }

  @Test
  public void reliableMessageReplayPacketResetsSeqAndSid() {
    AbstractMessagePacket packet = new AbstractMessagePacket(1001L, 200, 9, 88, new byte[] {1, 2, 3});
    ReliableRpcMessage message =
        ReliableRpcMessage.from("msg-1", "game1001", "gate1001", packet, "response timeout");

    AbstractMessagePacket replayPacket = message.toPacket();

    assertEquals(0, replayPacket.getSeq());
    assertEquals(0, replayPacket.getSid());
    assertEquals(1001L, replayPacket.getGuid());
    assertEquals(200, replayPacket.getCmd());
  }
}
