package ly.net;

import com.google.protobuf.ByteString;
import ly.net.packet.AbstractMessagePacket;
import ly.net.packet.MessagePacketFactory;
import ly.ProtoMessageFactory;
import ly.proto.Cmd;
import ly.proto.Server;
import ly.rpc.RpcNodeConnector;
import ly.rpc.RpcService;
import ly.rpc.ReliableRpcStore;

/**
 * 网关客户端对象，保存账号、token、目标游戏服和长连接上下文。
 */
public class GateClient {
    private static final int LOGIN_RPC_TIMEOUT_MS = 10_000;

    private final GateConnectSession session;
    private String account;
    private long playerId;
    private long accountId;
    private String token;
    private String gameServerId;

    public GateClient(GateConnectSession session) {
        this.session = session;
    }

    public GateConnectSession getSession() {
        return session;
    }

    public long getSessionGuid() {
        return getAccountId();
    }

    public int getClientSid() {
        return session.getConnector().getSessionId();
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getGameServerId() {
        return gameServerId;
    }

    public void setGameServerId(String gameServerId) {
        this.gameServerId = gameServerId;
    }

    public void sendPacketToGameServer(AbstractMessagePacket csPacket) {
        // 普通客户端业务包已经通过登录态，Gate 不适合自行补偿业务失败；RPC 失败时保存给 Game 恢复后补发。
        Server.scGate2GameRpcGameCall resp = sendPacketToGameServerSync(csPacket, true);
        if (resp != null) {
            AbstractMessagePacket s2cPacket =
                    PacketCompat.createPacket(
                            getSessionGuid(),
                            csPacket.getCmd() + 1,
                            csPacket.getSid(),
                            csPacket.getSeq() + 1,
                            resp.getData().toByteArray());
            sendPacketToClient(s2cPacket);
        }
    }

    public Server.scGate2GameRpcGameCall sendPacketToGameServerSync(AbstractMessagePacket csPacket) {
        return sendPacketToGameServerSync(csPacket, false);
    }

    /**
     * 同步转发客户端包到 GameServer。
     *
     * <p>登录流程调用默认不保存，普通业务转发可开启 saveOnFailOrTimeout，把包装后的 RPC
     * 请求保存到可靠 outbox，等目标 GameServer 恢复后补发。
     */
    public Server.scGate2GameRpcGameCall sendPacketToGameServerSync(
            AbstractMessagePacket csPacket, boolean saveOnFailOrTimeout) {
        RpcNodeConnector rpcNodeConnector = RpcService.getInstance().getRpcNodeConnector(gameServerId);
        if (rpcNodeConnector == null || rpcNodeConnector.getClient() == null) {
            saveReliableRpcIfNeeded(csPacket, null, saveOnFailOrTimeout, "connector unavailable");
            return null;
        }

        int rpcSeq = rpcNodeConnector.getClient().getSendSeq();
        Server.csGate2GameRpcGameCall.Builder req = Server.csGate2GameRpcGameCall.newBuilder();
        req.setCmd(csPacket.getCmd());
        req.setSid(csPacket.getSid());
        req.setGuid(getSessionGuid());
        // 内层请求必须保留客户端原始 seq，Game 回对应响应时会使用 seq + 1。
        req.setSeq(csPacket.getSeq());
        req.setData(ByteString.copyFrom(csPacket.getData()));

        AbstractMessagePacket rpcPacket =
                MessagePacketFactory.createAbstractMessagePacket(
                        req.getGuid(),
                        Cmd.CMD.CS_Gate2GameRpcGameCall_VALUE,
                        req.build(),
                        rpcSeq,
                        rpcNodeConnector.getClient().getSid());
        if (!rpcNodeConnector.sendPacket(rpcPacket)) {
            saveReliableRpcIfNeeded(csPacket, req.build(), saveOnFailOrTimeout, "send failed");
            return null;
        }

        long deadline = System.currentTimeMillis() + LOGIN_RPC_TIMEOUT_MS;
        while (System.currentTimeMillis() < deadline) {
            AbstractMessagePacket response =
                    rpcNodeConnector
                            .getClient()
                            .getReceiveMsgBySeq(csPacket.getSeq() + 1, Cmd.CMD.SC_Gate2GameRpcGameCall_VALUE);
            if (response != null) {
                return (Server.scGate2GameRpcGameCall)
                        ProtoMessageFactory.createProtoMessage(
                                Cmd.CMD.SC_Gate2GameRpcGameCall_VALUE, response.getData());
            }

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return null;
            }
        }
        saveReliableRpcIfNeeded(csPacket, req.build(), saveOnFailOrTimeout, "response timeout");
        return null;
    }

    private void saveReliableRpcIfNeeded(
            AbstractMessagePacket csPacket,
            Server.csGate2GameRpcGameCall wrappedRequest,
            boolean saveOnFailOrTimeout,
            String reason) {
        if (!saveOnFailOrTimeout || csPacket == null || gameServerId == null) {
            return;
        }
        Server.csGate2GameRpcGameCall request = wrappedRequest;
        if (request == null) {
            // 连接器不可用时还没有分配 RPC seq，这里只保留客户端原始 cmd/data，补发时重新走 RPC 发送。
            request =
                    Server.csGate2GameRpcGameCall.newBuilder()
                            .setCmd(csPacket.getCmd())
                            .setSid(csPacket.getSid())
                            .setGuid(getSessionGuid())
                            .setSeq(csPacket.getSeq())
                            .setData(ByteString.copyFrom(csPacket.getData()))
                            .build();
        }
        AbstractMessagePacket reliablePacket =
                MessagePacketFactory.createAbstractMessagePacket(
                        request.getGuid(),
                        Cmd.CMD.CS_Gate2GameRpcGameCall_VALUE,
                        request,
                        0,
                        0);
        ReliableRpcStore.getInstance().save(gameServerId, reliablePacket, reason);
    }

    public void sendPacketToClient(AbstractMessagePacket s2cPacket) {
        session.sendPacket(s2cPacket);
    }

    public void closeConnection() {
        session.closeChannel();
    }
}
