package cn.exsolo.console.render;

import cn.exsolo.batis.core.AbstractSanBatisPO;
import cn.exsolo.batis.core.Condition;
import cn.exsolo.console.security.po.UserPO;
import cn.exsolo.kit.render.impl.ExBatisPoDataRenderValueMapper;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

/**
 * @author prestolive
 * @date 2023/9/27
 **/
@Component
public class UserInfoDataRenderValueMapper extends ExBatisPoDataRenderValueMapper {
    @Override
    public Class<? extends AbstractSanBatisPO> getPoClass() {
        return UserPO.class;
    }

    @Override
    public String getMappedKeyField() {
        return "id";
    }

    @Override
    public Condition createCondition() {
        return new Condition();
    }

    @Override
    public String[] getValueFields() {
        return new String[]{"loginCode", "userName", "status"};
    }

    @Override
    public void customRender(String keyValue, JSONObject row) {

    }
}
