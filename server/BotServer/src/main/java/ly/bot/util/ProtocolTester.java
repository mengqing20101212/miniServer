package ly.bot.util;

import ly.net.NetClient;
import ly.net.NetService;
import ly.net.packet.MessagePacket;
import ly.net.packet.MessagePacketFactory;
import ly.proto.Cmd;
import ly.proto.Login;
import org.slf4j.Logger;
import ly.LoggerDef;

/**
 * 协议封包测试工具类
 * 用于验证NetClient协议封包是否正确
 */
public class ProtocolTester {
    private static final Logger logger = LoggerDef.SystemLogger;

    /**
     * 测试登录协议封包
     */
    public static void testLoginProtocol() {
        logger.info("开始测试登录协议封包...");
        
        try {
            // 创建登录请求消息
            Login.csLogin.Builder loginBuilder = Login.csLogin.newBuilder();
            loginBuilder.setAccount("test_account");
            loginBuilder.setAccountId(123456L);
            loginBuilder.setChannel("test_channel");
            loginBuilder.setToken("test_token");
            loginBuilder.setDeviceId("test_device");
            loginBuilder.setIsReconnect(false);
            
            Login.csLogin loginRequest = loginBuilder.build();
            
            // 测试创建 MessagePacket
            int seq = 1; // 序列号
            int sid = 100; // 会话ID
            long guid = 123456L; // 全局唯一ID
            
            MessagePacket packet = MessagePacketFactory.createMessagePacket(
                guid, // guid
                Cmd.CMD.CS_Login_VALUE, // 登录命令
                loginRequest, // protobuf数据
                seq, // 序列号
                sid // sid
            );
            
            System.out.println("协议封包测试结果:");
            System.out.println("- 命令: " + packet.getCmd());
            System.out.println("- SID: " + packet.getSid());
            System.out.println("- 序列号: " + packet.getSeq());
            System.out.println("- 数据长度: " + (packet.getData() != null ? packet.getData().length : 0));
            // MessagePacket 保留 guid 字段，Bot 可以用它校验玩家或账号标识。
            System.out.println("- GUID: " + packet.getGuid());
            
            logger.info("登录协议封包测试完成");
        } catch (Exception e) {
            logger.error("协议封包测试失败", e);
        }
    }
    
    /**
     * 演示如何正确使用NetClient发送协议包
     */
    public static void demonstrateCorrectUsage() {
        System.out.println("\n=== NetClient协议封包使用演示 ===");
        System.out.println("1. 创建NetClient实例");
        System.out.println("2. 连接到服务器");
        System.out.println("3. 使用MessagePacketFactory创建协议包");
        System.out.println("4. 通过NetClient.send()方法发送协议包");
        System.out.println("5. 处理服务器响应");
        
        System.out.println("\n关键点:");
        System.out.println("- 使用MessagePacketFactory.createMessagePacket()创建客户端到服务器的消息包");
        System.out.println("- 正确设置GUID、CMD、序列号、SID和protobuf数据");
        System.out.println("- 使用NetClient.getSendSeq()获取递增的序列号");
        System.out.println("- 使用NetClient.getSid()获取会话ID");
        System.out.println("- 通过NetClient.send()发送消息包");
        System.out.println("- 通过NetClient.readPacket()读取响应");
    }
}
