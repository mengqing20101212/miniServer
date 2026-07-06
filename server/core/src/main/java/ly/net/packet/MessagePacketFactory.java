package ly.net.packet;

import com.google.protobuf.AbstractMessage;

/**
 * 协议包工厂。
 * <p>
 * 统一返回 {@link MessagePacket}，集中收口协议包创建逻辑，避免业务代码直接拼公共包头字段。
 */
public class MessagePacketFactory {
  private MessagePacketFactory() {}

  public static MessagePacket createMessagePacket() {
    return new MessagePacket();
  }

  // compatibility with old callsite
  public static MessagePacket createMessagePacket(int ignored) {
    return createMessagePacket();
  }

  public static MessagePacket createMessagePacket(
      long guid, int cmd, AbstractMessage protoData, int seq, int sid) {
    return new MessagePacket(guid, cmd, sid, seq, protoData.toByteArray());
  }

  public static MessagePacket copyMessagePacket(MessagePacket packet) {
    return new MessagePacket(
        packet.getGuid(), packet.getCmd(), packet.getSid(), packet.getSeq(), packet.getData());
  }

  public static MessagePacket createMessagePacket(int cmd, int seq, byte[] data) {
    MessagePacket packet = new MessagePacket(0, cmd, 0, seq, data);
    packet.setTime((int) (System.currentTimeMillis() / 1000L));
    return packet;
  }

  public static MessagePacket createMessagePacket(int cmd, byte[] data) {
    return createMessagePacket(cmd, 0, data);
  }

  /** @deprecated 使用 {@link #createMessagePacket(long, int, AbstractMessage, int, int)}。 */
  @Deprecated
  public static MessagePacket createAbstractMessagePacket(
      long guid, int cmd, AbstractMessage protoData, int seq, int sid) {
    return createMessagePacket(guid, cmd, protoData, seq, sid);
  }

  /** @deprecated 使用 {@link #createMessagePacket(int, int, byte[])}。 */
  @Deprecated
  public static MessagePacket createAbstractMessagePacket(int cmd, int seq, byte[] data) {
    return createMessagePacket(cmd, seq, data);
  }

  /** @deprecated 使用 {@link #createMessagePacket(int, byte[])}。 */
  @Deprecated
  public static MessagePacket createAbstractMessagePacket(int cmd, byte[] data) {
    return createMessagePacket(cmd, data);
  }
}
