package cn.exsolo.basic.render;

import cn.exsolo.kit.render.impl.SqlCacheDataRender;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;

/**
 * @author prestolive
 * @date 2024/3/6
 **/
@Component
public class UserInfoDataRender extends SqlCacheDataRender {
    @Override
    public void initRender(String keyField, MethodParameter methodParameter) {

    }

    @Override
    public String getSql() {
        return "select id as key,loginCode,userName,phone,email from ex_user";
    }
}
