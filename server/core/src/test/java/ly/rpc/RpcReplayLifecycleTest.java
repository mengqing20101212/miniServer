package ly.rpc;

import static org.junit.Assert.*;
import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import ly.net.NetClient;
import ly.net.packet.MessagePacket;
import ly.proto.Cmd;
import ly.proto.Server;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class RpcReplayLifecycleTest {
  @Rule public TemporaryFolder directory = new TemporaryFolder();

  @Test public void fastResponseCompletesRegisteredCallAndLateResponsesDoNotQueue() throws Exception {
    ImmediateClient client = new ImmediateClient();
    RpcNodeConnector connector = new RpcNodeConnector("target", client);
    MessagePacket request = RpcNodeConnector.createServer2ServerRpcPacket(1, 101, null, 0, 0, 55);
    MessagePacket response = connector.requestAsync(request, Cmd.CMD.SC_Server2Server_VALUE, 1000).get(1, TimeUnit.SECONDS);
    assertEquals(55, Server.scServer2Server.parseFrom(response.getData()).getCallId());
    client.addReceivePacket(response);
    assertEquals(0, client.getReceivePacketQueueSize());
    assertEquals(0, client.rpcResponses().size());
    client.stop();
  }

  @Test public void nettyWriteFailureCompletesWaitAndReleasesCapacity() throws Exception {
    NetClient client = new NetClient("localhost", 1, false);
    io.netty.channel.embedded.EmbeddedChannel channel = new io.netty.channel.embedded.EmbeddedChannel(
        new io.netty.channel.ChannelOutboundHandlerAdapter() {
          @Override public void write(io.netty.channel.ChannelHandlerContext context, Object packet,
              io.netty.channel.ChannelPromise promise) {
            promise.setFailure(new java.io.IOException("simulated write failure"));
          }
        });
    java.lang.reflect.Field field = NetClient.class.getDeclaredField("channel");
    field.setAccessible(true);
    field.set(client, channel);
    client.setSid(1);
    try {
      RpcNodeConnector connector = new RpcNodeConnector("target", client);
      MessagePacket request = RpcNodeConnector.createServer2ServerRpcPacket(1, 101, null, 0, 0, 77);
      try {
        connector.requestAsync(request, Cmd.CMD.SC_Server2Server_VALUE, 1000).get(1, TimeUnit.SECONDS);
        fail("Expected transport failure");
      } catch (java.util.concurrent.ExecutionException error) {
        assertEquals(RpcException.Code.TRANSPORT, ((RpcException) error.getCause()).code());
      }
      assertEquals(0, client.rpcResponses().size());
    } finally { client.stop(); channel.finishAndReleaseAll(); }
  }

  @Test public void successfulCallWithFailurePolicyDoesNotCreateOutbox() throws Exception {
    Path unused = directory.getRoot().toPath().resolve("must-not-be-created");
    String previous = System.getProperty("miniserver.rpc.rocksPath");
    System.setProperty("miniserver.rpc.rocksPath", unused.toString());
    ImmediateClient client = new ImmediateClient();
    try {
      RpcNodeConnector connector = new RpcNodeConnector("target", client);
      Object response = connector.asyncSendProtoMessage(1, Cmd.CMD.CS_RpcPing_VALUE,
          Server.csRpcPing.newBuilder().build(), 1000, RpcFailSavePolicy.SEND_FAILED_OR_TIMEOUT)
          .get(1, TimeUnit.SECONDS);
      assertTrue(response instanceof Server.scRpcPing);
      assertFalse(java.nio.file.Files.exists(unused));
    } finally {
      client.stop();
      if (previous == null) { System.clearProperty("miniserver.rpc.rocksPath"); }
      else { System.setProperty("miniserver.rpc.rocksPath", previous); }
    }
  }

  @Test public void restartReplaysOriginalIdentityOnlyOnceAcrossConcurrentTriggers() throws Exception {
    Path path = directory.newFolder().toPath();
    MessagePacket request = RpcNodeConnector.createServer2ServerRpcPacket(1, 101, null, 7, 9, 66);
    try (ReliableRpcStore failed = new ReliableRpcStore(path, target -> null)) {
      assertTrue(failed.save("target", request, "send failed"));
    }
    CountDownLatch sent = new CountDownLatch(1);
    CountDownLatch release = new CountDownLatch(1);
    ImmediateClient client = new ImmediateClient() {
      @Override public CompletableFuture<Void> sendRpcAsync(MessagePacket packet) {
        sent.countDown();
        try { assertTrue(release.await(2, TimeUnit.SECONDS)); }
        catch (InterruptedException error) { Thread.currentThread().interrupt(); throw new RuntimeException(error); }
        return super.sendRpcAsync(packet);
      }
    };
    RpcNodeConnector connector = new RpcNodeConnector("target", client);
    try (ReliableRpcStore restored = new ReliableRpcStore(path, target -> connector)) {
      restored.replayForTarget("target");
      assertTrue(sent.await(2, TimeUnit.SECONDS));
      for (int i = 0; i < 30; i++) { restored.replayForTarget("target"); }
      assertEquals(1, restored.pendingCount("target"));
      release.countDown();
      long deadline = System.nanoTime() + TimeUnit.SECONDS.toNanos(3);
      while (restored.pendingCount("target") != 0 && System.nanoTime() < deadline) { Thread.sleep(5); }
      assertEquals(0, restored.pendingCount("target"));
      assertEquals(1, client.sends.get());
      assertEquals(66, client.lastCallId);
    } finally { release.countDown(); client.stop(); }
    try (RocksRpcOutbox empty = new RocksRpcOutbox(path)) { assertTrue(empty.snapshot("target").isEmpty()); }
  }

  private static class ImmediateClient extends NetClient {
    final AtomicInteger sends = new AtomicInteger();
    volatile long lastCallId;
    ImmediateClient() { super("localhost", 1, false); }
    @Override public boolean isReady() { return true; }
    @Override public CompletableFuture<Void> sendRpcAsync(MessagePacket packet) {
      sends.incrementAndGet();
      lastCallId = ReliableRpcStore.callId(packet);
      Server.csServer2Server request;
      try { request = Server.csServer2Server.parseFrom(packet.getData()); }
      catch (Exception error) { throw new RuntimeException(error); }
      Server.scServer2Server response = Server.scServer2Server.newBuilder().setCallId(lastCallId)
          .setCmd(request.getCmd() + 1)
          .setData(Server.scRpcPing.newBuilder().build().toByteString()).build();
      addReceivePacket(new MessagePacket(1, Cmd.CMD.SC_Server2Server_VALUE, 0, 0, response.toByteArray()));
      return CompletableFuture.completedFuture(null);
    }
  }
}
