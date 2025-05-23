package cn.exsolo.springmvcext.plugins;

import cn.exsolo.batis.core.Condition;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.StringUtils;

/**
 * FIXME 目前方案是粗暴的将前端转成condition，但是API生成器无法识别
 *
 * @author prestolive
 * @date 2021/5/15
 **/
public class SpringMvcExtForBatis {

    public static Condition json2Condition(String json, String fieldName) {
        Gson gson = new Gson();
        JsonArray list = gson.fromJson(json,JsonArray.class);
        Condition cond = new Condition();
        for (int i = 0; i < list.size(); i++) {
            JsonObject item = list.get(i).getAsJsonObject();
            String domain = getAsString(item,"domain");
            //没有设置的默认值cond
            domain = domain == null ? "cond" : domain;
            if (!fieldName.equals(domain)) {
                continue;
            }
            String action = getAsString(item,"action");
            String key = getAsString(item,"key");
            JsonElement valueEle = item.get("value");
            Object value =null;
            Object[] valueArr =null;
            if(valueEle.isJsonPrimitive()) {
                if(valueEle.getAsJsonPrimitive().isString()){
                    value = valueEle.getAsString();
                }else if(valueEle.getAsJsonPrimitive().isNumber()){
                    value = valueEle.getAsNumber();
                }
            }else if(valueEle.isJsonArray()){
                JsonArray jsonArr = valueEle.getAsJsonArray();
                valueArr = new Object[jsonArr.size()];
                for (int index = 0; index< jsonArr.size();index ++) {
                    valueArr[index] = jsonArr.get(index).getAsString();
                }
            }
            String order = getAsString(item,"order");
            if (value!=null || valueArr!=null) {
                if ("eq".equals(action)) {
                    cond.eq(key, value);
                } else if ("ne".equals(action)) {
                    cond.ne(key, value);
                } else if ("gt".equals(action)) {
                    cond.gt(key, value);
                } else if ("lt".equals(action)) {
                    cond.lt(key, value);
                } else if ("ge".equals(action)) {
                    cond.ge(key, value);
                } else if ("le".equals(action)) {
                    cond.le(key, value);
                } else if ("lk".equals(action)) {
                    cond.lk(key, value);
                } else if ("lkl".equals(action)) {
                    cond.lkl(key, value);
                } else if ("lkr".equals(action)) {
                    cond.lkr(key, value);
                } else if ("in".equals(action)) {
                    if(valueArr!=null){
                        cond.in(key, valueArr);
                    }else {
                        cond.in(key, new Object[]{value});
                    }
                }
            }
            if (StringUtils.isNotEmpty(order)) {
                cond.orderBy(key, order);
            }
        }
        return cond;
    }

    private static String getAsString(JsonObject item,String key){
        JsonElement ele = item.get(key);
        if(ele==null){
            return null;
        }
        return ele.getAsString();
    }
}
