package ly.net;

import ly.GateClientManager;
import ly.LoggerDef;
import ly.net.packet.AbstractMessagePacket;
import ly.net.packet.C2SMessagePacket;

public class GateConnectSession extends ConnectSession {
    public GateConnectSession(long guid) {
        super(guid);
    }

    @Override
    public void tick() {
    }

    @Override
    public void addReceivePacket(AbstractMessagePacket packet) {
        super.addReceivePacket(packet);
        LoggerDef.LogProto("receive {}|{}|{}|{}|{}", getGuid(), packet.getSid(), packet.getType(), packet.getCmd(), packet.getLength());
        if (packet instanceof C2SMessagePacket csPacket) {//网关收到客户端的包
            GateClient client = GateClientManager.getInstance().getClient(getGuid());
            if (client == null) {
                HandlerRouterManager.execute(this, csPacket);
            } else {
                client.sendPacketToGameServer(csPacket);
            }
        }
    }
}
