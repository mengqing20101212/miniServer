package ly.net;

import ly.net.packet.C2SMessagePacket;
import ly.rpc.RpcNodeConnector;

public class GateClient {
    private final GateConnectSession session;
    private RpcNodeConnector gameRpcNodeConnector;
    private String account;
    private long playerId;
    private String token;

    public GateClient(GateConnectSession session) {
        this.session = session;
    }

    public GateConnectSession getSession() {
        return session;
    }

    public long getSessionGuid() {
        return session.getGuid();
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
        
    }
}
