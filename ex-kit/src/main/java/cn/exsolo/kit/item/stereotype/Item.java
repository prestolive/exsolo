package cn.exsolo.kit.item.stereotype;

import cn.exsolo.kit.item.ItemSchemaEnum;

import java.lang.annotation.*;

/**
 * 自动生成对象表
 * 需要和@cn.exsolo.batis.core.stereotype.Table同时使用
 * 系统在保存的时候会自动抽取字段同步到对象表
 * Created by prestolive on 2017/6/15.
 * @author prestolive
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Item {

    String tag();

    String name();

    ItemSchemaEnum schema();

    String nameField();

    String codeField();

    boolean customAble() default false;
}
