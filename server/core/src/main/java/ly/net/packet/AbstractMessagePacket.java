package ly.net.packet;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

/*
 * Author: liuYang
 * Date: 2025/4/8
 * File: AbstractMessagePacket
 */
public abstract class AbstractMessagePacket {
  static final byte MESSAGE_PACKET_TYPE_SERVER_TO_CLIENT = 0;
  static final byte MESSAGE_PACKET_TYPE_SERVER_TO_SERVER = 1;
  static final byte MESSAGE_PACKET_TYPE_CLIENT_TO_SERVER = 2;
  static final byte MESSAGE_PACKET_TYPE_CONNECT_ACK = 3;
  private short length;
  private final int type;

  public AbstractMessagePacket(int type) {
    this.type = type;
  }

  public int getLength() {
    return length;
  }

  public void setLength(short length) {
    this.length = length;
  }

  public int getType() {
    return type;
  }

  public byte[] getData() {
    return new byte[] {};
  }

  public void setSid(int sid) {}

  public void setSeq(int seq) {}

  protected short getAbstractPacketLen() {
    return 3;
  }

  public int getSeq() {
    return 0;
  }

  public int getSid() {
    return 0;
  }

  protected int getBodyLen(int packetLen) {
    return packetLen - getHeadLength();
  }

  protected abstract short getHeadLength();

  protected abstract short getPacketLen();

  public int getCmd() {
    return 0;
  }

  public abstract boolean encode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf);

  protected void encode(ByteBuf byteBuf) {
    byteBuf.writeShort(getPacketLen());
    byteBuf.writeByte(type);
  }

  public abstract boolean decode(int packetLen, ByteBuf in);
}
