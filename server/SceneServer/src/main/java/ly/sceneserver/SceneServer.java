package ly.sceneserver;

import ly.ServerContext;
import ly.config.ServerTypeEnum;
import ly.sceneserver.bootstrap.SceneBootstrap;
import ly.sceneserver.common.CommonSceneController;
import ly.sceneserver.net.SceneConnectSessionProvider;
import ly.sceneserver.net.SceneRpcDispatcher;
import ly.startup.StartupSkillLoader;

/**
 * 独立 SceneServer 进程入口。
 *
 * <p>网络、RPC、Nacos、日志和连接管理均来自 core；本工程只组装场景运行时和场景 Handler。
 */
public final class SceneServer {
    private SceneServer() {
    }

    public static void main(String[] args) throws InterruptedException {
        StartupSkillLoader.ResolvedServerArgs resolved =
                StartupSkillLoader.resolveServerArgs(ServerTypeEnum.SCENE, args);
        ServerContext.addController(new CommonSceneController());
        ServerContext.startUp(
                resolved.nacosUrl,
                ServerTypeEnum.SCENE.getType(),
                resolved.serverId,
                resolved.env,
                new SceneConnectSessionProvider());

        // 在创建 SceneRuntime 前应用 STARTUP.SKILL.md 中的 Tick、寻路、迁移和落库基线。
        StartupSkillLoader.applySceneRuntimeProperties();
        SceneBootstrap.start();
        SceneRpcDispatcher dispatcher = new SceneRpcDispatcher();
        dispatcher.start();
        Runtime.getRuntime().addShutdownHook(Thread.ofPlatform().unstarted(() -> {
            dispatcher.close();
            SceneBootstrap.stop();
        }));
        Thread.currentThread().join();
    }
}
