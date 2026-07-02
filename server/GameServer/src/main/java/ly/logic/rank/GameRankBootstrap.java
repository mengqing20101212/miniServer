package ly.logic.rank;

import ly.LoggerDef;
import ly.ServerContext;
import ly.utils.rank.RankService;

/** GameServer 排行榜注册和 tick 调度入口。 */
public final class GameRankBootstrap {
  private static volatile boolean started;

  private GameRankBootstrap() {}

  public static void start() {
    if (started) {
      return;
    }
    started = true;

    PowerRank powerRank = new PowerRank(ServerContext.getServerId());
    RankService.register(powerRank);
    LoggerDef.SystemLogger.info("注册战力榜成功, name={}, key={}", powerRank.getName(), powerRank.getRankKey());

    // 第一版先用独立虚拟线程驱动 RankService；后续如果有统一服务器心跳，可以改为接入主 tick。
    Thread.ofVirtual()
        .name("RankService-Tick")
        .start(
            () -> {
              while (!Thread.currentThread().isInterrupted()) {
                try {
                  RankService.tick();
                  Thread.sleep(100L);
                } catch (InterruptedException e) {
                  Thread.currentThread().interrupt();
                } catch (Exception e) {
                  LoggerDef.SystemLogger.error("RankService tick failed", e);
                }
              }
            });
  }
}
