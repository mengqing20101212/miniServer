package ly;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/*
 * Author: liuYang
 * Date: 2025/4/11
 * File: ParserProto
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
    List<String> cmdList = new ArrayList<>();
    File cmdFile = new File(cmdFileName);
    try (BufferedReader bufferedReader = new BufferedReader(new FileReader(cmdFile))) {
      String line;
      boolean begin = false;
      while ((line = bufferedReader.readLine()) != null) {
        if (line.startsWith("enum CMD{")) {
          begin = true;
          continue;
        }
        if (line.startsWith("}") && begin) {
          break;
        }
        if (begin) {
          if (!line.trim().startsWith("//")) {
            String[] strs = line.split("=");
            if (strs[0].contains("_")) {
              cmdList.add(strs[0]);
            }
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    List<MessageProto> protoList = new ArrayList<>();
    File dir = new File(dirFileName);
    if (!dir.isDirectory()) {
      System.out.println("该路径不是: " + dirFileName + " 目录, 请检查");
      return;
    }
    File[] files = dir.listFiles();
    for (File file : files) {
      if (file.isFile()
          && (file.getName().endsWith(".proto") && !file.getName().equals("Cmd.proto"))) {
        MessageProto messageProto = new MessageProto();
        messageProto.cmdFileName = file.getName().split(".proto")[0];
        String score = ParserExcelConfig.ExcelConfig.readFile(file);
        final String score1 = score.replaceAll("\n", "");
        protoList.add(messageProto);
        cmdList.forEach(
            cmdStr -> {
              String[] args = cmdStr.split("_");
              if (args.length == 2) {
                String mes = args[0].toLowerCase() + args[1];
                String cos = "message " + mes.trim() + "{";
                if (score1.contains(cos)) {
                  messageProto.cmdList.add(cmdStr);
                }
              }
            });
      }
    }
    createMessageFactoryJavaFile(protoList);
    long endTime = System.currentTimeMillis();
    System.out.println("解析 生成 ProtoMessageFactory 耗时: " + (endTime - startTime) + "ms");
  }

  private void createMessageFactoryJavaFile(List<MessageProto> protoList) {
    StringBuffer caseStr = new StringBuffer();
    protoList.forEach(
        protoMes -> {
          protoMes.cmdList.forEach(
              cmd -> {
                if (cmd.equals("CMD_null") || cmd.equals("MaxServeMsgId")) {
                  return;
                }
                caseStr
                    .append(
                        "        case Cmd.CMD."
                            + cmd.trim()
                            + "_VALUE ->{return "
                            + protoMes.cmdFileName.trim()
                            + ".")
                    .append(cmd.split("_")[0].toLowerCase().trim() + cmd.split("_")[1].trim())
                    .append(".parseFrom(data);" + "}\n");
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
            + "}\n";

    System.out.println(score);
    File dstFile = new File(descFactoryFileName);
    if (!dstFile.getParentFile().exists()) {
      dstFile.getParentFile().mkdirs();
    } else {
      dstFile.delete();
    }
    FileWriter fileWriter = null;
    try {
      fileWriter = new FileWriter(dstFile);
      fileWriter.write(score);
      fileWriter.flush();
      fileWriter.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  static class MessageProto {
    String cmdFileName;
    List<String> cmdList = new ArrayList<>();
  }

  public static void main(String[] args) {
    new ParserProto("D:\\WORK\\me\\miniServer\\proto").parser();
  }
}
