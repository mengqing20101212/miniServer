package ly.logic.player;

import ly.logic.login.PlayerLogicModule;
import ly.logic.hero.module.HeroModule;
import ly.logic.resource.module.ResourceModule;

/**
 * ModuleEnum 的核心定义，承载所在包对应的业务模型或辅助逻辑。
 */
public enum ModuleEnum {
    PLAYER_LOGIC_MODULE(new PlayerLogicModule()),
    HERO_MODULE(new HeroModule()),
    RESOURCE_MODULE(new ResourceModule()),
    ;
    private AbstractModule module;

    ModuleEnum(AbstractModule module) {
        this.module = module;
    }

    public AbstractModule getModule() {
        return module;
    }

    public String getName() {
        return this.module.getClass().getName();
    }
}
