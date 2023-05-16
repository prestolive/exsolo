package cn.exsolo.springmvcext.stereotype;

import java.lang.annotation.*;

/**
 * Created by prestolive on 2017/6/22.
 * @author  prestolive
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestJSON {

    String value() default "";

}
