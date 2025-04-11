package ly.net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import ly.LoggerDef;
import ly.net.packet.AbstractMessagePacket;
import org.slf4j.Logger;

/*
 * Author: liuYang
 * Date: 2025/4/8
 * File: CommonEncoder
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
