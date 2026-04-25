package ly;

import com.google.protobuf.AbstractMessage;
import com.google.protobuf.InvalidProtocolBufferException;
import ly.proto.*;

/**
 * 工厂类，集中创建对应类型对象以隔离调用方和具体实现。
 */
public class ProtoMessageFactory {
  public static AbstractMessage createProtoMessage(int cmd, byte[] data) {
    try {
      switch (cmd) {
        case Cmd.CMD.CS_ErrorCode_VALUE ->{return ErrorMsg.csErrorCode.parseFrom(data);}
        case Cmd.CMD.SC_ErrorCode_VALUE ->{return ErrorMsg.scErrorCode.parseFrom(data);}
        case Cmd.CMD.CS_Login_VALUE ->{return Login.csLogin.parseFrom(data);}
        case Cmd.CMD.SC_Login_VALUE ->{return Login.scLogin.parseFrom(data);}
        case Cmd.CMD.CS_Logout_VALUE ->{return Login.csLogout.parseFrom(data);}
        case Cmd.CMD.SC_Logout_VALUE ->{return Login.scLogout.parseFrom(data);}
        case Cmd.CMD.CS_Server2Server_VALUE ->{return Server.csServer2Server.parseFrom(data);}
        case Cmd.CMD.SC_Server2Server_VALUE ->{return Server.scServer2Server.parseFrom(data);}
        case Cmd.CMD.CS_RpcPing_VALUE ->{return Server.csRpcPing.parseFrom(data);}
        case Cmd.CMD.SC_RpcPing_VALUE ->{return Server.scRpcPing.parseFrom(data);}
        case Cmd.CMD.CS_Gate2GameRpcGameCall_VALUE ->{return Server.csGate2GameRpcGameCall.parseFrom(data);}
        case Cmd.CMD.SC_Gate2GameRpcGameCall_VALUE ->{return Server.scGate2GameRpcGameCall.parseFrom(data);}
      }
    } catch (InvalidProtocolBufferException e) {
      e.printStackTrace();
      return null;
    }
    return null;
  }
}
