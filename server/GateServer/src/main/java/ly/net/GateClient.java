package ly.net;

import com.google.protobuf.ByteString;

import java.util.concurrent.atomic.AtomicInteger;

import com.google.protobuf.Message;

import ly.LoggerDef;
import ly.ProtoMessageFactory;
import ly.net.packet.AbstractMessagePacket;
import ly.net.packet.MessagePacketFactory;
import ly.proto.Cmd;
import ly.proto.Server;
import ly.rpc.ReliableRpcStore;
import ly.rpc.RpcNodeConnector;
import ly.rpc.RpcService;

/**
 * Gate 侧客户端上下文，保存账号、目标 GameServer、客户端连接 sid 和下行 seq。
 */
public class GateClient {
    private static final int LOGIN_RPC_TIMEOUT_MS = 10_000;

    private final GateConnectSession session;
    private final AtomicInteger clientDownSeq = new AtomicInteger();
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
        return playerId > 0 ? playerId : getAccountId();
    }

    public int getClientSid() {
        return session.getConnector().getSessionId();
    }

    public int nextClientDownSeq() {
        return clientDownSeq.incrementAndGet();
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
        // 普通业务包失败后保存到可靠 RPC outbox，等目标 GameServer 恢复后补发。
        Server.scGate2GameRpcGameCall resp = sendPacketToGameServerSync(csPacket, true);
        if (resp != null) {
            sendGameResponseToClient(resp);
        }
    }

    public Server.scGate2GameRpcGameCall sendPacketToGameServerSync(AbstractMessagePacket csPacket) {
        return sendPacketToGameServerSync(csPacket, false);
    }

    /**
     * Send packet to GameServer and parse the inner response payload into the
     * specified protobuf type.
     * Returns null on timeout, parse error or if response is missing.
     */
    public <T extends com.google.protobuf.AbstractMessage> T sendPacketToGameServerSync(
            AbstractMessagePacket csPacket, Class<T> protoClass) {
        Server.scGate2GameRpcGameCall resp = sendPacketToGameServerSync(csPacket, false);
        if (resp == null) {
            return null;
        }
        return ProtoMessageFactory.createProtoMessage(resp.getClientCmd(), resp.getData().toByteArray(), protoClass);
    }

    /**
     * 同步转发客户端包到 GameServer。
     *
     * <p>
     * RPC 外层 seq 只属于 Gate 和 Game 的 socket；客户端 sid/上行 seq 保存在内层协议里。
     * 等待响应时必须使用 callId，不能再用客户端 seq 推导响应包。
     */
    public Server.scGate2GameRpcGameCall sendPacketToGameServerSync(
            AbstractMessagePacket csPacket, boolean saveOnFailOrTimeout) {
        RpcNodeConnector rpcNodeConnector = RpcService.getInstance().getRpcNodeConnector(gameServerId);
        long callId = newCallId();
        if (rpcNodeConnector == null || rpcNodeConnector.getClient() == null) {
            saveReliableRpcIfNeeded(csPacket, null, saveOnFailOrTimeout, "connector unavailable");
            return null;
        }

        int rpcSeq = rpcNodeConnector.getClient().getSendSeq();
        Server.csGate2GameRpcGameCall req = Server.csGate2GameRpcGameCall.newBuilder()
                .setClientCmd(csPacket.getCmd())
                .setClientSid(csPacket.getSid())
                .setGuid(csPacket.getGuid())
                .setClientReqSeq(csPacket.getSeq())
                .setData(ByteString.copyFrom(csPacket.getData()))
                .setCallId(callId)
                .build();

        AbstractMessagePacket rpcPacket = MessagePacketFactory.createAbstractMessagePacket(
                req.getGuid(),
                Cmd.CMD.CS_Gate2GameRpcGameCall_VALUE,
                req,
                rpcSeq,
                rpcNodeConnector.getClient().getSid());
        LoggerDef.LogNet(String.format(">>%s, guid:%d, clientSid:%d, cmd:%s, clientSeq:%d, len:%d sid:%d, seq:%d",
                gameServerId, req.getGuid(), csPacket.getSid(), Cmd.CMD.forNumber(csPacket.getCmd()).name(),
                csPacket.getSeq(),
                csPacket.getLength(), rpcPacket.getSid(), rpcSeq));
        if (!rpcNodeConnector.sendPacket(rpcPacket)) {
            saveReliableRpcIfNeeded(csPacket, req, saveOnFailOrTimeout, "send failed");
            return null;
        }

        long deadline = System.currentTimeMillis() + LOGIN_RPC_TIMEOUT_MS;
        while (System.currentTimeMillis() < deadline) {
            AbstractMessagePacket response = rpcNodeConnector
                    .getClient()
                    .getReceiveMsgByCallId(callId, Cmd.CMD.SC_Gate2GameRpcGameCall_VALUE);
            if (response != null) {
                Server.scGate2GameRpcGameCall resp = (Server.scGate2GameRpcGameCall) ProtoMessageFactory
                        .createProtoMessage(
                                Cmd.CMD.SC_Gate2GameRpcGameCall_VALUE, response.getData());
                LoggerDef.LogNet(
                        String.format("<<%s, guid:%d, clientSid:%d, cmd:%s, len:%d sid:%d, seq:%d",
                                gameServerId, req.getGuid(), csPacket.getSid(),
                                Cmd.CMD.forNumber(resp.getClientCmd()).name(),
                                resp.getData().size(), response.getSid(), response.getSeq()));
                return resp;
            }

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return null;
            }
        }
        saveReliableRpcIfNeeded(csPacket, req, saveOnFailOrTimeout, "response timeout");
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
            // 连接不可用时还没有 RPC 外层 seq，这里只保存客户端原始包，补发时重新走 RPC 发送。
            request = Server.csGate2GameRpcGameCall.newBuilder()
                    .setClientCmd(csPacket.getCmd())
                    .setClientSid(csPacket.getSid())
                    .setGuid(csPacket.getGuid())
                    .setClientReqSeq(csPacket.getSeq())
                    .setData(ByteString.copyFrom(csPacket.getData()))
                    .build();
        }
        AbstractMessagePacket reliablePacket = MessagePacketFactory.createAbstractMessagePacket(
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

    public void sendGameResponseToClient(Server.scGate2GameRpcGameCall resp) {
        if (resp == null) {
            return;
        }
        // 客户端下行 seq 只在 Gate 连接维度递增，Game 不参与生成。
        AbstractMessagePacket s2cPacket = new AbstractMessagePacket(
                getSessionGuid(),
                resp.getClientCmd(),
                resp.getClientSid(),
                nextClientDownSeq(),
                resp.getData().toByteArray());
        LoggerDef.LogNet(String.format(">>client, guid:%d, clientSid:%d, seq:%d, cmd:%s, len:%d",
                getSessionGuid(), resp.getClientSid(), s2cPacket.getSeq(),
                Cmd.CMD.forNumber(resp.getClientCmd()).name(), resp.getData().size()));
        sendPacketToClient(s2cPacket);
    }

    private long newCallId() {
        return System.nanoTime() ^ Thread.currentThread().threadId();
    }

    public void closeConnection() {
        session.closeChannel();
    }
}
