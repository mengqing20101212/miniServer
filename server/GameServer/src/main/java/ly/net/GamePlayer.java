package ly.net;

import com.google.protobuf.AbstractMessage;
import ly.LoggerDef;
import ly.logic.player.Player;
import ly.net.packet.S2SMessagePacket;
import ly.proto.Cmd;

import java.util.concurrent.ArrayBlockingQueue;

public class GamePlayer {
    private final GameConnectSession session;
    private String account;
    private long playerId;
    private long accountId;
    private String token;
    private String gameServerId;
    private Player player;

    private int lastSeq;
    private int lastClientCmd;

    ArrayBlockingQueue<S2SMessagePacket> packetQueue = new ArrayBlockingQueue<>(100);

    public GamePlayer(GameConnectSession session) {
        this.session = session;
    }

    public GameConnectSession getSession() {
        return session;
    }

    public long getSessionGuid() {
        return getPlayerId();
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


    public void closeConnection() {
        session.closeChannel();
    }

    public int getLastClientCmd() {
        return lastClientCmd;
    }

    public void setLastClientCmd(int lastClientCmd) {
        this.lastClientCmd = lastClientCmd;
    }

    public void addPacket(S2SMessagePacket packet) {
        if (packet.getCmd() == Cmd.CMD.CS_Gate2GameRpcGameCall_VALUE) {// gate 转发来之客户端的Rpc调用 非登录包 请求
            setLastSeq(packet.getSeq());
            setLastClientCmd(packet.getCmd());
        }
        packetQueue.add(packet);
    }

    public int getLastSeq() {
        return lastSeq;
    }

    public void setLastSeq(int lastSeq) {
        this.lastSeq = lastSeq;
    }

    public void setPlayer(Player player) {
    }

    public void tickPacket() {
        S2SMessagePacket packet = packetQueue.poll();
        if (packet == null) {
            return;
        }
        try {
            if (player == null) {
                return;
            }
            long now = System.currentTimeMillis();
            GameHandlerRouteManager.getInstance().processPacket(player, packet);
            long cost = System.currentTimeMillis() - now;
            if (cost > 100) {
                LoggerDef.SystemLogger.warn("GamePlayer tickPacket cmd {} too long cost {} ms", packet.getCmd(), cost);
            }
        } catch (Exception e) {
            e.printStackTrace();
            LoggerDef.SystemLogger.error("GamePlayer tickPacket error", e);
        }
    }

    /***
     * 发送消息给客户端，主要用于收到客户端包处理完成之后 携带着客户端上行的包的seq
     * @param cmd 命令
     * @param message 消息
     */
    public void sendMsg(Cmd.CMD cmd, AbstractMessage message) {
        S2SMessagePacket packet = new S2SMessagePacket(getPlayerId(), cmd.getNumber(), lastSeq, message.toByteArray());
        session.addSendPacket(packet);
    }


}
