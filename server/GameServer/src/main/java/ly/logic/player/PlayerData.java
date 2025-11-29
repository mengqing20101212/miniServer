package ly.logic.player;

import ly.db.entry.PlayerEntry;

public class PlayerData {
    final PlayerEntry playerEntry;

    public PlayerData(PlayerEntry playerEntry) {
        this.playerEntry = playerEntry;
    }

    public PlayerEntry getPlayerEntry() {
        return playerEntry;
    }


}
