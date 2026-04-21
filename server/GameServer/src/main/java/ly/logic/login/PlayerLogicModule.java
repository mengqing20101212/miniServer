package ly.logic.login;

import ly.logic.player.AbstractModule;
import ly.logic.player.event.PlayerEventParam;
import ly.logic.player.event.PlayerEventType;

import java.util.List;

public class PlayerLogicModule extends AbstractModule {
    long lastLoginTimer;

    @Override
    public void onLoadData() {
    }

    @Override
    public boolean saveData() {
        return false;
    }

    @Override
    public void onOpenFunction() {
    }

    @Override
    public void onEvent(PlayerEventParam param) {

    }

    @Override
    public List<PlayerEventType> getRegisterEventTypes() {
        return List.of(PlayerEventType.CROSS_DAY_ZERO);
    }
}
