package ly.db;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
 * db entry实体类注解 用于 entry SQL Java bean映射
 * Author: liuYang
 * Date: 2025/4/3
 * File: RandomUtils
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
