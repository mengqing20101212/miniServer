package ly;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * 工具模块命令入口，用于串联配置、协议和数据库代码生成流程。
 */
public class ToolMain {
  public static void main(String[] args) {
    if (args == null || args.length == 0) {
      printUsage();
      return;
    }
    String type = args[0];
    if (type.equals("parserExcelConfig")) {
      requireArgs(args, 2, "parserExcelConfig <excel-dir>");
      String excelFileDir = args[1];
      System.out.println("开始解析 策划表 ");
      new ParserExcelConfig(excelFileDir).startParser();
      System.out.println("解析 策划表 完成");
    } else if (type.equals("buildConfigData")) {
      requireArgs(args, 3, "buildConfigData <excel-dir> <output-dir>");
      System.out.println("开始生成运行时配置");
      new ParserExcelConfig(args[1], args[2], false).startParser();
      System.out.println("生成运行时配置完成");
    } else if (type.equals("ParserProto")) {
      new ParserProto("D:\\WORK\\me\\miniServer\\proto").parser();
    } else if (type.equals("parserDbEntry")) {
      String module = args[1];
      System.out.println("开始生成DB Entry代码，targetModule=" + module);
      new ParserDbEntry(module).parser();
      System.out.println("生成 DB Entry 完成");
    } else if (type.equals("generateSqlFromEntity")) {
      System.out.println("开始从实体类生成SQL...");
      // 注意：这个功能需要在有数据库连接的情况下运行
      // 这里只是一个示例，实际使用时需要提供数据库连接参数
      System.out.println("此功能已集成到服务器启动流程中，会在启动时自动执行");
    }
    System.out.println("Hello, World!");
  }

  private static void requireArgs(String[] args, int count, String usage) {
    if (args.length != count) {
      throw new IllegalArgumentException("用法: " + usage);
    }
  }

  private static void printUsage() {
    System.out.println("用法:");
    System.out.println("  buildConfigData <excel-dir> <output-dir>");
    System.out.println("  parserExcelConfig <excel-dir>");
    System.out.println("  parserDbEntry <module>");
  }

  public static void copyFile(String srcFile, String destFile) {
    Path sourcePath = Paths.get(srcFile);
    Path destinationPath = Paths.get(destFile);

    try {
      // 复制文件，如果目标文件已存在，则直接替换
      Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
      System.out.println(srcFile + " 复制到 " + destFile + " 成功");
    } catch (IOException e) {
      System.err.println("Error copying file: " + e.getMessage());
    }
  }
}
