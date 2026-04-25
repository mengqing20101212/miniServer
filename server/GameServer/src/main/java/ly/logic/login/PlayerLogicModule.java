package ly.logic.login;

import ly.logic.player.AbstractModule;
import ly.logic.player.event.PlayerEventParam;
import ly.logic.player.event.PlayerEventType;

import java.util.List;

/**
 * 游戏服玩家相关模型，承载玩家连接状态、持久化数据或模块数据。
 */
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
