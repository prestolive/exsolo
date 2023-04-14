package cn.exsolo.kit.ex;


import cn.exsolo.kit.item.ExKitErrorCodeEnum;

import java.util.List;

/**
 * @author prestolive
 * @date 2023/4/1
 **/
public class ExAssert {

    public static void isNull(Object obj){
        if(obj==null||obj.toString().length()==0) {
            throw new ExNerverException(ExKitErrorCodeEnum.DEV_NULL);
        }
    }

    public static void isNull(Object... objs){
        for(Object obj:objs){
            isNull(obj);
        }
    }

    public static void isEmpty(Object obj){
        if(obj==null){
            throw new ExNerverException(ExKitErrorCodeEnum.DEV_NULL);
        }
        if(obj instanceof List){
            List list = (List) obj;
            if(list.size()==0){
                throw new ExNerverException(ExKitErrorCodeEnum.DEV_EMPTY);
            }
        }else{
            if(obj.toString().length()==0){
                throw new ExNerverException(ExKitErrorCodeEnum.DEV_NULL);
            }
        }

    }
}
