package ly.db.entry;

import ly.db.AbstractEntry;
import ly.db.DbMeta;

/** 玩家独立记录；setter 自动登记变化，由玩家保存边界提交。 */
@DbMeta.DbTable(
        name = "player_resource",
        uniqueKeys = {"player_id,resource_type"})
public final class PlayerResourceEntry extends AbstractEntry {
    @DbMeta.DbMasterKey(name = "id")
    @DbMeta.DbField(name = "id")
    private Long id;

    @DbMeta.DbField(name = "player_id", nullable = false)
    private Long playerId;

    @DbMeta.DbField(name = "resource_type", nullable = false)
    private Integer resourceType;

    @DbMeta.DbField(name = "amount", nullable = false)
    private Long amount;

    @Override
    protected String[] allDirtyFieldNames() {
        return new String[] {"player_id", "resource_type", "amount"};
    }

    public Long getId() {
        return id;
    }

    public void setId(Long value) {
        checkPrimaryKeyChange(id, value);
        id = value;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long value) {
        checkPrimaryKeyChange(playerId, value);
        if (java.util.Objects.equals(playerId, value)) {
            return;
        }
        autoAddCurVersion();
        playerId = value;
        markFieldDirty(0);
    }

    public Integer getResourceType() {
        return resourceType;
    }

    public void setResourceType(Integer value) {
        checkPrimaryKeyChange(resourceType, value);
        if (java.util.Objects.equals(resourceType, value)) {
            return;
        }
        autoAddCurVersion();
        resourceType = value;
        markFieldDirty(1);
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long value) {
        if (java.util.Objects.equals(amount, value)) {
            return;
        }
        autoAddCurVersion();
        amount = value;
        markFieldDirty(2);
    }
}
