package ly.net;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;
import ly.LoggerDef;
import ly.logic.player.Player;
import ly.logic.player.event.PlayerEventParam;
import ly.net.packet.AbstractMessagePacket;
import ly.net.packet.MessagePacketFactory;
import ly.proto.Cmd;
import ly.proto.ErrorMsg;
import ly.proto.Server;

/**
 * 玩家网络上下文和串行执行队列。
 *
 * <p>GamePlayer 只保存玩家连接状态、当前请求上下文，以及 packet/event 的 FIFO 执行队列。
 * 客户端下行 seq 由 Gate 维护，Game 只把目标 clientSid 和业务数据返回给 Gate。
 */
public class GamePlayer {
    private static final int WORK_QUEUE_CAPACITY = 1024;

    private final GameConnectSession session;
    private final ArrayBlockingQueue<GamePlayerWorkItem> workQueue = new ArrayBlockingQueue<>(WORK_QUEUE_CAPACITY);
    private long playerId;
    private Player player;

    /** 当前正在处理的客户端请求上下文，只在玩家协程执行某个 packet 期间有效。 */
    private int lastClientReqSeq;
    private int lastClientCmd;
    private int lastClientSid;
    /** 当前请求携带的 RPC callId，Game 回包必须原样带回，Gate 用它匹配等待中的 RPC。 */
    private long lastCallId;

    public GamePlayer(GameConnectSession session) {
        this.session = session;
    }

    public GameConnectSession getSession() {
        return session;
    }

