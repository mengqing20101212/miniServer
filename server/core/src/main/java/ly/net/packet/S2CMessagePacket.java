package ly.net.packet;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import java.util.Arrays;

/*
 * Author: liuYang
 * Date: 2025/4/8
 * File: MessagePacket
 */
public class S2CMessagePacket extends AbstractMessagePacket {

  /***消息号*/
  private int cmd;

  /** 会话id session id */
  private int sid;

  /** 包的序列号，自增 客户端用来检查是否丢包 */
  private int seq;

  /** 服务器时间戳 秒* */
  private int time;

  /** PB 消息 */
  private byte[] data;

  public S2CMessagePacket(int cmd, int seq, int sid, byte[] data) {
    super(MESSAGE_PACKET_TYPE_SERVER_TO_CLIENT);
    this.cmd = cmd;
    this.seq = seq;
    this.sid = sid;
    this.data = data;
    this.time = (int) (System.currentTimeMillis() / 1000L);
    setLength(getPacketLen());
  }

  public S2CMessagePacket() {
    super(AbstractMessagePacket.MESSAGE_PACKET_TYPE_SERVER_TO_CLIENT);
  }

  @Override
  protected short getHeadLength() {
    //      包头 默认长度 + cmd 长度 +  seq 长度 + sid 长度 + time 长度
    return (short) (getAbstractPacketLen() + 4 + 4 + 4 + 4);
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
  public void setSeq(int seq) {
    this.seq = seq;
  }

  @Override
  public boolean encode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) {
    try {
      encode(byteBuf);
      byteBuf.writeInt(cmd);
      byteBuf.writeInt(seq);
      byteBuf.writeInt(sid);
      byteBuf.writeInt(time);
      byteBuf.writeBytes(data);
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  @Override
  public boolean decode(int packetLen, ByteBuf in) {
    int bodyLen = getBodyLen(packetLen);
    this.cmd = in.readInt();
    this.seq = in.readInt();
    this.sid = in.readInt();
    this.time = in.readInt();
    this.data = new byte[bodyLen];
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
    return "S2CMessagePacket{"
        + "cmd="
        + cmd
        + ", sid="
        + sid
        + ", seq="
        + seq
        + ", time="
        + time
        + ", data="
        + Arrays.toString(data)
        + '}';
  }
}
