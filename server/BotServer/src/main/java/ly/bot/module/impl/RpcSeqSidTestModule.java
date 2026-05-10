package ly.bot.module.impl;

import ly.ProtoMessageFactory;
import ly.bot.http.HttpServerListClient;
import ly.bot.module.RobotModule;
import ly.bot.session.RobotSession;
import ly.net.NetClient;
import ly.net.NetService;
import ly.net.packet.AbstractMessagePacket;
import ly.net.packet.MessagePacketFactory;
import ly.proto.Cmd;
import ly.proto.Hero;
import ly.proto.Login;

/**
 * RPC seq/sid 专项测试模块。
 *
 * <p>该模块覆盖 Bot -> Gate -> Game -> Gate -> Bot 的真实链路，断言登录包和普通业务包的
 * cmd/seq/sid 是否符合协议约定。
 */
public class RpcSeqSidTestModule implements RobotModule {
    private static final long RESPONSE_TIMEOUT_MS = 15_000;
    private static final long POLL_INTERVAL_MS = 20;

    private boolean completed;
    private boolean success;

    public static boolean runStandalone(String loginHost, int loginHttpPort) {
        RpcSeqSidTestModule module = new RpcSeqSidTestModule();
        return module.runFullTest(loginHost, loginHttpPort, 0, RESPONSE_TIMEOUT_MS, false);
    }

    public static boolean runReliableReplayStandalone(
            String loginHost, int loginHttpPort, long delayBeforeHeroMs, long responseTimeoutMs) {
        return runReliableReplayStandalone(
                loginHost, loginHttpPort, delayBeforeHeroMs, responseTimeoutMs, null);
    }

    public static boolean runReliableReplayStandalone(
            String loginHost,
            int loginHttpPort,
            long delayBeforeHeroMs,
            long responseTimeoutMs,
            String account) {
        RpcSeqSidTestModule module = new RpcSeqSidTestModule();
        return module.runFullTest(loginHost, loginHttpPort, delayBeforeHeroMs, responseTimeoutMs, true, account);
    }

    @Override
    public boolean executeStep(NetClient client, RobotSession session) {
        if (completed) {
            return true;
        }
        try {
            if (client == null || !client.isReady()) {
                return failStep("Gate 连接未就绪，无法执行 RPC seq/sid 测试");
            }
            int sid = client.getSid();
            int heroSeq = client.getSendSeq();
            Hero.CS_HeroList heroReq = Hero.CS_HeroList.newBuilder().build();
            long playerId = session.getPlayerInfo() != null ? session.getPlayerInfo().getPlayerId() : 0;
            AbstractMessagePacket heroPacket =
                    MessagePacketFactory.createAbstractMessagePacket(
                            playerId, Cmd.CMD.CS_HeroList_VALUE, heroReq, heroSeq, sid);
            if (!client.send(heroPacket)) {
                return failStep("发送 CS_HeroList 失败");
            }
            AbstractMessagePacket heroResp =
                    waitForResponse(client, Cmd.CMD.SC_HeroList_VALUE, heroSeq + 1, sid);
            assertPacket(heroResp, Cmd.CMD.SC_HeroList_VALUE, heroSeq + 1, sid, "英雄列表响应");
            Hero.SC_HeroList scHeroList =
                    (Hero.SC_HeroList)
                            ProtoMessageFactory.createProtoMessage(
                                    Cmd.CMD.SC_HeroList_VALUE, heroResp.getData());
            if (scHeroList == null) {
                return failStep("SC_HeroList 数据解析失败");
            }
            System.out.printf("[RPC-SEQ-SID] PASS module heroList cmd=%d seq=%d sid=%d heroCount=%d%n",
                    heroResp.getCmd(), heroResp.getSeq(), heroResp.getSid(), scHeroList.getHeroListCount());
            success = true;
            completed = true;
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return failStep("模块执行异常: " + e.getMessage());
        }
    }

    @Override
    public void reset() {
        completed = false;
        success = false;
    }

    @Override
    public boolean isCompleted() {
        return completed;
    }

    @Override
    public String getName() {
        return "RpcSeqSidTestModule";
    }

    public boolean isSuccess() {
        return success;
    }

