package ly.net;

import com.google.protobuf.AbstractMessage;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import ly.LoggerDef;
import ly.net.packet.MessagePacket;
import ly.net.packet.MessagePacketFactory;
import org.slf4j.Logger;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 到远端服务器节点的 TCP 客户端。
 * <p>
 * 主要用于服务器间 RPC，也可用于测试客户端。发送时会自动维护链路 seq；接收包进入本地队列。
 * 同步 RPC 必须通过业务层 callId 匹配结果，seq 只用于链路日志和客户端包顺序排查。
 */
public class NetClient {
    static Logger logger = LoggerDef.NetLogger;

    private final String host;
    private final int port;
    private Channel channel;
    private EventLoopGroup group;
    private final boolean isMultiplex;
    private int sid;
    BlockingQueue<MessagePacket> receivePacketQueue = new ArrayBlockingQueue<>(1024);
    AtomicInteger sendSeq = new AtomicInteger(0);
    static AttributeKey<NetClient> SELF_ATTR_KEY = AttributeKey.valueOf("NET_CLIENT");

    public NetClient(String host, int port, boolean isMultiplex) {
        this.isMultiplex = isMultiplex;
        this.host = host;
        this.port = port;
    }

    public boolean isMultiplex() {
        return isMultiplex;
    }

    public String getId() {
        return channel.id().asLongText();
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getIpPortKey() {
        return host + ":" + port;
    }

    /**
     * 在指定 EventLoopGroup 上启动连接。
     * <p>
     * 连接本身是异步建立的，调用方应通过 {@link #isReady()} 判断是否已经拿到 sid。
     */
    public void start(EventLoopGroup group) {
        this.group = group;
        connectOnce();
    }

    private void connectOnce() {
        Bootstrap bootstrap = new Bootstrap();
        setSid(0);
        final NetClient SELF = this;
        bootstrap
                .group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.SO_SNDBUF, NetService.SO_SNDBUF)
                .option(ChannelOption.SO_RCVBUF, NetService.SO_RCVBUF)
                .handler(
                        new ChannelInitializer<Channel>() {
                            @Override
                            protected void initChannel(Channel ch) throws Exception {
                                ch.attr(SELF_ATTR_KEY).set(SELF);
                                ch.pipeline()
                                        .addLast(new CommonDecoder())
                                        .addLast(new CommonEncoder())
                                        .addLast(new ClientHandler());
                            }
                        });

        try {
            final long begin = System.currentTimeMillis();
            ChannelFuture future = bootstrap.connect(new InetSocketAddress(host, port)).sync();
            if (future.isSuccess()) {
                channel = future.channel();
                NetClientManager.getInstance().addNewClient(this);
                logger.info("Netty 客户端连接成功： channelId:{},  {}:{},  耗时:{}毫秒", channel.id().asLongText(), host, port,
                        System.currentTimeMillis() - begin);
                int maxTimeOut = 1000;
                while (!isReady() && maxTimeOut > 0) {
                    Thread.sleep(3);
                    maxTimeOut -= 3;
                }
            } else {
                logger.error("客户端连接失败", future.cause());
            }
        } catch (Exception e) {
            logger.error("客户端连接失败", e);
        }
    }

    /**
     * 发送服务器间 protobuf 消息。
     *
     * @return 本次发送使用的 seq；发送失败返回 -1
     */
    public int sendS2SMessage(long guid, int cmd, AbstractMessage protoData) {
        final int seq = sendSeq.getAndIncrement();
        MessagePacket messagePacket = MessagePacketFactory.createMessagePacket(
                guid, cmd, protoData, seq, sid);
        return send(messagePacket) ? seq : -1;
    }

    public void setSid(int sid) {
        if (this.sid == 0) {
            this.sid = sid;
        }
    }

    public int getSendSeq() {
        return sendSeq.getAndIncrement();
    }

    public void stop() {
        if (group != null) {
            channel.attr(NetService.SELF_CLOSED).set(true);
            channel.close();
        }
    }

