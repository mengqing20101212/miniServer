package ly.logic.hero;

import ly.config.HeroAwakenConfig;
import ly.config.HeroAwakenConfigManager;
import ly.config.HeroExpConfig;
import ly.config.HeroExpConfigManager;
import ly.config.HeroInfoConfig;
import ly.config.HeroInfoConfigManager;
import ly.config.HeroStarConfig;
import ly.config.HeroStarConfigManager;
import ly.config.ResourceType;
import ly.db.entry.PlayerHeroEntry;
import ly.logic.asset.ChangeReason;
import ly.logic.asset.Consume;
import ly.logic.hero.module.HeroModule;
import ly.logic.player.ModuleEnum;
import ly.logic.player.Player;
import ly.logic.resource.module.ResourceModule;
import ly.net.GameHandlerContext;
import ly.net.IGameController;
import ly.proto.Cmd;
import ly.proto.ErrorMsg;
import ly.proto.Hero;
import ly.utils.KV;

import java.util.ArrayList;
import java.util.List;

/** 英雄消息控制器 */
public class HeroController implements IGameController {

    @Override
    public void registerHandlerRouter() {
        gameHandlerRegister(Cmd.CMD.CS_HeroList, this::handleHeroList);
        gameHandlerRegister(Cmd.CMD.CS_HeroLevelUp, this::handleHeroLevelUp);
        gameHandlerRegister(Cmd.CMD.CS_HeroStarUp, this::handleHeroStarUp);
        gameHandlerRegister(Cmd.CMD.CS_HeroAwaken, this::handleHeroAwaken);
        gameHandlerRegister(Cmd.CMD.CS_HeroAdd, this::handleHeroAdd);
    }

    /** 处理获取英雄列表 */
    public void handleHeroList(GameHandlerContext context, Hero.CS_HeroList request) {
        Player player = context.player();
        HeroModule heroModule =
                (HeroModule) player.getPlayerData().getModule(ModuleEnum.HERO_MODULE);
        List<PlayerHeroEntry> heroList = heroModule.getHeroList();

        Hero.SC_HeroList.Builder builder = Hero.SC_HeroList.newBuilder();
        for (PlayerHeroEntry entry : heroList) {
            Hero.HeroInfo info = buildHeroInfo(entry);
            builder.addHeroList(info);
        }

        player.sendMsg(Cmd.CMD.SC_HeroList, builder.build());
    }

