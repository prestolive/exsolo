package cn.exsolo.kit.utils;


import cn.exsolo.comm.ex.ExDevException;

import java.util.Collection;
import java.util.List;

/**
 * @author prestolive
 * @date 2023/4/1
 **/
public class ExAssert {

    public static void isNull(Object obj){
        if(obj==null||obj.toString().length()==0) {
            throw new ExDevException("assert null");
        }
    }

    public static void isNull(Object... objs){
        for(Object obj:objs){
            isNull(obj);
        }
    }

    public static void isEmpty(Object obj){
        if(obj==null){
            throw new ExDevException("assert null");
        }
        if(obj instanceof Collection){
            List list = (List) obj;
            if(list.size()==0){
                throw new ExDevException("assert collection empty");
            }
        }else{
            if(obj.toString().length()==0){
                throw new ExDevException("assert zero length");
            }
        }

    }
}
