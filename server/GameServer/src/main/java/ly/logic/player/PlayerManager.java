package ly.logic.player;

import ly.LoggerDef;
import ly.db.entry.PlayerEntry;
import ly.db.entry.PlayerEntryHelper;
import ly.logic.player.event.PlayerEventType;
import ly.proto.Login;
import ly.utils.TimeUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
        entry.setId(createPlayerId());
        entry.setLevel(PlayerConstant.INIT_PLAYER_LEVEL);
        entry.setGuidid(0L);
        entry.setName(csLogin.getPlayerName());
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
}
