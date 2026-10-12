package ly.config.hotupdate;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.util.Map;
import java.util.TreeMap;

/** 完整配置包的确定性摘要，不依赖文件枚举次序和节点本地基线。 */
public final class ConfigBundle {
    private ConfigBundle() {}

    public static String digest(Map<String, String> files) {
        try {
            MessageDigest hash = MessageDigest.getInstance("SHA-256");
            for (var entry : new TreeMap<>(files).entrySet()) {
                validateName(entry.getKey());
                for (String value : new String[] {entry.getKey(), entry.getValue()}) {
                    byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
                    hash.update(java.nio.ByteBuffer.allocate(4).putInt(bytes.length).array());
                    hash.update(bytes);
                }
            }
            return java.util.HexFormat.of().formatHex(hash.digest());
        } catch (java.security.NoSuchAlgorithmException error) {
            throw new IllegalStateException(error);
        }
    }

    public static void validateName(String name) {
        if (name == null || !name.matches("[A-Za-z0-9_.-]+\\.txt") || name.contains(".."))
            throw new IllegalArgumentException("非法配置文件名: " + name);
    }

    public static Map<String, String> read(Path directory) throws Exception {
        Map<String, String> files = new TreeMap<>();
        try (var paths = Files.list(directory)) {
            for (Path path : paths.toList())
                if (Files.isRegularFile(path) && path.toString().endsWith(".txt"))
                    files.put(
                            path.getFileName().toString(),
                            Files.readString(path, StandardCharsets.UTF_8));
        }
        return files;
    }
}
