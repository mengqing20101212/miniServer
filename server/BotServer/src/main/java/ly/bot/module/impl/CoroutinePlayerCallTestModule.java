package ly.bot.module.impl;

import ly.ProtoMessageFactory;
import ly.bot.http.HttpServerListClient;
import ly.net.NetClient;
import ly.net.NetService;
import ly.net.packet.AbstractMessagePacket;
import ly.net.packet.MessagePacketFactory;
import ly.proto.Cmd;
import ly.proto.ErrorMsg;
import ly.proto.Login;
import ly.proto.Move;

/**
 * 玩家协程跨玩家调用专项测试。
 *
 * <p>测试链路：Bot A 登录 -> Bot B 登录 -> A 发送 Move 并携带 B 的 playerId ->
 * Game 在 A 的玩家队列里通过 CoroutineUtils.on(B) 读取 B 的等级 -> A 收到 Move 响应并校验。
 *
 * <p>客户端上行 seq 只用于日志排查，Gate 返回客户端的下行 seq 由 Gate 连接维度统一递增。
 */
public class CoroutinePlayerCallTestModule {
    private static final long RESPONSE_TIMEOUT_MS = 15_000L;
    private static final long POLL_INTERVAL_MS = 20L;

    public static boolean runStandalone(String loginHost, int loginHttpPort) {
        String suffix = String.valueOf(System.currentTimeMillis());
        return new CoroutinePlayerCallTestModule()
                .run(loginHost, loginHttpPort, "bot_coroutine_a_" + suffix, "bot_coroutine_b_" + suffix);
    }

    public boolean run(String loginHost, int loginHttpPort, String accountA, String accountB) {
        NetClient clientA = null;
        NetClient clientB = null;
        try {
            HttpServerListClient httpClient = new HttpServerListClient(loginHost, loginHttpPort);
            LoginContext contextA = login(httpClient, accountA, "CoroutineBotA");
            LoginContext contextB = login(httpClient, accountB, "CoroutineBotB");
            clientA = contextA.client();
            clientB = contextB.client();

            int moveSeq = clientA.getSendSeq();
            Move.csMove request =
                    Move.csMove.newBuilder()
                            .setTargetX(100)
                            .setTargetY(200)
                            .setObservePlayerId(contextB.playerId())
                            .build();
            AbstractMessagePacket packet =
                    MessagePacketFactory.createAbstractMessagePacket(
                            contextA.playerId(),
                            Cmd.CMD.CS_Move_VALUE,
                            request,
                            moveSeq,
                            clientA.getSid());
            if (!clientA.send(packet)) {
                return fail("A 发送 CS_Move 失败");
            }

            AbstractMessagePacket response =
                    waitForResponse(clientA, Cmd.CMD.SC_Move_VALUE, clientA.getSid());
            assertNextClientDownSeq(contextA.lastClientDownSeq(), response, "A Move响应");
            Move.scMove scMove =
                    (Move.scMove)
                            ProtoMessageFactory.createProtoMessage(
                                    Cmd.CMD.SC_Move_VALUE, response.getData());
            if (scMove == null) {
                return fail("SC_Move 解析失败");
            }
            if (scMove.getResult() != ErrorMsg.ErrorCode.Ok) {
                return fail("SC_Move result 非 Ok: " + scMove.getResult());
            }
            if (scMove.getObservedPlayerId() != contextB.playerId()) {
                return fail(
                        "observedPlayerId 错误，expect="
                                + contextB.playerId()
                                + ", actual="
                                + scMove.getObservedPlayerId());
            }
            if (scMove.getObservedLevel() <= 0) {
                return fail("observedLevel 非法: " + scMove.getObservedLevel());
            }
            System.out.printf(
                    "[COROUTINE-CALL] PASS playerA=%d playerB=%d observedLevel=%d seq=%d sid=%d%n",
                    contextA.playerId(),
                    contextB.playerId(),
                    scMove.getObservedLevel(),
                    response.getSeq(),
                    response.getSid());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return fail("测试异常: " + e.getMessage());
        } finally {
            if (clientA != null) {
                clientA.stop();
            }
            if (clientB != null) {
                clientB.stop();
            }
        }
    }

