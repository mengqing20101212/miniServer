package ly.logic.player;

public interface IModule {


    public void onLoadData();

    public boolean saveData();

    default void onLogin(boolean isReconnect) {
    }

    default void onLogout() {
    }

    public void onOpenFunction();
}
