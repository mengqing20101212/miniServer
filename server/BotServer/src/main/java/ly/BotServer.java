package ly;

import ly.bot.RobotManager;
import ly.bot.util.ProtocolTester;

/**
 * BotServer - 机器人压测服务器
 * 用于创建大量机器人连接到游戏服务器进行压力测试
 * 
 * 机器人登录流程：
 * 1. 连接LoginServer进行登录，获取服务器列表和账户信息
 * 2. 根据返回的GateServer信息，连接GateServer
 * 3. 通过TCP协议发送登录包到GateServer
 * 4. 登录成功后设置状态，执行各种游戏行为
 * 
 * Author: OpenClaw AI Assistant
 * Date: 2026/2/5
 * File: BotServer
 */
public class BotServer {
    
    public static void main(String[] args) {
        System.out.println("BotServer - 机器人压测服务器启动");
        System.out.println("支持完整的登录流程：LoginServer -> GateServer");
        
        if (args.length < 1) {
            System.out.println("使用方法: java -jar BotServer.jar [options]");
            System.out.println("选项:");
            System.out.println("  --test-protocol     测试协议封包功能");
            System.out.println("  --run-bots <host> <port> <num>  运行机器人压测");
            System.out.println("例如:");
            System.out.println("  java -jar BotServer.jar --test-protocol");
            System.out.println("  java -jar BotServer.jar --run-bots localhost 8080 100");
            return;
        }
        
        String command = args[0];
        
        switch (command) {
            case "--test-protocol":
                // 测试协议封包功能
                ProtocolTester.testLoginProtocol();
                ProtocolTester.demonstrateCorrectUsage();
                break;
                
            case "--run-bots":
                if (args.length < 4) {
                    System.out.println("错误: --run-bots 需要提供 <host> <port> <num> 参数");
                    return;
                }
                
                String loginServerHost = args[1];
                int loginServerPort = Integer.parseInt(args[2]);
                int numBots = Integer.parseInt(args[3]);
                
                System.out.println("准备创建 " + numBots + " 个机器人，通过LoginServer " + 
                                  loginServerHost + ":" + loginServerPort + " 进行登录");
                
                try {
                    // 创建机器人管理器
                    RobotManager robotManager = new RobotManager(loginServerHost, loginServerPort, numBots);
                    
                    // 启动机器人
                    robotManager.start();
                    
                    // 启动一个监控虚拟线程，定期输出统计信息
                    Thread.ofVirtual().name("RobotMonitorThread").start(() -> {
                        while (!Thread.currentThread().isInterrupted()) {
                            try {
                                RobotManager.RobotStats stats = robotManager.getStats();
                                System.out.println("[统计信息] " + stats.toString());
                                
                                // 每隔10秒输出一次统计信息
                                Thread.sleep(10000);
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                                break;
                            } catch (Exception e) {
                                System.err.println("监控虚拟线程出错: " + e.getMessage());
                            }
                        }
                    });
                    
                    // 保持主线程运行
                    Thread.currentThread().join();
                    
                } catch (Exception e) {
                    System.err.println("启动机器人过程中发生错误:");
                    e.printStackTrace();
                }
                break;
                
            default:
                System.out.println("未知命令: " + command);
                System.out.println("使用 --help 查看帮助");
                break;
        }
    }
}