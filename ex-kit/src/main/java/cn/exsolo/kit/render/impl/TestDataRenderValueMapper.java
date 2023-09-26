package cn.exsolo.kit.render.impl;

import cn.exsolo.batis.core.AbstractSanBatisPO;
import cn.exsolo.batis.core.Condition;
import cn.exsolo.kit.item.po.ItemPO;

/**
 * @author prestolive
 * @date 2021/9/21
 **/
public class TestDataRenderValueMapper extends ExBatisPoDataRenderValueMapper {
    @Override
    Class<? extends AbstractSanBatisPO> getPoClass() {
        return ItemPO.class;
    }

    @Override
    String getMappedKeyField() {
        return "id";
    }

    @Override
    Condition createCondition() {
        return new Condition();
    }

    @Override
    String[] getValueFields() {
        return new String[]{"schema,name,code"};
    }
}
