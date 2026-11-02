package ly;

import com.alibaba.fastjson2.JSON;
import java.math.BigDecimal;
import java.util.*;
import javax.lang.model.SourceVersion;

/** Excel 类型行的类型树；转表和运行时共用相同的值校验规则。 */
public record ConfigValueType(String kind, List<ConfigValueType> arguments) {
    public ConfigValueType {
        arguments = List.copyOf(arguments);
    }

    public static ConfigValueType parse(String declaration) {
        if (declaration == null) throw new IllegalArgumentException("类型不能为空");
        TypeReader reader = new TypeReader(declaration);
        ConfigValueType type = reader.read();
        reader.space();
        if (reader.position != declaration.length()) throw reader.error("类型声明有多余字符");
        return type;
    }

    public String declaration() {
        if (arguments.isEmpty()) return kind;
        return kind + "<" + String.join(",", arguments.stream().map(ConfigValueType::declaration).toList()) + ">";
    }

    public boolean scalar() {
        return !Set.of("list", "map").contains(kind);
    }

    /** 空单元格：数值为0、布尔为false、字符串为空、集合为空。 */
    public Object readCell(String text) {
        String trimmed = text.trim();
        if (kind.equals("string")) return text;
        if (trimmed.isEmpty()) {
            return switch (kind) {
                case "list" -> List.of();
                case "map" -> Map.of();
                case "bool" -> false;
                case "byte", "short", "int", "long", "float", "double" -> convert("0", "$");
                default -> throw new IllegalArgumentException("类型 " + declaration() + " 不允许空单元格");
            };
        }
        Object raw = text;
        if (!scalar()) {
            raw = JSON.parse(trimmed);
        }
        return convert(raw, "$");
    }

    private Object convert(Object raw, String path) {
        if (raw == null) throw new IllegalArgumentException(path + ": 不允许 null");
        try {
            return switch (kind) {
                case "string" -> {
                    if (!(raw instanceof String)) throw new IllegalArgumentException("需要字符串");
                    yield raw;
                }
                case "byte" -> decimal(raw).byteValueExact();
                case "short" -> decimal(raw).shortValueExact();
                case "int" -> decimal(raw).intValueExact();
                case "long" -> decimal(raw).longValueExact();
                case "float" -> {
                    float number = decimal(raw).floatValue();
                    if (!Float.isFinite(number)) throw new IllegalArgumentException("float 溢出");
                    yield number;
                }
                case "double" -> {
                    double number = decimal(raw).doubleValue();
                    if (!Double.isFinite(number)) throw new IllegalArgumentException("double 溢出");
                    yield number;
                }
                case "bool" -> {
                    String value = raw.toString();
                    if (value.equalsIgnoreCase("true") || value.equals("1")) yield true;
                    if (value.equalsIgnoreCase("false") || value.equals("0")) yield false;
                    throw new IllegalArgumentException("布尔值只能是 true/false/1/0");
                }
                case "list" -> {
                    if (!(raw instanceof List<?> list)) throw new IllegalArgumentException("需要 JSON 数组");
                    List<Object> result = new ArrayList<>();
                    for (int i = 0; i < list.size(); i++)
                        result.add(arguments.getFirst().convert(list.get(i), path + "[" + i + "]"));
                    yield List.copyOf(result);
                }
                case "map" -> {
                    Map<Object, Object> result = new LinkedHashMap<>();
                    if (raw instanceof Map<?, ?> map) {
                        for (Map.Entry<?, ?> entry : map.entrySet())
                            put(result, entry.getKey(), entry.getValue(), path);
                    } else if (raw instanceof List<?> pairs) {
                        for (Object entry : pairs) {
                            if (!(entry instanceof List<?> pair) || pair.size() != 2)
                                throw new IllegalArgumentException("Map 条目必须为 [key,value]");
                            put(result, pair.get(0), pair.get(1), path);
                        }
                    } else throw new IllegalArgumentException("需要 JSON 对象或键值对数组");
                    yield Collections.unmodifiableMap(result);
                }
                default -> throw new IllegalArgumentException("未知类型 " + kind);
            };
        } catch (RuntimeException error) {
            throw new IllegalArgumentException(path + " (" + declaration() + "): " + error.getMessage(), error);
        }
    }

    private void put(Map<Object, Object> result, Object key, Object value, String path) {
        Object parsedKey = arguments.get(0).convert(key, path + ".key");
        Object parsedValue = arguments.get(1).convert(value, path + "[" + key + "]");
        result.put(parsedKey, parsedValue);
    }

    private static BigDecimal decimal(Object value) {
        if (!(value instanceof Number) && !(value instanceof String))
            throw new IllegalArgumentException("需要数值");
        return new BigDecimal(value.toString().trim());
    }

    private static ConfigValueType type(String kind, List<ConfigValueType> args) {
        return new ConfigValueType(kind, args);
    }

    private static final class TypeReader {
        private final String source;
        private int position;
        TypeReader(String source) { this.source = source; }
        void space() { while (position < source.length() && Character.isWhitespace(source.charAt(position))) position++; }
        boolean take(char token) {
            space();
            if (position < source.length() && source.charAt(position) == token) { position++; return true; }
            return false;
        }
        void require(char token) { if (!take(token)) throw error("缺少 " + token); }
        String name() {
            space();
            int start = position;
            while (position < source.length() && Character.isJavaIdentifierPart(source.charAt(position))) position++;
            String name = source.substring(start, position);
            if (!SourceVersion.isIdentifier(name))
                throw error("非法名称 " + name);
            return name;
        }
        IllegalArgumentException error(String message) {
            return new IllegalArgumentException("类型 " + source + "，位置 " + position + ": " + message);
        }
        ConfigValueType read() {
            String kind = name().toLowerCase(Locale.ROOT);
            kind = switch (kind) { case "boolean" -> "bool"; case "str", "text" -> "string"; default -> kind; };
            if (Set.of("byte", "short", "int", "long", "float", "double", "bool", "string").contains(kind))
                return type(kind, List.of());
            if (!kind.equals("list") && !kind.equals("map")) throw error("不支持的类型 " + kind);
            require('<');
            List<ConfigValueType> args = new ArrayList<>();
            args.add(read());
            if (kind.equals("map")) {
                if (!Set.of("byte", "short", "int", "long", "bool", "string").contains(args.getFirst().kind))
                    throw error("Map 键必须为整数、布尔、字符串");
                require(',');
                args.add(read());
            }
            require('>');
            return type(kind, args);
        }
    }
}
