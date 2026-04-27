package ly.logic.hero;

import java.util.ArrayList;
import java.util.List;

import ly.config.HeroAwakenConfig;
import ly.config.HeroAwakenConfigManager;
import ly.config.HeroExpConfig;
import ly.config.HeroExpConfigManager;
import ly.config.HeroInfoConfig;
import ly.config.HeroInfoConfigManager;
import ly.config.HeroStarConfig;
import ly.config.HeroStarConfigManager;
import ly.config.ResourceType;
import ly.logic.hero.module.HeroBean;
import ly.logic.hero.module.HeroModule;
import ly.logic.player.ModuleEnum;
import ly.logic.player.Player;
import ly.logic.resource.module.ResourceModule;
import ly.net.GameHandlerContext;
import ly.net.IGameController;
import ly.proto.Cmd;
import ly.proto.ErrorMsg;
import ly.proto.Hero;
import ly.utils.ExcelKVParser;
import ly.utils.KV;

/**
 * 英雄消息控制器
 */
public class HeroController implements IGameController {

    @Override
    public void registerHandlerRouter() {
        gameHandlerRegister(Cmd.CMD.CS_HeroList, this::handleHeroList);
        gameHandlerRegister(Cmd.CMD.CS_HeroLevelUp, this::handleHeroLevelUp);
        gameHandlerRegister(Cmd.CMD.CS_HeroStarUp, this::handleHeroStarUp);
        gameHandlerRegister(Cmd.CMD.CS_HeroAwaken, this::handleHeroAwaken);
        gameHandlerRegister(Cmd.CMD.CS_HeroAdd, this::handleHeroAdd);
    }

    /**
     * 处理获取英雄列表
     */
    public void handleHeroList(GameHandlerContext context, Hero.CS_HeroList request) {
        Player player = context.player();
        HeroModule heroModule = (HeroModule) player.getPlayerData().getModule(ModuleEnum.HERO_MODULE);
        List<HeroBean> heroList = heroModule.getHeroList();

        Hero.SC_HeroList.Builder builder = Hero.SC_HeroList.newBuilder();
        for (HeroBean entry : heroList) {
            Hero.HeroInfo info = buildHeroInfo(entry);
            builder.addHeroList(info);
        }

        player.sendMsg(Cmd.CMD.SC_HeroList, builder.build());
    }

    /**
     * 处理英雄升级
     */
    public void handleHeroLevelUp(GameHandlerContext context, Hero.CS_HeroLevelUp request) {
        Player player = context.player();
        HeroModule heroModule = (HeroModule) player.getPlayerData().getModule(ModuleEnum.HERO_MODULE);
        ResourceModule resourceModule = (ResourceModule) player.getPlayerData().getModule(ModuleEnum.RESOURCE_MODULE);

        // 验证英雄存在
        HeroBean hero = heroModule.getHero(request.getHeroUid());
        if (hero == null) {
            player.sendErrorCode(Cmd.CMD.CS_HeroLevelUp, ErrorMsg.ErrorCode.HERO_NOT_FOUND);
            return;
        }

        // 获取英雄配置
        HeroInfoConfig heroConfig = HeroInfoConfigManager.getInstance().getConfigMap().get(hero.heroId);
        if (heroConfig == null) {
            player.sendErrorCode(Cmd.CMD.CS_HeroLevelUp, ErrorMsg.ErrorCode.HERO_NOT_FOUND);
            return;
        }

        // 获取升级经验配置
        HeroExpConfig expConfig = HeroExpConfigManager.getInstance().getByModelIdAndLevel(heroConfig.expModelId, hero.level + 1);
        if (expConfig == null) {
            player.sendErrorCode(Cmd.CMD.CS_HeroLevelUp, ErrorMsg.ErrorCode.LEVEL_MAX);
            return;
        }

        long expItemCount = request.getExpItemIdsCount();
        if (expItemCount <= 0) {
            player.sendErrorCode(Cmd.CMD.CS_HeroLevelUp, ErrorMsg.ErrorCode.RESOURCE_NOT_ENOUGH);
            return;
        }

        if (resourceModule.getResource(ResourceType.EXP_ITEM) < expItemCount) {
            player.sendErrorCode(Cmd.CMD.CS_HeroLevelUp, ErrorMsg.ErrorCode.RESOURCE_NOT_ENOUGH);
            return;
        }

        // 扣除经验道具
        resourceModule.deductResource(ResourceType.EXP_ITEM, expItemCount);

        // 扣除升级所需的额外消费
        if (!canAffordUpgrade(resourceModule, expConfig)) {
            player.sendErrorCode(Cmd.CMD.CS_HeroLevelUp, ErrorMsg.ErrorCode.RESOURCE_NOT_ENOUGH);
            return;
        }
        deductUpgradeCost(resourceModule, expConfig);

        // 更新英雄经验
        hero.exp += expItemCount;

        // 升级逻辑
        while (true) {
            HeroExpConfig nextLevelConfig = HeroExpConfigManager.getInstance().getByModelIdAndLevel(heroConfig.expModelId, hero.level + 1);
            if (nextLevelConfig == null) {
                break; // 已达最高等级
            }
            if (hero.exp < nextLevelConfig.exp) {
                break; // 经验不足升级
            }
            hero.exp -= nextLevelConfig.exp;
            hero.level++;
        }

        // 保存数据
        heroModule.saveData();
        resourceModule.saveData();

        // 返回结果
        Hero.SC_HeroLevelUp.Builder builder = Hero.SC_HeroLevelUp.newBuilder();
        builder.setResult(ErrorMsg.ErrorCode.Ok);
        builder.setHeroInfo(buildHeroInfo(hero));
        player.sendMsg(Cmd.CMD.SC_HeroLevelUp, builder.build());
    }

