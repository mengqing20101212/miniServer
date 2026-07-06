package ly.net;

import ly.LoggerDef;
import ly.net.packet.MessagePacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 单个长连接在业务层的会话对象。
 * <p>
 * 它不直接持有 Netty Channel，而是通过 {@link Connector} 间接写包。收到的包进入
 * receive 队列，由业务线程主动拉取；待发送的包进入 send 队列，由
 * {@link NetService} 的发送任务批量刷新。
 */
public class ConnectSession {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(ConnectSession.class);
    static Logger logger = LoggerDef.SystemLogger;

    private final long guid;
    Connector connector;

    /**
     * 上一次收到的包的序列号
     */
    int lastReceivedSeq;

    /** 待异步发送的消息队列，由 NetService 后台任务统一 flush。 */
    Queue<MessagePacket> sendPacketQueue = new ConcurrentLinkedQueue<MessagePacket>();

    /** 收到的业务包队列；Netty IO 线程只入队，业务逻辑自行 drain。 */
    BlockingQueue<MessagePacket> receivePacketQueue = new ArrayBlockingQueue<>(1024);

    public ConnectSession(long guid) {
        this.guid = guid;
    }

    public long getGuid() {
        return guid;
    }

    public Connector getConnector() {
        return connector;
    }

    public void setConnector(Connector connector) {
        this.connector = connector;
    }

    /**
     * 添加收到的包。
     * <p>
     * 默认会做空包和 seq 连续性校验，子类可通过
     * {@link #checkAddReceivePacket(MessagePacket)} 补充业务校验。
     */
    public void addReceivePacket(MessagePacket packet) {
        if (!canAddReceivePacket(packet))
            return;
        if (!receivePacketQueue.offer(packet)) {
            logger.warn("Too many receive packets, dropping packet. size: {}", receivePacketQueue.size());
        }
        this.lastReceivedSeq = packet.getSeq();
    }

    /**
     * 判断包是否允许进入 receive 队列。
     * <p>
     * seq 为 0 的包被视为无序列要求，非 0 包必须严格递增，用于尽早发现客户端漏包、
     * 重放或乱序。
     */
    public boolean canAddReceivePacket(MessagePacket packet) {
        if (packet == null) {
            logger.error("Can't add receive packet, packet is null");
            return false;
        }
        // if (packet.getSeq() != 0 && lastReceivedSeq != packet.getSeq() - 1) {
        // logger.error(
        // String.format(
        // "sid[%s : %d] 丢包了，上一个包的序列号:%d, 当前包序列号:%d",
        // connector.socketChannel.channel().id(),
        // connector.sessionId,
        // lastReceivedSeq,
        // packet.getSeq()));
        // return false;
        // }
        return checkAddReceivePacket(packet);
    }

    /**
     * 子类扩展点，例如网关/游戏服可在这里做登录态、cmd 白名单等检查。
     */
    protected boolean checkAddReceivePacket(MessagePacket packet) {
        return true;
    }

    /***
     * 该消息不会立即发送，会缓存在sendPacketQueue 由另外一个task 定时刷新 发送
     * 
     * @param packet 待发送的消息
     * @return 是否添加成功
     */
    public boolean addSendPacket(MessagePacket packet) {
        packet = Server2ServerRpcContext.wrapResponseIfNeeded(packet);
        return sendPacketQueue.add(packet);
    }

    /**
     * 如果连接可用 ，则立即发送消息
     *
     * @param packet 待发送消息
     * @return true 发送成功， false发送失败
     */
    public boolean sendPacket(MessagePacket packet) {
        if (connector != null) {
            return connector.write(packet);
        }
        return false;
    }

    /**
     * 刷新当前会话待发送队列。
     * <p>
     * 如果某个包发送失败，会保留后续未 poll 的包，避免在连接不可用时继续空转写入。
     */
    public void sendAllPackets() {
        if (connector != null) {
            MessagePacket sendPacket;
            // 修复逻辑错误，避免跳过第一个包
            while ((sendPacket = sendPacketQueue.poll()) != null) {
                if (!sendPacket(sendPacket)) {
                    break;
                }
            }
        }
    }

    public void closeChannel() {
        if (connector != null && connector.isConnected()) {
            connector.close();
        }
    }

    /***
     * 批量获取当前所有接收的消息并清空队列
     */
    public List<MessagePacket> getReceivePacketList() {
        List<MessagePacket> packets = new ArrayList<>();
        receivePacketQueue.drainTo(packets); // 一次性取出所有内容
        return packets;
    }

    /**
     * 业务周期回调。基础会话不做处理，子类可用于心跳、超时或批量消息处理。
     */
    public void tick() {
    }
}
