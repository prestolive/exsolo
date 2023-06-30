package cn.exsolo.auth.shiro.ext.stereotype;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author prestolive
 * @date 2023/6/27
 **/

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface AccessProvider {

    String module();

    String node();

    String label();

    //处理类

}
