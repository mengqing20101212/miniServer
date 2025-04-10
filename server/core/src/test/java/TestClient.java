import ly.net.NetClient;
import ly.net.NetClientManager;
import ly.net.packet.S2SMessagePacket;

/*
 * Author: liuYang
 * Date: 2025/4/9
 * File: TestClient
 */
public class TestClient {
  public static void main(String[] args) {
    for (int i = 0; i < 1000; i++) {
      Thread.ofVirtual()
          .start(
              () -> {
                testClient(0, "127.0.0.1", 5526);
              });
    }
    try {
      Thread.sleep(Integer.MAX_VALUE);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  private static void testClient(int guid, String ip, int port) {
    NetClient client = NetClientManager.getInstance().newNetClient(ip, port);
    for (int i = 0; i < 1000; i++) {
      S2SMessagePacket packet =
          new S2SMessagePacket(i, 1, 0, client.getSendSeq(), new byte[] {1, 2, 3, 4});
      client.send(packet);
    }
  }
}
