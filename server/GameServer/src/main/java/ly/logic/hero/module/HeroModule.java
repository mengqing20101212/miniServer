package ly.logic.hero.module;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.baidu.bjf.remoting.protobuf.FieldType;
import com.baidu.bjf.remoting.protobuf.annotation.EnableZigZap;
import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;

import ly.config.HeroInfoConfig;
import ly.config.HeroInfoConfigManager;
import ly.logic.player.AbstractModule;
import ly.logic.player.event.PlayerEventParam;
import ly.logic.player.event.PlayerEventType;

/**
 * 英雄模块
 * 继承 AbstractModule，自身为 @ProtobufClass，框架自动处理序列化/反序列化。
 */
@ProtobufClass
@EnableZigZap
public class HeroModule extends AbstractModule {

    @Protobuf(fieldType = FieldType.OBJECT, order = 1, required = false)
    public List<HeroBean> heroList = new ArrayList<>();

    @Protobuf(fieldType = FieldType.INT32, order = 2, required = false)
    public int maxHeroCount = 100; // 最大英雄数量

    @Override
    public void onLoadData() {
        // 框架自动反序列化，无需特殊逻辑
    }

    @Override
    public boolean saveData() {
        // 框架定时自动保存，无需特殊逻辑
        return true;
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
    public List<HeroBean> getHeroList() {
        return heroList;
    }

    /**
     * 获取单个英雄
     */
    public HeroBean getHero(long heroUid) {
        for (HeroBean hero : heroList) {
            if (hero.heroUid == heroUid) {
                return hero;
            }
        }
        return null;
    }

    /**
     * 添加英雄
     */
    public HeroBean addHero(int heroId) {
        if (heroList.size() >= maxHeroCount) {
            return null;
        }
        long heroUid = generateHeroUid(heroId);
        if (getHero(heroUid) != null) {
            return null;
        }
        HeroInfoConfig heroInfoConfig = HeroInfoConfigManager.getInstance().getConfigMap().get(heroId);
        if (heroInfoConfig == null) {
            return null;
        }
        if (heroInfoConfig.id != heroId) {
            return null;
        }

        HeroBean hero = new HeroBean();
        hero.heroUid = heroUid;
        hero.heroId = heroId;
        hero.level = 1;
        hero.star = 1;
        hero.awaken = 0;
        hero.exp = 0;
        heroList.add(hero);
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
