package ly.net;

import com.google.protobuf.AbstractMessage;
import ly.LoggerDef;
import ly.net.packet.AbstractMessagePacket;
import ly.net.packet.MessagePacketFactory;
import ly.proto.Cmd;
import ly.proto.ErrorMsg;

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
    public void addReceivePacket(AbstractMessagePacket packet) {
        super.addReceivePacket(packet);
        LoggerDef.LogProto("receive {}|{}|{}|{}", getGuid(), packet.getSid(), packet.getCmd(), packet.getLength());
        GameHandlerRouteManager.execute(this, packet);
    }


    public void sendClientMsg(int cmd, int seq, long playerId, AbstractMessage msg) {
        AbstractMessagePacket s2cPacket = MessagePacketFactory.createAbstractMessagePacket(playerId, cmd, msg, seq, (int) getGuid());
        addSendPacket(s2cPacket);
    }

    public void sendErrorMsg(long playerId, ErrorMsg.ErrorCode errorCode, int req, int cmd) {
        ErrorMsg.scErrorCode errorMsg = ErrorMsg.scErrorCode.newBuilder().setErrorCode(errorCode).setMsgId(cmd).build();
        sendClientMsg(Cmd.CMD.SC_ErrorCode_VALUE, req + 1, playerId, errorMsg);
    }
}
