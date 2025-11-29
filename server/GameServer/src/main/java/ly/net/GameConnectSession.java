package ly.net;

import com.google.protobuf.AbstractMessage;
import ly.LoggerDef;
import ly.net.packet.AbstractMessagePacket;
import ly.net.packet.MessagePacketFactory;
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
            // 使用新的execute方法处理消息
            GameHandlerRouteManager.execute(this, s2sPacket);
        }
    }


    public void sendClientMsg(int cmd, int seq, long playerId, AbstractMessage msg) {
        S2SMessagePacket s2cPacket = MessagePacketFactory.createS2SMessagePacket(playerId, cmd, msg, seq, (int) getGuid());
        addSendPacket(s2cPacket);
    }
}