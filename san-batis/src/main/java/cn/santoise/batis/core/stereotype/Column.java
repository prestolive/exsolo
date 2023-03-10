package cn.santoise.batis.core.stereotype;

import java.lang.annotation.*;

/**
 * Created by wuby on 2017/6/15.
 * @author wuby
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented//action生成注释
public @interface Column {

    String name();

    /**
     * 下述4个字段用来做写入控制
     * 如果采用模板生成，那么模板会试图解析出下列的要素
     */
    boolean primary() default false;

    boolean nullable() default true;

    int maxLength() default 0;

    int scale() default 0;

    String defaultValue() default "";

    /**
     * 例如 varchar(24) smallint decimal(20,2) 这类的
     * 该字段才是用于创建表，该类型可以用任何数据库的类型命名
     * 本框架会试图兼容任一数据库的类型命名并按照目标数据库进行转换
     */
    String datatype();


}
