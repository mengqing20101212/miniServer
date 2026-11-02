package ly;

/** 从表头类型树生成具体的 Java 类型和集合转换表达式。 */
final class ConfigTypeJava {
    private ConfigTypeJava() {}

    static String javaType(ConfigValueType type, boolean boxed) {
        return switch (type.kind()) {
            case "list" -> "java.util.List<" + javaType(type.arguments().getFirst(), true) + ">";
            case "map" -> "java.util.Map<" + javaType(type.arguments().get(0), true)
                    + ", " + javaType(type.arguments().get(1), true) + ">";
            case "string" -> "String";
            case "bool" -> boxed ? "Boolean" : "boolean";
            case "byte" -> boxed ? "Byte" : "byte";
            case "short" -> boxed ? "Short" : "short";
            case "int" -> boxed ? "Integer" : "int";
            case "long" -> boxed ? "Long" : "long";
            case "float" -> boxed ? "Float" : "float";
            case "double" -> boxed ? "Double" : "double";
            default -> throw new IllegalArgumentException("不支持的类型 " + type.kind());
        };
    }

}
