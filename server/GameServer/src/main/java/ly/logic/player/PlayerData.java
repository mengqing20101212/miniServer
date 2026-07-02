package ly.logic.player;

import java.util.HashMap;
import java.util.Map;

import com.baidu.bjf.remoting.protobuf.Codec;
import com.baidu.bjf.remoting.protobuf.ProtobufProxy;

import ly.db.entry.PlayerEntry;

/**
 * 游戏服玩家相关模型，承载玩家连接状态、持久化数据或模块数据。
 */
public class PlayerData {
    final PlayerEntry playerEntry;
    PlayerModuleData moduleData;
    private final Map<String, AbstractModule> modules = new HashMap<>();

    public PlayerData(PlayerEntry playerEntry) {
        this.playerEntry = playerEntry;
        Codec<PlayerModuleData> moduleDataCodec = ProtobufProxy
                .create(PlayerModuleData.class);
        try {
            byte[] modules = playerEntry.getModules();
            if (modules == null || modules.length == 0) {
                moduleData = new PlayerModuleData();
            } else {
                moduleData = moduleDataCodec.decode(modules);
            }
        } catch (Exception e) {
            e.printStackTrace();
            moduleData = new PlayerModuleData();
        }
    }

    public byte[] getModuleData(ModuleEnum moduleType) {
        return moduleData.getModuleData(moduleType.getName());
    }

    public PlayerModuleData getModuleData() {
        return moduleData;
    }

    public void putModule(ModuleEnum moduleType, AbstractModule module) {
        modules.put(moduleType.getName(), module);
    }

    public AbstractModule getModule(ModuleEnum moduleType) {
        return modules.get(moduleType.getName());
    }

    public PlayerEntry getPlayerEntry() {
        return playerEntry;
    }

    /**
     * 写回单个模块的 protobuf 数据，并重新序列化 PlayerModuleData 到 PlayerEntry.modules。
     *
     * <p>这里只调用 PlayerEntry.setModules 标记脏数据，不主动执行 DB update；后续仍交给现有保存流程处理。
     */
    public void markModuleDirty(ModuleEnum moduleType, byte[] moduleBytes) {
        moduleData.addModuleData(moduleType.getName(), moduleBytes);
        try {
            Codec<PlayerModuleData> moduleDataCodec = ProtobufProxy.create(PlayerModuleData.class);
            playerEntry.setModules(moduleDataCodec.encode(moduleData));
        } catch (Exception e) {
            throw new IllegalStateException("serialize player module data failed, module=" + moduleType.name(), e);
        }
    }

}
