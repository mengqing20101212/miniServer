package ly.logic.player;

import ly.logic.player.event.IPlayerEvent;

public abstract class AbstractModule implements IModule, IPlayerEvent {
    protected Player player;

    public void init(Player player) {
        this.player = player;
        getRegisterEventTypes().forEach(eventType -> player.getEventManager().register(eventType, this));
    }


}
