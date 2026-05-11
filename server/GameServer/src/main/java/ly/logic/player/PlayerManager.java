package ly.logic.player;

import com.baidu.bjf.remoting.protobuf.Codec;
import com.baidu.bjf.remoting.protobuf.ProtobufProxy;
import ly.LoggerDef;
import ly.db.entry.PlayerEntry;
import ly.db.entry.PlayerEntryHelper;
import ly.logic.player.event.PlayerEventSource;
import ly.logic.player.event.PlayerEventType;
import ly.proto.Login;
import ly.utils.TimeUtils;

import java.util.Map;
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
        if (csLogin.getPlayerId() == 0) {
            entry.setId(createPlayerId());
        }
        entry.setAccount(csLogin.getAccount());
        entry.setCreatetime(TimeUtils.now());
        entry.setLogintime(TimeUtils.now());
        entry.setLogouttime(TimeUtils.now());
        if (entry.getId() == null || entry.getId() <= 0) {
            entry.setId(createPlayerId());
        }
        entry.setLevel(PlayerConstant.INIT_PLAYER_LEVEL);
        entry.setViplevel(0);
        entry.setGuidid(0L);
        entry.setName(csLogin.getPlayerName());
        entry.setModules(createDefaultModules());
        PlayerEntryHelper.save(entry);
        Player newPlayer = createNewPlayer(entry);
        newPlayer.setStatus(PlayerStatusEnum.CREATE);
        LoggerDef.SystemLogger.info(String.format("PlayerManager create new player pid:%d, playerName:%s, account:%s ", newPlayer.getPlayerId(), newPlayer.getPlayerName(), newPlayer.getAccount()));
        newPlayer.dispatchEvent(PlayerEventType.PLAYER_CREATE_COMPLETE);
        return newPlayer;
    }

    public void addOnlinePlayer(Player player) {
        playerMap.put(player.getPlayerId(), player);
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

    private byte[] createDefaultModules() {
        try {
            Codec<PlayerModuleData> codec = ProtobufProxy.create(PlayerModuleData.class);
            return codec.encode(new PlayerModuleData());
        } catch (Exception e) {
            LoggerDef.SystemLogger.warn("create default player modules failed", e);
            return null;
        }
    }
}
