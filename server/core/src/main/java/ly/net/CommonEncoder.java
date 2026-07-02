package ly.net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import ly.LoggerDef;
import ly.net.packet.AbstractMessagePacket;
import org.slf4j.Logger;

/**
 * Netty 出站编码器，把 {@link AbstractMessagePacket} 写成统一二进制包格式。
 * <p>
 * 真正的字段写入逻辑放在 packet 自身，编码器只负责接入 Netty pipeline 和统一日志。
 */
public class CommonEncoder extends MessageToByteEncoder<AbstractMessagePacket> {
  static final Logger log = LoggerDef.SystemLogger;

  @Override
  protected void encode(
      ChannelHandlerContext channelHandlerContext, AbstractMessagePacket packet, ByteBuf byteBuf)
      throws Exception {
    if (packet.encode(channelHandlerContext, byteBuf)) {
      return;
    } else {
      log.error(String.format("Packet write failed :%s", packet.toString()));
      return;
    }
  }
}
