import ly.EntityToSqlGenerator;

public class test_entity_generator {
    public static void main(String[] args) {
        try {
            System.out.println("开始测试EntityToSqlGenerator...");
            
            // 创建生成器实例
            EntityToSqlGenerator generator = new EntityToSqlGenerator();
            
            // 生成SQL文件
            generator.generateSqlFromPackage("ly.db.entry");
            
            System.out.println("SQL文件已生成到 ./generated-sql/ 目录下");
            
            // 检查生成的文件
            java.io.File createSqlFile = new java.io.File("./generated-sql/create-tables.sql");
            System.out.println("CREATE TABLE SQL文件是否存在: " + createSqlFile.exists());
            
            if (createSqlFile.exists()) {
                System.out.println("CREATE TABLE SQL内容预览:");
                java.nio.file.Files.lines(createSqlFile.toPath())
                    .forEach(System.out::println);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}