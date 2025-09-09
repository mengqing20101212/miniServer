package ly.net;

import com.google.protobuf.Message;
import ly.LoggerDef;
import ly.net.packet.AbstractMessagePacket;
import ly.net.packet.MessagePacketFactory;
import ly.net.packet.S2CMessagePacket;
import ly.net.packet.S2SMessagePacket;

public class GameConnectSession extends ConnectSession {
    public GameConnectSession(long guid) {
        super(guid);
    }

    @Override
    public void tick() {
    }

    @Override
    public void addReceivePacket(AbstractMessagePacket packet) {
        super.addReceivePacket(packet);
        LoggerDef.LogProto("receive {}|{}|{}|{}|{}", getGuid(), packet.getSid(), packet.getType(), packet.getCmd(), packet.getLength());
        if (packet instanceof S2SMessagePacket s2sPacket) {
            HandlerRouterManager.execute(this, s2sPacket);
        }
    }


    public void sendClientMsg(int cmd, Message msg) {
        S2CMessagePacket s2cPacket = MessagePacketFactory.createS2CMessagePacket(cmd, msg.toByteArray());
        addSendPacket(s2cPacket);
    }
}
