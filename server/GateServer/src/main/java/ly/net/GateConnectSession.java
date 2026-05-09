package ly.net;

import com.google.protobuf.AbstractMessage;
import com.google.protobuf.Message;
import ly.GateClientManager;
import ly.LoggerDef;
import ly.ProtoMessageFactory;
import ly.net.packet.AbstractMessagePacket;
import ly.proto.Cmd;
import ly.proto.Server;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 网关连接会话，封装客户端连接、收发队列和网关转发所需状态。
 */
public class GateConnectSession extends ConnectSession {
    private final AtomicBoolean receiveWorkerStarted = new AtomicBoolean();
    private volatile Thread receiveWorker;

    public GateConnectSession(long guid) {
        super(guid);
        startReceiveWorker();
    }

    @Override
    public void tick() {
    }

    @Override
    public void addReceivePacket(AbstractMessagePacket packet) {
        super.addReceivePacket(packet);
    }

    private void startReceiveWorker() {
        if (!receiveWorkerStarted.compareAndSet(false, true)) {
            return;
        }
        receiveWorker = Thread.ofVirtual()
                .name("gate-session-receive-" + getGuid())
                .start(
                        () -> {
                            while (!Thread.currentThread().isInterrupted()) {
                                try {
                                    AbstractMessagePacket packet = receivePacketQueue.take();
                                    handleReceivePacket(packet);
                                } catch (InterruptedException e) {
                                    Thread.currentThread().interrupt();
                                } catch (Exception e) {
                                    LoggerDef.SystemLogger.error(
                                            "GateConnectSession handle receive packet error, guid={}",
                                            getGuid(),
                                            e);
                                }
                            }
                        });
    }

    @Override
    public void closeChannel() {
        Thread worker = receiveWorker;
        if (worker != null) {
            worker.interrupt();
        }
        super.closeChannel();
    }

    private void handleReceivePacket(AbstractMessagePacket packet) {
        LoggerDef.LogProto("receive {}|{}|{}|{}", getGuid(), packet.getSid(), packet.getCmd(), packet.getLength());

        boolean serverInnerCmd =
                packet.getCmd() > Cmd.CMD.CS_Server2Server_VALUE
                        && packet.getCmd() <= Cmd.CMD.MaxServeMsgId_VALUE;

        if (!serverInnerCmd && packet.getCmd() != Cmd.CMD.SC_Logout_VALUE) {
            AbstractMessagePacket csPacket = packet;
            GateClient client = GateClientManager.getInstance().getClient(getGuid());
            if (client == null) {
                // 登录后的客户端业务包可能携带 playerId 作为 guid，Gate 连接定位必须按 sid 兜底。
                client = GateClientManager.getInstance().getClientBySid(csPacket.getSid());
            }

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
                    client = GateClientManager.getInstance().getClientBySid(s2sPacket.getSid());
                }
                if (client != null) {
                    if (s2sPacket.getCmd() == Cmd.CMD.SC_Gate2GameRpcGameCall_VALUE) {
                        // GameServer 包装给客户端的消息时已经写入真实 cmd/seq/sid，Gate 只负责解包转发。
                        Server.scGate2GameRpcGameCall resp =
                                (Server.scGate2GameRpcGameCall)
                                        ProtoMessageFactory.createProtoMessage(
                                                Cmd.CMD.SC_Gate2GameRpcGameCall_VALUE, s2sPacket.getData());
                        client.sendGameResponseToClient(resp);
                    } else {
                        client.sendPacketToClient(s2sPacket);
                    }
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