    private boolean runFullTest(
            String loginHost,
            int loginHttpPort,
            long delayBeforeHeroMs,
            long responseTimeoutMs,
            boolean reliableReplayMode) {
        return runFullTest(
                loginHost, loginHttpPort, delayBeforeHeroMs, responseTimeoutMs, reliableReplayMode, null);
    }

    private boolean runFullTest(
            String loginHost,
            int loginHttpPort,
            long delayBeforeHeroMs,
            long responseTimeoutMs,
            boolean reliableReplayMode,
            String accountOverride) {
        String account =
                accountOverride != null && !accountOverride.isBlank()
                        ? accountOverride
                        : "bot_rpc_seq_sid_" + System.currentTimeMillis();
        NetClient gateClient = null;
        try {
            HttpServerListClient httpClient = new HttpServerListClient(loginHost, loginHttpPort);
            HttpServerListClient.ServerListResult serverList = ensureAccountAndServerList(httpClient, account);
            if (serverList == null || serverList.getGate() == null) {
                return fail("未获取到 GateServer 信息");
            }
            if (serverList.getAccountId() <= 0 || serverList.getToken() == null || serverList.getToken().isBlank()) {
                return fail("未获取到有效账号 token，无法继续登录 GateServer");
            }
            String gameServerId = serverList.getFirstGameServerId();
            if (gameServerId == null || gameServerId.isBlank()) {
                return fail("未获取到可用 GameServer");
            }

            HttpServerListClient.ServerNode gate = serverList.getGate();
            gateClient = new NetClient(gate.getServerIp(), gate.getServerPort(), false);
            gateClient.start(NetService.getInstance().getWorkerGroup());
            if (!waitReady(gateClient)) {
                return fail("连接 GateServer 超时");
            }

            int sid = gateClient.getSid();
            long accountId = serverList.getAccountId();
            String token = serverList.getToken();
            System.out.printf("[RPC-SEQ-SID] Gate connected sid=%d, accountId=%d, gameServerId=%s%n",
                    sid, accountId, gameServerId);

            int loginSeq = gateClient.getSendSeq();
            Login.csLogin loginReq =
                    Login.csLogin.newBuilder()
                            .setAccount(account)
                            .setAccountId(accountId)
                            .setPlayerId(0)
                            .setPlayerName("SeqSidBot")
                            .setChannel("bot")
                            .setGameServerId(gameServerId)
                            .setToken(token)
                            .setDeviceId("rpc-seq-sid-test")
                            .setIsReconnect(false)
                            .build();
            AbstractMessagePacket loginPacket =
                    MessagePacketFactory.createAbstractMessagePacket(
                            accountId, Cmd.CMD.CS_Login_VALUE, loginReq, loginSeq, sid);
            if (!gateClient.send(loginPacket)) {
                return fail("发送 CS_Login 失败");
            }
            AbstractMessagePacket loginResp =
                    waitForResponse(gateClient, Cmd.CMD.SC_Login_VALUE, loginSeq + 1, sid);
            assertPacket(loginResp, Cmd.CMD.SC_Login_VALUE, loginSeq + 1, sid, "登录响应");
            Login.scLogin scLogin =
                    (Login.scLogin)
                            ProtoMessageFactory.createProtoMessage(
                                    Cmd.CMD.SC_Login_VALUE, loginResp.getData());
            if (scLogin == null || scLogin.getPlayerId() <= 0) {
                return fail("SC_Login 数据解析失败或 playerId 无效");
            }
            System.out.printf("[RPC-SEQ-SID] PASS login cmd=%d seq=%d sid=%d playerId=%d%n",
                    loginResp.getCmd(), loginResp.getSeq(), loginResp.getSid(), scLogin.getPlayerId());
            if (delayBeforeHeroMs > 0) {
                // 可靠重放测试需要在登录后停掉 GameServer，再发送业务包触发 Gate 保存 outbox。
                System.out.printf(
                        "[RPC-SEQ-SID] WAIT before heroList %dms, stop GameServer now if testing reliable replay%n",
                        delayBeforeHeroMs);
                Thread.sleep(delayBeforeHeroMs);
            }

            int heroSeq = gateClient.getSendSeq();
            Hero.CS_HeroList heroReq = Hero.CS_HeroList.newBuilder().build();
            AbstractMessagePacket heroPacket =
                    MessagePacketFactory.createAbstractMessagePacket(
                            scLogin.getPlayerId(), Cmd.CMD.CS_HeroList_VALUE, heroReq, heroSeq, sid);
            if (!gateClient.send(heroPacket)) {
                return fail("发送 CS_HeroList 失败");
            }
            AbstractMessagePacket heroResp =
                    waitForResponse(
                            gateClient, Cmd.CMD.SC_HeroList_VALUE, heroSeq + 1, sid, responseTimeoutMs);
            assertPacket(heroResp, Cmd.CMD.SC_HeroList_VALUE, heroSeq + 1, sid, "英雄列表响应");
            Hero.SC_HeroList scHeroList =
                    (Hero.SC_HeroList)
                            ProtoMessageFactory.createProtoMessage(
                                    Cmd.CMD.SC_HeroList_VALUE, heroResp.getData());
            if (scHeroList == null) {
                return fail("SC_HeroList 数据解析失败");
            }
            System.out.printf("[RPC-SEQ-SID] PASS heroList cmd=%d seq=%d sid=%d heroCount=%d%n",
                    heroResp.getCmd(), heroResp.getSeq(), heroResp.getSid(), scHeroList.getHeroListCount());
            System.out.println(reliableReplayMode ? "[RPC-SEQ-SID] RELIABLE REPLAY PASS" : "[RPC-SEQ-SID] ALL PASS");
            success = true;
            completed = true;
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return fail("测试异常: " + e.getMessage());
        } finally {
            if (gateClient != null) {
                gateClient.stop();
            }
        }
    }

