package ly;

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
        return switch (kind) {
            case "list" -> readList(trimmed);
            case "map" -> readMap(trimmed);
            default -> convert(text, "$");
        };
    }

    private List<Object> readList(String text) {
        List<Object> result = new ArrayList<>();
        String[] items = text.split(";", -1);
        for (int index = 0; index < items.length; index++)
            result.add(arguments.getFirst().convert(items[index].trim(), "$[" + index + "]"));
        return List.copyOf(result);
    }

    private Map<Object, Object> readMap(String text) {
        Map<Object, Object> result = new LinkedHashMap<>();
        for (String item : text.split(";", -1)) {
            String[] pair = item.split(":", 2);
            if (pair.length != 2) throw new IllegalArgumentException("Map 条目应为 键:值，条目=" + item);
            Object key = arguments.get(0).convert(pair[0].trim(), "$.key");
            Object value = arguments.get(1).convert(pair[1].trim(), "$[" + pair[0].trim() + "]");
            result.put(key, value);
        }
        return Collections.unmodifiableMap(result);
    }

    private Object convert(String raw, String path) {
        try {
            return switch (kind) {
                case "string" -> raw;
                case "byte" -> new BigDecimal(raw.trim()).byteValueExact();
                case "short" -> new BigDecimal(raw.trim()).shortValueExact();
                case "int" -> new BigDecimal(raw.trim()).intValueExact();
                case "long" -> new BigDecimal(raw.trim()).longValueExact();
                case "float" -> {
                    float number = new BigDecimal(raw.trim()).floatValue();
                    if (!Float.isFinite(number)) throw new IllegalArgumentException("float 溢出");
                    yield number;
                }
                case "double" -> {
                    double number = new BigDecimal(raw.trim()).doubleValue();
                    if (!Double.isFinite(number)) throw new IllegalArgumentException("double 溢出");
                    yield number;
                }
                case "bool" -> {
                    String value = raw.trim();
                    if (value.equalsIgnoreCase("true") || value.equals("1")) yield true;
                    if (value.equalsIgnoreCase("false") || value.equals("0")) yield false;
                    throw new IllegalArgumentException("布尔值只能是 true/false/1/0");
                }
                default -> throw new IllegalArgumentException("需要基础类型，实际为 " + kind);
            };
        } catch (RuntimeException error) {
            throw new IllegalArgumentException(path + " (" + declaration() + "): " + error.getMessage(), error);
        }
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
            for (ConfigValueType argument : args)
                if (!argument.scalar()) throw error("不支持嵌套集合，复杂配置请拆成明细表并通过ID关联");
            return type(kind, args);
        }
    }
}
