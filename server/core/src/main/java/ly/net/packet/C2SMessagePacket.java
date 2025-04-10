package ly.net.packet;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

/*
 * Author: liuYang
 * Date: 2025/4/9
 * File: C2SMessagePacket
 */
public class C2SMessagePacket extends AbstractMessagePacket {
  private int cmd;
  private int sid;
  private long guid;
  private int seq;
  private byte[] data;

  public C2SMessagePacket() {
    super(AbstractMessagePacket.MESSAGE_PACKET_TYPE_CLIENT_TO_SERVER);
  }

  @Override
  protected short getHeadLength() {
    return (short) (getAbstractPacketLen() + 4 + 4 + 8 + 4);
  }

  @Override
  public void setSid(int sid) {
    this.sid = sid;
  }

  @Override
  protected short getPacketLen() {
    return (short) (getHeadLength() + data.length);
  }

  @Override
  public boolean encode(ChannelHandlerContext channelHandlerContext, ByteBuf in) {
    encode(in);
    in.writeInt(cmd);
    in.writeInt(sid);
    in.writeLong(guid);
    in.writeInt(seq);
    in.writeBytes(data);
    return false;
  }

  @Override
  public boolean decode(int packetLen, ByteBuf in) {
    int bodyLen = getBodyLen(packetLen);
    this.data = new byte[bodyLen];
    this.cmd = in.readInt();
    this.sid = in.readInt();
    this.guid = in.readLong();
    this.seq = in.readInt();
    in.readBytes(data);
    return true;
  }

  @Override
  public int getSid() {
    return sid;
  }

  @Override
  public int getSeq() {
    return seq;
  }

  @Override
  public void setSeq(int seq) {
    this.seq = seq;
  }

  @Override
  public int getCmd() {
    return cmd;
  }

  @Override
  public byte[] getData() {
    return data;
  }
}