    private static HttpServerListClient.ServerListResult ensureAccountAndServerList(
            HttpServerListClient httpClient, String account) throws InterruptedException {
        HttpServerListClient.ServerListResult serverList = null;
        for (int attempt = 1; attempt <= 3; attempt++) {
            serverList = httpClient.getServerList(account);
            if (serverList != null && serverList.getAccountId() > 0 && serverList.getToken() != null) {
                return serverList;
            }
            // 远端 Redis/MySQL 偶发抖动时，注册可能短暂失败；这里重试避免测试入口直接 NPE。
            httpClient.register(account, "bot");
            Thread.sleep(500L * attempt);
        }
        return serverList;
    }

    private static boolean waitReady(NetClient client) throws InterruptedException {
        long deadline = System.currentTimeMillis() + RESPONSE_TIMEOUT_MS;
        while (System.currentTimeMillis() < deadline) {
            if (client.isReady()) {
                return true;
            }
            Thread.sleep(POLL_INTERVAL_MS);
        }
        return false;
    }

    private static AbstractMessagePacket waitForResponse(NetClient client, int cmd, int seq, int sid)
            throws InterruptedException {
        return waitForResponse(client, cmd, seq, sid, RESPONSE_TIMEOUT_MS);
    }

    private static AbstractMessagePacket waitForResponse(
            NetClient client, int cmd, int seq, int sid, long timeoutMs)
            throws InterruptedException {
        long deadline = System.currentTimeMillis() + timeoutMs;
        while (System.currentTimeMillis() < deadline) {
            AbstractMessagePacket packet = client.readPacket();
            if (packet != null) {
                if (packet.getCmd() == cmd && packet.getSeq() == seq && packet.getSid() == sid) {
                    return packet;
                }
                System.out.printf("[RPC-SEQ-SID] ignore packet cmd=%d seq=%d sid=%d%n",
                        packet.getCmd(), packet.getSeq(), packet.getSid());
            }
            Thread.sleep(POLL_INTERVAL_MS);
        }
        throw new IllegalStateException(
                String.format("等待响应超时 cmd=%d seq=%d sid=%d", cmd, seq, sid));
    }

    private static void assertPacket(AbstractMessagePacket packet, int cmd, int seq, int sid, String name) {
        if (packet == null) {
            throw new IllegalStateException(name + "为空");
        }
        if (packet.getCmd() != cmd || packet.getSeq() != seq || packet.getSid() != sid) {
            throw new IllegalStateException(
                    String.format(
                            "%s 校验失败，expect cmd=%d seq=%d sid=%d, actual cmd=%d seq=%d sid=%d",
                            name, cmd, seq, sid, packet.getCmd(), packet.getSeq(), packet.getSid()));
        }
    }

    private boolean failStep(String message) {
        completed = true;
        success = false;
        return fail(message);
    }

    private static boolean fail(String message) {
        System.err.println("[RPC-SEQ-SID] FAIL " + message);
        return false;
    }
}
