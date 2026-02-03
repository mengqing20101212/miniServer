package ly.utils;

import java.util.*;

/**
 * Excel 键值对解析器
 * 用于解析从 Excel 里面解析出来的 k1:v1, k2:v2, k3:v3 这种格式的数据
 */
public class ExcelKVParser {

    /**
     * 解析简单的键值对字符串，如 "k1:v1,k2:v2,k3:v3"
     * 
     * @param input 输入的键值对字符串
     * @param separator 键值对之间的分隔符，默认为逗号
     * @param kvSeparator 键和值之间的分隔符，默认为冒号
     * @return KV 对象列表
     */
    public static <K, V> List<KV<K, V>> parseSimpleKV(String input, String separator, String kvSeparator) {
        if (input == null || input.trim().isEmpty()) {
            return new ArrayList<>();
        }
        
        List<KV<K, V>> result = new ArrayList<>();
        String[] pairs = input.split(separator);
        
        for (String pair : pairs) {
            pair = pair.trim();
            if (!pair.isEmpty()) {
                int idx = pair.indexOf(kvSeparator);
                if (idx > 0) {
                    String keyStr = pair.substring(0, idx).trim();
                    String valueStr = pair.substring(idx + kvSeparator.length()).trim();
                    
                    // 由于泛型擦除，这里返回字符串类型的 KV，用户可以根据需要转换
                    result.add((KV<K, V>) new KV<>(keyStr, valueStr));
                }
            }
        }
        
        return result;
    }

    /**
     * 解析简单的键值对字符串，使用默认分隔符 (逗号和冒号)
     * 
     * @param input 输入的键值对字符串，格式如 "k1:v1,k2:v2,k3:v3"
     * @return KV 对象列表
     */
    public static <K, V> List<KV<K, V>> parseSimpleKV(String input) {
        return parseSimpleKV(input, ",", ":");
    }

    /**
     * 解析键值对字符串，支持引号包围的值（处理包含分隔符的情况）
     * 
     * @param input 输入的键值对字符串
     * @param separator 键值对之间的分隔符
     * @param kvSeparator 键和值之间的分隔符
     * @return KV 对象列表
     */
    public static <K, V> List<KV<K, V>> parseQuotedKV(String input, String separator, String kvSeparator) {
        if (input == null || input.trim().isEmpty()) {
            return new ArrayList<>();
        }
        
        List<KV<K, V>> result = new ArrayList<>();
        List<String> pairs = splitRespectingQuotes(input, separator.charAt(0));
        
        for (String pair : pairs) {
            pair = pair.trim();
            if (!pair.isEmpty()) {
                int idx = findUnquotedIndex(pair, kvSeparator.charAt(0));
                if (idx > 0) {
                    String keyStr = pair.substring(0, idx).trim();
                    String valueStr = pair.substring(idx + kvSeparator.length()).trim();
                    
                    // 去除引号（如果有的话）
                    keyStr = unquote(keyStr);
                    valueStr = unquote(valueStr);
                    
                    result.add((KV<K, V>) new KV<>(keyStr, valueStr));
                }
            }
        }
        
        return result;
    }

    /**
     * 解析键值对字符串，支持引号包围的值（使用默认分隔符）
     * 
     * @param input 输入的键值对字符串
     * @return KV 对象列表
     */
    public static <K, V> List<KV<K, V>> parseQuotedKV(String input) {
        return parseQuotedKV(input, ",", ":");
    }

    /**
     * 按照分隔符拆分字符串，但尊重引号内的内容
     */
    private static List<String> splitRespectingQuotes(String input, char separator) {
        List<String> result = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean inQuotes = false;
        char quoteChar = '"';
        
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            
            if (c == '"' || c == '\'') {
                if (!inQuotes) {
                    inQuotes = true;
                    quoteChar = c;
                    current.append(c);
                } else if (c == quoteChar) {
                    inQuotes = false;
                    current.append(c);
                } else {
                    current.append(c);
                }
            } else if (c == separator && !inQuotes) {
                result.add(current.toString());
                current.setLength(0);
            } else {
                current.append(c);
            }
        }
        
        if (current.length() > 0) {
            result.add(current.toString());
        }
        
        return result;
    }

    /**
     * 在不考虑引号内分隔符的情况下查找分隔符的位置
     */
    private static int findUnquotedIndex(String input, char separator) {
        boolean inQuotes = false;
        char quoteChar = 0;
        
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            
            if ((c == '"' || c == '\'') && (i == 0 || input.charAt(i - 1) != '\\')) {
                if (!inQuotes) {
                    inQuotes = true;
                    quoteChar = c;
                } else if (c == quoteChar) {
                    inQuotes = false;
                }
            } else if (c == separator && !inQuotes) {
                return i;
            }
        }
        
        return -1;
    }

    /**
     * 去除字符串两端的引号
     */
    private static String unquote(String str) {
        if (str.length() >= 2) {
            char first = str.charAt(0);
            char last = str.charAt(str.length() - 1);
            
            if ((first == '"' && last == '"') || (first == '\'' && last == '\'')) {
                return str.substring(1, str.length() - 1);
            }
        }
        return str;
    }

    /**
     * 将 KV 列表转换为 Map
     */
    public static <K, V> Map<K, V> toMap(List<KV<K, V>> kvList) {
        Map<K, V> result = new LinkedHashMap<>();
        for (KV<K, V> kv : kvList) {
            result.put(kv.getKey(), kv.getValue());
        }
        return result;
    }

    /**
     * 将 Map 转换为 KV 列表
     */
    public static <K, V> List<KV<K, V>> fromMap(Map<K, V> map) {
        List<KV<K, V>> result = new ArrayList<>();
        for (Map.Entry<K, V> entry : map.entrySet()) {
            result.add(new KV<>(entry.getKey(), entry.getValue()));
        }
        return result;
    }
}