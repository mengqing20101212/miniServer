package ly.logic.player;

import com.baidu.bjf.remoting.protobuf.Codec;
import com.baidu.bjf.remoting.protobuf.ProtobufProxy;
import ly.db.entry.PlayerEntry;

public class PlayerData {
    final PlayerEntry playerEntry;
    PlayerModuleData moduleData;

    public PlayerData(PlayerEntry playerEntry) {
        this.playerEntry = playerEntry;
        Codec<PlayerModuleData> moduleDataCodec = ProtobufProxy
                .create(PlayerModuleData.class);
        try {
            moduleData = moduleDataCodec.decode(playerEntry.getModules());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public byte[] getModuleData(ModuleEnum moduleType) {
        return moduleData.getModuleData(moduleType.getName());
    }


    public PlayerEntry getPlayerEntry() {
        return playerEntry;
    }


}
