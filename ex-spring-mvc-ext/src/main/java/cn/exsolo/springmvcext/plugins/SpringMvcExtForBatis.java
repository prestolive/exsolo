package cn.exsolo.springmvcext.plugins;

import cn.exsolo.batis.core.Condition;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * @author prestolive
 * @date 2023/5/15
 **/
public class SpringMvcExtForBatis {

    public static Condition json2Condition(String json){
        JSONArray list = JSON.parseArray(json);
        Condition cond = new Condition();
        for(int i=0;i<list.size();i++){
            JSONObject item = list.getJSONObject(i);
            String action = item.getString("action");
            String key = item.getString("key");
            String value = item.getString("value");
            if ("eq".equals(action)) {
                cond.eq(key,value);
            }
        }
        return cond;
    }
}
