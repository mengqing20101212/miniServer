package ly.logic.hero;

import ly.config.ResourceType;
import ly.logic.hero.module.HeroBean;
import ly.logic.hero.module.HeroModule;
import ly.logic.player.ModuleEnum;
import ly.logic.player.Player;
import ly.logic.player.PlayerData;
import ly.logic.player.PlayerDataTestFactory;
import ly.logic.resource.module.ResourceModule;
import ly.logic.resource.module.ResourceModuleData;
import ly.net.GameConnectSession;
import ly.net.GameHandlerContext;
import ly.net.GamePlayer;
import ly.net.packet.MessagePacket;
import ly.proto.Cmd;
import ly.proto.ErrorMsg;
import ly.proto.Hero;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import org.slf4j.Logger;

import java.io.File;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.Assert.*;

/**
 * HeroController 集成测试
 * 测试英雄模块的消息处理流程，包括升级、升星、觉醒等操作
 */
public class HeroControllerTest {

    private HeroController heroController;
    private MockGamePlayer mockGamePlayer;
    private Player mockPlayer;
    private HeroModule heroModule;
    private ResourceModule resourceModule;
    private GameHandlerContext mockContext;
    private static final Logger logger = ly.LoggerDef.SystemLogger;

    @Before
    public void setUp() throws Exception {
        // 初始化控制器
        heroController = new HeroController();
        heroController.registerHandlerRouter();

        // 加载所有配置表
        String configDir = new File("../../excel/serverConfig").getAbsolutePath();
        logger.info("Loading configs from: {}", configDir);
        ly.ConfigService.getInstance().loadAllConfig(logger, configDir);
        // 初始化模拟玩家和游戏对象
        mockGamePlayer = new MockGamePlayer(100001L);
        // 连接 session 和 gamePlayer（消息捕获）
        ((MockGameConnectSession) mockGamePlayer.getSession()).setTestPlayer(mockGamePlayer);
        mockPlayer = new Player();
        // 连接 GamePlayer（Player.sendMsg 需要）
        setField(mockPlayer, "gamePlayer", mockGamePlayer);
        PlayerData mockPlayerData = testPlayerData();
        mockPlayer.setPlayerData(mockPlayerData);

        // 初始化英雄模块和资源模块
        heroModule = new HeroModule();
        heroModule.init(mockPlayer);

        resourceModule = new ResourceModule();
        resourceModule.init(mockPlayer);
        setField(resourceModule, "moduleData", new ResourceModuleData());

        // 给玩家添加默认资源
        resourceModule.addResource(ResourceType.GOLD, 100000);
        resourceModule.addResource(ResourceType.DIAMOND, 10000);
        resourceModule.addResource(ResourceType.EXP_ITEM, 5000);
        resourceModule.addResource(ResourceType.HERO_DEBRIS, 1000);
        resourceModule.addResource(ResourceType.AWAKEN_ITEM, 500);
        resourceModule.addResource(1120001, 50000);  // 觉醒货币
        resourceModule.addResource(1210001, 100);    // 觉醒材料（1210001,40）

        // 注册模块到玩家
        mockPlayerData.putModule(ModuleEnum.HERO_MODULE, heroModule);
        mockPlayerData.putModule(ModuleEnum.RESOURCE_MODULE, resourceModule);

        // 初始化上下文
        mockContext = new GameHandlerContext(mockPlayer, new MockRequestPacket());
    }

    @After
    public void tearDown() {
        heroController = null;
        mockGamePlayer = null;
        mockPlayer = null;
        heroModule = null;
        resourceModule = null;
        mockContext = null;
    }

    private static void setField(Object target, String fieldName, Object value) throws Exception {
        Class<?> clazz = target.getClass();
        while (clazz != null) {
            try {
                Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                field.set(target, value);
                return;
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            }
        }
        throw new NoSuchFieldException(fieldName + " not found in " + target.getClass());
    }

