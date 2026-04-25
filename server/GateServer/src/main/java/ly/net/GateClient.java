package ly.net;

import com.google.protobuf.ByteString;
import ly.net.packet.AbstractMessagePacket;
import ly.net.packet.MessagePacketFactory;
import ly.ProtoMessageFactory;
import ly.proto.Cmd;
import ly.proto.Server;
import ly.rpc.RpcNodeConnector;
import ly.rpc.RpcService;

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
        Server.scGate2GameRpcGameCall resp = sendPacketToGameServerSync(csPacket);
        if (resp != null) {
            AbstractMessagePacket s2cPacket =
                    PacketCompat.createPacket(
                            getSessionGuid(),
                            csPacket.getCmd(),
                            csPacket.getSid(),
                            csPacket.getSeq(),
                            resp.getData().toByteArray());
            sendPacketToClient(s2cPacket);
        }
    }

    public Server.scGate2GameRpcGameCall sendPacketToGameServerSync(AbstractMessagePacket csPacket) {
        RpcNodeConnector rpcNodeConnector = RpcService.getInstance().getRpcNodeConnector(gameServerId);
        if (rpcNodeConnector == null || rpcNodeConnector.getClient() == null) {
            return null;
        }

        int rpcSeq = rpcNodeConnector.getClient().getSendSeq();
        Server.csGate2GameRpcGameCall.Builder req = Server.csGate2GameRpcGameCall.newBuilder();
        req.setCmd(csPacket.getCmd());
        req.setSid(csPacket.getSid());
        req.setGuid(getSessionGuid());
        // Keep inner and outer seq aligned so Game's wrapped response can be matched.
        req.setSeq(rpcSeq);
        req.setData(ByteString.copyFrom(csPacket.getData()));

        AbstractMessagePacket rpcPacket =
                MessagePacketFactory.createAbstractMessagePacket(
                        req.getGuid(),
                        Cmd.CMD.CS_Gate2GameRpcGameCall_VALUE,
                        req.build(),
                        rpcSeq,
                        rpcNodeConnector.getClient().getSid());
        if (!rpcNodeConnector.sendPacket(rpcPacket)) {
            return null;
        }

        long deadline = System.currentTimeMillis() + LOGIN_RPC_TIMEOUT_MS;
        while (System.currentTimeMillis() < deadline) {
            AbstractMessagePacket response =
                    rpcNodeConnector
                            .getClient()
                            .getReceiveMsgBySeq(rpcSeq, Cmd.CMD.SC_Gate2GameRpcGameCall_VALUE);
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
        return null;
    }

    public void sendPacketToClient(AbstractMessagePacket s2cPacket) {
        session.sendPacket(s2cPacket);
    }

    public void closeConnection() {
        session.closeChannel();
    }
}
