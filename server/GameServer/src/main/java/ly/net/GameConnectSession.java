package ly.net;

import com.google.protobuf.AbstractMessage;

import ly.LoggerDef;
import ly.net.packet.MessagePacket;
import ly.net.packet.MessagePacketFactory;
import ly.proto.Cmd;
import ly.proto.ErrorMsg;
import ly.utils.CommonUtils;

/**
 * 游戏服连接会话，封装网络连接与玩家协议包收发逻辑。
 */
public class GameConnectSession extends ConnectSession {
    public GameConnectSession(long guid) {
        super(guid);
    }

    @Override
    public void tick() {
    }

    @Override
    public void addReceivePacket(MessagePacket packet) {
        super.addReceivePacket(packet);
        // LoggerDef.LogProto("receive {}|{}|{}|{}", getGuid(), packet.getSid(),
        // packet.getCmd(), packet.getLength());
        GameHandlerRouteManager.execute(this, packet);
    }

    public void sendClientMsg(int cmd, long playerId, AbstractMessage msg) {
        MessagePacket s2cPacket = MessagePacketFactory.createMessagePacket(playerId, cmd, msg, 0,
                (int) getGuid());
        LoggerDef.LogProto("send {}|{}|{}", playerId, Cmd.CMD.forNumber(cmd).name(), CommonUtils.logProto(msg));
        addSendPacket(s2cPacket);
    }

    public void sendErrorMsg(long playerId, ErrorMsg.ErrorCode errorCode, int cmd) {
        ErrorMsg.scErrorCode errorMsg = ErrorMsg.scErrorCode.newBuilder().setErrorCode(errorCode).setMsgId(cmd).build();
        sendClientMsg(Cmd.CMD.SC_ErrorCode_VALUE, playerId, errorMsg);
    }
}
