package ly.utils;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

/**
 * 测试类，用于验证该模块的连接、生成器或工具方法行为。
 */
public class KVTest {

    @Test
    public void testKVConstructorAndGetters() {
        KV<String, Integer> kv = new KV<>("key1", 123);
        assertEquals("key1", kv.getKey());
        assertEquals(Integer.valueOf(123), kv.getValue());
    }

    @Test
    public void testKVSetters() {
        KV<String, Integer> kv = new KV<>("key1", 123);
        kv.setKey("key2");
        kv.setValue(456);
        
        assertEquals("key2", kv.getKey());
        assertEquals(Integer.valueOf(456), kv.getValue());
    }

    @Test
    public void testExcelKVParserSimple() {
        String input = "k1:v1,k2:v2,k3:v3";
        List<KV<String, String>> result = ExcelKVParser.parseSimpleKV(input);
        
        assertEquals(3, result.size());
        assertEquals("k1", result.get(0).getKey());
        assertEquals("v1", result.get(0).getValue());
        assertEquals("k2", result.get(1).getKey());
        assertEquals("v2", result.get(1).getValue());
        assertEquals("k3", result.get(2).getKey());
        assertEquals("v3", result.get(2).getValue());
    }

    @Test
    public void testExcelKVParserWithSpaces() {
        String input = " key1 : value1 , key2 : value2 ";
        List<KV<String, String>> result = ExcelKVParser.parseSimpleKV(input);
        
        assertEquals(2, result.size());
        assertEquals("key1", result.get(0).getKey());
        assertEquals("value1", result.get(0).getValue());
        assertEquals("key2", result.get(1).getKey());
        assertEquals("value2", result.get(1).getValue());
    }

    @Test
    public void testExcelKVParserEmptyInput() {
        List<KV<String, String>> result = ExcelKVParser.parseSimpleKV("");
        assertTrue(result.isEmpty());
        
        result = ExcelKVParser.parseSimpleKV(null);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testToMapConversion() {
        String input = "k1:v1,k2:v2,k3:v3";
        List<KV<String, String>> kvList = ExcelKVParser.parseSimpleKV(input);
        Map<String, String> map = ExcelKVParser.toMap(kvList);
        
        assertEquals(3, map.size());
        assertEquals("v1", map.get("k1"));
        assertEquals("v2", map.get("k2"));
        assertEquals("v3", map.get("k3"));
    }

    @Test
    public void testFromMapConversion() {
        java.util.LinkedHashMap<String, String> map = new java.util.LinkedHashMap<>();
        map.put("k1", "v1");
        map.put("k2", "v2");
        map.put("k3", "v3");
        
        List<KV<String, String>> kvList = ExcelKVParser.fromMap(map);
        
        assertEquals(3, kvList.size());
        assertEquals("k1", kvList.get(0).getKey());
        assertEquals("v1", kvList.get(0).getValue());
        assertEquals("k2", kvList.get(1).getKey());
        assertEquals("v2", kvList.get(1).getValue());
        assertEquals("k3", kvList.get(2).getKey());
        assertEquals("v3", kvList.get(2).getValue());
    }
}