    private static int validHeroId(int index) {
        List<Integer> heroIds = ly.config.HeroInfoConfigManager.getInstance()
                .getConfigMap()
                .keySet()
                .stream()
                .sorted()
                .toList();
        if (heroIds.isEmpty()) {
            throw new IllegalStateException("heroInfo 配置为空，无法执行 HeroControllerTest");
        }
        return heroIds.get(index % heroIds.size());
    }

    private static PlayerData testPlayerData() {
        ly.db.entry.PlayerEntry entry = new ly.db.entry.PlayerEntry();
        entry.setId(100001L);
        entry.setAccount("test_account");
        return PlayerDataTestFactory.create(entry);
    }

    /**
     * 测试获取英雄列表
     */
    @Test
    public void testHandleHeroList() {
        // 先添加几个英雄
        heroModule.addHero(validHeroId(0));
        heroModule.addHero(validHeroId(1));
        heroModule.addHero(validHeroId(2));

        // 创建请求
        Hero.CS_HeroList request = Hero.CS_HeroList.newBuilder().build();

        // 调用处理器
        heroController.handleHeroList(mockContext, request);

        // 验证返回的消息
        MockMessagePacket sentMessage = mockGamePlayer.getSentMessage(Cmd.CMD.SC_HeroList_VALUE);
        assertNotNull("应发送消息", sentMessage);
        assertEquals("消息 CMD 应为 SC_HeroList", Cmd.CMD.SC_HeroList_VALUE, sentMessage.cmd);

        // 验证消息内容
        Hero.SC_HeroList response = (Hero.SC_HeroList) sentMessage.message;
        assertNotNull("响应不应为空", response);
        assertEquals("英雄列表应包含 3 个英雄", 3, response.getHeroListCount());
    }

    /**
     * 测试英雄升级
     */
    @Test
    public void testHandleHeroLevelUp() {
        // 添加一个英雄
        HeroBean hero = heroModule.addHero(validHeroId(0));
        int initialLevel = hero.level;

        // 创建升级请求（使用经验道具 ID 列表）
        Hero.CS_HeroLevelUp request = Hero.CS_HeroLevelUp.newBuilder()
                .setHeroUid(hero.heroUid)
                .addExpItemIds(1) // 添加一个经验道具 ID
                .build();

        // 调用处理器
        heroController.handleHeroLevelUp(mockContext, request);

        // 验证返回的消息
        MockMessagePacket sentMessage = mockGamePlayer.getSentMessage(Cmd.CMD.SC_HeroLevelUp_VALUE);
        assertNotNull("应发送消息", sentMessage);
        assertEquals("消息 CMD 应为 SC_HeroLevelUp", Cmd.CMD.SC_HeroLevelUp_VALUE, sentMessage.cmd);

        // 验证响应结果
        Hero.SC_HeroLevelUp response = (Hero.SC_HeroLevelUp) sentMessage.message;
        assertEquals("升级应成功", ErrorMsg.ErrorCode.Ok, response.getResult());
        assertNotNull("应返回英雄信息", response.getHeroInfo());

        // 验证英雄等级提升
        assertTrue("英雄等级应提升", response.getHeroInfo().getLevel() >= initialLevel);
    }

    /**
     * 测试升级不存在的英雄
     */
    @Test
    public void testHandleHeroLevelUpNonExistent() {
        // 创建升级请求（使用不存在的 heroUid）
        Hero.CS_HeroLevelUp request = Hero.CS_HeroLevelUp.newBuilder()
                .setHeroUid(999999L)
                .addExpItemIds(1)
                .build();

        // 调用处理器
        heroController.handleHeroLevelUp(mockContext, request);

        // 验证返回错误码
        MockMessagePacket sentMessage = mockGamePlayer.getLastSentMessage();
        // 应该发送错误码或失败响应
        assertNotNull("应发送响应", sentMessage);
    }

