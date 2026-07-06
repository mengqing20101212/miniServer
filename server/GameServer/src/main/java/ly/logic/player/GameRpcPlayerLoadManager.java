package ly.logic.player;

import java.util.concurrent.ArrayBlockingQueue;

import ly.LoggerDef;
import ly.net.GamePlayer;
import ly.net.packet.AbstractMessagePacket;
import ly.net.packet.MessagePacketFactory;
import ly.proto.Cmd;
import ly.proto.ErrorMsg;
import ly.proto.Server;

/**
 * Gate -> Game 非登录业务包加载管理器。
 *
 * <p>非登录包到达时，如果玩家不在线，旧逻辑会在 RPC 入站线程里同步查 DB 并初始化
 * GamePlayer。DB IO 抖动会拖慢整条 Gate/Game RPC 连接。这里改为独立队列消费：
 * 入站线程只负责轻量判断和投递，真正的 lazy load、绑定玩家、投递玩家队列都在
 * 该管理器的虚拟线程里执行。</p>
 */
public class GameRpcPlayerLoadManager {
    private static final int TASK_QUEUE_CAPACITY = 1024;
    private static final GameRpcPlayerLoadManager INSTANCE = new GameRpcPlayerLoadManager();

    private final ArrayBlockingQueue<GameRpcPlayerTask> tasks = new ArrayBlockingQueue<>(TASK_QUEUE_CAPACITY);

    public static GameRpcPlayerLoadManager getInstance() {
        return INSTANCE;
    }

    private GameRpcPlayerLoadManager() {
        Thread.ofVirtual()
                .name("GameRpcPlayerLoadManager")
                .start(this::runLoop);
    }

    public void addTask(GameRpcPlayerTask task) {
        if (task == null || task.packet == null) {
            return;
        }
        if (!tasks.offer(task)) {
            AbstractMessagePacket packet = task.packet;
            LoggerDef.SystemLogger.error(
                    "GameRpcPlayerLoadManager queue full, drop packet playerId={}, cmd={}, seq={}, sid={}, queueSize={}, queueCapacity={}",
                    packet.getGuid(),
                    packet.getCmd(),
                    packet.getSeq(),
                    packet.getSid(),
                    tasks.size(),
                    TASK_QUEUE_CAPACITY);
            sendErrorCode(task, ErrorMsg.ErrorCode.SYSTEM_ERROR);
        }
    }

    private void runLoop() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                handleTask(tasks.take());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                LoggerDef.SystemLogger.error("GameRpcPlayerLoadManager interrupted", e);
            } catch (Exception e) {
                LoggerDef.SystemLogger.error("GameRpcPlayerLoadManager handle task error", e);
            }
        }
    }

    private void handleTask(GameRpcPlayerTask task) {
        AbstractMessagePacket packet = task.packet;
        long playerId = packet.getGuid();
        Player player = PlayerManager.getInstance().getOnlinePlayer(playerId);
        if (player == null) {
            player = PlayerManager.getInstance().getPlayerByDB(playerId);
            if (player == null) {
                sendErrorCode(task, ErrorMsg.ErrorCode.PLAYER_NOT_EXIST);
                LoggerDef.NetLogger.warn(
                        "[Gate2GameRpc] player not found in DB, guid={}, clientCmd={}, callId={}",
                        playerId,
                        packet.getCmd(),
                        task.callId);
                return;
            }
            bindOnlinePlayer(task, player);
        }

        player.getGamePlayer().setLastCallId(task.callId);
        player.getGamePlayer().addPacket(packet);
    }

    private void bindOnlinePlayer(GameRpcPlayerTask task, Player player) {
        LoggerDef.NetLogger.info(
                "[Gate2GameRpc] player loaded from DB for lazy init, guid={}",
                player.getPlayerId());
        GamePlayer gamePlayer = new GamePlayer(task.session);
        gamePlayer.setPlayerId(player.getPlayerId());
        gamePlayer.bindPlayer(player);
        player.setGamePlayer(gamePlayer);
        PlayerManager.getInstance().addOnlinePlayer(player);
        player.statPlay();
    }

    private void sendErrorCode(GameRpcPlayerTask task, ErrorMsg.ErrorCode errorCode) {
        AbstractMessagePacket packet = task.packet;
        ErrorMsg.scErrorCode errorMsg = ErrorMsg.scErrorCode.newBuilder()
                .setErrorCode(errorCode)
                .setMsgId(packet.getCmd())
                .build();
        Server.scGate2GameRpcGameCall response = Server.scGate2GameRpcGameCall.newBuilder()
                .setClientCmd(Cmd.CMD.SC_ErrorCode_VALUE)
                .setClientSid(packet.getSid())
                .setData(errorMsg.toByteString())
                .setCallId(task.callId)
                .build();
        AbstractMessagePacket responsePacket = MessagePacketFactory.createAbstractMessagePacket(
                packet.getGuid(),
                Cmd.CMD.SC_Gate2GameRpcGameCall_VALUE,
                response,
                0,
                0);
        task.session.addSendPacket(responsePacket);
    }
}
