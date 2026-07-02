package ly.logic.player;

import ly.proto.Login;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 游戏服玩家相关模型，承载玩家连接状态、持久化数据或模块数据。
 */
public class PlayerUtils {
    private static final Logger logger = LoggerFactory.getLogger(PlayerUtils.class);
    
    /**
     * 生成玩家信息对象
     * @param player 玩家对象
     * @return 玩家信息协议对象，如果参数无效则返回null
     */
    public static Login.PlayerInfo genPlayerInfo(Player player) {
        // 参数校验
        if (player == null) {
            logger.error("Player object is null when generating PlayerInfo");
            return null;
        }
        
        try {
            Login.PlayerInfo.Builder builder = Login.PlayerInfo.newBuilder();
            
            // 设置基本信息，确保每个字段都有默认值防止空指针
            builder.setPlayerId(player.getPlayerId() > 0 ? player.getPlayerId() : 0);
            builder.setCreateTime(player.getCreateTime() > 0 ? player.getCreateTime() : 0);
            builder.setLevel(player.getLevel() >= 0 ? player.getLevel() : 1); // 默认为1级
            builder.setVipLevel(player.getVipLevel() >= 0 ? player.getVipLevel() : 0); // 默认为0级VIP
            builder.setLoginTime(player.getLoginTime() > 0 ? player.getLoginTime() : 0);
            builder.setLastLogoutTime(player.getLastLogoutTime() > 0 ? player.getLastLogoutTime() : 0);
            
            // 如果玩家名称不为空，设置玩家名称
            if (player.getPlayerName() != null && !player.getPlayerName().trim().isEmpty()) {
                builder.setPlayerName(player.getPlayerName());
            } else {
                // 设置默认名称或ID字符串
                builder.setPlayerName("Player_" + player.getPlayerId());
            }
            
            return builder.build();
        } catch (Exception e) {
            logger.error("Error generating PlayerInfo for playerId: {}", player.getPlayerId(), e);
            // 发生异常时返回基本的玩家信息，确保调用方不会因为异常而崩溃
            return Login.PlayerInfo.newBuilder()
                    .setPlayerId(player.getPlayerId() > 0 ? player.getPlayerId() : 0)
                    .setLevel(1)
                    .setVipLevel(0)
                    .build();
        }
    }
}
