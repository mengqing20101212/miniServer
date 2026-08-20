package ly;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * 协议生成辅助工具，扫描 proto 命令定义并生成命令到消息类的工厂代码。
 */
public class ParserProto {
  String cmdFileName;
  String descFactoryFileName;
  String dirFileName;

  public ParserProto(String dir) {
    this.cmdFileName = dir + "/Cmd.proto";
    this.dirFileName = dir;
    this.descFactoryFileName =
        Path.of(dir)
            .toAbsolutePath()
            .resolve("../server/proto/src/main/java/ly/ProtoMessageFactory.java")
            .toString();
  }

  void parser() {
    long startTime = System.currentTimeMillis();
    List<String> cmdList = readCmdList();
    List<MessageProto> protoList = readProtoList(cmdList);
    createMessageFactoryJavaFile(protoList);
    long endTime = System.currentTimeMillis();
    System.out.println("解析 生成 ProtoMessageFactory 耗时: " + (endTime - startTime) + "ms");
  }

  private List<String> readCmdList() {
    List<String> cmdList = new ArrayList<>();
    File cmdFile = new File(cmdFileName);
    try (BufferedReader bufferedReader = new BufferedReader(new FileReader(cmdFile))) {
      String line;
      boolean begin = false;
      while ((line = bufferedReader.readLine()) != null) {
        String trimmed = line.trim();
        if (trimmed.startsWith("enum CMD{") || trimmed.startsWith("enum CMD {")) {
          begin = true;
          continue;
        }
        if (trimmed.startsWith("}") && begin) {
          break;
        }
        if (begin && !trimmed.startsWith("//")) {
          String[] strs = trimmed.split("=");
          if (strs.length > 1 && strs[0].contains("_")) {
            cmdList.add(strs[0].trim());
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return cmdList;
  }

  private List<MessageProto> readProtoList(List<String> cmdList) {
    List<MessageProto> protoList = new ArrayList<>();
    File dir = new File(dirFileName);
    if (!dir.isDirectory()) {
      System.out.println("该路径不是目录: " + dirFileName + "，请检查");
      return protoList;
    }
    File[] files = dir.listFiles();
    if (files == null) {
      return protoList;
    }
    for (File file : files) {
      if (!file.isFile() || !file.getName().endsWith(".proto") || file.getName().equals("Cmd.proto")) {
        continue;
      }
      MessageProto messageProto = new MessageProto();
      messageProto.protoClassName = file.getName().replaceFirst("\\.proto$", "");
      String source = ParserExcelConfig.ExcelConfig.readFile(file).replaceAll("\\s+", "");
      cmdList.forEach(
          cmdStr -> {
            String messageName = findMessageName(source, cmdStr);
            if (messageName != null) {
              messageProto.cmdList.add(new CmdMessage(cmdStr, messageName));
            }
          });
      protoList.add(messageProto);
    }
    return protoList;
  }

  private String findMessageName(String protoSource, String cmdStr) {
    for (String messageName : candidateMessageNames(cmdStr)) {
      if (protoSource.contains("message" + messageName + "{")) {
        return messageName;
      }
    }
    return null;
  }

  private List<String> candidateMessageNames(String cmdStr) {
    List<String> names = new ArrayList<>();
    names.add(cmdStr);
    String[] args = cmdStr.split("_", 2);
    if (args.length == 2) {
      names.add(args[0].toLowerCase() + args[1]);
    }
    return names;
  }

  private void createMessageFactoryJavaFile(List<MessageProto> protoList) {
    StringBuilder caseStr = new StringBuilder();
    protoList.forEach(
        protoMes -> {
          protoMes.cmdList.forEach(
              cmdMessage -> {
                if (cmdMessage.cmd.equals("CMD_null") || cmdMessage.cmd.equals("MaxServeMsgId")) {
                  return;
                }
                caseStr
                    .append("        case Cmd.CMD.")
                    .append(cmdMessage.cmd.trim())
                    .append("_VALUE ->{return ")
                    .append(protoMes.protoClassName.trim())
                    .append(".")
                    .append(cmdMessage.messageName.trim())
                    .append(".parseFrom(data);}\n");
              });
        });

    String score =
        "package ly;\n"
            + "\n"
            + "import com.google.protobuf.AbstractMessage;\n"
            + "import com.google.protobuf.InvalidProtocolBufferException;\n"
            + "import ly.proto.*;\n"
            + "\n"
            + "/*\n"
            + " * Author: liuYang\n"
            + " * Date: 2025/4/10\n"
            + " * File: ProtoMessageFactory\n"
            + " */\n"
            + "public class ProtoMessageFactory {\n"
            + "  public static AbstractMessage createProtoMessage(int cmd, byte[] data) {\n"
            + "    try {\n"
            + "      switch (cmd) {\n"
            + caseStr
            + "      }\n"
            + "    } catch (InvalidProtocolBufferException e) {\n"
            + "      e.printStackTrace();\n"
            + "      return null;\n"
            + "    }\n"
            + "    return null;\n"
            + "  }\n"
            + "\n"
            + "  /**\n"
            + "   * 按协议号反序列化消息，并校验结果是否为调用方期望的 protobuf 类型。\n"
            + "   *\n"
            + "   * <p>协议号与类型不匹配时返回 {@code null}，避免把类型转换异常留到调用方。</p>\n"
            + "   */\n"
            + "  public static <T extends AbstractMessage> T createProtoMessage(int cmd, byte[] data, Class<T> clazz) {\n"
            + "    AbstractMessage message = createProtoMessage(cmd, data);\n"
            + "    return clazz.isInstance(message) ? clazz.cast(message) : null;\n"
            + "  }\n"
            + "}\n";

    System.out.println(score);
    File dstFile = new File(descFactoryFileName);
    if (!dstFile.getParentFile().exists() && !dstFile.getParentFile().mkdirs()) {
      throw new RuntimeException("创建目录失败: " + dstFile.getParent());
    }
    try (FileWriter fileWriter = new FileWriter(dstFile)) {
      fileWriter.write(score);
      fileWriter.flush();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  static class MessageProto {
    String protoClassName;
    List<CmdMessage> cmdList = new ArrayList<>();
  }

  static class CmdMessage {
    String cmd;
    String messageName;

    CmdMessage(String cmd, String messageName) {
      this.cmd = cmd;
      this.messageName = messageName;
    }
  }

  public static void main(String[] args) {
    new ParserProto("D:\\WORK\\me\\miniServer\\proto").parser();
  }
}
