package ly.logic.player;

import com.baidu.bjf.remoting.protobuf.Codec;
import com.baidu.bjf.remoting.protobuf.ProtobufProxy;
import com.google.protobuf.AbstractMessage;

import ly.LoggerDef;
import ly.logic.player.event.PlayerEventManager;
import ly.logic.player.event.PlayerEventParam;
import ly.logic.player.event.PlayerEventSource;
import ly.logic.player.event.PlayerEventType;
import ly.net.GamePlayer;
import ly.net.packet.AbstractMessagePacket;
import ly.net.packet.MessagePacketFactory;
import ly.proto.Cmd;
import ly.proto.ErrorMsg;
import ly.proto.Server;
import ly.utils.TimeStatisticsUtils;
import ly.utils.TimeUtils;

/**
 * 游戏服玩家相关模型，承载玩家连接状态、持久化数据或模块数据。
 */
public class Player {
    private GamePlayer gamePlayer;
    private PlayerData playerData;
    private PlayerStatusEnum status;
    private String loginToken;

    private final PlayerEventManager eventManager = new PlayerEventManager();

    public Player(GamePlayer gamePlayer) {
        this.gamePlayer = gamePlayer;
        this.status = PlayerStatusEnum.INIT;
    }

    public PlayerEventManager getEventManager() {
        return eventManager;
    }

    public Player() {
        this.status = PlayerStatusEnum.INIT;
    }

    public GamePlayer getGamePlayer() {
        return gamePlayer;
    }

    public PlayerData getPlayerData() {
        return playerData;
    }

    public void setPlayerData(PlayerData playerData) {
        this.playerData = playerData;
    }

    public void initAllModules() {
        // 确保玩家数据已加载
        if (playerData == null) {
            System.err.println("Player data is null when initializing modules for player: " + getPlayerId());
            return;
        }

        try {
            // 初始化玩家各个功能模块
            // 这里可以初始化装备、技能、任务等系统

            TimeStatisticsUtils.TimeStatisticsLog log = TimeStatisticsUtils.makeLogBegin(String.format("Player-%s-%d-InitModules", getAccount(), getPlayerId()), 1000);

            for (ModuleEnum moduleEnum : ModuleEnum.values()) {
                TimeStatisticsUtils.TimeStatisticsLog moduleInitLog = TimeStatisticsUtils.makeLogBegin(String.format("Player-%s-%d-InitModules:%s", getAccount(), getPlayerId(), moduleEnum.getName()), 50);
                byte[] moduleData = playerData.getModuleData(moduleEnum);
                AbstractModule module = createModuleInstance(moduleEnum, moduleData);
                module.init(this);
                module.onLoadData();
                playerData.putModule(moduleEnum, module);
                moduleInitLog.LogEnd();
            }

            log.LogEnd();

            // 设置玩家状态为已初始化
//            setStatus(PlayerStatusEnum.INITIALIZED);

            // 分发玩家初始化完成事件
//            dispatchEvent(PlayerEventType.PLAYER_INIT_COMPLETE);
        } catch (Exception e) {
            System.err.println("Error initializing modules for player " + getPlayerId() + ": " + e.getMessage());
//            setStatus(PlayerStatusEnum.INIT_FAILED);
        }

    }

    private AbstractModule createModuleInstance(ModuleEnum moduleEnum, byte[] moduleData) throws Exception {
        Class<? extends AbstractModule> moduleClass = moduleEnum.getModule().getClass();
        if (moduleData == null || moduleData.length == 0) {
            return moduleClass.getDeclaredConstructor().newInstance();
        }
        try {
            Codec<?> codec = ProtobufProxy.create(moduleClass);
            return (AbstractModule) codec.decode(moduleData);
        } catch (Exception e) {
            LoggerDef.SystemLogger.warn(
                    "init module fallback, playerId={}, module={}, reason={}",
                    getPlayerId(),
                    moduleClass.getSimpleName(),
                    e.getMessage());
            return moduleClass.getDeclaredConstructor().newInstance();
        }
    }

    public void setStatus(PlayerStatusEnum playerStatusEnum) {
        this.status = playerStatusEnum;
    }

    public long getPlayerId() {
        return playerData.playerEntry.getId();
    }

    public String getPlayerName() {
        return playerData.playerEntry.getName();
    }

    public String getAccount() {
        return playerData.playerEntry.getAccount();
    }

    public String getToken() {
        return loginToken;
    }

    public void setToken(String loginToken) {
        this.loginToken = loginToken;
    }

    public void dispatchEvent(PlayerEventType playerEventType, Object... args) {
        dispatchEvent(PlayerEventSource.SELF, getPlayerId(), playerEventType, args);
    }

    public void dispatchEvent(PlayerEventSource source, long sourcePlayerId, PlayerEventType playerEventType, Object... args) {
        PlayerEventParam param =
                PlayerEventParam.of(this, playerEventType, source, sourcePlayerId, args);
        if (gamePlayer == null) {
            eventManager.dispatchEvent(param);
            return;
        }
        gamePlayer.addEvent(param);
    }

