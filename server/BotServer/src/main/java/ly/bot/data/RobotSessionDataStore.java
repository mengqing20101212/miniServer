package ly.bot.data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 机器人运行时数据存储，提供模块隔离的键值状态保存能力。
 */
public class RobotSessionDataStore {
    
    private final Map<String, Object> dataStore = new ConcurrentHashMap<>();
    
    /**
     * 存储数据
     * @param key 键
     * @param value 值
     */
    public void put(String key, Object value) {
        dataStore.put(key, value);
    }
    
    /**
     * 存储模块特定的数据
     * @param moduleKey 模块标识
     * @param dataKey 数据键
     * @param value 数据值
     */
    public void put(String moduleKey, String dataKey, Object value) {
        String fullKey = moduleKey + "." + dataKey;
        dataStore.put(fullKey, value);
    }
    
    /**
     * 获取数据
     * @param key 键
     * @return 值
     */
    public Object get(String key) {
        return dataStore.get(key);
    }
    
    /**
     * 获取模块特定的数据
     * @param moduleKey 模块标识
     * @param dataKey 数据键
     * @return 数据值
     */
    public Object get(String moduleKey, String dataKey) {
        String fullKey = moduleKey + "." + dataKey;
        return dataStore.get(fullKey);
    }
    
    /**
     * 获取数据并转换为指定类型
     * @param key 键
     * @param clazz 类型
     * @return 转换后的值
     */
    @SuppressWarnings("unchecked")
    public <T> T getAs(String key, Class<T> clazz) {
        Object value = dataStore.get(key);
        if (value != null && clazz.isInstance(value)) {
            return (T) value;
        }
        return null;
    }
    
    /**
     * 获取模块特定的数据并转换为指定类型
     * @param moduleKey 模块标识
     * @param dataKey 数据键
     * @param clazz 类型
     * @return 转换后的值
     */
    @SuppressWarnings("unchecked")
    public <T> T getAs(String moduleKey, String dataKey, Class<T> clazz) {
        String fullKey = moduleKey + "." + dataKey;
        Object value = dataStore.get(fullKey);
        if (value != null && clazz.isInstance(value)) {
            return (T) value;
        }
        return null;
    }
    
    /**
     * 删除数据
     * @param key 键
     */
    public void remove(String key) {
        dataStore.remove(key);
    }
    
    /**
     * 删除模块特定的数据
     * @param moduleKey 模块标识
     * @param dataKey 数据键
     */
    public void remove(String moduleKey, String dataKey) {
        String fullKey = moduleKey + "." + dataKey;
        dataStore.remove(fullKey);
    }
    
    /**
     * 检查是否存在指定键
     * @param key 键
     * @return 是否存在
     */
    public boolean containsKey(String key) {
        return dataStore.containsKey(key);
    }
    
    /**
     * 检查是否存在模块特定的键
     * @param moduleKey 模块标识
     * @param dataKey 数据键
     * @return 是否存在
     */
    public boolean containsKey(String moduleKey, String dataKey) {
        String fullKey = moduleKey + "." + dataKey;
        return dataStore.containsKey(fullKey);
    }
    
    /**
     * 获取所有数据
     * @return 所有数据的映射
     */
    public Map<String, Object> getAll() {
        return new ConcurrentHashMap<>(dataStore);
    }
    
    /**
     * 获取特定模块的所有数据
     * @param moduleKey 模块标识
     * @return 特定模块的数据
     */
    public Map<String, Object> getModuleData(String moduleKey) {
        Map<String, Object> moduleData = new ConcurrentHashMap<>();
        for (Map.Entry<String, Object> entry : dataStore.entrySet()) {
            String key = entry.getKey();
            if (key.startsWith(moduleKey + ".")) {
                String subKey = key.substring(moduleKey.length() + 1);
                moduleData.put(subKey, entry.getValue());
            }
        }
        return moduleData;
    }
    
    /**
     * 清空所有数据
     */
    public void clear() {
        dataStore.clear();
    }
    
    /**
     * 清空特定模块的数据
     * @param moduleKey 模块标识
     */
    public void clearModuleData(String moduleKey) {
        for (String key : dataStore.keySet()) {
            if (key.startsWith(moduleKey + ".")) {
                dataStore.remove(key);
            }
        }
    }
    
    /**
     * 获取数据大小
     * @return 数据项数量
     */
    public int size() {
        return dataStore.size();
    }
}
