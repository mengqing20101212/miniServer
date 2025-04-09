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
      case AbstractMessagePacket.MESSAGE_PACKET_TYPE_SERVER_TO_CLIENT -> new S2CMessagePacket();
      case AbstractMessagePacket.MESSAGE_PACKET_TYPE_SERVER_TO_SERVER -> new S2SMessagePacket();
      case AbstractMessagePacket.MESSAGE_PACKET_TYPE_CLIENT_TO_SERVER -> new C2SMessagePacket();
    }
    return null;
  }

  public static S2SMessagePacket createS2SMessagePacket(
      long guid, int cmd, AbstractMessage protoData, int seq, int sid) {
    S2SMessagePacket messagePacket =
        new S2SMessagePacket(guid, cmd, sid, seq, protoData.toByteArray());
    return messagePacket;
  }
}
