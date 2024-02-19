package cn.exsolo.kit.render.impl;

import cn.exsolo.batis.core.AbstractPO;
import cn.exsolo.batis.core.Condition;
import cn.exsolo.kit.item.po.ItemPO;
import com.alibaba.fastjson.JSONObject;

/**
 * @author prestolive
 * @date 2021/9/21
 **/
public class TestDataRenderValueMapper extends ExBatisPoDataRenderValueMapper {
    @Override
    public Class<? extends AbstractPO> getPoClass() {
        return ItemPO.class;
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
        return new String[]{"schema,name,code"};
    }

    @Override
    public void customRender(String keyValue, JSONObject row) {

    }
}