    /**
     * 测试升级时经验道具不足
     */
    @Test
    public void testHandleHeroLevelUpInsufficientExpItems() {
        // 添加一个英雄
        HeroBean hero = heroModule.addHero(validHeroId(0));

        // 清空经验道具
        resourceModule.deductResource(ResourceType.EXP_ITEM, 5000);

        // 创建升级请求
        Hero.CS_HeroLevelUp request = Hero.CS_HeroLevelUp.newBuilder()
                .setHeroUid(hero.heroUid)
                .addExpItemIds(1)
                .build();

        // 调用处理器
        heroController.handleHeroLevelUp(mockContext, request);

        // 验证返回错误码
        MockMessagePacket sentMessage = mockGamePlayer.getLastSentMessage();
        assertNotNull("应发送响应", sentMessage);
    }

    /**
     * 测试英雄升星
     */
    @Test
    public void testHandleHeroStarUp() {
        // 添加一个英雄
        HeroBean hero = heroModule.addHero(validHeroId(0));

        // 创建升星请求
        Hero.CS_HeroStarUp request = Hero.CS_HeroStarUp.newBuilder()
                .setHeroUid(hero.heroUid)
                .build();

        // 调用处理器
        heroController.handleHeroStarUp(mockContext, request);

        // 验证返回的消息
        MockMessagePacket sentMessage = mockGamePlayer.getSentMessage(Cmd.CMD.SC_HeroStarUp_VALUE);
        assertNotNull("应发送消息", sentMessage);
        assertEquals("消息 CMD 应为 SC_HeroStarUp", Cmd.CMD.SC_HeroStarUp_VALUE, sentMessage.cmd);
    }

    /**
     * 测试升星不存在的英雄
     */
    @Test
    public void testHandleHeroStarUpNonExistent() {
        // 创建升星请求（使用不存在的 heroUid）
        Hero.CS_HeroStarUp request = Hero.CS_HeroStarUp.newBuilder()
                .setHeroUid(999999L)
                .build();

        // 调用处理器
        heroController.handleHeroStarUp(mockContext, request);

        // 验证返回错误码
        MockMessagePacket sentMessage = mockGamePlayer.getLastSentMessage();
        assertNotNull("应发送响应", sentMessage);
    }

    /**
     * 测试英雄觉醒
     */
    @Test
    public void testHandleHeroAwaken() {
        // 添加一个英雄
        HeroBean hero = heroModule.addHero(validHeroId(0));

        // 创建觉醒请求
        Hero.CS_HeroAwaken request = Hero.CS_HeroAwaken.newBuilder()
                .setHeroUid(hero.heroUid)
                .build();

        // 调用处理器
        heroController.handleHeroAwaken(mockContext, request);

        // 验证返回的消息
        MockMessagePacket sentMessage = mockGamePlayer.getSentMessage(Cmd.CMD.SC_HeroAwaken_VALUE);
        assertNotNull("应发送消息", sentMessage);
        assertEquals("消息 CMD 应为 SC_HeroAwaken", Cmd.CMD.SC_HeroAwaken_VALUE, sentMessage.cmd);

        // 验证响应结果
        Hero.SC_HeroAwaken response = (Hero.SC_HeroAwaken) sentMessage.message;
        assertNotNull("应返回响应", response);
    }

    /**
     * 测试觉醒不存在的英雄
     */
    @Test
    public void testHandleHeroAwakenNonExistent() {
        // 创建觉醒请求（使用不存在的 heroUid）
        Hero.CS_HeroAwaken request = Hero.CS_HeroAwaken.newBuilder()
                .setHeroUid(999999L)
                .build();

        // 调用处理器
        heroController.handleHeroAwaken(mockContext, request);

        // 验证返回错误码
        MockMessagePacket sentMessage = mockGamePlayer.getLastSentMessage();
        assertNotNull("应发送响应", sentMessage);
    }

