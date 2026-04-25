package ly.net;

import com.google.protobuf.AbstractMessage;
import com.google.protobuf.Message;
import ly.GateClientManager;
import ly.LoggerDef;
import ly.net.packet.AbstractMessagePacket;
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
        LoggerDef.LogProto("receive {}|{}|{}|{}", getGuid(), packet.getSid(), packet.getCmd(), packet.getLength());

        boolean serverInnerCmd =
                packet.getCmd() > Cmd.CMD.CS_Server2Server_VALUE
                        && packet.getCmd() <= Cmd.CMD.MaxServeMsgId_VALUE;

        if (!serverInnerCmd && packet.getCmd() != Cmd.CMD.SC_Logout_VALUE) {
            AbstractMessagePacket csPacket = packet;
            GateClient client = GateClientManager.getInstance().getClient(getGuid());

            if (client == null) {
                try {
                    // Login is handled asynchronously. Keep the channel open for the response.
                    HandlerRouterManager.execute(this, csPacket);
                } catch (Exception e) {
                    LoggerDef.SystemLogger.error(
                            "GateConnectSession addReceivePacket error, cmd={}", csPacket.getCmd(), e);
                    e.printStackTrace();
                }
            } else {
                client.sendPacketToGameServer(csPacket);
            }
        } else {
            AbstractMessagePacket s2sPacket = packet;
            if (s2sPacket.getCmd() == Cmd.CMD.SC_Logout_VALUE) {
                HandlerRouterManager.execute(this, s2sPacket);
            } else {
                GateClient client = GateClientManager.getInstance().getClient(getGuid());
                if (client == null) {
                    client = GateClientManager.getInstance().getClient((long) s2sPacket.getSid());
                }
                if (client != null) {
                    client.sendPacketToClient(s2sPacket);
                }
            }
        }
    }

    public void sendClientMsg(int cmd, Message msg) {
        if (!(msg instanceof AbstractMessage abstractMessage)) {
            throw new IllegalArgumentException("msg must extend AbstractMessage");
        }
        AbstractMessagePacket s2cPacket =
                PacketCompat.createPacket(getGuid(), cmd, 0, 0, abstractMessage.toByteArray());
        addSendPacket(s2cPacket);
    }
}
