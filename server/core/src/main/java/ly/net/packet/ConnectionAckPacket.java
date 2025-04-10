package ly.net.packet;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

/*
 * Author: liuYang
 * Date: 2025/4/10
 * File: ConnectionAckPacket
 */
public class ConnectionAckPacket extends AbstractMessagePacket {
  private int sessionId;

  public ConnectionAckPacket(int sessionId) {
    super(MESSAGE_PACKET_TYPE_CONNECT_ACK);
    this.sessionId = sessionId;
  }

  public ConnectionAckPacket() {
    super(MESSAGE_PACKET_TYPE_CONNECT_ACK);
  }

  @Override
  protected short getHeadLength() {
    return getAbstractPacketLen();
  }

  @Override
  protected short getPacketLen() {
    return (short) (getHeadLength() + 4);
  }

  public int getSessionId() {
    return sessionId;
  }

  public void setSessionId(int sessionId) {
    this.sessionId = sessionId;
  }

  @Override
  public boolean encode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) {
    encode(byteBuf);
    byteBuf.writeInt(sessionId);
    return true;
  }

  @Override
  public boolean decode(int packetLen, ByteBuf in) {
    this.sessionId = in.readInt();
    return true;
  }

  @Override
  public String toString() {
    return "ConnectionAckPacket{" + "sessionId=" + sessionId + '}';
  }
}
