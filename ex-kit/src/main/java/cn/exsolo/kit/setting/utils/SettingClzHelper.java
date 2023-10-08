package cn.exsolo.kit.setting.utils;


import cn.exsolo.comm.ex.ExDevException;
import cn.exsolo.kit.setting.stereotype.SettingProp;

import java.lang.reflect.Field;
import java.math.BigDecimal;

/**
 *
 * @author prestolive on 2019/9/11.
 */
public class SettingClzHelper {

    /**
     * 通过调用这个方法来初始化当前类的静态变量，保证类第一次调用时候，已经赋默认值。
     * （为什么不直接在配置类的参数上直接设置默认值呢？答：因为后台管理需要了解该参数的默认值，作为参考。设置在参数上，当发生了变更就无从参考了）
     */
    public static void init() {
        String clzName = new Throwable().getStackTrace()[1].getClassName();
        Class thisClass = null;
        try {
            thisClass = Class.forName(clzName);
        } catch (ClassNotFoundException e) {
            throw new ExDevException("class for name fail");
        }
        initClzField(thisClass);

    }

    public static void initClzField(Class clz) {
        Field[] fields = clz.getDeclaredFields();

        if (fields != null) {
            for (Field field : fields) {
                SettingProp anno = field.getAnnotation(SettingProp.class);
                if (anno == null) {
                    continue;
                }
                setValue(clz, field, anno.inputType(), anno.defaultValue());
            }
        }
    }

    public static void setFieldValue(String clzName, String field_name,String value) {
        Class clz = null;
        try {
            clz = Class.forName(clzName);
        } catch (ClassNotFoundException e) {
            throw new ExDevException(String.format("初始化配置类失败:%s",clzName) + e.getMessage(), e);
        }
        Field[] fields = clz.getDeclaredFields();
        if (fields != null) {
            for (Field field : fields) {
                if (field.getName().endsWith(field_name)) {
                    SettingProp settingProp = field.getAnnotation(SettingProp.class);
                    if (settingProp == null) {
                        continue;
                    }
                    setValue(clz, field, settingProp.inputType(), value);
                }
            }
        }
    }

    private static void setValue(Class clz, Field field, SettingProp.InputType type, String valueStr) {
        try {
            field.setAccessible(true);
            switch (type) {
                case INTEGER: {
                    Integer value = Integer.valueOf(valueStr);
                    field.set(clz, value);
                    break;
                }
                case BOOLEAN: {
                    Boolean value = Boolean.valueOf(valueStr);
                    field.set(clz, value);
                    break;
                }
                case DECIMAL: {
                    BigDecimal value = new BigDecimal(valueStr);
                    field.set(clz, value);
                    break;
                }
                case STRING:
                case DATE:
                case MONTH:
                case YEAR:
                case DATETIME:
                case PICKER:
                default: {
                    field.set(clz, valueStr);
                }
            }
        } catch (IllegalAccessException e) {
            throw new ExDevException("配置实体类参数" + field.getName() + "初始化失败:" + e.getMessage(), e);
        }
    }

    public static Object getValue(Class clz, String field_name) {
        Field[] fields = clz.getDeclaredFields();
        if (fields != null) {
            for (Field field : fields) {
                if (field.getName().endsWith(field_name)) {
                    field.setAccessible(true);
                    try {
                        return field.get(clz);
                    } catch (IllegalAccessException e) {
                        throw new ExDevException("获取静态配置类参数错误" + e.getMessage(), e);
                    }
                }
            }
        }
        return null;
    }
}
