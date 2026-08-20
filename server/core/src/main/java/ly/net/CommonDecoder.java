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
  /**
   * 历史协议的 length 按 22 字节逻辑头计算，但真实编码头是 26 字节：length 自身 2 字节，
   * 再加 cmd/sid/seq/guid/time 共 24 字节。因此完整帧实际比 length 声明值多 4 字节。
   * 这里必须保留这个兼容偏移，不能直接修改线上 length 含义，否则旧客户端会全部断帧。
   */
  private static final int LEGACY_FRAME_LENGTH_BIAS = 4;

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
        // 已经消费 length 自身 2 字节，所以还需要 (len + 4 - 2) 字节。
        // 旧实现只等待 len - 2，在高频批量发包时会提前 4 字节尝试解包，产生假解析错误，
        // 严重时最后一个半包没有后续读事件触发重试，整条连接就一直等待响应。
        int requiredBytesAfterLength = len + LEGACY_FRAME_LENGTH_BIAS - Short.BYTES;
        if (in.readableBytes() < requiredBytesAfterLength) {
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
