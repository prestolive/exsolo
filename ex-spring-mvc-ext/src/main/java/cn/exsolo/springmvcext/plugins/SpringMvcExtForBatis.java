package cn.exsolo.springmvcext.plugins;

import cn.exsolo.batis.core.Condition;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * FIXME 目前方案是粗暴的将前端转成condition，但是API生成器无法识别
 * @author prestolive
 * @date 2021/5/15
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
            }else if("ne".equals(action)) {
                cond.ne(key,value);
            }else if("gt".equals(action)) {
                cond.gt(key,value);
            }else if("lt".equals(action)) {
                cond.lt(key,value);
            }else if("ge".equals(action)) {
                cond.ge(key,value);
            }else if("le".equals(action)) {
                cond.le(key,value);
            }else if("lk".equals(action)) {
                cond.lk(key,value);
            }else if("lkl".equals(action)) {
                cond.lkl(key,value);
            }else if("lkr".equals(action)) {
                cond.lkr(key,value);
            }
        }
        return cond;
    }
}
