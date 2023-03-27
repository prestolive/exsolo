package cn.exsolo.kit.dev;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Locale;

/**
 * @author prestolive
 * @date 2023/3/25
 **/
public enum JsTypeMapEnum {


    String(new Class[]{
            String.class,
            Character.class,
            Byte.class,
            java.util.Date.class, java.sql.Date.class, java.sql.Time.class, java.sql.Timestamp.class,
            Enum.class
    }),
    Number(new Class[]{
            Short.class,
            Integer.class,
            Long.class, Float.class, Double.class, BigInteger.class, BigDecimal.class
    }),
    Boolean(new Class[]{
            Boolean.class
    });


    private Class[] types;

    JsTypeMapEnum(Class[] types) {
        this.types = types;
    }

    public Class[] getTypes() {
        return types;
    }

    public static String getJavaScriptTypeName(Class clz) {
        if(clz.isEnum()){
            return JsTypeMapEnum.String.name().toLowerCase(Locale.ROOT);
        }
        if (isPrimitive(clz)) {
            JsTypeMapEnum type = Arrays.stream(JsTypeMapEnum.values())
                    .filter(row -> Arrays.stream(row.types).anyMatch(has -> has.getName().equals(clz.getName())))
                    .findFirst().orElse(null);
            return type.name().toLowerCase(Locale.ROOT);
        } else {
            return "object";
        }
    }

    public static boolean isPrimitive(Class clz) {
        return clz.isEnum() || Arrays.stream(JsTypeMapEnum.values()).anyMatch(row -> Arrays.stream(row.types).anyMatch(has -> has.getName().equals(clz.getName())));
    }

}
