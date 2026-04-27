package ly.logic.hero.module;

import com.baidu.bjf.remoting.protobuf.Codec;
import com.baidu.bjf.remoting.protobuf.ProtobufProxy;
import ly.logic.player.AbstractModule;
import ly.logic.player.ModuleEnum;
import ly.proto.Hero;

import ly.logic.player.event.PlayerEventParam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ly.logic.player.event.PlayerEventType;

/**
 * 英雄模块
 */
public class HeroModule extends AbstractModule {
    private HeroModuleData moduleData;

    @Override
    public void onLoadData() {
        byte[] data = player.getPlayerData().getModuleData(ModuleEnum.HERO_MODULE);
        if (data != null && data.length > 0) {
            try {
                Codec<HeroModuleData> codec = ProtobufProxy.create(HeroModuleData.class);
                moduleData = codec.decode(data);
            } catch (Exception e) {
                System.err.println("Error loading HeroModuleData for player " + player.getPlayerId() + ": " + e.getMessage());
                moduleData = new HeroModuleData();
            }
        } else {
            moduleData = new HeroModuleData();
        }
    }

    @Override
    public boolean saveData() {
        try {
            Codec<HeroModuleData> codec = ProtobufProxy.create(HeroModuleData.class);
            byte[] data = codec.encode(moduleData);
            player.getPlayerData().getModuleData().addModuleData(ModuleEnum.HERO_MODULE.getName(), data);
            return true;
        } catch (Exception e) {
            System.err.println("Error saving HeroModuleData for player " + player.getPlayerId() + ": " + e.getMessage());
            return false;
        }
    }

    @Override
    public void onOpenFunction() {
        // 功能开放时的初始化
    }

    @Override
    public List<PlayerEventType> getRegisterEventTypes() {
        return Collections.emptyList();
    }

    @Override
    public void onEvent(PlayerEventParam param) {
        // 不处理事件
    }

    /**
     * 获取英雄列表
     */
    public List<HeroEntry> getHeroList() {
        return moduleData.heroList;
    }

    /**
     * 获取单个英雄
     */
    public HeroEntry getHero(long heroUid) {
        for (HeroEntry hero : moduleData.heroList) {
            if (hero.heroUid == heroUid) {
                return hero;
            }
        }
        return null;
    }

    /**
     * 添加英雄
     */
    public HeroEntry addHero(int heroId) {
        if (moduleData.heroList.size() >= moduleData.maxHeroCount) {
            return null;
        }
        long heroUid = generateHeroUid(heroId);
        if (getHero(heroUid) != null) {
            return null;
        }
        HeroEntry hero = new HeroEntry();
        hero.heroUid = heroUid;
        hero.heroId = heroId;
        hero.level = 1;
        hero.star = 1;
        hero.awaken = 0;
        hero.exp = 0;
        moduleData.heroList.add(hero);
        return hero;
    }

    /**
     * 生成英雄唯一ID
     * 使用 playerId * 1000000 + heroId，避免 playerId > 10000 时冲突
     */
    private long generateHeroUid(int heroId) {
        return player.getPlayerId() * 1000000L + heroId;
    }
}