    public long getSessionGuid() {
        return getPlayerId();
    }

    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }

    public int getLastSid() {
        return lastClientSid;
    }

    public void setLastSid(int lastSid) {
        this.lastClientSid = lastSid;
    }

    public void closeConnection() {
        if (session != null) {
            session.closeChannel();
        }
    }

    public int getLastClientCmd() {
        return lastClientCmd;
    }

    public void setLastClientCmd(int lastClientCmd) {
        this.lastClientCmd = lastClientCmd;
    }

    public void addPacket(AbstractMessagePacket packet) {
        if (packet == null) {
            return;
        }
        if (!workQueue.offer(GamePlayerWorkItem.packet(packet))) {
            LoggerDef.SystemLogger.error(
                    "GamePlayer work queue full, drop packet playerId={}, cmd={}, seq={}, sid={}, queueSize={}, queueCapacity={}",
                    playerId,
                    packet.getCmd(),
                    packet.getSeq(),
                    packet.getSid(),
                    workQueue.size(),
                    WORK_QUEUE_CAPACITY);
        }
    }

    public void addEvent(PlayerEventParam event) {
        if (event == null) {
            return;
        }
        if (!workQueue.offer(GamePlayerWorkItem.event(event))) {
            LoggerDef.SystemLogger.error(
                    "GamePlayer work queue full, drop event playerId={}, eventType={}, source={}, sourcePlayerId={}, queueSize={}, queueCapacity={}",
                    playerId,
                    event.getEventType(),
                    event.getSource(),
                    event.getSourcePlayerId(),
                    workQueue.size(),
                    WORK_QUEUE_CAPACITY);
        }
    }

    public int getLastSeq() {
        return lastClientReqSeq;
    }

    public void setLastSeq(int lastSeq) {
        this.lastClientReqSeq = lastSeq;
    }

    public long getLastCallId() {
        return lastCallId;
    }

    public void setLastCallId(long lastCallId) {
        this.lastCallId = lastCallId;
    }

    /** 绑定所属 Player，队列执行 packet/event 时需要回到玩家业务上下文。 */
    public void bindPlayer(Player player) {
        this.player = player;
    }

    public boolean isEmpty() {
        return workQueue.isEmpty();
    }

    public void tickWorkItem() throws InterruptedException {
        GamePlayerWorkItem workItem = workQueue.poll(100, TimeUnit.MILLISECONDS);
        LoggerDef.SystemLogger.info("[tickWorkItem] polling, queueSize={}", workQueue.size());
        if (workItem == null) {
            return;
        }
        try {
            if (player == null) {
                LoggerDef.SystemLogger.error(
                        "GamePlayer has no Player bound, drop work item playerId={}, type={}, cmd={}, eventType={}",
                        playerId,
                        workItem.getType(),
                        workItem.getPacket() == null ? null : workItem.getPacket().getCmd(),
                        workItem.getEvent() == null ? null : workItem.getEvent().getEventType());
                return;
            }
            long now = System.currentTimeMillis();
            if (workItem.getType() == GamePlayerWorkItem.Type.PACKET) {
                processPacket(workItem.getPacket());
            } else if (workItem.getType() == GamePlayerWorkItem.Type.EVENT) {
                processEvent(workItem.getEvent());
            }
            long cost = System.currentTimeMillis() - now;
            if (cost > 100) {
                LoggerDef.SystemLogger.warn(
                        "GamePlayer tickWorkItem type {} too long cost {} ms",
                        workItem.getType(),
                        cost);
            }
        } catch (Exception e) {
            LoggerDef.SystemLogger.error(
                    "GamePlayer tickWorkItem error, playerId={}, type={}, cmd={}, eventType={}",
                    playerId,
                    workItem.getType(),
                    workItem.getPacket() == null ? null : workItem.getPacket().getCmd(),
                    workItem.getEvent() == null ? null : workItem.getEvent().getEventType(),
                    e);
        }
    }

    private void processPacket(AbstractMessagePacket packet) {
        if (packet == null) {
            return;
        }
        beginRequestContext(packet);
        try {
            GameHandlerRouteManager.getInstance().processPacket(player, packet);
        } finally {
            clearRequestContext();
        }
    }

    private void processEvent(PlayerEventParam event) {
        if (event == null) {
            return;
        }
        player.getEventManager().handleEvent(event);
    }

    private void beginRequestContext(AbstractMessagePacket packet) {
        setLastSeq(packet.getSeq());
        setLastClientCmd(packet.getCmd());
        setLastSid(packet.getSid());
    }

    private void clearRequestContext() {
        setLastSeq(0);
        setLastClientCmd(0);
        setLastSid(0);
        setLastCallId(0);
    }

    /**
     * 统一消息发送。
     *
     * <p>有客户端请求上下文时，Game 只封装 clientCmd/clientSid/data/callId 返回 Gate。
     * 客户端下行 seq 由 Gate 在真正写回客户端连接前生成。
     */
    public void sendMsg(int cmd, com.google.protobuf.AbstractMessage message) {
        if (lastClientCmd == 0) {
            AbstractMessagePacket packet =
                    MessagePacketFactory.createAbstractMessagePacket(playerId, cmd, message, 0, 0);
            session.addSendPacket(packet);
            return;
        }

        Server.scGate2GameRpcGameCall builder =
                Server.scGate2GameRpcGameCall.newBuilder()
                        .setClientCmd(cmd)
                        .setClientSid(lastClientSid)
                        .setData(message.toByteString())
                        .setCallId(lastCallId)
                        .build();
        AbstractMessagePacket packet =
                MessagePacketFactory.createAbstractMessagePacket(
                        playerId,
                        Cmd.CMD.SC_Gate2GameRpcGameCall_VALUE,
                        builder,
                        0,
                        0);
        session.addSendPacket(packet);
        LoggerDef.NetLogger.info(
                "[Gate2GameRpc] send to gate, playerId={}, cmd={}, clientReqSeq={}, clientSid={}, callId={}",
                playerId,
                cmd,
                lastClientReqSeq,
                lastClientSid,
                lastCallId);
    }

    public void sendErrorCode(int cmd, ErrorMsg.ErrorCode errorCode) {
        ErrorMsg.scErrorCode errorMsg =
                ErrorMsg.scErrorCode.newBuilder()
                        .setErrorCode(errorCode)
                        .setMsgId(cmd)
                        .build();
        sendMsg(Cmd.CMD.SC_ErrorCode_VALUE, errorMsg);
    }
}