    /** 处理英雄升级 */
    public void handleHeroLevelUp(GameHandlerContext context, Hero.CS_HeroLevelUp request) {
        Player player = context.player();
        HeroModule heroModule =
                (HeroModule) player.getPlayerData().getModule(ModuleEnum.HERO_MODULE);
        ResourceModule resourceModule =
                (ResourceModule) player.getPlayerData().getModule(ModuleEnum.RESOURCE_MODULE);

        // 验证英雄存在
        PlayerHeroEntry hero = heroModule.getHero(request.getHeroUid());
        if (hero == null) {
            player.sendErrorCode(Cmd.CMD.CS_HeroLevelUp, ErrorMsg.ErrorCode.HERO_NOT_FOUND);
            return;
        }

        // 获取英雄配置
        HeroInfoConfig heroConfig =
                HeroInfoConfigManager.getInstance().getConfigMap().get(hero.getHeroId());
        if (heroConfig == null) {
            player.sendErrorCode(Cmd.CMD.CS_HeroLevelUp, ErrorMsg.ErrorCode.HERO_NOT_FOUND);
            return;
        }

        // 获取升级经验配置
        HeroExpConfig expConfig =
                HeroExpConfigManager.getInstance()
                        .getByModelIdAndLevel(heroConfig.expModelId, hero.getLevel() + 1);
        if (expConfig == null) {
            player.sendErrorCode(Cmd.CMD.CS_HeroLevelUp, ErrorMsg.ErrorCode.LEVEL_MAX);
            return;
        }

        long expItemCount = request.getExpItemIdsCount();
        if (expItemCount <= 0) {
            player.sendErrorCode(Cmd.CMD.CS_HeroLevelUp, ErrorMsg.ErrorCode.RESOURCE_NOT_ENOUGH);
            return;
        }

        long nextExp = Math.addExact(hero.getExp(), expItemCount);
        Consume consume = upgradeCost(expConfig).addThing(ResourceType.EXP_ITEM, expItemCount);
        if (!consume.isEnoughAndCost(player, ChangeReason.HERO_LEVEL_UP)) {
            player.sendErrorCode(Cmd.CMD.CS_HeroLevelUp, ErrorMsg.ErrorCode.RESOURCE_NOT_ENOUGH);
            return;
        }
        hero.setExp(nextExp);

        // 升级逻辑
        while (true) {
            HeroExpConfig nextLevelConfig =
                    HeroExpConfigManager.getInstance()
                            .getByModelIdAndLevel(heroConfig.expModelId, hero.getLevel() + 1);
            if (nextLevelConfig == null) {
                break; // 已达最高等级
            }
            if (hero.getExp() < nextLevelConfig.exp) {
                break; // 经验不足升级
            }
            hero.setExp(hero.getExp() - nextLevelConfig.exp);
            hero.setLevel(hero.getLevel() + 1);
        }

        // 返回结果
        Hero.SC_HeroLevelUp.Builder builder = Hero.SC_HeroLevelUp.newBuilder();
        builder.setResult(ErrorMsg.ErrorCode.Ok);
        builder.setHeroInfo(buildHeroInfo(hero));
        player.sendMsg(Cmd.CMD.SC_HeroLevelUp, builder.build());
    }

    /** 处理英雄升星 */
    public void handleHeroStarUp(GameHandlerContext context, Hero.CS_HeroStarUp request) {
        Player player = context.player();
        HeroModule heroModule =
                (HeroModule) player.getPlayerData().getModule(ModuleEnum.HERO_MODULE);
        ResourceModule resourceModule =
                (ResourceModule) player.getPlayerData().getModule(ModuleEnum.RESOURCE_MODULE);

        // 验证英雄存在
        PlayerHeroEntry hero = heroModule.getHero(request.getHeroUid());
        if (hero == null) {
            player.sendErrorCode(Cmd.CMD.CS_HeroStarUp, ErrorMsg.ErrorCode.HERO_NOT_FOUND);
            return;
        }

        // 获取英雄配置
        HeroInfoConfig heroConfig =
                HeroInfoConfigManager.getInstance().getConfigMap().get(hero.getHeroId());
        if (heroConfig == null) {
            player.sendErrorCode(Cmd.CMD.CS_HeroStarUp, ErrorMsg.ErrorCode.HERO_NOT_FOUND);
            return;
        }

        // 获取升星配置
        HeroStarConfig starConfig =
                HeroStarConfigManager.getInstance()
                        .getByModelIdAndStar(heroConfig.starModelId, hero.getStar() + 1);
        if (starConfig == null) {
            player.sendErrorCode(Cmd.CMD.CS_HeroStarUp, ErrorMsg.ErrorCode.STAR_MAX);
            return;
        }

        long starCost = parseStarCurrencyCost(starConfig.currencyNum);
        Consume consume = Consume.create();
        if (starConfig.currencyType > 0) consume.addThing(starConfig.currencyType, starCost);
        if (!consume.isEnoughAndCost(player, ChangeReason.HERO_STAR_UP)) {
            player.sendErrorCode(Cmd.CMD.CS_HeroStarUp, ErrorMsg.ErrorCode.RESOURCE_NOT_ENOUGH);
            return;
        }

        // 更新星级
        hero.setStar(hero.getStar() + 1);

        // 返回结果
        Hero.SC_HeroStarUp.Builder builder = Hero.SC_HeroStarUp.newBuilder();
        builder.setResult(ErrorMsg.ErrorCode.Ok);
        builder.setHeroInfo(buildHeroInfo(hero));
        player.sendMsg(Cmd.CMD.SC_HeroStarUp, builder.build());
    }

