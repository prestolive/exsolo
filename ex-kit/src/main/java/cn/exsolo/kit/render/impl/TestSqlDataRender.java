package cn.exsolo.kit.render.impl;

import com.alibaba.fastjson.JSONObject;
import org.springframework.core.MethodParameter;

import java.util.Map;

/**
 * @author prestolive
 * @date 2024/3/6
 **/
public class TestSqlDataRender extends SqlCacheDataRender{


    @Override
    public void initRender(String keyField, MethodParameter methodParameter) {

    }

    @Override
    public String getSql() {
        return null;
    }
}
