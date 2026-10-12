package ly;

import java.util.Map;

/** 一个完整配置版本。发布后表及其记录仅供读取，不主动保存历史快照。 */
public final class ConfigSnapshot {
    private final String version;
    private final Map<Class<?>, AbstractConfigManger> tables;

    public ConfigSnapshot(String version, Map<Class<?>, AbstractConfigManger> tables) {
        this.version = java.util.Objects.requireNonNull(version);
        this.tables = Map.copyOf(tables);
    }

    public String version() {
        return version;
    }

    public <T extends AbstractConfigManger> T table(Class<T> type) {
        AbstractConfigManger table = tables.get(type);
        if (table == null) throw new ConfigLoadException("配置快照缺少表: " + type.getName());
        return type.cast(table);
    }

    Map<Class<?>, AbstractConfigManger> tables() {
        return tables;
    }
}
