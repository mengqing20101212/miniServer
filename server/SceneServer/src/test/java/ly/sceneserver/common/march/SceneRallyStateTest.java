package ly.sceneserver.common.march;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Test;

import ly.proto.Scene;
import ly.sceneserver.common.ScenePoint;

/** 验证创建集结、成员到达、到时发车、攻城结果幂等和全员返程。 */
public class SceneRallyStateTest {

    @Test
    public void rallyFreezesReadyMembersAndAppliesBattleResultOnce() {
        SceneRallyState rally = createRally();
        rally.join(102, 88, 2002, 800, 30_000, 21, 5_000);
        rally.join(103, 88, 2003, 600, 20_000, 22, 5_100);
        rally.memberArrived(102);

        SceneRallyLaunchSnapshot launch = rally.launch(101, 10_000);
        assertEquals(2, launch.members().size());
        assertEquals(1_800, launch.totalTroops());
        assertEquals(80_000L, launch.totalPower());
        Scene.SceneRallyLaunchSnapshot protoLaunch = SceneMarchProtoMapper.toProto(launch);
        assertEquals(2, protoLaunch.getMembersCount());
        assertEquals(9001L, protoLaunch.getTarget().getTargetId());
        assertEquals(SceneRallyMemberStatus.EXCLUDED,
                rally.snapshot().members().stream()
                        .filter(member -> member.playerId() == 103)
                        .findFirst()
                        .orElseThrow()
                        .status());

        rally.markBattlePending();
        SceneRallyBattleResult result = new SceneRallyBattleResult(
                7001,
                3001,
                9001,
                7,
                true,
                500,
                Map.of(101L, 900, 102L, 500));
        assertTrue(rally.applyBattleResult(result));
        assertFalse(rally.applyBattleResult(result));
        assertEquals(SceneRallyStatus.RETURNING, rally.snapshot().status());
        assertTrue(rally.snapshot().victory());

        rally.memberReturned(101);
        rally.memberReturned(102);
        assertEquals(SceneRallyStatus.FINISHED, rally.snapshot().status());
    }

    @Test(expected = IllegalArgumentException.class)
    public void differentAllianceCannotJoinRally() {
        SceneRallyState rally = createRally();
        rally.join(102, 99, 2002, 800, 30_000, 21, 5_000);
    }

    private static SceneRallyState createRally() {
        SceneTargetDescriptor city = new SceneTargetDescriptor(
                9001,
                SceneTargetType.ALLIANCE_CITY,
                new ScenePoint(100, 100),
                SceneTargetTag.ATTACKABLE.mask()
                        | SceneTargetTag.RALLYABLE.mask()
                        | SceneTargetTag.OCCUPIABLE.mask()
                        | SceneTargetTag.ENEMY.mask()
                        | SceneTargetTag.REQUIRES_VISION.mask(),
                7);
        return new SceneRallyState(
                3001,
                101,
                88,
                2001,
                new ScenePoint(10, 10),
                city,
                3,
                2,
                10_000,
                1_000,
                50_000,
                20);
    }
}
