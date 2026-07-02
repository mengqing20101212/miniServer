package ly.bot.module;

import ly.LoggerDef;
import ly.bot.session.RobotSession;
import ly.net.NetClient;
import org.slf4j.Logger;

import java.util.List;

/**
 * ModuleManager 的核心定义，承载所在包对应的业务模型或辅助逻辑。
 */
public class ModuleManager {
    private static final Logger logger = LoggerDef.SystemLogger;
    
    private final List<RobotModule> modules;
    private RobotModule currentModule;
    private final RobotSession session;
    private final NetClient client;
    private int nextModuleIndex;
    
    public ModuleManager(List<RobotModule> modules, RobotSession session, NetClient client) {
        this.modules = modules;
        this.session = session;
        this.client = client;
        this.currentModule = null;
        this.nextModuleIndex = 0;
    }
    
    /**
     * 执行一个行为步骤
     */
    public void executeStep() {
        // 如果当前模块已完成或不存在，则选择一个新模块
        if (currentModule == null || currentModule.isCompleted()) {
            selectNextModule();
        }
        
        // 执行当前模块的步骤
        if (currentModule != null) {
            try {
                boolean completed = currentModule.executeStep(client, session);
                if (completed) {
                    logger.debug("模块 {} 已完成", currentModule.getName());
                    currentModule.reset(); // 重置模块以备下次使用
                    selectNextModule(); // 选择下一个模块
                }
            } catch (Exception e) {
                logger.error("执行模块 {} 时出错", currentModule.getName(), e);
                selectNextModule(); // 出错时选择下一个模块
            }
        }
    }
    
    /**
     * 随机选择下一个模块
     */
    private void selectNextModule() {
        if (!modules.isEmpty()) {
            // 按注册顺序轮询模块，测试时可以稳定复现完整协议链路。
            currentModule = modules.get(nextModuleIndex);
            nextModuleIndex = (nextModuleIndex + 1) % modules.size();
            logger.debug("选择了新模块: {}", currentModule.getName());
        }
    }
    
    /**
     * 获取当前模块
     */
    public RobotModule getCurrentModule() {
        return currentModule;
    }
    
    /**
     * 设置当前模块
     */
    public void setCurrentModule(RobotModule module) {
        this.currentModule = module;
    }
    
    /**
     * 获取所有模块
     */
    public List<RobotModule> getModules() {
        return modules;
    }
}
