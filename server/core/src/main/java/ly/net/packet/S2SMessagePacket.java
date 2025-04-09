package ly.net.packet;

import com.google.protobuf.AbstractMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import java.util.Arrays;

/*
 * Author: liuYang
 * Date: 2025/4/8
 * File: S2SMessagePacket
 */
public class S2SMessagePacket extends AbstractMessagePacket {
  /***消息号*/
  private int cmd;

  /** 会话由服务器端分配 id session id */
  private int sid;

  /** 包的序列号，自增 客户端用来检查是否丢包 */
  private int seq;

  /** 该包所属实例的唯一id */
  private long guid;

  private byte[] data;

  public S2SMessagePacket(long guid, int cmd, int sid, int seq, byte[] data) {
    super(MESSAGE_PACKET_TYPE_SERVER_TO_SERVER);
    this.guid = guid;
    this.cmd = cmd;
    this.sid = sid;
    this.seq = seq;
    this.data = data;
  }

  public S2SMessagePacket(long guid, int cmd, int seq, byte[] data) {
    super(MESSAGE_PACKET_TYPE_SERVER_TO_SERVER);
    this.guid = guid;
    this.cmd = cmd;
    this.seq = seq;
    this.data = data;
  }

  public int getCmd() {
    return cmd;
  }

  public void setCmd(int cmd) {
    this.cmd = cmd;
  }

  @Override
  public void setSeq(int seq) {
    this.seq = seq;
  }

  public long getGuid() {
    return guid;
  }

  public void setGuid(long guid) {
    this.guid = guid;
  }

  public byte[] getData() {
    return data;
  }

  public void setData(byte[] data) {
    this.data = data;
  }

  public S2SMessagePacket(long guid, int cmd, int sid, int seq, AbstractMessage message) {
    super(MESSAGE_PACKET_TYPE_SERVER_TO_SERVER);
    this.guid = guid;
    this.cmd = cmd;
    this.sid = sid;
    this.seq = seq;
    this.data = message.toByteArray();
  }

  public S2SMessagePacket() {
    super(MESSAGE_PACKET_TYPE_SERVER_TO_SERVER);
  }

  @Override
  protected short getHeadLength() {
    //      包头 默认长度  + cmd 长度(4) +  seq 长度(4) + sid 长度(4) + guid 长度(8)
    return (short) (getAbstractPacketLen() + 4 + 4 + 4 + 8);
  }

  @Override
  protected short getPacketLen() {
    return (short) (getHeadLength() + data.length);
  }

  @Override
  public void setSid(int sid) {
    this.sid = sid;
  }

  @Override
  public boolean encode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) {
    encode(byteBuf);
    byteBuf.writeInt(cmd);
    byteBuf.writeInt(seq);
    byteBuf.writeInt(sid);
    byteBuf.writeLong(guid);
    byteBuf.writeBytes(data);
    return true;
  }

  @Override
  public boolean decode(int packetLen, ByteBuf in) {
    this.data = new byte[getBodyLen(packetLen)];
    this.cmd = in.readInt();
    this.seq = in.readInt();
    this.sid = in.readInt();
    this.guid = in.readLong();
    in.readBytes(data);
    return true;
  }

  @Override
  public int getSeq() {
    return seq;
  }

  @Override
  public int getSid() {
    return sid;
  }

  @Override
  public String toString() {
    return "S2SMessagePacket{"
        + "cmd="
        + cmd
        + ", sid="
        + sid
        + ", seq="
        + seq
        + ", guid="
        + guid
        + ", data="
        + Arrays.toString(data)
        + '}';
  }
}