    /**
     * 处理英雄升星
     */
    public void handleHeroStarUp(GameHandlerContext context, Hero.CS_HeroStarUp request) {
        Player player = context.player();
        HeroModule heroModule = (HeroModule) player.getPlayerData().getModule(ModuleEnum.HERO_MODULE);
        ResourceModule resourceModule = (ResourceModule) player.getPlayerData().getModule(ModuleEnum.RESOURCE_MODULE);

        // 验证英雄存在
        HeroBean hero = heroModule.getHero(request.getHeroUid());
        if (hero == null) {
            player.sendErrorCode(Cmd.CMD.CS_HeroStarUp, ErrorMsg.ErrorCode.HERO_NOT_FOUND);
            return;
        }

        // 获取英雄配置
        HeroInfoConfig heroConfig = HeroInfoConfigManager.getInstance().getConfigMap().get(hero.heroId);
        if (heroConfig == null) {
            player.sendErrorCode(Cmd.CMD.CS_HeroStarUp, ErrorMsg.ErrorCode.HERO_NOT_FOUND);
            return;
        }

        // 获取升星配置
        HeroStarConfig starConfig = HeroStarConfigManager.getInstance().getByModelIdAndStar(heroConfig.starModelId, hero.star + 1);
        if (starConfig == null) {
            player.sendErrorCode(Cmd.CMD.CS_HeroStarUp, ErrorMsg.ErrorCode.STAR_MAX);
            return;
        }

        // 检查货币是否充足（currencyNum 格式：slotLevel,cost|...）
        long starCost = parseStarCurrencyCost(starConfig.currencyNum);
        if (starConfig.currencyType > 0 && resourceModule.getResource(starConfig.currencyType) < starCost) {
            player.sendErrorCode(Cmd.CMD.CS_HeroStarUp, ErrorMsg.ErrorCode.RESOURCE_NOT_ENOUGH);
            return;
        }

        // 扣除资源
        if (starCost > 0 && starConfig.currencyType > 0) {
            resourceModule.deductResource(starConfig.currencyType, starCost);
        }

        // 更新星级
        hero.star++;

        // 保存数据
        heroModule.saveData();
        resourceModule.saveData();

        // 返回结果
        Hero.SC_HeroStarUp.Builder builder = Hero.SC_HeroStarUp.newBuilder();
        builder.setResult(ErrorMsg.ErrorCode.Ok);
        builder.setHeroInfo(buildHeroInfo(hero));
        player.sendMsg(Cmd.CMD.SC_HeroStarUp, builder.build());
    }

