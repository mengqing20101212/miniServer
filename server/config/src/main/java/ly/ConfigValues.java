package ly;

public final class ConfigValues {
    private ConfigValues() {}

    // 具体 Java 类型由生成器和同一份表头类型树确定，入口完成解析后不再转换或复制集合。
    @SuppressWarnings("unchecked")
    public static <T> T read(ConfigValueType type, String text, String location) {
        try {
            return (T) type.readCell(text);
        } catch (RuntimeException error) {
            throw new ConfigLoadException(location + ": " + error.getMessage());
        }
    }
}
