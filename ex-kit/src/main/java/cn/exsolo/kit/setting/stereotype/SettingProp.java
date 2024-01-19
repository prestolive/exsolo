package cn.exsolo.kit.setting.stereotype;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 配置对象
 * @author prestolive
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SettingProp {

    enum InputType {

        BOOLEAN,
        STRING,
        INTEGER,
        DECIMAL,
        DATE,
        MONTH,
        YEAR,
        DATETIME,
        PICKER,
        SELECTOR,
    }

    String label();

    String group();

    String desc() default "";

    int sortNo() default 0;

    InputType inputType();

    /* 如果是picker,select类型，设置注册的 code在此 */
    String dataRefCode() default "";

    /* 词头 */
    String prefix() default "";

    /* 词尾 */
    String suffix() default "";

    String defaultValue();

    /* 是否受保护，是则在后台管理界面上脱敏 */
    boolean isProtect() default false;

    /* 是否必须默认值，是则在系统初始化时，无默认值则报错 */
    /* 设置这个参数的目的是：开发者在确认参数不能有默认值时，要手动去关闭，并注意要去后台管理去设置参数（当然后台应该也给予正确的提示） */
    boolean isRequireInInit() default true;

    /* 是否必填，是则在后台管理时，设置的值不能为空 */
    boolean isRequire() default true;
}
