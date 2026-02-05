package ly;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import ly.proto.Cmd;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * BotServer - 机器人压测服务器
 * 用于创建大量机器人连接到游戏服务器进行压力测试
 * 
 * Author: OpenClaw AI Assistant
 * Date: 2026/2/5
 * File: BotServer
 */
public class BotServer {
    
    public static final AtomicInteger connectionCounter = new AtomicInteger(0);
    
    public static void main(String[] args) {
        System.out.println("BotServer - 机器人压测服务器启动");
        
        if (args.length < 3) {
            System.out.println("使用方法: java -jar BotServer.jar <server_host> <server_port> <num_bots>");
            System.out.println("例如: java -jar BotServer.jar localhost 8080 100");
            return;
        }
        
        String serverHost = args[0];
        int serverPort = Integer.parseInt(args[1]);
        int numBots = Integer.parseInt(args[2]);
        
        System.out.println("准备创建 " + numBots + " 个机器人连接到 " + serverHost + ":" + serverPort);
        
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workerGroup);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(1024, 0, 4, 0, 4));
                    ch.pipeline().addLast(new LengthFieldPrepender(4));
                    ch.pipeline().addLast(new BotClientHandler());
                }
            });

            // 创建多个机器人连接
            for (int i = 0; i < numBots; i++) {
                final int botId = i + 1;
                try {
                    ChannelFuture future = bootstrap.connect(serverHost, serverPort);
                    future.addListener((ChannelFutureListener) future1 -> {
                        if (future1.isSuccess()) {
                            System.out.println("机器人 #" + botId + " 连接成功");
                            int currentConnections = connectionCounter.incrementAndGet();
                            System.out.println("当前连接数: " + currentConnections);
                            
                            // 发送登录请求
                            sendLoginRequest(future1.channel(), "bot_user_" + botId, "bot_token_" + botId);
                        } else {
                            System.out.println("机器人 #" + botId + " 连接失败: " + future1.cause());
                        }
                    });
                    future.sync(); // 等待连接完成
                    
                    // 控制连接速度，避免瞬间建立过多连接
                    Thread.sleep(100);
                } catch (Exception e) {
                    System.err.println("创建机器人 #" + botId + " 时出错: " + e.getMessage());
                }
            }
            
            System.out.println("所有 " + numBots + " 个机器人已尝试连接");
            
            // 保持主线程运行
            Thread.currentThread().join();
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }
    
    /**
     * 发送登录请求
     */
    private static void sendLoginRequest(Channel channel, String username, String token) {
        // 创建登录请求数据
        String loginData = "{\"username\":\"" + username + "\",\"token\":\"" + token + "\"}";
        byte[] data = loginData.getBytes();
        
        // 构造消息: 命令ID + 数据长度 + 数据
        ByteBuf buffer = Unpooled.buffer();
        buffer.writeInt(Cmd.CMD.CS_Login_VALUE); // 登录命令
        buffer.writeInt(data.length); // 数据长度
        buffer.writeBytes(data); // 数据
        
        channel.writeAndFlush(buffer);
    }
}

/**
 * 机器人客户端处理器
 */
class BotClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
    
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端通道激活: " + ctx.channel().remoteAddress());
        super.channelActive(ctx);
    }
    
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        // 读取消息长度
        int cmd = msg.readInt();
        int len = msg.readInt();
        
        // 读取数据
        byte[] data = new byte[len];
        msg.readBytes(data);
        String response = new String(data);
        
        System.out.println("收到服务器响应 [CMD: " + cmd + "]: " + response);
        
        // 根据命令类型进行相应处理
        handleServerResponse(ctx, cmd, response);
    }
    
    /**
     * 处理服务器响应
     */
    private void handleServerResponse(ChannelHandlerContext ctx, int cmd, String response) {
        switch (cmd) {
            case 1001: // 假设这是登录响应命令
                System.out.println("登录响应: " + response);
                // 登录成功后可以发送其他指令
                scheduleRandomActions(ctx);
                break;
            case 2001: // 心跳响应
                System.out.println("心跳响应: " + response);
                break;
            default:
                System.out.println("未知命令响应 [" + cmd + "]: " + response);
        }
    }
    
    /**
     * 定期发送随机动作
     */
    private void scheduleRandomActions(ChannelHandlerContext ctx) {
        // 使用定时任务模拟机器人行为
        ctx.executor().schedule(() -> {
            // 发送一些随机的游戏行为
            sendRandomGameAction(ctx);
        }, 2 + (long)(Math.random() * 5), java.util.concurrent.TimeUnit.SECONDS);
    }
    
    /**
     * 发送随机游戏行为
     */
    private void sendRandomGameAction(ChannelHandlerContext ctx) {
        // 模拟一些游戏行为，如移动、攻击等
        int[] actions = {1002, 1003, 1004}; // 假设这些是不同的游戏命令
        int randomAction = actions[(int)(Math.random() * actions.length)];
        
        String actionData = "{\"action\":\"" + randomAction + "\",\"timestamp\":" + System.currentTimeMillis() + "}";
        byte[] data = actionData.getBytes();
        
        ByteBuf buffer = Unpooled.buffer();
        buffer.writeInt(randomAction);
        buffer.writeInt(data.length);
        buffer.writeBytes(data);
        
        ctx.writeAndFlush(buffer);
        
        // 计划下一次行动
        scheduleRandomActions(ctx);
    }
    
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.err.println("客户端发生异常: " + cause.getMessage());
        cause.printStackTrace();
        ctx.close();
    }
    
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端连接断开: " + ctx.channel().remoteAddress());
        BotServer.connectionCounter.decrementAndGet();
        super.channelInactive(ctx);
    }
}