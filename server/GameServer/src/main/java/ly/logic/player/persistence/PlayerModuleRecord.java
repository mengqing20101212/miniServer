package ly.logic.player.persistence;

import java.util.Arrays;

/** 数据库中一个玩家模块的持久化快照。 */
public final class PlayerModuleRecord {
    private final int moduleId;
    private final int dataVersion;
    private final long revision;
    private final byte[] data;

    public PlayerModuleRecord(int moduleId, int dataVersion, long revision, byte[] data) {
        this.moduleId = moduleId;
        this.dataVersion = dataVersion;
        this.revision = revision;
        this.data = data == null ? new byte[0] : Arrays.copyOf(data, data.length);
    }

    public int moduleId() {
        return moduleId;
    }

    public int dataVersion() {
        return dataVersion;
    }

    public long revision() {
        return revision;
    }

    public byte[] data() {
        return Arrays.copyOf(data, data.length);
    }
}
