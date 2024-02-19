package cn.exsolo.basic.item;

import cn.exsolo.kit.item.stereotype.ItemProvider;

/**
 * @author prestolive
 * @date 2021/5/17
 **/

@ItemProvider(tag = "EX_ROLE_SCHEMA",name="中台-系统角色类型")
public enum ExRoleSchemaEnum {

    NORMAL("一般用户"),ADMIN("系统管理");

    private String label;

    ExRoleSchemaEnum(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
