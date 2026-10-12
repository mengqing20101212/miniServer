package ly.db.entry;

import ly.db.AbstractEntry;
import ly.db.DbMeta;

/** 玩家独立记录；setter 自动登记变化，由玩家保存边界提交。 */
@DbMeta.DbTable(
        name = "player_hero",
        uniqueKeys = {"player_id,hero_id"})
public final class PlayerHeroEntry extends AbstractEntry {
    @DbMeta.DbMasterKey(name = "id")
    @DbMeta.DbField(name = "id")
    private Long id;

    @DbMeta.DbField(name = "player_id", nullable = false)
    private Long playerId;

    @DbMeta.DbField(name = "hero_id", nullable = false)
    private Integer heroId;

    @DbMeta.DbField(name = "level", nullable = false)
    private Integer level;

    @DbMeta.DbField(name = "star", nullable = false)
    private Integer star;

    @DbMeta.DbField(name = "awaken", nullable = false)
    private Integer awaken;

    @DbMeta.DbField(name = "exp", nullable = false)
    private Long exp;

    @Override
    protected String[] allDirtyFieldNames() {
        return new String[] {"player_id", "hero_id", "level", "star", "awaken", "exp"};
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

    public Integer getHeroId() {
        return heroId;
    }

    public void setHeroId(Integer value) {
        checkPrimaryKeyChange(heroId, value);
        if (java.util.Objects.equals(heroId, value)) {
            return;
        }
        autoAddCurVersion();
        heroId = value;
        markFieldDirty(1);
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer value) {
        if (java.util.Objects.equals(level, value)) {
            return;
        }
        autoAddCurVersion();
        level = value;
        markFieldDirty(2);
    }

    public Integer getStar() {
        return star;
    }

    public void setStar(Integer value) {
        if (java.util.Objects.equals(star, value)) {
            return;
        }
        autoAddCurVersion();
        star = value;
        markFieldDirty(3);
    }

    public Integer getAwaken() {
        return awaken;
    }

    public void setAwaken(Integer value) {
        if (java.util.Objects.equals(awaken, value)) {
            return;
        }
        autoAddCurVersion();
        awaken = value;
        markFieldDirty(4);
    }

    public Long getExp() {
        return exp;
    }

    public void setExp(Long value) {
        if (java.util.Objects.equals(exp, value)) {
            return;
        }
        autoAddCurVersion();
        exp = value;
        markFieldDirty(5);
    }
}
