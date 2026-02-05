package ly.bot.data.impl;

import ly.bot.data.ModuleDataStore;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 基于ConcurrentHashMap的模块数据存储实现
 * 
 * Author: OpenClaw AI Assistant
 * Date: 2026/2/5
 * File: ConcurrentModuleDataStore
 */
public class ConcurrentModuleDataStore<T> implements ModuleDataStore<T> {
    
    private final Map<String, T> dataStore = new ConcurrentHashMap<>();
    
    @Override
    public void put(String key, T value) {
        dataStore.put(key, value);
    }
    
    @Override
    public T get(String key) {
        return dataStore.get(key);
    }
    
    @Override
    public void remove(String key) {
        dataStore.remove(key);
    }
    
    @Override
    public boolean containsKey(String key) {
        return dataStore.containsKey(key);
    }
    
    @Override
    public Map<String, T> getAll() {
        return new ConcurrentHashMap<>(dataStore);
    }
    
    @Override
    public void clear() {
        dataStore.clear();
    }
    
    @Override
    public int size() {
        return dataStore.size();
    }
}