package ly.logic.player;

/**
 * 玩家状态
 */
public enum PlayerStatusEnum {
    /**
     * 初始化
     */
    INIT,
    /**
     * 创建
     */
    CREATE,
    /**
     * 登录中
     */
    LOGGING,
    /**
     * 游戏中
     */
    PLAYING,
    /**
     * 已退出
     */
    LOGOUT,
}
