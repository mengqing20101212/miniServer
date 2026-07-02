package ly.net.packet;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import java.util.Arrays;

/**
 * 网络层统一协议包。
 * <p>
 * 二进制格式固定为：
 * {@code [length:2][cmd:4][sid:4][seq:4][guid:8][time:4][data:N]}。
 * {@code length} 包含自身 2 字节和后续全部字段长度，因此最小包长为 22。
 * {@code data} 保存 protobuf 序列化后的业务消息，由上层根据 {@code cmd} 反序列化。
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

  /**
   * 创建连接确认包。
   * <p>
   * cmd 为 {@link #CMD_ACK}，sid 是服务端分配给该连接的会话 id。
   */
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

  /** 返回除 data 外的包头长度。 */
  protected short getHeadLength() {
    return 22;
  }

  /** 计算当前包完整长度，写包前会重新计算以同步 data 长度。 */
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

  /**
   * 将当前对象编码到 Netty 的输出缓冲区。
   *
   * @return 编码成功返回 {@code true}；异常时返回 {@code false} 并交由调用方记录日志
   */
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

  /**
   * 从输入缓冲区读取除 length 外的包体字段。
   * <p>
   * 调用方已经读出了 {@code packetLen}，这里按固定头结构继续读取 cmd、sid、seq、guid、
   * time 和 data。若包长小于头长，说明上游解码状态异常。
   */
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

  /** 返回适合协议日志使用的简短摘要，避免打印完整 data。 */
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
