package ly.logic.asset;

import static org.junit.Assert.*;

import ly.db.*;
import ly.db.entry.*;
import ly.logic.player.*;
import ly.logic.resource.module.ResourceModule;

import org.junit.Test;

import java.util.*;

public class AwardConsumeTest {
    private static final class Store extends PlayerDataTestFactory.MemoryStore {
        int batches;
        boolean reject;

        public boolean acceptBatch(List<AbstractEntry> saves, List<AbstractEntry> deletes) {
            if (reject) return false;
            batches++;
            return true;
        }
    }

    private Player player(Store store) {
        PlayerEntry entry = new PlayerEntry();
        entry.setId(123L);
        Player player = new Player();
        player.setPlayerData(new PlayerData(entry, store));
        ResourceModule module = new ResourceModule();
        module.init(player);
        return player;
    }

    private ResourceModule resource(Player player) {
        return (ResourceModule) player.getPlayerData().getModule(ModuleEnum.RESOURCE_MODULE);
    }

    @Test
    public void mergedCostIsCheckedBeforeAnyResourceIsDeducted() {
        Store store = new Store();
        Player player = player(store);
        resource(player).addResource(1, 10);
        resource(player).addResource(2, 5);
        int previous = store.batches;
        Consume cost = Consume.create(1, 6).addThing(1, 5).addThing(2, 1);
        assertFalse(cost.isEnoughAndCost(player, ChangeReason.HERO_LEVEL_UP));
        assertEquals(10, resource(player).getResource(1));
        assertEquals(5, resource(player).getResource(2));
        assertEquals(previous, store.batches);
        assertTrue(
                Consume.create(1, 6)
                        .addThing(2, 5)
                        .isEnoughAndCost(player, ChangeReason.HERO_LEVEL_UP));
        assertEquals(previous + 1, store.batches);
        assertEquals(4, resource(player).getResource(1));
        assertEquals(0, resource(player).getResource(2));
    }

    @Test
    public void nestedConsumeAndBusinessMutationShareOuterPersistenceBoundary() {
        Store store = new Store();
        Player player = player(store);
        resource(player).addResource(1, 10);
        int previous = store.batches;
        try (PersistenceScope scope = PersistenceScope.open()) {
            assertTrue(Consume.create(1, 3).isEnoughAndCost(player, ChangeReason.HERO_LEVEL_UP));
            player.getPlayerData().getPlayerEntry().setLevel(2);
            assertEquals(previous, store.batches);
        }
        assertEquals(previous + 1, store.batches);
    }

    @Test
    public void awardPrechecksEveryOverflowAndPreventsReusingExecutedObject() {
        Store store = new Store();
        Player player = player(store);
        resource(player).addResource(2, Long.MAX_VALUE);
        assertThrows(
                ArithmeticException.class,
                () -> Award.create(1, 1).addThing(2, 1).award(player, ChangeReason.QUEST_REWARD));
        assertEquals(0, resource(player).getResource(1));
        Award award = Award.create(1, 2).addThing(1, 3);
        award.award(player, ChangeReason.QUEST_REWARD);
        assertEquals(5, resource(player).getResource(1));
        assertThrows(
                IllegalStateException.class, () -> award.award(player, ChangeReason.QUEST_REWARD));
    }

    @Test
    public void rejectedBatchDoesNotRunSuccessCallbacksAndCanBeRetried() {
        Store store = new Store();
        Player player = player(store);
        boolean[] notified = {false};
        store.reject = true;
        assertThrows(
                IllegalStateException.class,
                () -> {
                    try (PersistenceScope scope = PersistenceScope.open()) {
                        resource(player).addResource(1, 3);
                        PersistenceScope.afterFlush(() -> notified[0] = true);
                    }
                });
        assertFalse(notified[0]);
        store.reject = false;
        player.getPlayerData().flushPendingChanges();
        assertEquals(1, store.batches);
    }
}