    public boolean isConnected() {
        return channel != null && channel.isActive();
    }

    /**
     * 发送已有协议包。
     * <p>
     * 若包内 sid 为 0，会填入当前连接握手得到的 sid；若 seq 为 0，则分配新的自增序号。
     */
    public boolean send(MessagePacket packet) {
        if (isConnected()) {
            sendPacket(packet);
            return true;
        } else {
            int i = 3;
            while (i-- > 0) {
                connectOnce();
                logger.info("连接不可用，正在重试第 :{} 次", i);
                if (isConnected() && sendPacket(packet)) {
                    return true;
                }
            }
            logger.warn("连接不可用，发送失败！");
        }
        return false;
    }

    public MessagePacket readPacket() {
        return receivePacketQueue.poll();
    }

    public List<MessagePacket> readAllPackets() {
        if (!receivePacketQueue.isEmpty()) {
            List<MessagePacket> packets = new ArrayList<>();
            receivePacketQueue.drainTo(packets); // 一次性取出所有内容
            return packets;
        }
        return new ArrayList<>();
    }

    private synchronized boolean sendPacket(MessagePacket packet) {
        if (packet.getSid() == 0) {
            packet.setSid(sid);
        }
        // if (LoggerDef.NetLogger.isDebugEnabled()) {
        // LoggerDef.NetLogger.debug(String.format("sid:%d send packet:%s", sid,
        // packet));
        // }
        channel.writeAndFlush(packet);
        return true;
    }

    public void addReceivePacket(MessagePacket packet) {
        if (packet.getCmd() == MessagePacket.CMD_ACK) {
            setSid(packet.getSid());
        } else {
            receivePacketQueue.add(packet);
        }
    }

    public Channel getChannel() {
        return channel;
    }

    public int getReceivePacketQueueSize() {
        return receivePacketQueue.size();
    }

    /**
     * 从接收队列中查找指定 callId 和外层 cmd 的 RPC 响应包。
     *
     * <p>Gate2Game 专用 RPC 和通用 Server2Server RPC 都必须使用 callId 匹配，
     * 不能再依赖 TCP 连接上的 seq。seq 只保留给链路日志和客户端包顺序排查。
     */
    public MessagePacket getReceiveMsgByCallId(long callId, int cmd) {
        Iterator<MessagePacket> iterator = receivePacketQueue.iterator();
        while (iterator.hasNext()) {
            MessagePacket packet = iterator.next();
            if (packet.getCmd() == cmd) {
                try {
                    com.google.protobuf.AbstractMessage msg = ly.ProtoMessageFactory.createProtoMessage(cmd,
                            packet.getData());
                    if (matchRpcCallId(msg, callId)) {
                        iterator.remove();
                        return packet;
                    }
                } catch (Exception ignored) {
                    // 解析失败时跳过该包，让其他读取逻辑继续处理。
                }
            }
        }
        return null;
    }

    private boolean matchRpcCallId(com.google.protobuf.AbstractMessage msg, long callId) {
        if (msg instanceof ly.proto.Server.scGate2GameRpcGameCall resp) {
            return resp.getCallId() == callId;
        }
        if (msg instanceof ly.proto.Server.scServer2Server resp) {
            return resp.getCallId() == callId;
        }
        return false;
    }

    /**
     * 将误取出的包放回接收队列头部。
     * <p>
     * 当 getReceiveMsgByCallId 取出包后发现 callId 不匹配时，调用此方法放回。
     */
    public void putBackPacket(MessagePacket packet) {
        if (packet != null) {
            receivePacketQueue.add(packet);
        }
    }

    @Override
    public String toString() {
        return "NetClient{"
                + "host='"
                + host
                + '\''
                + ", port="
                + port
                + ", sid="
                + sid
                + ", sendSeq="
                + sendSeq
                + ", channelId="
                + (channel == null ? "null" : channel.id().asLongText())
                + '}';
    }

    public boolean isReady() {
        return isConnected() && sid != 0;
    }

    public int getSid() {
        return sid;
    }

}
