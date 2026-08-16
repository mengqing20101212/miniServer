package ly.logic.player;

import com.baidu.bjf.remoting.protobuf.Codec;
import com.baidu.bjf.remoting.protobuf.ProtobufProxy;
import com.baidu.bjf.remoting.protobuf.annotation.Ignore;

import ly.logic.player.event.IPlayerEvent;

/**
 * 玩家逻辑模块抽象，定义模块在玩家对象中的挂载和生命周期职责。
 */
public abstract class AbstractModule implements IModule, IPlayerEvent {
    /** 运行期玩家引用，只用于业务上下文，不参与模块 protobuf 持久化。 */
    @Ignore
    protected transient Player player;

    public void init(Player player) {
        this.player = player;
        getRegisterEventTypes().forEach(eventType -> player.getEventManager().register(eventType, this));
    }

    /** 统一完成模块挂载、数据初始化，以及缺失模块记录的首次创建。 */
    public final void init(Player player, ModuleEnum moduleType, boolean moduleDataMissing) {
        init(player);
        onLoadData();
        player.getPlayerData().putModule(moduleType, this);
        if (moduleDataMissing && !saveData()) {
            throw new IllegalStateException(
                    "initialize player module failed, playerId=" + player.getPlayerId() + ", module=" + moduleType);
        }
    }

    /** 序列化当前模块并标记模块级快照为脏，数据库写入由统一刷新流程完成。 */
    @SuppressWarnings("unchecked")
    protected boolean saveModuleData(ModuleEnum moduleType, Object moduleData) {
        if (player == null || player.getPlayerData() == null || moduleType == null || moduleData == null) {
            return false;
        }
        try {
            Codec<Object> codec = ProtobufProxy.create((Class<Object>) moduleData.getClass());
            player.getPlayerData().markModuleDirty(moduleType, codec.encode(moduleData));
            return true;
        } catch (Exception e) {
            throw new IllegalStateException(
                    "serialize player module failed, playerId=" + player.getPlayerId() + ", module=" + moduleType,
                    e);
        }
    }
}
