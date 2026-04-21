package ly.net;

import com.google.protobuf.ByteString;
import com.google.protobuf.AbstractMessage;
import ly.ProtoMessageFactory;
import ly.net.packet.AbstractMessagePacket;
import ly.net.packet.MessagePacketFactory;
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
        // 发送给游戏服务器
        Server.scGate2GameRpcGameCall resp = sendPacketToGameServerSync(csPacket);
        if (resp != null) {
            AbstractMessage respProto = ProtoMessageFactory.createProtoMessage(csPacket.getCmd(), resp.getData().toByteArray());
            if (respProto == null) {
                return;
            }
            AbstractMessagePacket s2cPacket = MessagePacketFactory.createAbstractMessagePacket(
                    getSessionGuid(), csPacket.getCmd(), respProto, csPacket.getSeq(), csPacket.getSid());
            sendPacketToClient(s2cPacket);
        }

    }

    // 网关调用游戏服务器Rpc方法
//    message csGate2GameRpcGameCall{
//        int32 cmd = 1;// client 发送给gate 请求 cmd
//        int32 sid = 2;// client 发送给gate 请求 会话id
//        int64 guid = 3;// client 发送给gate 请求 玩家id
//        int32 seq = 4;// client 发送给gate 请求 序列id
//        bytes data = 5;// client 发送给gate 请求 数据
//    }
    public Server.scGate2GameRpcGameCall sendPacketToGameServerSync(AbstractMessagePacket csPacket) {
        // 发送给游戏服务器
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
