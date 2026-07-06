package ly.net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.util.List;
import ly.LoggerDef;
import ly.net.packet.MessagePacket;
import ly.net.packet.MessagePacketFactory;
import org.slf4j.Logger;

/**
 * Netty 入站解码器，把 TCP 字节流拆成完整的 {@link MessagePacket}。
 * <p>
 * TCP 可能半包或粘包，因此这里通过 length 字段循环读取。数据不足一个完整包时会
 * reset readerIndex，等待下一次网络读事件继续拼包。
 */
public class CommonDecoder extends ByteToMessageDecoder {
  static final Logger log = LoggerDef.SystemLogger;

  @Override
  protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf in, List<Object> list)
      throws Exception {
    while (in.readableBytes() >= 2) {
      in.markReaderIndex();
      try {
        short len = in.readShort();
        // 最小包只有固定包头，没有业务体；小于该长度说明包头已经损坏。
        if (len < 22) {
          in.resetReaderIndex();
          break;
        }
        // 业务体还没到齐，恢复读指针后等待 Netty 下次继续解码。
        if (in.readableBytes() < len - 2) {
          in.resetReaderIndex();
          break;
        }
        MessagePacket packet = MessagePacketFactory.createMessagePacket();
        if (packet != null && packet.decode(len, in)) {
          list.add(packet);
        } else {
          in.resetReaderIndex();
          log.error(
              String.format(
                  "CommonDecoder 解析配置报错, cid:%s packet:%s ",
                  channelHandlerContext.channel().id(),
                  packet == null ? "null" : packet.getClass().getSimpleName()));
          break;
        }
      } catch (Exception e) {
        log.error(e.getMessage());
        in.resetReaderIndex();
        e.printStackTrace();
        break;
      }
    }
  }
}
