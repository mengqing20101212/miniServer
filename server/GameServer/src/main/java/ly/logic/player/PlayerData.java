package ly.logic.player;

import com.baidu.bjf.remoting.protobuf.Codec;
import com.baidu.bjf.remoting.protobuf.ProtobufProxy;
import ly.db.entry.PlayerEntry;

/**
 * 游戏服玩家相关模型，承载玩家连接状态、持久化数据或模块数据。
 */
public class PlayerData {
    final PlayerEntry playerEntry;
    PlayerModuleData moduleData;

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


    public PlayerEntry getPlayerEntry() {
        return playerEntry;
    }


}
