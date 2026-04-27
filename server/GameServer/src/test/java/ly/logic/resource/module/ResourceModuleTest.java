package ly.logic.resource.module;

import com.baidu.bjf.remoting.protobuf.Codec;
import com.baidu.bjf.remoting.protobuf.ProtobufProxy;
import ly.config.ResourceType;
import ly.logic.player.ModuleEnum;
import ly.logic.player.Player;
import ly.logic.player.PlayerData;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.*;

/**
 * ResourceModule 单元测试
 */
public class ResourceModuleTest {

    private ResourceModule resourceModule;
    private MockPlayer mockPlayer;

    @Before
    public void setUp() throws Exception {
        // 初始化模拟玩家
        mockPlayer = new MockPlayer(100001L);

        // 初始化资源模块
        resourceModule = new ResourceModule();
        setField(resourceModule, "player", mockPlayer);
        setField(resourceModule, "moduleData", new ResourceModuleData());
    }

    @After
    public void tearDown() {
        resourceModule = null;
        mockPlayer = null;
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

    /**
     * 测试初始化默认资源
     */
    @Test
    public void testDefaultResources() {
        assertEquals("初始金币应为0", 0L, resourceModule.getResource(ResourceType.GOLD));
        assertEquals("初始钻石应为0", 0L, resourceModule.getResource(ResourceType.DIAMOND));
        assertEquals("初始英雄碎片应为0", 0L, resourceModule.getResource(ResourceType.HERO_DEBRIS));
        assertEquals("初始经验道具应为0", 0L, resourceModule.getResource(ResourceType.EXP_ITEM));
        assertEquals("初始觉醒道具应为0", 0L, resourceModule.getResource(ResourceType.AWAKEN_ITEM));
    }

    /**
     * 测试增加资源
     */
    @Test
    public void testAddResource() {
        // 增加金币
        boolean result = resourceModule.addResource(ResourceType.GOLD, 1000);

        assertTrue("增加资源应成功", result);
        assertEquals("金币数量应为1000", 1000L, resourceModule.getResource(ResourceType.GOLD));
    }

    /**
     * 测试多次增加资源
     */
    @Test
    public void testAddResourceMultipleTimes() {
        resourceModule.addResource(ResourceType.GOLD, 500);
        resourceModule.addResource(ResourceType.GOLD, 300);
        resourceModule.addResource(ResourceType.GOLD, 200);

        assertEquals("金币总数应为1000", 1000L, resourceModule.getResource(ResourceType.GOLD));
    }

    /**
     * 测试增加零或负数资源
     */
    @Test
    public void testAddZeroOrNegativeResource() {
        long initial = resourceModule.getResource(ResourceType.GOLD);

        // 增加零
        boolean result1 = resourceModule.addResource(ResourceType.GOLD, 0);
        assertFalse("增加零应失败", result1);
        assertEquals("增加零后资源不应改变", initial, resourceModule.getResource(ResourceType.GOLD));

        // 增加负数
        boolean result2 = resourceModule.addResource(ResourceType.GOLD, -100);
        assertFalse("增加负数应失败", result2);
        assertEquals("增加负数后资源不应改变", initial, resourceModule.getResource(ResourceType.GOLD));
    }

    /**
     * 测试扣除资源
     */
    @Test
    public void testDeductResource() {
        // 先增加资源
        resourceModule.addResource(ResourceType.GOLD, 1000);

        // 扣除资源
        boolean result = resourceModule.deductResource(ResourceType.GOLD, 300);

        assertTrue("扣除资源应成功", result);
        assertEquals("扣除后金币应为700", 700L, resourceModule.getResource(ResourceType.GOLD));
    }

    /**
     * 测试扣除不存在的资源
     */
    @Test
    public void testDeductNonExistentResource() {
        boolean result = resourceModule.deductResource(ResourceType.GOLD, 100);
        assertFalse("扣除不存在的资源应失败", result);
    }

    /**
     * 测试扣除超过现有数量的资源
     */
    @Test
    public void testDeductMoreThanAvailable() {
        // 先增加资源
        resourceModule.addResource(ResourceType.GOLD, 100);

        // 尝试扣除更多
        boolean result = resourceModule.deductResource(ResourceType.GOLD, 200);

        assertFalse("扣除超过现有数量应失败", result);
        assertEquals("扣除失败后资源应不变", 100L, resourceModule.getResource(ResourceType.GOLD));
    }

    /**
     * 测试扣除零或负数资源
     */
    @Test
    public void testDeductZeroOrNegativeResource() {
        resourceModule.addResource(ResourceType.GOLD, 1000);
        long initial = resourceModule.getResource(ResourceType.GOLD);

        // 扣除零
        boolean result1 = resourceModule.deductResource(ResourceType.GOLD, 0);
        assertFalse("扣除零应失败", result1);
        assertEquals("扣除零后资源不应改变", initial, resourceModule.getResource(ResourceType.GOLD));

        // 扣除负数
        boolean result2 = resourceModule.deductResource(ResourceType.GOLD, -100);
        assertFalse("扣除负数应失败", result2);
        assertEquals("扣除负数后资源不应改变", initial, resourceModule.getResource(ResourceType.GOLD));
    }

    /**
     * 测试获取资源
     */
    @Test
    public void testGetResource() {
        // 增加资源
        resourceModule.addResource(ResourceType.DIAMOND, 500);

        long amount = resourceModule.getResource(ResourceType.DIAMOND);
        assertEquals("获取的钻石数量应为500", 500L, amount);
    }

    /**
     * 测试获取不存在的资源类型
     */
    @Test
    public void testGetNonExistentResource() {
        long amount = resourceModule.getResource(9999);
        assertEquals("不存在的资源类型应返回0", 0L, amount);
    }

    /**
     * 测试多种资源类型独立管理
     */
    @Test
    public void testMultipleResourceTypes() {
        resourceModule.addResource(ResourceType.GOLD, 1000);
        resourceModule.addResource(ResourceType.DIAMOND, 500);
        resourceModule.addResource(ResourceType.HERO_DEBRIS, 100);
        resourceModule.addResource(ResourceType.EXP_ITEM, 200);
        resourceModule.addResource(ResourceType.AWAKEN_ITEM, 50);

        assertEquals("金币应为1000", 1000L, resourceModule.getResource(ResourceType.GOLD));
        assertEquals("钻石应为500", 500L, resourceModule.getResource(ResourceType.DIAMOND));
        assertEquals("英雄碎片应为100", 100L, resourceModule.getResource(ResourceType.HERO_DEBRIS));
        assertEquals("经验道具应为200", 200L, resourceModule.getResource(ResourceType.EXP_ITEM));
        assertEquals("觉醒道具应为50", 50L, resourceModule.getResource(ResourceType.AWAKEN_ITEM));
    }

    /**
     * 测试大批量资源操作
     */
    @Test
    public void testBulkResourceOperations() {
        // 批量增加
        for (int i = 0; i < 1000; i++) {
            resourceModule.addResource(ResourceType.GOLD, 1);
        }
        assertEquals("批量增加后金币应为1000", 1000L, resourceModule.getResource(ResourceType.GOLD));

        // 批量扣除
        for (int i = 0; i < 500; i++) {
            resourceModule.deductResource(ResourceType.GOLD, 1);
        }
        assertEquals("批量扣除后金币应为500", 500L, resourceModule.getResource(ResourceType.GOLD));
    }

    /**
     * 模拟Player类，用于测试
     */
    private static class MockPlayer extends Player {
        private final long playerId;

        public MockPlayer(long playerId) {
            this.playerId = playerId;
        }

        @Override
        public long getPlayerId() {
            return playerId;
        }

        @Override
        public PlayerData getPlayerData() {
            return null;
        }
    }
}
