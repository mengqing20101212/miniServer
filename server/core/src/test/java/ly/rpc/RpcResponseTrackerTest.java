package ly.rpc;

import static org.junit.Assert.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import ly.net.packet.MessagePacket;
import org.junit.Test;

public class RpcResponseTrackerTest {
  @Test public void correlatesOutOfOrderAndIgnoresLateOrWrongCommand() throws Exception {
    try (RpcResponseTracker tracker = new RpcResponseTracker()) {
      CompletableFuture<MessagePacket> first = tracker.register(1, 102, 5000);
      CompletableFuture<MessagePacket> second = tracker.register(2, 102, 5000);
      MessagePacket packet = new MessagePacket(1, 102, 0, 0, new byte[0]);
      assertFalse(tracker.receive(1, new MessagePacket(1, 104, 0, 0, new byte[0])));
      assertTrue(tracker.receive(2, packet));
      assertSame(packet, second.get(1, TimeUnit.SECONDS));
      assertFalse(first.isDone());
      assertTrue(tracker.receive(1, packet));
      assertFalse(tracker.receive(1, packet));
      assertEquals(0, tracker.size());
    }
  }
  @Test public void cancellationAndTimeoutReleaseCapacity() throws Exception {
    try (RpcResponseTracker tracker = new RpcResponseTracker(1)) {
      CompletableFuture<MessagePacket> waiting = tracker.register(1, 102, 5000);
      assertCode(RpcException.Code.BUSY, tracker.register(2, 102, 5000));
      waiting.cancel(false);
      assertEquals(0, tracker.size());
      assertCode(RpcException.Code.TIMEOUT, tracker.register(2, 102, 20));
      assertEquals(0, tracker.size());
    }
  }
  @Test public void disconnectAndCloseEndPendingCalls() throws Exception {
    RpcResponseTracker tracker = new RpcResponseTracker();
    CompletableFuture<MessagePacket> first = tracker.register(1, 102, 5000);
    tracker.disconnected();
    assertCode(RpcException.Code.TRANSPORT, first);
    CompletableFuture<MessagePacket> second = tracker.register(2, 102, 5000);
    tracker.close();
    assertCode(RpcException.Code.CLOSED, second);
    assertCode(RpcException.Code.CLOSED, tracker.register(3, 102, 5000));
  }
  private static void assertCode(RpcException.Code code, CompletableFuture<?> future) throws Exception {
    try { future.get(1, TimeUnit.SECONDS); fail("Expected " + code); }
    catch (ExecutionException error) { assertEquals(code, ((RpcException) error.getCause()).code()); }
  }
}