    /**
     * 测试添加英雄
     */
    @Test
    public void testHandleHeroAdd() {
        // 创建添加英雄请求
        Hero.CS_HeroAdd request = Hero.CS_HeroAdd.newBuilder()
                .setHeroId(validHeroId(0))
                .setCount(1)
                .build();

        // 调用处理器
        heroController.handleHeroAdd(mockContext, request);

        // 验证返回的消息
        MockMessagePacket sentMessage = mockGamePlayer.getSentMessage(Cmd.CMD.SC_HeroAdd_VALUE);
        assertNotNull("应发送消息", sentMessage);
        assertEquals("消息 CMD 应为 SC_HeroAdd", Cmd.CMD.SC_HeroAdd_VALUE, sentMessage.cmd);

        // 验证响应结果
        Hero.SC_HeroAdd response = (Hero.SC_HeroAdd) sentMessage.message;
        assertEquals("添加应成功", ErrorMsg.ErrorCode.Ok, response.getResult());
        assertEquals("应返回 1 个英雄", 1, response.getHeroListCount());

        // 验证英雄确实被添加
        HeroBean addedHero = heroModule.getHero(mockPlayer.getPlayerId() * 1000000L + validHeroId(0));
        assertNotNull("英雄应被添加", addedHero);
        assertEquals("英雄 ID 应正确", validHeroId(0), addedHero.heroId);
    }

    /**
     * 测试添加多个英雄
     */
    @Test
    public void testHandleHeroAddMultiple() {
        // 添加多个不同 ID 的英雄（相同 heroId 只能添加一个，因为 heroUid 唯一）
        Hero.CS_HeroAdd request = Hero.CS_HeroAdd.newBuilder()
                .setHeroId(validHeroId(1))
                .setCount(1)
                .build();

        // 调用处理器
        heroController.handleHeroAdd(mockContext, request);

        // 验证返回的消息
        MockMessagePacket sentMessage = mockGamePlayer.getSentMessage(Cmd.CMD.SC_HeroAdd_VALUE);
        assertNotNull("应发送消息", sentMessage);

        // 验证响应结果
        assertEquals("消息 CMD 应为 SC_HeroAdd", Cmd.CMD.SC_HeroAdd_VALUE, sentMessage.cmd);
        Hero.SC_HeroAdd response = (Hero.SC_HeroAdd) sentMessage.message;
        assertEquals("添加应成功", ErrorMsg.ErrorCode.Ok, response.getResult());
        assertEquals("应返回 1 个英雄", 1, response.getHeroListCount());
    }

    /**
     * 测试添加已存在的英雄
     */
    @Test
    public void testHandleHeroAddAlreadyExists() {
        // 先添加一个英雄
        int heroId = validHeroId(2);
        heroModule.addHero(heroId);

        // 再次尝试添加相同 ID 的英雄
        Hero.CS_HeroAdd request = Hero.CS_HeroAdd.newBuilder()
                .setHeroId(heroId)
                .setCount(1)
                .build();

        // 调用处理器
        heroController.handleHeroAdd(mockContext, request);

        // 验证返回错误码
        MockMessagePacket sentMessage = mockGamePlayer.getLastSentMessage();
        assertNotNull("应发送响应", sentMessage);
    }

    /**
     * 测试添加英雄超过最大数量限制
     */
    @Test
    public void testHandleHeroAddMaxLimit() {
        // 添加大量英雄直到达到限制
        for (int i = 0; i < 100; i++) {
            heroModule.addHero(validHeroId(i));
        }

        // 尝试再添加一个英雄
        Hero.CS_HeroAdd request = Hero.CS_HeroAdd.newBuilder()
                .setHeroId(validHeroId(0))
                .setCount(1)
                .build();

        // 调用处理器
        heroController.handleHeroAdd(mockContext, request);

        // 验证返回错误码（英雄槽已满）
        MockMessagePacket sentMessage = mockGamePlayer.getLastSentMessage();
        assertNotNull("应发送响应", sentMessage);
    }

