package ly.net;

import ly.net.packet.C2SMessagePacket;
import ly.net.packet.MessagePacketFactory;
import ly.net.packet.S2CMessagePacket;
import ly.net.packet.S2SMessagePacket;
import ly.rpc.RpcUtils;

public class GameClient {
    private final GameConnectSession session;
    private String account;
    private long playerId;
    private long accountId;
    private String token;
    private String gameServerId;

    public GameClient(GameConnectSession session) {
        this.session = session;
    }

    public GameConnectSession getSession() {
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
        S2CMessagePacket s2cPacket = MessagePacketFactory.createS2CMessagePacket(s2sPacket.getCmd(), s2sPacket.getSeq(), s2sPacket.getData());
        session.sendPacket(s2cPacket);
    }

    public void closeConnection() {
        session.closeChannel();
    }
}
