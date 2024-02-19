package cn.exsolo.basic.item;

import cn.exsolo.kit.item.stereotype.ItemProvider;

/**
 * @author prestolive
 * @date 2021/5/17
 **/

@ItemProvider(tag = "EX_ORG_SCHEMA",name="中台-组织管理类型",customAble = true)
public enum ExOrgSchemaEnum {

    DEFAULT("默认");

    private String label;

    ExOrgSchemaEnum(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
