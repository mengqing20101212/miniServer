package ly.utils;

/**
 * KV 键值对类，用于存储从 Excel 解析出来的 k1:v1, k2:v2, k3:v3 这种数据类型
 * 
 * @param <K> 键的类型
 * @param <V> 值的类型
 */
public class KV<K, V> {
    private K key;
    private V value;

    public KV(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "(" + key + "=" + value + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        KV<?, ?> kv = (KV<?, ?>) obj;
        
        if (key != null ? !key.equals(kv.key) : kv.key != null) return false;
        return value != null ? value.equals(kv.value) : kv.value == null;
    }

    @Override
    public int hashCode() {
        int result = key != null ? key.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }
}