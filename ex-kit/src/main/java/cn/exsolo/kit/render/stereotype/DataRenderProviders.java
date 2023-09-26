package cn.exsolo.kit.render.stereotype;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author prestolive
 * @date 2021/9/20
 **/

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DataRenderProviders {

    DataRenderProvider[] value();
}
