package cn.exsolo.batis.core;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by prestolive on 2018/9/20.
 */
public class ParamBuilder {

    private Map<String,Object> values;

    public ParamBuilder() {
        this.values = new HashMap<>();
    }

    public ParamBuilder put(String key, Object value){
        this.values.put(key,value);
        return this;
    }

    public Map<String,Object> get(){
        return this.values;
    }

}
