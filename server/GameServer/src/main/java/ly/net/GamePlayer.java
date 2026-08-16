package ly.net;

import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import ly.LoggerDef;
import ly.logic.player.Player;
import ly.logic.player.coroutine.PlayerCoroutineTask;
import ly.logic.player.coroutine.PlayerThreadContext;
import ly.logic.player.event.PlayerEventParam;
import ly.net.packet.MessagePacket;
import ly.net.packet.MessagePacketFactory;
import ly.proto.Cmd;
import ly.proto.ErrorMsg;
import ly.proto.Server;
import ly.utils.CommonUtils;

/**
 * 玩家网络上下文和串行执行队列。
 *
 * <p>
 * GamePlayer 只保存玩家连接状态、当前请求上下文，以及 packet/event 的 FIFO 执行队列。
 * 客户端下行 seq 由 Gate 维护，Game 只把目标 clientSid 和业务数据返回给 Gate。
 */
public class GamePlayer {
    private static final int WORK_QUEUE_CAPACITY = 1024;

    private final GameConnectSession session;
    private final ArrayBlockingQueue<GamePlayerWorkItem> workQueue = new ArrayBlockingQueue<>(WORK_QUEUE_CAPACITY);
    private final AtomicBoolean offlineDraining = new AtomicBoolean();
    private final Object drainLock = new Object();
    private int runningWorkCount;
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

