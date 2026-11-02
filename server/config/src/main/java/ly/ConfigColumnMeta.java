package ly;

/** 配置表字段元数据，用于 XML 字段检查与生成配置类型。 */
public record ConfigColumnMeta(int index, String name, String type) {}
