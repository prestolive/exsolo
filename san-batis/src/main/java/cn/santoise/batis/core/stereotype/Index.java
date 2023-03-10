package cn.santoise.batis.core.stereotype;

import java.lang.annotation.*;

/**
 * Created by wuby on 2017/6/15.
 * @author wuby
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented//action生成注释
@Repeatable(Indexes.class)
public @interface Index {

    String name();

    boolean unique();

    String fields();

}
