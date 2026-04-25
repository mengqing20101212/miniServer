import com.google.protobuf.AbstractMessage;
import io.netty.channel.ChannelHandlerContext;
import ly.ProtoMessageFactory;
import ly.net.ConnectSession;
import ly.net.GameObjectProvider;
import ly.net.NetService;
import ly.net.packet.MessagePacketFactory;
import ly.net.packet.AbstractMessagePacket;

/**
 * 公共服务器启动示例或独立运行入口，用于本地验证网络框架。
 */
public class TestServer {
    public static void main(String[] args) {
        NetService.getInstance()
                .startUp(
                        new GameObjectProvider() {
                            long guid;

                            @Override
                            public ConnectSession createGameObject(ChannelHandlerContext ctx) {
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

    static class TestPlayer extends ConnectSession {

        public TestPlayer(long guid) {
            super(guid);
        }

        @Override
        public void tick() {
            super.tick();
            getReceivePacketList()
                    .forEach(
                            packet -> {
                                AbstractMessagePacket msg =
                                        MessagePacketFactory.copyMessagePacket((AbstractMessagePacket) packet);
                                AbstractMessage protoMsg =
                                        ProtoMessageFactory.createProtoMessage(packet.getCmd(), packet.getData());
                                sendPacket(msg);
                            });
        }
    }
}
