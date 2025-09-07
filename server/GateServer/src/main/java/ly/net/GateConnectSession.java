package ly.net;

import com.google.protobuf.Message;
import ly.GateClientManager;
import ly.LoggerDef;
import ly.net.packet.*;
import ly.proto.Cmd;

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
                try {
                    HandlerRouterManager.execute(this, csPacket);
                    closeChannel();
                } catch (Exception e) {
                    LoggerDef.SystemLogger.error("GateConnectSession addReceivePacket error, cmd={}", csPacket.getCmd(), e);
                    e.printStackTrace();
                }
            } else {
                client.sendPacketToGameServer(csPacket);
            }
        } else if (packet instanceof S2SMessagePacket s2sPacket) {
            if (s2sPacket.getCmd() == Cmd.CMD.SC_Logout_VALUE) {
                HandlerRouterManager.execute(this, s2sPacket);
            } else {
                GateClient client = GateClientManager.getInstance().getClient(s2sPacket.getGuid());
                if (client != null) {
                    client.sendPacketToClient(s2sPacket);
                }
            }
        }
    }


    public void sendClientMsg(int cmd, Message msg) {
        S2CMessagePacket s2cPacket = MessagePacketFactory.createS2CMessagePacket(cmd, msg.toByteArray());
        addSendPacket(s2cPacket);
    }
}
