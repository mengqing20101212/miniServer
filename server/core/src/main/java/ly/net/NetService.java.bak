package ly.net;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.AttributeKey;
import ly.LoggerDef;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/*
 * Author: liuYang
 * Date: 2025/4/8
 * File: NetServcie
 */
public class NetService {
    protected static final AttributeKey<Boolean> SELF_CLOSED = AttributeKey.valueOf("selfClosed");

    /**
     * tcp 收包 缓冲池大小 32K*
     */
    static final int SO_RCVBUF = 1024 * 32;

    static final Logger log = LoggerDef.SystemLogger;
    GameObjectProvider gameObjectProvider;
    static final int SO_SNDBUF = 1024 * 32;
    private static final NetService INSTANCE = new NetService();
    static EventLoopGroup boss = new NioEventLoopGroup(1);
    static EventLoopGroup worker = new NioEventLoopGroup(Runtime.getRuntime().availableProcessors());
    List<NetServer> servers = new ArrayList<NetServer>();
    Map<Long, ConnectSession> gameObjectMaps = new ConcurrentHashMap<>();
    Map<ChannelHandlerContext, ConnectSession> gameObjectContextMaps = new ConcurrentHashMap<>();
    AtomicInteger sidCreator = new AtomicInteger();

    private NetService() {
    }

    public static NetService getInstance() {
        return INSTANCE;
    }

    public int createSid() {
        return sidCreator.incrementAndGet();
    }

    public void startUp(GameObjectProvider gameObjectProvider, int... ports) {
        if (ports.length == 0) {
            throw new IllegalArgumentException("No ports provided");
        }
        this.gameObjectProvider = gameObjectProvider;

        // 优化NetService发送任务，使用自适应间隔和批量处理
        Thread.ofVirtual()
                .name("send-packet-task")
                .start(
                        () -> {
                            while (!Thread.currentThread().isInterrupted()) {
                                try {
                                    // 自适应间隔：根据队列大小调整发送频率
                                    int queueSize = gameObjectMaps.values().stream()
                                            .mapToInt(session -> session.sendPacketQueue.size())
                                            .sum();
                                    
                                    // 更智能的发送间隔计算，避免频繁创建Stream
                                    long sleepTime;
                                    if (queueSize > 1000) {
                                        sleepTime = 1; // 高负载时更频繁发送
                                    } else if (queueSize > 100) {
                                        sleepTime = 5;
                                    } else {
                                        sleepTime = 10; // 低负载时降低频率
                                    }
                                    
                                    Thread.sleep(sleepTime);

                                    // 批量处理，避免重复迭代
                                    gameObjectMaps.values().forEach(session -> {
                                        try {
                                            session.sendAllPackets();
                                        } catch (Exception e) {
                                            log.error("Error sending packets for session {}", session.getGuid(), e);
                                        }
                                    });
                                } catch (InterruptedException e) {
                                    Thread.currentThread().interrupt();
                                    break;
                                } catch (Exception e) {
                                    log.error("Unexpected error in send-packet-task", e);
                                }
                            }
                        });

        for (final int port : ports) {
            NetServer netServer = new NetServer(port, "NetServer-" + port);
            servers.add(netServer);
            netServer.startUp(boss, worker);
        }
    }

    public void delChannel(ChannelHandlerContext ctx) {
        if (ctx == null || ctx.channel() == null) {
            return;
        }
        
        String channelId = ctx.channel().id().asLongText();
        String remoteAddress = null;
        
        try {
            if (ctx.channel().remoteAddress() != null) {
                remoteAddress = ctx.channel().remoteAddress().toString();
            }
            
            ConnectSession object = gameObjectContextMaps.remove(ctx);
            if (object != null) {
                gameObjectMaps.remove(object.getGuid());
                try {
                    object.closeChannel();
                } catch (Exception e) {
                    log.warn("Error closing channel for session {}", object.getGuid(), e);
                }
            }
            
            // 使用参数化日志避免字符串拼接
            log.info("关闭远端连接 sid:{}, address:{}", channelId, remoteAddress);
        } catch (Exception e) {
            log.error("Error removing channel: {}", channelId, e);
        }
    }

    public ConnectSession addChannel(ChannelHandlerContext ctx) {
        if (ctx == null) {
            log.error("Failed to add channel: ChannelHandlerContext is null");
            return null;
        }
        
        if (gameObjectProvider == null) {
            log.error("Failed to add channel: GameObjectProvider is null");
            return null;
        }
        
        try {
            ConnectSession object = gameObjectProvider.createGameObject(ctx);
            if (object == null) {
                log.error("Failed to add channel: GameObjectProvider returned null");
                return null;
            }
            
            Connector connector = new Connector(ctx, createSid());
            object.setConnector(connector);
            
            // 使用computeIfAbsent避免重复检查
            Long guid = object.getGuid();
            if (guid == null) {
                log.error("Failed to add channel: ConnectSession GUID is null");
                return null;
            }
            
            ConnectSession existingObject = gameObjectMaps.computeIfAbsent(guid, k -> object);
            if (existingObject != object) {
                log.warn("Duplicate session GUID detected: {}", guid);
                return existingObject;
            }
            
            gameObjectContextMaps.put(ctx, object);
            return object;
        } catch (Exception e) {
            log.error("Failed to add channel", e);
            return null;
        }
    }

    public ConnectSession getGameObject(ChannelHandlerContext channelHandlerContext) {
        return gameObjectContextMaps.get(channelHandlerContext);
    }

    public Map<Long, ConnectSession> getGameObjectMaps() {
        return gameObjectMaps;
    }
}
