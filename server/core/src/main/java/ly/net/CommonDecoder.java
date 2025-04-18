package ly.net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.util.List;
import ly.LoggerDef;
import ly.net.packet.AbstractMessagePacket;
import ly.net.packet.MessagePacketFactory;
import org.slf4j.Logger;

/*
 * Author: liuYang
 * Date: 2025/4/8
 * File: CommonDecoder
 */
public class CommonDecoder extends ByteToMessageDecoder {
  static final Logger log = LoggerDef.SystemLogger;

  @Override
  protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf in, List<Object> list)
      throws Exception {
    while (in.readableBytes() > 2) {
      in.markReaderIndex();
      try {
        short len = in.readShort();
        if (in.readableBytes() < len - 2) {
          in.resetReaderIndex();
          break;
        }
        int type = in.readByte();
        AbstractMessagePacket packet = MessagePacketFactory.createMessagePacket(type);
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
