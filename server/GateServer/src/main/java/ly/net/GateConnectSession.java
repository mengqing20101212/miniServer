package ly.net;

import com.google.protobuf.Message;
import ly.GateClientManager;
import ly.LoggerDef;
import ly.net.packet.*;
import ly.proto.Cmd;

/**
 * 网关连接会话类
 * <p>
 * 负责处理网关服务器与客户端之间的连接，管理消息的接收和转发，
 * 是网关服务器中的核心网络会话组件。
 */
public class GateConnectSession extends ConnectSession {
    /**
     * 构造函数
     *
     * @param guid 会话全局唯一标识符
     */
    public GateConnectSession(long guid) {
        super(guid);
    }

    /**
     * 会话心跳更新方法
     * <p>
     * 网关会话暂不需要特定的心跳处理逻辑
     */
    @Override
    public void tick() {
        // 网关会话心跳更新，目前无需特殊处理
    }

    /**
     * 处理接收到的数据包
     * <p>
     * 根据数据包类型进行不同的处理：
     * 1. 客户端到服务器的数据包(AbstractMessagePacket)
     * 2. 服务器到服务器的数据包(AbstractMessagePacket)
     *
     * @param packet 接收到的数据包
     */
    @Override
    public void addReceivePacket(AbstractMessagePacket packet) {
        // 调用父类方法进行基础处理
        super.addReceivePacket(packet);

        // 记录接收到的数据包信息
        LoggerDef.LogProto("receive {}|{}|{}|{}", getGuid(), packet.getSid(), packet.getCmd(), packet.getLength());

        // 统一包结构后，通过命令区间判定来源类型：
        // 10000~20000 为服务器间消息；其余视为客户端请求。
        boolean serverInnerCmd = packet.getCmd() > Cmd.CMD.CS_Server2Server_VALUE
                && packet.getCmd() <= Cmd.CMD.MaxServeMsgId_VALUE;

        // 处理客户端到服务器的数据包
        if (!serverInnerCmd && packet.getCmd() != Cmd.CMD.SC_Logout_VALUE) {
            AbstractMessagePacket csPacket = packet;
            // 尝试获取对应的客户端对象
            GateClient client = GateClientManager.getInstance().getClient(getGuid());

            // 如果客户端对象不存在（未登录状态），则交给处理器路由处理（如登录请求）
            if (client == null) {
                try {
                    // 执行处理器路由
                    HandlerRouterManager.execute(this, csPacket);
                    // 处理完毕后关闭通道
                    closeChannel();
                } catch (Exception e) {
                    // 记录异常信息
                    LoggerDef.SystemLogger.error("GateConnectSession addReceivePacket error, cmd={}", csPacket.getCmd(), e);
                    e.printStackTrace();
                }
            } else {
                // 已登录状态，转发数据包到游戏服务器
                client.sendPacketToGameServer(csPacket);
            }
        }
        // 处理服务器到服务器的数据包
        else {
            AbstractMessagePacket s2sPacket = packet;
            // 处理登出命令特殊情况
            if (s2sPacket.getCmd() == Cmd.CMD.SC_Logout_VALUE) {
                HandlerRouterManager.execute(this, s2sPacket);
            } else {
                // 查找对应的客户端并转发消息
                GateClient client = GateClientManager.getInstance().getClient(getGuid());
                if (client == null) {
                    // 兼容部分链路通过 sid 关联客户端的情况
                    client = GateClientManager.getInstance().getClient((long) s2sPacket.getSid());
                }
                if (client != null) {
                    client.sendPacketToClient(s2sPacket);
                }
            }
        }
    }


    /**
     * 向客户端发送消息
     *
     * @param cmd 命令ID
     * @param msg Protobuf消息对象
     */
    public void sendClientMsg(int cmd, Message msg) {
        // 创建服务器到客户端的数据包
        AbstractMessagePacket s2cPacket = new AbstractMessagePacket();
        s2cPacket.setGuid(getGuid());
        s2cPacket.setCmd(cmd);
        s2cPacket.setData(msg.toByteArray());
        // 添加到发送队列
        addSendPacket(s2cPacket);
    }
}
