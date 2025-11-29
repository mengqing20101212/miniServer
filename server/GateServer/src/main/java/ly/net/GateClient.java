package ly.net;

import com.google.protobuf.ByteString;
import ly.net.packet.C2SMessagePacket;
import ly.net.packet.MessagePacketFactory;
import ly.net.packet.S2CMessagePacket;
import ly.net.packet.S2SMessagePacket;
import ly.proto.Server;
import ly.rpc.RpcUtils;

public class GateClient {
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

    public void sendPacketToGameServer(C2SMessagePacket csPacket) {
        // 发送给游戏服务器
        Server.scGate2GameRpcGameCall resp = sendPacketToGameServerSync(csPacket);
        if (resp != null) {
            S2CMessagePacket s2cPacket = MessagePacketFactory.createS2CMessagePacket(csPacket.getCmd(), csPacket.getSeq(), resp.getData().toByteArray());
            sendPacketToClient(s2cPacket);
        }

    }

    public Server.scGate2GameRpcGameCall sendPacketToGameServerSync(C2SMessagePacket csPacket) {
        // 发送给游戏服务器
        Server.csGate2GameRpcGameCall.Builder req = Server.csGate2GameRpcGameCall.newBuilder();
        req.setCmd(csPacket.getCmd());
        req.setSeq(csPacket.getSeq());
        req.setData(ByteString.copyFrom(csPacket.getData()));

        return RpcUtils.syncRequest(gameServerId, req.getGuid(), csPacket.getCmd(), req.build());
    }


    public void sendPacketToClient(S2SMessagePacket s2sPacket) {
        S2CMessagePacket s2cPacket = MessagePacketFactory.createS2CMessagePacket(s2sPacket.getCmd(), s2sPacket.getSeq(), s2sPacket.getData());
        session.sendPacket(s2cPacket);
    }

    public void sendPacketToClient(S2CMessagePacket s2cPacket) {
        session.sendPacket(s2cPacket);

    }

    public void closeConnection() {
        session.closeChannel();
    }
}
