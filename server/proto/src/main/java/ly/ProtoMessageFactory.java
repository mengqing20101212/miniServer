package ly;

import com.google.protobuf.AbstractMessage;
import com.google.protobuf.InvalidProtocolBufferException;
import ly.proto.*;

/*
 * Author: liuYang
 * Date: 2025/4/10
 * File: ProtoMessageFactory
 */
public class ProtoMessageFactory {
  public static AbstractMessage createProtoMessage(int cmd, byte[] data) {
    try {
      switch (cmd) {
        case Cmd.CMD.CS_ErrorCode_VALUE ->{return ErrorMsg.csErrorCode.parseFrom(data);}
        case Cmd.CMD.SC_ErrorCode_VALUE ->{return ErrorMsg.scErrorCode.parseFrom(data);}
        case Cmd.CMD.CS_HeroList_VALUE ->{return Hero.CS_HeroList.parseFrom(data);}
        case Cmd.CMD.SC_HeroList_VALUE ->{return Hero.SC_HeroList.parseFrom(data);}
        case Cmd.CMD.CS_HeroLevelUp_VALUE ->{return Hero.CS_HeroLevelUp.parseFrom(data);}
        case Cmd.CMD.SC_HeroLevelUp_VALUE ->{return Hero.SC_HeroLevelUp.parseFrom(data);}
        case Cmd.CMD.CS_HeroStarUp_VALUE ->{return Hero.CS_HeroStarUp.parseFrom(data);}
        case Cmd.CMD.SC_HeroStarUp_VALUE ->{return Hero.SC_HeroStarUp.parseFrom(data);}
        case Cmd.CMD.CS_HeroAwaken_VALUE ->{return Hero.CS_HeroAwaken.parseFrom(data);}
        case Cmd.CMD.SC_HeroAwaken_VALUE ->{return Hero.SC_HeroAwaken.parseFrom(data);}
        case Cmd.CMD.CS_HeroAdd_VALUE ->{return Hero.CS_HeroAdd.parseFrom(data);}
        case Cmd.CMD.SC_HeroAdd_VALUE ->{return Hero.SC_HeroAdd.parseFrom(data);}
        case Cmd.CMD.CS_Login_VALUE ->{return Login.csLogin.parseFrom(data);}
        case Cmd.CMD.SC_Login_VALUE ->{return Login.scLogin.parseFrom(data);}
        case Cmd.CMD.CS_Logout_VALUE ->{return Login.csLogout.parseFrom(data);}
        case Cmd.CMD.SC_Logout_VALUE ->{return Login.scLogout.parseFrom(data);}
        case Cmd.CMD.CS_Move_VALUE ->{return Move.csMove.parseFrom(data);}
        case Cmd.CMD.SC_Move_VALUE ->{return Move.scMove.parseFrom(data);}
        case Cmd.CMD.CS_ResourceQuery_VALUE ->{return Resource.CS_ResourceQuery.parseFrom(data);}
        case Cmd.CMD.SC_ResourceQuery_VALUE ->{return Resource.SC_ResourceQuery.parseFrom(data);}
        case Cmd.CMD.SC_ResourceChange_VALUE ->{return Resource.SC_ResourceChange.parseFrom(data);}
        case Cmd.CMD.SC_ResourceBatchChange_VALUE ->{return Resource.SC_ResourceBatchChange.parseFrom(data);}
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

  @SuppressWarnings("unchecked")
  public static <T extends AbstractMessage> T createProtoMessage(int cmd, byte[] data, Class<T> clazz) {
    AbstractMessage msg = createProtoMessage(cmd, data);
    if (msg == null) {
      return null;
    }
    try {
      return (T) msg;
    } catch (ClassCastException e) {
      // Return null if type doesn't match
      return null;
    }
  }
}