    public void addPacket(MessagePacket packet) {
        if (packet == null) {
            return;
        }
        if (offlineDraining.get()) {
            LoggerDef.SystemLogger.error(
                    "GamePlayer is offline draining, reject packet playerId={}, cmd={}, seq={}, sid={}",
                    playerId,
                    packet.getCmd(),
                    packet.getSeq(),
                    packet.getSid());
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
        if (offlineDraining.get()) {
            LoggerDef.SystemLogger.error(
                    "GamePlayer is offline draining, reject event playerId={}, eventType={}, source={}, sourcePlayerId={}",
                    playerId,
                    event.getEventType(),
                    event.getSource(),
                    event.getSourcePlayerId());
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

    public boolean addCoroutineTask(PlayerCoroutineTask<?> task) {
        if (task == null) {
            return false;
        }
        if (offlineDraining.get()) {
            LoggerDef.SystemLogger.error(
                    "GamePlayer is offline draining, reject coroutine playerId={}, sourcePlayerId={}, desc={}",
                    playerId,
                    task.getSourcePlayerId(),
                    task.getDescription());
            return false;
        }
        boolean success = workQueue.offer(GamePlayerWorkItem.coroutine(task));
        if (!success) {
            LoggerDef.SystemLogger.error(
                    "GamePlayer work queue full, drop coroutine playerId={}, sourcePlayerId={}, desc={}, queueSize={}, queueCapacity={}",
                    playerId,
                    task.getSourcePlayerId(),
                    task.getDescription(),
                    workQueue.size(),
                    WORK_QUEUE_CAPACITY);
        }
        return success;
    }

    public int getWorkQueueSize() {
        return workQueue.size();
    }

    public void cancelPendingCoroutineTasks(Throwable cause) {
        Iterator<GamePlayerWorkItem> iterator = workQueue.iterator();
        while (iterator.hasNext()) {
            GamePlayerWorkItem item = iterator.next();
            if (item.getType() == GamePlayerWorkItem.Type.COROUTINE && item.getCoroutineTask() != null) {
                item.getCoroutineTask().cancel(cause);
                iterator.remove();
            }
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

    public void beginOfflineDrain() {
        offlineDraining.set(true);
        notifyDrainIfComplete();
    }

    public boolean isOfflineDraining() {
        return offlineDraining.get();
    }

    public boolean awaitOfflineDrain(long timeoutMillis) throws InterruptedException {
        if (PlayerThreadContext.currentPlayerId() == playerId) {
            throw new IllegalStateException(
                    "cannot await offline drain in current player queue, playerId=" + playerId);
        }
        if (timeoutMillis <= 0) {
            throw new IllegalArgumentException("timeoutMillis must be positive");
        }
        beginOfflineDrain();
        long deadline = System.currentTimeMillis() + timeoutMillis;
        synchronized (drainLock) {
            while (!isDrainCompleteLocked()) {
                long waitMillis = deadline - System.currentTimeMillis();
                if (waitMillis <= 0) {
                    LoggerDef.SystemLogger.error(
                            "GamePlayer offline drain timeout, playerId={}, queueSize={}, runningWorkCount={}",
                            playerId,
                            workQueue.size(),
                            runningWorkCount);
                    return false;
                }
                drainLock.wait(waitMillis);
            }
            return true;
        }
    }

    public void tickWorkItem() throws InterruptedException {
        GamePlayerWorkItem workItem = workQueue.poll(100, TimeUnit.MILLISECONDS);
        // LoggerDef.SystemLogger.info("[tickWorkItem] polling, queueSize={}",
        // workQueue.size());
        if (workItem == null) {
            notifyDrainIfComplete();
            return;
        }
        markWorkStarted();
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
            PlayerThreadContext.enter(player.getPlayerId());
            try {
                if (workItem.getType() == GamePlayerWorkItem.Type.PACKET) {
                    processPacket(workItem.getPacket());
                } else if (workItem.getType() == GamePlayerWorkItem.Type.EVENT) {
                    processEvent(workItem.getEvent());
                } else if (workItem.getType() == GamePlayerWorkItem.Type.COROUTINE) {
                    processCoroutine(workItem.getCoroutineTask());
                }
            } finally {
                PlayerThreadContext.exit();
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
        } finally {
            markWorkFinished();
        }
    }

    private void markWorkStarted() {
        synchronized (drainLock) {
            runningWorkCount++;
        }
    }

    private void markWorkFinished() {
        synchronized (drainLock) {
            runningWorkCount--;
            if (runningWorkCount < 0) {
                runningWorkCount = 0;
            }
            if (isDrainCompleteLocked()) {
                drainLock.notifyAll();
            }
        }
    }

    private void notifyDrainIfComplete() {
        synchronized (drainLock) {
            if (isDrainCompleteLocked()) {
                drainLock.notifyAll();
            }
        }
    }

    private boolean isDrainCompleteLocked() {
        return offlineDraining.get() && runningWorkCount == 0 && workQueue.isEmpty();
    }

    private void processPacket(MessagePacket packet) {
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

    private void processCoroutine(PlayerCoroutineTask<?> task) {
        if (task == null) {
            return;
        }
        task.execute(player);
    }

    private void beginRequestContext(MessagePacket packet) {
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
     * <p>
     * 有客户端请求上下文时，Game 只封装 clientCmd/clientSid/data/callId 返回 Gate。
     * 客户端下行 seq 由 Gate 在真正写回客户端连接前生成。
     */
    public void sendMsg(int cmd, com.google.protobuf.AbstractMessage message) {
        LoggerDef.LogProto("send {}|{}|{}|{}", playerId, getAccount(), Cmd.CMD.forNumber(cmd).name(),
                CommonUtils.logProto(message));
        if (lastClientCmd == 0) {
            MessagePacket packet = MessagePacketFactory.createMessagePacket(playerId, cmd, message, 0,
                    0);
            session.addSendPacket(packet);
            return;
        }

        Server.scGate2GameRpcGameCall builder = Server.scGate2GameRpcGameCall.newBuilder()
                .setClientCmd(cmd)
                .setClientSid(lastClientSid)
                .setData(message.toByteString())
                .setCallId(lastCallId)
                .build();
        MessagePacket packet = MessagePacketFactory.createMessagePacket(
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

    private Object getAccount() {
        return player == null ? null : player.getAccount();
    }

    public void sendErrorCode(int cmd, ErrorMsg.ErrorCode errorCode) {
        ErrorMsg.scErrorCode errorMsg = ErrorMsg.scErrorCode.newBuilder()
                .setErrorCode(errorCode)
                .setMsgId(cmd)
                .build();
        sendMsg(Cmd.CMD.SC_ErrorCode_VALUE, errorMsg);
    }
}
