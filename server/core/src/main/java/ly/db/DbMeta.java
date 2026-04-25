package ly.db;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 数据库访问组件，封装连接、元数据、实体脏标记和增删改查操作。
 */
public class DbMeta {

  /***
   * 数据库表名 注解
   */
  @Retention(RetentionPolicy.RUNTIME)
  @Target(ElementType.TYPE)
  public @interface DbTable {
    String name() default "";
  }

  /** 数据库 主键标记 */
  @Retention(RetentionPolicy.RUNTIME)
  @Target(ElementType.FIELD)
  public @interface DbMasterKey {
    String name() default "";

    /** 是否是自增的主键 true 是自增的主键， false 不是自增的主键 */
    boolean autoIncrement() default false;
  }

  /** 数据库 字段属性 */
  @Retention(RetentionPolicy.RUNTIME)
  @Target(ElementType.FIELD)
  public @interface DbField {
    /** 属性名称 */
    String name() default "";
  }
}
