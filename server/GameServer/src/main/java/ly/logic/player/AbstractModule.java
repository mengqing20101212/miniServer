package ly.logic.player;

import com.baidu.bjf.remoting.protobuf.ProtobufProxy;
import com.baidu.bjf.remoting.protobuf.annotation.Ignore;

import ly.db.entry.PlayerModuleEntry;
import ly.logic.player.event.IPlayerEvent;

/** 玩家逻辑模块抽象，定义模块在玩家对象中的挂载和生命周期职责。 */
public abstract class AbstractModule implements IModule, IPlayerEvent {
    /** 运行期玩家引用，只用于业务上下文，不参与模块 protobuf 持久化。 */
    @Ignore protected transient Player player;

    /** 当前模块对应的数据库记录，与 PlayerData 中的 Entry 为同一对象。 */
    @Ignore protected transient PlayerModuleEntry moduleEntry;

    public void init(Player player) {
        if (player == null || player.getPlayerData() == null) {
            throw new IllegalArgumentException("player and playerData are required");
        }
        ModuleEnum moduleType = ModuleEnum.fromModuleClass(getClass());
        if (moduleType == null) {
            throw new IllegalArgumentException(
                    "unregistered module class: " + getClass().getName());
        }
        init(player, moduleType, player.getPlayerData().createModuleEntry(moduleType), false);
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
        // Blob 元数据由 persistDirtyModuleBlob 整体提交，禁止内部 setter 各自触发一次自动保存。
        moduleEntry.bindPersistenceChangeListener(() -> {});
        getRegisterEventTypes()
                .forEach(eventType -> player.getEventManager().register(eventType, this));
        onLoadData();
        player.getPlayerData().putModule(moduleType, this);
        if (moduleDataMissing && usesBlobPersistence()) {
            markModuleDataDirty();
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
        throw new IllegalStateException(
                "module data loader is not implemented: " + getClass().getName());
    }

    final PlayerModuleEntry getModuleEntry() {
        return moduleEntry;
    }

    @Ignore private transient boolean moduleDataDirty;

    /** 资源等纯 Entity 模块不创建模块 Blob。 */
    protected boolean usesBlobPersistence() {
        return true;
    }

    protected final void markModuleDataDirty() {
        if (!usesBlobPersistence()) {
            return;
        }
        moduleDataDirty = true;
        if (player != null && player.getPlayerData() != null) {
            player.getPlayerData().schedulePersistenceFlush();
        }
    }

    @SuppressWarnings("unchecked")
    final PlayerModuleEntry prepareDirtyModuleBlob() {
        if (!moduleDataDirty || !usesBlobPersistence()) return null;
        try {
            Object data = getModuleDataForPersistence();
            byte[] bytes = ProtobufProxy.create((Class<Object>) data.getClass()).encode(data);
            moduleEntry.setDataVersion(ModuleEnum.fromModuleClass(getClass()).getDataVersion());
            moduleEntry.setRevision(moduleEntry.getRevision() + 1L);
            moduleEntry.setModuleData(bytes);
            moduleEntry.setUpdateTime(java.time.LocalDateTime.now());
            return moduleEntry;
        } catch (Exception error) {
            throw new IllegalStateException("编码模块失败: " + getClass().getName(), error);
        }
    }

    final void acceptPreparedModuleBlob() {
        moduleEntry.markPersisted();
        moduleDataDirty = false;
    }

    /** 使用模块声明的持久化类型统一反序列化。 */
    @SuppressWarnings("unchecked")
    public static AbstractModule deserialize(
            Class<? extends AbstractModule> moduleClass, byte[] moduleBytes) throws Exception {
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
