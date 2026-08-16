package ly.logic.player;

import java.util.Map;

import com.google.protobuf.AbstractMessage;

import ly.LoggerDef;
import ly.db.entry.PlayerModuleEntry;
import ly.logic.player.event.PlayerEventManager;
import ly.logic.player.event.PlayerEventParam;
import ly.logic.player.event.PlayerEventSource;
import ly.logic.player.event.PlayerEventType;
import ly.net.GamePlayer;
import ly.proto.Cmd;
import ly.proto.ErrorMsg;
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

            TimeStatisticsUtils.TimeStatisticsLog log = TimeStatisticsUtils
                    .makeLogBegin(String.format("Player-%s-%d-InitModules", getAccount(), getPlayerId()), 1000);
            Map<ModuleEnum, PlayerModuleEntry> loadedEntries = playerData.loadModuleEntries();

            for (ModuleEnum moduleEnum : ModuleEnum.values()) {
                PlayerModuleEntry moduleEntry = loadedEntries.get(moduleEnum);
                boolean moduleDataMissing = moduleEntry == null || moduleEntry.getModuleData().length == 0;
                if (moduleEntry == null) {
                    moduleEntry = playerData.createModuleEntry(moduleEnum);
                }
                TimeStatisticsUtils.TimeStatisticsLog moduleInitLog = TimeStatisticsUtils.makeLogBegin(
                        String.format("Player-%s-%d-InitModules:%s", getAccount(), getPlayerId(), moduleEnum.getName()),
                        50);
                AbstractModule module = createModuleInstance(moduleEnum, moduleEntry);
                moduleDataMissing = moduleDataMissing || moduleEntry.getModuleData().length == 0;
                module.init(this, moduleEnum, moduleEntry, moduleDataMissing);
                moduleInitLog.LogEnd();
            }

            // 老 BLOB 数据和新初始化模块统一迁移到模块表，不再回写总 modules BLOB。
            playerData.flushAsync();

            log.LogEnd();

            // 设置玩家状态为已初始化
            // setStatus(PlayerStatusEnum.INITIALIZED);

            // 分发玩家初始化完成事件
            // dispatchEvent(PlayerEventType.PLAYER_INIT_COMPLETE);
        } catch (Exception e) {
            System.err.println("Error initializing modules for player " + getPlayerId() + ": " + e.getMessage());
            // setStatus(PlayerStatusEnum.INIT_FAILED);
        }

    }

    private AbstractModule createModuleInstance(ModuleEnum moduleEnum, PlayerModuleEntry moduleEntry) throws Exception {
        Class<? extends AbstractModule> moduleClass = moduleEnum.getModule().getClass();
        byte[] moduleData = moduleEntry == null ? null : moduleEntry.getModuleData();
        if (moduleData == null || moduleData.length == 0) {
            return moduleClass.getDeclaredConstructor().newInstance();
        }
        try {
            return AbstractModule.deserialize(moduleClass, moduleData);
        } catch (Exception e) {
            LoggerDef.SystemLogger.warn(
                    "init module fallback, playerId={}, module={}, reason={}",
                    getPlayerId(),
                    moduleClass.getSimpleName(),
                    e.getMessage());
            moduleEntry.setModuleData(new byte[0]);
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

    public void dispatchEvent(PlayerEventSource source, long sourcePlayerId, PlayerEventType playerEventType,
            Object... args) {
        PlayerEventParam param = PlayerEventParam.of(this, playerEventType, source, sourcePlayerId, args);
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
        LoggerDef.SystemLogger.info("[statPlay] starting tick thread, playerId={}, account={}", getPlayerId(),
                getAccount());
        Thread.ofVirtual().name(String.format("Player-%s-%d", getAccount(), getPlayerId())).start(() -> {
            tick();
        });
    }

    private void tick() {
        LoggerDef.SystemLogger.info("[Player-tick] thread started, playerId={}, gamePlayer={}", getPlayerId(),
                gamePlayer != null);
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
        gamePlayer.sendErrorCode(cmd.getNumber(), errorCode);
    }

    public void sendMsg(Cmd.CMD cmd, AbstractMessage message) {
        gamePlayer.sendMsg(cmd.getNumber(), message);
    }
}
