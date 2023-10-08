package cn.exsolo.kit.item.stereotype;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自动生成对象表
 * 需要和@cn.exsolo.batis.core.stereotype.Table同时使用
 * 系统在保存的时候会自动抽取字段同步到对象表
 * Created by prestolive on 2017/6/15.
 * @author prestolive
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ItemProvider {

    enum Type{
        NORMAL, ERROR_CODE
    }

    String tag();

    String name();

    Type type() default Type.NORMAL;

    boolean customAble() default false;
}