    /**
     * 处理英雄觉醒
     */
    public void handleHeroAwaken(GameHandlerContext context, Hero.CS_HeroAwaken request) {
        Player player = context.player();
        HeroModule heroModule = (HeroModule) player.getPlayerData().getModule(ModuleEnum.HERO_MODULE);
        ResourceModule resourceModule = (ResourceModule) player.getPlayerData().getModule(ModuleEnum.RESOURCE_MODULE);

        // 验证英雄存在
        HeroBean hero = heroModule.getHero(request.getHeroUid());
        if (hero == null) {
            player.sendErrorCode(Cmd.CMD.CS_HeroAwaken, ErrorMsg.ErrorCode.HERO_NOT_FOUND);
            return;
        }

        // 获取英雄配置
        HeroInfoConfig heroConfig = HeroInfoConfigManager.getInstance().getConfigMap().get(hero.heroId);
        if (heroConfig == null) {
            player.sendErrorCode(Cmd.CMD.CS_HeroAwaken, ErrorMsg.ErrorCode.HERO_NOT_FOUND);
            return;
        }

        // 获取觉醒配置
        HeroAwakenConfig awakenConfig = HeroAwakenConfigManager.getInstance().getByHeroAwakenDataAndLevel(heroConfig.heroAwakenData, hero.awaken + 1);
        if (awakenConfig == null) {
            player.sendErrorCode(Cmd.CMD.CS_HeroAwaken, ErrorMsg.ErrorCode.AWAKEN_MAX);
            return;
        }

        // 检查觉醒条件——新配置表中无星级要求字段，跳过星级检查
        // 原 heroStar 字段在重新生成后不再存在，觉醒条件由 awakenItem/awakenCurrency 约束

        // 检查觉醒材料是否充足
        if (!canAffordAwaken(resourceModule, awakenConfig)) {
            player.sendErrorCode(Cmd.CMD.CS_HeroAwaken, ErrorMsg.ErrorCode.RESOURCE_NOT_ENOUGH);
            return;
        }

        deductAwakenCost(resourceModule, awakenConfig);

        // 更新觉醒等级
        hero.awaken++;

        // 保存数据
        heroModule.saveData();
        resourceModule.saveData();

        // 返回结果
        Hero.SC_HeroAwaken.Builder builder = Hero.SC_HeroAwaken.newBuilder();
        builder.setResult(ErrorMsg.ErrorCode.Ok);
        builder.setHeroInfo(buildHeroInfo(hero));
        player.sendMsg(Cmd.CMD.SC_HeroAwaken, builder.build());
    }

    /**
     * 处理添加英雄（GM命令等）
     */
    public void handleHeroAdd(GameHandlerContext context, Hero.CS_HeroAdd request) {
        Player player = context.player();
        HeroModule heroModule = (HeroModule) player.getPlayerData().getModule(ModuleEnum.HERO_MODULE);

        Hero.SC_HeroAdd.Builder builder = Hero.SC_HeroAdd.newBuilder();

        if (heroModule.getHero(player.getPlayerId() * 1000000L + request.getHeroId()) != null) {
            player.sendErrorCode(Cmd.CMD.CS_HeroAdd, ErrorMsg.ErrorCode.HERO_ALREADY_EXISTS);
            return;
        }

        for (int i = 0; i < request.getCount(); i++) {
            HeroBean hero = heroModule.addHero(request.getHeroId());
            if (hero == null) {
                player.sendErrorCode(Cmd.CMD.CS_HeroAdd, ErrorMsg.ErrorCode.HERO_SLOT_FULL);
                return;
            }
            builder.addHeroList(buildHeroInfo(hero));
        }

        heroModule.saveData();

        builder.setResult(ErrorMsg.ErrorCode.Ok);
        player.sendMsg(Cmd.CMD.SC_HeroAdd, builder.build());
    }

    /**
     * 构建英雄信息
     */
    private Hero.HeroInfo buildHeroInfo(HeroBean entry) {
        Hero.HeroInfo.Builder builder = Hero.HeroInfo.newBuilder();
        builder.setHeroUid(entry.heroUid);
        builder.setHeroId(entry.heroId);
        builder.setLevel(entry.level);
        builder.setStar(entry.star);
        builder.setAwaken(entry.awaken);
        builder.setExp(entry.exp);

        // 计算升级所需经验
        long maxExp = calcMaxExp(entry.heroId, entry.level);
        builder.setMaxExp(maxExp);

        return builder.build();
    }

    private boolean canAffordUpgrade(ResourceModule resourceModule, HeroExpConfig expConfig) {
        if (expConfig.currencyType > 0) {
            long currencyAmount = parseStarCurrencyCost(expConfig.currencyNum);
            if (currencyAmount > 0 && resourceModule.getResource(expConfig.currencyType) < currencyAmount) {
                return false;
            }
        }
        for (KV<Integer, Integer> item : parseCostItems(expConfig.item)) {
            if (resourceModule.getResource(item.getKey()) < item.getValue()) {
                return false;
            }
        }
        return true;
    }

