package ly.logic.player;

import ly.net.GamePlayer;

public class Player {
    private GamePlayer gamePlayer;
    private PlayerData playerData;

    public Player(GamePlayer gamePlayer) {
        this.gamePlayer = gamePlayer;
    }

    public GamePlayer getGamePlayer() {
        return gamePlayer;
    }

    public PlayerData getPlayerData() {
        return playerData;
    }
}
