package ly.sceneserver.common.persistence;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * 防止后续业务代码绕过实体层重新引入手写 SQL。
 *
 * <p>Core 的 MysqlService/MysqlConnector 是允许生成和执行 SQL 的基础设施边界；GameServer 与
 * SceneServer 只能依赖 Entry、EntryHelper 和 Store。测试只扫描 main，数据库连通性测试中的
 * 原生 JDBC 不影响生产代码边界。
 */
public class BusinessPersistenceBoundaryTest {
    private static final List<String> FORBIDDEN = List.of(
            "import ly.db.MysqlService",
            "import ly.db.MysqlConnector",
            "getMysqlConnector(",
            "prepareStatement(",
            "executeUpdate(",
            "executeQuery(");

    @Test
    public void gameAndSceneBusinessCodeCannotBypassEntities() throws IOException {
        List<Path> roots = List.of(
                Path.of("src", "main", "java"),
                Path.of("..", "GameServer", "src", "main", "java"));
        ArrayList<String> violations = new ArrayList<>();
        for (Path root : roots) {
            if (!Files.isDirectory(root)) {
                fail("business source root does not exist: " + root.toAbsolutePath());
            }
            try (var files = Files.walk(root)) {
                for (Path file : files.filter(path -> path.toString().endsWith(".java")).toList()) {
                    String source = Files.readString(file, StandardCharsets.UTF_8);
                    for (String forbidden : FORBIDDEN) {
                        if (source.contains(forbidden)) {
                            violations.add(file + " contains " + forbidden);
                        }
                    }
                }
            }
        }
        if (!violations.isEmpty()) {
            fail("business persistence must use Entry/Helper/Store:\n" + String.join("\n", violations));
        }
    }
}