    private void deductUpgradeCost(ResourceModule resourceModule, HeroExpConfig expConfig) {
        if (expConfig.currencyType > 0) {
            long currencyAmount = parseStarCurrencyCost(expConfig.currencyNum);
            if (currencyAmount > 0) {
                resourceModule.deductResource(expConfig.currencyType, currencyAmount);
            }
        }
        for (KV<Integer, Integer> item : parseCostItems(expConfig.item)) {
            resourceModule.deductResource(item.getKey(), item.getValue());
        }
    }

    private boolean canAffordAwaken(ResourceModule resourceModule, HeroAwakenConfig awakenConfig) {
        // 检查觉醒货币（awakenCurrencyType/awakenCurrencyNum）
        if (awakenConfig.awakenCurrencyType > 0 && awakenConfig.awakenCurrencyNum > 0) {
            if (resourceModule.getResource(awakenConfig.awakenCurrencyType) < awakenConfig.awakenCurrencyNum) {
                return false;
            }
        }
        // 检查觉醒材料（awakenItem 格式：itemId,count）
        if (awakenConfig.awakenItem != null && !awakenConfig.awakenItem.trim().isEmpty()) {
            String[] parts = awakenConfig.awakenItem.split(",");
            if (parts.length >= 2) {
                try {
                    int itemId = Integer.parseInt(parts[0].trim());
                    int count = Integer.parseInt(parts[1].trim());
                    if (itemId > 0 && count > 0 && resourceModule.getResource(itemId) < count) {
                        return false;
                    }
                } catch (NumberFormatException ignored) {}
            }
        }
        return true;
    }

    private void deductAwakenCost(ResourceModule resourceModule, HeroAwakenConfig awakenConfig) {
        // 扣除觉醒货币
        if (awakenConfig.awakenCurrencyType > 0 && awakenConfig.awakenCurrencyNum > 0) {
            resourceModule.deductResource(awakenConfig.awakenCurrencyType, awakenConfig.awakenCurrencyNum);
        }
        // 扣除觉醒材料
        if (awakenConfig.awakenItem != null && !awakenConfig.awakenItem.trim().isEmpty()) {
            String[] parts = awakenConfig.awakenItem.split(",");
            if (parts.length >= 2) {
                try {
                    int itemId = Integer.parseInt(parts[0].trim());
                    int count = Integer.parseInt(parts[1].trim());
                    if (itemId > 0 && count > 0) {
                        resourceModule.deductResource(itemId, count);
                    }
                } catch (NumberFormatException ignored) {}
            }
        }
    }

    /**
     * 解析升星消耗（currencyNum 格式：slotLevel,cost|slotLevel,cost|...）
     */
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
        } catch (NumberFormatException ignored) {}
        return 0;
    }

    private long parseLong(String value) {
        if (value == null || value.trim().isEmpty()) {
            return 0;
        }
        try {
            return Long.parseLong(value.trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private List<KV<Integer, Integer>> parseCostItems(String costText) {
        List<KV<Integer, Integer>> result = new ArrayList<>();
        if (costText == null || costText.trim().isEmpty()) {
            return result;
        }
        List<KV<String, String>> parsed = ExcelKVParser.parseSimpleKV(costText);
        for (KV<String, String> kv : parsed) {
            try {
                int resourceType = Integer.parseInt(kv.getKey().trim());
                int amount = Integer.parseInt(kv.getValue().trim());
                if (resourceType > 0 && amount > 0) {
                    result.add(new KV<>(resourceType, amount));
                }
            } catch (NumberFormatException ignored) {
            }
        }
        return result;
    }

    /**
     * 计算升级所需经验
     */
    private long calcMaxExp(int heroId, int level) {
        HeroInfoConfig heroConfig = HeroInfoConfigManager.getInstance().getConfigMap().get(heroId);
        if (heroConfig == null) {
            return 0;
        }

        HeroExpConfig expConfig = HeroExpConfigManager.getInstance().getByModelIdAndLevel(heroConfig.expModelId, level + 1);
        if (expConfig == null) {
            return 0; // 已达最高等级
        }

        return expConfig.exp;
    }
}
