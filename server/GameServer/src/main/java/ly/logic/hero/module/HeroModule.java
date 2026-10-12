package ly.logic.hero.module;

import com.baidu.bjf.remoting.protobuf.FieldType;
import com.baidu.bjf.remoting.protobuf.annotation.Ignore;
import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;

import ly.config.HeroInfoConfigManager;
import ly.db.entry.PlayerHeroEntry;
import ly.logic.player.AbstractModule;
import ly.logic.player.event.PlayerEventParam;
import ly.logic.player.event.PlayerEventType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** 英雄记录独立保存，模块 Blob 只保存容量等少量状态。 */
@ProtobufClass
public class HeroModule extends AbstractModule {
    @Ignore private final List<PlayerHeroEntry> heroes = new ArrayList<>();

    @Protobuf(fieldType = FieldType.INT32, order = 1, required = false)
    private int maxHeroCount = 100;

    public int getMaxHeroCount() {
        return maxHeroCount;
    }

    public void setMaxHeroCount(int value) {
        if (value < heroes.size()) {
            throw new IllegalArgumentException("capacity below hero count");
        }
        if (maxHeroCount == value) {
            return;
        }
        maxHeroCount = value;
        markModuleDataDirty();
    }

    @Override
    public void onLoadData() {
        heroes.clear();
        heroes.addAll(player.getPlayerData().loadHeroEntries());
        for (PlayerHeroEntry entry : heroes) {
            player.getPlayerData().registerLoadedEntity(entry);
        }
    }

    @Override
    public void onOpenFunction() {}

    @Override
    public List<PlayerEventType> getRegisterEventTypes() {
        return Collections.emptyList();
    }

    @Override
    public void onEvent(PlayerEventParam event) {}

    public List<PlayerHeroEntry> getHeroList() {
        return Collections.unmodifiableList(heroes);
    }

    public PlayerHeroEntry getHero(long uid) {
        for (PlayerHeroEntry entry : heroes) {
            if (entry.getId() == uid) {
                return entry;
            }
        }
        return null;
    }

    public PlayerHeroEntry addHero(int heroId) {
        if (heroes.size() >= maxHeroCount
                || HeroInfoConfigManager.getInstance().getConfigMap().get(heroId) == null) {
            return null;
        }
        for (PlayerHeroEntry entry : heroes) {
            if (entry.getHeroId() == heroId) {
                return null;
            }
        }
        PlayerHeroEntry entry = new PlayerHeroEntry();
        entry.setId(player.getPlayerData().generateEntityId());
        entry.setPlayerId(player.getPlayerId());
        entry.setHeroId(heroId);
        entry.setLevel(1);
        entry.setStar(1);
        entry.setAwaken(0);
        entry.setExp(0L);
        heroes.add(entry);
        player.getPlayerData().registerNewEntity(entry);
        return entry;
    }

    public boolean removeHero(long uid) {
        PlayerHeroEntry entry = getHero(uid);
        if (entry == null) {
            return false;
        }
        heroes.remove(entry);
        player.getPlayerData().registerDeletedEntity(entry);
        return true;
    }
}
