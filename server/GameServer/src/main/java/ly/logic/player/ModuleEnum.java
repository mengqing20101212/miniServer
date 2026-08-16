package ly.logic.player;

import ly.logic.login.PlayerLogicModule;
import ly.logic.hero.module.HeroModule;
import ly.logic.resource.module.ResourceModule;

/**
 * ModuleEnum 的核心定义，承载所在包对应的业务模型或辅助逻辑。
 */
public enum ModuleEnum {
    PLAYER_LOGIC_MODULE(1, 1, new PlayerLogicModule()),
    HERO_MODULE(2, 1, new HeroModule()),
    RESOURCE_MODULE(3, 1, new ResourceModule()),
    ;
    private final int moduleId;
    private final int dataVersion;
    private final AbstractModule module;

    ModuleEnum(int moduleId, int dataVersion, AbstractModule module) {
        this.moduleId = moduleId;
        this.dataVersion = dataVersion;
        this.module = module;
    }

    public int getModuleId() {
        return moduleId;
    }

    public int getDataVersion() {
        return dataVersion;
    }

    public AbstractModule getModule() {
        return module;
    }

    public String getName() {
        return this.module.getClass().getName();
    }

    public static ModuleEnum fromModuleId(int moduleId) {
        for (ModuleEnum module : values()) {
            if (module.moduleId == moduleId) {
                return module;
            }
        }
        return null;
    }
}
