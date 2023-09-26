package cn.exsolo.auth.shiro.ext.stereotype;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author prestolive
 * @date 2021/7/11
 **/

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AccessCustom {

    String key();

    String label();
}
