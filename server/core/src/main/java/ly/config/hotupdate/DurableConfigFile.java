package ly.config.hotupdate;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.*;

/** 同目录写临时文件、强制刷盘后原子替换，禁止以非原子覆盖降级。 */
public final class DurableConfigFile {
    private static final ObjectMapper JSON = new ObjectMapper();

    private DurableConfigFile() {}

    public static void write(Path target, Object value) throws Exception {
        Files.createDirectories(target.toAbsolutePath().getParent());
        Path temporary = target.resolveSibling(target.getFileName() + ".tmp");
        byte[] bytes = JSON.writeValueAsBytes(value);
        try (FileChannel channel =
                FileChannel.open(
                        temporary,
                        StandardOpenOption.CREATE,
                        StandardOpenOption.TRUNCATE_EXISTING,
                        StandardOpenOption.WRITE)) {
            ByteBuffer buffer = ByteBuffer.wrap(bytes);
            while (buffer.hasRemaining()) channel.write(buffer);
            channel.force(true);
        }
        Files.move(
                temporary,
                target,
                StandardCopyOption.ATOMIC_MOVE,
                StandardCopyOption.REPLACE_EXISTING);
        // Linux 持久化目录项；Windows 不支持目录 FileChannel，文件内容已 force。
        if (!System.getProperty("os.name").startsWith("Windows")) {
            try (FileChannel directory =
                    FileChannel.open(
                            target.toAbsolutePath().getParent(), StandardOpenOption.READ)) {
                directory.force(true);
            }
        }
    }

    public static <T> T read(Path target, Class<T> type) throws Exception {
        return JSON.readValue(Files.readAllBytes(target), type);
    }
}
