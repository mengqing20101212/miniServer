package ly.logic.resource.module;

import com.baidu.bjf.remoting.protobuf.Codec;
import com.baidu.bjf.remoting.protobuf.ProtobufProxy;
import ly.config.ResourceType;
import ly.logic.player.AbstractModule;
import ly.logic.player.ModuleEnum;
import ly.proto.Resource;

import ly.logic.player.event.PlayerEventParam;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ly.logic.player.event.PlayerEventType;

/**
 * 资源模块
 */
public class ResourceModule extends AbstractModule {
    private ResourceModuleData moduleData;

    @Override
    public void onLoadData() {
        byte[] data = player.getPlayerData().getModuleData(ModuleEnum.RESOURCE_MODULE);
        if (data != null && data.length > 0) {
            try {
                Codec<ResourceModuleData> codec = ProtobufProxy.create(ResourceModuleData.class);
                moduleData = codec.decode(data);
            } catch (Exception e) {
                System.err.println("Error loading ResourceModuleData for player " + player.getPlayerId() + ": " + e.getMessage());
                moduleData = new ResourceModuleData();
                // 初始化默认资源
                initDefaultResources();
            }
        } else {
            moduleData = new ResourceModuleData();
            // 初始化默认资源
            initDefaultResources();
        }
    }

    private void initDefaultResources() {
        moduleData.resources.put(ResourceType.GOLD, 0L);
        moduleData.resources.put(ResourceType.DIAMOND, 0L);
        moduleData.resources.put(ResourceType.HERO_DEBRIS, 0L);
        moduleData.resources.put(ResourceType.EXP_ITEM, 0L);
        moduleData.resources.put(ResourceType.AWAKEN_ITEM, 0L);
    }

    @Override
    public boolean saveData() {
        return saveModuleData(ModuleEnum.RESOURCE_MODULE, moduleData);
    }

    @Override
    public void onOpenFunction() {
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
     * 增加资源
     */
    public boolean addResource(int resourceType, long amount) {
        if (amount <= 0) return false;
        long current = moduleData.resources.getOrDefault(resourceType, 0L);
        moduleData.resources.put(resourceType, current + amount);
        notifyResourceChange(resourceType, amount);
        return true;
    }

    /**
     * 扣除资源
     */
    public boolean deductResource(int resourceType, long amount) {
        if (amount <= 0) return false;
        long current = moduleData.resources.getOrDefault(resourceType, 0L);
        if (current < amount) {
            return false;
        }
        moduleData.resources.put(resourceType, current - amount);
        notifyResourceChange(resourceType, -amount);
        return true;
    }

    /**
     * 获取资源数量
     */
    public long getResource(int resourceType) {
        return moduleData.resources.getOrDefault(resourceType, 0L);
    }

    /**
     * 通知资源变化
     */
    private void notifyResourceChange(int resourceType, long amount) {
        try {
            // 发送 SC_ResourceChange 消息
            Resource.SC_ResourceChange.Builder builder = Resource.SC_ResourceChange.newBuilder();
            builder.putChanges(resourceType, amount);
            player.sendMsg(ly.proto.Cmd.CMD.SC_ResourceChange, builder.build());
        } catch (NoClassDefFoundError | Exception e) {
            // protobuf 版本兼容性兜底：当前运行时 protobuf 版本可能不支持 Map 反射
            System.err.println("WARN: notifyResourceChange failed (protobuf compat): " + e.getMessage());
        }
    }
}
