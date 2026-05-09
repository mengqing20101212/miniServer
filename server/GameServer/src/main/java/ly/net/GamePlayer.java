package ly.net;

import com.google.protobuf.AbstractMessage;
import ly.LoggerDef;
import ly.logic.player.Player;
import ly.net.packet.AbstractMessagePacket;
import ly.proto.Cmd;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * 游戏服玩家相关模型，承载玩家连接状态、持久化数据或模块数据。
 */
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
    private int lastSid;

    ArrayBlockingQueue<AbstractMessagePacket> packetQueue = new ArrayBlockingQueue<>(100);

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

    public int getLastSid() {
        return lastSid;
    }

    public void setLastSid(int lastSid) {
        this.lastSid = lastSid;
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

    public void addPacket(AbstractMessagePacket packet) {
        // Gate 转发过来的业务包已经在 RPC 控制器中还原为客户端原始包，这里记录原始 seq/sid。
        setLastSeq(packet.getSeq());
        setLastClientCmd(packet.getCmd());
        setLastSid(packet.getSid());
        packetQueue.add(packet);
    }

    public int getLastSeq() {
        return lastSeq;
    }

    public void setLastSeq(int lastSeq) {
        this.lastSeq = lastSeq;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }


    public boolean isEmpty() {
        return packetQueue.isEmpty();
    }

    public void tickPacket() {
        AbstractMessagePacket packet = packetQueue.poll();
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
        AbstractMessagePacket packet = new AbstractMessagePacket(getPlayerId(), cmd.getNumber(), lastSeq, message.toByteArray());
        session.addSendPacket(packet);
    }


}
