package ly.net.packet;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import java.util.Arrays;

/*
 * Unified packet implementation:
 * [length:2][cmd:4][sid:4][seq:4][guid:8][time:4][data:N]
 */
public class AbstractMessagePacket {
  public static final int CMD_ACK = 0;
  private short length;
  private int cmd;
  private int sid;
  private int seq;
  private long guid;
  private int time;
  private byte[] data = new byte[0];

  public AbstractMessagePacket() {
    this.time = (int) (System.currentTimeMillis() / 1000L);
  }

  // ack packet
  public AbstractMessagePacket(int sessionId) {
    this();
    this.cmd = CMD_ACK;
    this.sid = sessionId;
  }

  public AbstractMessagePacket(long guid, int cmd, int seq, byte[] data) {
    this(guid, cmd, 0, seq, data);
  }

  public AbstractMessagePacket(long guid, int cmd, int sid, int seq, byte[] data) {
    this();
    this.guid = guid;
    this.cmd = cmd;
    this.sid = sid;
    this.seq = seq;
    this.data = data == null ? new byte[0] : data;
    this.length = getPacketLen();
  }

  public int getLength() {
    return length;
  }

  public void setLength(short length) {
    this.length = length;
  }

  public byte[] getData() {
    return data;
  }

  public void setData(byte[] data) {
    this.data = data == null ? new byte[0] : data;
  }

  public void setSid(int sid) {
    this.sid = sid;
  }

  public void setSeq(int seq) {
    this.seq = seq;
  }

  public int getSeq() {
    return seq;
  }

  public int getSid() {
    return sid;
  }

  public long getGuid() {
    return guid;
  }

  public void setGuid(long guid) {
    this.guid = guid;
  }

  protected short getHeadLength() {
    return 22;
  }

  protected short getPacketLen() {
    return (short) (getHeadLength() + data.length);
  }

  public int getCmd() {
    return cmd;
  }

  public void setCmd(int cmd) {
    this.cmd = cmd;
  }

  public int getTime() {
    return time;
  }

  public void setTime(int time) {
    this.time = time;
  }

  public boolean encode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) {
    try {
      if (data == null) {
        data = new byte[0];
      }
      this.length = getPacketLen();
      byteBuf.writeShort(length);
      byteBuf.writeInt(cmd);
      byteBuf.writeInt(sid);
      byteBuf.writeInt(seq);
      byteBuf.writeLong(guid);
      byteBuf.writeInt(time);
      byteBuf.writeBytes(data);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  public boolean decode(int packetLen, ByteBuf in) {
    try {
      this.length = (short) packetLen;
      this.cmd = in.readInt();
      this.sid = in.readInt();
      this.seq = in.readInt();
      this.guid = in.readLong();
      this.time = in.readInt();
      int bodyLen = packetLen - getHeadLength();
      if (bodyLen < 0) {
        return false;
      }
      this.data = new byte[bodyLen];
      if (bodyLen > 0) {
        in.readBytes(this.data);
      }
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  public String toSimpleString() {
    return String.format("%d|%d|%d|%d|%d|%d", cmd, sid, seq, guid, data.length, getPacketLen());
  }

  @Override
  public String toString() {
    return "MessagePacket{"
        + "cmd="
        + cmd
        + ", sid="
        + sid
        + ", seq="
        + seq
        + ", guid="
        + guid
        + ", time="
        + time
        + ", data="
        + Arrays.toString(data)
        + '}';
  }
}
