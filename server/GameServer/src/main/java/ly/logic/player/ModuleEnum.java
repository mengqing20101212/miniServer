package ly.logic.player;

import ly.logic.login.PlayerLogicModule;

/**
 * ModuleEnum 的核心定义，承载所在包对应的业务模型或辅助逻辑。
 */
public enum ModuleEnum {
    PLAYER_LOGIC_MODULE(new PlayerLogicModule()),
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
