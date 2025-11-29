package ly.logic.player.event;

public enum PlayerEventType {
    PLAYER_LOAD_DATA_COMPLETE,//玩家数据加载完成
    PLAYER_CREATE_COMPLETE,//玩家创建完成
    PLAYER_LOGIN_COMPLETE,//玩家登录完成
    PLAYER_LOGIN_IS_RECONNECT,//玩家登录是是重连 true 是重连， false 不是重连
    PLAYER_BEGIN_LOGOUT,//玩家开始退出
    PLAYER_LOGOUT_COMPLETE,//玩家退出完成
    PLAYER_DELETE_FROM_CACHE,//玩家从缓存中删除
}
