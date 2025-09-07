package ly.net;

import ly.net.packet.C2SMessagePacket;
import ly.net.packet.S2SMessagePacket;
import ly.rpc.RpcNodeConnector;
import ly.rpc.RpcUtils;

public class GateClient {
    private final GateConnectSession session;
    private RpcNodeConnector gameRpcNodeConnector;
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
        RpcUtils.request(gameServerId, csPacket);
    }


    public void sendPacketToClient(S2SMessagePacket s2sPacket) {
        
    }
}