    private LoginContext login(HttpServerListClient httpClient, String account, String playerName)
            throws Exception {
        HttpServerListClient.ServerListResult serverList = ensureAccountAndServerList(httpClient, account);
        if (serverList == null || serverList.getGate() == null) {
            throw new IllegalStateException("未获取到 GateServer 信息, account=" + account);
        }
        String gameServerId = serverList.getFirstGameServerId();
        if (gameServerId == null || gameServerId.isBlank()) {
            throw new IllegalStateException("未获取到 GameServer, account=" + account);
        }

        HttpServerListClient.ServerNode gate = serverList.getGate();
        NetClient client = new NetClient(gate.getServerIp(), gate.getServerPort(), false);
        client.start(NetService.getInstance().getWorkerGroup());
        if (!waitReady(client)) {
            throw new IllegalStateException("连接 GateServer 超时, account=" + account);
        }

        int loginSeq = client.getSendSeq();
        Login.csLogin loginReq =
                Login.csLogin.newBuilder()
                        .setAccount(account)
                        .setAccountId(serverList.getAccountId())
                        .setPlayerId(0)
                        .setPlayerName(playerName)
                        .setChannel("bot")
                        .setGameServerId(gameServerId)
                        .setToken(serverList.getToken())
                        .setDeviceId("coroutine-player-call-test")
                        .setIsReconnect(false)
                        .build();
        AbstractMessagePacket loginPacket =
                MessagePacketFactory.createAbstractMessagePacket(
                        serverList.getAccountId(),
                        Cmd.CMD.CS_Login_VALUE,
                        loginReq,
                        loginSeq,
                        client.getSid());
        if (!client.send(loginPacket)) {
            throw new IllegalStateException("发送 CS_Login 失败, account=" + account);
        }
        AbstractMessagePacket loginResp =
                waitForResponse(client, Cmd.CMD.SC_Login_VALUE, client.getSid());
        int lastClientDownSeq = assertNextClientDownSeq(0, loginResp, account + " 登录响应");
        Login.scLogin scLogin =
                (Login.scLogin)
                        ProtoMessageFactory.createProtoMessage(Cmd.CMD.SC_Login_VALUE, loginResp.getData());
        if (scLogin == null || scLogin.getPlayerId() <= 0) {
            throw new IllegalStateException("登录响应无效, account=" + account);
        }
        System.out.printf(
                "[COROUTINE-CALL] LOGIN account=%s playerId=%d sid=%d%n",
                account,
                scLogin.getPlayerId(),
                client.getSid());
        return new LoginContext(client, scLogin.getPlayerId(), lastClientDownSeq);
    }

    private static HttpServerListClient.ServerListResult ensureAccountAndServerList(
            HttpServerListClient httpClient, String account) throws InterruptedException {
        HttpServerListClient.ServerListResult serverList = null;
        for (int attempt = 1; attempt <= 3; attempt++) {
            serverList = httpClient.getServerList(account);
            if (serverList != null && serverList.getAccountId() > 0 && serverList.getToken() != null) {
                return serverList;
            }
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

    private static AbstractMessagePacket waitForResponse(NetClient client, int cmd, int sid)
            throws InterruptedException {
        long deadline = System.currentTimeMillis() + RESPONSE_TIMEOUT_MS;
        while (System.currentTimeMillis() < deadline) {
            AbstractMessagePacket packet = client.readPacket();
            if (packet != null) {
                if (packet.getCmd() == cmd && packet.getSid() == sid) {
                    return packet;
                }
                System.out.printf(
                        "[COROUTINE-CALL] ignore packet cmd=%d seq=%d sid=%d%n",
                        packet.getCmd(),
                        packet.getSeq(),
                        packet.getSid());
            }
            Thread.sleep(POLL_INTERVAL_MS);
        }
        throw new IllegalStateException(
                String.format("等待响应超时 cmd=%d sid=%d", cmd, sid));
    }

    private static int assertNextClientDownSeq(int previousSeq, AbstractMessagePacket packet, String name) {
        int expectedSeq = previousSeq + 1;
        if (packet.getSeq() != expectedSeq) {
            throw new IllegalStateException(
                    String.format(
                            "%s 下行 seq 校验失败，expect seq=%d, actual seq=%d",
                            name,
                            expectedSeq,
                            packet.getSeq()));
        }
        return packet.getSeq();
    }

    private static boolean fail(String message) {
        System.err.println("[COROUTINE-CALL] FAIL " + message);
        return false;
    }

    private record LoginContext(NetClient client, long playerId, int lastClientDownSeq) {
    }
}
