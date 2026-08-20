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
        case Cmd.CMD.CS_GmPlayerDetail_VALUE ->{return GmPlayer.csGmPlayerDetail.parseFrom(data);}
        case Cmd.CMD.SC_GmPlayerDetail_VALUE ->{return GmPlayer.scGmPlayerDetail.parseFrom(data);}
        case Cmd.CMD.CS_GmUpdatePlayerModule_VALUE ->{return GmPlayer.csGmUpdatePlayerModule.parseFrom(data);}
        case Cmd.CMD.SC_GmUpdatePlayerModule_VALUE ->{return GmPlayer.scGmUpdatePlayerModule.parseFrom(data);}
        case Cmd.CMD.CS_GmRuntimeScriptExecute_VALUE ->{return GmRuntimeScriptProto.csGmRuntimeScriptExecute.parseFrom(data);}
        case Cmd.CMD.SC_GmRuntimeScriptExecute_VALUE ->{return GmRuntimeScriptProto.scGmRuntimeScriptExecute.parseFrom(data);}
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
        case Cmd.CMD.CS_SceneQuery_VALUE ->{return Scene.csSceneQuery.parseFrom(data);}
        case Cmd.CMD.SC_SceneQuery_VALUE ->{return Scene.scSceneQuery.parseFrom(data);}
        case Cmd.CMD.CS_SceneEnter_VALUE ->{return Scene.csSceneEnter.parseFrom(data);}
        case Cmd.CMD.SC_SceneEnter_VALUE ->{return Scene.scSceneEnter.parseFrom(data);}
        case Cmd.CMD.CS_SceneMove_VALUE ->{return Scene.csSceneMove.parseFrom(data);}
        case Cmd.CMD.SC_SceneMove_VALUE ->{return Scene.scSceneMove.parseFrom(data);}
        case Cmd.CMD.CS_SceneMetrics_VALUE ->{return Scene.csSceneMetrics.parseFrom(data);}
        case Cmd.CMD.SC_SceneMetrics_VALUE ->{return Scene.scSceneMetrics.parseFrom(data);}
        case Cmd.CMD.CS_SceneLeave_VALUE ->{return Scene.csSceneLeave.parseFrom(data);}
        case Cmd.CMD.SC_SceneLeave_VALUE ->{return Scene.scSceneLeave.parseFrom(data);}
        case Cmd.CMD.CS_SceneView_VALUE ->{return Scene.csSceneView.parseFrom(data);}
        case Cmd.CMD.SC_SceneView_VALUE ->{return Scene.scSceneView.parseFrom(data);}
        case Cmd.CMD.CS_ScenePathFind_VALUE ->{return Scene.csScenePathFind.parseFrom(data);}
        case Cmd.CMD.SC_ScenePathFind_VALUE ->{return Scene.scScenePathFind.parseFrom(data);}
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

  /**
   * 按协议号反序列化消息，并校验结果是否为调用方期望的 protobuf 类型。
   *
   * <p>该重载供需要强类型返回值的网关/RPC 调用使用；协议号与类型不匹配时返回 {@code null}，
   * 避免把类型转换异常留到调用方。</p>
   */
  public static <T extends AbstractMessage> T createProtoMessage(int cmd, byte[] data, Class<T> clazz) {
    AbstractMessage message = createProtoMessage(cmd, data);
    return clazz.isInstance(message) ? clazz.cast(message) : null;
  }
}
