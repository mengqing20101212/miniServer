import com.google.protobuf.AbstractMessage;
import io.netty.channel.ChannelHandlerContext;
import ly.ProtoMessageFactory;
import ly.net.GameObject;
import ly.net.GameObjectProvider;
import ly.net.NetService;
import ly.net.packet.MessagePacketFactory;
import ly.net.packet.S2SMessagePacket;

/*
 * Author: liuYang
 * Date: 2025/4/9
 * File: TestServer
 */
public class TestServer {
  public static void main(String[] args) {
    NetService.getInstance()
        .startUp(
            new GameObjectProvider() {
              long guid;

              @Override
              public GameObject createGameObject(ChannelHandlerContext ctx) {
                return new TestPlayer(++guid);
              }
            },
            5525,
            5526,
            5527);

    while (true) {
      try {
        Thread.sleep(50L);
        NetService.getInstance()
            .getGameObjectMaps()
            .values()
            .forEach(
                gameObject -> {
                  gameObject.tick();
                });
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }
  }

  static class TestPlayer extends GameObject {

    public TestPlayer(long guid) {
      super(guid);
    }

    @Override
    public void tick() {
      super.tick();
      getReceivePacketList()
          .forEach(
              packet -> {
                S2SMessagePacket msg =
                    MessagePacketFactory.copyMessagePacket((S2SMessagePacket) packet);
                AbstractMessage protoMsg =
                    ProtoMessageFactory.createProtoMessage(packet.getCmd(), packet.getData());
                sendPacket(msg);
              });
    }
  }
}
