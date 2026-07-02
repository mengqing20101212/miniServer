package ly.db;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** 数据库实体元数据注解定义。 */
public class DbMeta {

  /** 数据库表名注解。 */
  @Retention(RetentionPolicy.RUNTIME)
  @Target(ElementType.TYPE)
  public @interface DbTable {
    String name() default "";
  }

  /** 数据库主键字段注解。 */
  @Retention(RetentionPolicy.RUNTIME)
  @Target(ElementType.FIELD)
  public @interface DbMasterKey {
    String name() default "";

    /** 是否为自增主键。 */
    boolean autoIncrement() default false;
  }

  /** 数据库普通字段注解。 */
  @Retention(RetentionPolicy.RUNTIME)
  @Target(ElementType.FIELD)
  public @interface DbField {
    String name() default "";

    /** 指定建表时使用的数据库列类型；为空时按 Java 字段类型自动推导。 */
    String columnType() default "";
  }
}