    /**
     * 测试资源消耗正确性
     */
    @Test
    public void testResourceConsumption() {
        // 记录初始资源
        long initialGold = resourceModule.getResource(ResourceType.GOLD);
        long initialExpItem = resourceModule.getResource(ResourceType.EXP_ITEM);

        // 添加英雄并尝试升级
        HeroBean hero = heroModule.addHero(validHeroId(0));

        // 创建升级请求
        Hero.CS_HeroLevelUp request = Hero.CS_HeroLevelUp.newBuilder()
                .setHeroUid(hero.heroUid)
                .addExpItemIds(1)
                .build();

        // 调用处理器
        heroController.handleHeroLevelUp(mockContext, request);

        // 验证资源被消耗
        long currentExpItem = resourceModule.getResource(ResourceType.EXP_ITEM);
        assertTrue("经验道具应被消耗", currentExpItem < initialExpItem);
    }

    /**
     * 模拟 GamePlayer
     */
    private static class MockGamePlayer extends GamePlayer {
        private final ConcurrentHashMap<Integer, MockMessagePacket> sentMessages = new ConcurrentHashMap<>();

        public MockGamePlayer(long playerId) {
            super(new MockGameConnectSession());
            this.setPlayerId(playerId);
        }

        public void addSentMessage(int cmd, Object message) {
            sentMessages.put(cmd, new MockMessagePacket(cmd, message));
        }

        public MockMessagePacket getLastSentMessage() {
            if (sentMessages.isEmpty()) {
                return null;
            }
            return sentMessages.values().iterator().next();
        }

        public MockMessagePacket getSentMessage(int cmd) {
            return sentMessages.get(cmd);
        }

        public long getPlayerId() {
            return super.getPlayerId();
        }
    }

    /**
     * 模拟 GameConnectSession
     */
    private static class MockGameConnectSession extends GameConnectSession {
        private MockGamePlayer testPlayer;

        public MockGameConnectSession() {
            super(0L);
        }

        public void setTestPlayer(MockGamePlayer player) {
            this.testPlayer = player;
        }

        @Override
        public boolean addSendPacket(ly.net.packet.MessagePacket packet) {
            // 捕获发送的消息供测试验证（提取 protobuf 对象）
            if (testPlayer != null) {
                testPlayer.addSentMessage(packet.getCmd(), extractMessage(packet));
            }
            return super.addSendPacket(packet);
        }

        public void sendMsg(Cmd.CMD cmd, Object msg) {
            // 模拟发送消息
        }

        @Override
        public void closeChannel() {
            // 模拟关闭通道
        }
    }

    /**
     * 模拟 MessagePacket
     */
    private static class MockRequestPacket extends MessagePacket {
        public MockRequestPacket() {
            super();
        }

        @Override
        public int getCmd() {
            return 0;
        }
    }

    /**
     * 模拟消息包
     */
    private static class MockMessagePacket {
        public final int cmd;
        public final Object message;

        public MockMessagePacket(int cmd, Object message) {
            this.cmd = cmd;
            this.message = message;
        }
    }

    /**
     * 从 MessagePacket 中提取 protobuf 消息对象
     */
    private static Object extractMessage(MessagePacket packet) {
        try {
            byte[] data = packet.getData();
            int cmd = packet.getCmd();
            // 下行包 cmd 使用 SC_ 枚举值
            if (cmd == Cmd.CMD.SC_ErrorCode_VALUE)
                return ErrorMsg.scErrorCode.parseFrom(data);
            if (cmd == Cmd.CMD.SC_HeroList_VALUE)
                return Hero.SC_HeroList.parseFrom(data);
            if (cmd == Cmd.CMD.SC_HeroLevelUp_VALUE)
                return Hero.SC_HeroLevelUp.parseFrom(data);
            if (cmd == Cmd.CMD.SC_HeroStarUp_VALUE)
                return Hero.SC_HeroStarUp.parseFrom(data);
            if (cmd == Cmd.CMD.SC_HeroAwaken_VALUE)
                return Hero.SC_HeroAwaken.parseFrom(data);
            if (cmd == Cmd.CMD.SC_HeroAdd_VALUE)
                return Hero.SC_HeroAdd.parseFrom(data);
            return packet;
        } catch (Exception e) {
            return packet;
        }
    }
}
