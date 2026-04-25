package ly.logic.player;

/**
 * 玩家逻辑模块抽象，定义模块在玩家对象中的挂载和生命周期职责。
 */
public interface IModule {


    public void onLoadData();

    public boolean saveData();

    default void onLogin(boolean isReconnect) {
    }

    default void onLogout() {
    }

    public void onOpenFunction();
}
