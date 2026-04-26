package ly;

/**
 * 手工冒烟测试入口：验证 EntityToSqlGenerator 能否基于 ly.db.entry 生成 SQL 文件。
 */
public class EntityToSqlGeneratorSmokeTest {
    public static void main(String[] args) {
        try {
            System.out.println("开始测试 EntityToSqlGenerator...");

            EntityToSqlGenerator generator = new EntityToSqlGenerator();
            generator.generateSqlFromPackage("ly.db.entry");

            System.out.println("SQL 文件已生成到 ./generated-sql/ 目录下");

            java.io.File createSqlFile = new java.io.File("./generated-sql/create-tables.sql");
            System.out.println("CREATE TABLE SQL 文件是否存在: " + createSqlFile.exists());

            if (createSqlFile.exists()) {
                System.out.println("CREATE TABLE SQL 内容预览:");
                java.nio.file.Files.lines(createSqlFile.toPath())
                    .forEach(System.out::println);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
