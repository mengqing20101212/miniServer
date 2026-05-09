package ly;

import ly.bot.RobotManager;
import ly.bot.module.impl.RpcSeqSidTestModule;
import ly.bot.util.ProtocolTester;
import ly.startup.StartupSkillLoader;

/**
 * BotServer - 机器人压测服务器
 */
public class BotServer {

    public static void main(String[] args) {
        System.out.println("BotServer - 机器人压测服务器启动");
        System.out.println("支持完整的登录流程：LoginServer -> GateServer");

        if (args != null && args.length > 0 && "--test-rpc-seq-sid".equals(args[0])) {
            String loginHost = args.length >= 2 ? args[1] : "127.0.0.1";
            int loginHttpPort = args.length >= 3 ? Integer.parseInt(args[2]) : 8889;
            boolean success = RpcSeqSidTestModule.runStandalone(loginHost, loginHttpPort);
            System.exit(success ? 0 : 1);
        }

        StartupSkillLoader.ResolvedBotArgs resolved = StartupSkillLoader.resolveBotArgs(args);
        String command = resolved.command;

        switch (command) {
            case "--test-protocol":
                ProtocolTester.testLoginProtocol();
                ProtocolTester.demonstrateCorrectUsage();
                break;

            case "--run-bots":
                String loginServerHost = resolved.loginHost;
                int loginServerPort = resolved.loginHttpPort;
                int numBots = resolved.numBots;

                System.out.println("准备创建 " + numBots + " 个机器人，通过LoginServer "
                        + loginServerHost + ":" + loginServerPort + " 进行登录");

                try {
                    RobotManager robotManager = new RobotManager(loginServerHost, loginServerPort, numBots);
                    robotManager.start();

                    Thread.ofVirtual().name("RobotMonitorThread").start(() -> {
                        while (!Thread.currentThread().isInterrupted()) {
                            try {
                                RobotManager.RobotStats stats = robotManager.getStats();
                                System.out.println("[统计信息] " + stats);
                                Thread.sleep(10000);
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                                break;
                            } catch (Exception e) {
                                System.err.println("监控线程出错: " + e.getMessage());
                            }
                        }
                    });

                    Thread.currentThread().join();
                } catch (Exception e) {
                    System.err.println("启动机器人过程中发生错误:");
                    e.printStackTrace();
                }
                break;

            default:
                System.out.println("未知命令: " + command);
                System.out.println("可用命令: --test-protocol, --run-bots");
                break;
        }
    }
}
