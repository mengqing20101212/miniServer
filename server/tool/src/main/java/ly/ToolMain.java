package ly;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class ToolMain {
  public static void main(String[] args) {
    String type = args[0];
    if (type.equals("parserExcelConfig")) {
      String excelFileDir = args[1];
      System.out.println("开始解析 策划表 ");
      new ParserExcelConfig(excelFileDir).startParser();
      System.out.println("解析 策划表 完成");
    } else if (type.equals("ParserProto")) {
      new ParserProto("D:\\WORK\\me\\miniServer\\proto").parser();
    }
    System.out.println("Hello, World!");
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
