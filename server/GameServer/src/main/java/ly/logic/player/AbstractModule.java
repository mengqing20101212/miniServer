package ly.logic.player;

import ly.logic.player.event.IPlayerEvent;

/**
 * 玩家逻辑模块抽象，定义模块在玩家对象中的挂载和生命周期职责。
 */
public abstract class AbstractModule implements IModule, IPlayerEvent {
    protected Player player;

    public void init(Player player) {
        this.player = player;
        getRegisterEventTypes().forEach(eventType -> player.getEventManager().register(eventType, this));
    }


}
