package ly.utils;

import java.util.List;
import java.util.Map;

/**
 * ExcelKVParser 使用示例
 * 演示如何解析从 Excel 读取的键值对数据
 */
public class ExcelKVExample {

    public static void main(String[] args) {
        // 示例1: 解析简单的键值对字符串
        System.out.println("=== 示例1: 解析简单键值对 ===");
        String simpleInput = "k1:v1,k2:v2,k3:v3";
        List<KV<String, String>> kvList1 = ExcelKVParser.parseSimpleKV(simpleInput);
        
        for (KV<String, String> kv : kvList1) {
            System.out.println("Key: " + kv.getKey() + ", Value: " + kv.getValue());
        }
        
        // 转换为 Map
        Map<String, String> map1 = ExcelKVParser.toMap(kvList1);
        System.out.println("Converted to Map: " + map1);
        
        System.out.println();

        // 示例2: 解析带空格的键值对
        System.out.println("=== 示例2: 解析带空格的键值对 ===");
        String spacedInput = " name : John Doe , age : 30 , city : New York ";
        List<KV<String, String>> kvList2 = ExcelKVParser.parseSimpleKV(spacedInput);
        
        for (KV<String, String> kv : kvList2) {
            System.out.println("Key: " + kv.getKey() + ", Value: " + kv.getValue());
        }
        
        System.out.println();

        // 示例3: 解析带引号的值（处理包含分隔符的情况）
        System.out.println("=== 示例3: 解析带引号的值 ===");
        String quotedInput = "name:\"John, Jr.\",description:\"Engineer, \"\"Senior\"\"\"";
        List<KV<String, String>> kvList3 = ExcelKVParser.parseQuotedKV(quotedInput);
        
        for (KV<String, String> kv : kvList3) {
            System.out.println("Key: " + kv.getKey() + ", Value: " + kv.getValue());
        }
        
        System.out.println();

        // 示例4: 从 Map 转换回 KV 列表
        System.out.println("=== 示例4: 从 Map 转换回 KV 列表 ===");
        Map<String, Object> originalMap = Map.of(
            "playerId", 1001,
            "playerName", "Alice",
            "level", 15,
            "gold", 10000
        );
        
        List<KV<String, Object>> kvFromMap = ExcelKVParser.fromMap(originalMap);
        for (KV<String, Object> kv : kvFromMap) {
            System.out.println("Key: " + kv.getKey() + ", Value: " + kv.getValue());
        }
        
        System.out.println();

        // 示例5: 处理不同分隔符
        System.out.println("=== 示例5: 使用自定义分隔符 ===");
        String customInput = "type=warrior|hp=100|mp=50|strength=20";
        List<KV<String, String>> kvList5 = ExcelKVParser.parseSimpleKV(customInput, "\\|", "=");
        
        for (KV<String, String> kv : kvList5) {
            System.out.println("Key: " + kv.getKey() + ", Value: " + kv.getValue());
        }
    }
}