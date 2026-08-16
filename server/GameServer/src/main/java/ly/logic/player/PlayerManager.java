package ly.logic.player;

import ly.ServerContext;
import ly.LoggerDef;
import ly.db.entry.LoginEntry;
import ly.db.entry.LoginEntryHelper;
import ly.db.entry.PlayerEntry;
import ly.db.entry.PlayerEntryHelper;
import ly.game.MiniPlayer;
import ly.logic.player.event.PlayerEventSource;
import ly.logic.player.event.PlayerEventType;
import ly.proto.Login;
import ly.redis.RedisKeys;
import ly.redis.RedisUtils;
import ly.utils.TimeUtils;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 游戏服管理器，维护对应业务对象的生命周期和查询入口。
 */
public class PlayerManager {
    long createPlayerId = 1;
    private static final PlayerManager instance = new PlayerManager();
    private final Map<Long, Player> playerMap = new ConcurrentHashMap<>();

    public static PlayerManager getInstance() {
        return instance;
    }

    public long createPlayerId() {
        return createPlayerId++;
    }

    public Player getOnlinePlayer(long playerId) {
        return playerMap.get(playerId);
    }

    public Player getPlayerByDB(long playerId) {
        PlayerEntry entry = PlayerEntryHelper.getPlayerEntryById(playerId);
        if (entry == null) {
            return null;
        }
        LoggerDef.SystemLogger.info(String.format("getPlayerByDB playerId=%d", playerId));
        Player dbPlayer = createNewPlayer(entry);
        dbPlayer.dispatchEvent(PlayerEventType.PLAYER_LOAD_DATA_COMPLETE);
        return dbPlayer;
    }

    private Player createNewPlayer(PlayerEntry entry) {
        Player player = new Player();
        player.setPlayerData(new PlayerData(entry));
        player.initAllModules();
        LoggerDef.SystemLogger.info(String.format("Player create successfully! playerId:%d, playerName:%s, account:%s", player.getPlayerId(), player.getPlayerName(), player.getAccount()));
        return player;
    }

    public Player createNewPlayer(Login.csLogin csLogin) {
        PlayerEntry entry = new PlayerEntry();
        entry.setAccount(csLogin.getAccount());
        entry.setCreatetime(TimeUtils.now());
        entry.setLogintime(TimeUtils.now());
        entry.setLogouttime(TimeUtils.now());
        entry.setLevel(PlayerConstant.INIT_PLAYER_LEVEL);
        entry.setViplevel(0);
        entry.setGuidid(0L);
        entry.setName(csLogin.getPlayerName());
        if (!PlayerEntryHelper.save(entry)) {
            throw new IllegalStateException("create player save db failed, account=" + csLogin.getAccount());
        }
        Player newPlayer = createNewPlayer(entry);
        registerAccountPlayer(newPlayer);
        newPlayer.setStatus(PlayerStatusEnum.CREATE);
        LoggerDef.SystemLogger.info(String.format("PlayerManager create new player pid:%d, playerName:%s, account:%s ", newPlayer.getPlayerId(), newPlayer.getPlayerName(), newPlayer.getAccount()));
        newPlayer.dispatchEvent(PlayerEventType.PLAYER_CREATE_COMPLETE);
        return newPlayer;
    }

    private void registerAccountPlayer(Player player) {
        if (player == null || player.getAccount() == null || player.getAccount().isBlank()) {
            return;
        }
        try {
            List<LoginEntry> entries = LoginEntryHelper.select(new String[] { "account" }, player.getAccount());
            if (!entries.isEmpty()) {
                LoginEntry loginEntry = entries.get(0);
                loginEntry.setPlayers(appendPlayerId(loginEntry.getPlayers(), player.getPlayerId()));
                LoginEntryHelper.update(loginEntry, "players");
            } else {
                LoggerDef.SystemLogger.warn(
                        "register account player skipped, login entry not found, account={}, playerId={}",
                        player.getAccount(),
                        player.getPlayerId());
            }
            RedisUtils.set(RedisKeys.MINI_PLAYER_KEY.getKey(player.getPlayerId()), toMiniPlayer(player));
        } catch (Exception e) {
            LoggerDef.SystemLogger.error(
                    "register account player failed, account={}, playerId={}",
                    player.getAccount(),
                    player.getPlayerId(),
                    e);
        }
    }

    private String appendPlayerId(String players, long playerId) {
        Set<String> ids = new LinkedHashSet<>();
        if (players != null && !players.isBlank()) {
            for (String id : players.split(";")) {
                if (id != null && !id.isBlank()) {
                    ids.add(id.trim());
                }
            }
        }
        ids.add(String.valueOf(playerId));
        return String.join(";", ids);
    }

    private MiniPlayer toMiniPlayer(Player player) {
        MiniPlayer miniPlayer = new MiniPlayer();
        miniPlayer.setGuid(player.getPlayerId());
        miniPlayer.setPlayerName(player.getPlayerName());
        miniPlayer.setGameServerId(ServerContext.getServerId());
        miniPlayer.setLevel(player.getLevel());
        miniPlayer.setLastLoginTime(player.getLoginTime());
        miniPlayer.setLastLogoutTime(player.getLastLogoutTime());
        return miniPlayer;
    }

    public void addOnlinePlayer(Player player) {
        playerMap.put(player.getPlayerId(), player);
    }

    public void flushAllPlayerModules() {
        for (Player player : playerMap.values()) {
            if (player != null && player.getPlayerData() != null) {
                player.getPlayerData().flushAsync();
            }
        }
    }

    public void dispatchEventToPlayer(long playerId, PlayerEventSource source, long sourcePlayerId, PlayerEventType eventType, Object... args) {
        Player player = getOnlinePlayer(playerId);
        if (player == null) {
            LoggerDef.SystemLogger.warn("dispatchEventToPlayer failed, player not online, playerId={}, eventType={}", playerId, eventType);
            return;
        }
        player.dispatchEvent(source, sourcePlayerId, eventType, args);
    }

    public void dispatchGlobalEvent(PlayerEventType eventType, Object... args) {
        for (Player player : playerMap.values()) {
            player.dispatchEvent(PlayerEventSource.SYSTEM_GLOBAL, 0L, eventType, args);
        }
    }

}