    public long getCreateTime() {
        return TimeUtils.getTimer(playerData.playerEntry.getCreatetime());
    }

    public int getLevel() {
        Integer level = playerData.playerEntry.getLevel();
        return level == null ? 1 : level;
    }

    public int getVipLevel() {
        Integer vipLevel = playerData.playerEntry.getViplevel();
        return vipLevel == null ? 0 : vipLevel;
    }

    public long getLoginTime() {
        return TimeUtils.getTimer(playerData.playerEntry.getLogintime());
    }

    public long getLastLogoutTime() {
        return TimeUtils.getTimer(playerData.playerEntry.getLogouttime());
    }

    public void setGamePlayer(GamePlayer gamePlayer) {
        this.gamePlayer = gamePlayer;
        // GamePlayer 处理队列中的业务包时需要反向拿到 Player 上下文。
        if (gamePlayer != null) {
            gamePlayer.bindPlayer(this);
            eventManager.drainPendingEvents(gamePlayer::addEvent);
        }
    }

    public void statPlay() {
        Thread.ofVirtual().name(String.format("Player-%s-%d", getAccount(), getPlayerId())).start(() -> {
            tick();
        });
    }

    private void tick() {
        try {
            while (true) {
                try {
                    if (gamePlayer == null) {
                        Thread.sleep(100L);
                        continue;
                    }
                    gamePlayer.tickWorkItem();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendErrorCode(Cmd.CMD cmd, ErrorMsg.ErrorCode errorCode) {
        ErrorMsg.scErrorCode.Builder res = ErrorMsg.scErrorCode.newBuilder();
        res.setMsgId(cmd.getNumber());
        res.setErrorCode(errorCode);
        sendMsg(Cmd.CMD.SC_ErrorCode, res.build());
    }

    public void sendMsg(Cmd.CMD cmd, AbstractMessage message) {
        long callId = getGamePlayer().getLastCallId();
        if (getGamePlayer().getLastClientCmd() == 0) {
            AbstractMessagePacket sendPacket = MessagePacketFactory.createAbstractMessagePacket(getPlayerId(), cmd.getNumber(), message, 0, 0);
            getGamePlayer().getSession().addSendPacket(sendPacket);
        } else {
            if (cmd.getNumber() == getGamePlayer().getLastClientCmd() + 1) {
                Server.scGate2GameRpcGameCall.Builder builder = Server.scGate2GameRpcGameCall.newBuilder();
                // 当前请求对应的响应由 GameServer 统一确定客户端下行 cmd/seq/sid。
                builder.setCmd(cmd.getNumber());
                builder.setSid(gamePlayer.getLastSid());
                builder.setSeq(getGamePlayer().getLastSeq() + 1);
                builder.setData(message.toByteString());
                builder.setCallId(callId);
                AbstractMessagePacket sendPacket = MessagePacketFactory.createAbstractMessagePacket(getPlayerId(), Cmd.CMD.SC_Gate2GameRpcGameCall_VALUE, builder.build(), getGamePlayer().getLastSeq() + 1, gamePlayer.getLastSid());
                getGamePlayer().getSession().addSendPacket(sendPacket);
                LoggerDef.NetLogger.info(
                        "[Gate2GameRpc] sending SC_Gate2GameRpcGameCall response, playerId={}, respCmd={}, respSeq={}, respSid={}, lastClientCmd={}, lastSeq={}, callId={}",
                        getPlayerId(), cmd.getNumber(), getGamePlayer().getLastSeq() + 1, gamePlayer.getLastSid(), getGamePlayer().getLastClientCmd(), getGamePlayer().getLastSeq(), callId);
                getGamePlayer().setLastClientCmd(0);
                getGamePlayer().setLastSeq(0);
                getGamePlayer().setLastSid(0);
                getGamePlayer().setLastCallId(0);
            } else {
                Server.scGate2GameRpcGameCall.Builder builder = Server.scGate2GameRpcGameCall.newBuilder();
                // 主动推送不消耗客户端请求序号，保持最近一次客户端 seq，便于 Gate 按 sid 定位连接。
                builder.setCmd(cmd.getNumber());
                builder.setSid(gamePlayer.getLastSid());
                builder.setSeq(gamePlayer.getLastSeq());
                builder.setData(message.toByteString());
                builder.setCallId(callId);
                AbstractMessagePacket sendPacket = MessagePacketFactory.createAbstractMessagePacket(getPlayerId(), Cmd.CMD.SC_Gate2GameRpcGameCall_VALUE, builder.build(), gamePlayer.getLastSeq(), gamePlayer.getLastSid());
                getGamePlayer().getSession().addSendPacket(sendPacket);
                LoggerDef.NetLogger.info(
                        "[Gate2GameRpc] sending SC_Gate2GameRpcGameCall push, playerId={}, respCmd={}, respSeq={}, respSid={}, lastClientCmd={}, callId={}",
                        getPlayerId(), cmd.getNumber(), gamePlayer.getLastSeq(), gamePlayer.getLastSid(), getGamePlayer().getLastClientCmd(), callId);
            }
        }
    }
}