    /** 处理英雄觉醒 */
    public void handleHeroAwaken(GameHandlerContext context, Hero.CS_HeroAwaken request) {
        Player player = context.player();
        HeroModule heroModule =
                (HeroModule) player.getPlayerData().getModule(ModuleEnum.HERO_MODULE);
        ResourceModule resourceModule =
                (ResourceModule) player.getPlayerData().getModule(ModuleEnum.RESOURCE_MODULE);

        // 验证英雄存在
        PlayerHeroEntry hero = heroModule.getHero(request.getHeroUid());
        if (hero == null) {
            player.sendErrorCode(Cmd.CMD.CS_HeroAwaken, ErrorMsg.ErrorCode.HERO_NOT_FOUND);
            return;
        }

        // 获取英雄配置
        HeroInfoConfig heroConfig =
                HeroInfoConfigManager.getInstance().getConfigMap().get(hero.getHeroId());
        if (heroConfig == null) {
            player.sendErrorCode(Cmd.CMD.CS_HeroAwaken, ErrorMsg.ErrorCode.HERO_NOT_FOUND);
            return;
        }

        // 获取觉醒配置
        HeroAwakenConfig awakenConfig =
                HeroAwakenConfigManager.getInstance()
                        .getByHeroAwakenDataAndLevel(
                                heroConfig.heroAwakenData, hero.getAwaken() + 1);
        if (awakenConfig == null) {
            player.sendErrorCode(Cmd.CMD.CS_HeroAwaken, ErrorMsg.ErrorCode.AWAKEN_MAX);
            return;
        }

        Consume consume = Consume.create();
        if (awakenConfig.awakenCurrencyType > 0)
            consume.addThing(awakenConfig.awakenCurrencyType, awakenConfig.awakenCurrencyNum);
        if (awakenConfig.awakenItem != null && !awakenConfig.awakenItem.isBlank()) {
            String[] item = awakenConfig.awakenItem.split(",", -1);
            if (item.length != 2) throw new IllegalArgumentException("觉醒消耗配置格式错误");
            consume.addThing(Integer.parseInt(item[0].trim()), Long.parseLong(item[1].trim()));
        }
        if (!consume.isEnoughAndCost(player, ChangeReason.HERO_AWAKEN)) {
            player.sendErrorCode(Cmd.CMD.CS_HeroAwaken, ErrorMsg.ErrorCode.RESOURCE_NOT_ENOUGH);
            return;
        }

        // 更新觉醒等级
        hero.setAwaken(hero.getAwaken() + 1);

        // 返回结果
        Hero.SC_HeroAwaken.Builder builder = Hero.SC_HeroAwaken.newBuilder();
        builder.setResult(ErrorMsg.ErrorCode.Ok);
        builder.setHeroInfo(buildHeroInfo(hero));
        player.sendMsg(Cmd.CMD.SC_HeroAwaken, builder.build());
    }

    /** 处理添加英雄（GM命令等） */
    public void handleHeroAdd(GameHandlerContext context, Hero.CS_HeroAdd request) {
        Player player = context.player();
        HeroModule heroModule =
                (HeroModule) player.getPlayerData().getModule(ModuleEnum.HERO_MODULE);

        Hero.SC_HeroAdd.Builder builder = Hero.SC_HeroAdd.newBuilder();

        if (heroModule.hasHero(request.getHeroId())) {
            player.sendErrorCode(Cmd.CMD.CS_HeroAdd, ErrorMsg.ErrorCode.HERO_ALREADY_EXISTS);
            return;
        }

        // 当前规则每种英雄只能拥有一份，拒绝批量请求，避免部分添加后才报错。
        if (request.getCount() != 1
                || !heroModule.canAddHeroes(java.util.List.of(request.getHeroId()))) {
            player.sendErrorCode(Cmd.CMD.CS_HeroAdd, ErrorMsg.ErrorCode.HERO_SLOT_FULL);
            return;
        }
        for (int i = 0; i < request.getCount(); i++) {
            PlayerHeroEntry hero = heroModule.addHero(request.getHeroId());
            if (hero == null) {
                player.sendErrorCode(Cmd.CMD.CS_HeroAdd, ErrorMsg.ErrorCode.HERO_SLOT_FULL);
                return;
            }
            builder.addHeroList(buildHeroInfo(hero));
        }

        builder.setResult(ErrorMsg.ErrorCode.Ok);
        player.sendMsg(Cmd.CMD.SC_HeroAdd, builder.build());
    }

