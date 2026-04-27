package ly.logic.player;

/**
 * 玩家逻辑模块抽象，定义模块在玩家对象中的挂载和生命周期职责。
 */
public interface IModule {

    /**
     * 从持久化数据加载模块状态，通常在玩家登录时调用。实现类一般来说没有特殊的业务在加载数据之后
     * 处理，那么就不需要实现代码了，直接在实现类里写个空方法体就行了。
     */
    public void onLoadData();

    /**
     * 将模块状态保存到持久化数据，通常在玩家登出或定时保存时调用.
     * 实现类一般来说没有特殊的业务在保存数据之前处理，那么就不需要实现代码了，直接在实现类里写个空方法体就行了。
     * 
     * @return
     */
    public boolean saveData();

    /**
     * 玩家登录时调用，参数 {@code isReconnect}
     * 表示是否为断线重连。实现类可以根据需要在登录时执行一些初始化逻辑，例如刷新状态、发送欢迎消息等。
     * 
     * @param isReconnect
     */
    default void onLogin(boolean isReconnect) {
    }

    /**
     * 玩家登出时调用，表示是否为断线重连。实现类可以根据需要在登出时执行一些清理逻辑，例如保存状态、释放资源等。
     */
    default void onLogout() {
    }

    /**
     * 功能开启时调用。实现类可以根据需要在功能开启时执行一些逻辑，例如初始化数据、发送通知等。
     */
    public void onOpenFunction();
}
