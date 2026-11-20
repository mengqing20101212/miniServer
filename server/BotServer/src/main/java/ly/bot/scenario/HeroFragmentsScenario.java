package ly.bot.scenario;

import com.google.protobuf.AbstractMessage;
import java.util.List;
import java.util.concurrent.TimeUnit;
import ly.bot.action.RobotActionContext;
import ly.bot.action.impl.SceneRobotAction;
import ly.bot.session.RobotSession;
import ly.proto.Cmd;
import ly.proto.ErrorMsg;
import ly.proto.Hero;
import ly.proto.Resource;

/** 使用新账号。测试奖励由服务端通过 Award 发放，客户端只请求解锁、列表和余额。 */
public final class HeroFragmentsScenario {
    private final RobotSession session;

    public HeroFragmentsScenario(String host, int port, String game, String account) {
        session = new RobotSession(1, host, port, account, game, ignored -> {});
    }

    public void run() throws Exception {
        try {
            session.startLoginProcess();
            session.loginFuture().get(120, TimeUnit.SECONDS);
            require(list().isEmpty(), "需要没有英雄的新账号");
            require(unlock(3).getResult() == ErrorMsg.ErrorCode.RESOURCE_NOT_ENOUGH, "碎片不足仍可解锁");
            require(list().isEmpty(), "解锁失败后生成了英雄");

            System.out.println("[HERO-FRAGMENTS] READY_BATCH " + session.getPlayerId());
            awaitBalance(3110002, 200);
            List<Hero.HeroInfo> owned = list();
            require(owned.size() == 1 && owned.getFirst().getHeroId() == 2, "三张相同整卡应只创建一个实例");
            long firstUid = owned.getFirst().getHeroUid();
            require(balance(3110003) == 13, "解锁材料未到账");
            Hero.SC_HeroUnlock unlocked = unlock(3);
            require(unlocked.getResult() == ErrorMsg.ErrorCode.Ok && unlocked.getHeroInfo().getHeroId() == 3
                    && balance(3110003) == 3, "解锁未正确扣除10个专属碎片");
            long unlockedUid = unlocked.getHeroInfo().getHeroUid();
            Hero.SC_HeroUnlock repeated = unlock(3);
            require(repeated.getResult() == ErrorMsg.ErrorCode.Ok && repeated.getHeroInfo().getHeroUid() == unlockedUid
                    && balance(3110003) == 3, "重复解锁改变了实例或余额");

            System.out.println("[HERO-FRAGMENTS] READY_OWNED " + session.getPlayerId());
            awaitBalance(3110002, 300);
            List<Hero.HeroInfo> result = list();
            require(result.size() == 2 && result.stream().anyMatch(h -> h.getHeroUid() == firstUid)
                    && result.stream().anyMatch(h -> h.getHeroUid() == unlockedUid), "重复整卡改变了已拥有英雄");
            System.out.printf("[HERO-FRAGMENTS] PASS player=%d hero2=%d shards=200->300 hero3=%d shards=13->3->3%n",
                    session.getPlayerId(), firstUid, unlockedUid);
        } finally {
            session.shutdown();
        }
    }

    private Hero.SC_HeroUnlock unlock(int id) throws Exception {
        return request(Cmd.CMD.CS_HeroUnlock, Cmd.CMD.SC_HeroUnlock,
                Hero.CS_HeroUnlock.newBuilder().setHeroId(id).build(), Hero.SC_HeroUnlock.class);
    }

    private List<Hero.HeroInfo> list() throws Exception {
        return request(Cmd.CMD.CS_HeroList, Cmd.CMD.SC_HeroList, Hero.CS_HeroList.getDefaultInstance(),
                Hero.SC_HeroList.class).getHeroListList();
    }

    private long balance(int id) throws Exception {
        return request(Cmd.CMD.CS_ResourceQuery, Cmd.CMD.SC_ResourceQuery,
                Resource.CS_ResourceQuery.newBuilder().addResourceTypes(id).build(), Resource.SC_ResourceQuery.class)
                .getResourcesOrDefault(id, 0);
    }

    private void awaitBalance(int id, long expected) throws Exception {
        long deadline = System.nanoTime() + TimeUnit.MINUTES.toNanos(2);
        while (balance(id) != expected) {
            require(System.nanoTime() < deadline, "等待服务端奖励超时，碎片 " + id + " 预期 " + expected);
            Thread.sleep(250);
        }
    }

    private <T extends AbstractMessage> T request(Cmd.CMD cmd, Cmd.CMD reply, AbstractMessage body, Class<T> type)
            throws Exception {
        SceneRobotAction<T> action = new SceneRobotAction<>(cmd, reply, body, type, cmd.name());
        require(action.execute(new RobotActionContext(null, session)).isSuccess(), "发包失败 " + cmd);
        return action.responseFuture().get(15, TimeUnit.SECONDS);
    }

    private static void require(boolean result, String message) {
        if (!result) throw new IllegalStateException(message);
    }
}
