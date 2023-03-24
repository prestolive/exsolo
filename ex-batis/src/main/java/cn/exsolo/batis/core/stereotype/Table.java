package cn.exsolo.batis.core.stereotype;

import java.lang.annotation.*;

/**
 * Created by prestolive on 2017/6/15.
 * @author prestolive
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented//action生成注释
public @interface Table {

    String value();

}
