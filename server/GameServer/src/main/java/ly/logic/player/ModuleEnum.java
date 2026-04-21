package ly.logic.player;

import ly.logic.login.PlayerLogicModule;

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
