package ly;

import ly.bot.RobotManager;

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
        
        if (args.length < 3) {
            System.out.println("使用方法: java -jar BotServer.jar <login_server_host> <login_server_port> <num_bots>");
            System.out.println("例如: java -jar BotServer.jar localhost 8080 100");
            return;
        }
        
        String loginServerHost = args[0];
        int loginServerPort = Integer.parseInt(args[1]);
        int numBots = Integer.parseInt(args[2]);
        
        System.out.println("准备创建 " + numBots + " 个机器人，通过LoginServer " + 
                          loginServerHost + ":" + loginServerPort + " 进行登录");
        
        try {
            // 创建机器人管理器
            RobotManager robotManager = new RobotManager(loginServerHost, loginServerPort, numBots);
            
            // 启动机器人
            robotManager.start();
            
            // 启动一个监控线程，定期输出统计信息
            Thread monitorThread = new Thread(() -> {
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
                        System.err.println("监控线程出错: " + e.getMessage());
                    }
                }
            });
            
            monitorThread.setName("RobotMonitorThread");
            monitorThread.start();
            
            // 保持主线程运行
            Thread.currentThread().join();
            
        } catch (Exception e) {
            System.err.println("启动机器人过程中发生错误:");
            e.printStackTrace();
        }
    }
}