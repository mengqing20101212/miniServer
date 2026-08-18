package ly.sceneserver.common.march;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import ly.proto.Scene;
import ly.sceneserver.common.ScenePoint;

/** 验证普通发车、攻击到达和返程状态机。 */
public class SceneMarchStateTest {

    @Test
    public void attackMarchUsesFrozenTargetAndReturnsAfterBattle() {
        SceneTargetDescriptor city = cityTarget(9001, 7);
        SceneMarchState march = new SceneMarchState(
                1001,
                101,
                88,
                SceneMarchType.ATTACK,
                SceneMarchTag.SOLO.mask() | SceneMarchTag.HOSTILE.mask(),
                new ScenePoint(0, 0),
                city,
                1_000,
                50_000,
                12);

        List<ScenePoint> outbound = List.of(
                new ScenePoint(0, 0),
                new ScenePoint(1, 0),
                new ScenePoint(2, 0));
        march.assignPath(outbound, 100, 50, 1_000);
        assertEquals(SceneMarchStatus.MARCHING, march.snapshot().status());
        assertEquals(3_000L, march.snapshot().arrivalAtMillis());
        Scene.SceneMarchSnapshot proto = SceneMarchProtoMapper.toProto(march.snapshot());
        assertEquals(Scene.SceneMarchType.SCENE_MARCH_ATTACK, proto.getType());
        assertEquals(city.tagMask(), proto.getTarget().getTagMask());

        march.advanceTo(1);
        march.arrive();
        assertEquals(SceneMarchStatus.BATTLE_PENDING, march.snapshot().status());
        assertTrue(SceneMarchTag.contains(march.snapshot().tagMask(), SceneMarchTag.BATTLE_PENDING));

        march.beginReturn(outbound.reversed(), 100, 100, 5_000);
        assertEquals(SceneMarchStatus.RETURNING, march.snapshot().status());
        march.advanceTo(1);
        march.arrive();
        assertEquals(SceneMarchStatus.FINISHED, march.snapshot().status());
    }

    @Test(expected = IllegalArgumentException.class)
    public void gatherCannotTargetAttackOnlyCity() {
        new SceneMarchState(
                1002,
                101,
                88,
                SceneMarchType.GATHER,
                0L,
                new ScenePoint(0, 0),
                cityTarget(9001, 7),
                100,
                1_000,
                1);
    }

    private static SceneTargetDescriptor cityTarget(long targetId, long version) {
        return new SceneTargetDescriptor(
                targetId,
                SceneTargetType.ALLIANCE_CITY,
                new ScenePoint(2, 0),
                SceneTargetTag.ATTACKABLE.mask()
                        | SceneTargetTag.RALLYABLE.mask()
                        | SceneTargetTag.OCCUPIABLE.mask()
                        | SceneTargetTag.ENEMY.mask()
                        | SceneTargetTag.REQUIRES_VISION.mask(),
                version);
    }
}
