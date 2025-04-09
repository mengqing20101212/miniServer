package ly.net.packet;

import com.google.protobuf.AbstractMessage;

/*
 * Author: liuYang
 * Date: 2025/4/8
 * File: MessagePacketFactory
 */
public class MessagePacketFactory {
  public static AbstractMessagePacket createMessagePacket(int type) {
    switch (type) {
      case AbstractMessagePacket.MESSAGE_PACKET_TYPE_SERVER_TO_CLIENT -> {
        return new S2CMessagePacket();
      }
      case AbstractMessagePacket.MESSAGE_PACKET_TYPE_SERVER_TO_SERVER -> {
        return new S2SMessagePacket();
      }
      case AbstractMessagePacket.MESSAGE_PACKET_TYPE_CLIENT_TO_SERVER -> {
        return new C2SMessagePacket();
      }
    }
    return null;
  }

  public static S2SMessagePacket createS2SMessagePacket(
      long guid, int cmd, AbstractMessage protoData, int seq, int sid) {
    S2SMessagePacket messagePacket =
        new S2SMessagePacket(guid, cmd, sid, seq, protoData.toByteArray());
    return messagePacket;
  }

  public static S2SMessagePacket copyMessagePacket(S2SMessagePacket packet) {
    S2SMessagePacket newPacket =
        new S2SMessagePacket(
            packet.getGuid(), packet.getCmd(), packet.getSid(), packet.getSeq(), packet.getData());
    return newPacket;
  }
}
