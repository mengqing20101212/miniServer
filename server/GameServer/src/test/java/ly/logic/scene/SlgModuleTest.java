package ly.logic.scene;

import static org.junit.Assert.*;
import com.baidu.bjf.remoting.protobuf.ProtobufProxy;
import ly.db.PersistenceScope;
import ly.db.entry.PlayerEntry;
import ly.logic.player.*;
import ly.logic.resource.module.ResourceModule;
import ly.proto.Scene;
import org.junit.Test;

public class SlgModuleTest {
    @Test public void returnedMarchAwardsOnceAndReceiptSurvivesModuleSerialization() throws Exception {
        PlayerEntry entry = new PlayerEntry(); entry.setId(123L);
        Player player = new Player();
        player.setPlayerData(new PlayerData(entry, new PlayerDataTestFactory.MemoryStore()));
        ResourceModule resources = new ResourceModule(); resources.init(player);
        SlgModule slg = new SlgModule(); slg.init(player);
        Scene.SceneMarchSnapshot result = Scene.SceneMarchSnapshot.newBuilder().setMarchId(7)
                .setOwnerPlayerId(123).setResourceId(1).setRewardAmount(100)
                .setStatus(Scene.SceneMarchStatus.SCENE_MARCH_RETURNING).build();
        slg.settle(result);
        assertEquals(0, resources.getResource(1));
        result = result.toBuilder().setStatus(Scene.SceneMarchStatus.SCENE_MARCH_FINISHED).build();
        try (PersistenceScope scope = PersistenceScope.open()) { slg.settle(result); slg.settle(result); }
        assertEquals(100, resources.getResource(1));
        byte[] bytes = ProtobufProxy.create(SlgModule.class).encode(slg);
        SlgModule restored = ProtobufProxy.create(SlgModule.class).decode(bytes); restored.init(player);
        restored.settle(result);
        assertEquals(100, resources.getResource(1));
        assertEquals(java.util.List.of(7L), restored.settledMarches);
    }
}
