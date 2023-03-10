package cn.santoise.batis.core.stereotype;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented//action生成注释
public @interface Indexes {
    Index[] value();
}
