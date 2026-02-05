package ly.bot.data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 模块数据存储接口
 * 为每个模块提供独立的数据存储空间
 * 
 * Author: OpenClaw AI Assistant
 * Date: 2026/2/5
 * File: ModuleDataStore
 */
public interface ModuleDataStore<T> {
    
    /**
     * 存储数据
     * @param key 键
     * @param value 值
     */
    void put(String key, T value);
    
    /**
     * 获取数据
     * @param key 键
     * @return 值
     */
    T get(String key);
    
    /**
     * 删除数据
     * @param key 键
     */
    void remove(String key);
    
    /**
     * 检查是否存在指定键
     * @param key 键
     * @return 是否存在
     */
    boolean containsKey(String key);
    
    /**
     * 获取所有数据
     * @return 所有数据的映射
     */
    Map<String, T> getAll();
    
    /**
     * 清空所有数据
     */
    void clear();
    
    /**
     * 获取数据大小
     * @return 数据项数量
     */
    int size();
}