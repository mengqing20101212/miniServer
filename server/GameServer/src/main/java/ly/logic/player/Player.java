package ly.logic.player;

import com.baidu.bjf.remoting.protobuf.Codec;
import com.baidu.bjf.remoting.protobuf.ProtobufProxy;
import com.google.protobuf.AbstractMessage;
import ly.logic.player.event.PlayerEventManager;
import ly.logic.player.event.PlayerEventType;
import ly.net.GamePlayer;
import ly.proto.Cmd;
import ly.utils.TimeStatisticsUtils;
import ly.utils.TimeUtils;

public class Player {
    private GamePlayer gamePlayer;
    private PlayerData playerData;
    private PlayerStatusEnum status;

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
                Codec<?> codec = ProtobufProxy.create(moduleEnum.getModule().getClass());
                AbstractModule module = (AbstractModule) codec.decode(moduleData);
                module.init(this);
                module.onLoadData();
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
        return gamePlayer.getToken();
    }

    // TODO  玩家事件分发 需要一个异步版本
    public void dispatchEvent(PlayerEventType playerEventType, Object... args) {
        eventManager.dispatchEvent(this, playerEventType, args);
    }

    public long getCreateTime() {
        return TimeUtils.getTimer(playerData.playerEntry.getCreatetime());
    }

    public int getLevel() {
        return playerData.playerEntry.getLevel();
    }

    public int getVipLevel() {
        return playerData.playerEntry.getViplevel();
    }

    public long getLoginTime() {
        return TimeUtils.getTimer(playerData.playerEntry.getLogintime());
    }

    public long getLastLogoutTime() {
        return TimeUtils.getTimer(playerData.playerEntry.getLogouttime());
    }

    public void setGamePlayer(GamePlayer gamePlayer) {
        this.gamePlayer = gamePlayer;
    }

    public void statPlay() {
        Thread.ofVirtual().name(String.format("Player-%s-%d", getAccount(), getPlayerId())).start(() -> {
            tick();
        });
    }

    private void tick() {
        gamePlayer.tickPacket();
    }

    public void sendMsg(Cmd.CMD cmd, AbstractMessage message) {
        if (cmd.getNumber() == getGamePlayer().getLastClientCmd() + 1) {
            getGamePlayer().setLastClientCmd(0);
            getGamePlayer().setLastSeq(0);
        }

    }
}