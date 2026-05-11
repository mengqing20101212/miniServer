package ly;

/** 配置表字段元数据，用于校验 txt 表头是否和当前 Java 配置代码一致。 */
public record ConfigColumnMeta(int index, String name, String type) {}
