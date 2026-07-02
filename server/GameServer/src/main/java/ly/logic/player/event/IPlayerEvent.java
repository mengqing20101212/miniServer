package ly.logic.player.event;

import java.util.List;

/**
 * 游戏服玩家相关模型，承载玩家连接状态、持久化数据或模块数据。
 */
public interface IPlayerEvent {
    void onEvent(PlayerEventParam param);

    List<PlayerEventType> getRegisterEventTypes();

    default void onRegister(PlayerEventManager eventManager) {
        for (PlayerEventType eventType : getRegisterEventTypes()) {
            eventManager.register(eventType, this);
        }
    }
}
