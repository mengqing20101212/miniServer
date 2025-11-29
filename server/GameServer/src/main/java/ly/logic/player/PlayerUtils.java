package ly.logic.player;

import ly.proto.Login;

public class PlayerUtils {
    public static Login.PlayerInfo genPlayerInfo(Player player) {
        Login.PlayerInfo.Builder builder = Login.PlayerInfo.newBuilder();
        builder.setPlayerId(player.getPlayerId());
        builder.setCreateTime(player.getCreateTime());
        builder.setLevel(player.getLevel());
        builder.setVipLevel(player.getVipLevel());
        builder.setLoginTime(player.getLoginTime());
        builder.setLastLogoutTime(player.getLastLogoutTime());
        return builder.build();
    }
}
