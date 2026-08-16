package ly.logic.player;

import com.baidu.bjf.remoting.protobuf.Codec;
import com.baidu.bjf.remoting.protobuf.ProtobufProxy;
import com.baidu.bjf.remoting.protobuf.annotation.Ignore;

import ly.db.entry.PlayerModuleEntry;
import ly.logic.player.event.IPlayerEvent;

/**
 * 玩家逻辑模块抽象，定义模块在玩家对象中的挂载和生命周期职责。
 */
public abstract class AbstractModule implements IModule, IPlayerEvent {
    /** 运行期玩家引用，只用于业务上下文，不参与模块 protobuf 持久化。 */
    @Ignore
    protected transient Player player;

    /** 当前模块对应的数据库记录，与 PlayerData 中的 Entry 为同一对象。 */
    @Ignore
    protected transient PlayerModuleEntry moduleEntry;

    public void init(Player player) {
        this.player = player;
        if (player != null && player.getPlayerData() != null) {
            ModuleEnum moduleType = ModuleEnum.fromModuleClass(getClass());
            if (moduleType != null) {
                this.moduleEntry = player.getPlayerData().getOrCreateModuleEntry(moduleType);
            }
        }
        getRegisterEventTypes().forEach(eventType -> player.getEventManager().register(eventType, this));
    }

    /** 统一完成模块挂载、数据初始化，以及缺失模块记录的首次创建。 */
    public final void init(
            Player player,
            ModuleEnum moduleType,
            PlayerModuleEntry moduleEntry,
            boolean moduleDataMissing) {
        if (moduleType == null
                || moduleEntry == null
                || moduleEntry.getModuleId() == null
                || moduleEntry.getModuleId() != moduleType.getModuleId()) {
            throw new IllegalArgumentException("moduleType and moduleEntry do not match");
        }
        this.player = player;
        this.moduleEntry = moduleEntry;
        getRegisterEventTypes().forEach(eventType -> player.getEventManager().register(eventType, this));
        onLoadData();
        player.getPlayerData().putModule(moduleType, this);
        if (moduleDataMissing && !saveData()) {
            throw new IllegalStateException(
                    "initialize player module failed, playerId=" + player.getPlayerId() + ", module=" + moduleType);
        }
    }

    /** 模块实际写入 protobuf 的对象，默认直接保存模块自身。 */
    protected Object getModuleDataForPersistence() {
        return this;
    }

    /** 模块实际读取的 protobuf 类型，默认直接恢复模块自身。 */
    protected Class<?> getModuleDataClass() {
        return getClass();
    }

    /** 独立数据对象解码后的装配入口；直接恢复模块自身时不调用。 */
    protected void loadModuleData(Object moduleData) {
        throw new IllegalStateException("module data loader is not implemented: " + getClass().getName());
    }

    /** 统一序列化当前模块并更新绑定的 Entry。 */
    @Override
    @SuppressWarnings("unchecked")
    public final boolean saveData() {
        Object moduleData = getModuleDataForPersistence();
        if (player == null || player.getPlayerData() == null || moduleEntry == null || moduleData == null) {
            return false;
        }
        try {
            Codec<Object> codec = ProtobufProxy.create((Class<Object>) moduleData.getClass());
            player.getPlayerData().markModuleDirty(moduleEntry, codec.encode(moduleData));
            return true;
        } catch (Exception e) {
            throw new IllegalStateException(
                    "serialize player module failed, playerId=" + player.getPlayerId()
                            + ", moduleId=" + moduleEntry.getModuleId(),
                    e);
        }
    }

    /** 使用模块声明的持久化类型统一反序列化。 */
    @SuppressWarnings("unchecked")
    public static AbstractModule deserialize(
            Class<? extends AbstractModule> moduleClass,
            byte[] moduleBytes) throws Exception {
        AbstractModule emptyModule = moduleClass.getDeclaredConstructor().newInstance();
        Class<Object> dataClass = (Class<Object>) emptyModule.getModuleDataClass();
        Object decoded = ProtobufProxy.create(dataClass).decode(moduleBytes);
        if (moduleClass.isInstance(decoded)) {
            return (AbstractModule) decoded;
        }
        emptyModule.loadModuleData(decoded);
        return emptyModule;
    }
}
