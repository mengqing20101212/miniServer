package ly.logic.player.event;

import java.util.List;

public interface IPlayerEvent {
    void onEvent(PlayerEventParam param);

    List<PlayerEventType> getRegisterEventTypes();

    default void onRegister(PlayerEventManager eventManager) {
        for (PlayerEventType eventType : getRegisterEventTypes()) {
            eventManager.register(eventType, this);
        }
    }
}
