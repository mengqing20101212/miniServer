package ly.logic.hero.module;

import com.baidu.bjf.remoting.protobuf.Codec;
import com.baidu.bjf.remoting.protobuf.ProtobufProxy;
import ly.logic.player.ModuleEnum;
import ly.logic.player.Player;
import ly.logic.player.PlayerData;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.*;

/**
 * HeroModule 单元测试
 * 注：不使用 onLoadData()/saveData()，因为 PlayerData 构造函数会触发的 protobuf
 * 类不在 classpath 上。改用反射直接管理 moduleData。
 */
public class HeroModuleTest {

    private HeroModule heroModule;
    private MockPlayer mockPlayer;

    @Before
    public void setUp() throws Exception {
        // 初始化模拟玩家
        mockPlayer = new MockPlayer(100001L);

        // 初始化英雄模块
        heroModule = new HeroModule();
        setField(heroModule, "player", mockPlayer);
    }

    @After
    public void tearDown() {
        heroModule = null;
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
     * 测试添加英雄
     */
    @Test
    public void testAddHero() {
        // 添加英雄ID为1的英雄
        HeroBean hero = heroModule.addHero(1);

        assertNotNull("添加的英雄不应为空", hero);
        assertEquals("英雄ID应为1", 1, hero.heroId);
        assertEquals("初始等级应为1", 1, hero.level);
        assertEquals("初始星级应为1", 1, hero.star);
        assertEquals("初始觉醒应为0", 0, hero.awaken);
        assertEquals("初始经验应为0", 0L, hero.exp);

        // 验证heroUid生成规则
        long expectedUid = mockPlayer.getPlayerId() * 1000000L + 1;
        assertEquals("heroUid应按规则生成", expectedUid, hero.heroUid);

        // 验证英雄列表包含新英雄
        assertTrue("英雄列表应包含新英雄", heroModule.getHeroList().contains(hero));
    }

    /**
     * 测试获取英雄
     */
    @Test
    public void testGetHero() {
        // 添加一个英雄
        HeroBean addedHero = heroModule.addHero(2);
        long heroUid = addedHero.heroUid;

        // 获取英雄
        HeroBean hero = heroModule.getHero(heroUid);

        assertNotNull("应能获取到英雄", hero);
        assertEquals("获取的英雄ID应正确", hero.heroId, addedHero.heroId);
        assertEquals("获取的heroUid应正确", hero.heroUid, addedHero.heroUid);
    }

    /**
     * 测试获取不存在的英雄
     */
    @Test
    public void testGetNonExistentHero() {
        HeroBean hero = heroModule.getHero(999999L);
        assertNull("不存在的英雄应返回null", hero);
    }

    /**
     * 测试英雄列表
     */
    @Test
    public void testGetHeroList() {
        // 初始列表应为空
        assertEquals("初始英雄列表应为空", 0, heroModule.getHeroList().size());

        // 添加多个英雄
        heroModule.addHero(1);
        heroModule.addHero(2);
        heroModule.addHero(3);

        // 验证列表大小
        assertEquals("英雄列表大小应为3", 3, heroModule.getHeroList().size());
    }

    /**
     * 测试最大英雄数量限制
     */
    @Test
    public void testMaxHeroCount() {
        // 添加多个英雄
        for (int i = 0; i < 110; i++) {
            heroModule.addHero(i + 1);
        }

        // 验证英雄数量不超过限制
        assertTrue("英雄数量不应超过最大限制", heroModule.getHeroList().size() <= 100);
    }

    /**
     * 测试多个玩家的heroUid不冲突
     */
    @Test
    public void testHeroUidUnique() throws Exception {
        // 玩家1添加英雄
        MockPlayer player1 = new MockPlayer(1L);
        HeroModule module1 = new HeroModule();
        setField(module1, "player", player1);
        HeroBean hero1 = module1.addHero(100);

        // 玩家2添加相同ID的英雄
        MockPlayer player2 = new MockPlayer(2L);
        HeroModule module2 = new HeroModule();
        setField(module2, "player", player2);
        HeroBean hero2 = module2.addHero(100);

        // 验证heroUid不同
        assertNotEquals("不同玩家的heroUid应不同", hero1.heroUid, hero2.heroUid);
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
