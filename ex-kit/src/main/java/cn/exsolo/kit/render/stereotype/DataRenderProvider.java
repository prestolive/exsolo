package cn.exsolo.kit.render.stereotype;

import java.lang.annotation.*;

/**
 * @author prestolive
 * @date 2021/9/20
 **/

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(DataRenderProviders.class)
public  @interface DataRenderProvider{

    enum WapperType{
        //代称
        alias,
        //展开
        flat,
    }

    String path();

    String keyField();

    /**
     * extends IDataRender
     * @return
     */
    Class dataRenderClass();

    /**
     * 包装方法 默认用代称
     * @return
     */
    WapperType wapperType() default WapperType.alias;

    /**
     * 代称，如果为空，则默认按照下划线加keyFields，例如{id,code} ->  _idcode
     * @return
     */
    String defineAlias() default "";

}
