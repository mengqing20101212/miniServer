package ly.net.packet;

import com.google.protobuf.AbstractMessage;

public class MessagePacketFactory {
  private MessagePacketFactory() {}

  public static AbstractMessagePacket createMessagePacket() {
    return new AbstractMessagePacket();
  }

  // compatibility with old callsite
  public static AbstractMessagePacket createMessagePacket(int ignored) {
    return createMessagePacket();
  }

  public static AbstractMessagePacket createAbstractMessagePacket(
      long guid, int cmd, AbstractMessage protoData, int seq, int sid) {
    return new AbstractMessagePacket(guid, cmd, sid, seq, protoData.toByteArray());
  }

  public static AbstractMessagePacket copyMessagePacket(AbstractMessagePacket packet) {
    return new AbstractMessagePacket(
        packet.getGuid(), packet.getCmd(), packet.getSid(), packet.getSeq(), packet.getData());
  }

  public static AbstractMessagePacket createAbstractMessagePacket(int cmd, int seq, byte[] data) {
    AbstractMessagePacket packet = new AbstractMessagePacket(0, cmd, 0, seq, data);
    packet.setTime((int) (System.currentTimeMillis() / 1000L));
    return packet;
  }

  public static AbstractMessagePacket createAbstractMessagePacket(int cmd, byte[] data) {
    return createAbstractMessagePacket(cmd, 0, data);
  }
}
