package ly.rpc;

import static org.junit.Assert.*;
import java.nio.file.Path;
import java.util.List;
import ly.net.packet.MessagePacket;
import ly.proto.Cmd;
import ly.proto.Server;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class RocksRpcOutboxTest {
  @Rule public TemporaryFolder directory = new TemporaryFolder();
  @Test public void restartPreservesIndependentCommandsAndRetryState() throws Exception {
    Path path = directory.newFolder().toPath();
    MessagePacket packet = RpcNodeConnector.createServer2ServerRpcPacket(10, 101, null, 7, 8, 123);
    ReliableRpcMessage first = ReliableRpcMessage.from("first", "source", "target", packet, "timeout");
    ReliableRpcMessage second = ReliableRpcMessage.from("second", "source", "target", packet, "timeout");
    try (RocksRpcOutbox store = new RocksRpcOutbox(path)) {
      store.put(first); store.put(second);
      for (int i = 0; i < 30; i++) { first.increaseRetryCount(999); }
      store.put(first);
      packet.getData()[0] ^= 1; // Persisted snapshots must not share mutable packet references.
    }
    try (RocksRpcOutbox store = new RocksRpcOutbox(path)) {
      List<ReliableRpcMessage> restored = store.snapshot("target");
      assertEquals(2, restored.size());
      ReliableRpcMessage message = restored.stream().filter(m -> m.getMsgId().equals("first")).findFirst().orElseThrow();
      assertEquals(30, message.getRetryCount());
      assertEquals(123, ReliableRpcStore.callId(message.toPacket()));
      assertEquals(0, message.toPacket().getSid());
      store.remove("first");
    }
    try (RocksRpcOutbox store = new RocksRpcOutbox(path)) {
      assertEquals("second", store.snapshot("target").getFirst().getMsgId());
    }
  }
  @Test public void wrongInnerResponseDoesNotConfirmDelivery() {
    MessagePacket request = RpcNodeConnector.createServer2ServerRpcPacket(1, 101, null, 0, 0, 123);
    ReliableRpcMessage message = ReliableRpcMessage.from("one", "source", "target", request, "timeout");
    Server.scServer2Server wrong = Server.scServer2Server.newBuilder().setCallId(123).setCmd(104).build();
    assertFalse(ReliableRpcStore.validateResponse(message,
        new MessagePacket(1, Cmd.CMD.SC_Server2Server_VALUE, 0, 0, wrong.toByteArray())));
    Server.scServer2Server correct = wrong.toBuilder().setCmd(102).build();
    assertTrue(ReliableRpcStore.validateResponse(message,
        new MessagePacket(1, Cmd.CMD.SC_Server2Server_VALUE, 0, 0, correct.toByteArray())));
  }
}
