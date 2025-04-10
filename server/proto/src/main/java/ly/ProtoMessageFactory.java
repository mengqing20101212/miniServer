package ly;

import com.google.protobuf.AbstractMessage;
import com.google.protobuf.InvalidProtocolBufferException;
import ly.proto.Cmd;
import ly.proto.Login;

/*
 * Author: liuYang
 * Date: 2025/4/10
 * File: ProtoMessageFactory
 */
public class ProtoMessageFactory {
  public static AbstractMessage createProtoMessage(int cmd, byte[] data) {
    try {
      switch (cmd) {
        case Cmd.CMD.CS_Login_VALUE -> {
          return Login.csLogin.parseFrom(data);
        }
        case Cmd.CMD.SC_Login_VALUE -> {
          return Login.scLogin.parseFrom(data);
        }
      }
    } catch (InvalidProtocolBufferException e) {
      e.printStackTrace();
      return null;
    }
    return null;
  }
}