    /** 构建英雄信息 */
    private Hero.HeroInfo buildHeroInfo(PlayerHeroEntry entry) {
        Hero.HeroInfo.Builder builder = Hero.HeroInfo.newBuilder();
        builder.setHeroUid(entry.getId());
        builder.setHeroId(entry.getHeroId());
        builder.setLevel(entry.getLevel());
        builder.setStar(entry.getStar());
        builder.setAwaken(entry.getAwaken());
        builder.setExp(entry.getExp());

        // 计算升级所需经验
        long maxExp = calcMaxExp(entry.getHeroId(), entry.getLevel());
        builder.setMaxExp(maxExp);

        return builder.build();
    }

    private Consume upgradeCost(HeroExpConfig config) {
        Consume consume = Consume.create();
        if (config.currencyType > 0)
            consume.addThing(config.currencyType, parseStarCurrencyCost(config.currencyNum));
        for (KV<Integer, Integer> item : parseCostItems(config.item))
            consume.addThing(item.getKey(), item.getValue());
        return consume;
    }

    /** 解析升星消耗（currencyNum 格式：slotLevel,cost|slotLevel,cost|...） */
    private long parseStarCurrencyCost(String currencyNum) {
        if (currencyNum == null || currencyNum.trim().isEmpty()) {
            return 0;
        }
        try {
            String[] pairs = currencyNum.split("\\|");
            if (pairs.length > 0) {
                String[] kv = pairs[0].split(",");
                if (kv.length >= 2) {
                    return Long.parseLong(kv[1].trim());
                }
            }
        } catch (NumberFormatException error) {
            throw new IllegalArgumentException("消耗数量配置错误", error);
        }
        throw new IllegalArgumentException("消耗配置格式错误: " + currencyNum);
    }

    private List<KV<Integer, Integer>> parseCostItems(String costText) {
        List<KV<Integer, Integer>> result = new ArrayList<>();
        if (costText == null || costText.isBlank() || costText.trim().equals("0")) return result;
        boolean colonFormat = costText.contains(":");
        for (String item : costText.split(colonFormat ? "," : "\\|", -1)) {
            String[] pair = item.split(colonFormat ? ":" : ",", -1);
            if (pair.length != 2) throw new IllegalArgumentException("消耗道具配置格式错误: " + costText);
            int id = Integer.parseInt(pair[0].trim());
            int amount = Integer.parseInt(pair[1].trim());
            if (id == 0 && amount == 0) continue;
            if (id <= 0 || amount < 0) throw new IllegalArgumentException("无效消耗道具 ID 或数量");
            result.add(new KV<>(id, amount));
        }
        return result;
    }

    /** 计算升级所需经验 */
    private long calcMaxExp(int heroId, int level) {
        HeroInfoConfig heroConfig = HeroInfoConfigManager.getInstance().getConfigMap().get(heroId);
        if (heroConfig == null) {
            return 0;
        }

        HeroExpConfig expConfig =
                HeroExpConfigManager.getInstance()
                        .getByModelIdAndLevel(heroConfig.expModelId, level + 1);
        if (expConfig == null) {
            return 0; // 已达最高等级
        }

        return expConfig.exp;
    }
}
