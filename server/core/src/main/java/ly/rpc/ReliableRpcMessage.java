package ly.rpc;

import java.io.Serializable;
import java.util.Arrays;
import ly.net.packet.MessagePacket;

/**
 * 保存到 Redis 的可靠 RPC 消息快照，用于目标服恢复后补发。
 *
 * <p>快照只保存协议包必要字段，不保存同步调用栈和响应等待状态；补发是一次新的异步投递。
 */
public class ReliableRpcMessage implements Serializable {
  private static final long serialVersionUID = 1L;

  private String msgId;
  private String sourceServerId;
  private String targetServerId;
  private int cmd;
  private int sid;
  private int seq;
  private long guid;
  private int time;
  private byte[] data = new byte[0];
  private int retryCount;
  private long createdAt;
  private long nextRetryAt;
  private String reason;

  public static ReliableRpcMessage from(
      String msgId,
      String sourceServerId,
      String targetServerId,
      MessagePacket packet,
      String reason) {
    ReliableRpcMessage message = new ReliableRpcMessage();
    message.msgId = msgId;
    message.sourceServerId = sourceServerId;
    message.targetServerId = targetServerId;
    message.cmd = packet.getCmd();
    // 补发会使用新 TCP 连接和新 session，不能复用失败时旧连接上的 sid/seq。
    message.sid = 0;
    message.seq = 0;
    message.guid = packet.getGuid();
    message.time = packet.getTime();
    message.data = packet.getData() == null ? new byte[0] : Arrays.copyOf(packet.getData(), packet.getData().length);
    message.retryCount = 0;
    message.createdAt = System.currentTimeMillis();
    message.nextRetryAt = message.createdAt;
    message.reason = reason;
    return message;
  }

  public MessagePacket toPacket() {
    // 重建协议包时保持 guid/cmd/data 不变，sid/seq 使用保存时归零后的值。
    MessagePacket packet =
        new MessagePacket(guid, cmd, sid, seq, data == null ? new byte[0] : data);
    packet.setTime(time);
    return packet;
  }

  public String getMsgId() {
    return msgId;
  }

  public String getSourceServerId() {
    return sourceServerId;
  }

  public String getTargetServerId() {
    return targetServerId;
  }

  public int getCmd() {
    return cmd;
  }

  public int getRetryCount() {
    return retryCount;
  }

  public void increaseRetryCount(long nextRetryAt) {
    retryCount++;
    this.nextRetryAt = nextRetryAt;
  }

  public long getNextRetryAt() {
    return nextRetryAt;
  }

  public long getCreatedAt() {
    return createdAt;
  }

  public String getReason() {
    return reason;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof ReliableRpcMessage other)) {
      return false;
    }
    return msgId != null && msgId.equals(other.msgId);
  }

  @Override
  public int hashCode() {
    return msgId == null ? 0 : msgId.hashCode();
  }
}
