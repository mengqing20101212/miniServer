package ly.bot.data;

import java.util.Map;

/**
 * 机器人模块的数据存储接口。
 * <p>
 * 每个模块可以通过同一套键值 API 保存自己的运行状态，具体实现负责线程安全、
 * 生命周期和数据隔离策略。
 */
public interface ModuleDataStore<T> {
    
    /**
     * 写入或覆盖指定键的数据。
     *
     * @param key 数据键
     * @param value 数据值
     */
    void put(String key, T value);
    
    /**
     * 读取指定键的数据。
     *
     * @param key 数据键
     * @return 数据值；不存在时返回 {@code null}
     */
    T get(String key);
    
    /**
     * 删除指定键的数据。
     *
     * @param key 数据键
     */
    void remove(String key);
    
    /**
     * 判断指定键是否存在。
     *
     * @param key 数据键
     * @return 存在返回 {@code true}
     */
    boolean containsKey(String key);
    
    /**
     * 返回当前存储中的全部数据。
     *
     * @return 数据映射
     */
    Map<String, T> getAll();
    
    /**
     * 清空当前存储。
     */
    void clear();
    
    /**
     * 返回当前存储的数据条数。
     *
     * @return 数据条数
     */
    int size();
}
