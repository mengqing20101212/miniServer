package ly.net;

import com.google.protobuf.ByteString;

import ly.net.packet.AbstractMessagePacket;
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

    public void sendPacketToGameServer(AbstractMessagePacket csPacket) {
        // 鍙戦€佺粰娓告垙鏈嶅姟鍣?
        Server.scGate2GameRpcGameCall resp = sendPacketToGameServerSync(csPacket);
        if (resp != null) {
            AbstractMessagePacket s2cPacket = PacketCompat.createPacket(
                    getSessionGuid(), csPacket.getCmd(), csPacket.getSid(), csPacket.getSeq(), resp.getData().toByteArray());
            sendPacketToClient(s2cPacket);
        }

    }

    // 缃戝叧璋冪敤娓告垙鏈嶅姟鍣≧pc鏂规硶
//    message csGate2GameRpcGameCall{
//        int32 cmd = 1;// client 鍙戦€佺粰gate 璇锋眰 cmd
//        int32 sid = 2;// client 鍙戦€佺粰gate 璇锋眰 浼氳瘽id
//        int64 guid = 3;// client 鍙戦€佺粰gate 璇锋眰 鐜╁id
//        int32 seq = 4;// client 鍙戦€佺粰gate 璇锋眰 搴忓垪id
//        bytes data = 5;// client 鍙戦€佺粰gate 璇锋眰 鏁版嵁
//    }
    public Server.scGate2GameRpcGameCall sendPacketToGameServerSync(AbstractMessagePacket csPacket) {
        // 鍙戦€佺粰娓告垙鏈嶅姟鍣?
        Server.csGate2GameRpcGameCall.Builder req = Server.csGate2GameRpcGameCall.newBuilder();
        req.setCmd(csPacket.getCmd());
        req.setSid(csPacket.getSid());
        req.setGuid(getSessionGuid());
        req.setSeq(csPacket.getSeq());
        req.setData(ByteString.copyFrom(csPacket.getData()));

        return RpcUtils.syncRequest(gameServerId, req.getGuid(), csPacket.getCmd(), req.build());
    }


    public void sendPacketToClient(AbstractMessagePacket s2cPacket) {
        session.sendPacket(s2cPacket);

    }

    public void closeConnection() {
        session.closeChannel();
    }
}
